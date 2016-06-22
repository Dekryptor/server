package openbns.loginserver.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 09.02.14
 * Time: 12:43
 */
public class GameAccount
{
  @XStreamAlias( "Alias" )
  private String uuid;

  @XStreamAlias( "Created" )
  private Date created;
}
