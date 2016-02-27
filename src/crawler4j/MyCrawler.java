package crawler4j;

//import java.io.FileOutputStream;
//regular expression java class
import java.util.regex.Pattern;

import languageXMLParse.XMLConverter;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MyCrawler extends WebCrawler {
	// set pattern's regular expression, use for selection
	// regular expression:
	// .*(\\.(css|js|bmp|gif|jpe?g - 1. "."  match any character except new line \n 2. * match many times 3. e in front of ? can be 0 or 1 time, $ ending character
	// \\. first escape any one character except \n behind \, then css js format string
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	// url prefix rule, below is tibetan's wikipage.
	// private final static String URL_PREFIX = "http://ti.tibet3.com/";

	// Korean website prefix
	private final static String URL_PREFIX = "http://kr.people.com.cn/";

	public String templateUrl=" ";

    int i = 0;

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * below code is to set crawl logic, to limit which URL can or can't access.
     */
    @Override
    public boolean shouldVisit(WebURL url) {
    		// turn url string into all lower case.
            String href = url.getURL().toLowerCase();
            // if filters all match(css, js class suffix no need) or not starting with URL_PREFIX, return false
            if (FILTERS.matcher(href).matches() || !href.startsWith(URL_PREFIX)) {
                return false;
            }
            // otherwise returns true
            templateUrl=href;
            return true;
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     * current page fetched, then execute method visit
     *
     * @param Page's instance object
     */
    //@SuppressWarnings("deprecation")
	@Override
	public void visit(Page page) {
    	try {
            // obtain the data after parse, if it is a instance of HtmlParseData
            if (page.getParseData() instanceof HtmlParseData) {
            	// force convert to HtmlParseData from the data obtained from object page.
            	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                // get the html from object htmlParseData
                String html = htmlParseData.getHtml();
                // jsoup parse html into document node type, through the tag ref in html (HTML including a <base href> tag)
                Document doc = Jsoup.parse(html);

                // in html file, select the node by element
                // contents_html, get korean context location div, and then merge all paragraph.
                Elements contents_html = doc.select(".wb_1");
                if (!contents_html.isEmpty()) {
                    // content body
                    String content = contents_html.select("p").text();
                    // header
                    String title = doc.select("h1").text();
                    // publish time
                    String timeofpublish =doc.select("#p_publishtime").text();
                    // URL domain address
                    String domainname =templateUrl;
                    // provider website
                    String provider= URL_PREFIX;
                    // author
                    String author=doc.select(".wb_2").text();

                    System.out.println("#"+i+" record\n");
                    System.out.println("Title:"+title+"\n");
                    System.out.println("Author:"+author+"\n");
                    System.out.println("Publish Time:"+timeofpublish+"\n");
                    System.out.println("Content:"+content+"\n");
                    System.out.println("Domain Name:"+domainname+"\n");
                    System.out.println("Provider:"+provider+"\n");

                    i++;

                    // add additional info
                    String chinesename="人民网朝语版";
                    String pinyin="rmwcyb";
                    String webencoding="utf-8";
                    String language="朝文";
                    String encodingtype="utf-8";
                    // convert into xml
                    XMLConverter xc=new  XMLConverter();
                    xc.ConvertToXML(title,author,timeofpublish,content,domainname,provider,chinesename,pinyin,webencoding,language,encodingtype);
                }
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}                   
