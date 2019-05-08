//silas Bartha, May 8, 2019, Array Map Generation Assignment
package arrays;

public class ArrayMap {

	static final int SIZE = 20;
	static int[][] arr = new int[SIZE][SIZE];
	
	public static void main(String[] args) {
		//Create Map
		loadArray();
		//Display Map
		printArray();
	}

	static boolean isEdge(int row, int col){
		return(row == 0 || row == SIZE-1 || col == 0 || col == SIZE-1);
	}
	
	static void loadArray(){
		for(int row = 0; row < SIZE; row++){
			for(int col = 0; col < SIZE; col++){
				//Set edges to 10
				if(isEdge(row,col)) arr[row][col] = 10;
				
				/* Set (8,2) to 99 if size is greater than 8
				 * NOTE: The point (8, 2) suggests an X-value (column) of 8, unlike what is displayed in the example
				 * Recall: Java handles 2D arrays by accessing the ROW first, then the COL.
				 */
 				if(SIZE > 8) arr[2][8] = 99;
			}
		}
	}
	
	static void printArray(){
		for(int row = 0; row < SIZE; row++){
			for(int col = 0; col < SIZE; col++){
				System.out.printf("%3d", arr[row][col]);
			}
			System.out.println();
		}
	}
}
