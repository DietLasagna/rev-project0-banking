package bankingapp;

import java.io.*;
import java.util.*;

public class Marshal {
	/**
	 * Saves incoming data to specified file using try-with-resources
	 * @param fileLocation
	 * @param e
	 */
	public static <K, V> void serialize(String fileLocation, HashMap<K, V> UserMap) {
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileLocation))) {
			
			File myFile = new File(fileLocation);
			
			if(!myFile.isFile()) {
				
				myFile.createNewFile();
				System.out.println("NEW FILE CREATED"); // DEBUG
			
			}
			
			out.writeObject(UserMap);
			
		} catch(IOException ex) {
			
			ex.printStackTrace();
			System.out.println("DIDN'T SERIALIZE"); // DEBUG
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <V extends UserAbstract> HashMap<String, V> deserialize(String fileLocation, Class<V> v) throws Exception {
		
		HashMap<String, V> UserMap = new HashMap<String, V>();
		
		if(new File(fileLocation).isFile()) {
			
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileLocation));) {
				
				UserMap = (HashMap<String, V>) in.readObject();
				
			}
			
		}
		
		return UserMap;
		
	}

}
