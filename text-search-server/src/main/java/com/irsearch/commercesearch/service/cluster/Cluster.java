package com.irsearch.commercesearch.service.cluster;

import com.irsearch.commercesearch.init.JSONParser;
import com.irsearch.commercesearch.model.SearchClusterResults;

import com.irsearch.commercesearch.model.SearchEntity;

import java.io.*;
import java.util.*;

import com.irsearch.commercesearch.model.ClusterEntity;
import com.irsearch.commercesearch.service.queryretrival.Searcher;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cluster {
    HashMap<String, Double[]> clusterAssignments;
    HashMap<Integer, String> clusterTitles;
    HashMap<Integer, TreeMap<Double, String>> bestDocuments;

    public Cluster() {
        /*
         * clusterAssignments contains the url and a Double[] containing [clusterAssignment, distance]
         */
        clusterAssignments = new HashMap<String, Double[]>();
        clusterTitles = new HashMap<Integer, String>();
        bestDocuments = new HashMap<Integer, TreeMap<Double, String>>();
    }

    public Object loadModel(String modelFileName) {
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

    public void setClusterAssignments(HashMap<String, Double[]> clusterAssignments) {
        this.clusterAssignments = clusterAssignments;
    }

    public void setClusterAssignments(String fileName) {
        this.clusterAssignments = (HashMap<String, Double[]>) loadModel(fileName);
    }

    public void setBestDocuments(String fileName) {
        this.bestDocuments = (HashMap<Integer, TreeMap<Double, String>>) loadModel(fileName);
    }

    public void setBestDocuments(HashMap<Integer, TreeMap<Double, String>> bestDocuments) {
        this.bestDocuments = bestDocuments;
    }

    public void setClusterTitles(HashMap<Integer, String> clusterTitles) {
        this.clusterTitles = clusterTitles;
    }

    public void setClusterTitles(String clusterTitles) {
        this.clusterTitles = (HashMap<Integer, String>) loadModel(clusterTitles);
    }

    public void printClusterTitles() {
        for (Map.Entry<Integer, String> entry: clusterTitles.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    public void printClusterAssignments() {
        for (Map.Entry<String, Double[]> entry: clusterAssignments.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue()[0] + ", " + entry.getValue()[1]);
        }
    }

    public void printBestDocuments() {
        for (Map.Entry<Integer, TreeMap<Double, String>> entry: bestDocuments.entrySet()) {
            System.out.println("DISTANCES FOR CLUSTER #" + entry.getKey());
            for (Map.Entry<Double, String> entry2: entry.getValue().entrySet()) {
                System.out.println(entry2.getKey() + " => " + entry2.getValue());
            }
        }
    }

    public SearchClusterResults addResults(Vector<String> results) {
        List<SearchEntity> resultList = new ArrayList<SearchEntity>();
        for (String result : results) {
            resultList.add(new SearchEntity(result, "", ""));
        }
        return addResults(resultList);
    }

    public SearchClusterResults addResults(List<SearchEntity> results) {

        List<ClusterEntity> clusterEntities = new ArrayList<ClusterEntity>();
        List<SearchEntity> searchEntities = new ArrayList<SearchEntity>();

        HashMap<Integer, Integer> clusterSize = new HashMap<Integer,Integer>();

        HashMap<String, Double> newRankings = new HashMap<String,Double>();

        double currentRanking = 1.0;
        for (SearchEntity result : results) {
            if (!clusterAssignments.containsKey(result.getUrl())) {
                continue;
            }

            int currentCluster = clusterAssignments.get(result.getUrl())[0].intValue();

            currentRanking = (clusterAssignments.get(result.getUrl())[1] / bestDocuments.get(currentCluster).firstKey()) +
                    ((double)results.size() / currentRanking);

            if (newRankings.containsKey(result.getUrl())) {
                newRankings.put(result.getUrl(), newRankings.get(result.getUrl() + currentRanking));
            } else {
                newRankings.put(result.getUrl(), currentRanking);
            }

            currentRanking++;

            if (clusterSize.containsKey(currentCluster)) {
                clusterSize.put(currentCluster, clusterSize.get(currentCluster) + 1);
                continue;
            }

            TreeMap<Double, String> clusterBest = bestDocuments.get(currentCluster);
            double veryBest = clusterBest.firstKey();


            int docsToAdd = 5;
            for (Map.Entry<Double, String> topDocs : clusterBest.entrySet()) {
                if (newRankings.containsKey(topDocs.getValue())) {
                    newRankings.put(topDocs.getValue(), newRankings.get(topDocs.getValue())
                            + (topDocs.getKey() / veryBest));
                } else {
                    newRankings.put(topDocs.getValue(), (topDocs.getKey() / veryBest));
                }
                docsToAdd--;

                if (docsToAdd < 0) {
                    break;
                }
            }

            ClusterEntity clusterEntity = new ClusterEntity();
            clusterEntity.setTitle(clusterTitles.get(currentCluster));
            clusterEntity.setClusterNo(currentCluster);
            clusterEntities.add(clusterEntity);
            clusterSize.put(currentCluster, 1);
        }

        TreeMap<Double, SearchEntity> rankedResults = new TreeMap<Double,SearchEntity>(Collections.reverseOrder());

        for (SearchEntity result : results) {
            rankedResults.put(newRankings.get(result.getUrl()), result);
            newRankings.remove(result.getUrl());
        }

        for (Map.Entry<String, Double> entry : newRankings.entrySet()) {
            // Ram - need to do lookup here
            if (JSONParser.clusterMap.containsKey(entry.getKey())) {
                rankedResults.put(entry.getValue(), new SearchEntity(entry.getKey(),
                        JSONParser.clusterMap.get(entry.getKey()).getTitle(),
                        JSONParser.clusterMap.get(entry.getKey()).getDescription()));
            } else {
//                List<SearchEntity> seL = Searcher.searchIndexByURL(entry.getKey());
//                if (seL.size() > 0) {
//                    rankedResults.put(entry.getValue(), seL.get(0));
//                }
            }
        }

        List<SearchEntity> finalResults = new ArrayList<SearchEntity>(rankedResults.values());

        SearchClusterResults searchClusterResults = new SearchClusterResults();

        searchClusterResults.setClusters(clusterEntities);
        searchClusterResults.setResults(finalResults);
        searchClusterResults.setResultCount(finalResults.size());

        searchClusterResults.setClusters(clusterEntities);

        return searchClusterResults;
    }

    public void generateGraphJSONAll() {
        int test = 0;
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> entry: clusterTitles.entrySet()) {
            JSONArray ja = new JSONArray();
            JSONObject root = new JSONObject();
            root.put("name", entry.getValue());
            root.put("size", bestDocuments.get(entry.getKey()).size());
            ja.put(root);

            TreeMap<Double, String> documents = bestDocuments.get(entry.getKey());
            JSONArray children = new JSONArray();
            int test2 = 0;
            for (Map.Entry<Double, String> docEntry : documents.entrySet()) {
                JSONObject child = new JSONObject();
                child.put("name", docEntry.getValue());
                child.put("size", docEntry.getKey());
                children.put(child);
                test2++;
                if (test2 > 1) {
                    break;
                }
            }
            JSONObject temp = new JSONObject();
            temp.put("children", children);
            ja.put(temp);
            System.out.println(ja.toString());
            jsonArray.put(ja);
            test++;

            if (test > 1) {
                break;
            }
        }

        ClusterFileUtil.saveModel("testingJSON.json", jsonArray.toString());
        System.out.println(jsonArray.toString());
    }

    public Vector<String> generateClusterQueries() {
        int queriesToGenerate = 500;
        int queryLenth = 3;
        Vector<String> queries = new Vector<String>();
        Vector<String> titleWords = new Vector<String>();

        for (Map.Entry<Integer, String> entry: clusterTitles.entrySet()) {
            titleWords.addAll(Arrays.asList(entry.getValue().split(" ")));
        }

        HashSet h = new HashSet(titleWords);
        titleWords.clear();
        titleWords.addAll(h);

        Collections.sort(titleWords);

        for (int i = 0; i < titleWords.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(titleWords.get(i));
            sb.append(" ");
            sb.append(titleWords.get((int) (Math.random() * titleWords.size())));
            sb.append(" ");
            sb.append(titleWords.get((int) (Math.random() * titleWords.size())));
            queries.add(sb.toString());
        }
        return queries;
    }

    public void updateClusterTitles(String saveModelFile) {
        for (Map.Entry<Integer, String> entry: clusterTitles.entrySet()) {
            boolean badTitle = true;
            String newTitle = entry.getValue();
            while (badTitle) {
                try {
                    System.out.println(entry.getValue());
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String proposedTitle = br.readLine();
                    String[] titleWords = proposedTitle.split(" ");
                    for (String titleWord : titleWords) {
                        if (!entry.getValue().contains(titleWord)) {
                            continue;
                        }
                        newTitle = proposedTitle;
                        badTitle = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            clusterTitles.put(entry.getKey(), newTitle);
            ClusterFileUtil.saveModel(saveModelFile, clusterTitles);
        }

    }
}