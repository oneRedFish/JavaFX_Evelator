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
	 * called from {@link Elevator} to inform {@link ElevatorSystem} of request stop to new floor.
	 * @param elevator - reference to the calling elevator.
	 * @param floor - new floor to which {@link Elevator} will travel.
	 */
	void requestStop( final Elevator elevator, final int floor);

	/**
	 * called from {@link Elevator} to inform {@link ElevatorSystem} of multiple stop requests.
	 * @param elevator - reference to the calling elevator.
	 * @param floors - new stops to which {@link Elevator} will travel.
	 */
	void requestStops( final Elevator elevator, final int... floors);
}
