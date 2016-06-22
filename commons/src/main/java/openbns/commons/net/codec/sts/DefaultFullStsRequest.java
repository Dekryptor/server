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
 * Default implementation of {@link FullStsRequest}.
 */
public class DefaultFullStsRequest extends DefaultStsRequest implements FullStsRequest
{
  private final ByteBuf content;
  private final StsHeaders trailingHeader;
  private final boolean validateHeaders;

  public DefaultFullStsRequest( StsVersion stsVersion, StsMethod method, String uri )
  {
    this( stsVersion, method, uri, Unpooled.buffer( 0 ) );
  }

  public DefaultFullStsRequest( StsVersion stsVersion, StsMethod method, String uri, ByteBuf content )
  {
    this( stsVersion, method, uri, content, true );
  }

  public DefaultFullStsRequest( StsVersion stsVersion, StsMethod method, String uri, ByteBuf content,
                                boolean validateHeaders )
  {
    super( stsVersion, method, uri, validateHeaders );
    if( content == null )
    {
      throw new NullPointerException( "content" );
    }
    this.content = content;
    trailingHeader = new DefaultStsHeaders( validateHeaders );
    this.validateHeaders = validateHeaders;
  }

  @Override
  public StsHeaders trailingHeaders()
  {
    return trailingHeader;
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
  public FullStsRequest retain()
  {
    content.retain();
    return this;
  }

  @Override
  public FullStsRequest retain( int increment )
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
  public FullStsRequest setMethod( StsMethod method )
  {
    super.setMethod( method );
    return this;
  }

  @Override
  public FullStsRequest setUri( String uri )
  {
    super.setUri( uri );
    return this;
  }

  @Override
  public FullStsRequest copy()
  {
    DefaultFullStsRequest copy = new DefaultFullStsRequest( getProtocolVersion(), getMethod(), getUri(), content().copy(), validateHeaders );
    copy.headers().set( headers() );
    copy.trailingHeaders().set( trailingHeaders() );
    return copy;
  }

  @Override
  public FullStsRequest duplicate()
  {
    DefaultFullStsRequest duplicate = new DefaultFullStsRequest( getProtocolVersion(), getMethod(), getUri(), content().duplicate(), validateHeaders );
    duplicate.headers().set( headers() );
    duplicate.trailingHeaders().set( trailingHeaders() );
    return duplicate;
  }
}
