// CaesarCipher.java, unit 5.3.7

/****************************************************************
 * 
 *  Encrypts a string through Caesar cipher
 *	Only encrypts uppercase, alphabetical characters, 
 *	leaving other characters/numbers intact.
 *
 *  @author:  Anna Ntenta, anna.ntenta@gmail.com
 *  @version: Last Modified ___, 2014
 *
 ****************************************************************/

import java.util.*;
import java.io.*;

class BadModeException extends Exception {}
class BadShiftException extends Exception {}
//class NoOfQuestionsException extends Exception {}
//class BadAnswerKeyException extends Exception {}

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
		while (retry) 
			try {
				
				System.out.print("Enter 1 to encipher, or 2 to decipher (-1 to exit): ");
				// get the mode
				mode = keyboard.nextInt();
				// check that user provided correct input
				if ((mode != 1) && (mode != 2) && (mode != -1)) throw new BadModeException();

				// if user chose to exit
				if (mode == -1) retry = false;

				System.out.print("\nWhat shift should I use? ");
				// get the shift
				shift = keyboard.nextInt();
				// check that user provided correct input
				if (shift < 0) throw new BadShiftException();
				// if shift is larger than 26, "wrap around" by modulus to a number <= 26
				if (shift > 26) shift %= 26;

				// get the name of the input file
				System.out.print("\nWhat is the name of the input file? ");
				inputFile = keyboard.next();

				// set the name of the output file
				System.out.print("\nWhat is the name of the output file? ");
				// get the name of the student answers file 
				outputFile = keyboard.next();

				// if user chose to encifer
				if (mode == 1) {
					encodedOutput = caesarEncipher (inputFile, shift);
					// create PrintWriter object
					PrintWriter outputWriter = new PrintWriter (outputFile);
					// write encoded lines to outputFile

				}
				else {
					//caesarDecipher (inputFile, shift);
					// write to output file
				}
			}
			catch(BadModeException e) {}
			catch(BadShiftException e) {}
			catch(FileNotFoundException e) {}

		}

		public static String caesarEncipher (String input, int shift) {




		}

}