package it.unibo.oop.cctan.view;

import java.awt.Dimension;

import javax.swing.JPanel;

class GraphicPanel extends JPanel {

	private static final long serialVersionUID = 7666161570364892169L;
	private Dimension dimension;

	public GraphicPanel(View view) {
		dimension = view.getDimension();
		setSize(dimension);
		
	}
	
}
