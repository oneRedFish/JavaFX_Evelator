package elevatorsystem;

import elevator.Elevator;

/**
 * <p>
 * this interface is implemented by {@link ElevatorSystem}.
 * it is used by {@link Elevator} to call back to {@link ElevatorSystem}
 * regarding the the floor to which {@link Elevator} intends to travel.
 * </p>
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 30, 2018
 */
public interface ElevatorPanel{

	/**
	 * called from {@link Elevator} to inform {@link ElevatorSystem} of new floor.
	 * @param floor - new floor to which {@link Elevator} is moving.
	 * @param elevator - reference to the calling elevator.
	 */
	void requestStop( final int floor, Elevator elevator);
}
