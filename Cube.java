import java.util.ArrayList;
import java.awt.image.BufferedImage;

// takes care of the model space and the world space
public class Cube {
    // array or list of polygons
    private ArrayList<Polygon> polygons = new ArrayList<>();

    // world matrix set to identity
    public Cube() {
        // create 8 points in the model space representing a cube of unit dimensions
        // leftBottomBack
        final Point leftBottomBack = new Point(-0.5f, -0.5f, -0.5f);
        // rightBottomBack
        final Point rightBottomBack = new Point(0.5f, -0.5f, -0.5f);
        // rightTopBack
        final Point rightTopBack = new Point(0.5f, 0.5f, -0.5f);
        // leftTopBack
        final Point leftTopBack = new Point(-0.5f, 0.5f, -0.5f);
        // leftBottomFront
        final Point leftBottomFront = new Point(-0.5f, -0.5f, 0.5f);
        // rightBottomFront
        final Point rightBottomFront = new Point(0.5f, -0.5f, 0.5f);
        // rightTopFront
        final Point rightTopFront = new Point(0.5f, 0.5f, 0.5f);
        // leftTopFront
        final Point leftTopFront = new Point(-0.5f, 0.5f, 0.5f);

        // create 6 faces using the points above
        // right
        final Polygon right = new Polygon(rightBottomFront, rightBottomBack, rightTopBack, rightTopFront);
        // left
        final Polygon left = new Polygon(leftBottomFront, leftBottomBack, leftTopBack, leftTopFront);
        // top
        final Polygon top = new Polygon(leftTopFront, leftTopBack, rightTopBack, rightTopFront);
        // bottom
        final Polygon bottom = new Polygon(leftBottomFront, leftBottomBack, rightBottomBack, rightBottomFront);
        // front
        final Polygon front = new Polygon(leftBottomFront, leftTopFront, rightTopFront, rightBottomFront);
        // back
        final Polygon back = new Polygon(leftBottomBack, leftTopFront, rightTopBack, rightBottomBack);

        // add those faces to the list of polygons
        this.polygons.add(right);
        this.polygons.add(left);
        this.polygons.add(top);
        this.polygons.add(bottom);
        this.polygons.add(front);
        this.polygons.add(back);
    }

    public void draw(BufferedImage image) {
        // iterate over all polygons
        for (final Polygon polygon : this.polygons) {
            // call their draw method
            polygon.draw(image);
        }
    }

    public void translate(float tx, float ty, float tz) {
        // get the translation matrix
        // update the world matrix
    }

    public void scale(float sx, float sy, float sz) {
        // get the scaling matrix
        // update the world matrix
    }

    public void rotateY(float angle) {
        // get the rotation matrix around the y axis
        // update the world matrix
    }
}