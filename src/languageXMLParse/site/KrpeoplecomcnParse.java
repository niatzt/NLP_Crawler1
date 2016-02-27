package languageXMLParse.site;

//import java.io.ByteArrayOutputStream;
import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
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

public class KrpeoplecomcnParse extends WebCrawler {

	// set pattern's regular expression, use for selection
	// regular expression:
	// .*(\\.(css|js|bmp|gif|jpe?g - 1. "."  match any character except new line \n 2. * match many times 3. e in front of ? can be 0 or 1 time, $ ending character
	// \\. first escape any one character except \n behind \, then css js format string
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	//private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

    
   // private final static String URL_PREFIX = "http://ti.tibet3.com/";
	
	private final static String URL_PREFIX = "http://kr.people.com.cn";
	
	
	public String templateUrl=" ";
	
	
    
    int i = 0;
   
    
    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        if (FILTERS.matcher(href).matches() || !href.startsWith(URL_PREFIX)) {  
            return false;  
        }                 
        templateUrl=href;
        return true;  
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     * 
     * @param Page
     */
    //@SuppressWarnings("deprecation")
	@Override
    public void visit(Page page) {   
            try{
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();                    
                    String html = htmlParseData.getHtml();
                    Document doc = Jsoup.parse(html);
                    Elements contents_html = doc.select(".wb_1");
                    if(!contents_html.isEmpty()){
                    String title =doc.select("h1").text();
                    String content = doc.select(".wb_1").text();
                    if(content.isEmpty()||content.equalsIgnoreCase(title)) content = doc.select(".wb_4 p").text();                   
                    String timeofpublish =doc.select("#p_publishtime").text();
                   // String domainname =templateUrl;
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
                    
                    
                    //http://kr.people.com.cn/NMediaFile/2014/0917/FOREIGN201409170910000504682411330.jpg
                   
                    Elements E_image=doc.select("table tbody img");
                    
                    ArrayList<String> imageUrls=new ArrayList<String>();
                 
                    Iterator<Element> it=E_image.iterator();
                  
                    for(int j=0;it.hasNext();j++)
                    
	                  //  if(it.hasNext())
	                    {
	                    String imageAdress=URL_PREFIX+it.next().attr("src");
	                   // System.out.println(imageAdress);
	                    
	                    String extension = imageAdress.substring(imageAdress.lastIndexOf("."));
	                    String imageFileName = pinyin + dateOfPublish.getTime() +"number"+j+ extension;
	                    @SuppressWarnings("deprecation")
	                    String path="C:\\j\\NLP\\XHSDATA\\"+language+"\\"+chinesename+"\\"+(dateOfPublish.getYear()+1900)+"\\"+(dateOfPublish.getMonth()+1)+"\\"+dateOfPublish.getDate();
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



