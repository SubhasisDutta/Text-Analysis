package com.irsearch.commercesearch.init;

import org.json.JSONArray;

import com.irsearch.commercesearch.service.cluster.Cluster;
import com.irsearch.commercesearch.service.cluster.ClusterFileUtil;

import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;

public class Clustering {

	public static void main(String[] args) throws Throwable {
		//loadData();
		Cluster cluster = new Cluster();
		try {

			cluster.setBestDocuments("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/bestDocuments");
			cluster.setClusterAssignments("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterAssignments");
			cluster.setClusterTitles("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterTitles");

			Vector<String> queries = cluster.generateClusterQueries();

			int i = 1;
			for (String query : queries) {
				System.out.println(i + ": " + query);
				i++;
			}

			//cluster.generateGraphJSONAll();

//			cluster.printBestDocuments();
//			cluster.printClusterAssignments();
			//cluster.printClusterTitles();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Vector<String> testPages = new Vector<String>();
//		testPages.add("http://www.target.com/p/convenience-concepts-northfield-wall-console-table/-/A-10974416");
//		testPages.add("http://www.target.com/p/threshold-parsons-coffee-table/-/A-16982954");
//		testPages.add("http://www.target.com/tdir/p/beauty/-/N-55r1x");
//
//		JSONArray json = cluster.addResults(testPages);

//		ClusterFileUtil.breakOutFile("./somepages.dat");
//		ClusterFileUtil.readWebpages("./somepages.dat", "./testingDat");
//
//		HashMap<String, Integer> testTreeMap = new HashMap<String, Integer>();
//
//		for (int ii = 0; ii < 200000; ii++) {
//			testTreeMap.put("reallylongasdfkjas;dlfkjstringnamewithstuff" + ii, ii);
//		}
//
//		ClusterFileUtil.saveModel("./hashMapModel", testTreeMap);
	}

	public static void loadData() throws Throwable {

		boolean PARSE_CRAWLER_DATA = false;
		boolean LOAD_INST_FROM_FILE = false;
		boolean LOAD_MODELS_FROM_FILES = false;

		String CRAWLER_DIRECTORY = "./Webpages";
		String CRAWLER_ARFF = "./Crawler.arff";

		String MODEL_FILENAME_KMEANS = "./kMeans.model";
		String CLUSTER_TITLE_FILE = "./clusterTitles";
		String CLUSTER_BEST_DOCUMENTS_FILE = "./bestDocuments";
		String CLUSTER_ASSIGNMENTS_FILE = "./clusterAssignments";

		String MODEL_FILENAME_HIER_COMPLETE = "./hier_complete.model";
		String MODEL_FILENAME_HIER_SINGLE = "./hier_single.model";
		String MODEL_FILENAME_HIER_CENTROID = "./hier_centroid.model";
		String MODEL_FILENAME_HIER_NEIGHBOR = "./hier_neighbor.model";

		String STOP_WORDS_FILE = "/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterStopWords";

		String INSTANCES_FILE = "./instances.data";

		int TITLE_LENGTH = 20;

		long startTime;
		long endTime;

		if (PARSE_CRAWLER_DATA) {
			ClusterFileUtil.readWebpages(CRAWLER_DIRECTORY, CRAWLER_ARFF);
		}

		System.out.println("Reading in data from arff file.");

		startTime = System.nanoTime();
		Instances data = null;
		if (LOAD_INST_FROM_FILE) {
			data = (Instances) ClusterFileUtil.tryToLoadModel(INSTANCES_FILE);
		}

		if (data == null) {
			data = ClusterFileUtil.getData(ClusterFileUtil.getDataSourceFromFile(CRAWLER_ARFF), true);
		}

		endTime = System.nanoTime();

		System.out.println("Time to load data: " + ((endTime - startTime)/1000000.0) + "ms");

		if (data == null) {
			System.out.println("Problem getting data.  Returning.");
			return;
		}

		ClusterFileUtil.saveModel("./instances.data", data);

		/*
		 * Clustering Algorithms to be used.
		 */
		// Simple K-Means
		System.out.println("Calculating SimpleKMeans");
		startTime = System.nanoTime();
		SimpleKMeans kMeans = ClusteringUtils.newKMeans(data, "-N 100 -C -O", MODEL_FILENAME_KMEANS, LOAD_MODELS_FROM_FILES);
		if (kMeans == null) {
			kMeans = ClusteringUtils.newKMeans(data, "-N 100 -O", MODEL_FILENAME_KMEANS, LOAD_MODELS_FROM_FILES);
		}
		endTime = System.nanoTime();

		System.out.println("Time to calculate kMeans: " + ((endTime - startTime)/1000000.0) + "ms");

		// Now let's do some kMeans stuff.
		EuclideanDistance ed = (EuclideanDistance)kMeans.getDistanceFunction();

		int[] assignments = kMeans.getAssignments();
		Instances unfilteredData = ClusterFileUtil.getData(ClusterFileUtil.getDataSourceFromFile(CRAWLER_ARFF), false);

		HashMap<String, Double[]> clusterAssignments = new HashMap<String, Double[]>();
		HashMap<Integer, TreeMap<Double, String>> bestDocuments = new HashMap<Integer, TreeMap<Double, String>>();

		int currentInstance = 0;

		for(int clusterNum : assignments) {
			Double[] clusterAndDistance = new Double[2];
			clusterAndDistance[0] = (double) clusterNum;
			clusterAndDistance[1] = ed.distance(data.instance(currentInstance), kMeans.getClusterCentroids().instance(clusterNum));

			clusterAssignments.put(unfilteredData.instance(currentInstance).toString(1), clusterAndDistance);

			if (!bestDocuments.containsKey(clusterNum)) {
				bestDocuments.put(clusterNum, new TreeMap<Double, String>());
			}

			TreeMap<Double, String> temp = bestDocuments.get(clusterNum);
			temp.put(ed.distance(data.instance(currentInstance), kMeans.getClusterCentroids().instance(clusterNum)),
					unfilteredData.instance(currentInstance).toString(1));

			bestDocuments.put(clusterNum, temp);

			currentInstance++;
		}

		ClusterFileUtil.saveModel(CLUSTER_TITLE_FILE, ClusterFileUtil.getClusterTitles(kMeans, TITLE_LENGTH));
		ClusterFileUtil.saveModel(CLUSTER_BEST_DOCUMENTS_FILE, bestDocuments);
		ClusterFileUtil.saveModel(CLUSTER_ASSIGNMENTS_FILE, clusterAssignments);

		System.out.println("K-Means is done.");

		// null out clusters we aren't using anymore.
		kMeans = null;

		// Complete Hierarchical Cluster
		startTime = System.nanoTime();
		HierarchicalClusterer completeHier = ClusteringUtils.newHierarchy(data,
				"-N 100 -L COMPLETE -A weka.core.EuclideanDistance",
				MODEL_FILENAME_HIER_COMPLETE, LOAD_MODELS_FROM_FILES);
		endTime = System.nanoTime();

		System.out.println("Time to calculate Complete Hierarchical Cluster: " + ((endTime - startTime)/1000000.0) + "ms");


		completeHier = null;

		// Single Hierarchical Cluster
		startTime = System.nanoTime();
		HierarchicalClusterer singleHier = ClusteringUtils.newHierarchy(data,
				"-N 1000 -L SINGLE -A weka.core.EuclideanDistance",
				MODEL_FILENAME_HIER_SINGLE, LOAD_MODELS_FROM_FILES);
		endTime = System.nanoTime();

		System.out.println("Time to calculate Single Hierarchical Cluster: " + ((endTime - startTime)/1000000.0) + "ms");

		singleHier = null;

		// Centroid Hierarchical Cluster
		startTime = System.nanoTime();
		HierarchicalClusterer centroidCluster = ClusteringUtils.newHierarchy(data,
				"-N 1000 -L CENTROID -A weka.core.EuclideanDistance",
				MODEL_FILENAME_HIER_CENTROID, LOAD_MODELS_FROM_FILES);
		endTime = System.nanoTime();

		System.out.println("Time to calculate Centroid Hierarchical Cluster: " + ((endTime - startTime)/1000000.0) + "ms");

		centroidCluster = null;


		// Neighbor-Joining Hierarchical Cluster
		startTime = System.nanoTime();
		HierarchicalClusterer neighborCluster = ClusteringUtils.newHierarchy(data,
				"-N 1000 -L NEIGHBOR_JOINING -A weka.core.EuclideanDistance",
				MODEL_FILENAME_HIER_NEIGHBOR, LOAD_MODELS_FROM_FILES);
		endTime = System.nanoTime();

		System.out.println("Time to calculate Neighbor-Joining Hierarchical Cluster: " + ((endTime - startTime)/1000000.0) + "ms");

		neighborCluster = null;

		return;
	}
}
