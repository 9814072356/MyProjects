public class Auf1_MatrixOperations {
	public static void printMatrix(double[][] M) {      //        Print out the matrix with the form: 
		for(int i = 0; i < M[0].length; i++) {			//                 | 00 01 02 .... 0n |
			System.out.print("| ");						//                 | 10 11 12 .... 1n |
			for(int j = 0; j < M.length; j++) {			//				   | ................ |
				System.out.print(M[j][i] + "  ");		//                 | m0 m1 m2 .... mn |
			}
			System.out.print("|\n");
		}
	}
	public static void matMult(double[][] A, double[][] B) {
		if(A.length == 0 && B.length == 0) {			//  Check if the matrixes are empty or not
			System.out.println("Invalid input");
			return;
		}else {
			for(int i = 0; i < A.length; i++) {				//	 Check whether the inputs are matrixes or just arrays.
				if(A[i].length != A[0].length) {			//   The correct form of a matrix is a set of arrays with the same length, beside
					System.out.println("Invalid input");	//   that it's just an array.
					return;
				}
			}
			for(int i = 0; i < B.length; i++) {
				if(B[i].length != B[0].length) {
					System.out.println("Invalid input");
					return;
				}
			}
			double[][] R = new double[A.length][B[0].length];		// The matrix to be printed out, which is also the result of the operation between two inputs
			double result = 0.0;
			if(A.length != B[0].length) {							// In order to multiply two matrixes, it is required that the number 
				System.out.println("Invalid input");				// of columns of the first one to be as same as the number of rows
				return;												// of the second one.
			}else {
				for(int a = 0; a < B.length; a++) {				// Perform multiplying the rows of the first with the columns of the second matrix
					for(int i = 0; i < A[0].length; i++) {
						for(int j = 0; j < A.length; j++) {
							result += A[j][i] * B[a][j];
						}
						R[a][i] = result;						// After each multiplication of a row and a column, assign the result to 
						result = 0;								// the cell with the respective position in R (which means row i multiplied by column j
					}											// will be the value of R(i,j)). Then proceed until the end of second matrix.
				}
				printMatrix(R);
			}
		}
	}
	public static void matAdd(double[][] A, double[][] B) {
		for(int i = 0; i < A.length; i++) {					// Check if the input is a legitimate matrix
			if(A[i].length != A[0].length) {
				System.out.println("A is not a Matrix");
				return;
			}
		}
		for(int i = 0; i < B.length; i++) {					// Check if the input is a legitimate matrix
			if(B[i].length != B[0].length) {
				System.out.println("B is not a Matrix");
				return;
			}
		}
		if((A.length == 0 && B.length == 0) || (A.length != B.length) || (A[0].length != B[0].length)) {	// Conditions to add two matrixes
			System.out.println("Invalid input");
			return;
		}else {
			double[][] R = new double[A.length][B[0].length];
			for(int i = 0; i < A.length; i++) {						// Iterate through every cells of the two matrixes and add their values together
				for(int j = 0; j < A[0].length; j++) {			
					R[i][j] = A[i][j] + B[i][j];
				}
			}
			printMatrix(R);
			
		}
	}
	public static void main(String[] args) {
		double[][] a = {{1,2,3}};
		double[][] c = new double[3][5];
		printMatrix(a);
		System.out.println("   x   ");
		printMatrix(c);
		System.out.println("   =   ");
		matMult(a,c);
		System.out.println("\n");
		printMatrix(a);
		System.out.println("   +   ");
		printMatrix(c);
		System.out.println("   =   ");
		matAdd(a,c);
	}

}
