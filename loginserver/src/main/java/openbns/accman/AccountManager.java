package openbns.accman;

import openbns.DataBaseFactory;
import openbns.loginserver.Config;
import openbns.loginserver.model.Account;
import openbns.loginserver.service.AccountRegistration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 19:47
 */
public class AccountManager
{
  private static final AccountRegistration registrationService = AccountRegistration.getInstance();

  public static void main( String[] args ) throws IOException, NoSuchAlgorithmException
  {
    Config.load();
    DataBaseFactory.getInstance();

    System.out.println( "Select operation:" );
    System.out.println( "1. Create account" );
    System.out.println( "0. Exit" );

    Scanner scanner = new Scanner( System.in );

    int op = scanner.nextInt();

    switch( op )
    {
      case 1:
        createMenu( scanner );
        break;
      case 0:
        System.exit( 0 );
        break;
    }
  }

  private static void createMenu( Scanner scanner ) throws NoSuchAlgorithmException
  {
    System.out.println( "Enter login (not email)" );
    scanner.nextLine();
    String login = scanner.nextLine();

    System.out.println( "Enter password" );
    String password = scanner.nextLine();

    Account account = registrationService.createAccount( login, password );

    System.out.println( "Congratulation! Account created!" );
    System.out.println( account );
  }
}
