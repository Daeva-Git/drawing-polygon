public class Matrix {
    // Function to multiply
    // two matrices A[][] and B[][]
    public static float[][] multiplyMatrix(float[][] A, float[][] B) {
        final int row1 = A.length;
        final int col1 = A[0].length;

        final int row2 = B.length;
        final int col2 = B[0].length;

        // Check if multiplication is Possible
        if (row2 != col1)
            return null;

        // Matrix to store the result
        // The product matrix will
        // be of size row1 x col2
        final float C[][] = new float[row1][col2];

        // Multiply the two matrices
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < row2; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        printMatrix(C);
        return C;
    }

    public static void printMatrix(float matrix[][]) {
        final int rows = matrix.length;
        final int cols = matrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}
