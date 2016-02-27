package languageXMLParse;

import java.io.File;   
import java.io.FileWriter;   
import java.io.IOException;   
//import java.io.StringReader;
import java.io.Writer;   
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;   
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;   
import org.dom4j.DocumentException;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
import org.dom4j.io.SAXReader;   
import org.dom4j.io.XMLWriter;   
import static config.Constants.*;

public class XMLConverter {

	// define some XML nodes
	// XML file	
	String file=" ";
	//
	String domainname=" ";
	String chinesename=" ";
	String pinyin=" ";
	String webencoding=" ";
	String language=" ";
	String encodingtype=" ";
	String registrationnumber=" ";
	String provider=" ";
	String corpustype=" ";
	String filename=" ";
	String title=" "; 
	String subtitle=" ";
	String author=" ";
	String timeofpublish=" ";
	String timeofdownload=" ";
	String url=" ";
	String clicktimes=" ";
	String codeofclassification=" ";
	String nameofclassification=" ";
	String methodofclassification=" ";
	String keywordsofclassification=" ";
	String content=" ";
	String column=" ";
	String[] imageUrls=new String[]{};

	/**  
	 *   
	 * save all data into xml
	 * @param none
	 * @return return xml encapsulated string
	 * 
	 */ 
	// no param, just for template
	protected void ConvertToXML() {
		// set the save time as the download time
		if(timeofdownload==" "){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			//System.out.println(df.format(new Date()));
			timeofdownload=df.format(new Date()).toString();
		}
		
		//create xml file
		Document document = DocumentHelper.createDocument();
		//create xml doc template, save data, if no data, leave it as blank
		Element E_file=document.addElement("file");   
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_chinesename= E_file.addElement("chinesename");		
		E_chinesename.setText(chinesename);
		Element E_pinyin= E_file.addElement("pinyin");		
		E_pinyin.setText(pinyin);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_registrationnumber= E_file.addElement("registrationnumber");		
		E_registrationnumber.setText(registrationnumber);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_corpustype= E_file.addElement("corpustype");		
		E_corpustype.setText(corpustype);
		Element E_filename= E_file.addElement("filename");		
		E_filename.setText(filename);
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_subtitle= E_file.addElement("subtitle");		
		E_subtitle.setText(subtitle);
		Element E_author= E_file.addElement("author");		
		E_author.setText(author);
		Element E_timeofpublish= E_file.addElement("timeofpublish");		
		E_timeofpublish.setText(timeofpublish);
		Element E_timeofdownload= E_file.addElement("timeofdownload");		
		E_timeofdownload.setText(timeofdownload);
		Element E_url= E_file.addElement("url");		
		E_url.setText(url);
		Element E_clicktimes= E_file.addElement("clicktimes");		
		E_clicktimes.setText(clicktimes);
		Element E_codeofclassification= E_file.addElement("codeofclassification");		
		E_codeofclassification.setText(codeofclassification);
		Element E_nameofclassification= E_file.addElement("nameofclassification");		
		E_nameofclassification.setText(nameofclassification);
		Element E_methodofclassification= E_file.addElement("methodofclassification");		
		E_methodofclassification.setText(methodofclassification);
		Element E_keywordsofclassification= E_file.addElement("keywordsofclassification");		
		E_keywordsofclassification.setText(keywordsofclassification);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("column");		
		E_column.setText(column);

		try {   
			Writer fileWriter=new FileWriter(DATA_PATH+File.separator+XML_SUB_DIR+File.separator+pinyin+timeofdownload+".xml");
			XMLWriter xmlWriter=new XMLWriter(fileWriter);   
			xmlWriter.write(document);   
			xmlWriter.close();
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}
	}

