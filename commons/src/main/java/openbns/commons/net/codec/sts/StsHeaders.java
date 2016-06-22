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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import static openbns.commons.net.codec.sts.StsConstants.CR;
import static openbns.commons.net.codec.sts.StsConstants.LF;

/**
 * Provides the constants for the standard HTTP header names and values and
 * commonly used utility methods that accesses an {@link StsMessage}.
 */
public abstract class StsHeaders implements Iterable<Entry<String, String>>
{

  private static final byte[] HEADER_SEPERATOR = { StsConstants.COLON, StsConstants.SP };
  private static final byte[] CRLF = { CR, LF };
  private static final CharSequence CONTENT_LENGTH_ENTITY = newEntity( Names.CONTENT_LENGTH );

  public static final StsHeaders EMPTY_HEADERS = new StsHeaders()
  {
    @Override
    public String get( String name )
    {
      return null;
    }

    @Override
    public List<String> getAll( String name )
    {
      return Collections.emptyList();
    }

    @Override
    public List<Entry<String, String>> entries()
    {
      return Collections.emptyList();
    }

    @Override
    public boolean contains( String name )
    {
      return false;
    }

    @Override
    public boolean isEmpty()
    {
      return true;
    }

    @Override
    public Set<String> names()
    {
      return Collections.emptySet();
    }

    @Override
    public StsHeaders add( String name, Object value )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public StsHeaders add( String name, Iterable<?> values )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public StsHeaders set( String name, Object value )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public StsHeaders set( String name, Iterable<?> values )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public StsHeaders remove( String name )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public StsHeaders clear()
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public Iterator<Entry<String, String>> iterator()
    {
      return entries().iterator();
    }
  };

  public static final class Names
  {
    public static final String CONTENT_LENGTH = "l";
    public static final String SESSION_NUMBER = "s";

    private Names()
    {
    }
  }

  /**
   * @see {@link #getHeader(StsMessage, CharSequence)}
   */
  public static String getHeader( StsMessage message, String name )
  {
    return message.headers().get( name );
  }

  /**
   * Returns the header value with the specified header name.  If there are
   * more than one header value for the specified header name, the first
   * value is returned.
   *
   * @return the header value or {@code null} if there is no such header
   */
  public static String getHeader( StsMessage message, CharSequence name )
  {
    return message.headers().get( name );
  }

  /**
   * @see {@link #getHeader(StsMessage, CharSequence, String)}
   */
  public static String getHeader( StsMessage message, String name, String defaultValue )
  {
    return getHeader( message, (CharSequence) name, defaultValue );
  }

  /**
   * Returns the header value with the specified header name.  If there are
   * more than one header value for the specified header name, the first
   * value is returned.
   *
   * @return the header value or the {@code defaultValue} if there is no such
   * header
   */
  public static String getHeader( StsMessage message, CharSequence name, String defaultValue )
  {
    String value = message.headers().get( name );
    if( value == null )
    {
      return defaultValue;
    }
    return value;
  }

  /**
   * @see {@link #setHeader(StsMessage, CharSequence, Object)}
   */
  public static void setHeader( StsMessage message, String name, Object value )
  {
    message.headers().set( name, value );
  }

  /**
   * Sets a new header with the specified name and value.  If there is an
   * existing header with the same name, the existing header is removed.
   * If the specified value is not a {@link String}, it is converted into a
   * {@link String} by {@link Object#toString()}, except for {@link java.util.Date}
   * and {@link java.util.Calendar} which are formatted to the date format defined in
   * <a href="sts://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
   */
  public static void setHeader( StsMessage message, CharSequence name, Object value )
  {
    message.headers().set( name, value );
  }

  /**
   * @see {@link #setHeader(StsMessage, CharSequence, Iterable)}
   */
  public static void setHeader( StsMessage message, String name, Iterable<?> values )
  {
    message.headers().set( name, values );
  }

  /**
   * Sets a new header with the specified name and values.  If there is an
   * existing header with the same name, the existing header is removed.
   * This getMethod can be represented approximately as the following code:
   * <pre>
   * removeHeader(message, name);
   * for (Object v: values) {
   *     if (v == null) {
   *         break;
   *     }
   *     addHeader(message, name, v);
   * }
   * </pre>
   */
  public static void setHeader( StsMessage message, CharSequence name, Iterable<?> values )
  {
    message.headers().set( name, values );
  }

