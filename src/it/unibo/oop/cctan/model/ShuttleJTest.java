package it.unibo.oop.cctan.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import javafx.geometry.Point2D;


/**
 * A test for basic operations on the Shuttle.
 */
public class ShuttleJTest {

    private static final String SEPARATOR = "-----------------------------------";

    /**
     * Test the movement (rotation) of the shuttle, checking some known points (0, 90, 180, and 270 degrees).
     * Note: to run correctly this test, please set INTERVALS field to 1 in ShuttleImpl class.
     */
    @Test
    public void testRotationShuttle() {
        final double tollerance = 0.00001;
        System.out.println("testRotationShuttle");
        final Model model = new ModelImpl();
        final Shuttle shuttle = model.getShuttle();

        // now shuttle top should be on the right
        shuttle.setAngle(0);
        comparePoints(new Point2D(0.5, 0.5), shuttle.getPos(), tollerance);
        List<Point2D> shapePoints = shuttle.getShape();
        List<Point2D> expectedPoints = Arrays.asList(new Point2D(0.5, 0), new Point2D(-0.5, 0.5),
                new Point2D(-0.5, -0.5));
        comparePointLists(expectedPoints, shapePoints, tollerance);
        printShuttleInfo(shuttle.getPos(), shapePoints);
        System.out.println(SEPARATOR);

        // now set angle to 90° (starting from x-axis), such that shuttle top should be in the up
        shuttle.setAngle(90);
        comparePoints(new Point2D(-0.5, 0.5), shuttle.getPos(), tollerance);
        shapePoints = shuttle.getShape();
        expectedPoints = Arrays.asList(new Point2D(0, 0.5), new Point2D(-0.5, -0.5), new Point2D(0.5, -0.5));
        comparePointLists(expectedPoints, shapePoints, tollerance);
        printShuttleInfo(shuttle.getPos(), shapePoints);
        System.out.println(SEPARATOR);

        // now set angle to 180° (starting from x-axis), such that shuttle top should be on the left
        shuttle.setAngle(180);
        comparePoints(new Point2D(-0.5, -0.5), shuttle.getPos(), tollerance);
        shapePoints = shuttle.getShape();
        expectedPoints = Arrays.asList(new Point2D(-0.5, 0), new Point2D(0.5, -0.5), new Point2D(0.5, 0.5));
        comparePointLists(expectedPoints, shapePoints, tollerance);
        printShuttleInfo(shuttle.getPos(), shapePoints);
        System.out.println(SEPARATOR);

        // now set angle to 270° (starting from x-axis), such that shuttle top should be on the bottom
        final double angle = 270;
        shuttle.setAngle(angle);
        comparePoints(new Point2D(0.5, -0.5), shuttle.getPos(), tollerance);
        shapePoints = shuttle.getShape();
        expectedPoints = Arrays.asList(new Point2D(0, -0.5), new Point2D(0.5, 0.5), new Point2D(-0.5, 0.5));
        comparePointLists(expectedPoints, shapePoints, tollerance);
        printShuttleInfo(shuttle.getPos(), shapePoints);
        System.out.println(SEPARATOR);
    }

    /**
     * Test the library function scale around the shape center.
     */
    @Test
    public void testScale() {
        System.out.println("testScale");
        final int maxCoord = 20, increment = 5, maxSize = 30;
        final double scale = 0.5, tollerance = 0.00001;
        for (Point2D pos = new Point2D(0, 0); pos.getX() < maxCoord; pos = new Point2D(pos.getX() + increment,
                pos.getY())) {
            for (; pos.getY() < maxCoord; pos = new Point2D(pos.getX(), pos.getY() + increment)) {
                for (int width = increment; width < maxSize; width += increment) {
                    for (int height = increment; height < maxSize; height += increment) {
                        Rectangle2D rect = new Rectangle2D.Double(pos.getX(), pos.getY(), width, height);
                        final AffineTransform tx = new AffineTransform();
                        // tx.translate(pos.getX() + (width / 2) * (1 - factor),
                        // pos.getY() + (height / 2) * (1 - factor));
                        tx.translate((width * (1 - scale) + pos.getX()) / 2, (height * (1 - scale) + pos.getY()) / 2);
                        tx.scale(scale, scale);
                        rect = tx.createTransformedShape(rect).getBounds2D();
                        assertEquals(pos.getX() + width * (1 - scale) / 2, rect.getX(), tollerance,
                                "wrong X coord. " + pos + "; " + width + ", " + height + "; " + scale);
                        assertEquals(pos.getY() + height * (1 - scale) / 2, rect.getY(), tollerance,
                                "wrong Y coord. " + pos + "; " + width + ", " + height + "; " + scale);
                        assertEquals(width * scale, rect.getWidth(), tollerance, "wrong width");
                        assertEquals(height * scale, rect.getHeight(), tollerance, "wrong height");

                        // System.out.println("Width: " + rect.getWidth() + "; Height: " +
                        // rect.getHeight());
                        // System.out.println("Coordinate: (" + rect.getX() + "; " + rect.getY() + ")");
                        // System.out.println(SEPARATOR);
                    }
                }
            }
        }
        System.out.println(SEPARATOR);
    }

    private void comparePointLists(final List<Point2D> expected, final List<Point2D> actual, final double tollerance) {
        actual.forEach(p -> comparePoints(expected.get(actual.indexOf(p)), p, tollerance));
    }

    private void comparePoints(final Point2D expected, final Point2D actual, final double tollerance) {
        assertEquals(expected.getX(), actual.getX(), tollerance, "wrong X coord. ");
        assertEquals(expected.getY(), actual.getY(), tollerance, "wrong Y coord. ");
    }

    private void printShuttleInfo(final Point2D pos, final List<Point2D> coords) {
        System.out.println("Coordinate: (" + pos.getX() + "; " + pos.getY() + ")");
        coords.forEach(p -> System.out.println("(" + p.getX() + "; " + p.getY() + ")"));
    }
}
