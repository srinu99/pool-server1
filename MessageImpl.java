import java.io.Serializable;

/**
 * Implements the interface Message and Serializable.
 * Takes a string and counts how many digits and characters are in it.
 * The original string and calculated number of digits/characters are stored privately.
 * The number of digits/characters can be accessed through getCharacterCount() and getDigitCount()
 * @author Paul Marshall
 */
public class MessageImpl implements Message, Serializable {

	//fields of MessageImpl
	private static final long serialVersionUID = 5950169519310163575L; //why?
	private String input;
	private int digitCount;
	private int letterCount;
	
    /** 
     * takes a string and calls setCounts() to calculate number of digits/characters
     * @param input - no restrictions required
     */  
	public MessageImpl(String input){
		this.input = input;
		setCounts();
	}
	
    /** 
     * Iterates through provided string and counts the digits and characters
     */  
	public void setCounts() {
		int letcount = 0;
		int digcount = 0;
		
		for (int i = 0; i < input.length(); i++) {
		    if (Character.isLetter(input.charAt(i))) {
		        letcount++; 
		    }else if(Character.isDigit(input.charAt(i))){
		    	digcount++;
		    }else{
		    	//not a letter or digit
		    }
		}
		
		digitCount = digcount;
		letterCount = letcount;
		
	}
	
    /** 
     * returns number of characters in string
     * @return letterCount
     */  
	public int getCharacterCount() {
		return letterCount;
	}

    /** 
     * returns number of digits in string
     * @return digitCount
     */  
	public int getDigitCount() {
		return digitCount;
	}

}
