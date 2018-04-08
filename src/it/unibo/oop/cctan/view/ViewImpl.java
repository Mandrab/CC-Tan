package it.unibo.oop.cctan.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ViewImpl extends JFrame implements View {

	private static final long serialVersionUID = -7557896163163466182L;
	private Dimension gameWindowSize;
	private Pair<Integer, Integer> displayRatio;
	private GraphicPanel gpanel;
	
	public ViewImpl() {
		//show(new ImmutablePair<Integer, Integer>(16, 9), new Dimension(100, 100));
	}

	public void show(Pair<Integer, Integer> displayRatio, Dimension gameWindowSize) {
		setTitle("CC-Tan!");
		this.gameWindowSize = gameWindowSize;
		this.displayRatio = displayRatio;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		gpanel = new GraphicPanel(this);
		getContentPane().add(gpanel, BorderLayout.CENTER);

		setSize(gameWindowSize.width, gameWindowSize.height);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public Dimension getDimension() {
		return gameWindowSize;
	}

	@Override
	public Point getWindowLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMouseRelativePosition(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommandsObserver(CommandsObserver commandsObserver) {
		// TODO Auto-generated method stub
		
	}
	
}
