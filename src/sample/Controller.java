package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Controller {

    static final String INFO_NAME = "Price Comparison Tool ";
    static final String INFO_VERSION = "Version 0.1.1";
    static final String FILE_NAME = "HTML.txt";
    static final int ITEM_COUNT = 15;
    static final String SEARCH_BUTTON_STRING = "Compare";
    static final int SORT_1A = 0; //price low to high
    static final int SORT_1B = 1; //price high to low
    static final int SORT_2A = 2; //store a-z
    static final int SORT_2B = 3; //store z-a
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
    static int sortType = -1;
    @FXML
    private TextArea display;

    @FXML
    private TextField userInput;

    @FXML
    private RadioButton cheapestButton;

    @FXML
    private RadioButton expensiveButton;

    @FXML
    private RadioButton alphabeticalButton;

    @FXML
    private RadioButton antialphabeticalButton;

    @FXML
    private RadioButton resetButton;


    public void pressCompareButton(ActionEvent event) {
        String search = userInput.getText();
        display.setText(INFO_NAME + INFO_VERSION + "\n");

            //Variables
            Item[] items = new Item[ITEM_COUNT];
            for(int i = 0; i < ITEM_COUNT; i++)
            {
                items[i] = new Item();
            }
            long time = 0;

            //Displays the program's information
            //displayInformation();

            //Gets the search-string from the user
            //search = getSearch();

            //Gets the sorting type from the user
            //sortType = getSortType();

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
    void outputItems(Item[] items)
    {
        for(int i = 0; i < ITEM_COUNT; i++)
        {
            //Outputs the items to the console
            display.setText(display.getText() + "\n" + Integer.toString(i + 1) +
                    ". " + items[i].name + "\n" + items[i].price + "       " + items[i].store);
        }
    }

    //Function for outputting the elapsed time (in seconds)
    void outputTime(long time)
    {
        display.setText(display.getText() + "\n\nOperation completed in " + time / 1000.0 + " seconds\n");
    }

    public void sortByCheapestButton(ActionEvent event) {
        expensiveButton.setDisable(true);
        alphabeticalButton.setDisable(true);
        antialphabeticalButton.setDisable(true);
        sortType = 0;
    }

    public void sortByExpensiveButton(ActionEvent event) {
        cheapestButton.setDisable(true);
        alphabeticalButton.setDisable(true);
        antialphabeticalButton.setDisable(true);
        resetButton.setDisable(false);
        sortType = 1;
    }

    public void sortByAlphabeticalButton(ActionEvent event) {
        cheapestButton.setDisable(true);
        expensiveButton.setDisable(true);
        antialphabeticalButton.setDisable(true);
        resetButton.setDisable(false);
        sortType = 2;
    }

    public void sortByAntiAlphabeticalButton(ActionEvent event) {
        cheapestButton.setDisable(true);
        expensiveButton.setDisable(true);
        alphabeticalButton.setDisable(true);
        resetButton.setDisable(false);
        sortType = 3;
    }

    public void actionToResetButton(ActionEvent event) {
        expensiveButton.setDisable(false);
        alphabeticalButton.setDisable(false);
        antialphabeticalButton.setDisable(false);
        cheapestButton.setDisable(false);
    }

    public void quittingProgramButton(ActionEvent event) {
    System.exit(0);
    }
}
