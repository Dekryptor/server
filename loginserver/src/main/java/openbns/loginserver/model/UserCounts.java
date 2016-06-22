package openbns.loginserver.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 09.02.14
 * Time: 12:47
 */
public class UserCounts
{
  @XStreamAlias( "PlayingUsers" )
  private int playing;

  @XStreamAlias( "WaitingUsers" )
  private int waiting;

  @XStreamAlias( "MaxUsers" )
  private int maximum;
}
