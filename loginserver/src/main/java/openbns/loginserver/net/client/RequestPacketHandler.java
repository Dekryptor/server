package openbns.loginserver.net.client;

import openbns.loginserver.net.client.impl.RequestConnect;
import openbns.loginserver.net.client.impl.RequestKeyData;
import openbns.loginserver.net.client.impl.RequestLoginStart;
import openbns.loginserver.net.client.impl.RequestPing;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 22:54
 */
public class RequestPacketHandler
{
  private static RequestPacketHandler ourInstance = new RequestPacketHandler();

  public static RequestPacketHandler getInstance()
  {
    return ourInstance;
  }

  private RequestPacketHandler()
  {
  }

  public AbstractRequestPacket getPacket( String url ) throws IOException
  {
    switch( url )
    {
      case "/Sts/Connect":
        return new RequestConnect();
      case "/Sts/Ping":
        return new RequestPing();
      case "/Auth/LoginStart":
        return new RequestLoginStart();
      case "/Auth/KeyData":
        return new RequestKeyData();
      case "/Auth/LoginFinish":
      case "/Auth/RequestToken":
      case "/Auth/RequestGameToken":
      case "/Auth/GetMyUserInfo":
      case "/GameAccount/ListMyAccounts":
      case "/World/ListWorlds":
      case "/Slot/ListCharSlots":
      case "/Slot/GetCharSlot":
      case "/Slot/ListSlots":
      case "/Game.bns/CreatePC":
      case "/Game.bns/DeletePC":
      case "/SecondPassword/GetStatus":
      case "/Grade.bns/GetGameGrade":
      case "/Friend/GetUserInfo":
      case "/VirtualCurrency/GetBalance":
      case "/Friend/PageRecvProposals":
      default:
        throw new IOException( "No handler available for request " + url );
    }
  }
}
