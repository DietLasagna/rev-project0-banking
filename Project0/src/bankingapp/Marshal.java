/**
 * Marshal.java
 * 
 * Version 0.6
 * 
 * Mar 05, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.io.*;
import java.util.*;

/**
 * Marshal Class provides serialization capabilities. 
 * 
 * @version 0.6 05 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class Marshal {

	/**
	 * Saves incoming data to specified data file using try-with-resources.
	 * @param <K> Key data type of HashMap
	 * @param <V> Value data type of HashMap
	 * @param fileLocation Path for file to write
	 * @param UserMap HashMap to serialize to file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static <K, V> void serialize(String fileLocation, HashMap<K, V> UserMap)
			throws FileNotFoundException, IOException {
		
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(fileLocation))) {
			
			File myFile = new File(fileLocation);
			
			if(!myFile.isFile()) {
				
				myFile.createNewFile();
//				System.out.println("NEW FILE CREATED"); // DEBUG
			
			}
			
			out.writeObject(UserMap);
			
		}
		
	}
	
	/**
	 * Reads HashMap from serialized data file.
	 * @param <V> Value data type of HashMap
	 * @param fileLocation Path for file to write
	 * @param v Class of Value type
	 * @return HashMap of String, Value Type
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <V extends UserAbstract> HashMap<String, V> 
	deserialize(String fileLocation, Class<V> v)
			throws FileNotFoundException, IOException, ClassNotFoundException  {
		
		HashMap<String, V> UserMap = new HashMap<String, V>();
		
		if(new File(fileLocation).isFile()) {
			
			try (ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(fileLocation));) {
				
				UserMap = (HashMap<String, V>) in.readObject();
				
			}
			
		}
		
		return UserMap;
		
	}

}
