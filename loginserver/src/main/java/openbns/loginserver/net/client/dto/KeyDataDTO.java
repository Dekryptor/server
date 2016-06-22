package openbns.loginserver.net.client.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 23:03
 */
@XStreamAlias("Request")
public class KeyDataDTO
{
  @XStreamAlias("KeyData")
  private String keyData;

  public String getKeyData()
  {
    return keyData;
  }

  public void setKeyData( String keyData )
  {
    this.keyData = keyData;
  }

  @Override
  public String toString()
  {
    return "KeyDataDTO{" +
            "keyData='" + keyData + '\'' +
            '}';
  }
}
