package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/***
 * @author Angel Morales
 * Class that converts data into a file
 * */


public class DataConverter {
	
	/**
	 * Method that takes in a string and output file name
	 * */
	
	public static void toFile (String data, String outFileName) {
		
		try (FileWriter file = new FileWriter(outFileName)){
			file.write(data);
			file.close();
		}catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	
	public static void main (String[] args)  {
		
		DataLoader dl = new CsvLoader();
		HashMap<String,Person> PersonData = CsvLoader.loadPeople();
		List<Store> StoreData = dl.loadStores();
		HashMap<String,Item> ItemsData = CsvLoader.loadItems();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String people = gson.toJson(PersonData);
		String stores = gson.toJson(StoreData);
		String items = gson.toJson(ItemsData);
		
		DataConverter.toFile(people, "data/Persons.json");
		DataConverter.toFile(stores, "data/Stores.json");
		DataConverter.toFile(items, "data/Items.json");

	}
	
	
}
