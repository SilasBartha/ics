//Silas Bartha, May 22 2019, Array Practice "Five in a Row" program.
package arrays;

public class FiveInARow {

	public static void main(String[] args) {
		//Number Array
		int[] nums = new int[20]; 
		
		//Set Values of Numbers in Array
		for(int i = 0; i < nums.length; i++){
			double rand = Math.random();
			if(rand<=.5){
				nums[i] = 1;
				continue;
			}
			if(rand>.75) nums[i] = 2;
			if(rand<.75) nums[i] = 0;
		}
		//Counter for Number of Consecutive Identical Numbers
		int inARow = 0;
		//Check Array
		for(int i = 1; i < nums.length; i++){
			//Congratulate Player and End Program Immediately if They Win
			if(inARow == 5){
				System.out.println("You got 5 in a row!");
				System.exit(0);
			}
			//Print This Number
			System.out.println(nums[i]);
			//If Identical to Previous, Increase Number of Consecutive Identical Numbers
			if(nums[i]==nums[i-1]){
				inARow++;
			}else{
				//Reset Counter
				inARow = 0;
			}
		}
		//Womp womp :^(
		System.out.println("Unfortunately you did not get 5 in a row.");
	}

}
