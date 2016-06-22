package openbns.commons.util;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 20:12
 */
public class UUIDHelper
{
  public static String uuid()
  {
    return UUID.randomUUID().toString().toUpperCase();
  }
}
