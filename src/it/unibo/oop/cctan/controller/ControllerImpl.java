package it.unibo.oop.cctan.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.view.View;

public class ControllerImpl implements Controller {

    private Optional<View> view;
    private MappableDataAdapter mappableDataAdapter;
    
    public ControllerImpl() {
        mappableDataAdapter = new MappableDataAdapter();
    }
    
    @Override
    public List<MappableData> getListOfMappableData() {
        return mappableDataAdapter.getListOfMappableData();
    }

    @Override
    public int getScore() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void advanceLoading(int value) {
        if(view.isPresent()) {
            view.get().advanceLoading(value);
        }
    }

    @Override
    public void setView(View v) {
        this.view = Optional.of(v);
        FileLoader fileLoader = new FileLoader(this);
        fileLoader.start();
    }

    @Override
    public void setLoadImage(ImageIcon img) {
        if(view.isPresent()) {
            view.get().setLoadImage(img);
        }
    }

}
