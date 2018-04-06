package it.unibo.oop.cctan.view;

import java.awt.*;
import javax.swing.*;
import org.apache.commons.lang3.tuple.*;

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
	
}
