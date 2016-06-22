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

import static openbns.commons.net.codec.sts.StsConstants.SP;

/**
 * The response code and its description of HTTP or its derived protocols, such as
 * <a href="sts://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
 * <a href="sts://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>.
 */
public class StsResponseStatus implements Comparable<StsResponseStatus>
{

  /**
   * 100 Continue
   */
  public static final StsResponseStatus CONTINUE = new StsResponseStatus( 100, "Continue", true );

  /**
   * 101 Switching Protocols
   */
  public static final StsResponseStatus SWITCHING_PROTOCOLS = new StsResponseStatus( 101, "Switching Protocols", true );

  /**
   * 102 Processing (WebDAV, RFC2518)
   */
  public static final StsResponseStatus PROCESSING = new StsResponseStatus( 102, "Processing", true );

  /**
   * 200 OK
   */
  public static final StsResponseStatus OK = new StsResponseStatus( 200, "OK", true );

  /**
   * 201 Created
   */
  public static final StsResponseStatus CREATED = new StsResponseStatus( 201, "Created", true );

  /**
   * 202 Accepted
   */
  public static final StsResponseStatus ACCEPTED = new StsResponseStatus( 202, "Accepted", true );

  /**
   * 203 Non-Authoritative Information (since HTTP/1.1)
   */
  public static final StsResponseStatus NON_AUTHORITATIVE_INFORMATION = new StsResponseStatus( 203, "Non-Authoritative Information", true );

  /**
   * 204 No Content
   */
  public static final StsResponseStatus NO_CONTENT = new StsResponseStatus( 204, "No Content", true );

  /**
   * 205 Reset Content
   */
  public static final StsResponseStatus RESET_CONTENT = new StsResponseStatus( 205, "Reset Content", true );

  /**
   * 206 Partial Content
   */
  public static final StsResponseStatus PARTIAL_CONTENT = new StsResponseStatus( 206, "Partial Content", true );

  /**
   * 207 Multi-Status (WebDAV, RFC2518)
   */
  public static final StsResponseStatus MULTI_STATUS = new StsResponseStatus( 207, "Multi-Status", true );

  /**
   * 300 Multiple Choices
   */
  public static final StsResponseStatus MULTIPLE_CHOICES = new StsResponseStatus( 300, "Multiple Choices", true );

  /**
   * 301 Moved Permanently
   */
  public static final StsResponseStatus MOVED_PERMANENTLY = new StsResponseStatus( 301, "Moved Permanently", true );

  /**
   * 302 Found
   */
  public static final StsResponseStatus FOUND = new StsResponseStatus( 302, "Found", true );

  /**
   * 303 See Other (since HTTP/1.1)
   */
  public static final StsResponseStatus SEE_OTHER = new StsResponseStatus( 303, "See Other", true );

  /**
   * 304 Not Modified
   */
  public static final StsResponseStatus NOT_MODIFIED = new StsResponseStatus( 304, "Not Modified", true );

  /**
   * 305 Use Proxy (since HTTP/1.1)
   */
  public static final StsResponseStatus USE_PROXY = new StsResponseStatus( 305, "Use Proxy", true );

  /**
   * 307 Temporary Redirect (since HTTP/1.1)
   */
  public static final StsResponseStatus TEMPORARY_REDIRECT = new StsResponseStatus( 307, "Temporary Redirect", true );

  /**
   * 400 Bad Request
   */
  public static final StsResponseStatus NOT_ONLINE = new StsResponseStatus( 400, "ErrUserNotOnline", true );

  /**
   * 401 Unauthorized
   */
  public static final StsResponseStatus UNAUTHORIZED = new StsResponseStatus( 401, "Unauthorized", true );

  /**
   * 402 Payment Required
   */
  public static final StsResponseStatus PAYMENT_REQUIRED = new StsResponseStatus( 402, "Payment Required", true );

  /**
   * 403 Forbidden
   */
  public static final StsResponseStatus FORBIDDEN = new StsResponseStatus( 403, "Forbidden", true );

  /**
   * 404 Not Found
   */
  public static final StsResponseStatus NOT_FOUND = new StsResponseStatus( 404, "Not Found", true );

