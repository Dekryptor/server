package openbns.loginserver.service;

import openbns.commons.util.UUIDHelper;
import openbns.loginserver.crypt.HashHelper;
import openbns.loginserver.dao.AccountDAO;
import openbns.loginserver.model.AccessLevel;
import openbns.loginserver.model.Account;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 19:53
 */
public class AccountRegistration
{
  private static final AccountDAO dao = AccountDAO.getInstance();
  private static AccountRegistration ourInstance = new AccountRegistration();

  public static AccountRegistration getInstance()
  {
    return ourInstance;
  }

  private AccountRegistration()
  {
  }

  public Account createAccount( String login, String password ) throws NoSuchAlgorithmException
  {
    Account account = new Account();
    account.setUuid( UUIDHelper.uuid() );
    account.setLogin( login );
    account.setPassword( HashHelper.passwordHash( login, password ) );
    account.setAccessLevel( AccessLevel.NORMAL );
    account.setLastLogin( new Date() );
    account.setLastIp( "127.0.0.1" );
    account.setLastServerId( 0 );
    return dao.insert( account );
  }
}
