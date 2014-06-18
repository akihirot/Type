package com.akihirot.io;

/**
 * Writes a text file
 *
 * @author akihirot
 */
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {
	String FILE_NAME;

	public FileOutput() {

	}

	public void FileWrite(String[] original, String FILE_NAME) {
		this.FILE_NAME = FILE_NAME;
		try {
			FileWriter out =new FileWriter(FILE_NAME+".txt");
			for(int i = 0; i < original.length; i++){
				out.write(original[i]+"\r\n");
			}
			// not ¥n but ¥r¥n
			out.close();
			System.out.println("Conpleted.");
			}catch(IOException ie){
				System.out.println("Not found the file.");
			}catch(Exception e){
					System.out.println("Error opening the file.");
			}
	}
}
