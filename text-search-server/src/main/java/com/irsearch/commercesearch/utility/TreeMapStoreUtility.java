package com.irsearch.commercesearch.utility;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

import com.irsearch.commercesearch.model.SearchEntity;

public class TreeMapStoreUtility {
	@SuppressWarnings("unchecked")
	public TreeMap<String,SearchEntity> getSearchEntityMapFile(String searchEntityFileName)throws IOException,ClassNotFoundException{
		TreeMap<String,SearchEntity> map;		
		ObjectInputStream indexFile = null;
		try{
			indexFile = new ObjectInputStream(new FileInputStream(searchEntityFileName));
			map = (TreeMap<String,SearchEntity>)indexFile.readObject();			
			indexFile.close();
		}catch(EOFException e){			
			map = new TreeMap<String,SearchEntity>();
		}		
		return map;
	}
	
	public boolean setSearchEntityMapFile(String searchEntityFileName,Map<String,SearchEntity> indexMap)throws IOException{		
		ObjectOutputStream indexFile = new ObjectOutputStream(new FileOutputStream(searchEntityFileName));
		indexFile.writeObject(indexMap);
		indexFile.close();
		return true;
	}
	
	
}
