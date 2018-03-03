import java.util.ArrayList;

public class Data {

	public static final ArrayList<State> obstacle= new ArrayList<>();
	
	public static ArrayList<State> addObstacle(){
		obstacle.add(new State(1,1));
		obstacle.add(new State(1,2));
		obstacle.add(new State(1,3));
		obstacle.add(new State(1,4));
		obstacle.add(new State(1,5));
		obstacle.add(new State(4,1));
		obstacle.add(new State(4,2));
		obstacle.add(new State(4,3));
		obstacle.add(new State(4,4));
		obstacle.add(new State(4,5));
		obstacle.add(new State(7,1));
		obstacle.add(new State(7,2));
		obstacle.add(new State(7,3));
		obstacle.add(new State(7,4));
		obstacle.add(new State(7,5));
		obstacle.add(new State(10,1));
		obstacle.add(new State(10,2));
		obstacle.add(new State(10,3));
		obstacle.add(new State(10,4));
		obstacle.add(new State(10,5));
		
		return obstacle;
	}
	
}
