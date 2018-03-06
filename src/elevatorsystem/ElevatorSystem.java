package elevatorsystem;

import elevator.Elevator;

/**
 * <p>
 * {@link ElevatorSystem} guideline to be used.
 * these methods represent possible methods for {@link ElevatorSystem}
 * </p>
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 30, 2018
 */
public interface ElevatorSystem{

	/**
	 * get total floors to which {@link ElevatorSystem} can send an {@link Elevator}.
	 * behavior and definition of this method will likely change when more elevators are introduced.
	 * @return total floors
	 */
	int getFloorCount();
	
	/**
	 * get maximum floor for this {@link ElevatorSystem}
	 * @return maximum floor for this {@link ElevatorSystem}
	 */
	int getMaxFloor();
	
	/**
	 * get minimum floor for this {@link ElevatorSystem}
	 * @return minimum floor for this {@link ElevatorSystem}
	 */
	int getMinFloor();
	
	/**
	 * when calling up it means the passenger intends to travel to a higher floor.
	 * @param floor - passengers current floor when calling for an {@link Elevator}
	 * @return an {@link ElevatorSystem} that has reach the requested floor
	 */
	Elevator callUp( final int floor);
	
	/**
	 * when calling down it means the passenger intends to travel to a lower floor.
	 * @param floor - passengers current floor when calling for an {@link Elevator}
	 * @return an {@link ElevatorSystem} that has reach the requested floor
	 */
	Elevator callDown( final int floor);

	/**
	 * return current floor of {@link Elevator} in {@link ElevatorSystem}.
	 * since there is only 1 {@link Elevator} no need for any arguments.
	 * this method will likely change inn future designs.
	 * @return current floor of only {@link Elevator}
	 */
	int getCurrentFloor();
	
	/**
	 * return total power consumed by all {@link Elevator} in the {@link ElevatorSystem}
	 * @return total power consumed
	 */
	double getPowerConsumed();
	
	/**
	 * add an {@link Elevator} to {@link ElevatorSystem}, if implemented multiple {@link Elevator} can be added
	 * @param elevator - {@link Elevator} object to be added to {@link ElevatorSystem}
	 */
	void addElevator( Elevator elevator);
}