package com.entis.service;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;

public class TownsPrice {

    public String findWaysInGraph(ArrayList<String> stringGraph) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        ArrayList<String> vertexes = new ArrayList<>();
        ArrayList<String[]> allEdges = new ArrayList<>();

        String[] edgesOfCity;
        int countVertexes = Integer.parseInt(stringGraph.get(0));
        ArrayList<Integer> countEdgesOfEveryCity = new ArrayList<>();

        int countEdgesOfCity;
        int endIndex = 0;
        for (int i = 1; i < stringGraph.size(); i++) {
            vertexes.add(stringGraph.get(i));
            countEdgesOfCity = (Integer.parseInt((stringGraph.get(i + 1))));
            countEdgesOfEveryCity.add(countEdgesOfCity);
            i += 2;
            edgesOfCity = new String[countEdgesOfCity];
            for (int j = 0; j < edgesOfCity.length; i++) {
                edgesOfCity[j] = stringGraph.get(i);
                j++;
            }
            allEdges.add(edgesOfCity);
            countVertexes--;
            i--;
            if (countVertexes == 0) {
                endIndex = i + 1;
                break;
            }
        }

        countVertexes = Integer.parseInt(stringGraph.get(0));
        for (int i = 0; i < countVertexes; i++) {
            graph.addVertex(vertexes.get(i));
        }

        String vertexTarget;
        double weight;
        DefaultWeightedEdge edge;

        for (int i = 0; i < countVertexes; i++) {
            for (int j = 0; j < countEdgesOfEveryCity.get(i); j++) {
                String[] edgeString = allEdges.get(i)[j].split(" ");
                vertexTarget = vertexes.get(Integer.parseInt(edgeString[0]) - 1);
                weight = Double.parseDouble(edgeString[1]);
                if (!graph.containsEdge(vertexes.get(i), vertexTarget)) {
                    edge = graph.addEdge(vertexes.get(i), vertexTarget);
                    graph.setEdgeWeight(edge, weight);
                }
            }
        }
        return findWay(graph, stringGraph.subList(endIndex, stringGraph.size()));
    }

    private String findWay(Graph<String, DefaultWeightedEdge> graph, List<String> findWay) {
        int countFoundWays = Integer.parseInt(findWay.get(0));

        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPath
                = new DijkstraShortestPath<>(graph);

        double weight;
        String[] sourceAndTarget;
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < countFoundWays + 1; i++) {
            sourceAndTarget = findWay.get(i).split(" ");
            weight = dijkstraShortestPath.getPath(sourceAndTarget[0], sourceAndTarget[1])
                    .getWeight();
            output.append(weight).append("\n");
        }
        return output.toString();
    }
}
