package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import openbns.commons.net.codec.sts.DefaultFullStsResponse;
import openbns.commons.net.codec.sts.StsResponseStatus;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.ConnectDTO;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:09
 */
public class RequestConnect extends AbstractRequestPacket
{
  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( ConnectDTO.class );

    ConnectDTO connectDTO = (ConnectDTO) stream.fromXML( new ByteBufInputStream( buf ) );
  }

  @Override
  public void execute()
  {
    channel.write( new DefaultFullStsResponse( StsResponseStatus.OK ) );
  }
}