	/**  
	 *   
	 * save data into xml
	 * @param no downlaod time
	 * @return xml encapsulated string
	 * 
	 */ 
	 public void ConvertToXML(String title,String author,String timeofpublish,String content,String domainname,String provider,String chinesename,String pinyin,String webencoding,String language,String encodingtype) {
		if(timeofdownload==" "){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			//System.out.println(df.format(new Date()));
			timeofdownload=df.format(new Date()).toString();
		}

		Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_chinesename= E_file.addElement("chinesename");		
		E_chinesename.setText(chinesename);
		Element E_pinyin= E_file.addElement("pinyin");		
		E_pinyin.setText(pinyin);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_registrationnumber= E_file.addElement("registrationnumber");		
		E_registrationnumber.setText(registrationnumber);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_corpustype= E_file.addElement("corpustype");		
		E_corpustype.setText(corpustype);
		Element E_filename= E_file.addElement("filename");		
		E_filename.setText(filename);
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_subtitle= E_file.addElement("subtitle");		
		E_subtitle.setText(subtitle);
		Element E_author= E_file.addElement("author");		
		E_author.setText(author);
		Element E_timeofpublish= E_file.addElement("timeofpublish");		
		E_timeofpublish.setText(timeofpublish);
		Element E_timeofdownload= E_file.addElement("timeofdownload");		
		E_timeofdownload.setText(timeofdownload);
		Element E_url= E_file.addElement("url");		
		E_url.setText(url);
		Element E_clicktimes= E_file.addElement("clicktimes");		
		E_clicktimes.setText(clicktimes);
		Element E_codeofclassification= E_file.addElement("codeofclassification");		
		E_codeofclassification.setText(codeofclassification);
		Element E_nameofclassification= E_file.addElement("nameofclassification");		
		E_nameofclassification.setText(nameofclassification);
		Element E_methodofclassification= E_file.addElement("methodofclassification");		
		E_methodofclassification.setText(methodofclassification);
		Element E_keywordsofclassification= E_file.addElement("keywordsofclassification");		
		E_keywordsofclassification.setText(keywordsofclassification);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("column");		
		E_column.setText(column);

		try {
			Writer fileWriter=new FileWriter(DATA_PATH+File.separator+XML_SUB_DIR+File.separator+pinyin+timeofdownload+".xml");
			XMLWriter xmlWriter=new XMLWriter(fileWriter);   
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	 }
		
	 /**  
		*   
		* @param 1. download time 2. multiple photo address
		* @return xml encapsulated string
		* @throws ParseException
		*
		*/ 
	 public void ConvertToXML(String title,String author,String timeofpublish,String content,
        		   String domainname,String provider,String chinesename,String pinyin,String webencoding,
        		   String language,String encodingtype,String timeofdownload,ArrayList<String> imageUrls) throws ParseException {
		if(timeofdownload==" "){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			//System.out.println(df.format(new Date()));
			timeofdownload=df.format(new Date()).toString();
		}
		
		Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_chinesename= E_file.addElement("chinesename");		
		E_chinesename.setText(chinesename);
		Element E_pinyin= E_file.addElement("pinyin");		
		E_pinyin.setText(pinyin);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_registrationnumber= E_file.addElement("registrationnumber");		
		E_registrationnumber.setText(registrationnumber);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_corpustype= E_file.addElement("corpustype");		
		E_corpustype.setText(corpustype);
		Element E_filename= E_file.addElement("filename");		
		E_filename.setText(filename);
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_subtitle= E_file.addElement("subtitle");		
		E_subtitle.setText(subtitle);
		Element E_author= E_file.addElement("author");		
		E_author.setText(author);
		Element E_timeofpublish= E_file.addElement("timeofpublish");		
		E_timeofpublish.setText(timeofpublish);
		Element E_timeofdownload= E_file.addElement("timeofdownload");		
		E_timeofdownload.setText(timeofdownload);
		
		// create node, save multiple photo address
		Iterator<String> it=imageUrls.iterator();
        int j=0;
		while(it.hasNext()){
			Element E_Url= E_file.addElement("imageUrl"+j);	
			E_Url.setText((String)it.next());
			j++;
		}

		Element E_url= E_file.addElement("url");		
		E_url.setText(url);
		Element E_clicktimes= E_file.addElement("clicktimes");		
		E_clicktimes.setText(clicktimes);
		Element E_codeofclassification= E_file.addElement("codeofclassification");		
		E_codeofclassification.setText(codeofclassification);
		Element E_nameofclassification= E_file.addElement("nameofclassification");		
		E_nameofclassification.setText(nameofclassification);
		Element E_methodofclassification= E_file.addElement("methodofclassification");		
		E_methodofclassification.setText(methodofclassification);
		Element E_keywordsofclassification= E_file.addElement("keywordsofclassification");		
		E_keywordsofclassification.setText(keywordsofclassification);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("column");		
		E_column.setText(column);
			
		try {   
			SimpleDateFormat dfpt = new SimpleDateFormat("HH:mm, MMMMM dd, yyyy",Locale.ENGLISH);
            Date dateOfPublish=dfpt.parse(timeofpublish);
            // System.out.println("Get format check"+dateOfPublish+"\n");

            @SuppressWarnings("deprecation")
            String path=DATA_PATH+"/XHSDATA/"+language+File.separator+chinesename+File.separator+(dateOfPublish.getYear()+1900)+File.separator+(dateOfPublish.getMonth()+1)+File.separator+dateOfPublish.getDate();
            File fp = new File(path);  	                          
            if (!fp.exists()) {  
            	fp.mkdirs();  
            }
            Writer fileWriter=new FileWriter(path+"\\"+pinyin+dateOfPublish.getTime()+".xml");
            XMLWriter xmlWriter=new XMLWriter(fileWriter);   
            xmlWriter.write(document);   
            xmlWriter.close();   
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}
	 }

	 /**  
	 *   
	 * @param 1. download time 2. multiple photo address 3. download date format template
	 * @return xml encapsulated string
	 * @throws ParseException 
	 * 
	 */ 
	 public void ConvertToXML(String title,String author,String timeofpublish,String content,
			 String domainname,String provider,String chinesename,String pinyin,String webencoding,
			 String language,String encodingtype,String timeofdownload,ArrayList<String> imageUrls,String publishtimeFormation) throws ParseException {
		if(timeofdownload==" "){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			timeofdownload=df.format(new Date()).toString();
		}
        Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_chinesename= E_file.addElement("chinesename");		
		E_chinesename.setText(chinesename);
		Element E_pinyin= E_file.addElement("pinyin");		
		E_pinyin.setText(pinyin);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_registrationnumber= E_file.addElement("registrationnumber");		
		E_registrationnumber.setText(registrationnumber);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_corpustype= E_file.addElement("corpustype");		
		E_corpustype.setText(corpustype);
		Element E_filename= E_file.addElement("filename");		
		E_filename.setText(filename);
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_subtitle= E_file.addElement("subtitle");		
		E_subtitle.setText(subtitle);
		Element E_author= E_file.addElement("author");		
		E_author.setText(author);
		Element E_timeofpublish= E_file.addElement("timeofpublish");		
		E_timeofpublish.setText(timeofpublish);
		Element E_timeofdownload= E_file.addElement("timeofdownload");		
		E_timeofdownload.setText(timeofdownload);

		Iterator<String> it=imageUrls.iterator();
        int j=0;
		while(it.hasNext()){
			Element E_Url= E_file.addElement("imageUrl"+j);	
			E_Url.setText((String)it.next());
			j++;
		}

		Element E_url= E_file.addElement("url");		
		E_url.setText(url);
		Element E_clicktimes= E_file.addElement("clicktimes");		
		E_clicktimes.setText(clicktimes);
		Element E_codeofclassification= E_file.addElement("codeofclassification");		
		E_codeofclassification.setText(codeofclassification);
		Element E_nameofclassification= E_file.addElement("nameofclassification");		
		E_nameofclassification.setText(nameofclassification);
		Element E_methodofclassification= E_file.addElement("methodofclassification");		
		E_methodofclassification.setText(methodofclassification);
		Element E_keywordsofclassification= E_file.addElement("keywordsofclassification");		
		E_keywordsofclassification.setText(keywordsofclassification);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("column");		
		E_column.setText(column);

		try {   
			SimpleDateFormat dfpt = new SimpleDateFormat(publishtimeFormation);
			Date dateOfPublish=dfpt.parse(timeofpublish);
			// System.out.println("get format check"+dateOfPublish+"\n");

			@SuppressWarnings("deprecation")
			String path=DATA_PATH+"/XHSDATA/"+language+"/"+chinesename+"/"+(dateOfPublish.getYear()+1900)+"/"+(dateOfPublish.getMonth()+1)+"/"+dateOfPublish.getDate();
			File fp = new File(path);  	                          
			if (!fp.exists()) {  
				fp.mkdirs();  
			}

			Writer fileWriter=new FileWriter(path+"\\"+pinyin+dateOfPublish.getTime()+".xml");
			XMLWriter xmlWriter=new XMLWriter(fileWriter);   
			xmlWriter.write(document);   
			xmlWriter.close();   
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}   	
	 }

	 /**  
	  *
	  * @param 1. download time 2. multiple photo address 3. download date format template
	  * @return xml encapsulated string
	  * @throws ParseException 
	  *
	  */ 
	 public void saveAsXML(String title, String content, String workplace,
			 String domainname, String provider, String language,
			 String encodingtype) {
		Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("workplace");		
		E_column.setText(workplace);
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);

		try {   
	       //@SuppressWarnings("deprecation")
	       String path=DATA_PATH+File.separator+PINFO_SUB_DIR+File.separator+language+File.separator+workplace+File.separator;
	       File fp = new File(path);  	                          
	       if (!fp.exists()) {  
	    	   fp.mkdirs();  
	       }                     
	       Writer fileWriter=new FileWriter(path+title+".xml");
	       XMLWriter xmlWriter=new XMLWriter(fileWriter);   
	       xmlWriter.write(document);   
	       xmlWriter.close();   
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}  
	 } 
		
