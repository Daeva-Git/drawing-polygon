package library;
public class Transformation2D {
    public static float[][] getTranslationMatrix(int tx, int ty) {
        // create and return the translation matrix
        float[][] translationMatrix = {
            { 1, 0, tx },
            { 0, 1, ty },
            { 0, 0, 1 }
        };
        return translationMatrix;
    }

    public static float[][] getRotationMatrix(float angle) {
        // get rotation in radians
        final float rot = (float) Math.toRadians(angle);

        // calculate sin/cos
        final float sin = (float) Math.sin(rot);
        final float cos = (float) Math.cos(rot);

        // create and return the rotation matrix
        final float[][] rotationMatrix = {
                { cos, -sin, 0 },
                { sin, cos, 0 },
                { 0, 0, 1 }
        };
        return rotationMatrix;
    }

    public static float[][] getScalingMatrix(float sx, float sy) {
        // create and return the scaling matrix
        float[][] scalingMatrix = {
                { sx, 0, 0 },
                { 0, sy, 0 },
                { 0, 0, 1 }
        };
        return scalingMatrix;
    }

    // cx and cy can be the centroid coordinates of the polygon
    public static float[][] getRotationMatrixT(int cx, int cy, float angle) {
        // create translation matrix 1 (move to origin)
        final float[][] moveToOriginMatrix = getTranslationMatrix(-cx, -cy);

        // create rotation matrix
        final float[][] rotationMatrix = getRotationMatrix(angle);

        // create translation matrix 2 (move back to position)
        final float[][] backToPosition = getTranslationMatrix(cx, cy);

        // create and return the transformation matrix
        float[][] transformationMatrix = Matrix.matrixMultiplication(rotationMatrix, moveToOriginMatrix);
        transformationMatrix = Matrix.matrixMultiplication(backToPosition, transformationMatrix);
        return transformationMatrix;
    }

    public static float[][] getScalingMatrixT(int cx, int cy, float sx, float sy) {
        // create translation matrix 1 (move to origin)
        final float[][] moveToOriginMatrix = getTranslationMatrix(-cx, -cy);

        // create rotation matrix
        final float[][] scalingMatrix = getScalingMatrix(sx, sy);

        // create translation matrix 2 (move back to position)
        final float[][] backToPosition = getTranslationMatrix(cx, cy);

        // create and return the transformation matrix
        float[][] transformationMatrix = Matrix.matrixMultiplication(scalingMatrix, moveToOriginMatrix);
        transformationMatrix = Matrix.matrixMultiplication(backToPosition, transformationMatrix);
        return transformationMatrix;
    }
}
