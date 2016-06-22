package openbns.commons.net.codec.sts;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The version of HTTP or its derived protocols, such as
 * <a href="sts://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
 * <a href="sts://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>.
 */
public class StsVersion implements Comparable<StsVersion>
{
  private static final Pattern VERSION_PATTERN = Pattern.compile( "(\\S+)/(\\d+)\\.(\\d+)" );
  private static final String STS_1_0_STRING = "STS/1.0";

  public static final StsVersion STS_1_0 = new StsVersion( "STS", 1, 0, true );

  public static StsVersion valueOf( String text )
  {
    if( text == null )
    {
      throw new NullPointerException( "text" );
    }

    text = text.trim();

    if( text.isEmpty() )
    {
      throw new IllegalArgumentException( "text is empty" );
    }

    StsVersion version = version0( text );
    if( version == null )
    {
      text = text.toUpperCase();
      // try again after convert to uppercase
      version = version0( text );
      if( version == null )
      {
        // still no match, construct a new one
        version = new StsVersion( text );
      }
    }
    return version;
  }

  private static StsVersion version0( String text )
  {
    if( STS_1_0_STRING.equals( text ) )
    {
      return STS_1_0;
    }
    return null;
  }

  private final String protocolName;
  private final int majorVersion;
  private final int minorVersion;
  private final String text;
  private final byte[] bytes;

  public StsVersion( String text )
  {
    if( text == null )
    {
      throw new NullPointerException( "text" );
    }

    text = text.trim().toUpperCase();
    if( text.isEmpty() )
    {
      throw new IllegalArgumentException( "empty text" );
    }

    Matcher m = VERSION_PATTERN.matcher( text );
    if( !m.matches() )
    {
      throw new IllegalArgumentException( "invalid version format: " + text );
    }

    protocolName = m.group( 1 );
    majorVersion = Integer.parseInt( m.group( 2 ) );
    minorVersion = Integer.parseInt( m.group( 3 ) );
    this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
    bytes = null;
  }

  public StsVersion( String protocolName, int majorVersion, int minorVersion )
  {
    this( protocolName, majorVersion, minorVersion, false );
  }

  private StsVersion( String protocolName, int majorVersion, int minorVersion, boolean bytes )
  {
    if( protocolName == null )
    {
      throw new NullPointerException( "protocolName" );
    }

    protocolName = protocolName.trim().toUpperCase();
    if( protocolName.isEmpty() )
    {
      throw new IllegalArgumentException( "empty protocolName" );
    }

    for( int i = 0; i < protocolName.length(); i++ )
    {
      if( Character.isISOControl( protocolName.charAt( i ) ) || Character.isWhitespace( protocolName.charAt( i ) ) )
      {
        throw new IllegalArgumentException( "invalid character in protocolName" );
      }
    }

    if( majorVersion < 0 )
    {
      throw new IllegalArgumentException( "negative majorVersion" );
    }
    if( minorVersion < 0 )
    {
      throw new IllegalArgumentException( "negative minorVersion" );
    }

    this.protocolName = protocolName;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    text = protocolName + '/' + majorVersion + '.' + minorVersion;

    if( bytes )
    {
      this.bytes = text.getBytes( CharsetUtil.US_ASCII );
    }
    else
    {
      this.bytes = null;
    }
  }

  public String protocolName()
  {
    return protocolName;
  }

  public int majorVersion()
  {
    return majorVersion;
  }

  public int minorVersion()
  {
    return minorVersion;
  }

  public String text()
  {
    return text;
  }

  @Override
  public String toString()
  {
    return text();
  }

  @Override
  public int hashCode()
  {
    return (protocolName().hashCode() * 31 + majorVersion()) * 31 + minorVersion();
  }

  @Override
  public boolean equals( Object o )
  {
    if( !(o instanceof StsVersion) )
    {
      return false;
    }

    StsVersion that = (StsVersion) o;
    return minorVersion() == that.minorVersion() &&
            majorVersion() == that.majorVersion() &&
            protocolName().equals( that.protocolName() );
  }

  @Override
  public int compareTo( StsVersion o )
  {
    int v = protocolName().compareTo( o.protocolName() );
    if( v != 0 )
    {
      return v;
    }

    v = majorVersion() - o.majorVersion();
    if( v != 0 )
    {
      return v;
    }

    return minorVersion() - o.minorVersion();
  }

  void encode( ByteBuf buf )
  {
    if( bytes == null )
    {
      StsHeaders.encodeAscii0( text, buf );
    }
    else
    {
      buf.writeBytes( bytes );
    }
  }
}
