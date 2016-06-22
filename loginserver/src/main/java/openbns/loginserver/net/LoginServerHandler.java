package openbns.loginserver.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.codec.sts.DefaultStsRequest;
import openbns.commons.net.codec.sts.LastStsContent;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.RequestPacketHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 16.01.14
 * Time: 21:03
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter
{
  private static final Log log = LogFactory.getLog( LoginServerHandler.class );
  private static final RequestPacketHandler packetHandler = RequestPacketHandler.getInstance();

  private String lastURI;
  private Session session;

  // TODO: REFACTOR ALL. ITS ONLY FOR TESTING
  @Override
  public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception
  {
    if( msg instanceof DefaultStsRequest )
    {
      DefaultStsRequest req = (DefaultStsRequest) msg;
      lastURI = req.getUri();
      log.info( "Receive request from client. Method: " + req.getMethod() + "; URI: " + req.getUri() );

      String s = req.headers().get( "s" );
      if( s != null )
        session.setSessionId( Integer.parseInt( s ) );
    }
    else if( msg instanceof LastStsContent )
    {
      LastStsContent content = (LastStsContent) msg;
      AbstractRequestPacket packet = packetHandler.getPacket( lastURI );
      packet.setHandler( this );
      packet.setChannel( ctx.channel() );
      packet.setBuf( content.content() );
      packet.read();
      packet.execute();
    }
  }

  @Override
  public void channelReadComplete( ChannelHandlerContext ctx ) throws Exception
  {
    ctx.flush();
  }

  @Override
  public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception
  {
    log.error( "LoginServerHandler: Exception!!!", cause );
    ctx.close();
  }

  @Override
  public void channelRegistered( ChannelHandlerContext ctx ) throws Exception
  {
    log.debug( "Accepted new channel" );
    session = new Session();
    session.init();
    log.debug( "Generated session: " + session );
  }

  public Session getSession()
  {
    return session;
  }
}
