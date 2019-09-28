import java.util.ArrayList;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Mcq {
    static ArrayList<Integer> C = new ArrayList<Integer>();
    static Stack<State> S = new Stack<State>();

    private static void preProcessInstace(Graph G) {
        ArrayList<Integer> Q = new ArrayList<Integer>();
        ArrayList<Integer> K = G.sortByDegree();
        ArrayList<Integer> KN = new ArrayList<Integer>(K.size());

        int delta = G.maxDegree();
        for (int i = 0; i < delta; i++)
            KN.add(i, i+1);

        for (int i = delta; i < K.size(); i++)
            KN.add(i, (delta+1));

        C = new ArrayList<Integer>();
        S.push(new State(Q, K, KN));
    }

    private static boolean hasNeighnorInColor(Graph G, ArrayList<Integer> P, int u){
        for (int v : P) {
            if (G.isNeighbor(u, v))
                return true;
        }
        return false;
    }

    private static ArrayList<ArrayList <Integer>> Greedy(Graph G, ArrayList<Integer> Q, ArrayList<Integer> K){
        ArrayList<ArrayList<Integer>> P = new ArrayList<ArrayList<Integer>>();
        P.add(new ArrayList<Integer>());
        int maiorcor = 0;
        for (int v : K){
            int cor = 0;
            while (cor <= maiorcor && hasNeighnorInColor(G, P.get(cor), v)){
                cor++;
            }
            if (cor > maiorcor){
                maiorcor = cor;
                P.add(new ArrayList<Integer>());
            }
            P.get(cor).add(v);
        }
        return P;
    }

    private static void preProcessState(Graph G, State state, ArrayList<Integer> C) {
        if (!state.map.isEmpty()) return;
        ArrayList< ArrayList<Integer>> P = Greedy(G, state.Q, state.K);
        state.map = new ArrayList<Integer>(state.K.size());

        int size = P.size();
        int posicao = 0;

        for (int cor = 0; cor < size; cor++) {
            for (int v : P.get(cor)) {
                state.K.set(posicao, v);
                state.map.add(posicao, (cor+1));
                posicao++;
            }
        }
    }

    private static int upperBound(Graph G, State state, ArrayList<Integer> C) {
        return state.map.get(state.map.size()-1);
    }

    private static int removeVertex(Graph G, State state, ArrayList<Integer> C) {
        int v = state.K.get(state.K.size()-1);
        state.K.remove(state.K.size()-1);
        state.map.remove(state.map.size()-1);
        return v;
    }

    private static ArrayList<Integer> postProcessInstace(Graph G, ArrayList<Integer> C) {
        return C;
    }

    private static ArrayList<Integer> intersect(Graph G, ArrayList<Integer> K, int v) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        for (int u : K) {
            if (G.isNeighbor(u, v))
                neighbors.add(u);
        }
        return neighbors;
    }

    private static ArrayList<Integer> mcbb(Graph G, String timeOut) { // recebe como timeOut
        long count_states = 0;
        int v;
        double tempoInicio = System.currentTimeMillis();
        preProcessInstace(G);
        while(!S.isEmpty()){
            State state = S.peek();
            S.pop();
            preProcessState(G, state, C);
            count_states++;
            while(!state.K.isEmpty() && C.size() < state.Q.size()+upperBound(G, state, C)){
                v = removeVertex(G, state, C);
                S.push(state.copy());
                state.Q.add(v);
                state.K = intersect(G, state.K, v);
                state.map = new ArrayList<Integer>();
                preProcessState(G, state, C);
                count_states++;
            }
            if(C.size() < state.Q.size()){
                C = new ArrayList<Integer>(state.Q);
            }
            int limite = Integer.parseInt(timeOut); // converte o timeOut em int.
            limite = limite * 1000;
            double time = (System.currentTimeMillis()); // tempo no momento
            if(time - tempoInicio > limite){ // verifica o tempoInicio com o timeOut.
              System.out.println("j timeOut " + count_states); // mostra os estados.
              System.exit(0); // para o programa.
            }
        }
        System.out.print("j " + count_states); // Estados.
        double tempoFinal = ((System.currentTimeMillis() - tempoInicio) / 1000);
        System.out.print(" " + tempoFinal);
        return postProcessInstace(G, C);
    }

    public static void main(String[] args) throws IOException {
        int V, u, v;
        String array[] = new String[4];
        Graph g = new Graph();
        String path = args[0];
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        while (true) {
            linha = buffRead.readLine();
            if (linha != null) {
                array = linha.split(" ");
                if (linha.charAt(0) == 'p')  {
                    V = Integer.parseInt(array[2]);
                    g.create(V);
                }
                if (linha.charAt(0) == 'e') {

                    u = Integer.parseInt(array[1]);
                    v = Integer.parseInt(array[2]);
                    g.addEdge(u, v);
                }
            }
            else
                break;
        }
        System.out.println(" " + mcbb(g, args[1]).size()); // envio o tempo por meio do args[1] & retorna o tamanho da CM
    }
}
