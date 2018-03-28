package elevator;

import java.util.Arrays;
import java.util.Observable;

import elevatorsystem.ElevatorPanel;
/**
 * This class is used for implementing the Elevator interface，creating an elevator，
 * storing data and logical calculation
 * @author Minyi Yang
 * @version 1.0 
 * @see package elevator;
 * @see java.util.Arrays;
 * @see java.util.Observable;
 * @see elevatorsystem.ElevatorPanel;
 * @since 1.8.0_144
 */
public class ElevatorImp extends Observable implements Elevator{
	/**
	 * Create a unchanged int variable named POWER_START_STOP
	 */
	private final static int POWER_START_STOP = 2;
	/**
	 * Create a unchanged int variable named POWER_CONTINUOUS
	 */
	private final static int POWER_CONTINUOUS = 1;
	/**
	 * Create a unchanged int variable named START_STOP
	 */
	private final static long START_STOP = 500;
	/**
	 * Create a unchanged int variable named CONTINUOUS
	 */
	private final static long CONTINUOUS = 250;
	/**
	 * Create a unchanged int variable named MAX_CAPACITY_PERSONS
	 */
	private final int MAX_CAPACITY_PERSONS;
	/**
	 * Create a double variable named powerUsed
	 */
	private double powerUsed=0.0;
	/**
	 * Create a int variable named currentFloor
	 */
	private int currentFloor=0;
	/**
	 * Create a int variable named capacity
	 */
	private int capacity=0;
	/**
	 * Create a int variable named step
	 */
	private int step;
	/**
	 * Create a ElevatorPanel object named panel
	 */
	private ElevatorPanel panel;
	/**
	 * Create a MovingState object named state
	 */
	private MovingState state;
	/**
	 * to check whether the threads are delay
	 */
	private final boolean delay;
	/**
	 * The integer which is used to hold the id of elevator
	 */
	private final int ID;
	
	/**
	 * The constructor of ElevatorImp class which is used to initialize MAX_CAPACITY_PERSON and panel
	 * @param MAX_CAPACITY_PERSON - the max number of people which Elevator can hold
	 * @param panel - reference to the calling ElevatorPanel
	 * @param ID -  The integer which is used to hold the id of elevator
	 * @param delay - whether the threads are delay
	 */	
	public ElevatorImp(int MAX_CAPACITY_PERSON,ElevatorPanel panel,int ID,boolean delay) {
		if(MAX_CAPACITY_PERSON<0) {
			throw new IllegalArgumentException("Sorry the CAPACITY cannot be smaller than 0");
		}
		if(ID<0 ) {
			throw new IllegalArgumentException("Sorry the ID cannot be smaller than 0");
		}
		this.MAX_CAPACITY_PERSONS=MAX_CAPACITY_PERSON;
		this.panel=panel;
		this.ID = ID;
		this.delay = delay;
		this.state = MovingState.Idle;
	}
	/**
	 *  
	 * @param MAX_CAPACITY_PERSON- the max number of people which Elevator can hold
	 * @param panel- reference to the calling ElevatorPanel
	 * @param ID The integer which is used to hold the id of elevator
	 */
	public ElevatorImp(int MAX_CAPACITY_PERSON,ElevatorPanel panel,int ID) {
		this( MAX_CAPACITY_PERSON, panel, ID, true);
		if(MAX_CAPACITY_PERSON<0) {
			throw new IllegalArgumentException("Sorry the CAPACITY cannot be smaller than 0");
		}
		if(ID<0 ) {
			throw new IllegalArgumentException("Sorry the ID cannot be smaller than 0");
		}
	}
	
	/**
	 * This method is used to get the different states,currentFloor,targetFloor,powerUsed when the targetFloor is changed
	 * pass the values to Observer
	 */
	@Override
	public void moveTo(int floor) {
		while(currentFloor!=floor) {
			   switch(getState()) {
				   case Idle:
						try
						{
							Thread.sleep(START_STOP);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}

					    step=(floor - currentFloor)<0 ? -1 : 1;
					    state = (step ==1)? MovingState.SlowUp: MovingState.SlowDown;
					    break;
				    
				   case Up:
			       case Down:
			    	   		try
						{
							Thread.sleep(CONTINUOUS);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
			    	   		
					    currentFloor+=step;
					    powerUsed +=POWER_CONTINUOUS;
					    if(Math.abs(floor- currentFloor)==1) {
					    		state =state.slow();
					    }
					    break;
				    
				   case SlowUp:
				   case SlowDown:
					   try
						{
							Thread.sleep(START_STOP);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					   
			    			currentFloor+= step;
					    powerUsed+= POWER_START_STOP;
					    if(Math.abs(floor-currentFloor)>1) {
					    		state=state.normal();
					    }
					    if(currentFloor == floor) {
					    		state = MovingState.Idle;
					    }
					    break;
				    
				   default:
					   	throw new UnsupportedOperationException("Sorry this application cannotsupport this Operation");
					   //break;
			   }
			   //set observers
			   setChanged();
			   notifyObservers(Arrays.asList(currentFloor, floor, powerUsed, ID));
		}
	}
	/**
	 * get the person from elevator and store the value in capacity
	 */
	@Override
	public void addPersons(int persons) {
		if(persons<0 || persons==0 || persons+ capacity >MAX_CAPACITY_PERSONS) {
			throw new IllegalArgumentException("Sorry the persons cannot be negative number");
		}
		capacity += persons;
	}
	/**
	 * get the value of capacity
	 */
	@Override
	public int getCapacity() {
		return capacity;
		
	}
	/**
	 * get the value of currentFloor
	 */
	@Override
	public int getFloor() {
		return currentFloor;
	}
	/**
	 * get the value of powerUsed
	 */
	@Override
	public double getPowerConsumed() {
		return powerUsed;
	}
	/**
	 * get the value of state
	 */
	@Override
	public MovingState getState(){
		return state;
	}
	/**
	 * compare the value of MAX CAPACITY to real capacity 
	 * so we can know whether the elevator is full
	 */
	@Override
	public boolean isFull() {
		if(MAX_CAPACITY_PERSONS==capacity) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * compare the value of zero to real capacity 
	 * so we can know whether the elevator is empty
	 */
	@Override
	public boolean isEmpty() {
		if(capacity==0) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * through the requestStop method contact with the ElevatorPanel
	 */
	@Override
	public void requestStop(int floor) {
		if(floor<0) {
			throw new IllegalArgumentException("Sorry the floor cannot be smaller than zero");
		}
		panel.requestStops(this,floor);
	}
    /**
     * get the arrays from the simulator and pass the values to panel
     */
	@Override
	public void requestStops(int... floors) {
		for(int i=0;i<floors.length; i++) {
			if(floors[i]<0) {
				throw new IllegalArgumentException("Sorry the floor cannot be smaller than zero");
			}
		}
		panel.requestStops(this, floors);
	}
	/**
	 * get the elevatorID 
	 */
	@Override
	public int id() {
		return ID;
	}
	/**
	 * check the state of elevator isIdle
	 */
	@Override
	public boolean isIdle() {
		return state.isIdle();
	}
	/**
	 * get the hasCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}
	/**
	 * can be used to check whether the elevator is null
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElevatorImp other = (ElevatorImp) obj;
		if (ID != other.ID)
			return false;
		return true;
	};
	


}
