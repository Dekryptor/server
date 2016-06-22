package openbns.loginserver.service;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 01.02.14
 * Time: 11:25
 */
public class AccountLogging
{
  private static AccountLogging ourInstance = new AccountLogging();

  public static AccountLogging getInstance()
  {
    return ourInstance;
  }

  private AccountLogging()
  {
  }
}
