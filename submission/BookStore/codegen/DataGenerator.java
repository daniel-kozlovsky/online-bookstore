import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import data.beans.Customer;


public class DataGenerator {
	static List<String> customers;
	static List<String> poStatus;
	static List<String> completedLinks;
	static  Map<String, List<String>> koboParentLinks;
//	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		customers = new ArrayList<String>();
		poStatus= new ArrayList<String>();
		completedLinks=new ArrayList<String>();
		try {
			customers = Files.readAllLines(new File("ids-lf.txt").toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		customers.stream().forEach(System.out::println);
		poStatus.add("PAID");
		poStatus.add("PROCESSED");
		poStatus.add("SHIPPED");
		poStatus.add("COMPLETED");
		koboParentLinks= new HashMap<String, List<String>>();
		koboParentLinks.put("adventure", new ArrayList<String>());
		koboParentLinks.put("comics",  new ArrayList<String>());
		koboParentLinks.put("dystopia",  new ArrayList<String>());
		koboParentLinks.put("historical",  new ArrayList<String>());
		koboParentLinks.put("horror",  new ArrayList<String>());
		koboParentLinks.put("science-fiction",  new ArrayList<String>());
		koboParentLinks.put("artists",  new ArrayList<String>());
		koboParentLinks.put("business",  new ArrayList<String>());
		koboParentLinks.put("entertainment", new ArrayList<String>());
		koboParentLinks.put("romance",  new ArrayList<String>());
		for(String category:koboParentLinks.keySet()) {
			try {
				koboParentLinks.get(category).addAll(Files.readAllLines(new File(category+".txt").toPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			completedLinks=Files.readAllLines(new File("completedLinks.txt").toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void formatBookInserts() {
//		Set<String> ids;
		List<String> inserts= new ArrayList<String>();
		List<String> bookIds= new ArrayList<String>();
		List<String> customerIds= new ArrayList<String>();
		
//		try {
//			ids = new HashSet<String>(Files.readAllLines(new File("bookIds.txt").toPath()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String insertion="INSERT INTO BOOK (ID,TITLE,SERIES,DESCRIPTION,CATEGORY,AUTHOR,COVER,ISBN,PUBLISH_YEAR,PRICE,RATING,AMOUNT_SOLD) VALUES ";
		String insertion="";
		try {
			inserts=Files.readAllLines(new File("reviewInserts.txt").toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			customerIds=Files.readAllLines(new File("customerIds.txt").toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(customerIds.size()); //102
//		System.out.println(bookIds.size());//892

//		Set<String> nonDupe=new HashSet<String>();
//		for(String insert:inserts) {
//			nonDupe.add(insert.substring(41,77));
//		}
//		'PROCESSED','SHIPPED','DENIED','DELIVERED','ORDERED'
//			INSERT INTO PURCHASE_ORDER (ID, BOOK,STATUS,AMOUNT,CREATED_AT_EPOCH)
		String insertion2="INSERT INTO REVIEW (CUSTOMER,BOOK,RATING,TITLE,BODY,CREATED_AT_EPOCH ) VALUES ";
			String[] statuss = {"PROCESSED","SHIPPED","DENIED","DELIVERED","ORDERED"};
			FileWriter writer=null;
			try {
				writer = new FileWriter("insertReviews.txt"); 
				writer.write("INSERT INTO REVIEW (CUSTOMER,BOOK,RATING,TITLE,BODY,CREATED_AT_EPOCH ) VALUES "+"\n");
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				for(String insert:inserts) {
//					int status = (int)(Math.random()*4);
//					int book=(int)(Math.random()*891);
//					int customer=(int)(Math.random()*101);
//					int amountPO = (int)(Math.random()*3)+1;
//					int poDay=(int)(Math.random()*(27))+1;
//					String poDayString = Integer.toString(poDay);
//					int poMonth=(int)(Math.random()*11)+1;
//					String poMonthString = Integer.toString(poMonth);
//					String poDateString = ("2020-"+poMonthString+"-"+poDayString).stripLeading().stripTrailing();
//					dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));			
//				    Date date=null;
//				    long poEpoch =0;
//					try {
//						date = dateFormat.parse(poDateString);
//						poEpoch = date.getTime();
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					String[] split=insert.split(":");
//					String customerId=split[0];
//					String bookId=split[1];
//					String epoch = Long.toString(poEpoch);
//					int amount=(int)(Math.random()*3)+1;
//					String input = "('"+customerIds.get(customer)+"','"+bookIds.get(book)+"','"+statuss[status]+"',"+amount+","+epoch+")";
//					writer.write(insert+","+"\n");
//					String bookId=insert.substring(2,38);
//					System.out.println(bookID);
//					System.out.println(customerId);
				
//					String format = insert.replaceAll("','","_-_-_-_").replaceAll("\\('", "---___---").replaceAll("'", "''").replaceAll("[^[\\s][.][#][?]['][,]0-9A-Za-z_-]", "");
//					format=format.replaceAll("_-_-_-_","','").replaceAll("---___---", "('");
//					format=format.replaceAll("\",","',");
//					format=format.substring(1,format.length());
//					format="("+format+"###";
//					format=format.replace("'',","',");
//					format=format.replace("(',","'," );
//					System.out.println(format);
//					Pattern epochPattern = Pattern.compile("',([0-9]+)###");
//					Matcher epochMatcher = epochPattern.matcher(format);
//					epochMatcher.find();
//					String epoch=epochMatcher.group(1);
//					if(!insert.contains(","+epoch+")"))
//						System.err.println("epoch not copied proper: "+ epoch);
//					
//					String one= format.substring(0,78);
//					String two= format.substring(79,85);
//					String three= format.substring(86,format.length());
//					format=one+two+three;
//					format=format.replaceAll("'',"+epoch+"###", "',"+epoch+")");
//					format=format.replaceAll("\\(',"+epoch+"###", "',"+epoch+")");
//					format=format.replaceAll("###", ")");


					writer.write(insert+","+"\n");
					

				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(writer!=null) {
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		
	}
	public void generateBooksFromLinkFiles() {
		for(Entry<String, List<String>> entry:koboParentLinks.entrySet()) {
			for(String link: entry.getValue()) {
				generateBook(link,entry.getKey());
			}
			
		}
	}
	

	public void generateBooks() {
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 3);	
//		driver.get("https://www.amazon.com/s?i=specialty-aps&rh=n%3A17143709011&fs=true&ref=lp_17143709011_sar");
////		List<String> links=
//		driver.findElements(By.tagName("a"))
//		.stream()
//		.map(element->element.getAttribute("href"))
//		.filter(link->link!=null)
//		.filter(link->link.contains("/dp/"))
//		.filter(link->!link.toLowerCase().contains("#customerreviews"))
//		.filter(link->!link.contains("ebook"))
//		.filter(link->!link.contains(".com/dp/"))
//		.filter(link->!link.toLowerCase().contains("a-novel"))
//		.filter(link->!link.toLowerCase().contains("audiobook"))
//		.collect(Collectors.toSet())
//		.forEach(System.out::println);
//		
//		links.stream().filter(link->link!=null).filter(link->link.contains("/dp/")).forEach(System.out::println);
//		driver.get("https://www.kobo.com/ca/en/ebooks/drama-1");
//		driver.findElements(By.tagName("a"))
//		.stream()
//		.map(element->element.getAttribute("href"))
//		.filter(link->link!=null)
//		.filter(link->link.contains("/ca/en/ebook/"))
//		.collect(Collectors.toSet())
//		.forEach(System.out::println);
		


		Map<String, Set<String>> koboParentLinks= new HashMap<String, Set<String>>();
		koboParentLinks.put("adventure", new HashSet<String>());
		koboParentLinks.put("comics", new HashSet<String>());
		koboParentLinks.put("dystopia", new HashSet<String>());
		koboParentLinks.put("historical", new HashSet<String>());
		koboParentLinks.put("horror", new HashSet<String>());
		koboParentLinks.put("science-fiction", new HashSet<String>());
		koboParentLinks.put("artists", new HashSet<String>());
		koboParentLinks.put("business", new HashSet<String>());
		koboParentLinks.put("entertainment", new HashSet<String>());
		koboParentLinks.put("romance", new HashSet<String>());
		
		koboParentLinks.get("adventure").add("https://www.kobo.com/ca/en/ebooks/action-adventure-3");
		koboParentLinks.get("adventure").add("https://www.kobo.com/ca/en/ebooks/action-adventure-3?pageNumber=2");
		koboParentLinks.get("adventure").add("https://www.kobo.com/ca/en/ebooks/action-adventure-3?pageNumber=3");
		koboParentLinks.get("adventure").add("https://www.kobo.com/ca/en/ebooks/action-adventure-3?pageNumber=4");
		koboParentLinks.get("comics").add("https://www.kobo.com/ca/en/ebooks/comics-graphic-novels-1");
		koboParentLinks.get("comics").add("https://www.kobo.com/ca/en/ebooks/comics-graphic-novels-1?pageNumber=2");
		koboParentLinks.get("comics").add("https://www.kobo.com/ca/en/ebooks/comics-graphic-novels-1?pageNumber=3");
		koboParentLinks.get("comics").add("https://www.kobo.com/ca/en/ebooks/comics-graphic-novels-1?pageNumber=4");
		koboParentLinks.get("dystopia").add("https://www.kobo.com/ca/en/ebooks/dystopia");
		koboParentLinks.get("dystopia").add("https://www.kobo.com/ca/en/ebooks/dystopia?pageNumber=2");
		koboParentLinks.get("dystopia").add("https://www.kobo.com/ca/en/ebooks/dystopia?pageNumber=3");
		koboParentLinks.get("dystopia").add("https://www.kobo.com/ca/en/ebooks/dystopia?pageNumber=4");
		koboParentLinks.get("historical").add("https://www.kobo.com/ca/en/ebooks/historical-9");
		koboParentLinks.get("historical").add("https://www.kobo.com/ca/en/ebooks/historical-9?pageNumber=2");
		koboParentLinks.get("historical").add("https://www.kobo.com/ca/en/ebooks/historical-9?pageNumber=3");
		koboParentLinks.get("historical").add("https://www.kobo.com/ca/en/ebooks/historical-9?pageNumber=4");
		koboParentLinks.get("horror").add("https://www.kobo.com/ca/en/ebooks/horror-5");
		koboParentLinks.get("horror").add("https://www.kobo.com/ca/en/ebooks/horror-5?pageNumber=2");
		koboParentLinks.get("horror").add("https://www.kobo.com/ca/en/ebooks/horror-5?pageNumber=3");
		koboParentLinks.get("horror").add("https://www.kobo.com/ca/en/ebooks/horror-5?pageNumber=4");
		koboParentLinks.get("science-fiction").add("https://www.kobo.com/ca/en/ebooks/science-fiction-4");
		koboParentLinks.get("science-fiction").add("https://www.kobo.com/ca/en/ebooks/science-fiction-4?pageNumber=2");
		koboParentLinks.get("science-fiction").add("https://www.kobo.com/ca/en/ebooks/science-fiction-4?pageNumber=3");
		koboParentLinks.get("science-fiction").add("https://www.kobo.com/ca/en/ebooks/science-fiction-4?pageNumber=4");
		koboParentLinks.get("artists").add("https://www.kobo.com/ca/en/ebooks/artists-architects-photographers");
		koboParentLinks.get("artists").add("https://www.kobo.com/ca/en/ebooks/artists-architects-photographers?pageNumber=2");
		koboParentLinks.get("artists").add("https://www.kobo.com/ca/en/ebooks/artists-architects-photographers?pageNumber=3");
		koboParentLinks.get("artists").add("https://www.kobo.com/ca/en/ebooks/artists-architects-photographers?pageNumber=4");
		koboParentLinks.get("business").add("https://www.kobo.com/ca/en/ebooks/business");
		koboParentLinks.get("business").add("https://www.kobo.com/ca/en/ebooks/business?pageNumber=2");
		koboParentLinks.get("business").add("https://www.kobo.com/ca/en/ebooks/business?pageNumber=3");
		koboParentLinks.get("business").add("https://www.kobo.com/ca/en/ebooks/business?pageNumber=4");
		koboParentLinks.get("entertainment").add("https://www.kobo.com/ca/en/ebooks/entertainment-performing-arts");
		koboParentLinks.get("entertainment").add("https://www.kobo.com/ca/en/ebooks/entertainment-performing-arts?pageNumber=2");
		koboParentLinks.get("entertainment").add("https://www.kobo.com/ca/en/ebooks/entertainment-performing-arts?pageNumber=3");
		koboParentLinks.get("entertainment").add("https://www.kobo.com/ca/en/ebooks/entertainment-performing-arts?pageNumber=4");
		koboParentLinks.get("romance").add("https://www.kobo.com/ca/en/ebooks/contemporary-1");
		koboParentLinks.get("romance").add("https://www.kobo.com/ca/en/ebooks/contemporary-1?pageNumber=2");
		koboParentLinks.get("romance").add("https://www.kobo.com/ca/en/ebooks/contemporary-1?pageNumber=3");
		koboParentLinks.get("romance").add("https://www.kobo.com/ca/en/ebooks/contemporary-1?pageNumber=4");
		
		Map<String, Set<String>> koboCategoryLinks= new HashMap<String, Set<String>>();
		koboCategoryLinks.put("adventure", new HashSet<String>());
		koboCategoryLinks.put("comics", new HashSet<String>());
		koboCategoryLinks.put("dystopia", new HashSet<String>());
		koboCategoryLinks.put("historical", new HashSet<String>());
		koboCategoryLinks.put("horror", new HashSet<String>());
		koboCategoryLinks.put("science-fiction", new HashSet<String>());
		koboCategoryLinks.put("artists", new HashSet<String>());
		koboCategoryLinks.put("business", new HashSet<String>());
		koboCategoryLinks.put("entertainment", new HashSet<String>());
		koboCategoryLinks.put("romance", new HashSet<String>());
		
		for(Entry<String, Set<String>> entry:koboParentLinks.entrySet()) {
			for(String parentLink:entry.getValue()) {
				driver.get(parentLink);
				wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
				Set<String> links=	driver.findElements(By.tagName("a"))
						.stream()
						.map(element->element.getAttribute("href"))
						.filter(link->link!=null)
						.filter(link->link.contains("ebook"))
						.filter(link->!link.contains("ebooks"))
						.filter(link->!link.contains("facebook"))
						.filter(link->!link.contains("?"))
						.collect(Collectors.toSet());
				for(String link:links) {
					boolean linkExists=false;
					for(Entry<String, Set<String>> entryLink :koboCategoryLinks.entrySet()) {
						if(entryLink.getValue().contains(link)) {
							linkExists=true;
							break;
						}
					}
					if(!linkExists) {
						koboCategoryLinks.get(entry.getKey()).add(link);
					}
				}
			}
		}
		driver.close();
		driver.quit();
		
		koboCategoryLinks.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.forEach(System.out::println);
		for(Entry<String, Set<String>> entry:koboCategoryLinks.entrySet()) {
			FileWriter writer = null;
			  try {
				  writer = new FileWriter(entry.getKey()+".txt",true);				
				for(String link: entry.getValue()) {
					writer.write(link + "\n");
					

				}	

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(writer!=null) {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		
		}
		
		for(Entry<String, Set<String>> entry:koboCategoryLinks.entrySet()) {
			for(String link: entry.getValue()) {
				generateBook(link,entry.getKey());
			}
			
		}

			



	}
	
//	@Test	
	public void generateBook(String bookLink,String category) {
		if(completedLinks.contains(bookLink)) return;
		System.out.println("link: "+bookLink);
		FileWriter writer = null;
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 3);		
		driver.get(bookLink);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		String source = driver.getPageSource();
		
		String title = "";
		try {
			title = driver.findElement(By.cssSelector("h2.product-field:nth-child(2)")).getText().stripLeading().stripTrailing();

		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}	catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if( title==null||title.isEmpty()) {
				title = driver.findElement(By.cssSelector("h2.title:nth-child(3)")).getText().stripLeading().stripTrailing();
			}

		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}	catch (Exception e) {
			// TODO: handle exception
		}	
		try {
			if( title==null||title.isEmpty()) {
				title = driver.findElement(By.cssSelector("title product-field")).getText().stripLeading().stripTrailing();
			}

		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}	catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if( title==null||title.isEmpty()) {
				title = driver.findElement(By.cssSelector("title")).getText().stripLeading().stripTrailing();
			}

		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}	catch (Exception e) {
			// TODO: handle exception
		}
		if( title==null||title.isEmpty()) {
		title = driver.findElement(By.className("title")).getText().stripLeading().stripTrailing();
		Pattern titlePattern = Pattern.compile("\\s*\"@type\":\\s*\"Book\",\\s*\"name\":\\s*\"(.+)\",");
		Matcher titleMatcher = titlePattern.matcher(source);
		title = "";
			while(titleMatcher.find()) {
				title=titleMatcher.group(1).stripLeading().stripTrailing();
				if(title !=null || !title.isEmpty()) break;
			}
		}
		String author = driver.findElement(By.className("contributor-name")).getText().stripLeading().stripTrailing();
//		String author = "";
//		Pattern authorPattern = Pattern.compile("\"author\":\\s*\\{\\s*\"@type\":\\s*\"Person\",\\s*\"name\":\\s*\"(.+)\"");
//		Matcher authorMatcher = authorPattern.matcher(source);
//		if(authorMatcher.find()) {
//			title=authorMatcher.group(1);
//		}
		String series="";
			try {
				series = driver.findElement(By.className("book-series")).getText().stripLeading().stripTrailing();
				if( series==null||series.isEmpty()) {
				series = driver.findElement(By.cssSelector(".product-sequence-field > a:nth-child(1)")).getText().stripLeading().stripTrailing();
				}

			}catch(NoSuchElementException e) {
				e.printStackTrace();
			}	catch (Exception e) {
				// TODO: handle exception
			}		
//		String isbn = driver.findElement(By.cssSelector(".bookitem-secondary-metadata > ul:nth-child(2) > li:nth-child(4) > span:nth-child(1)")).getText().stripLeading().stripTrailing();
			String isbn = "";
			Pattern isbnPattern = Pattern.compile("ISBN:\\s*<span\\s*translate=\"no\">(\\d+)</span>");
			Matcher isbnMatcher = isbnPattern.matcher(source);
			if(isbnMatcher.find()) {
				isbn=isbnMatcher.group(1);
			}
			String price = "";
	        
			Pattern pricePattern = Pattern.compile("<span\\s*class=\"price\">\\$([0-9]+.99)</span>");
			Matcher priceMatcher = pricePattern.matcher(source);
			while(priceMatcher.find()) {
				price=priceMatcher.group(1).replace("$", "").replaceAll("\\s+", "").stripLeading().stripTrailing();
				if(price !=null || !price.isEmpty()) break;
			}

//		String price=Integer.toString((int)(Math.random()*10)+1)+"."+Integer.toString((int)(Math.random()*100));
//		boolean priceLoaded=false;
			if(price ==null || price.isEmpty()) {
				try {
					price=driver.findElement(By.className("price")).getText().replace("$", "").replaceAll("\\s+", "").stripLeading().stripTrailing();
				}catch(NoSuchElementException e) {
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if(price ==null || price.isEmpty()) 
						price = driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)")).getText().replace("$", "").replaceAll("\\s+", "").stripLeading().stripTrailing();
				}catch(NoSuchElementException e) {
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if(price ==null || price.isEmpty()) 
					price = driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span:nth-child(1)")).getText().replace("$", "").replaceAll("\\s+", "").stripLeading().stripTrailing();

				}catch(NoSuchElementException e) {
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
				}
			
			}
			if(price ==null || price.isEmpty())
				price=Integer.toString((int)(Math.random()*10)+1)+"."+"99";
////		if(driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span:nth-child(1)")).isDisplayed()||driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)")).isDisplayed()) {
//		if(!priceLoaded) {
//			try {
//				price = driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)")).getText().replace("$", "").replace(" out of 5","").replace("Rated ", "").replaceAll("\\s+", "").stripLeading().stripTrailing();
//				if(price!=null &&!price.isEmpty())
//					priceLoaded=true;
//			}catch(NoSuchElementException e) {
//				e.printStackTrace();
//			}catch (Exception e) {
//				// TODO: handle exception
//			}	
//			}
//			if(!priceLoaded) {
//				try {
//					price = driver.findElement(By.cssSelector("div.pricing-details:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span:nth-child(1)")).getText().replace("$", "").replace(" out of 5","").replace("Rated ", "").replaceAll("\\s+", "").stripLeading().stripTrailing();
//					if(price!=null &&!price.isEmpty())
//						priceLoaded=true;
//				}catch(NoSuchElementException e) {
//					e.printStackTrace();			
//				}catch (Exception e) {
//					// TODO: handle exception
//				}	
//			}
//			
//			if(!priceLoaded)
//				price=Integer.toString((int)(Math.random()*10)+1)+"."+Integer.toString((int)(Math.random()*100));
//
//		}
		String releaseDate ="";
		if(driver.findElement(By.cssSelector(".bookitem-secondary-metadata > ul:nth-child(2) > li:nth-child(2) > span:nth-child(1)")).isDisplayed()) {
			releaseDate = driver.findElement(By.cssSelector(".bookitem-secondary-metadata > ul:nth-child(2) > li:nth-child(2) > span:nth-child(1)")).getText().stripLeading().stripTrailing();
		}
		
		String releaseYear = releaseDate==null||releaseDate.isEmpty()?Integer.toString((int)(Math.random()*50)+1970) :releaseDate.replaceAll("[A-Za-z]+ [0-9]+, ", "").stripLeading().stripTrailing();
		String coverUrl = driver.findElement(By.className("cover-image")).getAttribute("src").stripLeading().stripTrailing();
		String descriptionRaw = driver.findElement(By.cssSelector(".synopsis-description")).getText().stripLeading().stripTrailing();
		if(descriptionRaw==null|| descriptionRaw.isEmpty())
			descriptionRaw=driver.findElement(By.cssSelector(".synopsis-description > p:nth-child(1)")).getText().stripLeading().stripTrailing();
		if(descriptionRaw==null|| descriptionRaw.isEmpty())
			descriptionRaw=driver.findElement(By.cssSelector(".synopsis-description > p:nth-child(1) > strong:nth-child(1)")).getText().stripLeading().stripTrailing();
		
		
		String[] descriptionRawLines=descriptionRaw.split("\\n");
		String description = "";
		for(int i=1;i<descriptionRawLines.length;i++) {
			description+=descriptionRawLines[i];
			description+=" ";
		}
		List<WebElement> reviewElements = new ArrayList<WebElement>();
		try {
			if(driver.findElement(By.className("review-item-wrapper")).isDisplayed()) {
				reviewElements = driver.findElements(By.className("review-item-wrapper"));
			}
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}	
		int loads=0;
		if(reviewElements.isEmpty()) {
			try {
				while(driver.findElement(By.cssSelector("#load-more-reviews")).isDisplayed()||loads==4) {
					if(driver.findElement(By.cssSelector("#load-more-reviews")).isDisplayed()) {
						driver.findElement(By.cssSelector("#load-more-reviews")).click();
					}
					loads++;
				}
			}catch(NoSuchElementException e) {
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
			}	
			try {
				reviewElements = driver.findElements(By.className("review-item-wrapper"));
			}catch(NoSuchElementException e) {
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
			}	

		}
		String idInput=isbn+releaseYear;
		String id =UUID.nameUUIDFromBytes(idInput.getBytes()).toString().stripLeading().stripTrailing();

		for(WebElement review: reviewElements) {
			int reviewDay=(int)(Math.random()*27)+1;
			String reviewDayString=Integer.toString(reviewDay);
			int reviewMonth=(int)(Math.random()*11)+1;
			String reviewMonthString=Integer.toString(reviewDay);
			String dateString = ("2020-"+reviewMonthString+"-"+reviewDayString).stripLeading().stripTrailing();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			int customerCount= customers.size();
			System.out.println(customerCount);
			int customerIndex=(int)(Math.random()*customerCount);
			System.out.println(customerIndex);
			String userId=customers.get(customerIndex);
			int amountPO = (int)(Math.random()*3)+1;
			int poDay=(int)(Math.random()*(reviewDay-1))+1;
			String poDayString = Integer.toString(poDay);
			int poMonth=(int)(Math.random()*reviewMonth)+1;
			String poMonthString = Integer.toString(poMonth);
			String poDateString = ("2020-"+poMonthString+"-"+poDayString).stripLeading().stripTrailing();
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));			
		    Date date=null;
		    long utc =0;
		    long poUtc =0;
			try {
				date = dateFormat.parse(dateString);
				poUtc = date.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				date = dateFormat.parse(dateString);
				 utc = date.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String reviewRating =review.findElement(By.className("rating-stars")).getAttribute("aria-label").replace(" out of 5","").replaceAll("[A-Za-z\\s]+", "").stripLeading().stripTrailing();
			String reviewTitle=review.findElement(By.className("review-title")).getText().stripLeading().stripTrailing();
			String reviewText=review.findElement(By.className("review-text")).getText().stripLeading().stripTrailing();
			String insertReview = "('"+userId+"','"+id+"',"+reviewRating+",'"+reviewTitle+"','"+reviewText+"',"+Long.toString(utc)+")";
			System.out.println(insertReview);




			
			String purchaseOrder= "('"+userId+"','"+id+"','COMPLETED',"+amountPO+","+Long.toString(poUtc)+")";
			System.out.println(purchaseOrder);
			
			  try {
					writer = new FileWriter("poInserts.txt",true); 
					writer.write(purchaseOrder + "\n");
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writer = new FileWriter("poId.txt",true); 
					writer.write(userId+":"+poUtc+ "\n");
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writer = new FileWriter("reviewInserts.txt",true); 
					writer.write(insertReview + "\n");
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writer = new FileWriter("reviewIds.txt",true); 
					writer.write(userId+":"+id+ "\n");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(writer!=null) {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

		}
		String rating="0";
		String amountSold="0";
//		INSERT INTO PURCHASE_ORDER (ID,BOOK, STATUS, AMOUNT,UTC) VALUES 
//		INSERT INTO BOOK (ID,TITLE,SERIES,DESCRIPTION,CATEGORY,AUTHOR,COVER,ISBN,PUBLISH_YEAR,PRICE,RATING,AMOUNT_SOLD) VALUES
//		INSERT INTO REVIEW (CUSTOMER,BOOK,RATING,TITLE,TITLE,BODY,UTC) VALUES

		 URL imageURL=null;
		 BufferedImage saveImage=null;
		 String imageFileName="NULL";
		try {
			imageURL = new URL(coverUrl);
			saveImage = ImageIO.read(imageURL);
			imageFileName=(title+"_"+isbn).replaceAll("\\s+", "_").replaceAll("\\W+","")+".jpg";
			 ImageIO.write(saveImage, "jpg", new File(".."+File.separator+".."+File.separator+"database"+File.separator+"book_images"+File.separator+"covers"+File.separator+imageFileName));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String insertValue="('"+id+"'"+",'"+title+"','"+series+"','"+description+"','"+category+"','"+author+"','"+imageFileName+"','"+isbn+"',"+releaseYear+","+price+","+rating+","+amountSold+")";
		System.out.println(insertValue);
		  try {
			writer = new FileWriter("bookInserts.txt",true); 
			writer.write(insertValue + "\n");
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer = new FileWriter("bookIds.txt",true); 
			writer.write(id + "\n");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		  

			  try {
				  writer = new FileWriter("completedLinks.txt",true);		
				  writer.write(bookLink + "\n");			

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(writer!=null) {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		
		  
				driver.close();


		
	}
	
	public void generateReviews() {
		
	}

	public void generateCustomers() {
		String update ="INSERT INTO CUSTOMER (ID,GIVENNAME,SURNAME,USERNAME,PASSWORD ,EMAIL,STREET_NUMBER,STREET,POSTAL_CODE,CITY,PROVINCE,COUNTRY,UTC,CREDIT_CARD,CREDIT_CARD_NUMBER,CREDIT_CARD_EXPIRY,CREDIT_CARD_CVV2) VALUES";
		
		Map<Integer,String> emailServices = new HashMap<Integer, String>();
		emailServices.put(0, "gmail");
		emailServices.put(1, "yahoo");
		emailServices.put(2, "live");
		emailServices.put(3, "icloud");
		emailServices.put(4, "outlook");
		emailServices.put(5, "protonmail");
		List<String> namesAdded = new ArrayList<String>();
		List<String> customerInserstions = new ArrayList<String>();
		List<String> customerIds = new ArrayList<String>();
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

		for(int i=0;i<100;i++) {
			WebDriver driver=null;
			try {
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
			driver.get("https://www.fakeaddressgenerator.com/World/ca_address_generator");
			WebDriverWait wait = new WebDriverWait(driver, 10);	
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			wait.wait(2);
			String fullName = driver.findElement(By.cssSelector(".table > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > strong:nth-child(1)")).getText();
			if(!namesAdded.contains(fullName)) {
				namesAdded.add(fullName);
				int nameWordCount=fullName.split(" ").length;
				String firstName=fullName.split(" ")[0].stripLeading().stripTrailing();
				String lastName=fullName.split(" ")[nameWordCount-1].stripLeading().stripTrailing();
				String address = driver.findElement(By.cssSelector(".detail > div:nth-child(3) > div:nth-child(4) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String streetNumber = address.replaceAll("\\D", "").stripLeading().stripTrailing();
				String userName= ((firstName.length()>2?firstName.substring(0, 1):firstName)+lastName+streetNumber).stripLeading().stripTrailing();
				String password=(firstName+"password").stripLeading().stripTrailing();
				String email=(userName+"@"+emailServices.get((int)(Math.random()*5))+".com").stripLeading().stripTrailing();
				String street = address.replaceAll("\\d", "").stripLeading().stripTrailing();
				String city = driver.findElement(By.cssSelector(".detail > div:nth-child(3) > div:nth-child(5) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String province = driver.findElement(By.cssSelector("div.row:nth-child(6) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String postalCode = driver.findElement(By.cssSelector("div.row:nth-child(7) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String creditCardType = driver.findElement(By.cssSelector("div.row:nth-child(26) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String creditCardNumber = driver.findElement(By.cssSelector("div.row:nth-child(27) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String cvv2 = driver.findElement(By.cssSelector("div.row:nth-child(28) > div:nth-child(2) > strong:nth-child(1) > input:nth-child(1)")).getAttribute("value").stripLeading().stripTrailing();
				String creditCardExpiry= driver.findElement(By.cssSelector("#creditCard")).getAttribute("value").stripLeading().stripTrailing();
				String id =UUID.nameUUIDFromBytes(userName.getBytes()).toString().stripLeading().stripTrailing();
				String country ="Canada";
				String dateStringRaw=driver.findElement(By.cssSelector(".table > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2) > strong:nth-child(1)")).getText().stripLeading().stripTrailing();
				String[] dateComponents=dateStringRaw.split("/");
				String dateString = ("2020-"+dateComponents[0]+"-"+dateComponents[1]).stripLeading().stripTrailing();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			    Date date;
			    long utc =0;
				try {
					date = dateFormat.parse(dateString);
					 utc = date.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//		System.out.println("first name: "+firstName);
//		System.out.println("last name: "+lastName);
//		System.out.println("user name: "+userName);
//		System.out.println("password : "+password);
//		System.out.println("email : "+ email);
//		System.out.println("streetNumber: "+streetNumber);
//		System.out.println("street: "+street);
//		System.out.println("city: "+city);
//		System.out.println("province :"+ province);
//		System.out.println("postal code: "+postalCode);
//		System.out.println("credit card type: "+ creditCardType);
//		System.out.println("credit card number: "+creditCardNumber);
//		System.out.println("cvv2: "+cvv2);
//		System.out.println("credit card expiry: "+creditCardExpiry);
//		System.out.println("ID: "+id);
//		System.out.println("ID: "+id);
//		System.out.println("ID: "+id);
//		System.out.println("ID: "+id);
//		System.out.println("utc: "+ Long.toString(utc));

				boolean emptyData=
						id==null || id.isEmpty() ||
						firstName==null  || firstName.isEmpty() ||
						lastName==null  || lastName.isEmpty() ||
						userName==null  || userName.isEmpty() ||
						password==null  || password.isEmpty() ||
						email==null  || email.isEmpty() ||
						streetNumber==null  || streetNumber.isEmpty() ||
						street==null  || street.isEmpty() ||
						postalCode==null  || postalCode.isEmpty() ||
						city==null  || city.isEmpty() ||
						province==null  || province.isEmpty() ||
						country==null  || country.isEmpty() ||
						utc==0 ||
						creditCardType==null  || creditCardType.isEmpty() ||
						creditCardNumber==null  || creditCardNumber.isEmpty() ||
						creditCardExpiry==null  || creditCardExpiry.isEmpty() ||
						cvv2==null  || cvv2.isEmpty();
		   
			
				if(!emptyData) {
					customerInserstions.add("("+
							id+","+
							firstName+","+
							lastName+","+
							userName+","+
							password+","+
							email+","+
							streetNumber+","+
							street+","+
							postalCode+","+
							city+","+
							province+","+
							country+","+
							utc+","+
							creditCardType+","+
							creditCardNumber+","+
							creditCardExpiry+","+
							cvv2+")");
					customerIds.add(id);
				}
			}
		}catch(NoSuchElementException e) {
				e.printStackTrace();
		}catch(NoSuchWindowException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(driver!=null) {
				driver.close();
			}
		}	
		
		

		

	}
		customerInserstions.forEach(System.out::println);
		customerIds.forEach(System.out::println);
		FileWriter writer = null;
		  try {
			writer = new FileWriter("sqlinserts.txt",true); 
			for(String str: customerInserstions) {
			  writer.write(str + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		  try {
			writer = new FileWriter("ids.txt",true); 
			for(String str: customerIds) {
			  writer.write(str + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		}

	public void fixInputs() {
//		(8e5ed615-7e6c-3b74-9718-555d34a9548c,Hedy,Cory,HCory4153,Hedypassword,HCory4153@gmail.com,4153,Island Hwy,V9W 2C9,Campbell River,British Columbia,Canada,1593648000000,Visa,4916341604421985,11/2021,677)
		List<String> formatLines = new ArrayList<String>();
		try {
			List<String> lines = Files.readAllLines(new File("formatInserts.txt").toPath());


			for(String line:lines) {
				String formatLine = "";
				String[] items=line.split(",");
				for(int i=0;i<items.length;i++) {
					if(i==items.length-1) {
						String itemRemoveTail = items[i].substring(0, items[i].length()-1);
						formatLine += "'"+itemRemoveTail+"'),";
					}else if(i!=0 && i!=1 && i!=13){
						String quote = "'"+items[i]+"',";
						formatLine+=quote;
					}else {
				
						formatLine+=items[i]+",";
					}
				}
				formatLines.add(formatLine);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter("finalInserts.txt",true); 
			for(String str: formatLines) {
			  writer.write(str + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
//	@Test 
	public void fixEmails(){
		Map<Integer,String> emailServices = new HashMap<Integer, String>();
		emailServices.put(0, "gmail");
		emailServices.put(1, "yahoo");
		emailServices.put(2, "live");
		emailServices.put(3, "icloud");
		emailServices.put(4, "outlook");
		emailServices.put(5, "protonmail");
		List<String> fixedLines = new ArrayList<String>();
		
		try {
			List<String> lines = Files.readAllLines(new File("finalInserts.txt").toPath());
			
			

			for(String line:lines) {
				fixedLines.add(line.replace("@gmail","@"+ emailServices.get((int)(Math.random()*5))));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fixedLines.forEach(System.out::println);
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("finalInsertsFixed.txt",true); 
			for(String str: fixedLines) {
			  writer.write(str + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	
	

}