  /**
   * @see {@link #addHeader(StsMessage, CharSequence, Object)}
   */
  public static void addHeader( StsMessage message, String name, Object value )
  {
    message.headers().add( name, value );
  }

  /**
   * Adds a new header with the specified name and value.
   * If the specified value is not a {@link String}, it is converted into a
   * {@link String} by {@link Object#toString()}, except for {@link java.util.Date}
   * and {@link java.util.Calendar} which are formatted to the date format defined in
   * <a href="sts://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
   */
  public static void addHeader( StsMessage message, CharSequence name, Object value )
  {
    message.headers().add( name, value );
  }

  /**
   * @see {@link #removeHeader(StsMessage, CharSequence)}
   */
  public static void removeHeader( StsMessage message, String name )
  {
    message.headers().remove( name );
  }

  /**
   * Removes the header with the specified name.
   */
  public static void removeHeader( StsMessage message, CharSequence name )
  {
    message.headers().remove( name );
  }

  /**
   * Removes all headers from the specified message.
   */
  public static void clearHeaders( StsMessage message )
  {
    message.headers().clear();
  }

  /**
   * @see {@link #getIntHeader(StsMessage, CharSequence)}
   */
  public static int getIntHeader( StsMessage message, String name )
  {
    return getIntHeader( message, (CharSequence) name );
  }

  /**
   * Returns the integer header value with the specified header name.  If
   * there are more than one header value for the specified header name, the
   * first value is returned.
   *
   * @return the header value
   * @throws NumberFormatException if there is no such header or the header value is not a number
   */
  public static int getIntHeader( StsMessage message, CharSequence name )
  {
    String value = getHeader( message, name );
    if( value == null )
    {
      throw new NumberFormatException( "header not found: " + name );
    }
    return Integer.parseInt( value );
  }

  /**
   * @see {@link #getIntHeader(StsMessage, CharSequence, int)}
   */
  public static int getIntHeader( StsMessage message, String name, int defaultValue )
  {
    return getIntHeader( message, (CharSequence) name, defaultValue );
  }

  /**
   * Returns the integer header value with the specified header name.  If
   * there are more than one header value for the specified header name, the
   * first value is returned.
   *
   * @return the header value or the {@code defaultValue} if there is no such
   * header or the header value is not a number
   */
  public static int getIntHeader( StsMessage message, CharSequence name, int defaultValue )
  {
    String value = getHeader( message, name );
    if( value == null )
    {
      return defaultValue;
    }

    try
    {
      return Integer.parseInt( value );
    }
    catch( NumberFormatException e )
    {
      return defaultValue;
    }
  }

  /**
   * @see {@link #setIntHeader(StsMessage, CharSequence, int)}
   */
  public static void setIntHeader( StsMessage message, String name, int value )
  {
    message.headers().set( name, value );
  }

  /**
   * Sets a new integer header with the specified name and value.  If there
   * is an existing header with the same name, the existing header is removed.
   */
  public static void setIntHeader( StsMessage message, CharSequence name, int value )
  {
    message.headers().set( name, value );
  }

  /**
   * @see {@link #setIntHeader(StsMessage, CharSequence, Iterable)}
   */
  public static void setIntHeader( StsMessage message, String name, Iterable<Integer> values )
  {
    message.headers().set( name, values );
  }

  /**
   * Sets a new integer header with the specified name and values.  If there
   * is an existing header with the same name, the existing header is removed.
   */
  public static void setIntHeader( StsMessage message, CharSequence name, Iterable<Integer> values )
  {
    message.headers().set( name, values );
  }

  /**
   * @see {@link #addIntHeader(StsMessage, CharSequence, int)}
   */
  public static void addIntHeader( StsMessage message, String name, int value )
  {
    message.headers().add( name, value );
  }

  /**
   * Adds a new integer header with the specified name and value.
   */
  public static void addIntHeader( StsMessage message, CharSequence name, int value )
  {
    message.headers().add( name, value );
  }

  public static long getContentLength( StsMessage message )
  {
    String value = getHeader( message, CONTENT_LENGTH_ENTITY );
    if( value != null )
    {
      return Long.parseLong( value );
    }

    // Otherwise we don't.
    throw new NumberFormatException( "header not found: " + Names.CONTENT_LENGTH );
  }

