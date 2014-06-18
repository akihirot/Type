package com.akihirot.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads a text file
 * to String array for each line
 *
 * @author akihirot
 */

public class FileInput {
	String FILE_NAME;
	String[] a = null;

	public FileInput() {
	}

	public String[] FileRead(String FILE_NAME) {
		try{
			this.FILE_NAME = FILE_NAME;
			BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
			String line;
			int x = 0;
			while(null!=(line= reader.readLine())){
				x++;
			}
			a = new String[x];
			x=0;
			reader.close();

			reader = new BufferedReader(new FileReader(FILE_NAME));

			while((line=reader.readLine()) !=null) {
				a[x] =(line);
				x++;
			}
		}catch(IOException ie){
			System.out.println("Not found the file.");

		}catch(Exception e){
			System.out.println("Error opening the file.");
		}
		return a;
	}
}
