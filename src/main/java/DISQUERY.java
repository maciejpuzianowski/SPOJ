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

        new RoadMap(streets, cities, journeys);

    }
}

class RoadMap{

    private City[] Map;
    private int[][] Route;

    public RoadMap(String[] map, int cities, String[] journeys) {
        build(map, cities);
        getEulersRoute(cities);
        for(var s: journeys) {
            printResults(s);
        }
    }

    private void printResults(String s) {
        var split = s.split(" ");
        var start = Integer.parseInt(split[0]);
        var stop = Integer.parseInt(split[1]);
        int max = 0;
        int min = Integer.MAX_VALUE;
        int lca = Integer.MAX_VALUE;
        int startingIndex = 0;
        int stoppingIndex = 0;
        for(int i = 0; i < Route.length; i++){
            if(Route[i][0] == start ) {
                startingIndex = i;
                for (int k = startingIndex; k < Route.length; k++) {
                    if(Route[k][0] == start) {
                        lca = Integer.MAX_VALUE;
                        startingIndex = k;
                    }
                    if(Route[i][0] == stop) {
                        lca = Integer.MAX_VALUE;
                        stoppingIndex = i;
                    }
                    lca = Math.min(lca, Route[k][1]);
                    if (Route[k][0] == stop) {
                        stoppingIndex = k;
                        break;
                    }
                }
                if(stoppingIndex < startingIndex){
                    var temp = stoppingIndex;
                    stoppingIndex = startingIndex;
                    startingIndex = temp;
                }
                break;
            }
            if(Route[i][0] == stop) {
                stoppingIndex = i;
            }
        }
        for(int i = startingIndex; i <= stoppingIndex; i++){
            if(lca < Route[i][1]) {
                min = Math.min(min, Map[Route[i][0] - 1].getDistanceFromRoot());
                max = Math.max(max, Map[Route[i][0] - 1].getDistanceFromRoot());
            }
        }
        System.out.println(min +" "+ max);
    }

    public void build(String[] journeys, int citiesNo) {
        Map = new City[citiesNo];
        for(int i = 0; i < citiesNo; i++){
            Map[i] = new City(i+1);
        }

        for (int i = journeys.length-1; i >= 0; i--){
            var split = journeys[i].split(" ");
            int root = Integer.parseInt(split[0]);
            int child = Integer.parseInt(split[1]);
            int distance = Integer.parseInt(split[2]);

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

    public void getEulersRoute(int cities){
        Route = new int[(cities*2)-1][2];
        var city = Map[0];
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