  /**
   * Returns the length of the content.  Please note that this value is
   * not retrieved from {@link StsContent#content()} but from the
   * {@code "Content-Length"} header, and thus they are independent from each
   * other.
   *
   * @return the content length or {@code defaultValue} if this message does
   * not have the {@code "Content-Length"} header or its value is not
   * a number
   */
  public static long getContentLength( StsMessage message, long defaultValue )
  {
    String contentLength = message.headers().get( CONTENT_LENGTH_ENTITY );
    if( contentLength != null )
    {
      try
      {
        return Long.parseLong( contentLength );
      }
      catch( NumberFormatException e )
      {
        return defaultValue;
      }
    }

    // Otherwise we don't.
    return defaultValue;
  }

  /**
   * Sets the {@code "Content-Length"} header.
   */
  public static void setContentLength( StsMessage message, long length )
  {
    message.headers().set( CONTENT_LENGTH_ENTITY, length );
  }

  /**
   * Validates the name of a header
   *
   * @param headerName The header name being validated
   */
  static void validateHeaderName( CharSequence headerName )
  {
    //Check to see if the name is null
    if( headerName == null )
    {
      throw new NullPointerException( "Header names cannot be null" );
    }
    //Go through each of the characters in the name
    for( int index = 0; index < headerName.length(); index++ )
    {
      //Actually get the character
      char character = headerName.charAt( index );

      //Check to see if the character is not an ASCII character
      if( character > 127 )
      {
        throw new IllegalArgumentException( "Header name cannot contain non-ASCII characters: " + headerName );
      }

      //Check for prohibited characters.
      switch( character )
      {
        case '\t':
        case '\n':
        case 0x0b:
        case '\f':
        case '\r':
        case ' ':
        case ',':
        case ':':
        case ';':
        case '=':
          throw new IllegalArgumentException( "Header name cannot contain the following prohibited characters: " +
                                                      "=,;: \\t\\r\\n\\v\\f: " + headerName );
      }
    }
  }

  /**
   * Validates the specified header value
   *
   * @param headerValue The value being validated
   */
  static void validateHeaderValue( CharSequence headerValue )
  {
    //Check to see if the value is null
    if( headerValue == null )
    {
      throw new NullPointerException( "Header values cannot be null" );
    }

        /*
         * Set up the state of the validation
         *
         * States are as follows:
         *
         * 0: Previous character was neither CR nor LF
         * 1: The previous character was CR
         * 2: The previous character was LF
         */
    int state = 0;

    //Start looping through each of the character

    for( int index = 0; index < headerValue.length(); index++ )
    {
      char character = headerValue.charAt( index );

      //Check the absolutely prohibited characters.
      switch( character )
      {
        case 0x0b: // Vertical tab
          throw new IllegalArgumentException( "Header value contains a prohibited character '\\v': " + headerValue );
        case '\f':
          throw new IllegalArgumentException( "Header value contains a prohibited character '\\f': " + headerValue );
      }

      // Check the CRLF (HT | SP) pattern
      switch( state )
      {
        case 0:
          switch( character )
          {
            case '\r':
              state = 1;
              break;
            case '\n':
              state = 2;
              break;
          }
          break;
        case 1:
          switch( character )
          {
            case '\n':
              state = 2;
              break;
            default:
              throw new IllegalArgumentException( "Only '\\n' is allowed after '\\r': " + headerValue );
          }
          break;
        case 2:
          switch( character )
          {
            case '\t':
            case ' ':
              state = 0;
              break;
            default:
              throw new IllegalArgumentException( "Only ' ' and '\\t' are allowed after '\\n': " + headerValue );
          }
      }
    }

    if( state != 0 )
    {
      throw new IllegalArgumentException( "Header value must not end with '\\r' or '\\n':" + headerValue );
    }
  }

  public static boolean isContentLengthSet( StsMessage m )
  {
    return m.headers().contains( CONTENT_LENGTH_ENTITY );
  }

  /**
   * Returns {@code true} if both {@link CharSequence}'s are equals when ignore the case.
   * This only supports US_ASCII.
   */
  public static boolean equalsIgnoreCase( CharSequence name1, CharSequence name2 )
  {
    if( name1 == name2 )
    {
      return true;
    }

    if( name1 == null || name2 == null )
    {
      return false;
    }

    int nameLen = name1.length();
    if( nameLen != name2.length() )
    {
      return false;
    }

    for( int i = nameLen - 1; i >= 0; i-- )
    {
      char c1 = name1.charAt( i );
      char c2 = name2.charAt( i );
      if( c1 != c2 )
      {
        if( c1 >= 'A' && c1 <= 'Z' )
        {
          c1 += 32;
        }
        if( c2 >= 'A' && c2 <= 'Z' )
        {
          c2 += 32;
        }
        if( c1 != c2 )
        {
          return false;
        }
      }
    }
    return true;
  }

