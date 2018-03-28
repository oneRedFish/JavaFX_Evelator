package gui;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

public class Simulator{

	private static final int MIN_FLOOR = 0;
	private static final int MAX_FLOOR = 20;
	private static final int MAX_CAPACITY = 5;

	private ElevatorSystem system;

	public Simulator( Observer observer, final int minFloor, final int maxFloor){
		system = new ElevatorSystemImp( minFloor, maxFloor);
		system.addElevator( new ElevatorImp( MAX_CAPACITY, (ElevatorPanel) system, system.getElevatorCount()));
		system.addElevator( new ElevatorImp( MAX_CAPACITY, (ElevatorPanel) system, system.getElevatorCount()));
		system.addElevator( new ElevatorImp( MAX_CAPACITY, (ElevatorPanel) system, system.getElevatorCount()));
		system.addElevator( new ElevatorImp( MAX_CAPACITY, (ElevatorPanel) system, system.getElevatorCount()));
		system.addObserver( observer);
	}

	public Simulator( Observer observer){
		this( observer, MIN_FLOOR, MAX_FLOOR);
	}

	public int getFloorCount(){
		return system.getFloorCount();
	}

	public int getElevatorCount(){
		return system.getElevatorCount();
	}

	public void shutdown(){
		system.shutdown();
	}

	public void start(){
		system.start();
		ScheduledExecutorService se = Executors.newScheduledThreadPool( 4);
		se.schedule( () -> {
			Elevator elevator = system.callUp( 0);
			System.out.println( "ID: " + elevator.id());
			elevator.addPersons( 1);
			elevator.requestStops( 12, 2, 5, 9, 20, 3);
		}, 0, TimeUnit.MILLISECONDS);
		se.schedule( () -> {
			Elevator elevator = system.callDown( 20);
			System.out.println( "ID: " + elevator.id());
			elevator.addPersons( 2);
			elevator.requestStops( 11, 3, 6, 10, 19, 15);
		}, 50, TimeUnit.MILLISECONDS);
		se.schedule( () -> {
			Elevator elevator = system.callUp( 0);
			System.out.println( "ID: " + elevator.id());
			elevator.addPersons( 3);
			elevator.requestStops( 12, 4, 7, 11, 18, 9);
		}, 100, TimeUnit.MILLISECONDS);
		se.schedule( () -> {
			Elevator elevator = system.callDown( 20);
			System.out.println( "ID: " + elevator.id());
			elevator.addPersons( 4);
			elevator.requestStops( 9, 5, 8, 12, 17, 2);
		}, 150, TimeUnit.MILLISECONDS);
		se.shutdown();
	}
}