	 /**
       * get the value of driver, url, username, password
       * @param args
       * @throws IOException
       * @throws DocumentException
       */
	 public Map<String,String> readFromXML() throws DocumentException{
		 Map<String,String> map=new HashMap<String,String>();
		 SAXReader reader = new SAXReader();
		 File fdir=new File(DATA_PATH+File.separator+PINFO_SUB_DIR+"/English/csulb");
		 if (fdir.isDirectory()){
			 for(String filename:fdir.list()){
				 System.out.println(filename);
			 }
		 }
		 Document document = reader.read(DATA_PATH+File.separator+PINFO_SUB_DIR+"/English/csulb/Dr. Burkhard Englert.xml");
         Element root=document.getRootElement();
         String title= root.elementText("title");
         String content= root.elementText("content");
         String workplace= root.elementText("workplace");
         String provider= root.elementText("provider");
         String domainname= root.elementText("domainname");
         String language= root.elementText("language");
         String encodingtype= root.elementText("encodingtype");
         map.put("title", title);
         map.put("content", content);
         map.put("workplace", workplace);
         map.put("provider", provider);
         map.put("domainname", domainname);
         map.put("language", language);
         map.put("encodingtype", encodingtype);

         System.out.println("\ntitle:"+title+"\ncontent"+content+"\nworkplace"+ workplace+"\nprovider"+provider+"\ndomainname"+ domainname+"\nlanguage"+ language+"\nencodingtype"+encodingtype);	
         return map;			          
		  // Element titleElement = (Element)document.selectSingleNode("/file/title");
		  /*         Element urlElement = (Element)document.selectSingleNode("/configuration/environments/environment/dataSource/property[@name='url']");
		   Element usernameElement = (Element)document.selectSingleNode("/configuration/environments/environment/dataSource/property[@name='username']");
		   Element passwordElement = (Element)document.selectSingleNode("/configuration/environments/environment/dataSource/property[@name='password']");*/
		 //  String title = titleElement.attributeValue("value");
		/*	           String url = urlElement.attributeValue("value");
			           String username = usernameElement.attributeValue("value");
			           String password = passwordElement.attributeValue("value");*/
	 }

