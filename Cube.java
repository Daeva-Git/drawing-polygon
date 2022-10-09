import java.util.ArrayList;

import library.Matrix;
import library.Transformation3D;

import java.awt.image.BufferedImage;

// takes care of the model space and the world space
public class Cube {
    // array or list of polygons
    private ArrayList<Polygon> polygons = new ArrayList<>();

    // world matrix set to identity
    private float[][] worldMatrix = {
            { 1, 0, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 1 }
    };

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

        // create 6 faces using the points above (polygons are winded clockwise)
        // right
        final Polygon right = new Polygon(rightTopFront.copy(), rightTopBack.copy(), rightBottomBack.copy(),
                rightBottomFront.copy());
        // left
        final Polygon left = new Polygon(leftTopBack.copy(), leftTopFront.copy(), leftBottomFront.copy(),
                leftBottomBack.copy());
        // top
        final Polygon top = new Polygon(leftTopFront.copy(), leftTopBack.copy(), rightTopBack.copy(),
                rightTopFront.copy());
        // bottom
        final Polygon bottom = new Polygon(rightBottomFront.copy(), leftBottomFront.copy(), leftBottomBack.copy(),
                rightBottomBack.copy());
        // front
        final Polygon front = new Polygon(leftTopFront.copy(), rightTopFront.copy(), rightBottomFront.copy(),
                leftBottomFront.copy());
        // back
        final Polygon back = new Polygon(rightTopBack.copy(), leftTopBack.copy(), leftBottomBack.copy(),
                rightBottomBack.copy());

        // add those faces to the list of polygons
        this.polygons.add(right);
        this.polygons.add(left);
        this.polygons.add(top);
        this.polygons.add(bottom);
        this.polygons.add(front);
        this.polygons.add(back);
    }

    // getters
    public float[][] getWorldMatrix() {
        return this.worldMatrix;
    }

    public ArrayList<Polygon> getPolygons() {
        return this.polygons;
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
        final float[][] translationMatrix = Transformation3D.getTranslationMatrix(tx, ty, tz);

        // update the world matrix
        this.worldMatrix = Matrix.matrixMultiplication(translationMatrix, this.worldMatrix);
    }

    public void scale(float sx, float sy, float sz) {
        // get the scaling matrix
        final float[][] scalingMatrix = Transformation3D.getScalingMatrix(sx, sy, sz);

        // update the world matrix
        this.worldMatrix = Matrix.matrixMultiplication(scalingMatrix, this.worldMatrix);
    }

    public void rotateY(float angle) {
        // get the rotation matrix around the y axis
        final float[][] rotationMatrix = Transformation3D.getRotationMatrixY(angle);

        // update the world matrix
        this.worldMatrix = Matrix.matrixMultiplication(rotationMatrix, this.worldMatrix);
    }

    public void rotateYOrigin(float angle) {
        // get the rotation matrix around the y axis
        final float[][] rotationMatrix = Transformation3D.getRotationMatrixY(angle);

        // update the world matrix
        this.worldMatrix = Matrix.matrixMultiplication(this.worldMatrix, rotationMatrix);
    }
}