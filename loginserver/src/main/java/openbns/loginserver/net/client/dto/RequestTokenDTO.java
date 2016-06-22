package openbns.loginserver.net.client.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 16:34
 */
@XStreamAlias("Request")
public class RequestTokenDTO
{
  @XStreamAlias("AppId")
  private String appId;

  public String getAppId()
  {
    return appId;
  }

  public void setAppId( String appId )
  {
    this.appId = appId;
  }

  @Override
  public String toString()
  {
    return "RequestTokenDTO{" +
            "appId='" + appId + '\'' +
            '}';
  }
}
