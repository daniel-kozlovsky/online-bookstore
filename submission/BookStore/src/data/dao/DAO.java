package data.dao;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;


import javax.xml.transform.stream.StreamResult;

import data.beans.Bean;
import data.query.Query;

public interface DAO{
//	public abstract T getById(T t);
	public abstract Query newQueryRequest();	
	public abstract DataUpdate newUpdateRequest();	
//	public abstract Query newUpdateRequest();
//	public abstract Query newDeleteRequest();
//	public abstract Query newInsertRequest();
	


	
//	String toXML() {
//		JAXBContext jc=null;
//		try {
//			jc = JAXBContext.newInstance(this.getClass());
//			Marshaller marshaller = jc.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//			StringWriter sw = new StringWriter();
//			sw.write("\n");
//			marshaller.marshal(this, new StreamResult(sw));			
//			System.out.println("XML output: \n"+sw.toString()); // for debugging
//			return sw.toString();
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}

}
