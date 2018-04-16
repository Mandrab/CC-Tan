package it.unibo.oop.cctan.model;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class SquareGeneratorImpl extends Thread implements SquareGenerator {

	private static final int GENERATION_RATIO = 2000;
	private final Model model;
	private final List<SquareAgent> squares;

	public SquareGeneratorImpl(final Model model) {
		this.squares = new ArrayList<>();
		this.model = model;
	}

	@Override
	public void start() {
		super.start(); 		
	}      
	
	@Override
	public void run() {
		while (true) {
			this.createNewSquare();
			try {
				Thread.sleep(GENERATION_RATIO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void createNewSquare() {
		SquareAgent square = new SquareAgent(this.model, new Random().nextInt(41)+40);
		this.squares.add(square);
		square.run();
	}

	@Override
	public synchronized void removeSquare(final SquareAgent square) {
		if (!this.squares.isEmpty() && square != null) {
			square.terminate();
			this.squares.remove(square);
		}
	}

	public synchronized List<SquareAgent> getSquareAgents() {
		return Collections.unmodifiableList(this.squares);
	}

}
