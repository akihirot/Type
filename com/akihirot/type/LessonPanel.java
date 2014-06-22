package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.akihirot.io.FileInput;

abstract public class LessonPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	// Fix int
	final int NO = 0;
	final int YES = 1;
	final int RIGHT = 0;
	final int INCORRECT = 1;
	final int BACK_SPACE = 2;
	final int SPACE = 3;
	final int Aquamarine = 0;
	final int UP = 1;
	final int DOWN = 0;

	// String for Type
	String[] stLesson;
	final String pointer = "<font color=navy>_</font>";
	final char chSPACE = 0x25AF;
	final ImageIcon[] icon = {new ImageIcon("key.png"), new ImageIcon("keyUp.png")};
	final String[] wordOfKey = {"1234567890-^","QWERTYUIOP@[","ASDFGHJKL;:]","ZXCVBNM,./"};
	final String FONT_RED = "<font color=red>";
	final String FONT_END = "</font>";
	final String HTML = "<html>"; 

	final FileInput FI = new FileInput();

	JLabel ansLabel;
	JLabel typeLabel;
	JLabel timeLabel;
	JLabel backLabel;
	JLabel[] KeyBord;
	JLabel[] KeySt;

	int typingNum;
	int missType;
	int fixType;
	int sec;
	int dsec;
	int msec;
	boolean LessonNotEnd;

	Color[] Col;

	LessonPanel(){
		super();
		setLayout(null);

		/*
		 * Must test change Font to "Segoe UI Symbol"
		 */

		typingNum = 0;
		missType = 0;
		fixType = 0;
		sec = 0;
		dsec = 0;
		msec = 0;
		LessonNotEnd = false;

		Col = (Color[])MyColor.getColor().clone();

		setLabels();

	}

	public int checkKey(char in, String s) {

		if(in == ansLabel.getText().charAt(typingNum)){		// RIGHT
			return RIGHT;
		}		// RIGHT  end
		else if((0x21<=in && in<=0x3b)||in==0x3d || (0x3f<=in && in<=0x7e))
		{		// INCORRECT
			return INCORRECT;
		}
		else if(in == KeyEvent.VK_BACK_SPACE)
		{		// BackSpace key
			return BACK_SPACE;
		} // BackSpace key  end
		else if(in == KeyEvent.VK_SPACE)			// Space key
		{
			return SPACE;	
		}	// Space key  end
		
		return -1;	
	}	// checkKey() end	

	public void callLessonTimer(){
		dsec++;
		msec += 100;
		if(dsec == 10){
			dsec = 0;
			sec++;
		}
	}

	// Create KeyBord Image
	public void showKey() {
		KeyBord = new JLabel[48];
		KeySt = new JLabel[48];

		int counter = 0;
		int dan = 0;
		int moji = 0;

		for(; counter < 12; counter++,moji++){
			KeySt[counter] = new JLabel();
			KeySt[counter].setText(""+wordOfKey[dan].charAt(moji));
			KeySt[counter].setBounds(120+(46*moji),300, 45, 45);
			KeySt[counter].setFont(new Font("Arial", Font.BOLD,20));

			KeyBord[counter] = new JLabel(icon[DOWN]);
			KeyBord[counter].setBounds(110+(46*moji),300, 47, 46);

			add(KeySt[counter]);
			add(KeyBord[counter]);
		}

		dan++;

		for(moji = 0; counter < 24; counter++,moji++){
			KeySt[counter] = new JLabel();
			KeySt[counter].setText(""+wordOfKey[dan].charAt(moji));
			KeySt[counter].setBounds(120+(23*dan)+(46*moji),300+(46*dan), 45, 45);
			KeySt[counter].setFont(new Font("Arial", Font.BOLD,20));

			KeyBord[counter] = new JLabel(icon[DOWN]);
			KeyBord[counter].setBounds(110+(23*dan)+(46*moji),300+(46*dan), 47, 46);

			add(KeySt[counter]);
			add(KeyBord[counter]);
		}

		dan++;

		for(moji = 0; counter < 36; counter++,moji++){
			KeySt[counter] = new JLabel();
			KeySt[counter].setText(""+wordOfKey[dan].charAt(moji));
			KeySt[counter].setBounds(120+(23*dan)+(46*moji),300+(46*dan), 45, 45);
			KeySt[counter].setFont(new Font("Arial", Font.BOLD,20));

			KeyBord[counter] = new JLabel(icon[DOWN]);
			KeyBord[counter].setBounds(110+(23*dan)+(46*moji),300+(46*dan), 47, 46);

			add(KeySt[counter]);
			add(KeyBord[counter]);
		}

		dan++;

		for(moji = 0; counter < 46; counter++,moji++){
			KeySt[counter] = new JLabel();
			KeySt[counter].setText(""+wordOfKey[dan].charAt(moji));
			KeySt[counter].setBounds(120+(23*dan)+(46*moji),300+(46*dan), 45, 45);
			KeySt[counter].setFont(new Font("Arial", Font.BOLD,20));

			KeyBord[counter] = new JLabel(icon[DOWN]);
			KeyBord[counter].setBounds(110+(23*dan)+(46*moji),300+(46*dan), 47, 46);

			add(KeySt[counter]);
			add(KeyBord[counter]);
		}

	}

	public void updownKey(int UD) {
		char Key = ansLabel.getText().charAt(typingNum);
		int dan = 0;
		int moji = 0;
		int hit = -1;
		for(dan = 0; dan < wordOfKey.length; dan++) {
			for(moji = 0; moji < wordOfKey[dan].length(); moji++) {
				if(Key == wordOfKey[dan].charAt(moji)){
					hit = 1;
					break;
				}
			}
			if(hit == 1)
				break;
		}
		if(hit == 1){
			int counter = 0;
			for(int i = 0; i < dan; i++)
				counter += wordOfKey[i].length();
			counter += moji;
			KeyBord[counter].setIcon(icon[UD]);
		}
	}

	public void deletePointer() {
		String s = typeLabel.getText();
		s = s.substring(0, s.length()-pointer.length());
		typeLabel.setText(s);
	}

	// get sentence for Lesson
	public void getStLesson(String st){
		stLesson = FI.FileRead(st);
	}
	
	// set Time for timeLabel
	abstract public void updateTime();

	// Paint Text area
	abstract public void paintComponent(Graphics g);

	// Initialize Labels
	abstract public void setLabels();

	// set Lesson's Text
	abstract public void setLesson();

	// Key Event
	abstract public int TypedKey(char in);
}

