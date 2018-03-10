package rp.assignments.team.warehouse.server.localisation;

import lejos.robotics.navigation.Pose;
import rp.robotics.localisation.GridPoseProvider;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class MarkovLocalisation implements GridPoseProvider{

	// The map used as the basis of behaviour
	private GridMap m_map;

	private GridPositionDistribution m_distribution;

	private boolean running = true;
	
	private Heading heading;
	
	private float threshold = 0.7f;

	public MarkovLocalisation(GridMap m_map) {
		super();
		this.m_map = m_map;
		this.m_distribution = new GridPositionDistribution(this.m_map);
	}

	public boolean isConfident() {
		for (int i = 0; i < m_distribution.getGridHeight(); i++) {
			for (int j = 0; j < m_distribution.getGridWidth(); j++) {
				if (m_distribution.getProbability(i, j) > threshold)
					return true;
			}
		}
		return false;
	}



	public void move(Heading _heading) {
		this.heading = _heading;
		OurActionModel actionModel = new OurActionModel();
		m_distribution = actionModel.updateAfterMove(m_distribution, _heading);
	}

	public void stop() {
		this.running = false;
	}

	@Override
	public GridPose getGridPose() {
		GridPose pose = null;
		float maximum = 0f;
		for(int i = 0; i<m_distribution.getGridHeight(); i++){
			for(int j=0; j<m_distribution.getGridWidth(); j++){
				if (m_distribution.getProbability(i, j) > maximum){
					maximum = m_distribution.getProbability(i, j);
					pose = new GridPose(i, j, heading);
				}
			}
		}
		return pose;
	}

	@Override
	public void setGridPose(GridPose _pose) {
		
	}

}
