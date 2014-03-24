// NoDuplicates.java, unit 5.3.11

/**
 * 
 *  Reads a text ﬁle of numbers of type int and outputs all the 
 *  numbers to another ﬁle, but without any duplicate numbers.
 *  Precondition: The input file must be sorted.
 *
 *
 *  @author:  Anna Ntenta, anna.ntenta@gmail.com
 *  @version: Last Modified 3/24, 2014
 *  @param:	  arg1 input file
 *  @param:   arg2 output file
 *	@throws:  BadArgumentException If called with less than two command-line arguments
 *  @throws:  FileNotFoundException If in/output files cannot be read/written to
 **/

import java.util.*;
import java.io.*;

class BadArgumentException extends Exception {}

public class NoDuplicates
{
	public static void main (String [] args) throws FileNotFoundException
	{ 
		try {
			// create a scanner object to read from the keyboard
			Scanner keyboard = new Scanner (System.in); 
			if(args.length < 2) throw new BadArgumentException();
			// String containing the name of the file of possibly duplicate numbers
			String inputFile = args[0];
			// String containing the name of the file with no duplicate numbers
			String outputFile = args[1];
			// create ArrayList to hold all integers in input file. 
			ArrayList<Integer> integerList = new ArrayList<Integer>();
			// create StringBuilder object to hold integers without duplicates
			ArrayList<Integer> noDuplicates = new ArrayList<Integer>();
			// String containing the result
			String result;

			try {
				// create Scanner object from inputFile
				Scanner inFile = new Scanner(new File(inputFile));
				// put all the integers from original file into a StringBuilder
				while (inFile.hasNextInt()) {
					int num = inFile.nextInt();
					integerList.add(num);
				}
			}
			catch(FileNotFoundException e) {
			System.out.println("\nSorry. The file \"" + inputFile + "\" cannot be read. Perhaps it's misspelled? Please start over. \n");
			return;
			}

			// add unique integers to StringBuilder noDuplicates 
			// only attempt to add integers if inputFile was not empty
			if (integerList.size() > 0) {

				System.out.println("\nORIGINAL FILE " + inputFile + " contains the values:\n"+ integerList +"\n");

				// add first integer to list
				noDuplicates.add(integerList.get(0));
				// for each subsequent integer in the file
				for (int i = 1; i < integerList.size(); i++)
				{
					// if the int does not equal the preceeding int, add to noDuplicates
					if(!integerList.get(i).equals(integerList.get(i - 1))) {
						noDuplicates.add(integerList.get(i));
					}
				}
			}
			else {
				System.out.println("\nSorry. Input file must contain integers - and only integers. Please try a different file.\n");
				return;
			}

			System.out.println("\nOUTPUT FILE " + outputFile + " contains the values:\n"+ noDuplicates +"\n");

			// create printStream object for outputFile
			PrintStream output = new PrintStream (new File(outputFile));
			// write string of numbers without duplicates to outputFile
			output.print(noDuplicates);
		}
		catch(BadArgumentException e) {
			System.out.println("\nSorry, an error occurred.\nUsage: java NoDuplicates inputFile outputFile\n");
		}
		catch(FileNotFoundException e) {
			System.out.println("\nSorry. The file cannot be read/written to. Perhaps it's misspelled? Please start over. \n");
		}
	}
}	
		