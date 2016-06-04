To build the project in the root directory of the project 

mvn package

This will create the jars in target folder

To run the server 
java Rest_API_Entity_Server-jar-with-dependencies.jar

java -cp .\Rest_API_Entity_Server-jar-with-dependencies.jar com.serendio.textanalyzer.Main

Build the Index (To be run only once to build the index)
java -cp .\Rest_API_Search_Server-jar-with-dependencies.jar com.irsearch.commercesearch.init.IndexFiles config.properties

nohup java -cp .\Rest_API_Search_Server-jar-with-dependencies.jar com.irsearch.commercesearch.init.IndexFiles config.properties &

Run the Search Server
java -cp .\Rest_API_Search_Server-jar-with-dependencies.jar com.irsearch.commercesearch.Main config.properties

nohup java -cp .\Rest_API_Search_Server-jar-with-dependencies.jar com.irsearch.commercesearch.Main config.properties &

