package com.irsearch.commercesearch.config;

import java.util.Properties;

/**
 * Created by wyatt.chastain on 4/24/16.
 */
public class SearchConstants {
	public static String BASE_URI = "http://localhost:8989/";
    //TODO: Change this location to the folder to the one that contains all .dat files provided by Crawling
    public static String INPUT_DAT_FOLDER = "/crawler_folder/";
    // TODO:provide the absolute path to where to store the data files
    public static String OUTPUT_SEPERATE_DATA_FILES = "/seperated-files-folder/";
    //TODO:change to proper location
    public static String DATA_FILE = "./current.arff";
    //TODO:change to proper location
    public static String WEB_PAGES = "./somepages.dat";
    // TODO:provide the absolute path to where to where the index will be stored
    public static String INDEX_DIRECTORY_PATH = "/index-folder";
    //	public static final String inputDocumentDirectoryPath = breakUpPage.OUTPUT_SEPERATE_DATA_FILES;
    //public static String inputDocumentDirectoryPath = OUTPUT_SEPERATE_DATA_FILES;
}
