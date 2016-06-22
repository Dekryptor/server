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
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * The request getMethod of HTTP or its derived protocols, such as
 * <a href="sts://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
 * <a href="sts://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>.
 */
public class StsMethod implements Comparable<StsMethod>
{
  /**
   * The GET getMethod means retrieve whatever information (in the form of an entity) is identified
   * by the Request-URI.  If the Request-URI refers to a data-producing process, it is the
   * produced data which shall be returned as the entity in the response and not the source text
   * of the process, unless that text happens to be the output of the process.
   */
  public static final StsMethod GET = new StsMethod( "GET", true );

  /**
   * The POST getMethod is used to request that the origin server accept the entity enclosed in the
   * request as a new subordinate of the resource identified by the Request-URI in the
   * Request-Line.
   */
  public static final StsMethod POST = new StsMethod( "POST", true );

  private static final Map<String, StsMethod> methodMap = new HashMap<String, StsMethod>();

  static
  {
    methodMap.put( POST.toString(), POST );
  }

  /**
   * Returns the {@link StsMethod} represented by the specified name.
   * If the specified name is a standard HTTP getMethod name, a cached instance
   * will be returned.  Otherwise, a new instance will be returned.
   */
  public static StsMethod valueOf( String name )
  {
    if( name == null )
    {
      throw new NullPointerException( "name" );
    }

    name = name.trim();
    if( name.isEmpty() )
    {
      throw new IllegalArgumentException( "empty name" );
    }

    StsMethod result = methodMap.get( name );
    if( result != null )
    {
      return result;
    }
    else
    {
      return new StsMethod( name );
    }
  }

  private final String name;
  private final byte[] bytes;

  /**
   * Creates a new HTTP getMethod with the specified name.  You will not need to
   * create a new getMethod unless you are implementing a protocol derived from
   * HTTP, such as
   * <a href="sts://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
   * <a href="sts://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>
   */
  public StsMethod( String name )
  {
    this( name, false );
  }

  private StsMethod( String name, boolean bytes )
  {
    if( name == null )
    {
      throw new NullPointerException( "name" );
    }

    name = name.trim();
    if( name.isEmpty() )
    {
      throw new IllegalArgumentException( "empty name" );
    }

    for( int i = 0; i < name.length(); i++ )
    {
      if( Character.isISOControl( name.charAt( i ) ) || Character.isWhitespace( name.charAt( i ) ) )
      {
        throw new IllegalArgumentException( "invalid character in name" );
      }
    }

    this.name = name;
    if( bytes )
    {
      this.bytes = name.getBytes( CharsetUtil.US_ASCII );
    }
    else
    {
      this.bytes = null;
    }
  }

  /**
   * Returns the name of this getMethod.
   */
  public String name()
  {
    return name;
  }

  @Override
  public int hashCode()
  {
    return name().hashCode();
  }

  @Override
  public boolean equals( Object o )
  {
    if( !(o instanceof StsMethod) )
    {
      return false;
    }

    StsMethod that = (StsMethod) o;
    return name().equals( that.name() );
  }

  @Override
  public String toString()
  {
    return name();
  }

  @Override
  public int compareTo( StsMethod o )
  {
    return name().compareTo( o.name() );
  }

  void encode( ByteBuf buf )
  {
    if( bytes == null )
    {
      StsHeaders.encodeAscii0( name, buf );
    }
    else
    {
      buf.writeBytes( bytes );
    }
  }
}
