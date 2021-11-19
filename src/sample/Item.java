package sample;

public class Item {
    //Variables
    public String name;
    public String price;
    public String store;

    //Constructors
    public Item()
    {
        name = "";
        price = "";
        store = "";
    }
    public Item(String nameNew, String priceNew, String storeNew)
    {
        name = nameNew;
        price = priceNew;
        store = storeNew;
    }
}
