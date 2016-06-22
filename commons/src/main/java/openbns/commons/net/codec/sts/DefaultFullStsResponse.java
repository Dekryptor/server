/*
 * Copyright 2013 The Netty Project
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
import io.netty.buffer.Unpooled;

/**
 * Default implementation of a {@link FullStsResponse}.
 */
public class DefaultFullStsResponse extends DefaultStsResponse implements FullStsResponse
{

  private final ByteBuf content;
  private final StsHeaders trailingHeaders;
  private final boolean validateHeaders;

  public DefaultFullStsResponse( StsResponseStatus status )
  {
    this( status, Unpooled.buffer( 0 ) );
  }

  public DefaultFullStsResponse( StsResponseStatus status, ByteBuf content )
  {
    this( status, content, true );
  }

  public DefaultFullStsResponse( StsResponseStatus status, ByteBuf content, boolean validateHeaders )
  {
    super( status, validateHeaders );
    if( content == null )
    {
      throw new NullPointerException( "content" );
    }
    this.content = content;
    trailingHeaders = new DefaultStsHeaders( validateHeaders );
    this.validateHeaders = validateHeaders;
  }

  @Override
  public StsHeaders trailingHeaders()
  {
    return trailingHeaders;
  }

  @Override
  public ByteBuf content()
  {
    return content;
  }

  @Override
  public int refCnt()
  {
    return content.refCnt();
  }

  @Override
  public FullStsResponse retain()
  {
    content.retain();
    return this;
  }

  @Override
  public FullStsResponse retain( int increment )
  {
    content.retain( increment );
    return this;
  }

  @Override
  public boolean release()
  {
    return content.release();
  }

  @Override
  public boolean release( int decrement )
  {
    return content.release( decrement );
  }

  @Override
  public FullStsResponse setStatus( StsResponseStatus status )
  {
    super.setStatus( status );
    return this;
  }

  @Override
  public FullStsResponse copy()
  {
    DefaultFullStsResponse copy = new DefaultFullStsResponse( getStatus(), content().copy(), validateHeaders );
    copy.headers().set( headers() );
    copy.trailingHeaders().set( trailingHeaders() );
    return copy;
  }

  @Override
  public FullStsResponse duplicate()
  {
    DefaultFullStsResponse duplicate = new DefaultFullStsResponse( getStatus(), content().duplicate(), validateHeaders );
    duplicate.headers().set( headers() );
    duplicate.trailingHeaders().set( trailingHeaders() );
    return duplicate;
  }
}
