package it.unibo.oop.cctan.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.oop.cctan.model.BallAgent;

public class BallGeneratorImpl extends Thread implements BallGenerator {

	private static final int GENERATION_RATIO = 500;
	private List<BallAgent> balls;
	private final Model model;
	
	public BallGeneratorImpl(final Model model) {
		this.model = model;
		this.balls = new ArrayList<>();
	}

	@Override
	public void removeBall(BallAgent ball) {
		if (!this.balls.isEmpty() && ball != null) {
			ball.terminate();
			this.balls.remove(ball);
		}
	}

	@Override
	public List<BallAgent> getBallAgents() {
		return Collections.unmodifiableList(this.balls);
	}

	@Override
	public void start() {
		super.run();
	}

}
