package it.unibo.oop.cctan.controller;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.SquareAgent;

public class MappableDataAdapter {

    private Model model;
    
    public MappableDataAdapter(Model model) {
        this.model = model;
    }

    public List<MappableData> getListOfMappableData() {
        //Add all the bullets to the list of mappable data
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
                                                      sa.getColor(), 
                                                      sa.getShape()))
                      .collect(Collectors.toList()));
        
        //Add shuttle to the list of mappable data
        l.add(0, 
              new MappableDataImpl("", 
                                   Color.WHITE,
                                   model.getShuttle()
                                        .getShape()));
        
      //Add all the powerup to the list of mappable data
        l.addAll(model.getPowerUpBlocks()
                      .stream()
                      .map(pu -> new MappableDataImpl(pu.getSymbol() + " " + pu.getHP(), 
                                                      pu.getColor(), 
                                                      pu.getShape()))
                      .collect(Collectors.toList()));
        return l;
    }

}
