import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;

class Node implements Comparable<Node> {
    String name;
    Map<Node, Integer> neighbors;

    Node(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    void addNeighbor(Node neighbor, int distance) {
        this.neighbors.put(neighbor, distance);
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(Dijkstra.distances.get(this), Dijkstra.distances.get(other));
    }
}


class Dijkstra {
    static Map<Node, Integer> distances = new HashMap<>();
    static Map<Node, Node> previousNodes = new HashMap<>();

    static void computeShortestPaths(Node source) {
        distances.put(source, 0);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            for (Map.Entry<Node, Integer> neighborEntry : currentNode.neighbors.entrySet()) {
                Node neighbor = neighborEntry.getKey();
                Integer edgeWeight = neighborEntry.getValue();
                Integer alternativePathDistance = distances.get(currentNode) + edgeWeight;

                if (alternativePathDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, alternativePathDistance);
                    previousNodes.put(neighbor, currentNode);
                    priorityQueue.add(neighbor);
                }
            }
        }
    }

    static void printShortestPath(Node destination) {
        if (distances.get(destination) == null) {
            System.out.println("No path found.");
            return;
        }

        Node currentNode = destination;
        while (currentNode != null) {
            System.out.print(currentNode.name + " ");
            currentNode = previousNodes.get(currentNode);
        }
        System.out.println();
        System.out.println("Distance: " + distances.get(destination));
    }
}

public class MapNavigator {
    public static void main(String[] args) {
        Node newDelhi = new Node("New Delhi");
        Node mumbai = new Node("Mumbai");
        Node bangalore = new Node("Bangalore");
        Node chennai = new Node("Chennai");
        Node kolkata = new Node("Kolkata");

        newDelhi.addNeighbor(mumbai, 1400);
        newDelhi.addNeighbor(kolkata, 1500);
        mumbai.addNeighbor(bangalore, 1000);
        bangalore.addNeighbor(chennai, 350);
        kolkata.addNeighbor(chennai, 1650);

        Dijkstra.computeShortestPaths(newDelhi);
        System.out.println("Shortest path from New Delhi to Chennai:");
        Dijkstra.printShortestPath(chennai);
    }
}
