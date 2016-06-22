package openbns.loginserver.net.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import openbns.loginserver.net.LoginServerHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 22:03
 */
public abstract class AbstractRequestPacket
{
  protected ByteBuf buf;
  protected Channel channel;
  protected LoginServerHandler handler;

  public abstract void read();

  public abstract void execute();

  public ByteBuf getBuf()
  {
    return buf;
  }

  public void setBuf( ByteBuf buf )
  {
    this.buf = buf;
  }

  public Channel getChannel()
  {
    return channel;
  }

  public void setChannel( Channel channel )
  {
    this.channel = channel;
  }

  public void setHandler( LoginServerHandler handler )
  {
    this.handler = handler;
  }

  public LoginServerHandler getHandler()
  {
    return handler;
  }
}
