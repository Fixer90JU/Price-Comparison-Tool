//Item
public class Item
{
	//Variables
	public String name;
	public int price;
	public String store;
	
	//Constructors
	public Item()
	{
		name = "";
		price = 0;
		store = "";
	}
	public Item(String nameNew, int priceNew, String storeNew)
	{
		name = nameNew;
		price = priceNew;
		store = storeNew;
	}
}