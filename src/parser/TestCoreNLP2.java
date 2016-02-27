package parser;

import java.util.List;
//import java.util.Map;
import java.util.Properties;

//import edu.stanford.nlp.dcoref.CorefChain;
//import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
//import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.RelationMentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
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
//import edu.stanford.nlp.util.StringUtils;
public class TestCoreNLP2 {
    public static void main(String[] args) {

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution

  Properties props = new Properties();

  props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref,relation");
  //props.put("regexner.mapping", "org/foo/resources/jg-regexner.txt");
  //#nfl.relation.model =  /scr/nlp/data/ldc/LDC2009E112/Machine_Reading_P1_NFL_Scoring_Training_Data_V1.2/models/nfl_relation_model.ser
  StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

  

  // read some text in the text variable

 // String text = "Add your text here. I am roger.I am living in NewYork.Jack works in hospital.Lili was in Beijing";
String text= "Barack Obama lives in America. Obama works for the Federal Goverment.";
  

  // create an empty Annotation just with the given text

  Annotation document = new Annotation(text);

  

  // run all Annotators on this text

  pipeline.annotate(document);

  

  // these are all the sentences in this document

  // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types

  List<CoreMap> sentences = document.get(SentencesAnnotation.class);



  

  for(CoreMap sentence: sentences) {

    // traversing the words in the current sentence

    // a CoreLabel is a CoreMap with additional token-specific methods

    for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

      // this is the text of the token

      String word = token.get(TextAnnotation.class);

      // this is the POS tag of the token

      String pos = token.get(PartOfSpeechAnnotation.class);

      // this is the NER label of the token

      String ne = token.get(NamedEntityTagAnnotation.class);     

      

      System.out.println(word+","+pos+","+ne);

    }



    // this is the parse tree of the current sentence

  //Tree tree = sentence.get(TreeAnnotation.class);



    // this is the Stanford dependency graph of the current sentence

    //SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

  }



  // This is the coreference link graph

  // Each chain stores a set of mentions that link to each other,

  // along with a method for getting the most representative mention

  // Both sentence and token offsets start at 1!

  //Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
  
  // try entity relation.
 
  for(CoreMap s: document.get(CoreAnnotations.SentencesAnnotation.class)){
      System.out.println("For sentence " + s.get(CoreAnnotations.TextAnnotation.class));
      List<RelationMention> rls  = s.get(RelationMentionsAnnotation.class);
      for(RelationMention rl: rls){
        System.out.println("!!!!relation:"+rl.toString());
      }
  
/* 
 * 
  List<RelationMention> relations=document.get( MachineReadingAnnotations.RelationMentionsAnnotation.class);  
   if(relations!=null)
  for(RelationMention relation : relations)
  {if(relation!=null)
	 System.out.println("!!!!relation:"+relation.toString());
  }*/
  // relations=null;
   
}
    }
}
    
/*    try{
        Properties props = StringUtils.argsToProperties(args);
        props.setProperty("annotators", "tokenize,ssplit,lemma,pos,parse,ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP();
        String sentence = "Barack Obama lives in America. Obama works for the Federal Goverment.";
        Annotation doc = new Annotation(sentence);
        pipeline.annotate(doc);
        RelationExtractorAnnotator r = new RelationExtractorAnnotator(props);
        r.annotate(doc);
        for(CoreMap s: doc.get(CoreAnnotations.SentencesAnnotation.class)){
          System.out.println("For sentence " + s.get(CoreAnnotations.TextAnnotation.class));
          List<RelationMention> rls  = s.get(RelationMentionsAnnotation.class);
          for(RelationMention rl: rls){
            System.out.println(rl.toString());
          }
        }
      }catch(Exception e){
        e.printStackTrace();
      }
    */
    


