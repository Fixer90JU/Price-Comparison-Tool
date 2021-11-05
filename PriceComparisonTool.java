//Libraries
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//Price Comparison Tool
public class PriceComparisonTool
{
	//Constants
	static final String INFO_NAME = "Price Comparison Tool";
	static final String INFO_VERSION = "Version 0.1.0";
	static final String FILE_NAME = "Comparison.txt";
	static final int ITEM_COUNT = 15;
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
	static final String URL_PREFIX = "https://www.google.com/search?q=";
	static final String URL_SUFFIX = "&tbm=shop";
	static final String HTML_PREFIX = "data-sh-gr=\"line\">";
	static final String HTML_NAME_PREFIX_PREFIX = "<h4 class=\"";
	static final String HTML_NAME_PREFIX_SUFFIX = "\">";
	static final String HTML_NAME_SUFFIX = "</h4>";
	static final String HTML_PRICE_PREFIX = "\">$";
	static final String HTML_PRICE_SUFFIX = "</span></span>";
	static final String HTML_STORE_PREFIX = "Visit site of ";
	static final String HTML_STORE_SUFFIX = " in a new window";
	
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
		//Formats the URL
		search = search.replaceAll("[^a-zA-Z0-9\\s]", "");
		search = search.replace(' ', '+');
		search = URL_PREFIX + search + URL_SUFFIX;
		
		//Gets the HTML page as a string from the URL
		try
		{
			final Document DOCUMENT = Jsoup.connect(search).get();
			search = DOCUMENT.outerHtml();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Gets the item name prefix from the HTML string
		int pos = search.indexOf(HTML_PREFIX) + HTML_PREFIX.length();
		pos = search.indexOf(HTML_NAME_PREFIX_PREFIX, pos);
		final String HTML_NAME_PREFIX = search.substring(pos,
			search.indexOf(HTML_NAME_PREFIX_SUFFIX, pos))
			+ HTML_NAME_PREFIX_SUFFIX;
		pos += HTML_NAME_PREFIX.length();
		
		//Extracts the data of each item from the HTML string
		for(int i = 0; i < ITEM_COUNT; i++)
		{
			//Gets an item's name
			items[i].name = search.substring(pos,
				search.indexOf(HTML_NAME_SUFFIX, pos));
			
			//Gets an item's price
			pos = search.indexOf(HTML_PRICE_PREFIX, pos)
				+ HTML_PRICE_PREFIX.length();
			items[i].price = '$' + search.substring(pos,
				search.indexOf(HTML_PRICE_SUFFIX, pos));
			
			//Gets an item's store
			pos = search.indexOf(HTML_STORE_PREFIX, pos)
				+ HTML_STORE_PREFIX.length();
			items[i].store = search.substring(pos,
				search.indexOf(HTML_STORE_SUFFIX, pos));
			
			//Sets the position for the next item
			pos = search.indexOf(HTML_NAME_PREFIX, pos)
				+ HTML_NAME_PREFIX.length();
		}
	}
	
	//Function for sorting the items based on the sorting type
	static void sortItems(Item[] items, int sortType)
	{
		ItemSorter sorter = new ItemSorter(sortType);
		sorter.sort(items, 0, ITEM_COUNT - 1);
	}
	
	//Function for outputting the items
	static void outputItems(Item[] items) throws IOException
	{
		PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME), true);
		for(int i = 0; i < ITEM_COUNT; i++)
		{
			//Outputs the items to the text file
			writer.printf("%-4s%s\n%-15s%s\n\n", Integer.toString(i + 1) + '.',
				items[i].name, items[i].price, items[i].store);
			
			//Outputs the items to the console
			System.out.printf("\n%-4s%s\n%-15s%s\n", Integer.toString(i + 1) +
				'.', items[i].name, items[i].price, items[i].store);
		}
	}
	
	//Function for outputting the elapsed time (in seconds)
	static void outputTime(long time)
	{
		System.out.printf("\nOperation completed in %.3f seconds\n",
			time / 1000.0);
	}
	
	//Main
	public static void main(String[] args) throws IOException
	{
		//Variables
		String search = "";
		int sortType = SORT_1A;
		Item[] items = new Item[ITEM_COUNT];
		for(int i = 0; i < ITEM_COUNT; i++)
		{
			items[i] = new Item();
		}
		long time = 0;
		
		//Displays the program's information
		displayInformation();
		
		//Gets the search-string from the user
		search = getSearch();
		
		//Gets the sorting type from the user
		sortType = getSortType();
		
		//Starts the stopwatch
		time = System.currentTimeMillis();
		
		//Gets the items from online stores
		getItems(items, search);
		
		//Sorts the items based on the sorting type
		sortItems(items, sortType);
		
		//Outputs the items
		outputItems(items);
		
		//Stops the stopwatch
		time = System.currentTimeMillis() - time;
		
		//Outputs the elapsed time
		outputTime(time);
	}
}