//Item Sorter
public class ItemSorter
{
	//Sorting type
	private int sortType;
	
	//Constructors
	public ItemSorter()
	{
		sortType = 0;
	}
	public ItemSorter(int sortTypeNew)
	{
		sortType = sortTypeNew;
	}
	
	//Function for merging two arrays
	private void merge(Item[] items, int l, int m, int r)
	{
		//Variables
		int sizeL = m - l + 1;
		int sizeR = r - m;
		Item[] arrayL = new Item[sizeL];
		Item[] arrayR = new Item[sizeR];
		
		//Copies the data to the temporary arrays
		for(int i = 0; i < sizeL; i++)
		{
			arrayL[i] = items[l + i];
		}
		for(int i = 0; i < sizeR; i++)
		{
			arrayR[i] = items[m + i + 1];
		}
		
		//Merges the temporary arrays
		int i = 0;
		int j = 0;
		int k = l;
		while(i < sizeL && j < sizeR)
		{
			if((sortType == 0 && arrayL[i].price <= arrayR[j].price)
				|| (sortType == 1 && arrayL[i].price >= arrayR[j].price)
				|| (sortType == 2
				&& arrayL[i].store.compareToIgnoreCase(arrayR[j].store) <= 0)
				|| (sortType == 3
				&& arrayL[i].store.compareToIgnoreCase(arrayR[j].store) >= 0))
			{
				items[k] = arrayL[i];
				i++;
			}
			else
			{
				items[k] = arrayR[j];
				j++;
			}
			k++;
		}
		
		//Copies the remaining elements of the temporary arrays if necessary
		while(i < sizeL)
		{
			items[k] = arrayL[i];
			i++;
			k++;
		}
		while(j < sizeR)
		{
			items[k] = arrayR[j];
			j++;
			k++;
		}
	}
	
	//Function for sorting an array
	public void sort(Item[] items, int l, int r)
	{
		if(l < r)
		{
			//Gets the middle
			int m = l + ((r - l) / 2);
			
			//Sorts the left and right halves of the array
			sort(items, l, m);
			sort(items, m + 1, r);
			
			//Merges the sorted halves
			merge(items, l, m, r);
		}
	}
}