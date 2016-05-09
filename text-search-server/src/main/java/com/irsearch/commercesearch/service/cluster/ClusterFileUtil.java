package com.irsearch.commercesearch.service.cluster;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.*;
import java.util.*;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ClusterFileUtil {

	public static void readWebpages(String crawlerDirectory, String saveFile) {

		System.out.println("Reading from directory " + crawlerDirectory);

		File dir = new File(crawlerDirectory);

		if (!dir.isDirectory()) {
			System.out.println("Directory not passed in.  Returning.");
			return;
		}

		FastVector atts = new FastVector();
		atts.addElement(new Attribute("ID"));
		atts.addElement(new Attribute("url", (FastVector) null));
		atts.addElement(new Attribute("body", (FastVector) null));

		Instances data = new Instances("Data", atts, 0);

		try {
			for (File file : dir.listFiles()) {
				System.out.println(file.getName() + " to arff");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				if (file.getName().equalsIgnoreCase(".DS_Store")) {
					continue;
				}
				while ((line = br.readLine()) != null) {
					JSONObject obj = new JSONObject(line);
					//System.out.println(Jsoup.parse(obj.getString("Body")).title());
					double[] vals = new double[3];
					String body = Jsoup.parse(obj.getString("Body")).body()
							.text()
							.replaceAll("\\P{L}", " ")
							.replaceAll("[^\\x00-\\x7F]", "")
							.toLowerCase();

					if (body.length() > 0) {
						vals[0] = data.numInstances();
						vals[1] = data.attribute(1).addStringValue(obj.getString("Url"));
						vals[2] = data.attribute(2).addStringValue(body);
					}
					data.add(new Instance(1.0, vals));
				}
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Saving file: " + saveFile);

		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File(saveFile));
			saver.writeBatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Data converted to arff.");
	}

	public static DataSource getDataSourceFromFile(String dataFileName) {
		String arffFile = dataFileName;

		if (!StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(dataFileName), "arff")) {
			// Try to convert to arff
			arffFile = dataFileName.replace(FilenameUtils.getExtension(dataFileName), "").concat(".arff");
			readWebpages(dataFileName, arffFile);
		}

		try {
			return new DataSource(arffFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Instances getData(DataSource source, boolean process) {
		Instances data = null;
		Instances outputInstances = null;
		StringToWordVector filter = new StringToWordVector();

		Stopwords stopwords = new Stopwords();

		try {
			System.out.println("Loading data");
			data = source.getDataSet();
			filter.setInputFormat(data);

			File f = new File("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterStopWords");
			if (f.exists()) {
				System.out.println("true");
			}

			System.out.println("Processing data");
			filter.setStopwords(new File("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterStopWords"));
			if (process) {
				filter.setOutputWordCounts(true);
				filter.setIDFTransform(true);
				filter.setLowerCaseTokens(true);
				filter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER));
				data = Filter.useFilter(data, filter);
			}
			System.out.println("Done filtering");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Object tryToLoadModel(String modelFileName) {
		File f = new File(modelFileName);
		Object model = null;

		if (f.exists()) {
			try {
				InputStream is = new FileInputStream(modelFileName);
				ObjectInputStream objectInputStream = new ObjectInputStream(is);
				model = objectInputStream.readObject();
				objectInputStream.close();
			} catch (Exception e) {
				// don't care; will recreate the model.
			}
		}

		return model;
	}

	public static void saveModel(String modelFileName, Object o) {
		try {
			OutputStream os = new FileOutputStream(modelFileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
			objectOutputStream.writeObject(o);
			objectOutputStream.close();
		} catch (Exception e) {
			System.out.println("Error saving file.");
			//e.printStackTrace();
		}
	}


	public static HashMap<Integer, String> getClusterTitles(SimpleKMeans kMeans, int titleLength) throws Throwable {

		HashMap<Integer, String> titles = new HashMap<Integer, String>();

		for (int i = 0; i < kMeans.numberOfClusters(); i++) {

			TreeMap<Double, String> importantAttributes = new TreeMap<Double, String>(Collections.reverseOrder());
			for (int j = 0; j < kMeans.getClusterCentroids().numAttributes(); j++) {
				String attributeName = kMeans.getClusterCentroids().attribute(j).name();
				double attributeValue = kMeans.getClusterCentroids().instance(i).value(j);
				importantAttributes.put(attributeValue, attributeName);
			}

			StringBuilder sb = new StringBuilder();

			int MAX = titleLength;
			int COUNT = 0;
			for (Map.Entry<Double, String> entry : importantAttributes.entrySet()) {
				// @TODO manual approving of titles
				sb.append(entry.getValue());

				COUNT++;

				if (COUNT == MAX) {
					break;
				} else {
					sb.append(" ");
				}
			}

			titles.put(i, sb.toString());
		}

		return titles;
	}

	public static void produceJSON(SimpleKMeans kMeans, Vector<String> finalPages, TreeMap<Integer, Integer> clusterCounts) throws JSONException, Exception {
		JSONArray fileJSON = new JSONArray();
		JSONArray pagesArray = new JSONArray();
		JSONArray countsArray = new JSONArray();

		for (String page : finalPages) {
			// do a lookup for title and snippet here
			JSONObject jo = new JSONObject();
			jo.put("url", page);
			pagesArray.put(jo);
		}

		fileJSON.put(pagesArray);

		for (int i = 0; i < kMeans.numberOfClusters(); i++) {

			if (!clusterCounts.containsKey(i)) continue;

			TreeMap<Double, String> importantAttributes = new TreeMap<Double, String>(Collections.reverseOrder());
			for (int j = 0; j < kMeans.getClusterCentroids().numAttributes(); j++) {
				String attributeName = kMeans.getClusterCentroids().attribute(j).name();
				double attributeValue = kMeans.getClusterCentroids().instance(i).value(j);
				importantAttributes.put(attributeValue, attributeName);
			}

			StringBuilder sb = new StringBuilder();

			int MAX = 6;
			int COUNT = 0;
			for (Map.Entry<Double, String> entry : importantAttributes.entrySet()) {
				// @TODO manual approving of titles
				sb.append(entry.getValue());

				COUNT++;

				if (COUNT == MAX) {
					break;
				} else {
					sb.append(" ");
				}
			}

			JSONObject jo = new JSONObject();
			jo.put("Cluster", i);
			jo.put("Title", sb.toString());
			jo.put("Size", clusterCounts.get(i));

			countsArray.put(jo);
			System.out.println("CLUSTER #" + i + " done.");
		}

		fileJSON.put(countsArray);

		try {
			FileWriter file = new FileWriter("./frontend.json");
			file.write(fileJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void produceJsonKMeansClusters(SimpleKMeans kMeans, String jsonFile, int titleCount) {
		JSONArray ja = new JSONArray();

		try {
			for (int i = 0; i < kMeans.numberOfClusters(); i++) {

				TreeMap<Double, String> importantAttributes = new TreeMap<Double, String>(Collections.reverseOrder());
				for (int j = 0; j < kMeans.getClusterCentroids().numAttributes(); j++) {
					String attributeName = kMeans.getClusterCentroids().attribute(j).name();
					double attributeValue = kMeans.getClusterCentroids().instance(i).value(j);
					importantAttributes.put(attributeValue, attributeName);
				}

				StringBuilder sb = new StringBuilder();

				int MAX = titleCount + 1;
				int COUNT = 0;
				for (Map.Entry<Double, String> entry : importantAttributes.entrySet()) {
					// @TODO manual approving of titles
					sb.append(entry.getValue());

					COUNT++;

					if (COUNT == MAX) {
						break;
					} else {
						sb.append(" ");
					}
				}

				JSONObject jo = new JSONObject();
				jo.put("Cluster", i);
				jo.put("Title", sb.toString());
				jo.put("Size", kMeans.getClusterSizes()[i]);

				ja.put(jo);
				//System.out.println("CLUSTER #" + i + " done.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileWriter file = new FileWriter(jsonFile);
			file.write(ja.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
