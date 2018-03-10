package rp.assignments.team.warehouse.server.localisation;

import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.navigation.Heading;

public class OurActionModel implements ActionModel{

	@Override
	public GridPositionDistribution updateAfterMove(GridPositionDistribution _dist, Heading _heading) {
		
		GridPositionDistribution target = new GridPositionDistribution(_dist);
		
		switch(_heading){
			case PLUS_X:{update(_dist, target, -1, 0);break;}
			case MINUS_X:{update(_dist, target, 1, 0);break;}
			case PLUS_Y:{update(_dist, target, 0, -1);break;}
			case MINUS_Y:{update(_dist, target, 0, 1);break;}
		}

		return target;
	}
	
	private void update(GridPositionDistribution _from, GridPositionDistribution _to, int x, int y){
		
		for(int i = 0; i < _to.getGridHeight(); i++){
			for(int j = 0; j < _to.getGridWidth(); j++){
				
				int from_x = i + x;
				int from_y = j + y;
				
				float prob = 0f;
				
				// current is obstacle
				if(!_to.isObstructed(i, j) && !_to.isValidGridPosition(from_x, from_y)){
					if(_to.getGridMap().isValidTransition(from_x, from_y, i, j)){
						prob = _from.getProbability(from_x, from_y);
					}
				}
				_to.setProbability(i, j, prob);
				
			}
		}
	}

}
