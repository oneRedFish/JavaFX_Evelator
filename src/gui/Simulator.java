package gui;

import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

public class Simulator{

	private ElevatorSystem system;

	public Simulator( Observer observer){
		system = new ElevatorSystemImp( 0, 20);
		ElevatorImp e = new ElevatorImp( 10, (ElevatorPanel) system);
		e.addObserver(observer);
		system.addElevator( e);
	}

	public void start(){
		ExecutorService e = Executors.newSingleThreadExecutor();
		e.execute( () -> {
			try {
				Thread.sleep( 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			Elevator elevator = system.callUp( 0);
			elevator.addPersons( 1);
			elevator.requestStop( 10);
			elevator.requestStop( 2);
			elevator.requestStop( 5);
			elevator.requestStop( 9);
			elevator.requestStop( 20);
			elevator.requestStop( 0);
		});
		e.shutdown();
	}
}

