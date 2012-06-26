package Application;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer
{
	public static void writeToFile(String filename, Object object) throws IOException
	{
		ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
		objOut.writeObject(object);
		objOut.close(); 
	}

	public static Object readFromFile(String filename) throws IOException, ClassNotFoundException
	{
		ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
		Object object = (Object) objIn.readObject();
		objIn.close();
	
		return object;
	}
}
