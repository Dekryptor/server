package openbns.loginserver.net.server;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import openbns.loginserver.model.GameAccount;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 09.02.14
 * Time: 12:44
 */
@XStreamAlias( "Reply" )
public class ReplyAccountListDTO
{
  @XStreamAsAttribute
  private String type = "array";

//  private Collection<GameAccount> accounts;

  @XStreamAlias( "GameAccount" )
  private GameAccount gameAccount;
}


