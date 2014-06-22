package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JLabel;

public class MiddleLessonPanel extends LessonPanel{

	private static final long serialVersionUID = 1L;
	Random rnd = new Random();

	MiddleLessonPanel(){
		super();
		setLayout(null);
		getStLesson("middle.typ");
	}

	//setLabels
	public void setLabels(){
		ansLabel = new JLabel();
		ansLabel.setBounds(100,80,600,100);
		typeLabel = new JLabel();
		typeLabel.setBounds(100,115,600,100);
		timeLabel = new JLabel();
		timeLabel.setBounds(600,50,100,50);

		ansLabel.setFont(new Font("Courier New", Font.BOLD,20));
		typeLabel.setFont(new Font("Courier New", Font.BOLD,20));
		timeLabel.setFont(new Font("Arial", Font.PLAIN,20));
	}

	public void setLesson() {
		ansLabel.setText(stLesson[rnd.nextInt(stLesson.length)]);
		typeLabel.setText(HTML + pointer);
		timeLabel.setText("0.00" );
		add(typeLabel);
		add(ansLabel);
		add(timeLabel);
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		g2.setPaint(Col[Aquamarine]);
		g2.fill(new Rectangle2D.Double(0.0d, 0.0d, 800.0d, 600.0d));
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(80.0d, 100.0d, 640.0d, 90.0d));


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

	public int TypedKey(char in){
		String s = typeLabel.getText();
		s = s.substring(0, s.length()-pointer.length());

		int num = super.checkKey(in, s);
	
		switch(num) {
			case RIGHT:
				typeLabel.setText(s + in + pointer);
				typingNum++;
				break;
			case INCORRECT:
				typeLabel.setText(s + FONT_RED + in
						+ FONT_END + pointer);
				typingNum++;
				missType++;
				break;
			case BACK_SPACE:
				backKey(s);
				break;
			case SPACE:
				typeLabel.setText(s + FONT_RED +
						chSPACE+ FONT_END + pointer);
				typingNum++;
				missType++;
				break;
			default:
		}

		repaint();

		if(typingNum >= ansLabel.getText().length())
		{
			LessonNotEnd = true;
			return 0;
		}
		return 1;
	}

	private void backKey(String s){
		// for check whether </font> is deleted
		int deleteFont = NO;

		if(s.length() > HTML.length())		// already started
		{
			if(s.charAt(s.length()-1) == '>')
			{		// String's end == </font>
				s = s.substring(0, s.length()-FONT_END.length());
				deleteFont = YES;
			}

			s = s.substring(0,s.length()-1);    // delete end char

			if(deleteFont == YES)
			{
				s = s.substring(0, s.length()-FONT_RED.length());
				missType--;
			}
			typeLabel.setText(s + pointer);
			typingNum --;
			fixType++;
		}
	}
}
