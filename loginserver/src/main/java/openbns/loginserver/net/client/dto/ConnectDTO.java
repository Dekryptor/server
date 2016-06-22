package openbns.loginserver.net.client.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 22:48
 */
@XStreamAlias("Connect")
public class ConnectDTO
{
  @XStreamAlias("ConnType")
  private int connType;

  @XStreamAlias("Address")
  private String address;

  @XStreamAlias("ProductType")
  private int productType;

  @XStreamAlias("AppIndex")
  private int appIndex;

  @XStreamAlias("Epoch")
  private long epoch;

  @XStreamAlias("Program")
  private int program;

  @XStreamAlias("Build")
  private int build;

  @XStreamAlias("Process")
  private int process;

  public int getConnType()
  {
    return connType;
  }

  public void setConnType( int connType )
  {
    this.connType = connType;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress( String address )
  {
    this.address = address;
  }

  public int getProductType()
  {
    return productType;
  }

  public void setProductType( int productType )
  {
    this.productType = productType;
  }

  public int getAppIndex()
  {
    return appIndex;
  }

  public void setAppIndex( int appIndex )
  {
    this.appIndex = appIndex;
  }

  public long getEpoch()
  {
    return epoch;
  }

  public void setEpoch( long epoch )
  {
    this.epoch = epoch;
  }

  public int getProgram()
  {
    return program;
  }

  public void setProgram( int program )
  {
    this.program = program;
  }

  public int getBuild()
  {
    return build;
  }

  public void setBuild( int build )
  {
    this.build = build;
  }

  public int getProcess()
  {
    return process;
  }

  public void setProcess( int process )
  {
    this.process = process;
  }

  @Override
  public String toString()
  {
    return "ConnectDTO{" +
            "connType=" + connType +
            ", address='" + address + '\'' +
            ", productType=" + productType +
            ", appIndex=" + appIndex +
            ", epoch=" + epoch +
            ", program=" + program +
            ", build=" + build +
            ", process=" + process +
            '}';
  }
}
