import java.util.*;

public class GEORGE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //intersections and streets
        var split = sc.nextLine().split(" ");
        int intersections = Integer.parseInt(split[0]);
        int streets = Integer.parseInt(split[1]);

        //itinerary data
        var data = sc.nextLine().split(" ");
        int lukasStartingPoint = Integer.parseInt(data[0]);
        int lukasDestination = Integer.parseInt(data[1]);
        int differenceInStartingTimes = Integer.parseInt(data[2]);
        int georgesIntersections = Integer.parseInt(data[3]);

        //route
        var route = sc.nextLine().split(" ");
        var routeArr = new int[georgesIntersections];
        for (int i = 0; i < georgesIntersections; i++){
            routeArr[i] = Integer.parseInt(route[i]);
        }

        //map
        int[][] map = new int[streets][3];
        for(int i = 0; i < streets; i++){
            var details = sc.nextLine().split(" ");
            map[i][0] = Integer.parseInt(details[0]); //start
            map[i][1] = Integer.parseInt(details[1]); //stop
            map[i][2] = Integer.parseInt(details[2]); //duration
        }

        var itinerary = new Itinerary(intersections, streets, lukasStartingPoint, lukasDestination, differenceInStartingTimes, georgesIntersections, routeArr, map);
        itinerary.time();

    }
}

class Itinerary{
    private final int Intersections;
    private final int Streets;
    private final int LukasStartingPoint;
    private final int LukasDestination;
    private final int DifferenceInStartingTimes;

    private final int GeorgesIntersections;
    private final int[] GeorgesRoute;
    private Graph Map;

    public Itinerary(int intersections, int streets, int lukasStartingPoint, int lukasDestination, int differenceInStartingTimes, int georgesIntersections, int[] georgesRoute, int[][] map) {
        Intersections = intersections;
        Streets = streets;
        LukasStartingPoint = lukasStartingPoint;
        LukasDestination = lukasDestination;
        DifferenceInStartingTimes = differenceInStartingTimes;
        GeorgesIntersections = georgesIntersections;
        GeorgesRoute = georgesRoute;
        initializeGraph(map);
    }

    private void initializeGraph(int[][] map){
        var nodes = new Node[Intersections];
        for (int i = 0; i < Intersections; i++){
            nodes[i] = new Node(i+1);
        }

        for(int i = 0; i < Streets; i++){
            var start = map[i][0];
            var stop = map[i][1];
            var duration = map[i][2];

            nodes[start-1].addNeighbours(nodes[stop-1], duration);
            nodes[stop-1].addNeighbours(nodes[start-1], duration);
        }

        Map = new Graph(nodes);
    }

