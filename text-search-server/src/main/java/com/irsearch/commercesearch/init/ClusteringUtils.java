package com.irsearch.commercesearch.init;


import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.Utils;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.irsearch.commercesearch.service.cluster.ClusterFileUtil;

/**
 * Created by wyatt.chastain on 4/18/16.
 */
public class ClusteringUtils {

	public static SimpleKMeans newKMeans(Instances data, String optionsString, String saveFile) {
		return newKMeans(data, optionsString, saveFile, false);
	}

	public static SimpleKMeans newKMeans(Instances data, String optionsString, String saveFile, boolean loadFromFile) {
		SimpleKMeans model = null;
		if (loadFromFile) {
			model = (SimpleKMeans) ClusterFileUtil.tryToLoadModel(saveFile);
		}

		if (model == null) {
			// @TODO use filter

			try {
				model = new SimpleKMeans();
				model.setOptions(Utils.splitOptions(optionsString));
				System.out.println("Build cluster time!");
				model.buildClusterer(data);
				System.out.println("Cluster built");
				ClusterFileUtil.saveModel(saveFile, model);
			} catch(Exception e) {
				e.printStackTrace();
				model = null;
			}
		}

		return model;
	}

	public static void produceKMeansLabels(SimpleKMeans kMeans, int numLabels) throws Exception {

		for (int i = 0; i < kMeans.numberOfClusters(); i++) {

			TreeMap<Double, String> importantAttributes = new TreeMap<Double, String>(Collections.reverseOrder());
			System.out.println(kMeans.getClusterCentroids().numAttributes());
			for (int j = 0; j < kMeans.getClusterCentroids().numAttributes(); j++) {
				String attributeName = kMeans.getClusterCentroids().attribute(j).name();
				double attributeValue = kMeans.getClusterCentroids().instance(i).value(j);
				importantAttributes.put(attributeValue, attributeName);
			}

			int MAX = numLabels + 1;
			int COUNT = 0;
			for(Map.Entry<Double,String> entry : importantAttributes.entrySet()) {
				Double key = entry.getKey();
				String value = entry.getValue();

				System.out.println(key + " => " + value);

				COUNT++;

				if (COUNT == MAX) {
					break;
				}
			}

			System.out.println("CLUSTER #" + i + " done.");
		}
	}

	public static HierarchicalClusterer newHierarchy(Instances data, String optionsString, String saveFile, boolean loadFromFile) {
		HierarchicalClusterer model = null;
		if (loadFromFile) {
			model = (HierarchicalClusterer) ClusterFileUtil.tryToLoadModel(saveFile);
		}
		if (model == null) {
			try {
				model = new HierarchicalClusterer();
				model.setOptions(Utils.splitOptions(optionsString));
				model.buildClusterer(data);
				ClusterFileUtil.saveModel(saveFile, model);
			} catch(Exception e) {
				e.printStackTrace();
				model = null;
			}
		}

		return model;
	}
}