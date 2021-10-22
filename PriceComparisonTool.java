//Libraries
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

//Price Comparison Tool
public class PriceComparisonTool
{
	//Constants
	static final String INFO_NAME = "Price Comparison Tool";
	static final String INFO_VERSION = "Version 0.1.0";
	static final String FILE_NAME = "Comparison.txt";
	static final String SEARCH_BUTTON_STRING = "Compare";
	static final int SORT_1A = 0;
	static final int SORT_1B = 1;
	static final int SORT_2A = 2;
	static final int SORT_2B = 3;
	static final String SORT_1_STRING = "Sort by Price";
	static final String SORT_1A_STRING = "Lowest to Highest";
	static final String SORT_1B_STRING = "Highest to Lowest";
	static final String SORT_2_STRING = "Sort by Store";
	static final String SORT_2A_STRING = "A-Z";
	static final String SORT_2B_STRING = "Z-A";
	static final int ITEM_COUNT = 15;
	static final String OUTPUT_FORMAT = "%-4s%s\n%-15s%s\n";
	
	//Function for displaying the program's information
	static void displayInformation()
	{
		System.out.printf("%s\n%s\n\n", INFO_NAME, INFO_VERSION);
	}
	
	//Function for getting the search-string from the user
	static String getSearch()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Input item to search for");
		String search = input.nextLine();
		return search;
	}
	
	//Function for getting the sorting type from the user
	static int getSortType()
	{
		Scanner input = new Scanner(System.in);
		System.out.printf(
			"Input sorting type\n1: %s - %s\n2: %s - %s\n3: %s - %s\n4: %s - %s\n",
			SORT_1_STRING, SORT_1A_STRING, SORT_1_STRING, SORT_1B_STRING,
			SORT_2_STRING, SORT_2A_STRING, SORT_2_STRING, SORT_2B_STRING);
		int sortType = input.nextInt() - 1;
		return sortType;
	}
	
	//Function for getting the items from online stores
	static void getItems(Item[] items, String search)
	{
		
	}
	
	//Function for sorting the items based on the sorting type
	static void sortItems(Item[] items, int sortType)
	{
		ItemSorter sorter = new ItemSorter(sortType);
		sorter.sort(items, 0, ITEM_COUNT - 1);
	}
	
	//Funtion for formatting the price to a string (USD)
	static String formatPrice(int price)
	{
		//Adds dot for cents
		String priceString = Integer.toString(price);
		switch(priceString.length())
		{
			case 1:
				priceString = "0.0" + priceString;
				break;
			case 2:
				priceString = "0." + priceString;
				break;
			default:
				priceString = priceString.substring(0,
					priceString.length() - 2) + '.'
					+ priceString.substring(priceString.length() - 2);
				break;
		}
		
		//Adds commas
		int commaCount = 0;
		for(int i = priceString.length() - 3; i > 3; i -= 3)
		{
			int commaPosition = priceString.length() - (3 * commaCount)
				- commaCount - 6;
			priceString = priceString.substring(0, commaPosition) + ','
				+ priceString.substring(commaPosition);
			commaCount++;
		}
		
		//Adds dollar sign
		priceString = '$' + priceString;
		
		//Returns formatted string
		return priceString;
	}
	
	//Function for outputting the items
	static void outputItems(Item[] items) throws IOException
	{
		PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME), true);
		for(int i = 0; i < ITEM_COUNT; i++)
		{
			//Outputs the items to the text file
			writer.printf(OUTPUT_FORMAT + '\n', Integer.toString(i + 1) + '.',
				items[i].name, formatPrice(items[i].price), items[i].store);
			
			//Outputs the items to the console
			System.out.printf('\n' + OUTPUT_FORMAT, Integer.toString(i + 1)
				+ '.', items[i].name, formatPrice(items[i].price),
				items[i].store);
		}
	}
	
	//Main
	public static void main(String[] args) throws IOException
	{
		//Variables
		String search = "";
		int sortType = SORT_1A;
		Item[] items = new Item[ITEM_COUNT];
		
		//Displays the program's information
		displayInformation();
		
		//Gets the search-string from the user
		search = getSearch();
		
		//Gets the sorting type from the user
		sortType = getSortType();
		
		//Gets the items from online stores
		//getItems(items, search);
		
		//TEMPORARY placeholder for getItems
		for(int i = 0; i < ITEM_COUNT; i++)
		{
			items[i] = new Item();
		}
		
		//Sorts the items based on the sorting type
		sortItems(items, sortType);
		
		//Outputs the items
		outputItems(items);
	}
}