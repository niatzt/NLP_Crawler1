package parser;

import java.util.List;
//import java.util.Map;
import java.util.Properties;

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
public class TestCoreNLP3 {
    public static void main(String[] args) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
    	Properties props = new Properties();

    	props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, relation");

    	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    	// read some text in the text variable

    	//  String text = "Add your text here. I am roger. I lives in NewYork. Jack works in hospital. Lili study in Beijing";

    	// String text = "Add your text here";
    	//String text ="Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at California State University Long Beach. He earned his PhD from the department of Mathematics at the University of Connecticut in 2000. His doctoral research was in the area of computability theory (recursion theory) and dealt with lattice embeddings into the computably enumerable degrees. He also worked on distributed algorithms. In 1992, he received a BS degree in mathematics from the University of Tuebingen in Germany. After finishing his PhD in 2000, Burkhard came to University of California, Los Angeles (UCLA) as an adjunct assistant professor in the program in computing. At UCLA he received the Sorgenfrey Distinguished Teaching Award for visiting faculty members for outstanding achievements in teaching by the department of Mathematics. Currently, Burkhard teaches a broad range of graduate and undergraduate courses concentrating on computer security, net-centric computing, and distributed systems. Burkhard's research interests are distributed computing, distributed algorithms, computer security, and transportation network modeling and optimization. Burkhard also serves as a reviewer and a member of program committees on a number of national and international conferences.";
    	// String text ="Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at"
    	//            +" California State University Long Beach. He earned his PhD from the department of Mathematics at the University "
    	//		  +"of Connecticut in 2000. ";
    	String text= "Barack Obama lives in America. Obama works for the Federal Goverment.";
  
    	// create an empty Annotation (annotation handling object) just with the given text

    	Annotation document = new Annotation(text);
    	// run all Annotators (one annotation represents one sentence handling) on this text
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
    			System.out.print(word+"("+pos+")["+ne+"]");
    		}
    		System.out.println("---");
  
    		// get entity relation
    		List<RelationMention> relations=sentence.get(RelationMentionsAnnotation.class);
    		if(relations!=null)
    			for(RelationMention relation : relations){
			    	if(relation!=null)
			    	/*relation.getExtentString();
			        relation.attributeMap();
			        relation.getArgNames();
			        relation.getEntityMentionArgs();*/
			    	if(!relation.getType().equalsIgnoreCase("_NR")){
				        System.out.println("relation.getType():"+relation.getType());
				        for(EntityMention em : relation.getEntityMentionArgs()){     	
				        	System.out.println("EntityMention["+em.getType()+"]("+em.getValue()+")");
				        }
			    	}
			    	/*System.out.println("self provide method:relation:"+relation.toString());
			    	
			    	System.out.println("getValue():"+relation.getEntityMentionArgs().);
			    	System.out.println("getFullValue():"+relation.getFullValue());
			    	System.out.println("getSignature():"+relation.getSignature());
			    	System.out.println("getExtentString():"+relation.getExtentString());
			    	System.out.println("getSignature():"+relation.getSignature());*/
			  	// System.out.println("self provide method:relation:"+relation.toString());
    			}

    		// this is the parse tree of the current sentence

    		//Tree tree = sentence.get(TreeAnnotation.class);
    		//  System.out.println("tree:"+tree.toString());

    		// this is the Stanford dependency graph of the current sentence

    		//SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
    		//    System.out.println("dependencies:"+dependencies.toString());            
    	}

    	// This is the coreference link graph

    	// Each chain stores a set of mentions that link to each other,

    	// along with a method for getting the most representative mention

    	// Both sentence and token offsets start at 1!

    	//Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
    	//  System.out.println("graph:"+graph.toString()); 
  
  
	/*  //entity relation try
	  for(CoreMap s: document.get(CoreAnnotations.SentencesAnnotation.class)){
	      System.out.println("For sentence " + s.get(CoreAnnotations.TextAnnotation.class));
	      List<RelationMention> rls  = s.get(RelationMentionsAnnotation.class);
	      for(RelationMention rl: rls){
	        System.out.println("system provide method:relation:"+rl.toString());
	      }
	      }
	  */
    }
}
