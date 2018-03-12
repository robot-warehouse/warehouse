package rp.assignments.team.warehouse.server.localisation;

import java.util.Random;

import javax.swing.JFrame;

import lejos.robotics.navigation.Pose;
import lejos.util.Delay;
import rp.robotics.LocalisedRangeScanner;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.example.MarkovLocalisationExample;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.testing.TestMaps;
import rp.robotics.visualisation.GridPositionDistributionVisualisation;
import rp.robotics.visualisation.KillMeNow;
import rp.robotics.visualisation.MapVisualisationComponent;
import rp.systems.StoppableRunnable;
import sun.applet.Main;

public class MarkovLocalisation implements StoppableRunnable {

	// The map used as the basis of behaviour
	private final GridMap m_map;

	// Probability distribution over the position of a robot on the given
	// grid map. Note this assumes that the robot has a known heading.
	private GridPositionDistribution m_distribution;

	// The visualisation showing position uncertainty
	private GridPositionDistributionVisualisation m_mapVis = null;

	// The pilot object used to move the robot around on the grid.
	private final GridPilot m_pilot;

	// The range scanning sensor
	private LocalisedRangeScanner m_ranger;

	private boolean m_running = false;

	public MarkovLocalisation(GridMap m_map, GridPilot m_pilot, LocalisedRangeScanner _ranger) {
		super();
		this.m_map = m_map;
		this.m_pilot = m_pilot;
		this.m_ranger = _ranger;
		this.m_distribution = new GridPositionDistribution(m_map);
	}
	
	public void visualise(MapBasedSimulation _sim) {

		JFrame frame = new JFrame("Map Viewer");
		frame.addWindowListener(new KillMeNow());

		// visualise the distribution on top of a line map
		m_mapVis = new GridPositionDistributionVisualisation(m_distribution,
				m_map);
		MapVisualisationComponent.populateVisualisation(m_mapVis, _sim);

		frame.add(m_mapVis);
		frame.pack();
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	@Override
	public void run() {
		// create the models
		TeamActionModel actionModel = new TeamActionModel();
		TeamSensorModel sensorModel = new TeamSensorModel(m_map.getCellSize());
		
		Random random = new Random(134299);
		m_running = true;
		
		while (m_running) {
			
			// if the robot is too close to the wall, randomly turn right or left
			if(m_ranger.getRange() / m_map.getCellSize() < 1f){
				if(random.nextBoolean())m_pilot.rotateNegative();
				else m_pilot.rotatePositive();
				continue;
			}
			
			// move forward 
			m_pilot.moveForward();
			
			// update distribution using action model.
			m_distribution = actionModel.updateAfterMove(m_distribution, m_pilot.getGridPose().getHeading());
			
			// update distribution using sensor model.
			m_distribution = sensorModel.updateAfterSensing(m_distribution, m_pilot.getGridPose().getHeading(), m_ranger.getRangeValues());
			
			if(m_mapVis != null){
				m_mapVis.setDistribution(m_distribution);
			}
			
			/*
			String string = "";
			for(int i=0; i<m_distribution.getGridMap().getXSize(); i++){
				for(int j=0; j<m_distribution.getGridMap().getYSize(); j++){
					if(m_distribution.getGridMap().isObstructed(i, j)) string += String.format("X.XXX ");
					else string += String.format("%.3f ", m_distribution.getProbability(i, j));
				}
				string += "\n";
			}
			//System.out.println(string + "\n\n");*/
			Delay.msDelay(100);
		
		}

	}

	@Override
	public void stop() {
		this.m_running = false;
	}

	public static void main(String[] argv) {
		// Work on this map
		GridMap map = TestMaps.warehouseMap();

		// Create the simulation using the given map. This simulation can run
		// without a GUI.
		MapBasedSimulation sim = new MapBasedSimulation(map);

		// the starting position of the robot for the simulation. This is not
		// known in the action model or position distribution
		int startGridX = 2;
		int startGridY = 1;

		GridPose gridStart = new GridPose(startGridX, startGridY, Heading.PLUS_Y);

		// Create a robot with a range scanner but no bumper
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(SimulatedRobots.makeConfiguration(false, true),
				map.toPose(gridStart));
		LocalisedRangeScanner ranger = sim.getRanger(wrapper);

		MarkovLocalisation ml = new MarkovLocalisation(map, new GridPilot(wrapper.getRobot().getPilot(), map, gridStart), ranger);
		ml.visualise(sim);
		ml.run();
	}

}
