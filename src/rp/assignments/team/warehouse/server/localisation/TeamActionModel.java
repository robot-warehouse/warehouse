package rp.assignments.team.warehouse.server.localisation;

import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.navigation.Heading;

public class TeamActionModel implements ActionModel{

	@Override
	public GridPositionDistribution updateAfterMove(GridPositionDistribution _dist, Heading _heading) {
		// create a distribution of same size
		GridPositionDistribution _to = new GridPositionDistribution(_dist);
		
		// calculate all grids' probability.
		for(int x=0; x<_to.getGridMap().getXSize(); x++){
			for(int y=0; y<_to.getGridMap().getYSize(); y++){
				// skip obstacles
				if(_to.getGridMap().isObstructed(x, y))continue;
				
				float prob = 0f;
				
				// get source probability
				switch(_heading){
				case PLUS_X: if(_dist.isValidGridPosition(x-1, y))prob = _dist.getProbability(x-1, y);break;
				case MINUS_X: if(_dist.isValidGridPosition(x+1, y))prob = _dist.getProbability(x+1, y);break;
				case PLUS_Y: if(_dist.isValidGridPosition(x, y-1))prob = _dist.getProbability(x, y-1);break;
				case MINUS_Y: if(_dist.isValidGridPosition(x, y+1))prob = _dist.getProbability(x, y+1);break;
				}
				
				// set impossible grids to 0
				switch(_heading){
				case PLUS_X: if(!_dist.isValidGridPosition(x-2, y) && _dist.isValidGridPosition(x-1, y))_to.setProbability(x-1, y, 0f);break;
				case MINUS_X: if(!_dist.isValidGridPosition(x+2, y) && _dist.isValidGridPosition(x+1, y))_to.setProbability(x+1, y, 0f);break;
				case PLUS_Y: if(!_dist.isValidGridPosition(x, y-2) && _dist.isValidGridPosition(x, y-1))_to.setProbability(x, y-1, 0f);break;
				case MINUS_Y: if(!_dist.isValidGridPosition(x, y+2) && _dist.isValidGridPosition(x, y+2))_to.setProbability(x, y+1, 0f);break;
				}
				// update probability
				_to.setProbability(x, y, prob);
			}
		}
		_to.normalise();
		return _to;
	}

}
