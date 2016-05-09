package com.irsearch.commercesearch.service.queryretrival;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.lemurproject.kstem.KrovetzStemmer;


public class QueryExpansion {
     
    private HashMap<String, Integer> stopWords;
    private HashMap<String, Integer> originalQuery;
    private ArrayList<String> [] fileContents;
    QueryExpansion() {
        stopWords = new HashMap<String, Integer>();
         
        loadStopWords();
    }

    private void loadStopWords() {
    	stopWords.put("redcard", Integer.MIN_VALUE);
    	stopWords.put("walmart", Integer.MIN_VALUE);
    	stopWords.put("target", Integer.MIN_VALUE);
    	stopWords.put("save", Integer.MIN_VALUE);
    	stopWords.put("skip",Integer.MIN_VALUE);
    	stopWords.put("com",Integer.MIN_VALUE);
        stopWords.put("about",Integer.MIN_VALUE);
        stopWords.put("after",Integer.MIN_VALUE);
        stopWords.put("against",Integer.MIN_VALUE);
        stopWords.put("am",Integer.MIN_VALUE);
        stopWords.put("and",Integer.MIN_VALUE);
        stopWords.put("are",Integer.MIN_VALUE);
        stopWords.put("as",Integer.MIN_VALUE);
        stopWords.put("be",Integer.MIN_VALUE);
        stopWords.put("been",Integer.MIN_VALUE);
        stopWords.put("being",Integer.MIN_VALUE);
        stopWords.put("between",Integer.MIN_VALUE);
        stopWords.put("but",Integer.MIN_VALUE);
        stopWords.put("can't",Integer.MIN_VALUE);
        stopWords.put("could",Integer.MIN_VALUE);
        stopWords.put("did",Integer.MIN_VALUE);
        stopWords.put("do",Integer.MIN_VALUE);
        stopWords.put("doesn't",Integer.MIN_VALUE);
        stopWords.put("don't",Integer.MIN_VALUE);
        stopWords.put("during",Integer.MIN_VALUE);
        stopWords.put("few",Integer.MIN_VALUE);
        stopWords.put("from",Integer.MIN_VALUE);
        stopWords.put("had",Integer.MIN_VALUE);
        stopWords.put("has",Integer.MIN_VALUE);
        stopWords.put("have",Integer.MIN_VALUE);
        stopWords.put("having",Integer.MIN_VALUE);
        stopWords.put("he'd",Integer.MIN_VALUE);
        stopWords.put("he's",Integer.MIN_VALUE);
        stopWords.put("here",Integer.MIN_VALUE);
        stopWords.put("hers",Integer.MIN_VALUE);
        stopWords.put("him",Integer.MIN_VALUE);
        stopWords.put("his",Integer.MIN_VALUE);
        stopWords.put("how's",Integer.MIN_VALUE);
        stopWords.put("i'd",Integer.MIN_VALUE);
        stopWords.put("i'm",Integer.MIN_VALUE);
        stopWords.put("if",Integer.MIN_VALUE);
        stopWords.put("into",Integer.MIN_VALUE);
        stopWords.put("isn't",Integer.MIN_VALUE);
        stopWords.put("it's",Integer.MIN_VALUE);
        stopWords.put("itself",Integer.MIN_VALUE);
        stopWords.put("me",Integer.MIN_VALUE);
        stopWords.put("most",Integer.MIN_VALUE);
        stopWords.put("my",Integer.MIN_VALUE);
        stopWords.put("no",Integer.MIN_VALUE);
        stopWords.put("not",Integer.MIN_VALUE);
        stopWords.put("off",Integer.MIN_VALUE);
        stopWords.put("once",Integer.MIN_VALUE);
        stopWords.put("or",Integer.MIN_VALUE);
        stopWords.put("ought",Integer.MIN_VALUE);
        stopWords.put("ours",Integer.MIN_VALUE);
        stopWords.put("out",Integer.MIN_VALUE);
        stopWords.put("own",Integer.MIN_VALUE);
        stopWords.put("shan't",Integer.MIN_VALUE);
        stopWords.put("she'd",Integer.MIN_VALUE);
        stopWords.put("she's",Integer.MIN_VALUE);
        stopWords.put("shouldn't",Integer.MIN_VALUE);
        stopWords.put("some",Integer.MIN_VALUE);
        stopWords.put("than",Integer.MIN_VALUE);
        stopWords.put("that's",Integer.MIN_VALUE);
        stopWords.put("their",Integer.MIN_VALUE);
        stopWords.put("them",Integer.MIN_VALUE);
        stopWords.put("then",Integer.MIN_VALUE);
        stopWords.put("there's",Integer.MIN_VALUE);
        stopWords.put("they",Integer.MIN_VALUE);
        stopWords.put("they'll",Integer.MIN_VALUE);
        stopWords.put("they've",Integer.MIN_VALUE);
        stopWords.put("those",Integer.MIN_VALUE);
        stopWords.put("to",Integer.MIN_VALUE);
        stopWords.put("under",Integer.MIN_VALUE);
        stopWords.put("up",Integer.MIN_VALUE);
        stopWords.put("was",Integer.MIN_VALUE);
        stopWords.put("we",Integer.MIN_VALUE);
        stopWords.put("we'll",Integer.MIN_VALUE);
        stopWords.put("we've",Integer.MIN_VALUE);
        stopWords.put("weren't",Integer.MIN_VALUE);
        stopWords.put("what's",Integer.MIN_VALUE);
        stopWords.put("when's",Integer.MIN_VALUE);
        stopWords.put("where's",Integer.MIN_VALUE);
        stopWords.put("while",Integer.MIN_VALUE);
        stopWords.put("who's",Integer.MIN_VALUE);
        stopWords.put("why",Integer.MIN_VALUE);
        stopWords.put("with",Integer.MIN_VALUE);
        stopWords.put("would",Integer.MIN_VALUE);
        stopWords.put("you",Integer.MIN_VALUE);
        stopWords.put("you'll",Integer.MIN_VALUE);
        stopWords.put("you've",Integer.MIN_VALUE);
        stopWords.put("yours",Integer.MIN_VALUE);
        stopWords.put("yourselves",Integer.MIN_VALUE);

    }
    
