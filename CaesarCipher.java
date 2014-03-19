// ExamAnalysis.java, unit 5.3.7

/** 
 *  Encrypts a string through Caesar cipher
 *	Only encrypts uppercase, alphabetical characters, 
 *	leaving other characters/numbers intact.
 *
 *  @author:  Anna Ntenta, anna.ntenta@gmail.com
 *  @version: Last Modified ___, 2014
 */

import java.util.*;
import java.io.*;

class BadModeException extends Exception {}
class BadShiftException extends Exception {}
//class NoOfQuestionsException extends Exception {}
//class BadAnswerKeyException extends Exception {}

public class ExamAnalysis
{
	public static void main (String [] args) throws FileNotFoundException
	{
		// create a scanner object to read from the keyboard
		Scanner keyboard = new Scanner (System.in); 
		// String containing the name of the file to be encoded or decoded
		String inputFile;
		// String containing the encoded/decoded file
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
				retry  = false; // if given correct input, end the while loop
				
				System.out.print("Enter 1 to encipher, or 2 to decipher (-1 to exit): ");
				// get the mode
				mode = keyboard.nextInt();
				// check that user provided correct input
				if ((mode != 1) && (mode != 2) && (mode != -1)) throw new BadModeException();

				// if user chose to exit
				if (mode == -1) return;

				System.out.print("\nWhat shift should I use? ");
				// get the shift
				shift = keyboard.nextInt();
				// check that user provided correct input
				if (shift < 0) throw new BadShiftException();

				// get the name of the input file
				System.out.print("\nWhat is the name of the input file? ");
				inputFile = keyboard.next();

				// set the name of the output file
				System.out.print("\nWhat is the name of the output file? ");
				// get the name of the student answers file 
				outputFile = keyboard.next();

				if (mode == 1) {
					caesarEncipher (inputFile, shift);
					// write to output file
				}
				else {
					caesarDecipher (inputFile, shift);
					// write to output file
				}
			}
			catch(BadModeException e) {}
			catch(BadShiftException e) {}
			catch(FileNotFoundException e) {}

		}

				// get the number of questions
				int noOfQuestions = answerKey.length();
				// check that user provides less than 100 questions
				if (noOfQuestions > 100) throw new NoOfQuestionsException();
				// check that answer key only contains possible answers
				for (int i = 0; i < noOfQuestions; i++)
				{
					if ("ABCDE".indexOf(answerKey.charAt(i)) < 0) throw new BadAnswerKeyException(); 
				}
					System.out.print("\nWhat is the name of the file containing each student's\nresponses to the " + noOfQuestions + " questions? ");
					// get the name of the student answers file 
					fileName = keyboard.next();

				// print out student answers
				studAnswers(fileName, answerKey);
			}
			catch(NoOfQuestionsException e) {
				System.out.println("\nThe EXAM ANALYZER can only process exams with no more than 100 questions. Please try again.\n");
				retry = true; // loop back
			}
			catch(BadAnswerKeyException e) {
				System.out.println("\nThe answer key can only contain the characters A, B, C, D and E. \nPlease try again (or control-Z to quit.)\n");
				retry = true;
			}
	}
}