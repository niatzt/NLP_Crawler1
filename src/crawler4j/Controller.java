package crawler4j;

import languageXMLParse.site.WwwcsucsbeduParse;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import static config.Constants.*;

public class Controller {
	public static void main(String[] args) throws Exception {
		//String crawlStorageFolder = "/data/crawl/root111";
		String crawlStorageFolder = DATA_PATH;
		// define number of crawler
		int numberOfCrawlers = 1;

		// create crawler's configuration object
		CrawlConfig config = new CrawlConfig();
		// set crawler's storage folder
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawler.
         */
        // create PageFetcher object and pass config info
        PageFetcher pageFetcher = new PageFetcher(config);
        // create RobotstxtConfig and RobotstxtServer object.
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // init crawler's controller and pass in config file object, page access object and Robotstxt server object 
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         * Need to add some seed URLs into server for the crawler init page.
         * Here set 3 URLs, they are in wikipages, 1. Tibetan Ali area 2. General Tibetan 3. Another Tibetan URL
         */
        // Korean website http://www.hljxinwen.cn/ Heilongjiang news korean version
        controller.addSeed("http://www.cs.ucsb.edu/people");
        // begin to crawl data, will block the current thread until the completion of the crawler thread and then execute the next line             
        controller.start(WwwcsucsbeduParse.class, numberOfCrawlers);
        System.out.println("ucsb:www.cs.ucsb.edu crawling process has completed!");
    }
}