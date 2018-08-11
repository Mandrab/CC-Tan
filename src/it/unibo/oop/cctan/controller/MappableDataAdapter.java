package it.unibo.oop.cctan.controller;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jhotdraw.geom.Polygon2D;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.model.BallAgent;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.SquareAgent;
import javafx.geometry.Point2D;

public class MappableDataAdapter {

    private Model model;
    
    public MappableDataAdapter(Model model) {
        this.model = model;
    }

    public List<MappableData> getListOfMappableData() {
        //Add all the bullets to the list of mappable data
        //usare il metodo getShape() per ottenere la shape giusta
        //System.out.println(model.getBallAgents());
        List<MappableData> l = model.getBulletAgents()
                                    .stream()
                                    .map(ba -> new MappableDataImpl("", 
                                                                    ba.getColor(),
                                                                    ba.getShape()))
                                    .collect(Collectors.toList());
        
        //Add all the squares to the list of mappable data
        l.addAll(model.getSquareAgents()
                      .stream()
                      .map(sa -> new MappableDataImpl("" + ((SquareAgent) sa).getHP(), 
                                                      ((SquareAgent) sa).getColor(),
                                                      sa.getShape()))
                      .collect(Collectors.toList()));
        
        //Add shuttle to the list of mappable data
        l.add(0, 
              new MappableDataImpl("", 
                                   Color.WHITE,
                                   new Polygon2D.Double(getAxisPointArray(point -> point.getX()),
                                                        getAxisPointArray(point -> point.getY()),
                                                        3)));
        l.addAll(model.getPowerUpBlocks()
                .stream()
                .map(pu -> new MappableDataImpl(pu.getSymbol() + " " + pu.getHP(), 
                                                pu.getColor(), pu.getShape()))
                .collect(Collectors.toList()));
        return l;
    }

    private double[] getAxisPointArray(Function<Point2D, Double> function) {
        return model.getShuttle()
                    .getShapePoints()
                    .stream()
                    .mapToDouble(point -> function.apply(point).doubleValue())
                    .toArray();
    }

    private Color calculateColor(int hp) {
        /*Color[] cols = new Color[n];
        for (int i = 0; i < n; i++)
            cols[i] = Color.getHSBColor((float) i / n, 1, 1);*/
        return null;
    }

}
