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

				for (int i = 0; i < array.size(); i++)
					array.get(i).replaceAll("_", " ");

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
		assertArrayEquals(first, StringSearches.boyerMoore(fox[1], fox[0]));
		assertArrayEquals(second, StringSearches.boyerMoore(fox[2], fox[0]));
		assertArrayEquals(third, StringSearches.boyerMoore(fox[3], fox[0]));
		assertArrayEquals(fourth, StringSearches.boyerMoore(fox[4], fox[0]));
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
		assertArrayEquals(first, StringSearches.rabinKarp(fox[1], fox[0]));
		assertArrayEquals(second, StringSearches.rabinKarp(fox[2], fox[0]));
		assertArrayEquals(third, StringSearches.rabinKarp(fox[3], fox[0]));
		assertArrayEquals(fourth, StringSearches.rabinKarp(fox[4], fox[0]));
	}
}
