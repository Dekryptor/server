package openbns.loginserver.crypt;

import openbns.loginserver.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 19:41
 */
public class HashHelper
{
  public static byte[] loginHash( String login ) throws NoSuchAlgorithmException
  {
    login = login + Config.LOGIN_POSTFIX;
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( login.getBytes() );
    return digest.digest();
  }

  public static byte[] passwordHash( String login, String password ) throws NoSuchAlgorithmException
  {
    String full_name = String.format( "%s%s:%s", login, Config.LOGIN_POSTFIX, password );
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( full_name.getBytes() );
    return digest.digest();
  }
}
