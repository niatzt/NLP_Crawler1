package parser;

import static config.Constants.*;
import java.io.File;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.DocumentException;

import languageXMLParse.XMLConverter;
//import edu.stanford.nlp.dcoref.CorefChain;
//import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.RelationMentionsAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
//import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.semgraph.SemanticGraph;
//import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
//import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
public class Pre_ProcessingByCoreNLP {
	static Properties props;
	static StanfordCoreNLP pipeline;
	String text = "Barack Obama lives in America. Obama works for the Federal Goverment.";

	public void initialCoreNLP() {

		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, relation");
		pipeline = new StanfordCoreNLP(props);
		// read some text in the text variable
		//  String text = "Add your text here. I am roger. I lives in NewYork. Jack works in hospital. Lili study in Beijing";
		// String text = "Add your text here";
		//String text ="Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at California State University Long Beach. He earned his PhD from the department of Mathematics at the University of Connecticut in 2000. His doctoral research was in the area of computability theory (recursion theory) and dealt with lattice embeddings into the computably enumerable degrees. He also worked on distributed algorithms. In 1992, he received a BS degree in mathematics from the University of Tuebingen in Germany. After finishing his PhD in 2000, Burkhard came to University of California, Los Angeles (UCLA) as an adjunct assistant professor in the program in computing. At UCLA he received the Sorgenfrey Distinguished Teaching Award for visiting faculty members for outstanding achievements in teaching by the department of Mathematics. Currently, Burkhard teaches a broad range of graduate and undergraduate courses concentrating on computer security, net-centric computing, and distributed systems. Burkhard's research interests are distributed computing, distributed algorithms, computer security, and transportation network modeling and optimization. Burkhard also serves as a reviewer and a member of program committees on a number of national and international conferences.";
		// String text ="Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at"
		//        	+" California State University Long Beach. He earned his PhD from the department of Mathematics at the University "
		//		  	+"of Connecticut in 2000. ";
	}

    public Map<String,List<String>> getAllFillName() {
    	// String peopleCVpath="lib/PersonInf_DATA/English/csulb";
    	String peopleCVpath=DATA_PATH+File.separator+PINFO_SUB_DIR+"/English";
    	// assume the first layer in school name and second layer is professor name in the file path.
    	HashMap<String, List<String>> fileNameMap;
    	File fdir=new File(peopleCVpath);//lib/PersonInf_DATA/English ||fileNameFirstDirMap
    	fileNameMap=new HashMap<String,List<String>>();
    	if (fdir.isDirectory()){ 	// if the first layer file is directory (school grade directory)
    		for(String universityNamedir:fdir.list()){
    			List<String> fileNameList= new ArrayList<String>();	// create inner map to put file name 
    			fileNameMap.put(universityNamedir,fileNameList);
    			File sub_fdir=new File(peopleCVpath+"/"+universityNamedir);
    			if (sub_fdir.isDirectory()){
        			 for(String filename:sub_fdir.list()){
        				 fileNameList.add(filename);// add to list, every professor introduced file name in the school.
        				 System.out.println(filename);
        			 }
    			}
    		}       		
    	}	
    	return fileNameMap;
    }
    
