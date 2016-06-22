package openbns.commons.xml;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 19.01.14
 * Time: 20:40
 */
public class StsXppDriver extends XppDriver
{
  public HierarchicalStreamWriter createWriter( Writer out )
  {
    return new StsPrintWriter( out, PrettyPrintWriter.XML_QUIRKS, new char[] { }, getNameCoder() );
  }
}
