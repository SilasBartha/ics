//Silas Bartha, May 22, 2019, Array Practice "Colors and Clothes" Program

package arrays;

public class Clothes {
	
	public static void main(String[] args) {
		String[] colors = {
			"Red ",
			"Vermillion ",
			"Orange ",
			"Goldenrod ",
			"Yellow ",
			"Chartreuse ",
			"Green ",
			"Aquamarine ",
			"Blue ",
			"Violet ",
			"Purple "
		};
		
		String[] clothes = {
			"Hat, ",
			"Glasses, ",
			"Mask, ",
			"Scarf, ",
			"Shirt, ",
			"Jacket, ",
			"Gloves, ",
			"Belt, ",
			"Pants, ",
			"Shoes"
		};
		
		for(String col: colors) {
			for(String item: clothes) {
				System.out.print(col + item);
			}
			System.out.println();
		}
	}
	
}
