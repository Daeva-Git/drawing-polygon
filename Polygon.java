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

            ScanConversion.bresenhamLine(previousPoint.getX(), previousPoint.getY(), currentPoint.getX(),
                    currentPoint.getY(), image, new int[] { 255, 255, 255 });
        }

        // draw the line between p1 and pn
        final Point firstPoint = points.get(0);
        final Point lastPoint = points.get(points.size() - 1);

        ScanConversion.bresenhamLine(firstPoint.getX(), firstPoint.getY(),
                lastPoint.getX(), lastPoint.getY(), image,
                new int[] { 255, 255, 255 });
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void clearPoints() {
        points.clear();
    }

    // public Point centroid() {
    //     // compute the average of all x and y
    //     int x = 0;
    //     int y = 0;
    //     for (Point point : points) {
    //         x += point.getX();
    //         y += point.getY();
    //     }

    //     // return a new point
    //     return new Point(x / points.size(), y / points.size());
    // }

    // public void translate(int tx, int ty) {
    //     // translate all points
    //     for (Point point : points) {
    //         point.translate(tx, ty);
    //     }
    // }

    // public void rotate(float angle) {
    //     final Point centroid = centroid();

    //     // rotate all points around the centroid
    //     for (Point point : points) {
    //         point.rotate(centroid.getX(), centroid.getY(), angle);
    //     }
    // }

    // public void scale(float sx, float sy) {
    //     final Point centroid = centroid();

    //     // scale all points in respect to the centroid
    //     for (Point point : points) {
    //         point.scale(centroid.getX(), centroid.getY(), sx, sy);
    //     }
    // }
}