    public String getRocchioExpansion(String oldQuery, ArrayList<String> files){
        
        String newQuery = "";
        int releventCollectionSize = files.size(); //|N|
        int localVocabularySize = 0; //|V|
        int localStemSize = 0; //|S|
        
        fileContents = new ArrayList[releventCollectionSize];
        originalQuery = getQuery(oldQuery); //holds the query stems. will update with the matrix row later
        ArrayList<Integer> newQueryTermRows = new ArrayList<Integer>();
        
        HashMap<String, HashMap<Integer, Integer>> localVocab = getLocalVocabulary(files);
        localVocabularySize = localVocab.size();
        HashMap<String, String> localStemmedVocab = getLocalStemmedVocabulary(localVocab);
        localStemSize = localStemmedVocab.size();
        
        ArrayList<double[]> documentVectors= new ArrayList<double[]>();
        
        Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);
        for(int j=0; j<fileContents.length; j++){
                ArrayList<String> s = fileContents[j];
	    	double[] d = new double[localStemmedVocab.size()];
                int i=0;
	    	for(String key: sortedMap.keySet()){
                    
	    		d[i] = tf(s,key) * idf(s,key);
                        if(d[i]!=d[i])//check if NaN
                            d[i]=0;
                        
                        i++;
                        
                        
                }
	    	documentVectors.add(d);
	    }
        
        
        double alpha = 1;
        double beta = .75;
        
        
        double[] relevantDocVectorSum = new double[localStemmedVocab.size()];
        
        for(int j=0; j<localStemmedVocab.size(); j++){ //column
            double total = 0.0;
            
            for(int i =0; i < documentVectors.size(); i++){ //row
           
                 total+= documentVectors.get(i)[j];
                
            }
           
            relevantDocVectorSum[j]+= total* beta*(1/(double)releventCollectionSize);
        }
        
