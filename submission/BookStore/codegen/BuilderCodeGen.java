import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class BuilderCodeGen {
	public static final String relRoot="src";//+File.separator;
	public static final String daoPath=relRoot+File.separator+"dao";
	
	
	public static boolean notContainKeywords(String input,String ...exclusions) {
		for(String exclusion:exclusions) {
			if(input.contains(exclusion)) return false;
		}
		return true;
	}
	@Test
	public void codeGenerator() {
		
		try {
			Files.walk(Paths.get(daoPath))
			.filter(Files::isRegularFile)
			.map(path -> path.toString())
			.filter(path->notContainKeywords(path,"DAO","Schema","accessors"))
			.map(path -> new File(path))
			.forEach(file->{
				 FileInputStream fis =null;
				 BufferedReader input=null ;
				 FileWriter writer=null;
				 String className=file.getPath().toString().substring(relRoot.length()+1).replace(File.separator,".").replace(".java","");


				 try {
					fis= new FileInputStream(file);
					input = new BufferedReader(new InputStreamReader(fis));
//					writer = new FileWriter(file);
					Class clazz=Class.forName(className);
					String builderCodeGen=builderString(clazz);
					String currentLine = "";
					String sourceCode = "";
				     while (currentLine != null) {	
				    	 currentLine = input.readLine();
				    	 if(currentLine!=null)sourceCode+=currentLine+"\n";
				    }
				     
				     //writer.write("testttttt");
				     //writer.flush();
				     System.out.println(sourceCode.replaceFirst("\n}", "replace\n}"));
				     //String completeCodeGen=sourceCode.replaceFirst("\n}", builderCodeGen+"\n}");
				    // System.out.println(completeCodeGen);
				} catch (IOException e) {
					e.printStackTrace();
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}finally {
					if(fis!=null) {
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(input!=null) {
						try {
							input.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(writer!=null) {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}


				}
				 
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String builderGen(Class<?> clazz,String sorceCode) {
		String inputCode="";
		//inputCode+="\t\t\public static class Builder{";
		for (Field field: clazz.getFields()) {
			
		}
		return sorceCode;
	}

	public String builderString(Class<?> clazz) {
		String inputCode="";
		inputCode+="\tpublic static class Builder{\n";
		String inputMethod="\t\tpublic Builder"+"(){\n"+"\t\t}\n\n";
		String fields="";
		String build="\t\tpublic "+clazz.getSimpleName()+" build(){\n";
		build+="\t\t\t"+clazz.getSimpleName()+" "+clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1,clazz.getSimpleName().length())+ "=new "+clazz.getSimpleName()+"()\n";
		for (Field field: clazz.getDeclaredFields()) {
			fields+="\t\tprivate "+field.getType().getSimpleName()+" "+field.getName()+";\n";
			inputMethod+="\t\tpublic Builder with"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1,field.getName().length())+"("+field.getType().getSimpleName()+" "+field.getName()+"){\n";
			inputMethod+="\t\t\tthis."+field.getName()+"="+field.getName()+";\n";
			inputMethod+="\t\t\treturn this;\n";
			inputMethod+="\t\t}\n\n";
			build+="\t\t\t"+clazz.getSimpleName().toLowerCase()+"."+field.getName()+"=this."+field.getName()+";\n";
		}
		build+="\t\t\treturn this;\n";
		build+="\t\t}\n";
		inputCode+=fields;
		inputCode+="\n";
		inputCode+=inputMethod;
		inputCode+=build;
		inputCode+="\n\t}";
		System.out.println(inputCode);
		return inputCode;

	}
}
