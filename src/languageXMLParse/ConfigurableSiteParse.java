package languageXMLParse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;
import languageXMLParse.ImageStorage;
import languageXMLParse.XMLConverter;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * ConfigurableSiteParse
 * This class is configurable website crawler.
 * It reads config info via json file, and assign value to specific field.
 * it crawl website data via configuration to set rule.      
 * copyright MUC-NLPL
 *
 */
public class ConfigurableSiteParse extends WebCrawler {

	/**
	 *    parameters:
	 *    1. title
	 *    2. author
	 *    3. publish time: timeofpublish
	 *    4. content
	 *    5. url domain name: domainname
	 *    6. provider
	 *    7. download time: timeofdownload
	 *    8. website chinese name: chinesename="人民网朝语版";
	 *    9. website name chinese pinyin: pinyin="rmwcyb";
	 *    10. website encoding: webencoding="utf-8";
	 *    11. website language: language="";
	 *    12. website encoding type: encodingtype="utf-8";
	 *    13. image url: imageurl+i
	 *    
	 * copyright MUC-NLPL 2014
	 *
	 */
	// set pattern's regular expression, use for selection
	// regular expression:
	// .*(\\.(css|js|bmp|gif|jpe?g - 1. "."  match any character except new line \n 2. * match many times 3. e in front of ? can be 0 or 1 time, $ ending character
	// \\. first escape any one character except \n behind \, then css js format string
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	// picture re pattern
	//private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");  	
	// url prefix
	private final static String URL_PREFIX = "http://kr.people.com.cn";	
	// record count
	private int i = 0;
   
    /**
     * set the crawler logic and limit which url can visit or not.
     * @param WebURL
	 * copyright MUC-NLPL
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            if (FILTERS.matcher(href).matches() || !href.startsWith(URL_PREFIX)) {  
                return false;  
            }                 
            return true;  
    }

    /**
     * after fetching the current page, execute method visit.
     * 
     * @param Page's instance object
     */
    @SuppressWarnings("deprecation")
	@Override
    public void visit(Page page) {   
    	try{
            if (page.getParseData() instanceof HtmlParseData) {
            	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();                    
            	String html = htmlParseData.getHtml();
            	Document doc = Jsoup.parse(html);              
            	Elements contents_html = doc.select(".wb_1");
            	if(!contents_html.isEmpty()){
                    String content = doc.select(".wb_1").text();
                    String title =doc.select("h1").text();
                    String timeofpublish =doc.select("#p_publishtime").text();
                    String domainname = page.getWebURL().getURL();
                    String provider= URL_PREFIX;
                    String author=doc.select(".wb_2").text();;
                    
                    System.out.println("#"+i+" record\n");
                    System.out.println("Title:"+title+"\n");
                    System.out.println("Author:"+author+"\n");
                    System.out.println("Publish Time:"+timeofpublish+"\n");
                    System.out.println("Content:"+content+"\n");
                    System.out.println("Domain Name:"+domainname+"\n");
                    System.out.println("Provider:"+provider+"\n");
                    
                    i++;
                    
                    SimpleDateFormat dfpt = new SimpleDateFormat("HH:mm, MMMMM dd, yyyy",Locale.ENGLISH);
                    Date dateOfPublish=dfpt.parse(timeofpublish);
                             
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
       				String timeofdownload=df.format(new Date()).toString();
                    String chinesename="人民网朝语版";
                    String pinyin="rmwcyb";
                    String webencoding="utf-8";
                    String language="朝文";
                    String encodingtype="utf-8";

                    Elements E_image=doc.select("table tbody img");                    
                    ArrayList<String> imageUrls=new ArrayList<String>();
                 
                    Iterator<Element> it=E_image.iterator();
                    for(int j=0;it.hasNext();j++){
	                    String imageAdress=URL_PREFIX+it.next().attr("src");
	                    String extension = imageAdress.substring(imageAdress.lastIndexOf("."));
	                    String imageFileName = pinyin + dateOfPublish.getTime() +"number"+j+ extension;
	                    String path="D:\\XHSDATA\\"+language+"\\"+chinesename+"\\"+(dateOfPublish.getYear()+1900)+"\\"+dateOfPublish.getMonth()+"\\"+dateOfPublish.getDay();
	                    String pathAndimageFileName=path+"\\"+imageFileName;
	                    File fp = new File(path);  	                          
	                    if (!fp.exists()) {  
	                    	fp.mkdirs();
                        }

	                    imageUrls.add(pathAndimageFileName);	                    
	                    ImageStorage.saveUrlFile(imageAdress, pathAndimageFileName); 
                    }

                    XMLConverter xc=new  XMLConverter();
                    xc.ConvertToXML(title,author,timeofpublish,content,domainname,provider,chinesename,pinyin,webencoding,language,encodingtype,timeofdownload,imageUrls);                   
            	}                 
            }            
        }catch(Exception e){
        	e.printStackTrace();
        }
    }     
}