  /**
   * 405 Method Not Allowed
   */
  public static final StsResponseStatus METHOD_NOT_ALLOWED = new StsResponseStatus( 405, "Method Not Allowed", true );

  /**
   * 406 Not Acceptable
   */
  public static final StsResponseStatus NOT_ACCEPTABLE = new StsResponseStatus( 406, "Not Acceptable", true );

  /**
   * 407 Proxy Authentication Required
   */
  public static final StsResponseStatus PROXY_AUTHENTICATION_REQUIRED = new StsResponseStatus( 407, "Proxy Authentication Required", true );

  /**
   * 408 Request Timeout
   */
  public static final StsResponseStatus REQUEST_TIMEOUT = new StsResponseStatus( 408, "Request Timeout", true );

  /**
   * 409 Conflict
   */
  public static final StsResponseStatus CONFLICT = new StsResponseStatus( 409, "Conflict", true );

  /**
   * 410 Gone
   */
  public static final StsResponseStatus GONE = new StsResponseStatus( 410, "Gone", true );

  /**
   * 411 Length Required
   */
  public static final StsResponseStatus LENGTH_REQUIRED = new StsResponseStatus( 411, "Length Required", true );

  /**
   * 412 Precondition Failed
   */
  public static final StsResponseStatus PRECONDITION_FAILED = new StsResponseStatus( 412, "Precondition Failed", true );

  /**
   * 413 Request Entity Too Large
   */
  public static final StsResponseStatus REQUEST_ENTITY_TOO_LARGE = new StsResponseStatus( 413, "Request Entity Too Large", true );

  /**
   * 414 Request-URI Too Long
   */
  public static final StsResponseStatus REQUEST_URI_TOO_LONG = new StsResponseStatus( 414, "Request-URI Too Long", true );

  /**
   * 415 Unsupported Media Type
   */
  public static final StsResponseStatus UNSUPPORTED_MEDIA_TYPE = new StsResponseStatus( 415, "Unsupported Media Type", true );

  /**
   * 416 Requested Range Not Satisfiable
   */
  public static final StsResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = new StsResponseStatus( 416, "Requested Range Not Satisfiable", true );

  /**
   * 417 Expectation Failed
   */
  public static final StsResponseStatus EXPECTATION_FAILED = new StsResponseStatus( 417, "Expectation Failed", true );

  /**
   * 422 Unprocessable Entity (WebDAV, RFC4918)
   */
  public static final StsResponseStatus UNPROCESSABLE_ENTITY = new StsResponseStatus( 422, "Unprocessable Entity", true );

  /**
   * 423 Locked (WebDAV, RFC4918)
   */
  public static final StsResponseStatus LOCKED = new StsResponseStatus( 423, "Locked", true );

  /**
   * 424 Failed Dependency (WebDAV, RFC4918)
   */
  public static final StsResponseStatus FAILED_DEPENDENCY = new StsResponseStatus( 424, "Failed Dependency", true );

  /**
   * 425 Unordered Collection (WebDAV, RFC3648)
   */
  public static final StsResponseStatus UNORDERED_COLLECTION = new StsResponseStatus( 425, "Unordered Collection", true );

  /**
   * 426 Upgrade Required (RFC2817)
   */
  public static final StsResponseStatus UPGRADE_REQUIRED = new StsResponseStatus( 426, "Upgrade Required", true );

  /**
   * 428 Precondition Required (RFC6585)
   */
  public static final StsResponseStatus PRECONDITION_REQUIRED = new StsResponseStatus( 428, "Precondition Required", true );

  /**
   * 429 Too Many Requests (RFC6585)
   */
  public static final StsResponseStatus TOO_MANY_REQUESTS = new StsResponseStatus( 429, "Too Many Requests", true );

  /**
   * 431 Request Header Fields Too Large (RFC6585)
   */
  public static final StsResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = new StsResponseStatus( 431, "Request Header Fields Too Large", true );

  /**
   * 500 Internal Server Error
   */
  public static final StsResponseStatus INTERNAL_SERVER_ERROR = new StsResponseStatus( 500, "Internal Server Error", true );

