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
    private float[][] viewMatrix = {
            { 1, 0, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 1 }
    };
    private float[][] projectionMatrix;

    // constructor
    public Camera(float theta, float width, float height, float near, float far) {
        this.theta = theta;
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;

        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
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
        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
    }

    public void setWidth(float width) {
        this.width = width;
        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
    }

    public void setHeight(float height) {
        this.height = height;
        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
    }

    public void setNear(float near) {
        this.near = near;
        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
    }

    public void setFar(float far) {
        this.far = far;
        this.projectionMatrix = makeProjectionMatrix(theta, width / height, near, far);
    }

    public void setViewMatrix(float[][] viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public void setProjectionMatrix(float[][] projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public float[][] makeProjectionMatrix(float theta, float aspectRatio, float near, float far) {
        final float tangent = (float) Math.tan(Math.toRadians(theta / 2.0f));
        final float[][] projectionMatrix = {
                { 1 / tangent / aspectRatio, 0, 0, 0 },
                { 0, 1 / tangent, 0, 0 },
                { 0, 0, -(far + near) / (far - near), -2 * far * near / (far - near) },
                { 0, 0, -1, 0 }
        };
        return projectionMatrix;
    }

    public void translateCamera(float tx, float ty, float tz) {
        // get the translation matrix
        final float[][] translationMatrix = Transformation3D.getTranslationMatrix(tx, ty, tz);

        // update the view matrix
        this.viewMatrix = Matrix.matrixMultiplication(translationMatrix, viewMatrix);
    }
}
