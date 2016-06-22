/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package openbns.commons.net.codec.sts;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * A {@link io.netty.channel.ChannelHandler} that aggregates an {@link StsMessage}
 * and its following {@link StsContent}s into a single {@link StsMessage} with
 * no following {@link StsContent}s.  It is useful when you don't want to take
 * care of HTTP messages whose transfer encoding is 'chunked'.  Insert this
 * handler after {@link HttpObjectDecoder} in the {@link io.netty.channel.ChannelPipeline}:
 * <pre>
 * {@link io.netty.channel.ChannelPipeline} p = ...;
 * ...
 * p.addLast("decoder", new {@link StsRequestDecoder}());
 * p.addLast("aggregator", <b>new {@link HttpObjectAggregator}(1048576)</b>);
 * ...
 * p.addLast("encoder", new {@link StsResponseEncoder}());
 * p.addLast("handler", new HttpRequestHandler());
 * </pre>
 */
public class HttpObjectAggregator extends MessageToMessageDecoder<StsObject>
{
  public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
  private static final FullStsResponse CONTINUE = new DefaultFullStsResponse( StsResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER );

  private final int maxContentLength;
  private FullStsMessage currentMessage;
  private boolean tooLongFrameFound;

  private int maxCumulationBufferComponents = DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS;
  private ChannelHandlerContext ctx;

  /**
   * Creates a new instance.
   *
   * @param maxContentLength the maximum length of the aggregated content.
   *                         If the length of the aggregated content exceeds this value,
   *                         a {@link TooLongFrameException} will be raised.
   */
  public HttpObjectAggregator( int maxContentLength )
  {
    if( maxContentLength <= 0 )
    {
      throw new IllegalArgumentException( "maxContentLength must be a positive integer: " + maxContentLength );
    }
    this.maxContentLength = maxContentLength;
  }

  /**
   * Returns the maximum number of components in the cumulation buffer.  If the number of
   * the components in the cumulation buffer exceeds this value, the components of the
   * cumulation buffer are consolidated into a single component, involving memory copies.
   * The default value of this property is {@link #DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS}.
   */
  public final int getMaxCumulationBufferComponents()
  {
    return maxCumulationBufferComponents;
  }

  /**
   * Sets the maximum number of components in the cumulation buffer.  If the number of
   * the components in the cumulation buffer exceeds this value, the components of the
   * cumulation buffer are consolidated into a single component, involving memory copies.
   * The default value of this property is {@link #DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS}
   * and its minimum allowed value is {@code 2}.
   */
  public final void setMaxCumulationBufferComponents( int maxCumulationBufferComponents )
  {
    if( maxCumulationBufferComponents < 2 )
    {
      throw new IllegalArgumentException( "maxCumulationBufferComponents: " + maxCumulationBufferComponents +
                                                  " (expected: >= 2)" );
    }

    if( ctx == null )
    {
      this.maxCumulationBufferComponents = maxCumulationBufferComponents;
    }
    else
    {
      throw new IllegalStateException( "decoder properties cannot be changed once the decoder is added to a pipeline." );
    }
  }

  @Override
  protected void decode( final ChannelHandlerContext ctx, StsObject msg, List<Object> out ) throws Exception
  {
    FullStsMessage currentMessage = this.currentMessage;

    if( msg instanceof StsMessage )
    {
      tooLongFrameFound = false;
      assert currentMessage == null;

      StsMessage m = (StsMessage) msg;

      if( !m.getDecoderResult().isSuccess() )
      {
        this.currentMessage = null;
        out.add( ReferenceCountUtil.retain( m ) );
        return;
      }
      if( msg instanceof StsRequest )
      {
        StsRequest header = (StsRequest) msg;
        this.currentMessage = currentMessage = new DefaultFullStsRequest( header.getProtocolVersion(), header.getMethod(), header.getUri(), Unpooled.compositeBuffer( maxCumulationBufferComponents ) );
      }
      else if( msg instanceof StsResponse )
      {
        StsResponse header = (StsResponse) msg;
        this.currentMessage = currentMessage = new DefaultFullStsResponse( header.getStatus(), Unpooled.compositeBuffer( maxCumulationBufferComponents ) );
      }
      else
      {
        throw new Error();
      }

      currentMessage.headers().set( m.headers() );
    }
    else if( msg instanceof StsContent )
    {
      if( tooLongFrameFound )
      {
        if( msg instanceof LastStsContent )
        {
          this.currentMessage = null;
        }
        // already detect the too long frame so just discard the content
        return;
      }
      assert currentMessage != null;

      // Merge the received chunk into the content of the current message.
      StsContent chunk = (StsContent) msg;
      CompositeByteBuf content = (CompositeByteBuf) currentMessage.content();

      if( content.readableBytes() > maxContentLength - chunk.content().readableBytes() )
      {
        tooLongFrameFound = true;

        // release current message to prevent leaks
        currentMessage.release();
        this.currentMessage = null;

        throw new TooLongFrameException( "HTTP content length exceeded " + maxContentLength +
                                                 " bytes." );
      }

      // Append the content of the chunk
      if( chunk.content().isReadable() )
      {
        chunk.retain();
        content.addComponent( chunk.content() );
        content.writerIndex( content.writerIndex() + chunk.content().readableBytes() );
      }

      final boolean last;
      if( !chunk.getDecoderResult().isSuccess() )
      {
        currentMessage.setDecoderResult( DecoderResult.failure( chunk.getDecoderResult().cause() ) );
        last = true;
      }
      else
      {
        last = chunk instanceof LastStsContent;
      }

      if( last )
      {
        this.currentMessage = null;

        // Merge trailing headers into the message.
        if( chunk instanceof LastStsContent )
        {
          LastStsContent trailer = (LastStsContent) chunk;
          currentMessage.headers().add( trailer.trailingHeaders() );
        }

        // Set the 'Content-Length' header.
        currentMessage.headers().set( StsHeaders.Names.CONTENT_LENGTH, String.valueOf( content.readableBytes() ) );

        // All done
        out.add( currentMessage );
      }
    }
    else
    {
      throw new Error();
    }
  }

  @Override
  public void channelInactive( ChannelHandlerContext ctx ) throws Exception
  {
    super.channelInactive( ctx );

    // release current message if it is not null as it may be a left-over
    if( currentMessage != null )
    {
      currentMessage.release();
      currentMessage = null;
    }
  }

  @Override
  public void handlerAdded( ChannelHandlerContext ctx ) throws Exception
  {
    this.ctx = ctx;
  }

  @Override
  public void handlerRemoved( ChannelHandlerContext ctx ) throws Exception
  {
    super.handlerRemoved( ctx );
    // release current message if it is not null as it may be a left-over as there is not much more we can do in
    // this case
    if( currentMessage != null )
    {
      currentMessage.release();
      currentMessage = null;
    }
  }
}
