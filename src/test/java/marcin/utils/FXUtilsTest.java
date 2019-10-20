package marcin.utils;

import org.junit.Assert;
import org.junit.Test;

public class FXUtilsTest {

	@Test
	public void regexStringShouldBeDefined() throws Exception {
		Assert.assertEquals("Expected the regex to be defined", "[A-Za-z0-9\\s\\./]*", FXUtils.REGEX);
	}

	@Test
	public void numberRegexStringShouldBeDefined() throws Exception {
		Assert.assertEquals("Expected the regex to be defined", "[0-9\\.]*", FXUtils.NUMBER_REGEX);
	}

	@Test
	public void isValidStringShouldReturnFalseIfTextIsWhitespaceOnly() throws Exception {
		Assert.assertFalse("Expected the result to be false", FXUtils.isValidString("   "));
	}

	@Test
	public void isValidStringShouldReturnFalseIfTextContainsNoWordOrNonDigitCharacters() throws Exception {
		Assert.assertFalse("Expected the result to be false", FXUtils.isValidString("test!@#"));
	}

	@Test
	public void isValidStringShouldReturnTrueForWordOrDigitCharacter() throws Exception {
		Assert.assertTrue("Expected the result to be false", FXUtils.isValidString("test123"));
	}

	@Test
	public void isValidStringShouldReturnTrueIfTextContainsWordOrDigitCharactersSeparatedByWhitespace()
			throws Exception {
		Assert.assertTrue("Expected the result to be false", FXUtils.isValidString("test 123"));
	}

//addStyle
}
