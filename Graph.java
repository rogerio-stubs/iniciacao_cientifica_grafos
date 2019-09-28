import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Map.Entry;

public class Graph {
    private int E;
    private int V;
    private int adj[][];
    private int degree[];
    private int listOfVertices[];

    // Cria a matriz.
    public void create(int V) {
        this.E = 0;
        this.V = V+1;
        this.adj = new int[V+1][V+1];
        this.degree = new int[V+1];
        this.listOfVertices = new int[V+1];
    }

    // Adiciona as arestas.
    public void addEdge(int u, int v) {
        this.E += 1;
        adj[u][v] = adj[v][u] = 1;
        // Soma 1 para cada aresta existente no vértice.
        degree[u] += 1;
        degree[v] += 1;
        // Armazena um Array com os vértices.
        listOfVertices[u] = u;
        listOfVertices[v] = v;
    }

    public int[] getListOfVertices() {
      return listOfVertices;
    }

    // Devolve o maior grau.
    public int maxDegree(){
      int maior = degree[0];
      for (int x = 1; x < degree.length; x++)
          if (maior < degree[x])
              maior = degree[x];
      return maior;
    }

    // Devolve um ArrayList com os vértices, ordenados de forma descrescente pelos graus.
    public ArrayList<Integer> sortByDegree(){
      ArrayList<Integer> K = new ArrayList<Integer>();
      ArrayList<Map.Entry<Integer, Integer>> degreeVertices =
                  new ArrayList<Map.Entry<Integer, Integer>>(); // ArrayList de Pares

      for(int i = 1; i < V; i++){
        degreeVertices.add(new AbstractMap.SimpleEntry(degree[i], i));
      }

      // Collections.sort(degreeVertices, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
      int min;
      for (int i = 0; i < degreeVertices.size(); i++) {
          min = i;
          for (int j = i; j < degreeVertices.size(); j++) {
              if (degreeVertices.get(j).getKey() > degreeVertices.get(min).getKey())
                  min = j;
          }
          Map.Entry<Integer,Integer> t = degreeVertices.get(min);
          degreeVertices.set(min, degreeVertices.get(i));
          degreeVertices.set(i, t);
      }

      for (Map.Entry<Integer, Integer> i : degreeVertices)
          K.add(i.getValue());

      return K;
    }

    // Devolve verdadeiro se dois vértices são vizinhos.
    public boolean isNeighbor(int u, int v) {
        if (adj[u][v] > 0)
            return true;
        return false;
    }

    // Imprime a matriz de adjacência.
    public void print() {
        for (int i = 1; i < V; i++) {
            for (int w : adj[i])
                System.out.printf(w + " ");
            System.out.println();
        }
    }
}
