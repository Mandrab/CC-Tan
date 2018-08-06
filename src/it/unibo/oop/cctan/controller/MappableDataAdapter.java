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
        //Add all the squares to the list of mappable data
        //System.out.println(model.getBallAgents());
        List<MappableData> l = model.getBallAgents()
                                    .stream()
                                    .map(ba -> new MappableDataImpl("", 
                                                                    Color.RED, 
                                                                    new Ellipse2D.Double(ba.getPos().getX(), 
                                                                                         ba.getPos().getY(), 
                                                                                         BallAgent.WIDTH, 
                                                                                         BallAgent.HEIGHT)))
                                    .collect(Collectors.toList());
        
        //Add all the squares to the list of mappable data
        l.addAll(model.getSquareAgents()
                      .stream()
                      .map(sa -> new MappableDataImpl("" + sa.getHP(), 
                                                      calculateColor(sa.getHP()), 
                                                      new Rectangle2D.Double(sa.getPos().getX(), 
                                                                             sa.getPos().getY(), 
                                                                             SquareAgent.WIDTH, 
                                                                             SquareAgent.HEIGHT)))
                      .collect(Collectors.toList()));
        
        //Add shuttle to the list of mappable data
        l.add(0, 
              new MappableDataImpl("", 
                                   Color.WHITE,
                                   new Polygon2D.Double(getAxisPointArray(point -> point.getX()),
                                                        getAxisPointArray(point -> point.getY()),
                                                        3)));
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
