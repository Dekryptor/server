package openbns.loginserver.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 09.02.14
 * Time: 12:46
 */
public class World
{
  @XStreamAlias( "WorldCode" )
  private int id;

  @XStreamAlias( "WorldName" )
  private String name;

  @XStreamAlias( "PublicNetAddress" )
  private String netAddress;

  @XStreamAlias( "UserCounts" )
  private UserCounts users;
}
