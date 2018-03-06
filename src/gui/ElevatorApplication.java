package gui;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import elevator.MovingState;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	 * Create a Label type array named floor
	 */
	private Label floor[];
	/**
	 * Create a GridPane named floorGrid
	 */
	private GridPane floorGrid;	
	/**
	 * Create a FlowPane named flow
	 */
	private FlowPane flow;
	/**
	 * Create a BorderPane named root
	 */
	private BorderPane root;
	/**
	 * Create a HBox named hbox_c
	 */
	private HBox hbox_c;
	/**
	 * Create a HBox named hbox_t
	 */
	private HBox hbox_t;
	/**
	 * Create a HBox named hbox_p
	 */
	private HBox hbox_p;
	/**
	 * Create a HBox named hbox_p
	 */
	private HBox hbox_s;
	/**
	 * Create a VBox named vbox
	 */
	private VBox vbox;
	/**
	 * Create a Label named note_c
	 */
	private Label note_c;
	/**
	 * Create a Label named note_t
	 */
	private Label note_t;
	/**
	 * Create a Label named note_p
	 */
	private Label note_p;
	/**
	 * Create a Label named note_s
	 */
	private Label note_s;
	/**
	 * Create a Label named fstate
	 */
	private Label fstate;
	/**
	 * Create a Label named cFloor
	 */
	private Label cFloor;
	/**
	 * Create a Label named tFloor
	 */
	private Label tFloor;
	/**
	 * Create a Label named powerused
	 */
	private Label powerused;
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
		floor = new Label[FLOOR_COUNT];
		note_c = new Label("Current Floor is : ");
		note_t = new Label("Target  Floor  is : ");
		note_p = new Label("Power  Used  is : ");
		note_s = new Label("The State  is : ");
		root = new BorderPane();
		flow = new FlowPane();
		vbox = new VBox();
		hbox_c = new HBox();
		hbox_t = new HBox();
		hbox_p = new HBox();
		hbox_s = new HBox();
		floorGrid = new GridPane();
		simulator = new Simulator(this);
		cFloor = new Label("0");
		tFloor = new Label();
		fstate = new Label("Idle");
		powerused = new Label("0");
		elevatorAnime = new ElevatorAnimator();
		
		for(int i = 0; i<FLOOR_COUNT; i++) {
			floor[i] = new Label();
			floor[i].setId("empty");
			floor[i].setText(String.valueOf(i));
		}
		floor[0].setId("elevator");
	
		floorGrid.setId("grid");
		flow.setId("flow");
		note_c.setId("note_c");
		note_t.setId("note_t");
		note_p.setId("note_p");
		note_s.setId("note_s");
		cFloor.setId("cFloor");
		tFloor.setId("tFloor");
		fstate.setId("fstate");
		powerused.setId("powerused");
	}
	/**
	 * it is used to add nodes to root pane 
	 * start simulator and elevatorAnime
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		for(int i =FLOOR_COUNT-1; i>=0; i--) {
			floorGrid.add(floor[i], 0, (FLOOR_COUNT-1)-i);
		}
		hbox_c.getChildren().addAll(note_c,cFloor);
		hbox_t.getChildren().addAll(note_t,tFloor);
		hbox_p.getChildren().addAll(note_p,powerused);
		hbox_s.getChildren().addAll(note_s,fstate);
		vbox.getChildren().addAll(hbox_c,hbox_t,hbox_p,hbox_s);
		flow.getChildren().add(vbox);
		root.setCenter(floorGrid);
		root.setRight(flow);
		Scene scene = new Scene(root,500,630);
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
		   List<Object> list= (List<Object>)arg;
		   elevatorAnime.addData(list);	
	}
	/**
	 * inherit abstract class AnimationTimer 
	 * set the time of Animation
	 */
	private class ElevatorAnimator extends AnimationTimer{
		/**
		 * Create a unchanged long variable named SECOND
		 */
		private static final long SECOND = 1000000001;
		/**
		 * Create a unchanged long variable named SLOW
		 */
		protected static final long SLOW = SECOND/3;
		/**
		 * Create a unchanged long variable named NORMAL
		 */
		protected static final long NORMAL = SECOND/7;
		/**
		 * Create an int variable named targetFloor to store targetFloor
		 */
		private int targetFloor;
		/**
		 * Create an int variable named currentFloor to store currentFloor
		 */
		private int currentFloor;
		/**
		 * Create an double variable named powerUsed to store power
		 */
		private double powerUsed;
		/**
		 * Create an long variable named lastUpdate to store lastUpdate time
		 */
		private long lastUpdate = 0;
		/**
		 * Create a MovingState object named state 
		 */
		private MovingState state;
		
		//queue
		Queue<List<Object>> queue=new LinkedList<>();
		/**
		 * add the List<Object> to queue
		 * @param list - which is paseed from update method
		 */
		public void addData(List<Object> list) {
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
			else {
				if(now - lastUpdate >= NORMAL && !String.valueOf(state).equals("SlowUp") && !String.valueOf(state).equals("SlowDown")) {
					this.showAnimation();
					lastUpdate = now;
				}
				if(now - lastUpdate >= SLOW && (String.valueOf(state).equals("SlowUp") || String.valueOf(state).equals("SlowDown"))){
					this.showAnimation();
					lastUpdate = now;
				}
				
				//set content of label
				cFloor.setText(String.valueOf(currentFloor));
				tFloor.setText(String.valueOf(targetFloor));
				powerused.setText(String.valueOf(powerUsed));
				fstate.setText(String.valueOf(state));
			}
		}
		/**
		 * set the css style to different floors
		 */
		public void showAnimation() {
			//set id for current floor to empty
			floor[currentFloor].setId("empty");
			//poll from queue
			List<Object> list=queue.poll();
			//assign new cf, tf, pu
			currentFloor = (Integer)list.get(0);
			targetFloor = (Integer)list.get(1);
			powerUsed = (Double)list.get(2);
			state = (MovingState)list.get(3);
			//set id for cf and tf
			floor[targetFloor].setId("target");
			floor[currentFloor].setId("elevator");
		}
	};
	/**
	 * stop the application
	 */
	@Override 
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
	/**
	 * Define the main method application launch from here
	 * @param args - a String array holds all parameter arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
