package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystemImp;

public class ElevatorSystemImpTest {
	ElevatorImp e;
	ElevatorSystemImp es;
	ElevatorPanel panel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("@BeforeClass - Runs one time, before any tests in this class.");
	}
	@Before
	public void setUp() {
		es = new ElevatorSystemImp( 0, 10);
		e = new ElevatorImp(5, (ElevatorPanel) es);
		es.addElevator(e);
		assertNotNull("Constructor failded", e);
	}
	@After
	public void tearDown() {
		es = null;
		e = null;
	}

	@Test
	public void testCallUp() {
		e.moveTo(5);
		assertEquals(5,e.getFloor(),0);
	}

	@Test
	public void testCallDown() {
		e.moveTo(3);
		assertEquals(3,e.getFloor(),0);
	}

	@Test
	public void testGetPowerConsumed() {
		e.moveTo(5);
		assertEquals(5,e.getFloor(),0);
		assertEquals(7,e.getPowerConsumed(),0);
	}

	@Test
	public void testGetCurrentFloor() {
		e.moveTo(5);
		assertEquals(5,es.getCurrentFloor());
	}

	@Test
	public void testGetMaxFloor() {
		es.getMaxFloor();
		assertEquals(10,es.getMaxFloor(),0);	
	}

	@Test
	public void testGetMinFloor() {
		es.getMinFloor();
		assertEquals(0,es.getMinFloor(),0);	
	}

	@Test
	public void testGetFloorCount() {
		es.getFloorCount();
		assertEquals(11,es.getFloorCount(),0);
	}

	@Test
	public void testAddElevator() {
		es.addElevator(e);
		assertEquals(e,es.callUp(5));
	}

	@Test
	public void testRequestStop() {
		es.requestStop(5, e);
		assertEquals(5,es.getCurrentFloor());
	}

}
