package ucab.fumadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Traza {

	//M�todo que permite la inserci�n de los eventos en un archivo de tipo XML
	public static void insertarTraza(String hora,String actor, String accion){
	      
	    Document document=null;
	      Element root=null;
	      File xmlFile = new File("Traza.xml");
	      //Se comprueba si el archivo XML ya existe o no.
	      if(xmlFile.exists()) {
	          FileInputStream fis;
	      try {
	        fis = new FileInputStream(xmlFile);
	            SAXBuilder sb = new SAXBuilder();
	            document = sb.build(fis);
	          //Si existe se obtiene su nodo raiz.
	            root = document.getRootElement();
	            fis.close();
	      } catch (JDOMException | IOException e) {
	        e.printStackTrace();
	      }
	      } else {
	    	//Si no existe se crea su nodo ra�z.
	          document = new Document();
	          root = new Element("Traza");
	      }
	      
	      //Se crea un nodo Hecho para insertar la informaci�n.
	      Element nodohecho = new Element("Hecho");  
	    //Se crea un nodo hora para insertar la informaci�n correspondiente a la hora.
	        Element nodohora = new Element("Hora");
	      //Se crea un nodo actor para insertar la informaci�n correspondiente al actor.
	      Element nodoactor = new Element("Actor");
	    //Se crea un nodo accion para insertar la informaci�n correspondiente a la acci�n.
	        Element nodoaccion = new Element("Accion");
	        
	      //Se asignan los valores enviados para cada nodo.
	        nodohora.setText(hora);
	        nodoactor.setText(actor);
	        nodoaccion.setText(accion);
	      //Se a�ade el contenido al nodo Hecho.  
	        nodohecho.addContent(nodohora);
	        nodohecho.addContent(nodoactor);
	        nodohecho.addContent(nodoaccion);
	      //Se a�ade el nodo Hecho al nodo ra�z.
	        root.addContent(nodohecho);
	        document.setContent(root);
	        
	      //Se procede a exportar el nuevo o actualizado archivo de traza XML   
	        XMLOutputter xmlOutput = new XMLOutputter();
	        xmlOutput.setFormat(Format.getPrettyFormat());
<<<<<<< HEAD

=======
	        
>>>>>>> ddc3caf5b37013a96d62e87eb6c31785dec809e5
	        try {
	      xmlOutput.output(document, new FileWriter("Traza.xml"));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	        
	  }	
	
	
}