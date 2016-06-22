package openbns.commons.util;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 01.02.14
 * Time: 22:45
 */
public class CryptUtil
{
  private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

  public static String hexToString( byte[] bytes )
  {
    char[] hexChars = new char[ bytes.length * 2 ];
    for( int j = 0; j < bytes.length; j++ )
    {
      int v = bytes[ j ] & 0xFF;
      hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
      hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
    }
    return new String( hexChars );
  }

  public static byte[] bigIntegerToByteArray( BigInteger i )
  {
    byte[] array = i.toByteArray();
    if( array[ 0 ] == 0 )
    {
      byte[] tmp = new byte[ array.length - 1 ];
      System.arraycopy( array, 1, tmp, 0, tmp.length );
      array = tmp;
    }
    return array;
  }

  public static String sha256( byte[] data ) throws NoSuchAlgorithmException
  {
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( data );
    return CryptUtil.hexToString( digest.digest() );
  }

  public static byte[] sha256bytes( byte[] data ) throws NoSuchAlgorithmException
  {
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( data );
    return digest.digest();
  }

  public static String base64( byte[] data )
  {
    return DatatypeConverter.printBase64Binary( data );
  }

  public static byte[] base64( String data )
  {
    return DatatypeConverter.parseBase64Binary( data );
  }

  public static byte[] mergeArrays( byte[]... arrays )
  {
    int size = 0;

    for( byte[] a : arrays )
      size += a.length;

    byte[] result = new byte[ size ];

    int lastPos = 0;
    for( byte[] a : arrays )
    {
      System.arraycopy( a, 0, result, lastPos, a.length );
      lastPos += a.length;
    }
    return result;
  }
}