  static int hash( CharSequence name )
  {
    if( name instanceof HttpHeaderEntity )
    {
      return ((HttpHeaderEntity) name).hash();
    }
    int h = 0;
    for( int i = name.length() - 1; i >= 0; i-- )
    {
      char c = name.charAt( i );
      if( c >= 'A' && c <= 'Z' )
      {
        c += 32;
      }
      h = 31 * h + c;
    }

    if( h > 0 )
    {
      return h;
    }
    else if( h == Integer.MIN_VALUE )
    {
      return Integer.MAX_VALUE;
    }
    else
    {
      return -h;
    }
  }

  static void encode( StsHeaders headers, ByteBuf buf )
  {
    if( headers instanceof DefaultStsHeaders )
    {
      ((DefaultStsHeaders) headers).encode( buf );
    }
    else
    {
      for( Entry<String, String> header : headers )
      {
        encode( header.getKey(), header.getValue(), buf );
      }
    }
  }

  static void encode( CharSequence key, CharSequence value, ByteBuf buf )
  {
    encodeAscii( key, buf );
    buf.writeBytes( HEADER_SEPERATOR );
    encodeAscii( value, buf );
    buf.writeBytes( CRLF );
  }

  public static void encodeAscii( CharSequence seq, ByteBuf buf )
  {
    if( seq instanceof HttpHeaderEntity )
    {
      ((HttpHeaderEntity) seq).encode( buf );
    }
    else
    {
      encodeAscii0( seq, buf );
    }
  }

  static void encodeAscii0( CharSequence seq, ByteBuf buf )
  {
    int length = seq.length();
    for( int i = 0; i < length; i++ )
    {
      buf.writeByte( (byte) seq.charAt( i ) );
    }
  }

  /**
   * Create a new {@link CharSequence} which is optimized for reuse as {@link StsHeaders} name or value.
   * So if you have a Header name or value that you want to reuse you should make use of this.
   */
  public static CharSequence newEntity( String name )
  {
    if( name == null )
    {
      throw new NullPointerException( "name" );
    }
    return new HttpHeaderEntity( name );
  }

  protected StsHeaders()
  {
  }

  /**
   * @see {@link #get(CharSequence)}
   */
  public abstract String get( String name );

  /**
   * Returns the value of a header with the specified name.  If there are
   * more than one values for the specified name, the first value is returned.
   *
   * @param name The name of the header to search
   * @return The first header value or {@code null} if there is no such header
   */
  public String get( CharSequence name )
  {
    return get( name.toString() );
  }

  /**
   * @see {@link #getAll(CharSequence)}
   */
  public abstract List<String> getAll( String name );

  /**
   * Returns the values of headers with the specified name
   *
   * @param name The name of the headers to search
   * @return A {@link java.util.List} of header values which will be empty if no values
   * are found
   */
  public List<String> getAll( CharSequence name )
  {
    return getAll( name.toString() );
  }

  /**
   * Returns a new {@link java.util.List} that contains all headers in this object.  Note that modifying the
   * returned {@link java.util.List} will not affect the state of this object.  If you intend to enumerate over the header
   * entries only, use {@link #iterator()} instead, which has much less overhead.
   */
  public abstract List<Entry<String, String>> entries();

  /**
   * @see {@link #contains(CharSequence)}
   */
  public abstract boolean contains( String name );

  /**
   * Checks to see if there is a header with the specified name
   *
   * @param name The name of the header to search for
   * @return True if at least one header is found
   */
  public boolean contains( CharSequence name )
  {
    return contains( name.toString() );
  }

  /**
   * Checks if no header exists.
   */
  public abstract boolean isEmpty();

  /**
   * Returns a new {@link java.util.Set} that contains the names of all headers in this object.  Note that modifying the
   * returned {@link java.util.Set} will not affect the state of this object.  If you intend to enumerate over the header
   * entries only, use {@link #iterator()} instead, which has much less overhead.
   */
  public abstract Set<String> names();

  /**
   * @see {@link #add(CharSequence, Object)}
   */
  public abstract StsHeaders add( String name, Object value );

  /**
   * Adds a new header with the specified name and value.
   * <p/>
   * If the specified value is not a {@link String}, it is converted
   * into a {@link String} by {@link Object#toString()}, except in the cases
   * of {@link java.util.Date} and {@link java.util.Calendar}, which are formatted to the date
   * format defined in <a href="sts://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
   *
   * @param name  The name of the header being added
   * @param value The value of the header being added
   * @return {@code this}
   */
  public StsHeaders add( CharSequence name, Object value )
  {
    return add( name.toString(), value );
  }

