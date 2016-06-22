package openbns;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 25.01.14
 * Time: 11:49
 */
public class DataBaseFactory
{
  private static final Log log = LogFactory.getLog( DataBaseFactory.class );
  private static DataBaseFactory ourInstance = new DataBaseFactory();
  private DataSource dataSource;

  public static DataBaseFactory getInstance()
  {
    return ourInstance;
  }

  private DataBaseFactory()
  {
    try (InputStream is = DataBaseFactory.class.getResourceAsStream( "/DataSource.properties" ))
    {
      Properties properties = new Properties();
      properties.load( is );
      dataSource = BasicDataSourceFactory.createDataSource( properties );
      log.info( "DataSource initialized successfully" );
    }
    catch( Exception e )
    {
      log.error( "DataSource initializing failed", e );
      System.exit( 1 );
    }
  }

  public Connection getConnection() throws SQLException
  {
    return dataSource.getConnection();
  }
}
