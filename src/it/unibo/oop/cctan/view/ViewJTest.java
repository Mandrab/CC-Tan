package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class ViewJTest {

    private static final int TIME_BEFORE_JUNIT_TEST_END = 5000; // ms

    @Test
    void test() throws InterruptedException {
        new ViewImpl(new Controller() {

            private int call = 0;

            @Override
            public List<MappableData> getListOfMappableData() {
                return Arrays.asList(new MappableData() {

                    @Override
                    public String getText() {
                        return "test shape 1";
                    }

                    @Override
                    public Shape getShape() {
                        return new Rectangle2D.Double(0 + (0.001 * call++), 0 + (0.001 * call++), 0.5, 0.5);
                    }

                    @Override
                    public Color getColor() {
                        return Color.WHITE;
                    }
                }, new MappableData() {

                    @Override
                    public String getText() {
                        return "Test with loooooooong text string ihihih";
                    }

                    @Override
                    public Shape getShape() {
                        return new Rectangle2D.Double(0 - (0.001 * call++), 0 - (0.001 * call++), 0.5, 0.5);
                    }

                    @Override
                    public Color getColor() {
                        return Color.RED;
                    }
                });
            }
        });
        Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
    }

}
