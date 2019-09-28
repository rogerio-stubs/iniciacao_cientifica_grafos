import java.util.ArrayList;

public class State {
    public ArrayList<Integer> Q  = new ArrayList<Integer>();
    public ArrayList<Integer> K  = new ArrayList<Integer>();
    public ArrayList<Integer> map = new ArrayList<Integer>();

    public State(ArrayList<Integer> Q, ArrayList<Integer> K, ArrayList<Integer> map) {
        this.Q  = new ArrayList<Integer>(Q);
        this.K  = new ArrayList<Integer>(K);
        this.map = new ArrayList<Integer>(map);
    }

    public State copy() {
        ArrayList<Integer> map = new ArrayList<Integer>();
        return new State(Q, K, map);
    }
  }
