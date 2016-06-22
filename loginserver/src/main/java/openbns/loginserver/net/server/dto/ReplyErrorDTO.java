package openbns.loginserver.net.server.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 20.01.14
 * Time: 1:14
 */
@XStreamAlias("Error")
public class ReplyErrorDTO implements IResponseDTO
{
  @XStreamAsAttribute
  private int code;

  @XStreamAsAttribute
  private int server;

  @XStreamAsAttribute
  private int module;

  @XStreamAsAttribute
  private int line;

  public int getCode()
  {
    return code;
  }

  public void setCode( int code )
  {
    this.code = code;
  }

  public int getServer()
  {
    return server;
  }

  public void setServer( int server )
  {
    this.server = server;
  }

  public int getModule()
  {
    return module;
  }

  public void setModule( int module )
  {
    this.module = module;
  }

  public int getLine()
  {
    return line;
  }

  public void setLine( int line )
  {
    this.line = line;
  }
}

