package problems;

import java.util.*;
import java.util.stream.Collectors;

public class DISQUERY {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cities = 0;
        if(scanner.hasNextLine()){
            cities = Integer.parseInt(scanner.nextLine());
        }

        String[] streets = new String[cities-1];
        for(int i = 0 ; i < cities-1; i++){
            streets[i] = scanner.nextLine();
        }

        int routes = 0;
        if(scanner.hasNextLine()){
            routes = Integer.parseInt(scanner.nextLine());
        }

        String[] journeys = new String[routes];
        for(int i = 0 ; i < routes; i++){
            journeys[i] = scanner.nextLine();
        }

        var roadMap = new RoadMap(streets, cities);
        for(var s: journeys) {
            System.out.println(roadMap.result(s));
        }
    }
}

class RoadMap{

    private City[] Map;
    private int[][] Route;

    public RoadMap(String[] map, int cities) {
        build(map, cities);
        getEulersRoute(cities);

    }

    public String result(String s) {
        //getting route
        var split = s.split(" ");
        var start = Integer.parseInt(split[0]);
        var stop = Integer.parseInt(split[1]);

        //defining needed variables
        int max = 0;
        int min = Integer.MAX_VALUE;
        int lca = Integer.MAX_VALUE;

        //looking for appropriate indices
        var indices = appropriateIndices(start, stop);
        var startingIndex = indices[0];
        var stoppingIndex = indices[1];

        //we want to have starting index lower than stopping
        if(stoppingIndex < startingIndex){
            var temp = stoppingIndex;
            stoppingIndex = startingIndex;
            startingIndex = temp;
        }

        //calculating lowest common ancestor
        for(int i = startingIndex; i <= stoppingIndex; i++){
            lca = Math.min(lca, Route[i][1]);
        }

        //finding the shortest path
        var path = shortestPathToArray(startingIndex, stoppingIndex, lca);

        //looking for min and max then print
        for (int j: path){
            if(j != 0) {
                min = Math.min(min, j);
                max = Math.max(max, j);
            }
        }
        return min +" "+ max;
    }

    private int[] appropriateIndices(int start, int stop){

        int startingIndex = 0;
        int stoppingIndex = 0;

        //looking for appropriate indices for cities by iterating through eulers route
        for(int i = 0; i < Route.length; i++){

            //start
            if(Route[i][0] == start ) {

                startingIndex = i;

                //if we already have stopping index then we break
                if(stoppingIndex != 0) break;

                //looking for stopping index if we have not found yet and
                // checking whether we are going to find starting index closer to the stop
                for (int k = startingIndex+1; k < Route.length; k++) {

                    if(Route[k][0] == start) {
                        startingIndex = k;
                    }

                    if (Route[k][0] == stop) {
                        stoppingIndex = k;
                        break;
                    }

                }
                break;
            }

            //stop
            else if(Route[i][0] == stop) {
                stoppingIndex = i;
            }

        }

        //we want to have starting index lower than stopping
        if(stoppingIndex < startingIndex){
            var temp = stoppingIndex;
            stoppingIndex = startingIndex;
            startingIndex = temp;
        }

        return new int[]{startingIndex, stoppingIndex};
    }

    private int[] shortestPathToArray(int startingIndex, int stoppingIndex, int lca){

        //we are looking for shortest path by getting the path to the lowest common ancestor
        //for both cities

        var node1 = Map[Route[startingIndex][0]-1];
        int nodesRoot1;
        var node2 = Map[Route[stoppingIndex][0]-1];
        int nodesRoot2;
        int maxLca = Route[startingIndex][1];
        var space = Math.max(Route[startingIndex][1], Route[stoppingIndex][1])*2;
        int[] path = new int[space];
        int i = 0;

        //check if they are neighbours
        if(startingIndex + 1 == stoppingIndex){
            return Route[stoppingIndex][1] > Route[startingIndex][1] ? new int[] {Map[Route[stoppingIndex][0]-1].getDistanceFromRoot()} : new int[] {Map[Route[startingIndex][0]-1].getDistanceFromRoot()};
        }

        do{
            if(i == 0 && node1.getRoot() == null){
                path[i++] = node1.getDistanceFromRoot();
                break;
            }
            else {
                nodesRoot1 = node1.getRoot().getName();
            }
            path[i++] = node1.getDistanceFromRoot();
            node1 = Map[nodesRoot1-1];
            if(node1.getRoot() == null) {
                path[i++] = node1.getDistanceFromRoot();
                break;
            }
        }while(--maxLca > lca);

        maxLca = Route[stoppingIndex][1];
        int k = i;
        do{
            if(k == i && node2.getRoot() == null){
                path[k] = node2.getDistanceFromRoot();
                break;
            }
            else {
                nodesRoot2 = node2.getRoot().getName();
            }
            path[k++] = node2.getDistanceFromRoot();
            node2 = Map[nodesRoot2-1];
            if(node2.getRoot() == null) {
                path[k] = node2.getDistanceFromRoot();
                break;
            }
        }while(--maxLca > lca);

        return path;
    }

    //creating graph out of input
    public void build(String[] journeys, int citiesNo) {
        Map = new City[citiesNo];

        //creating cities
        for(int i = 0; i < citiesNo; i++){
            Map[i] = new City(i+1);
        }

        //adding routes between cities
        for (int i = journeys.length-1; i >= 0; i--){
            var split = journeys[i].split(" ");
            int root = Integer.parseInt(split[0]);
            int child = Integer.parseInt(split[1]);
            int distance = Integer.parseInt(split[2]);

            //if child has not root then we change child with root
            if(Map[child-1].getRoot() != null){
                var temp = child;
                child = root;
                root = temp;
            }

            Map[root-1].addChild(Map[child-1]);
            Map[child-1].setRoot(Map[root-1]);
            Map[child-1].setDistanceFromRoot(distance);
        }
    }

    //creating eulers route
    public void getEulersRoute(int cities){
        Route = new int[(cities*2)-1][2];
        City city = Map[0];
        for(City c : Map){
            if(c.getRoot() == null){
                city = c;
                break;
            }
        }
        depthFirstTraversal(city, 0, 1);
    }

    private int depthFirstTraversal(City city, int i, int depth){
        if(i == 0 || (i-1 >= 0 && Route[i-1][0] != city.getName())) {
            Route[i][0] = city.getName();
            Route[i][1] = depth;
            i++;
        }
        depth++;
        for(var c: city.getChildren()){
            Route[i][0] = c.getName();
            Route[i][1] = depth;
            i++;
            i = depthFirstTraversal(c, i, depth);
            depth--;
            Route[i][0] = city.getName();
            Route[i][1] = depth;
            i++;
            depth++;
        }
        return i;
    }
}

class City{
    private final int Name;
    private City Root;
    private int DistanceFromRoot = 0;
    private HashSet<City> Children;

    public HashSet<City> getChildren() {
        return Children;
    }

    public City(int name) {
        Name = name;
        Children = new HashSet<>();
    }

    public void addChild(City child){
        Children.add(child);

        //sorting children by name
        Children = Children.stream().sorted(Comparator.comparingInt(City::getName)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public int getName() {
        return Name;
    }

    public City getRoot() {
        return Root;
    }

    public void setRoot(City root) {
        Root = root;
    }

    public int getDistanceFromRoot() {
        return DistanceFromRoot;
    }

    public void setDistanceFromRoot(int distanceFromRoot) {
        DistanceFromRoot = distanceFromRoot;
    }
}