	 /**
	  * by specific person's xml file path to get conent of xml, save into map
	  * @param String path
	  * @return content string
	  * @throws DocumentException 
     */
	 public static Map<String,String> readFromXML(String path) throws DocumentException{
          Map<String,String> map=new HashMap<String,String>();
          SAXReader reader = new SAXReader();
		         
	      /*    File fdir=new File("lib/PersonInf_DATA/English/csulb");
	          if (fdir.isDirectory()){
	        	  for(String filename:fdir.list()){
	        		  System.out.println(filename);
	        	  }
	          }*/
          Document document ;
          try{
        	  document = reader.read(path);
          }catch(Exception e){
        	  System.out.println( e.getMessage());
        	  return null;
          }
          Element root=document.getRootElement();
          String title= root.elementText("title");
          String content= root.elementText("content");
          String workplace= root.elementText("workplace");
          String provider= root.elementText("provider");
          String domainname= root.elementText("domainname");
          String language= root.elementText("language");
          String encodingtype= root.elementText("encodingtype");
          map.put("title", title);
          map.put("content", content);
          map.put("workplace", workplace);
          map.put("provider", provider);
          map.put("domainname", domainname);
          map.put("language", language);
          map.put("encodingtype", encodingtype);
          System.out.println("\nTitle:"+title+"\nContent:"+content+"\nWorkPlace:"+ workplace+"\nProvider:"+provider+"\nDomainName:"+ domainname+"\nLanguage:"+ language+"\nEncodingType:"+encodingtype);	
          return map;			          
	 }
	