    public List<StringBuffer> preProcessingData(String contentText){
    	List<StringBuffer> dualedlist=new ArrayList<StringBuffer>();
    	StringBuffer dualedContent=new StringBuffer();
    	// StringBuffer[] foundEntityRelations=null;
    	StringBuffer relationInf =null;
    	dualedlist.add(dualedContent);
    	// create an empty Annotation ( annotation handling object )just with the given text
    	Annotation document = new Annotation(contentText);
    	// run all Annotators(one annotation represents one sentence handling) on this text
    	pipeline.annotate(document);
    	// these are all the sentences in this document
    	// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    	List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    	for(CoreMap sentence: sentences) {
    		// traversing the words in the current sentence
    		System.out.println("current sentence:"+sentence.toString());
    		// a CoreLabel is a CoreMap with additional token-specific methods (token)
    		for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
    			//System.out.println("current token:"+token.toString());
    			// this is the text of the token
    			String word = token.get(TextAnnotation.class);
    			// this is the POS tag of the token
    			String pos = token.get(PartOfSpeechAnnotation.class);
    			// this is the NER label of the token (naming entity identity)
    			String ne = token.get(NamedEntityTagAnnotation.class);     
    			// System.out.println(word+","+pos+","+ne);
    			System.out.print(word+"("+pos+")["+ne+"]"+" ");
    			dualedContent.append(word+"("+pos+")["+ne+"]"+" ");
    		}
    		System.out.println("|||");
    		dualedContent.append("|||");
    		// get relation
    		List<RelationMention> relations=sentence.get(RelationMentionsAnnotation.class);
    		if(relations!=null)
    			for(RelationMention relation : relations){
    				if(relation!=null)
				    	/*relation.getExtentString();
				        relation.attributeMap();
				        relation.getArgNames();
				        relation.getEntityMentionArgs();*/
				    	relationInf = new StringBuffer();
			    	if(!relation.getType().equalsIgnoreCase("_NR")){
			    		System.out.print("{EntityRelation:"+relation.getType());
			    		relationInf.append("{EntityRelation:"+relation.getType());
			    		for(EntityMention em : relation.getEntityMentionArgs()){     	
			    			System.out.print("EntityMention:["+em.getType()+"]("+em.getValue()+")");
			    			relationInf.append("EntityMention:["+em.getType()+"]("+em.getValue()+")");
			    		}
			    		System.out.print("}\n");
			    		dualedlist.add(relationInf); // add to list object
			    	}

			    	/*System.out.println( "Self provide Method relation:"+relation.toString());
			
			    	System.out.println("getValue():"+relation.getEntityMentionArgs().);
			    	System.out.println("getFullValue():"+relation.getFullValue());
			    	System.out.println("getSignature():"+relation.getSignature());
			    	System.out.println("getExtentString():"+relation.getExtentString());
			    	System.out.println("getSignature():"+relation.getSignature());*/
			  	// System.out.println("Self provide Method relation:"+relation.toString());
    			}
    	}
    	return  dualedlist;
    }

    public static void main(String[] args) throws DocumentException {
    	String path=DATA_PATH+File.separator+PINFO_SUB_DIR+"/English/";
        String contentText="";
        Pre_ProcessingByCoreNLP ppc=new Pre_ProcessingByCoreNLP ();

        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, relation");
        pipeline = new StanfordCoreNLP(props);

    	Map<String,List<String>> map=ppc.getAllFillName();

    	for(Object o :map.keySet()){
    		String universityName=(String)o;
    		List<String> list=map.get(universityName);

    		for(String profNameFileName:list){
    			System.out.println("lookup path:"+path+universityName+"/"+profNameFileName);
    			Map<String,String> readedMapFromFile=XMLConverter.readFromXML(path+universityName+"/"+profNameFileName);
    			if (readedMapFromFile==null){continue;}// to avoid name recognition problem in path
    			contentText=readedMapFromFile.get("content");
    			List<StringBuffer> preProcessingDataList =ppc.preProcessingData(contentText);
    			/*for(StringBuffer sb:preProcessingDataList){
    				System.out.println("sbtest:"+sb.toString());
    			}*/
    			StringBuffer dualedContentStringBuffer=preProcessingDataList.get(0);
    			preProcessingDataList.remove(0);

    			// handling person attribute 
    			Map<String,String> personAttributeMap=new HashMap<String,String>();
    			// get person name
    			personAttributeMap.put("profName", profNameFileName.substring(0,profNameFileName.length()-5 ));// remove .xml 4+1
    			// get work place
    			personAttributeMap.put("workplace", universityName);
    			// prepare content text
    			String rawcontent=contentText;
    			String dualedcontent=dualedContentStringBuffer.toString();
    			List<StringBuffer> relationsDataList=preProcessingDataList;

    			personAttributeMap=AttributeExtractor.doAttrbuteExtractor(rawcontent,dualedcontent,relationsDataList,personAttributeMap);

    			for (String key:personAttributeMap.keySet()){
    				System.out.println("personAttribute:"+key+":"+personAttributeMap.get(key));
    			}

    			XMLConverter.saveDualedContenttoXML(path+universityName+"/"+profNameFileName,
    					readedMapFromFile.get("title"),readedMapFromFile.get("content"),readedMapFromFile.get("workplace"),
    					readedMapFromFile.get("domainname"),readedMapFromFile.get("provider"),readedMapFromFile.get("language"),
    					readedMapFromFile.get("encodingtype"),dualedContentStringBuffer.toString(),preProcessingDataList);	
    			XMLConverter.saveDualedContentAndPersonAttributetoXML(path+universityName+"/"+profNameFileName,
    					readedMapFromFile.get("title"),readedMapFromFile.get("content"),readedMapFromFile.get("workplace"),
    					readedMapFromFile.get("domainname"),readedMapFromFile.get("provider"),readedMapFromFile.get("language"),
    					readedMapFromFile.get("encodingtype"),dualedContentStringBuffer.toString(),preProcessingDataList,personAttributeMap);	
    		}
    	}
    	System.out.println("pre process has completed!");
    }
}