  /**
   * @see {@link #add(CharSequence, Iterable)}
   */
  public abstract StsHeaders add( String name, Iterable<?> values );

  /**
   * Adds a new header with the specified name and values.
   * <p/>
   * This getMethod can be represented approximately as the following code:
   * <pre>
   * for (Object v: values) {
   *     if (v == null) {
   *         break;
   *     }
   *     headers.add(name, v);
   * }
   * </pre>
   *
   * @param name   The name of the headers being set
   * @param values The values of the headers being set
   * @return {@code this}
   */
  public StsHeaders add( CharSequence name, Iterable<?> values )
  {
    return add( name.toString(), values );
  }

  /**
   * Adds all header entries of the specified {@code headers}.
   *
   * @return {@code this}
   */
  public StsHeaders add( StsHeaders headers )
  {
    if( headers == null )
    {
      throw new NullPointerException( "headers" );
    }
    for( Entry<String, String> e : headers )
    {
      add( e.getKey(), e.getValue() );
    }
    return this;
  }

  /**
   * @see {@link #set(CharSequence, Object)}
   */
  public abstract StsHeaders set( String name, Object value );

  /**
   * Sets a header with the specified name and value.
   * <p/>
   * If there is an existing header with the same name, it is removed.
   * If the specified value is not a {@link String}, it is converted into a
   * {@link String} by {@link Object#toString()}, except for {@link java.util.Date}
   * and {@link java.util.Calendar}, which are formatted to the date format defined in
   * <a href="sts://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
   *
   * @param name  The name of the header being set
   * @param value The value of the header being set
   * @return {@code this}
   */
  public StsHeaders set( CharSequence name, Object value )
  {
    return set( name.toString(), value );
  }

  /**
   * @see {@link #set(CharSequence, Iterable)}
   */
  public abstract StsHeaders set( String name, Iterable<?> values );

  /**
   * Sets a header with the specified name and values.
   * <p/>
   * If there is an existing header with the same name, it is removed.
   * This getMethod can be represented approximately as the following code:
   * <pre>
   * headers.remove(name);
   * for (Object v: values) {
   *     if (v == null) {
   *         break;
   *     }
   *     headers.add(name, v);
   * }
   * </pre>
   *
   * @param name   The name of the headers being set
   * @param values The values of the headers being set
   * @return {@code this}
   */
  public StsHeaders set( CharSequence name, Iterable<?> values )
  {
    return set( name.toString(), values );
  }

  /**
   * Cleans the current header entries and copies all header entries of the specified {@code headers}.
   *
   * @return {@code this}
   */
  public StsHeaders set( StsHeaders headers )
  {
    if( headers == null )
    {
      throw new NullPointerException( "headers" );
    }
    clear();
    for( Entry<String, String> e : headers )
    {
      add( e.getKey(), e.getValue() );
    }
    return this;
  }

  /**
   * @see {@link #remove(CharSequence)}
   */
  public abstract StsHeaders remove( String name );

  /**
   * Removes the header with the specified name.
   *
   * @param name The name of the header to remove
   * @return {@code this}
   */
  public StsHeaders remove( CharSequence name )
  {
    return remove( name.toString() );
  }

  /**
   * Removes all headers from this {@link StsMessage}.
   *
   * @return {@code this}
   */
  public abstract StsHeaders clear();

  /**
   * @see {@link #contains(CharSequence, CharSequence, boolean)}
   */
  public boolean contains( String name, String value, boolean ignoreCaseValue )
  {
    List<String> values = getAll( name );
    if( values.isEmpty() )
    {
      return false;
    }

    for( String v : values )
    {
      if( ignoreCaseValue )
      {
        if( equalsIgnoreCase( v, value ) )
        {
          return true;
        }
      }
      else
      {
        if( v.equals( value ) )
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns {@code true} if a header with the name and value exists.
   *
   * @param name            the headername
   * @param value           the value
   * @param ignoreCaseValue {@code true} if case should be ignored
   * @return contains         {@code true} if it contains it {@code false} otherwise
   */
  public boolean contains( CharSequence name, CharSequence value, boolean ignoreCaseValue )
  {
    return contains( name.toString(), value.toString(), ignoreCaseValue );
  }
}
