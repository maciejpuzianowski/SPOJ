package problems;

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
        System.out.println(itinerary.time());

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

    public int time(){
        int time = -DifferenceInStartingTimes;
        var blockedStreetsTime = new int[GeorgesIntersections-1][3];

        for(int i = 0; i < GeorgesIntersections-1; i++){
            blockedStreetsTime[i][0] = GeorgesRoute[i];
            blockedStreetsTime[i][1] = GeorgesRoute[i+1];
            blockedStreetsTime[i][2] = Map.getNodeByName(GeorgesRoute[i]).getNeighbours().get(Map.getNodeByName(GeorgesRoute[i+1]));
        }

        var lukasPath = Map.shortestPathBetween(Map.getNodeByName(LukasStartingPoint), Map.getNodeByName(LukasDestination));
        var currentLukasStreet = lukasPath.subList(0, 2);
        var endingLukasStreet = lukasPath.subList(lukasPath.size()-2, lukasPath.size());

        int georgesIndex = 0;
        int georgesTime = 0;
        int lukasIndex = 1;
        int lukasTime = 0;
        while(true){
//            georgesTime++;
            if(georgesIndex + 1 < GeorgesIntersections - 1 && georgesTime == blockedStreetsTime[georgesIndex][2]){
                georgesIndex++;
                georgesTime = 0;
            }

            if(lukasTime >= Map.pathDuration(currentLukasStreet) && lukasIndex + 1 < lukasPath.size()) {

                var lukasStart = currentLukasStreet.get(0).getName();
                var lukasStop = currentLukasStreet.get(1).getName();
                var georgeStart = blockedStreetsTime[georgesIndex][0];
                var georgeStop = blockedStreetsTime[georgesIndex][1];

                if (    (georgesTime > blockedStreetsTime[georgesIndex][2]) ||
                        (lukasStop == georgeStart && georgesTime >= 0) ||
                        (georgeStart == lukasStart) ||
                        (lukasStop == georgeStop && lukasPath.get(lukasIndex+1).getName() != georgeStart && georgesTime <= blockedStreetsTime[georgesIndex][2]) ||
                        (lukasStop != georgeStop && lukasStop != georgeStart)
                ) {
                    currentLukasStreet = lukasPath.subList(lukasIndex, lukasIndex + 2);
                    lukasTime = 0;
                    lukasIndex++;
                }

            }

            boolean sameStreet = currentLukasStreet.get(0).getName() == blockedStreetsTime[georgesIndex][0] && currentLukasStreet.get(1).getName() == blockedStreetsTime[georgesIndex][1];
            if (time >= 0 && lukasTime < Map.pathDuration(currentLukasStreet)) {
                if((!sameStreet || georgesTime >= blockedStreetsTime[georgesIndex][2] || georgesTime < lukasTime))
                    lukasTime++;
            }

            if(lukasTime == Map.pathDuration(currentLukasStreet) && currentLukasStreet.get(0) == endingLukasStreet.get(0) && currentLukasStreet.get(1) == endingLukasStreet.get(1)) break;
            georgesTime++;
            time++;
        }
        time++;
        return time;
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