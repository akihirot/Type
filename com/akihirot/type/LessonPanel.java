package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LessonPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	// Fix int
	final int DISABLE = 0;
	final int ENABLE = 1;

	// String for Tipe
	final String[] stLesson ={ "well done is better than well said" ,"wouldn't it be nice","dd."};
	final String pointer = "<font color=navy>_</font>";
	final char space = 0x25AF;
	final String stSpace = "<font color=red>"+space+"</font>" ;
	final ImageIcon icon = new ImageIcon("key.png");
	final String[] wordOfKey = {"1234567890-^","QWERTYUIOP@[","ASDFGHJKL;:]","ZXCVBNM,./"};

	final Color Aquamarine = new Color(0xE0,0xF8,0xD8,255);

	JLabel ansLabel;
	JLabel typeLabel;
	JLabel timeLabel;
	JLabel backLabel;
	JLabel[] KeyBord;
	JLabel[] KeySt;

	int typingNum;
	int missTipe;
	int fixTipe;
	int sec;
	int csec;
	int msec;
	boolean LessonNotEnd;

	LessonPanel(){
		super();
		setLayout(null);

	//setLabels
		ansLabel = new JLabel();
		ansLabel.setBounds(100,80,600,100);
		typeLabel = new JLabel();
		typeLabel.setBounds(100,115,600,100);
		timeLabel = new JLabel();
		timeLabel.setBounds(600,50,100,50);

		ansLabel.setFont(new Font("Courier New", Font.BOLD,25));
		typeLabel.setFont(new Font("Courier New", Font.BOLD,25));
		timeLabel.setFont(new Font("Arial", Font.BOLD,25));

		/*
		 * Must test change Font to "Segoe UI Symbol"
		 */

		typingNum = 0;
		missTipe = 0;
		fixTipe = 0;
		sec = 0;
		csec = 0;
		msec = 0;
		LessonNotEnd = false;

	}


	public void setLesson(int LessonNum) {
		ansLabel.setText(stLesson[LessonNum]);
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


		public int TypedKey(char in) {

			String s = typeLabel.getText();
			s = s.substring(0, s.length()-pointer.length());

			if(in == ansLabel.getText().charAt(typingNum)){			// RIGHT
				typeLabel.setText(s + in + pointer);
				typingNum++;
			}	// RIGHT  end
			else if((0x21<=in && in<=0x3b)||in==0x3d || (0x3f<=in && in<=0x7e))
			{		// INCORRECT
				typeLabel.setText(s +"<font color=red>" + in + "</font>" + pointer);
				typingNum++;
				missTipe++;
			}
			else if(in == KeyEvent.VK_BACK_SPACE)
			{																// BackSpace key
				int deleteFont = DISABLE;

				if(s.length() > 6)		// already started
				{
					if(s.charAt(s.length()-1) == '>')
					{		// String's end == </font>
						s = s.substring(0, s.length()-7);
						deleteFont = ENABLE;
					}

					s = s.substring(0,s.length()-1);			// delete end char

					if(deleteFont == ENABLE)
					{
						s = s.substring(0, s.length()-16);
						missTipe--;
					}
					typeLabel.setText(s + pointer);
					typingNum --;
					fixTipe++;
				}
			} // BackSpace key  end
			else if(in == KeyEvent.VK_SPACE)			// Space key
			{
				typeLabel.setText(s +"<font color=red>"+stSpace+"</font>" + pointer);
				typingNum++;
				missTipe++;
			}	// Space key  end

			repaint();

			if(typingNum >= ansLabel.getText().length())
			{
/*				typingNum=0;
				missTipe=0;
				fixTipe=0;*/
				LessonNotEnd = true;
				return 0;
			}
			return 1;
		}	// keyTyped() end

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

				KeyBord[counter] = new JLabel(icon);
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

				KeyBord[counter] = new JLabel(icon);
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

				KeyBord[counter] = new JLabel(icon);
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

				KeyBord[counter] = new JLabel(icon);
				KeyBord[counter].setBounds(110+(23*dan)+(46*moji),300+(46*dan), 47, 46);

				add(KeySt[counter]);
				add(KeyBord[counter]);
			}

		}

}
