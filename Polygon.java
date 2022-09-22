import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Polygon {
    // define polygone as a list of points
    private ArrayList<Point> points = new ArrayList<>();

    // constructor
    public void addPoint(Point p) {
        points.add(p);
    }

    public void clearPoints() {
        points.clear();
    }

    // draw a polyline
    public void drawOpen(BufferedImage image) {
        // draw the lines respectively from p1, p2, ..., pn using the Bresenham algorithm
        for (int i = 1; i < points.size(); i++) {
            final Point previousPoint = points.get(i - 1);
            final Point currentPoint = points.get(i);

            ScanConversion.bresenhamLine(previousPoint.getX(), previousPoint.getY(), currentPoint.getX(), currentPoint.getY(), image, new int[] {255, 255, 255});
        }
    }

    // draw a polygon
    public void drawClosed(BufferedImage image) {
        // draw the line between p1 and pn
        final Point firstPoint = points.get(0);
        final Point lastPoint = points.get(points.size() - 1);
        
        ScanConversion.bresenhamLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY(), image, new int[] {255, 255, 255});
    }

    public Point centroid() {
        // compute the average of all x and y
        int x = 0;
        int y = 0;
        for (Point point : points) {
            x += point.getX();
            y += point.getY();
        }

        // return a new point
        return new Point(x / points.size(), y / points.size());
    }

    public void translate(int tx, int ty) {
        // translate all points
        for (Point point : points) {
            point.translate(tx, ty);
        }
    }

    public void rotate(float angle) {
        final Point centroid = centroid();

        // rotate all points around the centroid
        for (Point point : points) {
            point.rotate(centroid.getX(), centroid.getY(), angle);
        }
    }

    public void scale(float sx, float sy) {
        final Point centroid = centroid();
        
        // scale all points in respect to the centroid
        for (Point point : points) {
            point.scale(centroid.getX(), centroid.getY(), sx, sy);
        }
    }
}
