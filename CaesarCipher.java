// CaesarCipher.java, unit 5.3.7

/****************************************************************
 * 
 *  Encrypts or decrypts a string through Caesar cipher
 *	Only encrypts uppercase, alphabetical characters, 
 *	leaving other characters/numbers intact.
 *
 *  @author:  Anna Ntenta, anna.ntenta@gmail.com
 *  @version: Last Modified 3/24, 2014
 *
 ****************************************************************/

import java.util.*;
import java.io.*;

class BadShiftException extends Exception {}

public class CaesarCipher
{
	public static void main (String [] args) throws FileNotFoundException
	{
		// create a scanner object to read from the keyboard
		Scanner keyboard = new Scanner (System.in); 
		// String containing the name of the file to be encoded or decoded
		String inputFile;
		// String containing the name of the encoded/decoded file
		String outputFile;
		// integer indicates wether user wants to encode, decode or exit
		int mode;
		// integer representing by how many shifts file is to be encoded/decoded
		int shift;

		System.out.println("\n********************************************\n      WELCOME TO CAESAR CIPHER\n");

		boolean retry = true; 
		// loop keeps asking until user provides correct input
		while (retry) {
			try {
				System.out.print("Enter 1 to encipher, or 2 to decipher (-1 to exit): ");
				// ask for the right input until given
				while (!keyboard.hasNextInt()) {
					keyboard.next();
					System.out.println("\nYou have to type 1, 2 or -1. Please try again.\n");
					System.out.print("Enter 1 to encipher, or 2 to decipher (-1 to exit): ");
				}
				// get the mode
				mode = keyboard.nextInt();
				
				// if user chose to exit
				if (mode == -1) {
					retry = false;
					return;
				}

				System.out.print("\nWhat shift should I use? ");
				while (!keyboard.hasNextInt()) {
					keyboard.next();
					System.out.println("\nSorry. You have to type an integer. Try again.");
					System.out.print("\nWhat shift should I use? ");
				}
				// get the shift
				shift = keyboard.nextInt();
				// check that user provided correct input
				if (shift < 0) throw new BadShiftException();
				// if shift is larger than 26, "wrap around" by modulus to a number <= 26
				if (shift > 26) shift %= 26;
				
				// get the name of the input file
				System.out.print("\nWhat is the name of the input file? ");
				inputFile = keyboard.next();
				File f = new File(inputFile);
				if(!f.exists()) throw new FileNotFoundException();

				// set the name of the output file
				System.out.print("\nWhat is the name of the output file? ");
				// get the name of the student answers file 
				outputFile = keyboard.next();

				// if user chose to encifer
				if (mode == 1) {
					String encodedOutput = caesarEncipher(inputFile, shift);
					// create PrintStream object
					PrintStream outputStream = new PrintStream (new File(outputFile));
					// write encoded message to outputFile
					outputStream.print(encodedOutput);
					System.out.print("\nDone!!!\n\n");
					outputStream.close();
				}
				else {
					String decodedOutput = caesarDecipher(inputFile, shift);
					// create PrintStream object
					PrintStream outputStream = new PrintStream (new File(outputFile));
					// write encoded message to outputFile
					outputStream.print(decodedOutput);
					System.out.print("\nDone!!!\n\n");
					outputStream.close();
				}
			}
			catch(InputMismatchException e) {
				System.out.print("\nSorry. You have to type an integer. Exiting...\n\n");
				keyboard.nextInt();
			}
			catch(BadShiftException e) {
				System.out.print("\nSorry. Shift has to be larger than zero. Please start over.\n\n");
			}
			catch(FileNotFoundException e) {
				System.out.println("\nSorry. That file can not be read or written to. Please start over. \n");
			}
		}
		keyboard.close();
		}

	/**
	 * Takes the name of an input file and an integer representing 
	 * the amount of "shift" by which the contents of the file should
	 * be encrypted and returns an encrypted string.
	 *
	 * @param   String inputFile
	 * @param   int Shift
	 * @throws: FileNotFoundException If input file can't be read
	 */	
	public static String caesarEncipher (String input, int shift) throws FileNotFoundException {
		Scanner inFile = null;
		try {
			// create Scanner object
			inFile = new Scanner(new File(input));
		}
		catch(FileNotFoundException e) {
				System.out.println("\nSomething went wrong. That file can not be read or written to. Exiting... \n");
		}
		// create StringBuilder object to hold enccoded lines of text
		StringBuilder encodedString = new StringBuilder();

		// while file has nextLine
		while (inFile.hasNextLine()) {
				// line to be encoded
			String line = inFile.nextLine();

			// get length of line
			int length = line.length(); 
					
			// for each character in line
			for (int i = 0; i < length; i++) {
				// check if uppercase
				if (Character.isUpperCase(line.charAt(i))) {
							
					// convert ASCII value of char to a value between 0-25 to make wraparound work
					int rot25 = line.charAt(i) - 65;
							
					// calculate the cipher character
					int cipher_char = (rot25 + shift) % 26;

					// converts to ASCII value again and place in StringBuilder
					encodedString.append((char)(cipher_char + 65));
				}
				// if not uppercase just add to StringBuilder object "as is"
				else {
					encodedString.append((char)line.charAt(i));
				}			
			}
			//add a newline
			encodedString.append("\n");
	    }
	    inFile.close();
	    // return string with encoded message
	    return encodedString.toString();	
	}
	/**
	 * Takes the name of an input file and an integer representing 
	 * the amount of "shift" by which the contents of the file should
	 * be decrypted and returns a decrypted string.
	 *
	 * @param   String inputFile
	 * @param   int Shift
	 * @throws: FileNotFoundException If input file can't be read
	 */	
	public static String caesarDecipher (String input, int shift) throws FileNotFoundException {
		// create Scanner object
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(input));
		}
		catch(FileNotFoundException e) {
				System.out.println("\nSomething went wrong. That file can not be read or written to. Exiting... \n");
		}
		// create StringBuilder object to hold deccoded lines of text
		StringBuilder decodedString = new StringBuilder();

		// while file has nextLine
		while (inFile.hasNextLine()) {
			// line to be encoded
			String line = inFile.nextLine();

			// get length of line
			int length = line.length(); 
					
			// for each character in line
			for (int i = 0; i < length; i++) {
				// check if uppercase
				if (Character.isUpperCase(line.charAt(i))) {
							
					// convert ASCII value of char to a value between 0-25 to make wraparound work
					int rot25 = line.charAt(i) - 65;
							
					// calculate the plaintext character
					int decipher_char = (rot25 - shift) % 26;

					// converts to ASCII value again and place in StringBuilder
					decodedString.append((char)(decipher_char + 65));
				}
				// if not uppercase just add to StringBuilder object "as is"
				else {
					decodedString.append((char)line.charAt(i));
				}			
			}
			//add a newline
			decodedString.append("\n");
	    }
	    inFile.close();
	    // return string with decoded message
	    return decodedString.toString();		
	}
}