        double [] queryVector = new double[localStemmedVocab.size()];
        int queryVectorRow = 0;
        for(String currentLocalVocabTerm: sortedMap.keySet()){
            
            double result = 0.0;
            String queryTerm ="";
                for(String currentQueryTerm: originalQuery.keySet() ){
                
                if(currentLocalVocabTerm.compareTo(currentQueryTerm)==0){
                    queryTerm = currentQueryTerm;
                    result = tfQuery(originalQuery, currentQueryTerm)*idfQuery(originalQuery, currentQueryTerm);
                    break;
                }
            }
            
            queryVector[queryVectorRow] = result*alpha;
            if(result!=0)
                originalQuery.put(queryTerm, queryVectorRow);
            queryVectorRow++;
        }
        
        
        double[] modifiedQueryVector = new double[localStemmedVocab.size()];


        for(int i=0; i <localStemmedVocab.size(); i++){
            modifiedQueryVector[i] = queryVector[i]+ relevantDocVectorSum[i];
            
        }
        
        
        int[] topThreeRows = new int[3];
        double[] topThree = new double[3];
        
        for(int i=0; i < modifiedQueryVector.length; i++){
            boolean partOfOriginalQuery = false;
            for(String queryTerm: originalQuery.keySet()){
                if(originalQuery.get(queryTerm)==i){
                    partOfOriginalQuery=true;
                }
            }
            
            if(!partOfOriginalQuery){
                double result = modifiedQueryVector[i];
                int row = i;
                double temp = 0.0;
                int tempRow = 0;
                if(topThree[0]< result){
                    temp = topThree[0];
                    tempRow = topThreeRows[0];
                    topThree[0] = result;
                    topThreeRows[0] = row;
                    result = temp;
                    row = tempRow;     
                }
                if(topThree[1]< result){
                    temp = topThree[1];
                    tempRow = topThreeRows[1];
                    topThree[1] = result;
                    topThreeRows[1] = row;
                    result = temp;
                    row = tempRow;
                }
                    if(topThree[2]< result){
                    temp = topThree[2];
                    tempRow = topThreeRows[2];
                    topThree[2] = modifiedQueryVector[i];
                    topThreeRows[2] = row;
                    result = temp;
                    row = tempRow;
                }
            } 
        }
        
        for(int i=0; i< topThree.length; i++)
                newQueryTermRows.add(topThreeRows[i]);
        
