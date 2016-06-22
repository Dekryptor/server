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
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * The default {@link LastStsContent} implementation.
 */
public class DefaultLastStsContent extends DefaultStsContent implements LastStsContent
{

  private final StsHeaders trailingHeaders;
  private final boolean validateHeaders;

  public DefaultLastStsContent()
  {
    this( Unpooled.buffer( 0 ) );
  }

  public DefaultLastStsContent( ByteBuf content )
  {
    this( content, true );
  }

  public DefaultLastStsContent( ByteBuf content, boolean validateHeaders )
  {
    super( content );
    trailingHeaders = new TrailingHeaders( validateHeaders );
    this.validateHeaders = validateHeaders;
  }

  @Override
  public LastStsContent copy()
  {
    DefaultLastStsContent copy = new DefaultLastStsContent( content().copy(), validateHeaders );
    copy.trailingHeaders().set( trailingHeaders() );
    return copy;
  }

  @Override
  public LastStsContent duplicate()
  {
    DefaultLastStsContent copy = new DefaultLastStsContent( content().duplicate(), validateHeaders );
    copy.trailingHeaders().set( trailingHeaders() );
    return copy;
  }

  @Override
  public LastStsContent retain( int increment )
  {
    super.retain( increment );
    return this;
  }

  @Override
  public LastStsContent retain()
  {
    super.retain();
    return this;
  }

  @Override
  public StsHeaders trailingHeaders()
  {
    return trailingHeaders;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder( super.toString() );
    buf.append( StringUtil.NEWLINE );
    appendHeaders( buf );

    // Remove the last newline.
    buf.setLength( buf.length() - StringUtil.NEWLINE.length() );
    return buf.toString();
  }

  private void appendHeaders( StringBuilder buf )
  {
    for( Map.Entry<String, String> e : trailingHeaders() )
    {
      buf.append( e.getKey() );
      buf.append( ": " );
      buf.append( e.getValue() );
      buf.append( StringUtil.NEWLINE );
    }
  }

  private static final class TrailingHeaders extends DefaultStsHeaders
  {
    TrailingHeaders( boolean validate )
    {
      super( validate );
    }

    @Override
    void validateHeaderName0( CharSequence name )
    {
      super.validateHeaderName0( name );
      if( equalsIgnoreCase( StsHeaders.Names.CONTENT_LENGTH, name ) )
      {
        throw new IllegalArgumentException( "prohibited trailing header: " + name );
      }
    }
  }
}
