import library.Matrix;
import library.Transformation3D;

// takes care of the view space and projection space
public class Camera {
    // theta, width, height, near, far
    private float theta;
    private float width;
    private float height;
    private float near;
    private float far;

    // viewMatrix, projectionMatrix
    private float[][] viewMatrix;
    private float[][] projectionMatrix;

    // constructor
    public Camera() {

    }

    // getters
    public float getTheta() {
        return theta;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public float[][] getViewMatrix() {
        return viewMatrix;
    }

    public float[][] getProjectionMatrix() {
        return projectionMatrix;
    }

    // setters
    public void setTheta(float theta) {
        this.theta = theta;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public void setViewMatrix(float[][] viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public void setProjectionMatrix(float[][] projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public float[][] makeProjectionMatrix(float theta, float aspectRatio, float near, float far) {
        final float tangent = (float) Math.tan(Math.toRadians(theta / 2.0f));
        final float height = near * tangent;
        final float width = height * aspectRatio;
        return this.makeProjectionMatrix(-width, width, -height, height, near, far);
    }

    public float[][] makeProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
        // create and return the projection matrix
        final float[][] projectionMatrix = {
                { 2 * near / (right - left), 0, (right + left) / (right - left), 0 },
                { 0, 2 * near / (top - bottom), (top + bottom) / (top - bottom), 0 },
                { 0, 0, -(far + near) / (far - near), -2 * far * near / (far - near) },
                { 0, 0, -1, 0 }
        };
        return projectionMatrix;
    }

    public void translateCamera(float tx, float ty, float tz) {
        // get the translation matrix
        final float[][] translationMatrix = Transformation3D.getTranslationMatrix(tx, ty, tz);

        // update the view matrix
        this.viewMatrix = Matrix.matrixMultiplication(viewMatrix, translationMatrix);
    }
}
