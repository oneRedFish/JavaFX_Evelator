package elevator;

/**
 * Represent different states of on elevator
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 30, 2018
 */
public enum MovingState{
	/**
	 * {@link MovingState#Up} is used when elevator is traveling at full speed.</br>
	 */
	Up,
	/**
	 * {@link MovingState#Down} is used when elevator is traveling at full speed.</br>
	 */
	Down,
	/**
	 * {@link MovingState#SlowUp} is used when elevator is slowing down to stop.</br>
	 */
	SlowUp,
	/**
	 * {@link MovingState#SlowDown} is used when elevator is slowing down to stop.</br>
	 */
	SlowDown,
	/**
	 * {@link MovingState#Idle} is used when elevator has nothing to do.</br>
	 */
	Idle,
	/**
	 * {@link MovingState#Off} is used when elevator should not respond to anything.</br>
	 */
	Off;

	/**
	 * Return the slow version of {@link MovingState#Up} or {@link MovingState#Down}, if already slow return the same.
	 * @return slow version of {@link MovingState#Up} or {@link MovingState#Down}.
	 * @throws IllegalStateException - if calling slow on wrong state.
	 */
	public MovingState slow(){
		switch( this){
			case Down:
				return SlowDown;
			case Up:
				return SlowUp;
			case SlowUp:
			case SlowDown:
				return this;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no slow");
		}
	}

	/**
	 * Return the normal version of {@link MovingState#SlowUp} or {@link MovingState#SlowUp}, if already normal return the same.
	 * @return normal version of {@link MovingState#SlowUp} or {@link MovingState#SlowUp}.
	 * @throws IllegalStateException - if calling slow on wrong state.
	 */
	public MovingState normal(){
		switch( this){
			case SlowDown:
				return Down;
			case SlowUp:
				return Up;
			case Up:
			case Down:
				return this;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no normal");
		}
	}

	/**
	 * return opposite of each state if any.</br>
	 * {@link MovingState#SlowUp} to {@link MovingState#SlowDown}</br>
	 * {@link MovingState#SlowDown} to {@link MovingState#SlowUp}</br>
	 * {@link MovingState#Up} to {@link MovingState#Down}</br>
	 * {@link MovingState#Down} to {@link MovingState#Up}</br>
	 * @return opposite of state if any
	 * @throws IllegalStateException - if state is has no opposite
	 */
	public MovingState opposite(){
		switch( this){
			case Down:
				return Up;
			case Up:
				return Down;
			case SlowDown:
				return SlowUp;
			case SlowUp:
				return SlowDown;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no opposite");
		}
	}

	/**
	 * return true if state is {@link MovingState#Down} or {@link MovingState#SlowDown}
	 * @return true if state is {@link MovingState#Down} or {@link MovingState#SlowDown}
	 */
	public boolean isGoingDown(){
		return this == Down || this == SlowDown;
	}

	/**
	 * return true if state is {@link MovingState#Up} or {@link MovingState#SlowUp}
	 * @return true if state is {@link MovingState#Up} or {@link MovingState#SlowUp}
	 */
	public boolean isGoingUp(){
		return this == Up || this == SlowUp;
	}

	/**
	 * return true if state is {@link MovingState#SlowUp} or {@link MovingState#SlowDown}
	 * @return true if state is {@link MovingState#SlowUp} or {@link MovingState#SlowDown}
	 */
	public boolean isGoingSlow(){
		return this == SlowDown || this == SlowUp;
	}
}