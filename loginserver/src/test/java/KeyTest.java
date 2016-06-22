import openbns.commons.util.CryptUtil;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 01.02.14
 * Time: 19:54
 */
public class KeyTest
{
  public static void main( String[] args ) throws IOException
  {
    byte[] res = CryptUtil.base64( "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg" );

    DataInputStream dataInputStream = new DataInputStream( new ByteArrayInputStream( res ) );

    int s = Integer.reverseBytes( dataInputStream.readInt() );

    byte[] bb = new byte[ s ];

    dataInputStream.read( bb );

    s = Integer.reverseBytes( dataInputStream.readInt() );

    bb = new byte[ s ];

    dataInputStream.read( bb );
  }
}
