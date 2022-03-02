package bankingapp;

import java.io.*;
import java.util.*;

public class Marshal {
	/**
	 * Saves incoming data to specified file using try-with-resources
	 * @param fileLocation
	 * @param e
	 */
	public static void serialize(String fileLocation, HashMap<String, UserAbstract> UserMap) {
		
		// make new file if file does not exist
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileLocation))) {
			
			out.writeObject(UserMap);
			
		} catch(IOException ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, UserAbstract> deserialize(String fileLocation) { // CONSIDER throwing ClassNotFound to end program early
		
		HashMap<String, UserAbstract> UserMap = new HashMap<String, UserAbstract>();
		
		if(new File(fileLocation).isFile()) {
			
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileLocation));) {
				
				UserMap = (HashMap<String, UserAbstract>) in.readObject();
				
			} catch (IOException ex) {
				
				ex.printStackTrace();
				
			} catch (ClassNotFoundException ex) {
				
				ex.printStackTrace();
				
			}
			
		}
		
		return UserMap;
		
	}

}
