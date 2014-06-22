package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JLabel;

public class BeginnerLessonPanel extends LessonPanel{

	private static final long serialVersionUID = 1L;
	Random rnd = new Random();
	final String redPointer = "<font color=red>_</font>";
	final String nokori = "残り";
	final String kai = "回";
	
	int allNum = 0;
	
	private int counter;
	private boolean incorrect;
	
	BeginnerLessonPanel(){
		super();
		setLayout(null);
		getStLesson("beginner.typ");

		counter = 0;
		incorrect = false;
	}
	
	public int getCounter() {
		return counter;
	}
	public void setCounter(int n) {
		counter = n;
		return ;
	}
	public void incCounter() {
		counter++;
		return ;
	}
	//setLabels
	public void setLabels(){
		ansLabel = new JLabel();
		ansLabel.setBounds(325,80,220,100);
		typeLabel = new JLabel();
		typeLabel.setBounds(325,80,220,100);
		timeLabel = new JLabel();
		timeLabel.setBounds(600,50,150,80);

		ansLabel.setFont(new Font("Courier New", Font.BOLD,60));
		typeLabel.setFont(new Font("Courier New", Font.BOLD,60));
		timeLabel.setFont(new Font("MS ゴシック", Font.PLAIN,25));

		ansLabel.setForeground(Color.GRAY);
	}

	public void setLesson() {
		newText();

		add(typeLabel);
		add(ansLabel);
		add(timeLabel);
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		g2.setPaint(Col[Aquamarine]);
		g2.fill(new Rectangle2D.Double(0.0d, 0.0d, 800.0d, 600.0d));
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(300.0d, 90.0d, 200.0d, 90.0d));


	}

	public void updateTime(){
		super.callLessonTimer();
		
		String stSec;
		if(sec < 10)
			stSec = "0" + sec;
		else
			stSec = "" + sec;
	
		timeLabel.setText("" + stSec + "." + dsec);
	}

	public void updateCounter(){
		super.callLessonTimer();
		timeLabel.setText(nokori + (10-counter) + kai);
		return ;
	}

	public int TypedKey(char in){
		String s = typeLabel.getText();
		if(!incorrect)
			s = s.substring(0, s.length()-pointer.length());
		else
			s = s.substring(0, s.length()-redPointer.length());

		int num = super.checkKey(in, s);
		boolean b = true;

		do{
			switch(num) {
				case RIGHT:
					typeLabel.setText(s + in + pointer);
					incorrect = false;
					updownKey(DOWN);
					typingNum++;
					break;
				case INCORRECT:
					char ans = ansLabel.getText().charAt(typingNum);
					in = Character.toUpperCase(in);
					if(in == ans){
						num = RIGHT;
						continue;
					}
				case SPACE:
					incorrect = true;
					typeLabel.setText(s + redPointer);
					missType++;
					break;
				default: break;
			}
			b = false;
		} while(b);

		repaint();

		if(typingNum >= ansLabel.getText().length())
		{
			LessonNotEnd = true;
			return 0;
		}
		if(!incorrect)
			updownKey(UP);
		return 1;
	}

	private String backKey(String s){
		if(s.length() > HTML.length())		// already started
		{
			s = s.substring(0, s.length()-FONT_END.length());

			s = s.substring(0,s.length()-1);    // delete end char
			s = s.substring(0, s.length()-FONT_RED.length());
		}
		return s;
	}

	public void newText(){
		ansLabel.setText(stLesson[rnd.nextInt(stLesson.length)]);
		typeLabel.setText(HTML + pointer);
		updateCounter();
		allNum += typingNum;
		typingNum = 0;	
		updownKey(UP);
		return ;
	}

}
