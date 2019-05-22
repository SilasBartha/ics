//Silas Bartha, May 22, 2019, Array Practice "Guess Color" Program
package arrays;

import java.util.Scanner;

public class GuessColor {
	public static void main(String[] args){
		//Set up Scanner
		Scanner sc = new Scanner(System.in);
		//Colors
		String[] colors = {
				"Vermillion",
				"Goldenrod",
				"Chartreuse",
				"Aquamarine",
				"Violet",
				"Cerulean",
				"Aubergine",
				"Crimson",
				"Viridian",
				"Amber"
			};
		//Choose Color and get Hints
		int rand = (int)(Math.random()*10);
		String answer = colors[rand];
		char startLetter = answer.charAt(0);
		int ansLength = answer.length();
		
		//Challenge Player
		System.out.println("You have 5 tries to guess which color I'm thinking of! "
				         + "\nIt starts with the letter \"" + startLetter + "\", and is " + ansLength + " letters long.");
		//Set Guess Counter
		int guesses = 0;
		//Player Input
		while(true){
			//Get Guess
			String guess = sc.nextLine(); 
			//If Guess is Correct Congratulate Player
			if(guess.equalsIgnoreCase(answer)){
				System.out.println("You got it!");
				break;
			}else{
				//Increase Try Count
				guesses++;
				//If Player has More Guesses Left
				if(guesses < 5){
					System.out.println("Sorry, that's incorrect. Try again!");
				}else{
					//Womp womp :^(
					System.out.println("It's okay, that was a hard one. The answer was: " + answer + ".");
					break;
				}
			}
		}
		//Close Scanner
		sc.close();
	}
}
