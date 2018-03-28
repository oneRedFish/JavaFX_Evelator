package elevatorsystem;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import elevator.Elevator;
import elevator.MovingState;

/**
 * This class is used to implements ElevatorSystem,ElevatorPanel get data from
 * user and pass the values to elevator
 * 
 * @author Minyi Yang
 * @version 2.0 
 * @see  java.util.LinkedHashMap;
 * @see  java.util.LinkedList;
 * @see  java.util.Map;
 * @see  java.util.List;
 * @see  java.util.Observer;
 * @see  java.util.concurrent.ExecutorService;
 * @see  java.util.concurrent.Executors;
 * @see  java.util.concurrent.atomic.AtomicBoolean;
 * @see  java.util.concurrent.atomic.AtomicInteger;
 * @see elevatorsystem.ElevatorPanel;
 * @since 1.8.0_144
 */
public class ElevatorSystemImp implements ElevatorSystem, ElevatorPanel {
	private final Object REQUEST_LOCK = new Object();
	private final int MAX_FLOOR;
	private final int MIN_FLOOR;
	private Map<Elevator, List<Integer>> stops;
	private ExecutorService Service;
	private MovingState callDirection;

	AtomicBoolean alive = new AtomicBoolean(true);
	private Runnable run = () -> {
		AtomicInteger[] ac = new AtomicInteger[stops.size()];
		for(int i =0 ; i< ac.length; i++) {
			ac[i] = new AtomicInteger(0);
		}
		while (alive.get()) {
			for (Elevator e : stops.keySet()) {
				if (!e.isIdle()|| stops.get(e).isEmpty() || ac[e.id()].get()!=0) {
					continue;
				}
				synchronized (REQUEST_LOCK) {
					// get list of stops for e
					List<Integer> list = stops.get(e);
					// get first(0) index of list
					int floor = list.remove(0);
					ac[e.id()].incrementAndGet();
					Service.submit(() -> {
						e.moveTo(floor);
						ac[e.id()].decrementAndGet();
					});
				}
			}
		}
	};

	/**
	 * The constructor of ElevatorSystemImp class which is used to initialize
	 * MIN_FLOOR and MAX_FLOOR
	 * 
	 * @param MIN_FLOOR
	 *            the smallest number of building
	 * @param MAX_FLOOR
	 *            the biggest number of building
	 */
	public ElevatorSystemImp(int MIN_FLOOR, int MAX_FLOOR) {
		if (MAX_FLOOR < MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the MAX_FLOOR cannot be smaller than MIN_FLOOR");
		}
		if (MAX_FLOOR == MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the MAX_FLOOR cannot equal MIN_FLOOR");
		}
		if (MIN_FLOOR < 0) {
			throw new IllegalArgumentException("Sorry the MIN_FLOOR cannot be smaller than ZERO");
		}
		this.MAX_FLOOR = MAX_FLOOR;
		this.MIN_FLOOR = MIN_FLOOR;
		stops = new LinkedHashMap<>();
		Service = Executors.newCachedThreadPool();
		callDirection = MovingState.Idle;
	}
	
   /**
    * to run the system thread
    */
	public void start() {
		Service.submit(run);
	}

	/**
	 * when user call up the elevator it will pass the value to call method
	 */
	@Override
	public Elevator callUp(final int floor) {
		floorCheck( floor);
		return call(floor, MovingState.Up);
	}

	/**
	 * when user call down the elevator it will pass the value to call method
	 */
	@Override
	public Elevator callDown(final int floor) {
		floorCheck( floor);
		return call(floor, MovingState.Down);
	}
	/**
	 * call the Elevator to target floor
	 */
	private Elevator call(final int floor, MovingState direction) {
		// store direction
		this.callDirection = direction;
		Elevator e = getAvailableElevatorIndex(floor);
		requestStop(e, floor);
		return e;
	}
	/**
	 * get the Available Elevator
	 */
	private synchronized Elevator getAvailableElevatorIndex(int floor) {
		floorCheck( floor);
		Elevator availableElevator = null;
		int smallest = Integer.MAX_VALUE;
		for (Elevator e : stops.keySet()) {
			// if list for e in stops is Idle and empaty
			if (e.isIdle() && stops.get(e).isEmpty()) {
				// which e is closet to tf Math.abs(cf-tf)+1
				int temp = Math.abs(e.getFloor() - floor) + 1;
				if (temp < smallest) {
					smallest = temp;
					availableElevator = e;
				}
			}
		}
		return availableElevator;
	}
	/**
	 * check the floor 
	 */
	private void floorCheck(int floor) {
		if (floor < 0) {
			throw new IllegalArgumentException("Sorry the floor cannot be smaller than zero");
		}
		if (floor > MAX_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be bigger than MAX_FLOOR");
		}
		if (floor < MIN_FLOOR) {
			throw new IllegalArgumentException("Sorry the targetFloor cannot be smaller than MIN_FLOOR");
		}
	}
	/**
	 * check the elevator 
	 */
	private void checkForEleator(Elevator elevator) {
		if (elevator == null) {
			throw new NullPointerException("Sorry the Elevator cannot be empty");
		}
	}

	/**
	 * get the number of power Consumed
	 */
	@Override
	public double getPowerConsumed() {
		double total = 0.0;
		for (Elevator e : stops.keySet()) {
			total += e.getPowerConsumed();
		}
		return total;
	}

	/**
	 * get the number of CurrentFloor
	 */
	@Override
	public int getCurrentFloor() {
		throw new UnsupportedOperationException();
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
		return Math.abs(MAX_FLOOR - MIN_FLOOR) + 1;
	}

	/**
	 * add an elevator object
	 */
	@Override
	public void addElevator(Elevator elevator) {
		this.checkForEleator(elevator);
		stops.put(elevator, new LinkedList<Integer>());
	}

	/**
	 * contact the elevator through requestStop method
	 */
	@Override
	public void requestStop(Elevator elevator, int floor) {
		floorCheck(floor);
		checkForEleator(elevator);
		requestStops(elevator, floor);
	}
	/**
	 * restore the order of the request floors and add stored floors to list
	 */
	@Override
	public void requestStops(Elevator elevator, int... floors) {
		// check exception
		for (int i = 0; i < floors.length; i++) {
			floorCheck(floors[i]);
		}
		this.checkForEleator(elevator);
		//sort
		if (callDirection == MovingState.Up) {
			QuickSort.increase(floors, 0, floors.length - 1);
		}
		else if (callDirection == MovingState.Down) {
			QuickSort.decrease(floors, 0, floors.length - 1);
		}
		// add fl for list of stops for e stop.get(e) lock this behavior
		synchronized (REQUEST_LOCK) {
			List<Integer> list = stops.get(elevator);
			// add list to map
			for(int f: floors) {
				list.add(f);
			}
		}
	}
	/**
	 * add observer to every Elevator
	 */
	@Override
	public void addObserver(Observer observer) {
		//miss a check
		for (Elevator e : stops.keySet()) {
			e.addObserver(observer);
		}
	}
	/**
	 * get the number of Elevator
	 */
	@Override
	public int getElevatorCount() {
		return stops.size();
	}
	/**
	 * shutdown the system thread
	 */
	@Override
	public void shutdown() {
		alive.set(false);
		Service.shutdown();
	}

}
