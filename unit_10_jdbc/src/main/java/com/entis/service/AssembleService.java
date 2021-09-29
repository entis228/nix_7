package com.entis.service;

import com.entis.dao.ConnectionDao;
import com.entis.entity.Location;
import com.entis.entity.Problem;
import com.entis.entity.Route;
import com.entis.entity.Solution;

import java.util.ArrayList;
import java.util.List;

public class AssembleService {

    private final TownsPrice price = new TownsPrice();

    public void assemble() {
        try (ConnectionDao dao = new ConnectionDao()) {
            List<Location> locations = dao.getLocations();
            List<Problem> problems = dao.getProblems();
            List<Route> routes = dao.getRoutes();
            ArrayList<String> input = new ArrayList<>();
            input.add(String.valueOf(locations.size()));
            for (Location currentLoc : locations) {
                input.add(currentLoc.name());
                List<Route> neighbours = new ArrayList<>();
                for (Route currentRoute : routes) {
                    if (currentRoute.from_id() == currentLoc.id()) {
                        neighbours.add(currentRoute);
                    }
                }
                input.add(String.valueOf(neighbours.size()));
                for (Route n : neighbours) {
                    input.add(n.to_id() + " " + n.cost());
                }
            }
            input.add(String.valueOf(problems.size()));
            for (Problem problem : problems) {
                input.add(locations.get(problem.from_id() - 1).name() + " " + locations.get(problem.to_id() - 1).name());
            }
            String[] results = price.findWaysInGraph(input).split("\n");
            List<Solution> solutions = new ArrayList<>();
            for (int i = 0; i < results.length; i++) {
                int cost = (int) Double.parseDouble(results[i]);
                solutions.add(new Solution(problems.get(i).id(), cost));
            }
            dao.writeSolution(solutions);
        }
    }
}
