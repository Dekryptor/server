package openbns.commons.net.codec.sts;

import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * The default {@link StsMessage} implementation.
 */
public abstract class DefaultStsMessage extends DefaultStsObject implements StsMessage
{
  private final StsVersion version = StsVersion.STS_1_0;
  private final StsHeaders headers;

  protected DefaultStsMessage( boolean validate )
  {
    headers = new DefaultStsHeaders( validate );
  }

  @Override
  public StsHeaders headers()
  {
    return headers;
  }

  @Override
  public StsVersion getProtocolVersion()
  {
    return version;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append( StringUtil.simpleClassName( this ) );
    buf.append( "(version: " );
    buf.append( getProtocolVersion().text() );
    buf.append( ')' );
    buf.append( StringUtil.NEWLINE );
    appendHeaders( buf );

    // Remove the last newline.
    buf.setLength( buf.length() - StringUtil.NEWLINE.length() );
    return buf.toString();
  }

  void appendHeaders( StringBuilder buf )
  {
    for( Map.Entry<String, String> e : headers() )
    {
      buf.append( e.getKey() );
      buf.append( ": " );
      buf.append( e.getValue() );
      buf.append( StringUtil.NEWLINE );
    }
  }
}
