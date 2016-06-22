package openbns.loginserver.net.client.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 23:01
 */
@XStreamAlias("Request")
public class LoginStartDTO
{
  @XStreamAlias("LoginName")
  private String loginName;

  public String getLoginName()
  {
    return loginName;
  }

  public void setLoginName( String loginName )
  {
    this.loginName = loginName;
  }

  @Override
  public String toString()
  {
    return "LoginStartDTO{" +
            "loginName='" + loginName + '\'' +
            '}';
  }
}
