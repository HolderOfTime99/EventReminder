package api;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLInterpreter {
    private Document doc;

    public XMLInterpreter(String xml) throws Exception {
        this.doc = loadXMLFromString(xml);
    }

    public Event[] getEvents(){
        Event[] ret;
        NodeList events = doc.getElementsByTagName("event");
        ret = new Event[events.getLength()];
        for (int i =0; i < events.getLength(); i++){
            Node cur = events.item(i);
            if (cur.getNodeType() == Node.ELEMENT_NODE){
                Element event = (Element) cur;
                ret[i] = new Event(getField(event, "title"),
                        getField(event, "venue_name"),
                        getField(event, "venue_address"),
                        getField(event, "description"),
                        getField(event, "start_time"),
                        getField(event, "stop_time"),
                        getField(event, "url"));
            }
        }

        return ret;
    }

    private String getField(Element e, String field){
        return e.getElementsByTagName(field)
                .item(0)
                .getTextContent();
    }



    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

}
