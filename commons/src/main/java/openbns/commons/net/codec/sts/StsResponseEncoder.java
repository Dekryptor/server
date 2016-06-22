package openbns.commons.net.codec.sts;

import io.netty.buffer.ByteBuf;

import static io.netty.handler.codec.http.HttpConstants.*;

public class StsResponseEncoder extends HttpObjectEncoder<StsResponse>
{
  private static final byte[] CRLF = { CR, LF };

  @Override
  public boolean acceptOutboundMessage( Object msg ) throws Exception
  {
    return super.acceptOutboundMessage( msg ) && !(msg instanceof StsRequest);
  }

  @Override
  protected void encodeInitialLine( ByteBuf buf, StsResponse response ) throws Exception
  {
    response.getProtocolVersion().encode( buf );
    buf.writeByte( SP );
    response.getStatus().encode( buf );
    buf.writeBytes( CRLF );
  }
}
