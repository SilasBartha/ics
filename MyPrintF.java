//Silas Bartha, May 8, 2019, printf practice

package printfpractice;

public class MyPrintF {

	public static void main(String[] args) {
		String name = "Bessy";
		String colour = "brown";
		int weight = 1200;
		int xx = +123;
		
		//Print: Pi to 5 decimals
		System.out.printf("Pi: %.5f\n", Math.PI);
		
		//Print: "The cow's name is Bessy, she is brown and weighs 1200 kg."
		System.out.printf("The cow's name is %s, she is %s and weighs %d kg.", name, colour, weight);
		
		
		//Print: numbers so that they are always 8 characters wide
		System.out.println("| 12345678 |");
		System.out.println("| ======== |");
		System.out.printf("| %8d |%n", xx);
		System.out.printf("| %08d |%n", xx);
		System.out.printf("| %+8d |%n", xx);
		System.out.printf("| %-8d |%n", xx);
		System.out.printf("| %8.1f |%n", (double)xx);
	}

}
