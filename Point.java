import library.Matrix;
import library.Transformation3D;

public class Point {
    // define point as a 4x1 matrix with x, y, z, w coordinates
    private float[][] point = new float[4][1];

    // constructors
    public Point(float x, float y, float z) {
        this(x, y, z, 1);
    }

    public Point(float x, float y, float z, float w) {
        this.setPoint(x, y, z, w);
    }

    // getters
    public int getX() {
        return (int) this.point[0][0];
    }

    public int getY() {
        return (int) this.point[1][0];
    }

    public int getZ() {
        return (int) this.point[2][0];
    }

    // setters
    public void setPoint(float x, float y, float z) {
        this.setPoint(x, y, z, 1);
    }

    public void setPoint(float x, float y, float z, float w) {
        point[0][0] = x;
        point[1][0] = y;
        point[2][0] = z;
        point[3][0] = w;
    }

    public void translate(int tx, int ty, int tz) {
        // get the translation matrix
        final float[][] transformationMatrix = Transformation3D.getTranslationMatrix(tx, ty, tz);

        // update the point coordinates wih matrix multiplication
        point = Matrix.matrixMultiplication(transformationMatrix, point);
    }


    // TODO: something not cool here about the origin
    public void rotate(int cx, int cy, int cz, float angle) {
        // get the rotation matrix
        final float[][] rotationMatrix = Transformation3D.getRotationMatrixY(angle);

        // update the point coordinates
        point = Matrix.matrixMultiplication(rotationMatrix, point);
    }

    // TODO: something not cool here about the origin
    public void scale(int cx, int cy, float sx, float sy, float sz) {
        // get the scaling matrix
        final float[][] scalingMatrix = Transformation3D.getScalingMatrix(sx, sy, sz);

        // update the point coordinates
        point = Matrix.matrixMultiplication(scalingMatrix, point);
    }
}
