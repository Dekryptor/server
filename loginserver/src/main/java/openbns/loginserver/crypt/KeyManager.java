package openbns.loginserver.crypt;

import openbns.commons.util.CryptUtil;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 22.01.14
 * Time: 0:08
 */
public class KeyManager
{
  /**
   * Thanks to Luna9966
   */
  public static final BigInteger N = new BigInteger( "E306EBC02F1DC69F5B437683FE3851FD9AAA6E97F4CBD42FC06C72053CBCED68EC570E6666F529C58518CF7B299B5582495DB169ADF48ECEB6D65461B4D7C75DD1DA89601D5C498EE48BB950E2D8D5E0E0C692D613483B38D381EA9674DF74D67665259C4C31A29E0B3CFF7587617260E8C58FFA0AF8339CD68DB3ADB90AAFEE", 16 );
  public static final BigInteger P = new BigInteger( "7A39FF57BCBFAA521DCE9C7DEFAB520640AC493E1B6024B95A28390E8F05787D", 16 );
  public static final byte[] STATIC_KEY = { (byte) 0xAC, (byte) 0x34, (byte) 0xF3, (byte) 0x07, (byte) 0x0D, (byte) 0xC0, (byte) 0xE5, (byte) 0x23, (byte) 0x02, (byte) 0xC2, (byte) 0xE8, (byte) 0xDA, (byte) 0x0E, (byte) 0x3F, (byte) 0x7B, (byte) 0x3E, (byte) 0x63, (byte) 0x22, (byte) 0x36, (byte) 0x97, (byte) 0x55, (byte) 0x5D, (byte) 0xF5, (byte) 0x4E, (byte) 0x71, (byte) 0x22, (byte) 0xA1, (byte) 0x4D, (byte) 0xBC, (byte) 0x99, (byte) 0xA3, (byte) 0xE8 };

  private static KeyManager ourInstance = new KeyManager();

  public static KeyManager getInstance()
  {
    return ourInstance;
  }

  private KeyManager()
  {
  }

  public BigInteger generatePrivateKey() throws NoSuchAlgorithmException
  {
    long time = System.currentTimeMillis();
    long ticks = 621355968000000000L + time * 10000;
    String s_time = String.valueOf( ticks );
    byte[] b_time = s_time.getBytes();

    return new BigInteger( CryptUtil.sha256( b_time ), 16 );
  }

  public BigInteger generateExchangeKey( BigInteger privateKey )
  {
    BigInteger k = new BigInteger( "2" );
    return k.modPow( privateKey, N );
  }

  public BigInteger generateAIIKey( byte[] tmp1, byte[] tmp2 ) throws NoSuchAlgorithmException, IOException
  {
    byte[] sharedArray = new byte[ tmp1.length + tmp2.length ];

    System.arraycopy( tmp1, 0, sharedArray, 0, tmp1.length );
    System.arraycopy( tmp2, 0, sharedArray, tmp1.length, tmp2.length );

    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( sharedArray );

    byte[] hash = digest.digest();
    byte[] reversed = reverseIntegerArray( hash );
    String k = CryptUtil.hexToString( reversed );

    return new BigInteger( k, 16 );
  }

  private byte[] reverseIntegerArray( byte[] array ) throws IOException
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream( array.length );
    DataOutputStream dos = new DataOutputStream( bos );
    DataInputStream dis = new DataInputStream( new ByteArrayInputStream( array ) );

    while( dis.available() > 0 )
    {
      int i = dis.readInt();
      dos.writeInt( Integer.reverseBytes( i ) );
    }

    dis.close();
    dos.close();
    bos.close();

    return bos.toByteArray();
  }

  public byte[] generateEncryptionKeyRoot( byte[] src ) throws NoSuchAlgorithmException
  {
    int firstSize = src.length;
    int startIndex = 0;
    byte[] half;
    byte[] dst = new byte[ 64 ];
    if( src.length > 4 )
    {
      do
      {
        if( src[ startIndex ] == 0 )
          break;
        firstSize--;
        startIndex++;
      }
      while( firstSize > 4 );
    }
    int size = firstSize >> 1;
    half = new byte[ size ];
    if( size > 0 )
    {
      int index = startIndex + firstSize - 1;
      for( int i = 0; i < size; i++ )
      {
        half[ i ] = src[ index ];
        index -= 2;
      }
    }
    byte[] hash = CryptUtil.sha256bytes( Arrays.copyOfRange( half, 0, size ) );
    for( int i = 0; i < 32; i++ )
    {
      dst[ 2 * i ] = hash[ i ];
    }
    if( size > 0 )
    {
      int index = startIndex + firstSize - 2;
      for( int i = 0; i < size; i++ )
      {
        half[ i ] = src[ index ];
        index -= 2;
      }
    }
    hash = CryptUtil.sha256bytes( Arrays.copyOfRange( half, 0, size ) );
    for( int i = 0; i < 32; i++ )
    {
      dst[ 2 * i + 1 ] = hash[ i ];
    }
    return dst;
  }

  public byte[] generate256BytesKey( byte[] src )
  {
    int v7 = 1;
    byte[] res = new byte[ 256 ];
    for( int i = 0; i < 256; i++ )
      res[ i ] = (byte) i;
    int v6 = 0;
    int counter = 0;
    for( int i = 64; i > 0; i-- )
    {
      int v9 = (v6 + src[ counter ] + res[ v7 - 1 ]) & 0xFF;
      int v10 = res[ v7 - 1 ];
      res[ v7 - 1 ] = res[ v9 ];
      int v8 = counter + 1;
      res[ v9 ] = (byte) v10;
      if( v8 == src.length )
        v8 = 0;
      int v13 = v9 + src[ v8 ];
      int v11 = v8 + 1;
      int v14 = v13 + res[ v7 ];
      v13 = res[ v7 ];
      int v12 = (byte) v14;
      res[ v7 ] = res[ v12 ];
      res[ v12 ] = (byte) v13;
      if( v11 == src.length )
        v11 = 0;
      int v16 = (v12 + src[ v11 ] + res[ v7 + 1 ]) & 0xFF;
      int v17 = res[ v7 + 1 ];
      res[ v7 + 1 ] = res[ v16 ];
      int v15 = v11 + 1;
      res[ v16 ] = (byte) v17;
      if( v15 == src.length )
        v15 = 0;
      int v18 = v16 + src[ v15 ];
      int v19 = res[ v7 + 2 ];
      v6 = (v18 + res[ v7 + 2 ]) & 0xFF;
      counter = v15 + 1;
      res[ v7 + 2 ] = res[ v6 ];
      res[ v6 ] = (byte) v19;
      if( counter == src.length )
        counter = 0;
      v7 += 4;
    }
    return res;
  }
}
