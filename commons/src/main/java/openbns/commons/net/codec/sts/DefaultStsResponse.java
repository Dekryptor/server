package openbns.commons.net.codec.sts;

import io.netty.util.internal.StringUtil;

/**
 * The default {@link StsResponse} implementation.
 */
public class DefaultStsResponse extends DefaultStsMessage implements StsResponse
{

  private StsResponseStatus status;

  /**
   * Creates a new instance.
   *
   * @param status  the getStatus of this response
   */
  public DefaultStsResponse( StsResponseStatus status )
  {
    this( status, true );
  }

  /**
   * Creates a new instance.
   *
   * @param status          the getStatus of this response
   * @param validateHeaders validate the headers when adding them
   */
  public DefaultStsResponse( StsResponseStatus status, boolean validateHeaders )
  {
    super( validateHeaders );
    if( status == null )
    {
      throw new NullPointerException( "status" );
    }
    this.status = status;
  }

  @Override
  public StsResponseStatus getStatus()
  {
    return status;
  }

  @Override
  public StsResponse setStatus( StsResponseStatus status )
  {
    if( status == null )
    {
      throw new NullPointerException( "status" );
    }
    this.status = status;
    return this;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append( StringUtil.simpleClassName( this ) );
    buf.append( "(decodeResult: " );
    buf.append( getDecoderResult() );
    buf.append( ')' );
    buf.append( StringUtil.NEWLINE );
    buf.append( getProtocolVersion().text() );
    buf.append( ' ' );
    buf.append( getStatus().toString() );
    buf.append( StringUtil.NEWLINE );
    appendHeaders( buf );

    // Remove the last newline.
    buf.setLength( buf.length() - StringUtil.NEWLINE.length() );
    return buf.toString();
  }
}