  /**
   * 501 Not Implemented
   */
  public static final StsResponseStatus NOT_IMPLEMENTED = new StsResponseStatus( 501, "Not Implemented", true );

  /**
   * 502 Bad Gateway
   */
  public static final StsResponseStatus BAD_GATEWAY = new StsResponseStatus( 502, "Bad Gateway", true );

  /**
   * 503 Service Unavailable
   */
  public static final StsResponseStatus SERVICE_UNAVAILABLE = new StsResponseStatus( 503, "Service Unavailable", true );

  /**
   * 504 Gateway Timeout
   */
  public static final StsResponseStatus GATEWAY_TIMEOUT = new StsResponseStatus( 504, "Gateway Timeout", true );

  /**
   * 505 HTTP Version Not Supported
   */
  public static final StsResponseStatus HTTP_VERSION_NOT_SUPPORTED = new StsResponseStatus( 505, "HTTP Version Not Supported", true );

  /**
   * 506 Variant Also Negotiates (RFC2295)
   */
  public static final StsResponseStatus VARIANT_ALSO_NEGOTIATES = new StsResponseStatus( 506, "Variant Also Negotiates", true );

  /**
   * 507 Insufficient Storage (WebDAV, RFC4918)
   */
  public static final StsResponseStatus INSUFFICIENT_STORAGE = new StsResponseStatus( 507, "Insufficient Storage", true );

  /**
   * 510 Not Extended (RFC2774)
   */
  public static final StsResponseStatus NOT_EXTENDED = new StsResponseStatus( 510, "Not Extended", true );

  /**
   * 511 Network Authentication Required (RFC6585)
   */
  public static final StsResponseStatus NETWORK_AUTHENTICATION_REQUIRED = new StsResponseStatus( 511, "Network Authentication Required", true );

  /**
   * Returns the {@link StsResponseStatus} represented by the specified code.
   * If the specified code is a standard HTTP getStatus code, a cached instance
   * will be returned.  Otherwise, a new instance will be returned.
   */
  public static StsResponseStatus valueOf( int code )
  {
    switch( code )
    {
      case 100:
        return CONTINUE;
      case 101:
        return SWITCHING_PROTOCOLS;
      case 102:
        return PROCESSING;
      case 200:
        return OK;
      case 201:
        return CREATED;
      case 202:
        return ACCEPTED;
      case 203:
        return NON_AUTHORITATIVE_INFORMATION;
      case 204:
        return NO_CONTENT;
      case 205:
        return RESET_CONTENT;
      case 206:
        return PARTIAL_CONTENT;
      case 207:
        return MULTI_STATUS;
      case 300:
        return MULTIPLE_CHOICES;
      case 301:
        return MOVED_PERMANENTLY;
      case 302:
        return FOUND;
      case 303:
        return SEE_OTHER;
      case 304:
        return NOT_MODIFIED;
      case 305:
        return USE_PROXY;
      case 307:
        return TEMPORARY_REDIRECT;
      case 400:
        return NOT_ONLINE;
      case 401:
        return UNAUTHORIZED;
      case 402:
        return PAYMENT_REQUIRED;
      case 403:
        return FORBIDDEN;
      case 404:
        return NOT_FOUND;
      case 405:
        return METHOD_NOT_ALLOWED;
      case 406:
        return NOT_ACCEPTABLE;
      case 407:
        return PROXY_AUTHENTICATION_REQUIRED;
      case 408:
        return REQUEST_TIMEOUT;
      case 409:
        return CONFLICT;
      case 410:
        return GONE;
      case 411:
        return LENGTH_REQUIRED;
      case 412:
        return PRECONDITION_FAILED;
      case 413:
        return REQUEST_ENTITY_TOO_LARGE;
      case 414:
        return REQUEST_URI_TOO_LONG;
      case 415:
        return UNSUPPORTED_MEDIA_TYPE;
      case 416:
        return REQUESTED_RANGE_NOT_SATISFIABLE;
      case 417:
        return EXPECTATION_FAILED;
      case 422:
        return UNPROCESSABLE_ENTITY;
      case 423:
        return LOCKED;
      case 424:
        return FAILED_DEPENDENCY;
      case 425:
        return UNORDERED_COLLECTION;
      case 426:
        return UPGRADE_REQUIRED;
      case 428:
        return PRECONDITION_REQUIRED;
      case 429:
        return TOO_MANY_REQUESTS;
      case 431:
        return REQUEST_HEADER_FIELDS_TOO_LARGE;
      case 500:
        return INTERNAL_SERVER_ERROR;
      case 501:
        return NOT_IMPLEMENTED;
      case 502:
        return BAD_GATEWAY;
      case 503:
        return SERVICE_UNAVAILABLE;
      case 504:
        return GATEWAY_TIMEOUT;
      case 505:
        return HTTP_VERSION_NOT_SUPPORTED;
      case 506:
        return VARIANT_ALSO_NEGOTIATES;
      case 507:
        return INSUFFICIENT_STORAGE;
      case 510:
        return NOT_EXTENDED;
      case 511:
        return NETWORK_AUTHENTICATION_REQUIRED;
    }

    final String reasonPhrase;

    if( code < 100 )
    {
      reasonPhrase = "Unknown Status";
    }
    else if( code < 200 )
    {
      reasonPhrase = "Informational";
    }
    else if( code < 300 )
    {
      reasonPhrase = "Successful";
    }
    else if( code < 400 )
    {
      reasonPhrase = "Redirection";
    }
    else if( code < 500 )
    {
      reasonPhrase = "Client Error";
    }
    else if( code < 600 )
    {
      reasonPhrase = "Server Error";
    }
    else
    {
      reasonPhrase = "Unknown Status";
    }

    return new StsResponseStatus( code, reasonPhrase + " (" + code + ')' );
  }

