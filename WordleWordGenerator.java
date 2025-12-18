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
		String continueGenerating;
		Scanner keyboard = new Scanner(System.in);
		File file = new File("./dictionary.txt");
		Scanner fileReader;

		do
		{
		fileReader = new Scanner(file);
		System.out.println("Usage: Enter 'cl' for Contains Letter, 'dcl' for Doesn't Contain Letter, 'll' for letter at location, and 'd' if you've entered all the hints");
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
				System.out.println("Enter the Location of the Letter: (1 for the first letter, 2 for the second letter, etc.)");
				location = keyboard.nextInt()- 1;
				keyboard.nextLine();
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
		System.out.println("Would you like to generate another set of guesses? (y/n)");
		continueGenerating = keyboard.nextLine();
	}while(continueGenerating.equals("y"));
	
	keyboard.close();
	fileReader.close();
	}
}