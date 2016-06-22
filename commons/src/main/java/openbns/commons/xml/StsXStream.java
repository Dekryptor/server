package openbns.commons.xml;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 19.01.14
 * Time: 20:37
 */
public class StsXStream extends XStream
{
  public StsXStream()
  {
    super( null, null, new StsXppDriver() );
  }
}
