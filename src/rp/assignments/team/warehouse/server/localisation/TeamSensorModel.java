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



	@Override
	public GridPositionDistribution updateAfterSensing(GridPositionDistribution _dist, Heading _heading,
			RangeReadings _readings) {
		if(_readings.get(0).invalidReading() || _readings.incomplete() || _readings.getRange(0) == -1)return _dist;
		int reading = (int) (_readings.getRange(0)/cellSize);
		System.out.println(reading + " away.");
		GridPositionDistribution _to = new GridPositionDistribution(_dist);
		for(int x=0; x<_to.getGridMap().getXSize(); x++){
			for(int y=0; y<_to.getGridMap().getYSize(); y++){
				if(_to.getGridMap().isObstructed(x, y))continue;
				int outData = (int) _to.getGridMap().rangeToObstacleFromGridPosition(x, y, Heading.toDegrees(_heading));
				float prob = 0f;
				switch(_heading){
				case PLUS_X: if(_dist.isValidGridPosition(x-1, y))prob = _dist.getProbability(x-1, y);break;
				case MINUS_X: if(_dist.isValidGridPosition(x+1, y))prob = _dist.getProbability(x+1, y);break;
				case PLUS_Y: if(_dist.isValidGridPosition(x, y-1))prob = _dist.getProbability(x, y-1);break;
				case MINUS_Y: if(_dist.isValidGridPosition(x, y+1))prob = _dist.getProbability(x, y+1);break;
				}
				
				if( 0 <= reading - outData && reading - outData <= 0){
				prob *= 1;
				}
				_to.setProbability(x, y, prob);
			}
		}
		//_to.setProbability(3, 1, 100f);
		_to.normalise();
		return _to;
		
	}

}
