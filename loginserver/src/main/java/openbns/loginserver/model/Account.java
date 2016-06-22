package openbns.loginserver.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 25.01.14
 * Time: 15:15
 */
public class Account
{
  private String uuid;
  private String login;
  private byte[] password;
  private AccessLevel accessLevel;
  private Date lastLogin;
  private String lastIp;
  private int lastServerId;

  public String getUuid()
  {
    return uuid;
  }

  public void setUuid( String uuid )
  {
    this.uuid = uuid;
  }

  public String getLogin()
  {
    return login;
  }

  public void setLogin( String login )
  {
    this.login = login;
  }

  public byte[] getPassword()
  {
    return password;
  }

  public void setPassword( byte[] password )
  {
    this.password = password;
  }

  public AccessLevel getAccessLevel()
  {
    return accessLevel;
  }

  public void setAccessLevel( AccessLevel accessLevel )
  {
    this.accessLevel = accessLevel;
  }

  public Date getLastLogin()
  {
    return lastLogin;
  }

  public void setLastLogin( Date lastLogin )
  {
    this.lastLogin = lastLogin;
  }

  public String getLastIp()
  {
    return lastIp;
  }

  public void setLastIp( String lastIp )
  {
    this.lastIp = lastIp;
  }

  public int getLastServerId()
  {
    return lastServerId;
  }

  public void setLastServerId( int lastServerId )
  {
    this.lastServerId = lastServerId;
  }

  @Override
  public boolean equals( Object o )
  {
    if( this == o )
    {
      return true;
    }
    if( o == null || getClass() != o.getClass() )
    {
      return false;
    }

    Account account = (Account) o;

    return !(uuid != null ? !uuid.equals( account.uuid ) : account.uuid != null) && !(login != null ? !login.equals( account.login ) : account.login != null);
  }

  @Override
  public int hashCode()
  {
    int result = uuid != null ? uuid.hashCode() : 0;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    return "Account{" +
            "uuid='" + uuid + '\'' +
            ", login='" + login + '\'' +
            ", lastLogin=" + lastLogin +
            ", accessLevel=" + accessLevel +
            ", lastIp='" + lastIp + '\'' +
            ", lastServerId=" + lastServerId +
            '}';
  }
}
