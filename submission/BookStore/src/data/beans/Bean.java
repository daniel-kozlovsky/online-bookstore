package data.beans;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
// extends IdObject<String>
public interface Bean {
	public abstract String toJson();
	
	public static String jsonMapVarChar(String key,String value) {
		return quote(key)+":"+quote(value);
	}
	
	public static String jsonMapNumber(String key,String value) {
		return quote(key)+":"+value; 
	}
	
	public static String quote(String word) {
		return "\""+word+"\"";
	}
	


}
