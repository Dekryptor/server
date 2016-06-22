package openbns.loginserver.dao;

import openbns.DataBaseFactory;
import openbns.commons.db.DbUtils;
import openbns.loginserver.model.AccessLevel;
import openbns.loginserver.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mariadb.jdbc.MySQLBlob;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 25.01.14
 * Time: 15:16
 */
public class AccountDAO
{
  private static final Log log = LogFactory.getLog( AccountDAO.class );
  private static final String INSERT_USER = "INSERT INTO accounts VALUES (?,?,?,?,?,?,?)";
  private static final String SELECT_BY_LOGIN = "SELECT * FROM accounts WHERE login = ?";

  private static AccountDAO ourInstance = new AccountDAO();

  public static AccountDAO getInstance()
  {
    return ourInstance;
  }

  private AccountDAO()
  {
  }

  public Account insert( Account account )
  {
    Connection con = null;
    PreparedStatement statement = null;

    try
    {
      con = DataBaseFactory.getInstance().getConnection();
      statement = con.prepareStatement( INSERT_USER );

      statement.setString( 1, account.getUuid() );
      statement.setString( 2, account.getLogin() );
      statement.setBlob( 3, new MySQLBlob( account.getPassword() ) );
      statement.setInt( 4, account.getAccessLevel().ordinal() );
      statement.setTimestamp( 5, new Timestamp( account.getLastLogin().getTime() ) );
      statement.setString( 6, account.getLastIp() );
      statement.setInt( 7, account.getLastServerId() );

      statement.execute();
    }
    catch( SQLException e )
    {
      log.error( "Error inserting " + account, e );
    }
    finally
    {
      DbUtils.closeQuietly( con, statement );
    }
    return account;
  }

  public Account getByLogin( String login )
  {
    login = login.split( "@" )[ 0 ];
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;

    try
    {
      con = DataBaseFactory.getInstance().getConnection();
      stmt = con.prepareStatement( SELECT_BY_LOGIN );
      stmt.setString( 1, login );
      rset = stmt.executeQuery();
      if( rset.next() )
      {
        Account account = new Account();
        account.setUuid( rset.getString( 1 ) );
        account.setLogin( rset.getString( 2 ) );
        account.setPassword( rset.getBytes( 3 ) );
        account.setAccessLevel( AccessLevel.values()[ rset.getInt( 4 ) ] );
        account.setLastLogin( rset.getTimestamp( 5 ) );
        account.setLastIp( rset.getString( 6 ) );
        account.setLastIp( rset.getString( 7 ) );
        return account;
      }
    }
    catch( SQLException e )
    {
      log.error( "Error getting account by login " + login, e );
    }
    finally
    {
      DbUtils.closeQuietly( con, stmt, rset );
    }

    throw new NullPointerException( "Account with login '" + login + "' not found" );
  }
}
