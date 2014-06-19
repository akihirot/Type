package com.akihirot.type;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	final Color Niagara = new Color(38,174,144,255);
	final Color ClearNiagara = new Color(38,174,144,220);
	final Color Back = new Color(0xE0,0xF0,0xD0,180);
	
	//Field
	JButton Button_OK;
	JLabel SpeedLabel;
	JLabel MissLabel;
	JLabel FixLabel;
	
		ResultPanel(int typingNum,int missTipe,int fixTipe, int msec){
		super();
		setLayout(null);

		// setBackground(Back);

		JLabel SpeedLabel = new JLabel();
		SpeedLabel.setBounds(100, 250, 250, 50);
		// SpeedLabel.setBackground(Color.WHITE);
		SpeedLabel.setFont(new Font("MS 明朝", Font.BOLD,20));
		SpeedLabel.setForeground(Color.WHITE);
		JLabel MissLabel = new JLabel();
		MissLabel.setBounds(100, 300, 200, 50);
		// MissLabel.setBackground(Color.white);
		MissLabel.setFont(new Font("MS 明朝", Font.BOLD,20));
		MissLabel.setForeground(Color.WHITE);
		JLabel FixLabel = new JLabel();
		FixLabel.setBounds(100, 350, 200, 50);
		// FixLabel.setBackground(Niagara);
		FixLabel.setFont(new Font("MS 明朝", Font.BOLD,20));
		FixLabel.setForeground(Color.WHITE);

		SpeedLabel.setText("打鍵速度："+ (typingNum*60000/msec) + "文字/分");
		MissLabel.setText("ミス：" + missTipe);
		FixLabel.setText("訂正回数：" + fixTipe);

		Button_OK = new JButton("OK (Enter)");
		Button_OK.setBounds(550,250,100,50);
		Button_OK.setBackground(Niagara);
		
		add(SpeedLabel);
		add(MissLabel);
		add(FixLabel);
		add(Button_OK);
	}
	
	public void paintComponent(Graphics g){
		  Graphics2D g2 = (Graphics2D)g;
		  g2.setPaint(Back);
		  g2.fill(new Rectangle2D.Double(0.0d, 0.0d, 800.0d, 600.0d));
		  g2.setPaint(ClearNiagara);
		  g2.fill(new Rectangle2D.Double(90.0d, 240.0d, 250.0d, 180.0d));
		

		}
}
