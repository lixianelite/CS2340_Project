package edu.gatech.oad.antlab.person;

/**
 *  A simple class for person 5
 *  returns their name and a
 *  modified string 
 *  
 *  @author Bob
 *  @version 1.1
 */
public class Person5 {
  /** Holds the persons real name */
  private String name; 
 
  	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */

  public Person5(String pname) {
    	name = pname;
  }
  	/**
	 * This method should take the string
	 * input and return its characters rotated
	 * 2 positions.
	 * given "gtg123b" it should return
	 * "g123bgt".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  //Person 5 put your implementation here
		int len = input.length();
		input = reverse(input, 0, len - 2 - 1);
		input = reverse(input, len - 2, len - 1);
		input = reverse(input, 0, len - 1);
	  	return input;
	}
	public String reverse(String input, int start, int end) {
		char[] arr = input.toCharArray();
		for(int i = start, j = end; i < j; i++, j--) {
			char temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
		return String.valueOf(arr);
	}
	
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the 
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}
}

