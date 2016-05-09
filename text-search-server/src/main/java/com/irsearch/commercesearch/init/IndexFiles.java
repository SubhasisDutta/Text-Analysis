package com.irsearch.commercesearch.init;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.irsearch.commercesearch.config.SearchConstants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONException;

public class IndexFiles {

	/**
	 * Build the index .
	 * @param args
	 * @throws JSONException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws JSONException, IOException, ParseException{
		IndexFiles indexFiles = new IndexFiles();
		indexFiles.indexer(); 
	}
	
	
	public void indexer() throws IOException, ParseException, JSONException{
		
		Directory dir = FSDirectory.open(Paths.get(SearchConstants.INDEX_DIRECTORY_PATH));
	    for(String f : dir.listAll()){
	    	dir.deleteFile(f);
	    }
	    Analyzer analyzer = new StandardAnalyzer();
	    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	    iwc.setOpenMode(OpenMode.CREATE);
	    IndexWriter writer = new IndexWriter(dir, iwc);
	    Path docDir = Paths.get(SearchConstants.inputDocumentDirectoryPath);
	    File files = new File(SearchConstants.inputDocumentDirectoryPath);
//		JSONParser json = new JSONParser();
//		for(File f : files.listFiles()){
//		    Parse the JSON object in the file to get the URL and the contents
//			Path p = Paths.get(f.getAbsolutePath());
//			InputStream is = Files.newInputStream(p);
//			json.readJSON(f,is);
//		}
	    indexDocs(writer, docDir);
	    writer.close();
	}
	
	public static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		    if (Files.isDirectory(path)) {
		      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
		        @Override
		        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
		          indexDoc(writer, file, attrs.lastModifiedTime().toMillis());		          
		          return FileVisitResult.CONTINUE;
		        }
		      });
		    } else {
		      indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
		    }
		  }

		  /** Indexes a single document */
		  public static void indexDoc(IndexWriter writer, Path file, long lastModified){

			  try{
				  InputStream stream = Files.newInputStream(file);  
		      // make a new, empty document
		      Document doc = new Document();
		      
		      // Add the path of the file as a field named "path".  Use a
		      // field that is indexed (i.e. searchable), but don't tokenize 
		      // the field into separate words and don't index term frequency
		      // or positional information:
		      Field pathField = new StringField("path", file.toString(), Field.Store.YES);
		      doc.add(pathField);
		      
		      // Add the last modified date of the file a field named "modified".
		      // Use a LongPoint that is indexed (i.e. efficiently filterable with
		      // PointRangeQuery).  This indexes to milli-second resolution, which
		      // is often too fine.  You could instead create a number based on
		      // year/month/day/hour/minutes/seconds, down the resolution you require.
		      // For example the long value 2011021714 would mean
		      // February 17, 2011, 2-3 PM.
		      doc.add(new LongPoint("modified", lastModified));
		      
		      // Add the contents of the file to a field named "contents".  Specify a Reader,
		      // so that the text of the file is tokenized and indexed, but not stored.
		      // Note that FileReader expects the file to be in UTF-8 encoding.
		      // If that's not the case searching for special characters will fail.
		      doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
		      
		      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
		        // New index, so we just add the document (no old document can be there):
		        //System.out.println("adding " + file);
		        writer.addDocument(doc);
		      } else {
		        // Existing index (an old copy of this document may have been indexed) so 
		        // we use updateDocument instead to replace the old one matching the exact 
		        // path, if present:
		        writer.updateDocument(new Term("path", file.toString()), doc);
		      }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		  }
}