        newQuery = getNewQueryTerms(newQueryTermRows,localStemmedVocab, oldQuery);
        
        
        return newQuery;
    }
    
    static double tf(ArrayList<String> doc, String term){
        KrovetzStemmer stemmer = new KrovetzStemmer();
        
    	double n = 0;
    	for(int i=0; i<doc.size(); i++){
            String s = stemmer.stem(doc.get(i));
    		if(s.compareTo(term)==0)
    			n++;
        }
    	return n/doc.size();
    }
    
    static double tfQuery(HashMap<String, Integer> originalQuery, String term){
        KrovetzStemmer stemmer = new KrovetzStemmer();
        
    	double n = 0;
    	for(String queryTerm : originalQuery.keySet()){
            term = stemmer.stem(term);
    		if(queryTerm.compareTo(term)==0)
    			n++;
        }
    	return n/originalQuery.size();
    }
    
    private  double idf(ArrayList<String> doc, String term){
        KrovetzStemmer stemmer = new KrovetzStemmer();
        
    	double n = 0;
    	for(int i=0; i<doc.size(); i++){
            String s = stemmer.stem(doc.get(i));
    			if(s.compareTo(term)==0){
    				n++;
    				break;
    			}
    	}
    	return Math.log(doc.size()/n);
    }
    
    private  double idfQuery(HashMap<String, Integer> originalQuery, String term){
        KrovetzStemmer stemmer = new KrovetzStemmer();
        
    	double n = 0;
    	for(String queryTerm: originalQuery.keySet()){
            term = stemmer.stem(term);
    			if(queryTerm.compareTo(term)==0){
    				n++;
    				break;
	    		}
                }
    	return Math.log(originalQuery.size()/n);
    }
    
    
    
    public String getScalarClusterExpansion(String oldQuery, ArrayList<String> files){
        KrovetzStemmer stemmer = new KrovetzStemmer();
        String newQuery ="";
        int releventCollectionSize = files.size(); //|N|
        
        int localStemSize = 0; //|S|
        fileContents = new ArrayList[releventCollectionSize];
        originalQuery = getQuery(oldQuery); //holds the query stems. will update with the matrix row later
        
        
        //maintain a list of local vocabulary found in relevant documents
        //will need this for the association matrix
        HashMap<String, HashMap<Integer, Integer>> localVocab = getLocalVocabulary(files);
        HashMap<String, String> localStemmedVocab = getLocalStemmedVocabulary(localVocab);
        localStemSize = localStemmedVocab.size();
        double [][] correlationMatrix = new double[localStemSize][localStemSize];
        
        //check to make sure that the terms from the original query appear at least once in the documents.
        //if not return original query
        int count =0;
        oldQuery = "";
        for(String key: originalQuery.keySet()){
            oldQuery += key+" ";
            if(localStemmedVocab.containsKey(key))
                count++;
        }
        if(count == 0 )
            return oldQuery;
        
        ArrayList<Integer> newQueryTermRows = new ArrayList<Integer>();
        
        /* matrix with rows of stems and columns of documents. one for each Query term?? I think so...*/
        
        newQuery = oldQuery;
        Map<String, String> sortedLocalVocabMap = new TreeMap<String, String>(localStemmedVocab);
        
        int rowCount = 0;
        for(String key: sortedLocalVocabMap.keySet()){
            
            String stem = key;
            
            stem = stemmer.stem(stem);//set string you need to stem
            
            
            double[][] correllationMatrix = getMetricCorrellation(releventCollectionSize, 
                    localStemSize, stem, localStemmedVocab, localVocab, files, originalQuery);
            
          
          for(int i=0; i<localStemSize; i++){ //i can be used for column
              double current = 0.0;
              for(int j=0;j <releventCollectionSize; j++){
                  current+= correllationMatrix[i][j];
                  
              }
              //normalize
              //get current Local Stem
              int row = 0;
              String t = "";
              Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);
              for(String term: sortedMap.keySet()){
                  if(row==i){
                      t = term;
                      break;
                  }
                  row++;
              }
              
              String [] tempo = localStemmedVocab.get(t).split(",");
              int keywordJCount = tempo.length;
              tempo = localStemmedVocab.get(key).split(",");
              int keywordICount = tempo.length;
              int cross = keywordICount * keywordJCount;
              current = current/cross; //normalizes value in current
              correlationMatrix[rowCount][i] = current;
          }
          
          rowCount++;//rowCount is for correlation matrix
          
        }
        
        
        double [] results = new double[localStemmedVocab.size()];
        
        
        for(int i =0; i<localStemmedVocab.size(); i++){ //i is row i.e. current vector
            for(int k = 0; k <localStemmedVocab.size(); k++){
                if(k!=i){ //we dont take the dot product of the vector against itself
                for(int j=0; j< localStemmedVocab.size(); j++){
                    results[i]+= correlationMatrix[i][j]* correlationMatrix[k][j];
                }
                }else{
                    results[i] =0.0;
                    
                }
                //normalize
              int rowK = 0;
              int rowI =0 ;
              String termK = "";
              String termI ="";
              Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);
              for(String term: sortedMap.keySet()){
                  if(rowK==k){
                      termK = term;
                     
                  }
                  if(rowI == i){
                      termI = term;
                      
                  }
                  rowK++;
                  rowI++;
              }
              
              String [] tempo = localStemmedVocab.get(termK).split(",");
              int keywordKCount = tempo.length;
              tempo = localStemmedVocab.get(termI).split(",");
              int keywordICount = tempo.length;
              
              int cross = keywordKCount* keywordICount;
              results[i] = results[i]/(double)cross;
              
            }
        }
        
        int[] topThreeRows = new int[3];
        double[] topThree = new double[3];
        for(int i=0; i < results.length; i++){
        	double result = results[i];
        	int row = i;
        	double temp = 0.0;
        	int tempRow = 0;
            if(topThree[0]< result){
                temp = topThree[0];
                tempRow = topThreeRows[0];
                
                topThree[0] = result;
                topThreeRows[0] = row;
                result = temp;
                row = tempRow;     
            }
            if(topThree[1]< result){
                temp = topThree[1];
                tempRow = topThreeRows[1];
                topThree[1] = result;
                topThreeRows[1] = row;
                result = temp;
                row = tempRow;
            }
                if(topThree[2]< result){
                temp = topThree[2];
                tempRow = topThreeRows[2];
                topThree[2] = results[i];
                topThreeRows[2] = row;
                result = temp;
                row = tempRow;
            }
            
        }
        
        for(int i=0; i< topThree.length; i++)
                newQueryTermRows.add(topThreeRows[i]);
        
        newQuery = getNewQueryTerms(newQueryTermRows,localStemmedVocab, oldQuery);
        return newQuery;
    }
    
    
    
    
    
    
    
    public String getMetricClusterExpansion(String oldQuery, ArrayList<String> files){ //metric clusters
    	
    	System.out.println("!!!!!!!!!!!!!!!!!!!Starting Metric Query Expansion.......!!!!!!!!!!!!!!!!!!!!");
        String newQuery ="";
        int releventCollectionSize = files.size(); //|N|
        int localStemSize = 0; //|S|
        fileContents = new ArrayList[releventCollectionSize];
        originalQuery = getQuery(oldQuery); //holds the query stems. will update with the matrix row later
        
        
        //maintain a list of local vocabualary found in relevent documents as well as the ount for each document.
        //will need this for the association matrix
        HashMap<String, HashMap<Integer, Integer>> localVocab = getLocalVocabulary(files);
        
        HashMap<String, String> localStemmedVocab = getLocalStemmedVocabulary(localVocab);
        localStemSize = localStemmedVocab.size();
        
        
        //check to make sure that the terms from the original query appear at least once in the documents.
        //if not return original query
        
        int count =0;
        oldQuery = "";
        for(String key: originalQuery.keySet()){
            oldQuery += key+" ";
            if(localStemmedVocab.containsKey(key))
                count++;
        }
        if(count == 0 )
            return oldQuery;
        
        ArrayList<Integer> newQueryTermRows = new ArrayList<Integer>();
        
        /* matrix with rows of stems and columns of documents. one for each Query term?? I think so...*/
        
        newQuery = oldQuery;
        for(String key: originalQuery.keySet()){
            String stem = key;
            KrovetzStemmer stemmer = new KrovetzStemmer();
            stem = stemmer.stem(stem);//set string you need to stem
            
            double[][] correllationMatrix = getMetricCorrellation(releventCollectionSize, 
                    localStemSize, stem, localStemmedVocab, localVocab, files, originalQuery);
            
          double[] topTwoCorrellation = new double[2];
          int[] topTwoTerms = new int[2];
          topTwoCorrellation[0] = 0.0;
          topTwoCorrellation[1] =0.0;
          topTwoTerms[0] = 0;
          topTwoTerms[1] =0;
          
          for(int i=0; i<localStemSize; i++){
              double current = 0.0;
              for(int j=0;j <releventCollectionSize; j++){
                  current+= correllationMatrix[i][j];
                  
              }
              //normalize
              //get current Local Stem
              int row = 0;
              String t = "";
              Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);
              for(String term: sortedMap.keySet()){
                  if(row==i){
                      t = term;
                      break;
                  }
                  row++;
              }
              
              String [] tempo = localStemmedVocab.get(t).split(",");
              int keywordJCount = tempo.length;
              tempo = localStemmedVocab.get(key).split(",");
              int keywordICount = tempo.length;
              
              int cross = keywordICount * keywordJCount;
              current = current/cross; //normalizes value in current
              
              if(current > topTwoCorrellation[0]){
                  double temp = topTwoCorrellation[0];
                  int tempRow = topTwoTerms[0];
                  topTwoCorrellation[0] = current;
                  topTwoTerms[0] = i;
                  
                  if(temp > topTwoCorrellation[1] ){
                      topTwoCorrellation[1]  = temp;
                      topTwoTerms[1] = tempRow;
                      
                  }
              }else if(current > topTwoCorrellation[1]){
                  topTwoCorrellation[1] = current;
                  topTwoTerms[1] = i;
              }
              
          }
          
          //add to expanded query
          newQueryTermRows.add(topTwoTerms[1]);
          newQueryTermRows.add(topTwoTerms[0]);
          
        }
        
        newQuery = getNewQueryTerms(newQueryTermRows,localStemmedVocab, oldQuery);
        System.out.println("New Expanded Query: "+newQuery);
        return newQuery;
    }

    private double [][] getMetricCorrellation(int collectionSize, int localStemSize, 
                                            String stem, HashMap<String, 
                                            String> localStemmedVocab, 
                                            HashMap<String, HashMap<Integer, Integer>> localVocab ,
                                            ArrayList<String> files, HashMap<String, Integer> originalQuery ){
        
        
        double [][] matrix = new double[localStemSize][collectionSize];
        KrovetzStemmer stemmer = new KrovetzStemmer();
        
        Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);
    
        for(int j=0; j < fileContents.length; j++){
            int row =0;
            String  keywordI = stemmer.stem(stem);
            for(String key: sortedMap.keySet()){
                
                double correllation = 0.0;
                
                boolean proceed = true;
                for(String term: originalQuery.keySet()){
                    
                    if(term.compareTo(key)==0)
                        proceed= false;
                }
                
                if(proceed){
                String keywordJ = stemmer.stem(key);
                
                    if(keywordJ.compareTo(keywordI)!=0){
                    
                        if(fileContents[j].contains(keywordI)&& fileContents[j].contains(keywordJ)){
                            
                            int keywordIOccurenceCount = Collections.frequency(fileContents[j], keywordI);
                            int keywordJOccurenceCount = Collections.frequency(fileContents[j], keywordJ);
                            
                            ArrayList<Integer> keywordIPositions = new ArrayList<Integer>();
                            ArrayList<Integer> keywordJPositions = new ArrayList<Integer>();
                            ArrayList<Integer> keywordIJGaps = new ArrayList<Integer>();
                           
                            
                            for(int g=0; g<fileContents[j].size(); g++){
                                String current = fileContents[j].get(g);
                                if(current.compareTo(keywordI)==0){
                                    keywordIPositions.add(g);
                                    
                                }
                                else if(current.compareTo(keywordJ)==0){
                                    keywordJPositions.add(g);
                                    
                                }  
                                
                            }
                            
                            for(int g=0; g<keywordIOccurenceCount; g++){
                                
                                for(int h=0; h<keywordJOccurenceCount; h++){
                                    
                                    
                                    if(keywordIPositions.get(g)<keywordJPositions.get(h)){
                                        int tempGap = keywordJPositions.get(h) - keywordIPositions.get(g);
                                        keywordIJGaps.add(tempGap);
                                                
                                    }else if(keywordIPositions.get(g)>keywordJPositions.get(h)){
                                        int tempGap = keywordIPositions.get(g) - keywordJPositions.get(h);
                                        keywordIJGaps.add(tempGap);
                                    }
                                    
                                }
                                
                            }
                            
                for(int g=0; g<keywordIJGaps.size(); g++){
                    correllation+= (double)1/keywordIJGaps.get(g);
                }
                
                matrix[row][j]= correllation;
                        }   
                row++;
                    }
                }
            }	
        }
        
        return matrix;
        
    }
    
    
    
    public String getAssosciationClusterExpansion(String oldQuery, ArrayList<String> files) { //association clusters

        String newQuery = "";
        int releventCollectionSize = files.size(); //|N|
        int localVocabularySize = 0; //|V|
        int localStemSize = 0; //|S|
        fileContents = new ArrayList[releventCollectionSize];

        originalQuery = getQuery(oldQuery); //holds the query stems. will update with the matrix row later

        
        //maintain a list of local vocabualary found in relevent documents as well as the ount for each document.
        //will need this for the association matrix
        HashMap<String, HashMap<Integer, Integer>> localVocab = getLocalVocabulary(files);
        localVocabularySize = localVocab.size();
        HashMap<String, String> localStemmedVocab = getLocalStemmedVocabulary(localVocab);
        localStemSize = localStemmedVocab.size();
        
        //check to make sure that the terms from the original query appear at least once in the documents.
        //if not return original query
        
        int count =0;
        oldQuery = "";
        for(String key: originalQuery.keySet()){
            oldQuery += key+" ";
            if(localStemmedVocab.containsKey(key))
                count++;
        }
        if(count == 0 )
            return oldQuery;
        
        //make the association matrix

         //fill association matrix
        int[][] associationMatrix = fillAssocMatrix(localStemSize,
                                                    releventCollectionSize,
                                                    localVocab,
                                                    localStemmedVocab);


       ArrayList<Integer> newQueryTermRows = new ArrayList<Integer>();
       
       //TODO add the original query to the new Query term rows, so we do not duplicate.

       //first we need to get the row each query term resides in

       for(String key: originalQuery.keySet()){
           newQueryTermRows.add(originalQuery.get(key));
           double [] associationMatrixCorrellations = new double[localStemSize];

            for(int i =0; i<localStemSize; i++){
                //stores each value from the computed correclation against each stem
                double cuv = 0;
                double cuu =0;
                double cvv = 0;
                if(i!=originalQuery.get(key) && originalQuery.get(key)!=Integer.MIN_VALUE){
                for(int j=0; j<releventCollectionSize; j++){
                    cuv+= associationMatrix[originalQuery.get(key)][j]* associationMatrix[i][j];
                    cuu+= associationMatrix[originalQuery.get(key)][j]*associationMatrix[originalQuery.get(key)][j];
                    cvv += associationMatrix[i][j] * associationMatrix[i][j];
                }
                }
               associationMatrixCorrellations[i] = (cuv/(cuv+cuu+cvv));
            }
            double [] topTwo = new double[2];
            int [] topTwoRows = new int[2];
            
            for(int i=0; i<localStemSize; i++){
                
                    if(associationMatrixCorrellations[i]>topTwo[0]){
                        double temp = topTwo[0];
                        int tempRow = topTwoRows[0];
                        topTwo[0] = associationMatrixCorrellations[i];
                        topTwoRows[0]=i;
                        if(temp > topTwo[1]){
                            topTwo[1] = temp;
                            topTwoRows[1] = tempRow;
                        }

                    }else{
                        if(associationMatrixCorrellations[i]>topTwo[1]){
                            topTwo[1] = associationMatrixCorrellations[i];
                            topTwoRows[1] = i;
                        }
                }
            }
            
            
            boolean[] inserted = new boolean[2];
            inserted[0] = false;
            inserted[1] = false;
            
            for(int i=0; i< newQueryTermRows.size(); i++){
               if(newQueryTermRows.get(i)== topTwoRows[1] && !inserted[1]){
                  inserted[1] = true;
                  if(inserted[0])
                        break;
               }
               if(newQueryTermRows.get(i)== topTwoRows[0] && !inserted[0]){
                   inserted[0] = true;
                  if(inserted[1])
                        break;
                   
               }
               
            }
            
            if(!inserted[0]){
                newQueryTermRows.add(topTwoRows[0]);
                
            }
            if(!inserted[1]){
                newQueryTermRows.add(topTwoRows[1]);
            }    
            
       }
       
       newQuery = /*oldQuery+" "+*/getNewQueryTerms(newQueryTermRows,localStemmedVocab, oldQuery);

        //Map<String, Integer> sortedMap = new TreeMap<String, Integer>(localVocab);
        return newQuery;
    }

    
    private String getNewQueryTerms(ArrayList<Integer> newQueryTermRows , HashMap<String, String> localStemmedVocab, String oldQuery){
        String query =oldQuery+" ";
        
        Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);

        for(int i=0; i <newQueryTermRows.size(); i++){
        int row = 0; //increment as we move forward in the list of stems
        int rowNeeded = newQueryTermRows.get(i);
        
        for(String key: sortedMap.keySet()){
            if(rowNeeded == row){
             
             //if(!query.contains(key))
               String word = key;//sortedMap.get(key).split(",");
                if(!query.contains(word))
                    query+= word+" ";
             break;
             
            }
            row++;
        }
        }
        return query;
    }

    private HashMap<String, Integer> getQuery(String line){

        HashMap<String, Integer> query = new HashMap<String, Integer>();
        line = line.replace("\n", " ").replace("\r", " ").toLowerCase().replaceAll("[^a-z]", " ");
        StringTokenizer tokens = new StringTokenizer(line);
        while (tokens.hasMoreTokens()) {
                String nextToken = tokens.nextToken();
                if (!stopWords.containsKey(nextToken) && nextToken.length() > 2) {
                    String stem = nextToken;
                    //PorterStemmer stemmer = new PorterStemmer();
                    KrovetzStemmer stemmer = new KrovetzStemmer();
                    stem = stemmer.stem(stem);//set string you need to stem
                    
                    if(!query.containsKey(stem)){
                        query.put(stem, Integer.MIN_VALUE);
                    }
                }
        }
        return query;
    }

    private int[][] fillAssocMatrix(int localStemSize, int releventCollectionSize,
                            HashMap<String, HashMap<Integer, Integer>> localVocab,
                            HashMap<String, String> localStemmedVocab){


        int[][] associationMatrix = new int[localStemSize][releventCollectionSize];

        Map<String, String> sortedMap = new TreeMap<String, String>(localStemmedVocab);

        int row = 0; //increment as we move forward in the list of stems
        for(String key: sortedMap.keySet()){

            String [] words = sortedMap.get(key).split(",");

            if(originalQuery.containsKey(key))
                originalQuery.put(key, row);

            for(int j =0; j<words.length; j++){ //stem is key words[j] is assococaited.
                                                //update the column count for each document where the words[j] is found
                HashMap<Integer, Integer> docs = localVocab.get(words[j]);

                for(Integer doc: docs.keySet()){
                    associationMatrix[row][doc]+= docs.get(doc);
                }

            }
            
            row++;
        }


        return  associationMatrix;
    }

    private HashMap<String, String> getLocalStemmedVocabulary(HashMap<String, HashMap<Integer, Integer>> localVocab) {
//TODO: come back and see if we cant make a better stemmer? this is a porter stemmer and she mentioned we cannot have that.
        HashMap<String, String> stemmedVocab = new HashMap<String, String>();
        
        for (String key : localVocab.keySet()) {

            String stem = key;
            KrovetzStemmer stemmer = new KrovetzStemmer();
            stem = stemmer.stem(stem);//set string you need to stem
            
            if(stemmedVocab.containsKey(stem)){
                
                String [] lines = stemmedVocab.get(stem).split(",");
                boolean insert = true;
                for(int i =0; i <lines.length; i ++){
                    if(lines[i].compareTo(key)==0)
                        insert = false;
                }
            
                if(insert)
                    stemmedVocab.put(stem, stemmedVocab.get(stem)+","+key);
            }
            else
                stemmedVocab.put(stem, key);
        }

        return stemmedVocab;
    }

    HashMap<String, HashMap<Integer, Integer>> getLocalVocabulary(ArrayList<String> files) {
        KrovetzStemmer stemmer = new KrovetzStemmer();
        HashMap<String, HashMap<Integer, Integer>>localVocab = new HashMap<String, HashMap<Integer, Integer>>();
              
        
        
        for (int i = 0; i < files.size(); i++) {
            fileContents[i] =  new ArrayList<String>();
            String line = files.get(i);
            
                
            line = line.replace("\n", " ").replace("\r", " ").toLowerCase().replaceAll("[^a-z]", " ");

            StringTokenizer tokens = new StringTokenizer(line);

            while (tokens.hasMoreTokens()) {
                String nextToken = tokens.nextToken();
                
                String stem = nextToken;
                
                
                //PorterStemmer stemmer = new PorterStemmer();
                stem = stemmer.stem(stem);//set string you need to stem
                fileContents[i].add(stem);
                if (!stopWords.containsKey(nextToken) && nextToken.length() > 2) {

                    if (localVocab.containsKey(nextToken)) {
                        HashMap<Integer, Integer> t = localVocab.get(nextToken);
                        if(t.containsKey(i))
                            t.put(i, t.get(i)+1);
                        else
                            t.put(i, 1);
                        localVocab.put(nextToken, t);
                    } else {
                        HashMap<Integer, Integer> t = new HashMap<Integer, Integer>();
                        t.put(i, 1);
                        localVocab.put(nextToken, t);
                    }
                }
            }
        }
        return localVocab;
    }

}
