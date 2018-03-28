package gui;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * This class is used for creating nodes and objects which are needed for application 
 * and display the data to users.
 * 
 * @author Minyi Yang
 * @version 1.0 
 * @see package gui;
 * @see java.util.LinkedList;
 * @see java.util.List;
 * @see java.util.Observable;
 * @see java.util.Observer;
 * @see java.util.Queue;
 * @see elevator.MovingState;
 * @see javafx.animation.AnimationTimer;
 * @see javafx.application.*;
 * @see javafx.scene.Scene;
 * @see javafx.scene.control.Label;
 * @see javafx.scene.control.TextField;
 * @see javafx.scene.layout.BorderPane;
 * @see javafx.scene.layout.FlowPane;
 * @see javafx.scene.layout.GridPane;
 * @see javafx.scene.layout.HBox;
 * @see javafx.scene.layout.VBox;
 * @see javafx.stage.Stage;
 * @since 1.8.0_144
 */
public class ElevatorApplication extends Application implements Observer{
	/**
	 * Create a unchanged int variable named FLOOR_COUNT
	 */
	private static final int FLOOR_COUNT = 21;
	/**
	 * Create a Label type array named floors
	 */
	private Label floors[][];
	/**
	 * Create a Label type array named note
	 */
	private Label note[][];
	/**
	 * Create a Label type array named content
	 */
	private Label content[][];
	/**
	 * Create a GridPane named floorGrid
	 */
	private GridPane floorGrid;	
	/**
	 * Create a GridPane named contentGrid
	 */
	private GridPane contentGrid;
	/**
	 * Create a GridPane named totalGrid
	 */
	private GridPane totalGrid;
	/**
	 * Create a BorderPane named root
	 */
	private BorderPane root;
	/**
	 * Create a Simulator object named simulator
	 */
	private Simulator simulator;
	/**
	 * Create a ElevatorAnimator object named elevatorAnime
	 */
	private ElevatorAnimator elevatorAnime;
	/**
	 * use to initialize different nodes
	 */
	@Override 
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		root = new BorderPane();
		totalGrid = new GridPane();
		simulator = new Simulator(this);
		elevatorAnime = new ElevatorAnimator();
		ArrayList<Integer> index_e=new ArrayList<>();
		ArrayList<Integer> index_c=new ArrayList<>();
		//get index of elevator and content
		for(int i =0; i<8;i++) {
			if(i%2==1) {
				index_c.add(i);
			}else if(i%2==0) {
				index_e.add(i);
			}
		}
		//initialize 2d array of elevator
		floors =new Label[4][FLOOR_COUNT];
		for(int id=0; id<4; id++) {
			floorGrid = new GridPane();
			for(int i = 0; i<FLOOR_COUNT; i++) {
				floors[id][i] = new Label();
				floors[id][i].setId("empty");
				floors[id][i].setText(String.valueOf(i));
				floorGrid.add(floors[id][i], 0, (FLOOR_COUNT-1)-i);
			}
			floors[id][0].setId("elevator");
			totalGrid.add(floorGrid,index_e.get(id),0);
		}
		//initialize 2d array of note and content
		note = new Label[4][4];
		content = new Label[4][4];
		for(int id =0; id<4; id++) {
			contentGrid = new GridPane();
			for(int i=0; i<4; i++) {
				note[id][i] = new Label();
				content[id][i] = new Label();
				note[id][i].setTextFill(Color.WHITE);
				content[id][i].setTextFill(Color.WHITE);
				contentGrid.add(note[id][i], 0, 3-i);
				contentGrid.add(content[id][i], 1, 3-i);
			}
			//set note
			note[id][3].setText(String.valueOf("Current Floor is : "));
			note[id][2].setText(String.valueOf("Target  Floor is : "));
			note[id][1].setText(String.valueOf("Power Used is : "));
			note[id][0].setText(String.valueOf("The ID is : "));
			contentGrid.setId("grid");
			totalGrid.add(contentGrid,index_c.get(id),0);
		}
	}
	/**
	 * it is used to add nodes to root pane 
	 * start simulator and elevatorAnime
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		root.setCenter(totalGrid);
		Scene scene = new Scene(root,1400,630);
		scene.getStylesheets().add(ElevatorApplication.class.getResource("elevator.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("ElevatorApplication");
		primaryStage.show();
		//start
		simulator.start();
		elevatorAnime.start();
	
	}
	/**
	 * update the Observers store the data to an arraylist and pass them to elevatorAnime
	 */
	@Override 
	public void update(Observable o,Object arg) {
		   List<Number> list= (List<Number>)arg;
		   elevatorAnime.addData(list);	
	}
	/**
	 * inherit abstract class AnimationTimer 
	 * set the time of Animation
	 */
	private class ElevatorAnimator extends AnimationTimer{
		//queue
		Queue<List<Number>> queue=new LinkedList<>();
		//store currentfloor
		int currentFloors[] = new int[simulator.getElevatorCount()];
		int targetFloors[] = new int[simulator.getElevatorCount()];
		double powerUseds[] = new double[simulator.getElevatorCount()];
		/**
		 * add the List<Object> to queue
		 * @param list - which is paseed from update method
		 */
		public void addData(List<Number> list) {
			//add to gueue
			queue.add(list);
		}
		/**
		 * set the different time when state changes and diaplay them in nodes
		 */
		@Override
		public void handle(long now) {
			if(queue.isEmpty()) {
				return;
			}
			//poll from queue
			List<Number> list=queue.poll();
			int id = list.get(3).intValue();
			//set initialize id for current floor to empty
			floors[id][currentFloors[id]].setId("empty");
			//assign new cf, tf, pu, id
			currentFloors[id] = list.get(0).intValue();
			targetFloors[id] = list.get(1).intValue();
			powerUseds[id] = list.get(2).doubleValue();
			//currentFloors[id] = list.get(0).intValue();
			//set id for cf and tf
			floors[id][targetFloors[id]].setId("target");
			floors[id][currentFloors[id]].setId("elevator");
			//set content of label
			content[id][3].setText(String.valueOf(currentFloors[id]));
			content[id][2].setText(String.valueOf(targetFloors[id]));
			content[id][1].setText(String.valueOf(powerUseds[id]));
			content[id][0].setText(String.valueOf(id));
		}
	};
	/**
	 * stop the application
	 */
	@Override 
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		simulator.shutdown();
	}
	/**
	 * Define the main method application launch from here
	 * @param args - a String array holds all parameter arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
