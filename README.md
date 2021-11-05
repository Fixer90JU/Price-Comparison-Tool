# Price Comparison Tool
A program that can check current online pricing for a user-input item.

This program uses the JSoup library to scrape HTML from a Google Shopping search based on a user-input item, where the string is then parsed for the items' data (name, price, and store). Before searching, the user selects a sorting method to use — price (lowest to highest), price (highest to lowest), store (A-Z), or store (Z-A). A merge sort is then performed on the retrieved items before being output to the user.
