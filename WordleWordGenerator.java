/*
 * Simple text parser that searches through the dictionary for words that match the user provided hints
 * Program will output all the words in the dictionary that match the given hints to be used as guesses for Wordle puzzle
 * Hints allowed are 'cl' (contains letter), 'll' (letter at a particular location in the word (1-indexed)), 'dcl' (words that do not contain letter), 'd' (done entering hints)
 */

package wordle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WordleWordGenerator 
{
	private static final int WORDLE_WORD_LENGTH = 5;
	
	private static class Rule
	{
		public String ruleName;
		public char target;
		public int location;
		
		public Rule(String rule, char trgt)
		{
			this.ruleName = rule;
			this.target = trgt;
		}
		
		public Rule(String rule, char trgt, int lctn)
		{
			this.ruleName = rule;
			this.target = trgt;
			this.location = lctn;
		}
	}
	
	private static boolean containsLetter(String word, char target)
	{
		for(int i = 0; i < word.length(); i++)
		{
			if(word.charAt(i) == target)
			{
				return true;
			}
		}
		return false;
	}
	
	private static boolean doesNotContainLetter(String word, char target)
	{
		return !containsLetter(word, target);
	}
	
	private static boolean charAtLocation(String word, char target, int location)
	{	
		if(word.length() > location && word.charAt(location) == target)
		{
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		System.out.println("Usage: Enter 'cl' for Contains Letter, 'dcl' for Doesn't Contain Letter, 'll' for letter at location, and 'd' if you've entered all the hints");
		
		Scanner keyboard = new Scanner(System.in);
		String rule;
		String word;
		char target;
		int location;
		ArrayList<Rule> rules = new ArrayList<Rule>();
		do
		{
			System.out.println("Enter a Hint: ");
			rule = keyboard.nextLine();
			
			switch(rule)
			{
			case "cl":
				System.out.println("Enter the Letter: ");
				target = keyboard.nextLine().charAt(0);
				Rule cl = new Rule(rule, target);
				rules.add(cl);
				break;
			case "dcl":
				System.out.println("Enter the Letter: ");
				target = keyboard.nextLine().charAt(0);
				Rule dcl = new Rule(rule, target);
				rules.add(dcl);
				break;
			case "ll":
				System.out.println("Enter the Letter: ");
				target = keyboard.nextLine().charAt(0);
				System.out.println("Enter the Location of the Letter: ");
				location = keyboard.nextInt();
				keyboard.nextLine();
				location--;
				Rule ll = new Rule(rule, target, location);
				rules.add(ll);
				break;
			case "d":
				break;
			default:
				System.out.println("Invalid Hint, Try again...");	
				break;
			}
		}while(!rule.equals("d"));
		keyboard.close();
		
		File file = new File("C:\\Users\\riley\\Documents\\Coding\\sandbox\\wordle\\src\\wordle\\dictionary.txt");
		Scanner fileReader = new Scanner(file);
		
		while(fileReader.hasNextLine())
		{
			boolean satisfiesRules = true;
			word = fileReader.nextLine();
			for(int i = 0; i < rules.size(); i++)
			{
				switch(rules.get(i).ruleName)
				{
				case "cl":
					if(!containsLetter(word, rules.get(i).target))
					{
						i = rules.size();
						satisfiesRules = false;
					}
					break;
				case "dcl":
					if(!doesNotContainLetter(word, rules.get(i).target))
					{
						i = rules.size();
						satisfiesRules = false;
					}
					break;
				case "ll":
					if(!charAtLocation(word, rules.get(i).target, rules.get(i).location))
					{
						i = rules.size();
						satisfiesRules = false;
					}
					break;
				}
				
			}
			if(word.length() != WORDLE_WORD_LENGTH)
			{
				satisfiesRules = false;
			}
			if(satisfiesRules)
			{
				System.out.println(word);
			}
		}
		
	fileReader.close();
	}

}