  private final int code;

  private final String reasonPhrase;
  private final byte[] bytes;

  /**
   * Creates a new instance with the specified {@code code} and its
   * {@code reasonPhrase}.
   */
  public StsResponseStatus( int code, String reasonPhrase )
  {
    this( code, reasonPhrase, false );
  }

  private StsResponseStatus( int code, String reasonPhrase, boolean bytes )
  {
    if( code < 0 )
    {
      throw new IllegalArgumentException( "code: " + code + " (expected: 0+)" );
    }

    if( reasonPhrase == null )
    {
      throw new NullPointerException( "reasonPhrase" );
    }

    for( int i = 0; i < reasonPhrase.length(); i++ )
    {
      char c = reasonPhrase.charAt( i );
      // Check prohibited characters.
      switch( c )
      {
        case '\n':
        case '\r':
          throw new IllegalArgumentException( "reasonPhrase contains one of the following prohibited characters: " +
                                                      "\\r\\n: " + reasonPhrase );
      }
    }

    this.code = code;
    this.reasonPhrase = reasonPhrase;
    if( bytes )
    {
      this.bytes = (code + " " + reasonPhrase).getBytes( CharsetUtil.US_ASCII );
    }
    else
    {
      this.bytes = null;
    }
  }

  /**
   * Returns the code of this getStatus.
   */
  public int code()
  {
    return code;
  }

  /**
   * Returns the reason phrase of this getStatus.
   */
  public String reasonPhrase()
  {
    return reasonPhrase;
  }

  @Override
  public int hashCode()
  {
    return code();
  }

  @Override
  public boolean equals( Object o )
  {
    if( !(o instanceof StsResponseStatus) )
    {
      return false;
    }

    return code() == ((StsResponseStatus) o).code();
  }

  @Override
  public int compareTo( StsResponseStatus o )
  {
    return code() - o.code();
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder( reasonPhrase.length() + 5 );
    buf.append( code );
    buf.append( ' ' );
    buf.append( reasonPhrase );
    return buf.toString();
  }

  void encode( ByteBuf buf )
  {
    if( bytes == null )
    {
      StsHeaders.encodeAscii0( String.valueOf( code() ), buf );
      buf.writeByte( SP );
      StsHeaders.encodeAscii0( String.valueOf( reasonPhrase() ), buf );
    }
    else
    {
      buf.writeBytes( bytes );
    }
  }
}
