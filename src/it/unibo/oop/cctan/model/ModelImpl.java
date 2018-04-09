package it.unibo.oop.cctan.model;

import java.util.List;

import it.unibo.oop.cctan.geometry.Boundary;

public class ModelImpl implements Model {

	private final Boundary bound;
	private final Shuttle shuttle;
	private final BallGenerator ballGenerator;
	private final SquareGenerator squareGenerator;

	public ModelImpl() {
		this.bound = new Boundary(-1, -1, 1, 1);
		this.shuttle = new ShuttleImpl(this);
		this.squareGenerator = new SquareGeneratorImpl(this);
		this.ballGenerator = new BallGeneratorImpl(this);
	}

	@Override
	public Boundary getBounds() {
		return this.bound;
	}

	@Override
	public void start() {
		this.squareGenerator.start();
		this.ballGenerator.start();
	}

	@Override
	public void removeSquare(SquareAgent square) {
		this.squareGenerator.removeSquare(square);
	}

	@Override
	public void removeBall(BallAgent ball) {
		this.ballGenerator.removeBall(ball);
	}

	@Override
	public List<SquareAgent> getSquareAgents() {
		return this.squareGenerator.getSquareAgents();
	}

	@Override
	public List<BallAgent> getBallAgents() {
		return this.ballGenerator.getBallAgents();
	}

	@Override
	public Shuttle getShuttle() {
		return this.shuttle;
	}
}
