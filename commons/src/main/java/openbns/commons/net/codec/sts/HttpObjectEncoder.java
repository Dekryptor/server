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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.StringUtil;

import java.util.List;

import static io.netty.buffer.Unpooled.*;
import static io.netty.handler.codec.http.HttpConstants.CR;
import static io.netty.handler.codec.http.HttpConstants.LF;

/**
 * Encodes an {@link StsMessage} or an {@link StsContent} into
 * a {@link io.netty.buffer.ByteBuf}.
 * <p/>
 * <h3>Extensibility</h3>
 * <p/>
 * Please note that this encoder is designed to be extended to implement
 * a protocol derived from HTTP, such as
 * <a href="sts://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
 * <a href="sts://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>.
 * To implement the encoder of such a derived protocol, extend this class and
 * implement all abstract methods properly.
 */
public abstract class HttpObjectEncoder<H extends StsMessage> extends MessageToMessageEncoder<Object>
{
  private static final byte[] CRLF = { CR, LF };
  private static final byte[] ZERO_CRLF = { '0', CR, LF };
  private static final byte[] ZERO_CRLF_CRLF = { '0', CR, LF, CR, LF };
  private static final ByteBuf CRLF_BUF = unreleasableBuffer( directBuffer( CRLF.length ).writeBytes( CRLF ) );
  private static final ByteBuf ZERO_CRLF_CRLF_BUF = unreleasableBuffer( directBuffer( ZERO_CRLF_CRLF.length ).writeBytes( ZERO_CRLF_CRLF ) );

  private static final int ST_INIT = 0;
  private static final int ST_CONTENT_NON_CHUNK = 1;

  @SuppressWarnings("RedundantFieldInitialization")
  private int state = ST_INIT;

  @Override
  protected void encode( ChannelHandlerContext ctx, Object msg, List<Object> out ) throws Exception
  {
    ByteBuf buf = null;

    if( msg instanceof StsMessage )
    {
      if( state != ST_INIT )
      {
        throw new IllegalStateException( "unexpected message type: " + StringUtil.simpleClassName( msg ) );
      }

      @SuppressWarnings({ "unchecked", "CastConflictsWithInstanceof" })
      H m = (H) msg;

      buf = ctx.alloc().buffer();
      // Encode the message.
      encodeInitialLine( buf, m );
      StsHeaders.encode( m.headers(), buf );
      buf.writeBytes( CRLF );
      state = ST_CONTENT_NON_CHUNK;
    }
    if( msg instanceof StsContent || msg instanceof ByteBuf || msg instanceof FileRegion )
    {
      if( state == ST_INIT )
      {
        throw new IllegalStateException( "unexpected message type: " + StringUtil.simpleClassName( msg ) );
      }

      int contentLength = contentLength( msg );
      if( state == ST_CONTENT_NON_CHUNK )
      {
        if( contentLength > 0 )
        {
          if( buf != null && buf.writableBytes() >= contentLength && msg instanceof StsContent )
          {
            // merge into other buffer for performance reasons
            buf.writeBytes( ((StsContent) msg).content() );
            out.add( buf );
          }
          else
          {
            if( buf != null )
            {
              out.add( buf );
            }
            out.add( encodeAndRetain( msg ) );
          }
        }
        else
        {
          if( buf != null )
          {
            out.add( buf );
          }
          else
          {
            // Need to produce some output otherwise an
            // IllegalStateException will be thrown
            out.add( EMPTY_BUFFER );
          }
        }

        if( msg instanceof LastStsContent )
        {
          state = ST_INIT;
        }
      }
      else
      {
        throw new Error();
      }
    }
    else
    {
      if( buf != null )
      {
        out.add( buf );
      }
    }
  }

  @Override
  public boolean acceptOutboundMessage( Object msg ) throws Exception
  {
    return msg instanceof StsObject || msg instanceof ByteBuf || msg instanceof FileRegion;
  }

  private static Object encodeAndRetain( Object msg )
  {
    if( msg instanceof ByteBuf )
    {
      return ((ByteBuf) msg).retain();
    }
    if( msg instanceof StsContent )
    {
      return ((StsContent) msg).content().retain();
    }
    if( msg instanceof FileRegion )
    {
      return ((FileRegion) msg).retain();
    }
    throw new IllegalStateException( "unexpected message type: " + StringUtil.simpleClassName( msg ) );
  }

  private static int contentLength( Object msg )
  {
    if( msg instanceof StsContent )
    {
      return ((StsContent) msg).content().readableBytes();
    }
    if( msg instanceof ByteBuf )
    {
      return ((ByteBuf) msg).readableBytes();
    }
    if( msg instanceof FileRegion )
    {
      return (int) ((FileRegion) msg).count();
    }
    throw new IllegalStateException( "unexpected message type: " + StringUtil.simpleClassName( msg ) );
  }

  @Deprecated
  protected static void encodeAscii( String s, ByteBuf buf )
  {
    StsHeaders.encodeAscii0( s, buf );
  }

  protected abstract void encodeInitialLine( ByteBuf buf, H message ) throws Exception;
}
