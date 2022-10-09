import java.util.ArrayList;

import library.ScanConversion;

import java.awt.image.BufferedImage;

// Polygon, Rectangle or Square
public class Polygon {

    // array or list of points
    private ArrayList<Point> points = new ArrayList<>();

    public Polygon(Point p1, Point p2, Point p3, Point p4) {
        // add the points to the list
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.points.add(p4);
    }

    public void draw(BufferedImage image) {
        // iterate over all points and draw a closed polygon
        // by calling the bresenhamLine method
        for (int i = 1; i < points.size(); i++) {
            final Point previousPoint = points.get(i - 1);
            final Point currentPoint = points.get(i);

            ScanConversion.bresenhamLine(
                    (int) previousPoint.getX(),
                    (int) previousPoint.getY(),
                    (int) currentPoint.getX(),
                    (int) currentPoint.getY(),
                    image, new int[] { 255, 255, 255 });
        }

        // draw the line between p1 and pn
        final Point firstPoint = points.get(0);
        final Point lastPoint = points.get(points.size() - 1);

        ScanConversion.bresenhamLine(
                (int) firstPoint.getX(),
                (int) firstPoint.getY(),
                (int) lastPoint.getX(),
                (int) lastPoint.getY(),
                image, new int[] { 255, 255, 255 });
    }

    // getters
    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void clearPoints() {
        points.clear();
    }
}
