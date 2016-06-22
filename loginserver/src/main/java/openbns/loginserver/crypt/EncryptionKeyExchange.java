package openbns.loginserver.crypt;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Luna9966
 * Date: 30.01.14
 * Time: 18:55
 */
public abstract class EncryptionKeyExchange
{
  public static BigInteger Module = new BigInteger( "f488fd584e49dbcd20b49de49107366b336c380d451d0f7c88b31c7c5b2d8ef6f3c923c043f0a55b188d8ebb558cb85d38d334fd7c175743a31d186cde33212cb52aff3ce1b1294018118d7c84a70a72d686c40319c807297aca950cd9969fabd00a509b0246d3083d66a45d419f9c7cbd894b221926baaba25ec355e92f78c7", 16 );

  protected byte[] key;

  public abstract EncryptionKeyExchange CreateNewInstance();

  public abstract void MakePrivateKey();

//  public abstract byte[] GetKeyExchangeBytes(Mode mode);
//
//  public abstract void MakeKey(Mode mode, byte[] keyExchangeBytes);

  public byte[] getKey()
  {
    return key;
  }

  public void setKey( byte[] key )
  {
    this.key = key;
  }
}
