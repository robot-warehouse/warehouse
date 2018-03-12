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
		if(_readings.get(0).invalidReading() || _readings.incomplete() || Float.isInfinite(_readings.get(0).getRange()))return _dist;
		int reading = (int) (_readings.getRange(0)/cellSize);
		System.out.println(reading + " away." + _readings.getRange(0));
		GridPositionDistribution _to = new GridPositionDistribution(_dist);
		for(int x=0; x<_to.getGridMap().getXSize(); x++){
			for(int y=0; y<_to.getGridMap().getYSize(); y++){
				if(_to.getGridMap().isObstructed(x, y))continue;
				int outData = (int) (_to.getGridMap().rangeToObstacleFromGridPosition(x, y, Heading.toDegrees(_heading)) / cellSize);
				float prob = _dist.getProbability(x, y);
				
				if(abs(outData-reading) == 0) prob *= 0.6;
				if(abs(outData-reading) == 1) prob *= 0.15;
				if(abs(outData-reading) == 2) prob *= 0.05;
				if(abs(outData-reading) > 2) prob = 0f;
				
				_to.setProbability(x, y, prob);
			}
		}
		//_to.setProbability(3, 1, 100f);
		_to.normalise();
		return _to;
		
	}

}
