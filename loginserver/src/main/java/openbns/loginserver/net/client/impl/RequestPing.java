package openbns.loginserver.net.client.impl;

import openbns.commons.net.codec.sts.DefaultFullStsResponse;
import openbns.commons.net.codec.sts.StsResponseStatus;
import openbns.loginserver.net.client.AbstractRequestPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:38
 */
public class RequestPing extends AbstractRequestPacket
{
  @Override
  public void read()
  {

  }

  @Override
  public void execute()
  {
    channel.write( new DefaultFullStsResponse( StsResponseStatus.OK ) );
  }
}