	 public static void main(String[] args) throws DocumentException {
		 XMLConverter x=new XMLConverter();
		 x.readFromXML();
	 }

	 public static void saveDualedContenttoXML(String oldPath,String title,String content,
			 String workplace, String domainname,String provider,
			 String language,String encodingtype,String dualedContent,List<StringBuffer> entityRelationShips) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("workplace");		
		E_column.setText(workplace);
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_dualedContent= E_file.addElement("dualedContent");		
		E_dualedContent.setText(dualedContent);
		if (entityRelationShips.isEmpty()){
			System.out.println("empty");
		}
		if (!entityRelationShips.isEmpty()){				
			int i=1;
			for(StringBuffer entityRelationShip:entityRelationShips){				
				Element E_entityRelationShip= E_file.addElement("entityRelationShip"+i);		
				E_entityRelationShip.setText(entityRelationShip.toString());
			}
		}

		try {   
			//@SuppressWarnings("deprecation")
			String path=DATA_PATH+File.separator+DUAL_PINFO_SUB_DIR+File.separator+language+File.separator+workplace+File.separator;
			File fp = new File(path);  	                          
			if (!fp.exists()) {  
				fp.mkdirs();  
			}                     
			Writer fileWriter=new FileWriter(path+title+".xml");
			XMLWriter xmlWriter=new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();   
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}
	}

	public static void saveDualedContentAndPersonAttributetoXML(String oldPath,String title,String content,
            String workplace, String domainname,String provider,
            String language,String encodingtype,String dualedContent,List<StringBuffer> entityRelationShips,Map<String,String> personAttributeMap) {
		Document document = DocumentHelper.createDocument();
		Element E_file=document.addElement("file");   
		Element E_title= E_file.addElement("title");		
		E_title.setText(title);
		Element E_content= E_file.addElement("content");		
		E_content.setText(content);
		Element E_column= E_file.addElement("workplace");		
		E_column.setText(workplace);
		Element E_domainname=E_file.addElement("domainname");  
		E_domainname.setText(domainname);
		Element E_provider= E_file.addElement("provider");		
		E_provider.setText(provider);
		Element E_language= E_file.addElement("language");		
		E_language.setText(language);
		Element E_encodingtype= E_file.addElement("encodingtype");		
		E_encodingtype.setText(encodingtype);
		Element E_dualedContent= E_file.addElement("dualedContent");		
		E_dualedContent.setText(dualedContent);
		if (entityRelationShips.isEmpty()){
			System.out.println("empty");
		}
		if (!entityRelationShips.isEmpty()){				
			int i=1;
			for(StringBuffer entityRelationShip:entityRelationShips){				
				Element E_entityRelationShip= E_file.addElement("entityRelationShip"+i);		
				E_entityRelationShip.setText(entityRelationShip.toString());
			}
		}
		if(!personAttributeMap.isEmpty()){
			for(String key:personAttributeMap.keySet())	{
				Element E_dataInMap= E_file.addElement(key);		
				E_dataInMap.setText(personAttributeMap.get(key));
			}	
		}
		try {   
			//@SuppressWarnings("deprecation")
			String path=DATA_PATH+File.separator+ATTR_DUAL_PINFO_SUB_DIR+File.separator+language+File.separator+workplace+File.separator;
			File fp = new File(path);  	                          
			if (!fp.exists()) {  
				fp.mkdirs();  
			}                     
			Writer fileWriter=new FileWriter(path+title+".xml");
			XMLWriter xmlWriter=new XMLWriter(fileWriter);   
			xmlWriter.write(document);   
			xmlWriter.close();   
		} catch (IOException e) {   
			System.out.println(e.getMessage());
		}
	}
}
