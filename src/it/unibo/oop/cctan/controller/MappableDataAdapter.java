package it.unibo.oop.cctan.controller;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;

import org.jhotdraw.geom.Polygon2D;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.model.BallAgent;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.SquareAgent;

public class MappableDataAdapter {

    private Model model;
    
    public List<MappableData> getListOfMappableData() {
        
        //Add all the squares to the list of mappable data
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
                                   model.getShuttle().getShape()));
                                   /*new Polygon2D.Double(new double[] {model.getShuttle().getPointx1,
                                                                      model.getShuttle().getPointx2,
                                                                      model.getShuttle().getPointx3},
                                                        new double[] {model.getShuttle().getPointy1,
                                                                      model.getShuttle().getPointy2,
                                                                      model.getShuttle().getPointy3},
                                                        3)));*/
        return l;
    }

    private Color calculateColor(int hp) {
        /*Color[] cols = new Color[n];
        for (int i = 0; i < n; i++)
            cols[i] = Color.getHSBColor((float) i / n, 1, 1);*/
        return null;
    }
    
}
