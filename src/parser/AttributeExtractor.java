package parser;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AttributeExtractor {
		// set pattern of regular expression for selection
		// regular expression:
		// .*(\\.(css|js|bmp|gif|jpe?g - 1. "."  match any character except new line \n 2. * match many times 3. e in front of ? can be 0 or 1 time, $ ending character
		// \\. first escape any one character except \n behind \, then css js format string
		/*private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
	                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
	                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf"
	                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
*/
		// is a Professor of 
		//is now an Assistant Professor in the Computer Engineering and Computer Science Department at Californian State University Long Beach
		//is(VBZ)[O]now(RB)[DATE]an(DT)[O]Assistant(NNP)[O]Professor(NNP)[O]in(IN)[O]the(DT)[O]Computer(NNP)[ORGANIZATION]Engineering(NNP)[ORGANIZATION]
		//and(CC)[ORGANIZATION]Computer(NNP)[ORGANIZATION]Science(NNP)[ORGANIZATION]Department(NNP)[ORGANIZATION]at(IN)[O]Californian(NN)[ORGANIZATION]State(NNP)
		//[ORGANIZATION]University(NNP)[ORGANIZATION]Long(NNP)[LOCATION]Beach(NNP)[LOCATION],
		//She is currently an associate professor in the Department of Computer Engineering and Computer Science, California State University at Long Beach.
		//He is presently Professor of both Electrical Engineering and Computer Science and Computer Engineering at California State University, Long Beach
		//Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at California State University Long Beach
		//Burkhard came to University of California, Los Angeles (UCLA) as an adjunct assistant professor in the program in computing
		//a Professor at UCSB
		//is an associate professor at
		//an associate professor at
		//is a professor, and currently the department chair, of Computer Science at the University of California
		//he was an associate professor at Washington University in St. Louis (1994-2000),
		// is an assistant professor in the Department of Computer Science at the University of California
		
		//a Ph.D. student at
		
		private final static Pattern FILTEROCCUPATION_Raw_Professor = Pattern.compile("is a Professor of|a Professor at|is a professor,|is a professor in|is presently Professor of|is a professor|a professor" 
        ,Pattern.CASE_INSENSITIVE  );
		private final static Pattern FILTEROCCUPATION_Raw_AssistantProfessor = Pattern.compile("is an associate professor at|is now an Assistant Professor in|is currently an associate professor in|an associate professor|associate professor" 
		        ,Pattern.CASE_INSENSITIVE  );
		private final static Pattern FILTEROCCUPATION_Raw_Student = Pattern.compile("is currently a Ph.D. student at|a third year Ph.D student|Ph.D. student of|PhD student|Ph.D. student in|Ph.D. student" 
		        ,Pattern.CASE_INSENSITIVE  );

		private final static Pattern FILTER_PHD = Pattern.compile("Ph.D.|Ph.D|PhD|Ph.D|doctor|Dr." 
		        ,Pattern.CASE_INSENSITIVE  );

		private final static Pattern FILTER_PREFIX_RESERCHINTERESTS = Pattern.compile("research interests|Interests include:|He specifies,|research interests|The integration of information|research focuses on|research area|Our current work has concentrated on three key areas:|main interest" 
		        ,Pattern.CASE_INSENSITIVE  );
		//private final static Pattern FILTER_PREFIX_RESERCHINTERESTS_WholeMatch = Pattern.compile(".*(research interests|Interests include:|He specifies,|research interests|The integration of information).*$" 
		//        ,Pattern.CASE_INSENSITIVE  );

		public static Map<String,String> doAttrbuteExtractor(String rawcontent,String dualedcontent,List<StringBuffer> relationsDataList,Map<String,String> personAttributeMap){
			// get the occupation
			if(FILTEROCCUPATION_Raw_Professor.matcher(rawcontent).find()){
				personAttributeMap.put("occupation", "Professor");
			}
			else if(FILTEROCCUPATION_Raw_AssistantProfessor.matcher(rawcontent).find()){
				personAttributeMap.put("occupation", "AssistantProfessor");
			}
			else if(FILTEROCCUPATION_Raw_Student.matcher(rawcontent).find()){
				personAttributeMap.put("occupation", "Student");
			}			
			else{ // default as professor
				personAttributeMap.put("occupation", "unknow");
			}

			// education background
			if(FILTER_PHD.matcher(rawcontent).find()){
				personAttributeMap.put("educationbackground", "Ph.D");
			}
			else{
				personAttributeMap.put("educationbackground", "unknow");
			}

			// research interests or directions
			if(FILTER_PREFIX_RESERCHINTERESTS.matcher(rawcontent).find()){
			//	FILTER_PREFIX_RESERCHINTERESTS.matcher(rawcontent)..
			//	int locationNumber=rawcontent..contains("research interests"|"Interests include:"|"He specifies,"|"research interests"|"The integration of information");
				
				int locationNumber=0;
				//int a=rawcontent.indexOf("research interests");
				if(rawcontent.indexOf("research interests")!=-1){
					locationNumber=rawcontent.indexOf("research interests",0);
				}
				else if(rawcontent.indexOf("Interests include:")!=-1){
					locationNumber=rawcontent.indexOf("Interests include:",0);
				}
				else if(rawcontent.indexOf("He specifies,")!=-1){
					locationNumber=rawcontent.indexOf("He specifies,",0);				
								}
				else if(rawcontent.indexOf("The integration of information")!=-1){
					locationNumber=rawcontent.indexOf("The integration of information",0);
				}
				else if(rawcontent.indexOf("research focuses on")!=-1){
					locationNumber=rawcontent.indexOf("research focuses on",0);//
				}
				else if(rawcontent.indexOf("research area")!=-1){
					locationNumber=rawcontent.indexOf("research area",0);
				}
				else if(rawcontent.indexOf("Our current work has concentrated on three key areas:")!=-1){
					locationNumber=rawcontent.indexOf("Our current work has concentrated on three key areas:",0);
				}
				else if(rawcontent.indexOf("main interest")!=-1){
					locationNumber=rawcontent.indexOf("main interest",0);
				}
				
				else{
					locationNumber=rawcontent.substring(0, locationNumber).indexOf(".",0)+1;}		
				
				int sentenceStart=rawcontent.substring(0, locationNumber).lastIndexOf(".")+1;
				int sentenceEnd=rawcontent.substring(locationNumber, rawcontent.length()).indexOf(".");// start from keyword, look for the closest backward.
				String reserchInterests=rawcontent.substring(sentenceStart, locationNumber+sentenceEnd+1);
				personAttributeMap.put("reserchInterests", reserchInterests);
			}
			else{
				personAttributeMap.put("reserchInterests", "unknow");
			}
			return personAttributeMap;
		}
		
	    public static void main(String[] args)
		{
			String rawcontent="Burkhard Englert is a professor in the department of Computer Engineering and Computer Science at California State University Long Beach. He earned his PhD from the department of Mathematics at the University of Connecticut in 2000. His doctoral research was in the area of computability theory (recursion theory) and dealt with lattice embeddings into the computably enumerable degrees. He also worked on distributed algorithms. In 1992, he received a BS degree in mathematics from the University of Tuebingen in Germany. After finishing his PhD in 2000, Burkhard came to University of California, Los Angeles (UCLA) as an adjunct assistant professor in the program in computing. At UCLA he received the Sorgenfrey Distinguished Teaching Award for visiting faculty members for outstanding achievements in teaching by the department of Mathematics. Currently, Burkhard teaches a broad range of graduate and undergraduate courses concentrating on computer security, net-centric computing, and distributed systems. Burkhard's research interests1 are distributed computing, distributed algorithms, computer security, and transportation network modeling and optimization. Burkhard also serves as a reviewer and a member of program committees on a number of national and international conferences.";	
			int locationNumber=0;
			int a=rawcontent.indexOf("d ");
			System.out.println("test:"+a);
			if(rawcontent.indexOf("research interests")!=-1){
				locationNumber=rawcontent.indexOf("research interests",0);
				System.out.println("test:"+locationNumber+"ttt"+rawcontent.charAt(1024)+rawcontent.charAt(1025)+rawcontent.charAt(1026));
			}
			else if(rawcontent.indexOf("Interests include:")!=-1){
				locationNumber=rawcontent.indexOf("Interests include:",0);
			}
			else if(rawcontent.indexOf("He specifies,")!=-1){
				locationNumber=rawcontent.indexOf("He specifies,",0);				
							}
			else if(rawcontent.indexOf("The integration of information")!=-1){
				locationNumber=rawcontent.indexOf("The integration of information",0);
			}
			else{
				locationNumber=rawcontent.substring(0, locationNumber).indexOf(".",0)+1;
			}
		
			int sentenceStart=rawcontent.substring(0, locationNumber).lastIndexOf(".")+1;
			System.out.println("test1:"+sentenceStart);
			int sentenceEnd=rawcontent.substring(locationNumber, rawcontent.length()).indexOf(".");
			System.out.println("test2:"+sentenceEnd);
			String reserchInterests=rawcontent.substring(sentenceStart, locationNumber+sentenceEnd+1);
			System.out.println("test3:"+reserchInterests);
		}
}