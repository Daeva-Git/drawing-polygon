package library;

public class Transformation3D {
    public static float[][] getTranslationMatrix(float tx, float ty, float tz) {
        // create and return the translation matrix
        final float[][] translationMatrix = {
                { 1, 0, 0, tx },
                { 0, 1, 0, ty },
                { 0, 0, 1, tz },
                { 0, 0, 0, 1 }
        };
        return translationMatrix;
    }

    public static float[][] getRotationMatrixY(float angle) {
        // get rotation in radians
        final float rot = (float) Math.toRadians(angle);

        // calculate sin/cos
        final float sin = (float) Math.sin(rot);
        final float cos = (float) Math.cos(rot);

        // create and return a rotation matrix that rotates the object around the y axis
        final float[][] rotationMatrix = {
                { cos, 0, sin, 0 },
                { 0, 1, 0, 0 },
                { -sin, 0, cos, 0 },
                { 0, 0, 0, 1 }
        };
        return rotationMatrix;
    }

    public static float[][] getScalingMatrix(float sx, float sy, float sz) {
        // create and return the scaling matrix
        final float[][] scalingMatrix = {
                { sx, 0, 0, 0 },
                { 0, sy, 0, 0 },
                { 0, 0, sz, 0 },
                { 0, 0, 0, 1 }
        };
        return scalingMatrix;
    }
}
