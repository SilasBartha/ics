//Silas Bartha, Array Nums program for determining user luck. May 3, 2019.

package arrays;

public class Array_nums {
	
	public static void main(String[] args) {
		//make an integer array of size 30 and fill it with random numbers from 1 to 20 inclusive
		int[] myArray = loadData(30);
		//print array out
		printArray(myArray);
		/*count 7s and 13s, return luck based on these Conditions:
		 * If there are three or more 13s return 'X' -- this means that your luck is so bad that it is deadly.
		 * If there are more 7s than 13s return 'G' for good luck, otherwise return 'B' for bad luck.
		 * Except that if you have no 13s at all, then you have good luck too, even if you dont' have any 7s.
		 */
		char luck = getLuck(myArray);
		//Print Luck
		System.out.println("Luck: " + luck);
		//Get sum of all int in array
		int sum = arraySum(myArray);
		//Print sum
		System.out.println("Sum: " + sum);
		// If sum is more than 330 (size of array times top half of the random numbers) AND if you already have 'G' luck then print out that you are exceptionally lucky.
		if(exLucky(myArray)){
			System.out.println("You are exceptionally lucky!");
		}
	}
	
	//Print each int in array
	static void printArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
		
	//Create Array
	static int[] loadData(int size) {
		int[] arr = new int[size];
		for (int i = 0; i < arr.length; i++) {			
			arr[i] = (int)(Math.random()*20+1);
		}
		return arr;
	}
	
	//Count how many of a number are in an array
	static int count(int countNum, int[] myArray){
		int num = 0;
		for (int i = 0; i < myArray.length; i++) {		
			if(myArray[i] == countNum){
				num++;
			}
		}
		return(num);
	}
	
	//Get how lucky the user is
	static char getLuck(int[] myArray){
		int sevens = count(7,myArray);
		int thirteens = count(13,myArray);
		
		if(thirteens >= 3){
			return('X');
		}
		if(sevens>thirteens||thirteens==0){
			return('G');
		}else{
			return('B');
		}
		
	}
	
	//Get sum of all parts in an integer array
	static int arraySum(int[] myArray){
		int sum=0;
		for(int i:myArray){
			sum+=i;
		}
		return sum;
	}

	//Determine if user is exceptionally lucky
	static boolean exLucky(int[] myArray){
		return(arraySum(myArray)>330&&getLuck(myArray)=='G');
	};
}
