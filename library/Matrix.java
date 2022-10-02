package library;
public class Matrix {
    
    public static float[][] matrixMultiplication(float[][] m1, float[][] m2) {
        final int firstMatrixRow = m1.length;
        final int firstMatrixCol = m1[0].length;

        final int secondMatrixRow = m2.length;
        final int secondMatrixCol = m2[0].length;

        // check if the matrix sizes match
        if (secondMatrixRow != firstMatrixCol)
            return null;

        // create a new matrix
        final float newMatrix[][] = new float[firstMatrixRow][secondMatrixCol];

        // iterate over the rows
        for (int row = 0; row < firstMatrixRow; row++) {
            // iterate over the columns
            for (int col = 0; col < secondMatrixCol; col++) {
                // compute the dot product and set it in the new matrix
                for (int k = 0; k < secondMatrixRow; k++) {
                    newMatrix[row][col] += m1[row][k] * m2[k][col];
                }
            }
        }

        // return the new matrix
        return newMatrix;
    }
}
