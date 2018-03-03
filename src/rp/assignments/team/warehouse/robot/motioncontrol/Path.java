package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Integer> pathList = new ArrayList<Integer>();
	
	public void addAction(int value) {
		pathList.add(value);
	}
	
	public List<Integer> getPathList() {
  		return pathList;
  	}
}
