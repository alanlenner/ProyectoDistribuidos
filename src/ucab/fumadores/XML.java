package ucab.fumadores;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Attribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class XML {
	
	public void imprimir(String fuente, String _hora, String _responsable, String _accion){
	
		try {
			
			File rutaXml = new File(fuente+".xml");
			Document doc = null;
			Element log = null;
			
			if(rutaXml.exists()) {
				FileInputStream fis;
				fis = new FileInputStream(rutaXml);
	            SAXBuilder sb = new SAXBuilder();
	            doc = sb.build(fis);
	          //Si existe se obtiene su nodo raiz.
	            log = doc.getRootElement();
	            fis.close();	          
			} else {
				// elemento root
				doc = new Document();
		        log = new Element("LOG");
		        
			}
	    
	         //  elemento evento
	         Element evento = new Element(fuente);
	         log.addContent(evento);

	         // atributo de eventp
	         Attribute hora = new Attribute("hora", _hora);
	         evento.setAttribute(hora);

	         // elemento accion
	         Element accion = new Element("accion");
	         Attribute responsable = new Attribute("responsable", _responsable);
	         accion.setAttribute(responsable);
	         accion.addContent(_accion);
	         evento.addContent(accion);
	         
	         doc.setContent(log);
	         
	         // escribir contenido al archivo xml
	         XMLOutputter xmlOutput = new XMLOutputter();
	         xmlOutput.setFormat(Format.getPrettyFormat());
	         xmlOutput.output(doc, new FileWriter(fuente+".xml"));
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}

}
