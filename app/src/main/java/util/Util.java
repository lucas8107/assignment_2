package util;

public abstract class Util {
	
	/**
	 * Check if the board is symmetric in order to
	 * cancel the search on symmetric descendants
	 * @param cells
	 * @return
	 */
	public static <T> boolean isSymmetric(char[][] cells) {	
		for(int i = 0; i < 6; i++)
			for(int j = 0; j < 3; j++)
				if(cells[i][j] != cells[i][6 - j])
					return false;
		return true;
	}
	
	/**
	 * Draw the game board
	 * @param matrix
	 */
	public static void draw2Darray(char[][] matrix) {
		String string = new String();
		
		for(int i = 0; i < 6; i++) {
			string += "\t" + matrix[5 - i][0] + " " +
							 matrix[5 - i][1] + " " +
							 matrix[5 - i][2] + " " +
							 matrix[5 - i][3] + " " +
							 matrix[5 - i][4] + " " +
							 matrix[5 - i][5] + " " +
							 matrix[5 - i][6] + '\n';
		}
		System.out.print(string);
		System.out.println("Col:1 2 3 4 5 6 7\n");
	}
	
	/**
	 * Copy an array
	 * @param array
	 * @return Copy of the array passed as arg
	 */
	public static int[] copyArray(int[] array) {
		int[] copy = new int[7];
		for(int i = 0; i < 7; i++)
			copy[i] = array[i];
		return copy;
	}
	
	/**
	 * Copy a 2D array
	 * @param array
	 * @return Copy of the 2D array passed as arg
	 */
	public static char[][] copy2Darray(char[][] array) {
		char[][] copy = new char[6][7];
		for(int i = 0; i < 6; i++)
			for(int j = 0; j < 7; j++)
				copy[i][j] = array[i][j];
		return copy;
	}
}
