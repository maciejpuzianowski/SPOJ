import java.net.ConnectException;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
        var routeArr = new int[route.length];
        for (int i = 0; i < route.length; i++){
            routeArr[i] = Integer.parseInt(route[i]);
        }

        //map
        List<int[]> map = new ArrayList<>();
        int iterations = streets;
        while(iterations-- > 0){
            var connection = sc.nextLine().split(" ");
            var details = new int[3];
            for (int i = 0; i < 3; i++){
                details[i] = Integer.parseInt(connection[i]);
            }
            map.add(details);
        }

        var itinerary = new Itinerary(intersections, streets, lukasStartingPoint, lukasDestination, differenceInStartingTimes, georgesIntersections,routeArr, map);
        itinerary.time();

    }
}

class Itinerary{
    private int Intersections;
    private int Streets;
    private final Node LukasStartingPoint;
    private final Node LukasDestination;
    private final int DifferenceInStartingTimes;
    private final int GeorgesIntersections;
    private final Node[] GeorgesRoute;
    private final Graph Map;

    public Itinerary(int intersections, int streets, int lukasStartingPoint, int lukasDestination, int differenceInStartingTimes, int georgesIntersections, int[] georgesRoute, List<int[]> map) {
        Intersections = intersections;
        Streets = streets;
        LukasStartingPoint = new Node(lukasStartingPoint);
        LukasDestination = new Node(lukasDestination);
        DifferenceInStartingTimes = differenceInStartingTimes;
        GeorgesIntersections = georgesIntersections;
        GeorgesRoute = new Node[GeorgesIntersections];
        for(int i = 0; i < GeorgesIntersections; i++){
            GeorgesRoute[i] = new Node(georgesRoute[i]);
        }
        Map = new Graph(Intersections);
        initializeGraph(map);
    }

    private void initializeGraph(List<int[]> map){
        for (int i = 0; i < Streets; ){
            var currRoute = map.get(i);
            var tryGet = Map.getNode(currRoute[0], Map.getNodes());
            var node = tryGet != null ? tryGet : new Node(currRoute[0]);
            do {
                currRoute = map.get(i);
                var newNode = new Node(currRoute[1], node, currRoute[2]);
                node.addNeighbours(newNode);
                Map.AllNodes.add(newNode);
            }while(++i<Streets && map.get(i)[0] == currRoute[0]);
            if(tryGet == null) {
                Map.addNode(node);
                Map.AllNodes.add(node);
            }
        }
    }

    private Node nodeInMap(Node node){
        return Map.getNode(node.getName(), Map.getNodes());
    }
    public void time(){
        int result = -DifferenceInStartingTimes;
        int i = 0;
        var currentStreet = new Node[2];

        int lu = 0;
        var lukasRoute = Map.shortestPathBetween(nodeInMap(LukasStartingPoint), nodeInMap(LukasDestination));

        for(; lu < lukasRoute.size()-1; i++){
            if(i+1 < GeorgesIntersections){
                currentStreet = new Node[]{GeorgesRoute[i], GeorgesRoute[i + 1]};
            }
            if(i+1 < GeorgesIntersections && (result < 0 || (lukasRoute.get(lu).equals(currentStreet[0]) && lukasRoute.get(lu+1).equals(currentStreet[1])))) {
                result += Map.pathTime(Map.shortestPathBetween(nodeInMap(GeorgesRoute[i]), nodeInMap(GeorgesRoute[i+1])));
            } else if(i+1 < GeorgesIntersections && (result < 0 || (lukasRoute.get(lu).equals(currentStreet[0]) && !lukasRoute.get(lu+1).equals(currentStreet[1])))){
                result += 1;
            }
            if(result > 0 && lukasRoute.get(lu+1).equals(currentStreet[1]) && lu == 0){
                var path = Map.shortestPathBetween(lukasRoute.get(lu), lukasRoute.get(lu+1));
                result -= Map.pathTime(path);
                lu++;
            } else if (result > 0 && lukasRoute.get(lu).equals(currentStreet[0])) {
                var path = Map.shortestPathBetween(lukasRoute.get(lu), lukasRoute.get(lu+1));
                result += Map.pathTime(path) + 1;
                lu++;
            } else if (result > 0 && !lukasRoute.get(lu).equals(currentStreet[0]) && !lukasRoute.get(lu+1).equals(currentStreet[1])){
                var path = Map.shortestPathBetween(lukasRoute.get(lu), lukasRoute.get(lu+1));
                result += Map.pathTime(path);
                lu++;
            }
        }

        System.out.println(result);
    }

}

