import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jared Moore
 * @version Apr 2, 2013
 */
public class StringSearchesTest {

	private static String[] fox, dog, letters, firstAardvark, secondAardvark;

	@BeforeClass
	public static void buildStrings() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"res/Strings.txt"));
			reader.readLine(); // the first line is comments for anyone trying
								// to edit the file
			String currentLine = reader.readLine();
			int lineNumber = 1;
			while (currentLine != null) {

				Scanner input = new Scanner(currentLine);
				input.useDelimiter("#");
				ArrayList<String> array = new ArrayList<String>();
				array.add(input.next());
				input.useDelimiter(" ");
				input.next(); // skip over '#' delimiter
				while (input.hasNext())
					array.add(input.next());
				input.close();

				for (int i = 0; i < array.size(); i++) // all underscores will be treated as spaces
					array.set(i, array.get(i).replace('_', ' '));

				switch (lineNumber) {
				case 5:
					secondAardvark = buildArray(array);
					break;
				case 4:
					firstAardvark = buildArray(array);
					break;
				case 3:
					letters = buildArray(array);
					break;
				case 2:
					dog = buildArray(array);
					break;
				case 1:
					fox = buildArray(array);
					break;
				default:
					System.err
							.println("You have an issue.  Fix the line numbers.  You tried to use "
									+ lineNumber);
					System.exit(lineNumber);
				}

				lineNumber++;
				currentLine = reader.readLine();
			}

			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("Error with file");
		}
	}

	/**
	 * @param array
	 * @return
	 */
	private static String[] buildArray(ArrayList<String> array) {

		String string[] = new String[array.size()];
		for (int i = 0; i < array.size(); i++)
			string[i] = array.get(i);
		return string;
	}

	@Test
	public void testFoxBoyerMoore() {

		int first[] = { 10 }, second[] = new int[0], third[] = { 33 }, fourth[] = new int[0];
		int firstSearch[] = StringSearches.boyerMoore(fox[1], fox[0]),
				secondSearch[] = StringSearches.boyerMoore(fox[2], fox[0]),
				thirdSearch[] = StringSearches.boyerMoore(fox[3], fox[0]), 
				fourthSearch[] = StringSearches.boyerMoore(fox[4], fox[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
		assertArrayEquals(third, thirdSearch);
		assertArrayEquals(fourth, fourthSearch);
	}

	@Test
	public void testFoxKMP() {

		int first[] = { 10 }, second[] = new int[0], third[] = { 33 }, fourth[] = new int[0];
		int firstSearch[] = StringSearches.kmp(fox[1], fox[0]),
				secondSearch[] = StringSearches.kmp(fox[2], fox[0]),
				thirdSearch[] = StringSearches.kmp(fox[3], fox[0]), 
				fourthSearch[] = StringSearches.kmp(fox[4], fox[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
		assertArrayEquals(third, thirdSearch);
		assertArrayEquals(fourth, fourthSearch);
	}

	@Test
	public void testFoxRabinKarp() {

		int first[] = { 10 }, second[] = new int[0], third[] = { 33 }, fourth[] = new int[0];
		int firstSearch[] = StringSearches.rabinKarp(fox[1], fox[0]),
				secondSearch[] = StringSearches.rabinKarp(fox[2], fox[0]),
				thirdSearch[] = StringSearches.rabinKarp(fox[3], fox[0]), 
				fourthSearch[] = StringSearches.rabinKarp(fox[4], fox[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
		assertArrayEquals(third, thirdSearch);
		assertArrayEquals(fourth, fourthSearch);
	}
	
	@Test
	public void testDogBoyerMoore() {
		int first[] = { 4, 8, 12, 16, 20, 24, 28 }, second[] = { 0 };
		int firstSearch[] = StringSearches.boyerMoore(dog[1], dog[0]),
				secondSearch[] = StringSearches.boyerMoore(dog[2], dog[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
	
	@Test
	public void testDogKMP() {
		int first[] = { 4, 8, 12, 16, 20, 24, 28 }, second[] = { 0 };
		int firstSearch[] = StringSearches.kmp(dog[1], dog[0]),
				secondSearch[] = StringSearches.kmp(dog[2], dog[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
	
	@Test
	public void testDogRabinKarp() {
		int first[] = { 4, 8, 12, 16, 20, 24, 28 }, second[] = { 0 };
		int firstSearch[] = StringSearches.rabinKarp(dog[1], dog[0]),
				secondSearch[] = StringSearches.rabinKarp(dog[2], dog[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
	
	@Test
	public void testLettersBoyerMoore() {
		int first[] = { 14 };
		int firstSearch[] = StringSearches.boyerMoore(letters[1], letters[0]);
		assertArrayEquals(first, firstSearch);
	}
	
	@Test
	public void testLettersKMP() {
		int first[] = { 14 };
		int firstSearch[] = StringSearches.kmp(letters[1], letters[0]);
		assertArrayEquals(first, firstSearch);
	}
	
	@Test
	public void testLettersRabinKarp() {
		int first[] = { 14 };
		int firstSearch[] = StringSearches.rabinKarp(letters[1], letters[0]);
		assertArrayEquals(first, firstSearch);
	}
	
	@Test
	public void testAardvarksBoyerMoore() {
		int first[] = { 14 }, second[] = { 17 };
		int firstSearch[] = StringSearches.boyerMoore(firstAardvark[1], firstAardvark[0]);
		int secondSearch[] = StringSearches.boyerMoore(secondAardvark[1], secondAardvark[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
	
	@Test
	public void testAardvarksKMP() {
		int first[] = { 14 }, second[] = { 17 };
		int firstSearch[] = StringSearches.kmp(firstAardvark[1], firstAardvark[0]);
		int secondSearch[] = StringSearches.kmp(secondAardvark[1], secondAardvark[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
	
	@Test
	public void testAardvarksRabinKarp() {
		int first[] = { 14 }, second[] = { 17 };
		int firstSearch[] = StringSearches.rabinKarp(firstAardvark[1], firstAardvark[0]);
		int secondSearch[] = StringSearches.rabinKarp(secondAardvark[1], secondAardvark[0]);
		assertArrayEquals(first, firstSearch);
		assertArrayEquals(second, secondSearch);
	}
}
