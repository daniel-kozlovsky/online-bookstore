import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class DAOGenerator {
	public static final String relRoot="src";//+File.separator;
	public static final String daoPath=relRoot+File.separator+"dao";
	@Test
	public void builderGen() {
		
		try {
			Files.walk(Paths.get(daoPath))
			.filter(Files::isRegularFile)
			.map(path -> path.toString())
			.filter(path->!path.contains("DAO")&&!path.contains("Schema")&&!path.contains("accessors"))
			.map(path -> new File(path))
			.map(file->file.getAbsolutePath())
			.collect(Collectors.toList())
			.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
