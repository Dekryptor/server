package openbns.loginserver.net.server.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 09.02.14
 * Time: 12:39
 */
@XStreamAlias( "Message" )
public class ReplyUserInfoDTO
{
  @XStreamAlias( "UserId" )
  private String userId;

  @XStreamAlias("UserCenter")
  private int userCenter;

  @XStreamAlias("UserName")
  private String userName;

  @XStreamAlias("Status")
  private String status;


}
