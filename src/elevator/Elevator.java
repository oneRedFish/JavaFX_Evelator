package elevator;

import java.util.Observer;

/**
 * <p>
 * {@link Elevator} guideline to be used.
 * these methods represent possible methods for {@link Elevator}
 * </p>
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 30, 2018
 */
public interface Elevator{

	/**
	 * get current capacity of the elevator not the maximum capacity.
	 * @return integer for total capacity currently in the {@link Elevator}
	 */
	int getCapacity();
	
	/**
	 * check if capacity has reached its maximum
	 * @return if {@link Elevator} is full
	 */
	boolean isFull();
	
	/**
	 * check if capacity is zero
	 * @return if {@link Elevator} is empty
	 */
	boolean isEmpty();
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	boolean isIdle();
	
	/**
	 * get current {@link MovingState} of the {@link Elevator}
	 * @return current {@link MovingState}
	 */
	MovingState getState();
	
	/**
	 * return total amount of power consumed to this point
	 * @return power consumed
	 */
	double getPowerConsumed();
	
	/**
	 * move {@link Elevator} to a specific floor
	 * @param floor - target floor
	 */
	void moveTo( final int floor);
	
	/**
	 * get current floor of {@link Elevator} at this point
	 * @return current floor
	 */
	int getFloor();
	
	/**
	 * Unique integer that identifies this {@link Elevator} object
	 * @return unique identifier integer
	 */
	int id();
	
	/**
	 * add number of persons to {@link Elevator}
	 * @param persons - number of passengers getting on at current floor
	 */
	void addPersons( final int persons);

	/**
	 * represent the request made by one passenger inside of an {@link Elevator} object
	 * @param floor - target floor
	 */
	void requestStop( final int floor);

	/**
	 * represent the request made by multiple passenger inside of an {@link Elevator} object
	 * @param floors - target floors
	 */
	void requestStops( final int...floors);
	
	/**
	 * add an {@link Observer} to this {@link Elevator}
	 * @param observer - add to this {@link Elevator}, cannot be null
	 */
	void addObserver( Observer observer);
}
