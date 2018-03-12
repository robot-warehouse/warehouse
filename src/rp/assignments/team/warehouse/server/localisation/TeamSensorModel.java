package rp.assignments.team.warehouse.server.localisation;

import lejos.robotics.RangeReadings;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.SensorModel;
import rp.robotics.navigation.Heading;
import sun.net.www.content.audio.x_aiff;

public class TeamSensorModel implements SensorModel{
	
	private float cellSize = 0f;

	public TeamSensorModel(float cellSize) {
		super();
		this.cellSize = cellSize;
	}

	private int abs(int x){
		if(x<0) return -x;
		return x;
	}

	@Override
	public GridPositionDistribution updateAfterSensing(GridPositionDistribution _dist, Heading _heading,
			RangeReadings _readings) {
		// If there are errors in the readings, just skip this time
		if(_readings.get(0).invalidReading() || _readings.incomplete() || Float.isInfinite(_readings.get(0).getRange()))return _dist;
		
		// calculate, based on the readings, how many grids away to nearest wall.
		int reading = (int) (_readings.getRange(0)/cellSize);

		// create a distribution of same size
		GridPositionDistribution _to = new GridPositionDistribution(_dist);
		
		// calculate all grid's probability.
		for(int x=0; x<_to.getGridMap().getXSize(); x++){
			for(int y=0; y<_to.getGridMap().getYSize(); y++){
				// skip wall and obstacles
				if(_to.getGridMap().isObstructed(x, y))continue;
				
				// calculate, based on the map, how many grids away to nearest wall.
				int outData = (int) (_to.getGridMap().rangeToObstacleFromGridPosition(x, y, Heading.toDegrees(_heading)) / cellSize);
				
				// get current probability 
				float prob = _dist.getProbability(x, y);
				
				// if the reading equals to map based distance, give it higher probability
				// the further, the lower probability
				if(abs(outData-reading) == 0) prob *= 0.6;
				if(abs(outData-reading) == 1) prob *= 0.15;
				if(abs(outData-reading) == 2) prob *= 0.05;
				// if it is too far, set it to 0.001f.
				// dont set it to 0 just in case sensor errors
				// set it to 0.001 so that if sensor give invalid data, the robot can gradually tune the location to accurate value
				if(abs(outData-reading) > 2) prob = 0.001f;
				
				// update probability
				_to.setProbability(x, y, prob);
			}
		}
		_to.normalise();
		return _to;
		
	}

}
