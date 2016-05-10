import static org.junit.Assert.*;

import org.junit.Test;


public class MessageImplTest {

	@Test
	public void testNonCharacters() {
		MessageImpl message1 = new MessageImpl("---///Hello");
		
		assertEquals(message1.getCharacterCount(), 5);
		assertEquals(message1.getDigitCount(), 0);
	}

	@Test
	public void testCharacters() {
		MessageImpl message1 = new MessageImpl("imalongstring");
		
		assertEquals(message1.getCharacterCount(), 13);
	}

	@Test
	public void testDigits() {
		MessageImpl message1 = new MessageImpl("12345");
		
		assertEquals(message1.getDigitCount(), 5);
	}

	@Test
	public void testMixed() {
		MessageImpl message1 = new MessageImpl("-5He3Tto5");
		
		assertEquals(message1.getCharacterCount(), 5);
		assertEquals(message1.getDigitCount(), 3);
	}

}
