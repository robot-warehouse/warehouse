package rp.assignments.team.warehouse.server.route.planning;

public class TwoRobots {
	
//---------At the beginning---------
	//priority is given to r1 and route is changed for r2
	//get Route for r1 in a list called l1
	
	//get Route for r2 in a list called l2
	
	//get the current time----> not sure how this would be used
	
	//create a map for both the robots with time steps as keys and coordinates as values.
	
	//loop through both the maps simultaneously
		//if there are no conflicts start moving
		//if any time step has the same coordinates in both the maps (without swapping) add a pause for r2 in the map for that particular time step
		//and re-check.....again if there is a possibility of a collision without swapping positions repeat. ( would have to put this in a loop )
		//if at any step the robots are swapping positions reroute for the second robot with that coordinate as obstacle and re-check
	
//---------Later-----------------
	//if r1 is moving and r2 is stationary i.e. waiting for a new route.
	
	//get the Route for r2
	
	//get the route for r1 and the current coordinate of r1
	
	//create a map for both the robots with the next to next position of r1 (say p3) as the value for the first time step.
	
	//loop through both the maps simultaneously 
		//if there are no conflicts, start moving as soon as r1 reaches p3
		//if any time step has the same coordinates in both the maps but they are not swapping positions, add a pause for r2 in the map for that particular time step
		//and re-check.....again if there is a possibility of a collision without swapping positions repeat. ( would have to put this in a loop )
		//if at any step the robots are swapping positions reroute for the second robot with that coordinate as obstacle
	
//------Similar approach if r2 is moving and r1 is stationary.	
}
