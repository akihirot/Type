package com.akihirot.type;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.akihirot.io.FileInput;

public class SellectPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
//	final Color Aquamarine = new Color(96,196,176,255);
//	final Color Aquamarine = new Color(0xE0,0xF8,0xD8,255);
//	final Color Niagara = new Color(96,196,176,255);
//	final Color Niagara = new Color(38,174,144,255);
	Color[] Col;
	
//	final String[] LessonLevel = {"BIGINNER" ,"MIDDLE", "SENIOR"};
	final String[] LessonLevel = {"初級" ,"中級", "上級"};
	final String[] SellectDistinct = {"<<", ">>"};
	final String[] htmlTag = {"<html>", "<font color=#60c4b0>", "</font>"};
	final String    dot = "●";
	final FileInput FI = new FileInput();
	String[] stColor;
	String[][] spColor;

	// Field
//	JButton[] LessonButton;
	JButton[] SellectButton;
	JLabel[]   LessonLabel;
	JLabel      DotsLabel;
	JButton    GoButton;

	/**** FOR BACK GROUND IMAGE 1/3 *****
	private BufferedImage image;*/

	SellectPanel(){
		super();
		setLayout(null);
		
		getColor();
		Col = new Color[spColor.length];
		for(int i = 0; i < spColor.length; i++)
			Col[i] = new Color(Integer.parseInt(spColor[i][0])
					, Integer.parseInt(spColor[i][1]),
				Integer.parseInt(spColor[i][2])
					, Integer.parseInt(spColor[i][3]));
		
		this.setBackground(Col[0]);
//		LessonButton = new JButton[3];
		LessonLabel = new JLabel[LessonLevel.length];
		SellectButton = new JButton[2];

		for(int i = 0; i <3;i++){
/*			LessonButton[i] = new JButton(""+i);
			LessonButton[i].setBounds(100, 200*(i+1), 100, 50);
			LessonButton[i].setBackground(Niagara);
			LessonButton[i].setBorderPainted(false);
			//			add(LessonButton[i]);
*/
			LessonLabel[i] = new JLabel();
			LessonLabel[i].setText(LessonLevel[i]);
			LessonLabel[i].setFont(new Font("MS ゴシック", Font.BOLD,70));
			//			LessonLabel[i].setForeground(Color.WHITE);
			LessonLabel[i].setHorizontalAlignment(JLabel.CENTER);
			LessonLabel[i].setBounds(200+ (i*600), 150, 400, 100);
			add(LessonLabel[i]);
		}


		for(int i = 0; i < 2; i++){
			SellectButton[i] = new JButton();
			SellectButton[i].setBounds(80+(540*i), 80, 90, 240);
			SellectButton[i].setText(SellectDistinct[i]);
			SellectButton[i].setBackground(Col[0]);
			SellectButton[i].setBorderPainted(false);
			add(SellectButton[i]);
		}
		
		DotsLabel = new JLabel();
		DotsLabel.setText("");
		DotsLabel.setFont(new Font("MS ゴシック", Font.BOLD,15));
		DotsLabel.setForeground(Color.WHITE);
		DotsLabel.setHorizontalAlignment(JLabel.CENTER);
		DotsLabel.setBounds(200, 300, 400, 50);
		add(DotsLabel);

		GoButton = new JButton("START");
		GoButton.setBounds(320, 400, 160, 80);
		GoButton.setFont(new Font("MS ゴシック", Font.BOLD, 30));
		GoButton.setBackground(Col[1]);
		GoButton.setBorderPainted(false);
		add(GoButton);
		
		
		/**** FOR BACK GROUND IMAGE 2/3 *****
		try {
            this.image = ImageIO.read(getClass().getResource("back.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
            this.image = null;
        }*/
	}
	
	public void setDots(int state, int LevelNum) {
		DotsLabel.setText(htmlTag[0] + " ");
		for(int i = 0; i < LevelNum; i++){
			if(i==state)
				DotsLabel.setText(DotsLabel.getText() + htmlTag[1] + dot + htmlTag[2]);
			else 
				DotsLabel.setText(DotsLabel.getText() + dot);
			
			DotsLabel.setText(DotsLabel.getText() + " ");
		}
	}
	
	public void getColor() {
		stColor = FI.FileRead("Color.typ");
		spColor = new String[stColor.length][4];
		for(int i  = 0; i < stColor.length; i++){
			spColor[i] = stColor[i].split(",");
		}
	}
	
	/**** FOR BACK GROUND IMAGE 3/3 *****
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double panelWidth = this.getWidth();
        double panelHeight = this.getHeight();

        // 画像がコンポーネントの何倍の大きさか計算
        double sx = (panelWidth / imageWidth);
        double sy = (panelHeight / imageHeight);

        // スケーリング
        AffineTransform af = AffineTransform.getScaleInstance(sx, sy);
        g2D.drawImage(image, af, this);
    }*/
	

}
