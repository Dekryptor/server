package openbns.loginserver.net;

import openbns.loginserver.crypt.HashHelper;
import openbns.loginserver.crypt.KeyManager;
import openbns.loginserver.model.Account;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static openbns.commons.util.CryptUtil.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 16:29
 */
public class Session
{
  private static final KeyManager keyManager = KeyManager.getInstance();
  private static final SecureRandom rnd = new SecureRandom();

  private BigInteger privateKey;
  private BigInteger exchangeKey;

  private BigInteger serverExchangeKey;
  private BigInteger clientExchangeKey;

  private BigInteger sessionKey;
  private int sessionId;

  private Account account;

  public void init()
  {
    try
    {
      privateKey = keyManager.generatePrivateKey();
      exchangeKey = keyManager.generateExchangeKey( privateKey );
      sessionKey = new BigInteger( hexToString( rnd.generateSeed( 8 ) ), 16 );
    }
    catch( NoSuchAlgorithmException e )
    {
      e.printStackTrace();
    }
  }

  public BigInteger generateServerExchangeKey() throws NoSuchAlgorithmException, IOException
  {
    byte[] passwordHash = account.getPassword();

    BigInteger spKey = keyManager.generateAIIKey( bigIntegerToByteArray( sessionKey ), passwordHash );
    BigInteger two = new BigInteger( "2" );
    BigInteger decKey = two.modPow( spKey, KeyManager.N );
    decKey = decKey.multiply( KeyManager.P ).mod( KeyManager.N );
    serverExchangeKey = exchangeKey.add( decKey ).mod( KeyManager.N );
    return serverExchangeKey;
  }

  public byte[][] generateServerKey( byte[] array ) throws NoSuchAlgorithmException, IOException
  {
    byte[] userNameHash = HashHelper.loginHash( account.getLogin() );
    byte[] passwordHash = account.getPassword();

    BigInteger hash1 = keyManager.generateAIIKey( array, bigIntegerToByteArray( serverExchangeKey ) );
    BigInteger hash2 = keyManager.generateAIIKey( bigIntegerToByteArray( sessionKey ), passwordHash );

    BigInteger v27 = new BigInteger( hexToString( array ), 16 );
    BigInteger v21 = exchangeKey.modPow( hash1.multiply( hash2 ), KeyManager.N ).multiply( v27.modPow( privateKey, KeyManager.N ) ).mod( KeyManager.N );

    byte[] rootKey = keyManager.generateEncryptionKeyRoot( bigIntegerToByteArray( v21 ) );

    byte[] calcHash1 = sha256bytes( mergeArrays( KeyManager.STATIC_KEY, userNameHash, bigIntegerToByteArray( sessionKey ), array, bigIntegerToByteArray( serverExchangeKey ), rootKey ) );
    byte[] calcHash2 = sha256bytes( mergeArrays( array, calcHash1, rootKey ) );
//    byte[] key256 = keyManager.generate256BytesKey( rootKey );

    return new byte[][] { calcHash1, calcHash2 };
  }

  public void setAccount( Account account )
  {
    this.account = account;
  }

  public BigInteger getPrivateKey()
  {
    return privateKey;
  }

  public BigInteger getExchangeKey()
  {
    return exchangeKey;
  }

  public BigInteger getServerExchangeKey()
  {
    return serverExchangeKey;
  }

  public BigInteger getSessionKey()
  {
    return sessionKey;
  }

  public int getSessionId()
  {
    return sessionId;
  }

  public void setSessionId( int sessionId )
  {
    this.sessionId = sessionId;
  }

  public Account getAccount()
  {
    return account;
  }

  @Override
  public String toString()
  {
    return "Session{" +
            "privateKey=" + privateKey +
            ", exchangeKey=" + exchangeKey +
            ", sessionKey=" + sessionKey +
            ", sessionId=" + sessionId +
            '}';
  }
}
