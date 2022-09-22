public class Point {
    // define point as a 3x1 matrix with x, y, w coordinates
    private float[][] point = new float[3][1];

    // constructor
    public Point(int x, int y) {
        this(x, y, 1);
    }

    public Point(int x, int y, int w) {
        this.setPoint(x, y, w);
    }

    // getters
    public int getX() {
        return (int) this.point[0][0];
    }

    public int getY() {
        return (int) this.point[1][0];
    }

    // setters
    public void setPoint(int x, int y) {
        this.setPoint(x, y, 1);
    }

    public void setPoint(int x, int y, int w) {
        point[0][0] = x;
        point[1][0] = y;
        point[2][0] = w;
    }

    public void translate(int tx, int ty) {
        // get the translation matrix
        final float[][] transformationMatrix = Transformation2D.getTranslationMatrix(tx, ty);

        // update the point coordinates wih matrix multiplication
        point = Matrix.matrixMultiplication(transformationMatrix, point);
    }

    public void rotate(int cx, int cy, float angle) {
        // get the rotation matrix
        final float[][] rotationMatrix = Transformation2D.getRotationMatrixT(cx, cy, angle);

        // update the point coordinates
        point = Matrix.matrixMultiplication(rotationMatrix, point);
    }

    public void scale(int cx, int cy, float sx, float sy) {
        // get the scaling matrix
        final float[][] scalingMatrix = Transformation2D.getScalingMatrixT(cx, cy, sx, sy);

        // update the point coordinates
        point = Matrix.matrixMultiplication(scalingMatrix, point);
    }
}
