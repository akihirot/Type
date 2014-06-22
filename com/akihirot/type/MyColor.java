package com.akihirot.type;

import java.awt.Color;
import com.akihirot.io.FileInput;

public class MyColor {

	public static Color[] Col;
	static FileInput FI = new FileInput();

	public static Color[] getColor() {
		String[] stColor;
		String[][] spColor;
		stColor = FI.FileRead("Color.typ");
		spColor = new String[stColor.length][4];
		for(int i  = 0; i < stColor.length; i++){
			spColor[i] = stColor[i].split(",");
		}
		Col = new Color[spColor.length];
		for(int i = 0; i < spColor.length; i++)
			Col[i] = new Color(Integer.parseInt(spColor[i][0])
					, Integer.parseInt(spColor[i][1]),
				Integer.parseInt(spColor[i][2])
					, Integer.parseInt(spColor[i][3]));
		return Col;
	}
}
