// ExamAnalysis.java, unit 5.3.6

/** 
 *  This program processes answers to multiple choice questions in an exam
 *
 *  @author:  Anna Ntenta, anna.ntenta@gmail.com
 *  @version: Last Modified ___, 2014
 */

import java.util.*;
import java.io.*;

class BadDataException extends Exception {}
class NoOfQuestionsException extends Exception {}
class BadAnswerKeyException extends Exception {}

public class ExamAnalysis
{
	public static void main (String [] args) throws FileNotFoundException
	{
		// create a scanner object to read from the keyboard
		Scanner keyboard = new Scanner (System.in); 
		// Strings containing the correct answers and name of file with student answers
		String answerKey, fileName;

		System.out.println("\n********************************************\n                EXAM ANALYZER\n");

		boolean retry = true; 
		// loop keeps asking until user provides correct input
		while (retry) 
			try {
				retry  = false; // if given correct input, end the while loop
				System.out.print("Please type the correct answers to the exam questions,\none right after the other: ");
				
				// get the correct answers to the questions and convert to uppercase
				answerKey = keyboard.next().toUpperCase();
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

		public static void studAnswers(String fileName, String answerKey ) throws FileNotFoundException
		{
			try {
				// construct a Scanner object to use for the printing of student answers
				Scanner initialInput = new Scanner(new File(fileName)); // remember to handle exception
				
				// counter for the printing of student answers
				int noOfStudents = 1;
				// print each line in input file
				while (initialInput.hasNextLine()) 
				{
					System.out.println("\nStudent #" + noOfStudents + "'s responses: " + initialInput.nextLine());
					noOfStudents++;
				}
				if ((noOfStudents > 100) || (noOfStudents < 2)) {
					throw new BadDataException();
				}		
				else System.out.println("\nThank you for the data on the " + (noOfStudents - 1) + " students. Here's the analysis:\n");
				
				// count number of correct/incorrect/blank answers per student and print
				studAnalysis(fileName, answerKey);
			}
			catch(FileNotFoundException e) {
				System.out.println("\nTrying to print analysis...\nCould not find file \"" + fileName + "\". Please try again.\n");
			}
			catch(BadDataException e) {
				System.out.println("\nThe EXAM ANALYZER can only analyze between 1 - 100 lines of data.\nPlease check your input file and try again.\n"); 
			}
		}
	
		public static void studAnalysis(String fileName, String answerKey ) throws FileNotFoundException
		{
			try {
				// construct a new Scanner object for the analysis of each student's result
				Scanner studentAnalysis = new Scanner(new File(fileName));

				// reset counter for the printing of student analysis
				int analysisCounter = 1;

				System.out.println("Student #         Correct         Incorrect         Blank");
				System.out.println("~~~~~~~~~         ~~~~~~~         ~~~~~~~~~         ~~~~~");

				
				// print one line of analysis per student
				while (studentAnalysis.hasNextLine()) 
				{
					// counters for student's results
					int correct = 0;
					int inCorrect = 0;
					int blank = 0;

					// number of questions
					int noOfQuestions = answerKey.length();

					// get string of answers for a student
					String answers = studentAnalysis.nextLine();

					// number of questions answered by current student
					int questionsAnswered = answers.length();

					// for each answer, check if correct, incorrect or blank
					for (int i = 0; i < noOfQuestions; i++)
					{
						// stop analysis if answer string ends with blanks
						if ( questionsAnswered > i) {
							// if answer is correct 
							if(answerKey.charAt(i) == answers.charAt(i)){
								correct += 1;
							}
							// if answer is blank
							else if ("ABCDE".indexOf(answers.charAt(i)) < 0)
								blank += 1;
							// if answer is incorrect
							else inCorrect +=1;
						}
					}
					// in case there are blank answers at the end of the string, add the 
					// difference between the length of the answer key and the answer string 
					// to the 'blank' counter, but not if the student has answered more questions
					// than there are .
					
					else blank += noOfQuestions - questionsAnswered;
					// print student's results
					System.out.printf("    %d                %d                %d               %d\n\n", analysisCounter, correct, inCorrect, blank);
					// 
					analysisCounter++;	
			}
			// breakdown and print the number and percentages of A,B,C,D,E and Blank answers per question
			questionAnalysis(fileName, answerKey);
		
			}
			catch (FileNotFoundException e) {
				System.out.println("\nTrying to print student analysis...\nCould not find file \"" + fileName + "\". Please try again.\n");
			}
		}
		public static void questionAnalysis(String fileName, String answerKey ) throws FileNotFoundException
		{
			try {
				// construct a Scanner object for the analysis of each question's result
				Scanner questionAnalysis = new Scanner(new File(fileName));

				final String[] HEADERS = { 	"  A*    B     C     D     E     Blank\n", 
											"  A     B*    C     D     E     Blank\n", 
		                                    "  A     B    C*     D     E     Blank\n", 
		                                    "  A     B    C     D*     E     Blank\n", 
		                                    "  A     B    C     D     E*     Blank\n"};

				System.out.println("\nQUESTION ANALYSIS (* marks the correct response)");
				System.out.println("~~~~~~~~~~~~~~~~~\n");

				// create ArrayList of Strings to store all student answers in
				ArrayList<String> answersArray = new ArrayList<String>();
				
				// populate answersArray
				int arrayCounter = 0;
				while (questionAnalysis.hasNextLine()) {
					// place strings in answersArray
					answersArray.add(arrayCounter, questionAnalysis.nextLine());
					arrayCounter++;
				}

				// number of students in the file
				int size = answersArray.size();
				// number of questions
				int len = answerKey.length();
				
				for (int i = 0; i < len; i++) {
					// array to store each question's analytics
					int [] data = { 0, 0, 0, 0, 0, 0 };
					// get the right answer for question i
					char correctAnswer = answerKey.charAt(i);

					System.out.println("Question #" + (i + 1) + ":\n");
					System.out.println(HEADERS[correctAnswer - 65]);
					// analyze all student answers for question i
					for (int j = 0; j < size; j++) {
						if (answersArray.get(j).length() > i) {
							int indexOfAnswer = "ABCDE".indexOf(answersArray.get(j).charAt(i));
							if( indexOfAnswer >= 0 ) 
					    	data[indexOfAnswer]++;
						}
					}

					data[5] = size - (data[0] + data[1] + data[2] + data[3] + data[4]);

					System.out.printf("  %d     %d     %d     %d     %d       %d\n\n",
					   data[0], data[1], data[2], data[3], data[4], data[5]);

					System.out.printf(" %4.1f%% %4.1f%% %4.1f%% %4.1f%% %4.1f%% %5.1f%%\n\n",
					   ((double)data[0]/(size))*100 , ((double)data[1]/(size))*100, 
						((double)data[2]/(size))*100, ((double)data[3]/(size))*100, 
					((double)data[4]/(size))*100, ((double)data[5]/(size))*100);
				}
			}
			catch (FileNotFoundException e) {
				System.out.println("\nTrying to print question analysis...\nCould not find file \"" + fileName + "\". Please try again.\n");
			}
		}

	}