class Graph {

    private final List<Node> Nodes;
    public final List<Node> AllNodes;
    private final int Intersections;
    public Graph(int intersections) {
        Nodes = new CopyOnWriteArrayList<>();
        AllNodes = new CopyOnWriteArrayList<>();
        Intersections = intersections;
    }

    public Node getNode(int name, List<Node> list){
        for (var node : list) {
            if (node.getName() == name) return node;
            for (var innerNode : node.getNeighbours()) {
                if (innerNode.getName() == name) return innerNode;
                var rest = innerNode.getNeighbours();
                rest.remove(node);
                if (getNode(name, rest) != null) {
                    var result = getNode(name, rest);
                    rest.add(node);
                    return result;
                }
                rest.add(node);
            }
        }
        return null;
    }

    public void addNode(Node nodeA) {
        Nodes.add(nodeA);
    }

    public int pathTime(List<Node> list){
        int result = 0;
        for(var n: list) result += n.getTempDist();
        return result;
    }

    private int timeBetweenNeighbours(Node source, Node target){
        return source.getName() < target.getName() ? target.getDistanceFromSuperNode() : source.getDistanceFromSuperNode();
    }

    public void prepareForDijkstra(){
        for(var node: AllNodes) {
            node.reset();
        }
    }

    public List<Node> shortestPathBetween(Node source, Node target){
        prepareForDijkstra();
        List<Node> allNodes = new ArrayList<>(AllNodes);
        getNode(source.getName(), Nodes).setTempDist(0);

        while(!allNodes.isEmpty()){
            Node u = allNodes.stream().min(Comparator.comparingInt(Node::getTempDist)).get();
            if(u.equals(target)) break;
            allNodes.remove(u);
            for (Node v: u.getNeighbours()){
                if(allNodes.contains(v)){
                    int alt = u.getTempDist() + timeBetweenNeighbours(u,v);
                    if(alt < v.getTempDist() && u.getTempDist() != Integer.MAX_VALUE){
                        v.setTempDist(alt);
                        v.setTempPrev(u);
                    }
                }
            }
        }

        var result = new CopyOnWriteArrayList<Node>();
        Node u = target;
        if(u.getTempPrev() != null || u.equals(source)){
            while(u != null){
                result.add(u);
                u = u.getTempPrev();
            }
        }
        Collections.reverse(result);
        return result;
    }

    public List<Node> getNodes() {
        return Nodes;
    }
}

class Node {

    private final int Name;

    private final List<Node> Neighbours;
    public void addNeighbours(Node node){
        Neighbours.add(node);
    }

    public List<Node> getNeighbours() {
        return Neighbours;
    }

    private int DistanceFromSuperNode = 0;

    public int getDistanceFromSuperNode() {
        return DistanceFromSuperNode;
    }

    public void setDistanceFromSuperNode(int distanceFromSuperNode) {
        DistanceFromSuperNode = distanceFromSuperNode;
    }

    private int TempDist = Integer.MAX_VALUE;

    public int getTempDist() {
        return TempDist;
    }

    public void setTempDist(int tempDist) {
        TempDist = tempDist;
    }

    public Node getTempPrev() {
        return TempPrev;
    }

    public void setTempPrev(Node tempPrev) {
        TempPrev = tempPrev;
    }

    private Node TempPrev;

    public int getName() {
        return Name;
    }

    public Node(int name) {
        this.Name = name;
        Neighbours = new CopyOnWriteArrayList<>();
    }

    public Node(int name, Node superNode, int distanceFromSuperNode) {
        this.Name = name;
        DistanceFromSuperNode = distanceFromSuperNode;
        Neighbours = new CopyOnWriteArrayList<>();
        Neighbours.add(superNode);
    }

    public void reset(){
        TempPrev = null;
        TempDist = Integer.MAX_VALUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Name == node.Name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }
}