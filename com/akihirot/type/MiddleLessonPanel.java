package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MiddleLessonPanel extends LessonPanel{

	private static final long serialVersionUID = 1L;
	Random rnd = new Random();


	MiddleLessonPanel(){
		super();
		setLayout(null);

		//setLabels
		ansLabel = new JLabel();
		ansLabel.setBounds(100,80,600,100);
		typeLabel = new JLabel();
		typeLabel.setBounds(100,115,600,100);
		timeLabel = new JLabel();
		timeLabel.setBounds(600,50,100,50);

		ansLabel.setFont(new Font("Courier New", Font.PLAIN,20));
		typeLabel.setFont(new Font("Courier New", Font.PLAIN,20));
		timeLabel.setFont(new Font("Arial", Font.PLAIN,20));

		getStLesson();

	}


	public void setLesson() {
		ansLabel.setText(stLesson[rnd.nextInt(stLesson.length)]);
		typeLabel.setText("<html>" + pointer);
		timeLabel.setText("0.00" );
		add(typeLabel);
		add(ansLabel);
		add(timeLabel);
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		g2.setPaint(Aquamarine);
		g2.fill(new Rectangle2D.Double(0.0d, 0.0d, 800.0d, 600.0d));
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(80.0d, 100.0d, 640.0d, 90.0d));


	}


	public void callLessonTimer(){
		csec++;
		msec += 10;
		if(csec == 100){
			csec = 0;
			sec++;
		}
		String stCsec;
		if(csec < 10)
			stCsec = "0" + csec;
		else
			stCsec = "" + csec;
		timeLabel.setText("" + sec + "." + stCsec);
	}

	public void getStLesson(){
		stLesson = FI.FileRead("middle.typ");
	}

}
