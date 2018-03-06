package elevatorsystem;

import elevator.Elevator;
import elevator.MovingState;
/**
 * This class is used to implements ElevatorSystem,ElevatorPanel 
 * get data from user and pass the values to elevator
 * @author Minyi Yang
 * @version 1.0 
 */
public class ElevatorSystemImp implements ElevatorSystem,ElevatorPanel{
	private int MAX_FLOOR;
	private int MIN_FLOOR;
	private Elevator elevator;
	/**
	 * The constructor of ElevatorSystemImp class which is used to initialize MIN_FLOOR and MAX_FLOOR
	 * @param MIN_FLOOR the smallest number of building
	 * @param MAX_FLOOR the biggest number of building
	 */
	public ElevatorSystemImp(int MIN_FLOOR,int MAX_FLOOR) {
		if(MAX_FLOOR<MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the MAX_FLOOR cannot be smaller than MIN_FLOOR");
		}
		if(MAX_FLOOR==MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the MAX_FLOOR cannot equal MIN_FLOOR");
		}
		if(MIN_FLOOR<0) {
			throw new IllegalArgumentException("Sorry the MIN_FLOOR cannot be smaller than ZERO");
		}
		this.MAX_FLOOR = MAX_FLOOR;
		this.MIN_FLOOR = MIN_FLOOR;
	}
	/**
	 * when user call up the elevator it will pass the value to move to method
	 */
	@Override
	public Elevator callUp(int floor) {
		if(floor>MAX_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be bigger than MAX_FLOOR");
		}
		if(floor<MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be smaller than MIN_FLOOR");
		}
		if(MIN_FLOOR<0) {
			throw new IllegalArgumentException("Sorry the MIN_FLOOR cannot be smaller than ZERO");
		}
		elevator.moveTo(floor);
		return elevator;
	}
	/**
	 * when user call down the elevator it will pass the value to move to method
	 */
	@Override
	public Elevator callDown(int floor) {
		if(floor>MAX_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be bigger than MAX_FLOOR");
		}
		if(floor<MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be smaller than MIN_FLOOR");
		}
		elevator.moveTo(floor);
		return elevator;
	}
	/**
	 * get the number of power Consumed
	 */
	@Override
	public double getPowerConsumed() {
		return elevator.getPowerConsumed();
	}
	/**
	 * get the number of CurrentFloor
	 */
	@Override
	public int getCurrentFloor() {
		return elevator.getFloor();
	}
	/**
	 * get the number of CurrentFloor
	 */
	@Override
	public int getMaxFloor() {
		return MAX_FLOOR;
	}
	/**
	 * get the smallest number of building
	 */
	@Override
	public int getMinFloor() {
		return MIN_FLOOR;
	}
	/**
	 * get the total number of building
	 */
	@Override
	public int getFloorCount() {
		return MAX_FLOOR-MIN_FLOOR+1;
	}
	/**
	 * add an elevator object
	 */
	@Override
	public void addElevator(Elevator elevator) {
		if(elevator==null) {
			throw new NullPointerException("Sorry the Elevator cannot be empty");
		}
		this.elevator = elevator;
	}
	/**
	 * contact the elevator through requestStop method
	 */
	@Override
	public void requestStop(int floor, Elevator elevator) {
		if(floor>MAX_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be bigger than MAX_FLOOR");
		}
		if(floor<MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be smaller than MIN_FLOOR");
		}
		if(floor<0) {
			throw new IllegalArgumentException("Sorry the floor cannot be smaller than zero");
		}
		if(elevator==null) {
			throw new NullPointerException("Sorry the Elevator cannot be empty");
		}

		elevator.moveTo(floor);
	}
}