    public void time(){
        int time = -DifferenceInStartingTimes;

        var lukasPath = Map.shortestPathBetween(Map.getNodeByName(LukasStartingPoint), Map.getNodeByName(LukasDestination));

        var georgesPath = new ArrayList<Node>();
        for(var i: GeorgesRoute){
            georgesPath.add(Map.getNodeByName(i));
        }

        int i = 0; //georges path index
        int j = 0; //lukas path index
        int[] georgeCurrentStreet = new int[2];
        int[] lukasCurrentStreet = new int[2];
        int georgeCurrentStreetDuration = -1;
        int lukasCurrentStreetDuration = -1;

        while(true){
            //george starting first -- getting his route
            if(i+1 < GeorgesIntersections && georgeCurrentStreetDuration <= 0) {
                var sub = georgesPath.subList(i, i + 2);
                georgeCurrentStreet[0] = sub.get(0).getName();
                georgeCurrentStreet[1] = sub.get(1).getName();
                georgeCurrentStreetDuration = Map.pathDuration(sub);
                i++;
            }

            //if it is time for luka to start -- getting his route
            if(time >= 0 && lukasCurrentStreetDuration <= 0){
                var sub = lukasPath.subList(j, j + 2);
                if(georgeCurrentStreet[0] != sub.get(1).getName()) {
                    lukasCurrentStreet[0] = sub.get(0).getName();
                    lukasCurrentStreet[1] = sub.get(1).getName();
                    lukasCurrentStreetDuration = Map.pathDuration(sub);
                    j++;
                }
            }

            //if luka is on his way and his starting point is george`s destination -- able to travel
            if (lukasCurrentStreetDuration > 0 && lukasCurrentStreet[0] == georgeCurrentStreet[1])
                lukasCurrentStreetDuration--;

            //if luka is on his way and his starting point is not george`s starting point, but they have same destination
            //then we are able to travel only if george will arrive later than us
            else if (lukasCurrentStreetDuration > 0 && lukasCurrentStreet[0] != georgeCurrentStreet[0] && lukasCurrentStreet[1] == georgeCurrentStreet[1] && georgeCurrentStreetDuration > 0
            && georgeCurrentStreetDuration > lukasCurrentStreetDuration)
                lukasCurrentStreetDuration--;

            //if luka is on his way, and they are not disturbing each other
            else if (lukasCurrentStreetDuration > 0 && lukasCurrentStreet[0] != georgeCurrentStreet[0] && lukasCurrentStreet[1] != georgeCurrentStreet[1])
                lukasCurrentStreetDuration--;

            //if luka is on his way, and they have same starting points and different destinations -- we are able to travel only if george will arrive later than us
            else if (lukasCurrentStreetDuration > 0 && lukasCurrentStreet[0] == georgeCurrentStreet[0] && lukasCurrentStreet[1] != georgeCurrentStreet[1] && georgeCurrentStreetDuration > 0)
                lukasCurrentStreetDuration--;

            //if luka is on his way, and they are on the same street -- we can travel only if luka is already on that street
            else if (lukasCurrentStreetDuration > 0 && lukasCurrentStreet[0] == georgeCurrentStreet[0] && lukasCurrentStreet[1] == georgeCurrentStreet[1] && georgeCurrentStreetDuration > lukasCurrentStreetDuration)
                lukasCurrentStreetDuration--;

            //if luka is on his way, and george has finished his journey - luka is free to go
            else if (lukasCurrentStreetDuration > 0 && georgeCurrentStreet[1] == georgesPath.get(georgesPath.size()-1).getName() && georgeCurrentStreetDuration <= 0) lukasCurrentStreetDuration--;

            //luka end
            if (lukasCurrentStreet[1] == LukasDestination && lukasCurrentStreetDuration == 0) break;


            georgeCurrentStreetDuration--;
            time++;
        }
        //last second
        time++;
        System.out.println(time);
    }

}

class Graph {

    private final Node[] Nodes;
    public Graph(Node[] nodes) {
        Nodes = nodes;
    }
    public Node getNodeByName(int name){
        return Nodes[name-1];
    }

    /*
        Dijkstra`s algorithm
     */
    public List<Node> shortestPathBetween(Node source, Node target){
        var nodesLen = Nodes.length;
        var dist = new int[nodesLen];
        var prev = new Node[nodesLen];
        var allNodes = new ArrayList<Node>();

        for(int i = 0; i < nodesLen; i++){
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
            allNodes.add(Nodes[i]);
        }
        dist[source.getName()-1] = 0;


        while(!allNodes.isEmpty()){
            int distMin = Integer.MAX_VALUE;

            for (Node node : allNodes) {
                distMin = Math.min(distMin, dist[node.getName() - 1]);
            }

            int nodeId = -1;

            for(var n: allNodes){
                if(dist[n.getName()-1] == distMin){
                    nodeId = n.getName()-1;
                    break;
                }
            }

            var u = Nodes[nodeId];

            if(u.equals(target)) break;

            allNodes.remove(u);

            var uNeighbours = u.getNeighbours().keySet();

            for (Node v: uNeighbours){
                if(allNodes.contains(v)){
                    int alt = dist[nodeId] + u.getNeighbours().get(v);
                    if(alt < dist[v.getName()-1] && dist[nodeId] != Integer.MAX_VALUE){
                        dist[v.getName()-1] = alt;
                        prev[v.getName()-1] = u;
                    }
                }
            }
        }

        var result = new ArrayList<Node>();
        Node u = target;
        if(prev[u.getName()-1] != null || u.equals(source)){
            while(u != null){
                result.add(u);
                u = prev[u.getName()-1];
            }
        }
        Collections.reverse(result);
        return result;
    }

    public int pathDuration(List<Node> path){
        int result = 0;
        for(int i = 0; i < path.size()-1; i++){
            result += path.get(i).getNeighbours().get(path.get(i+1));
        }
        return result;
    }
}

class Node {

    private final int Name;
    private final HashMap<Node, Integer> Neighbours;
    public void addNeighbours(Node node, int distance){
        Neighbours.put(node, distance);
    }

    public HashMap<Node, Integer> getNeighbours() {
        return Neighbours;
    }

    public int getName() {
        return Name;
    }

    public Node(int name) {
        this.Name = name;
        Neighbours = new HashMap<>();
    }

}