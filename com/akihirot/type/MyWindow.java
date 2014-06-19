package com.akihirot.type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	final Color Aquamarine = new Color(0xE0,0xF8,0xD8,255);
	final int LEFT = -1;
	final int RIGHT = 1;

	Container contentPane;
	MiddleLessonPanel MiddleLessonPanel;
	SellectPanel SellectPane;
	ResultPanel ResultPane;
	MyKeyAdapter KeyAd;
	Timer LessonTimer;
	Timer repaintTimer;
	Timer slideRightTimer;
	Timer slideLeftTimer;
	
	int LevelNum = 3;

	enum  PaneState{
		Lesson,
		ShortLesson,
		Sellect,
		Start,
		Result
	}
	enum SellectState{
		Biginner,
		Middle,
		Senior
	}
	PaneState PS = PaneState.Sellect;
	SellectState SS = SellectState.Biginner;

	MyWindow() {
		super("Typinnnnnnnnnnnnnnnnnnnnnnnnnnnn");

		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		contentPane = getContentPane();
		KeyAd = new MyKeyAdapter();
		this.addKeyListener(KeyAd);

		repaintTimer = new Timer(100, this);
		repaintTimer.setActionCommand("repaintTimer");
		
		slideRightTimer = new Timer(1, new slideRightTimerClass());
		slideLeftTimer = new Timer(1, new slideLeftTimerClass());
	}

	/********** Panel Creater **********/
	public void CreateMiddleLessonPanel(){
		SellectPane.setVisible(false);

		MiddleLessonPanel = new MiddleLessonPanel();
		PS = PaneState.ShortLesson;
		MiddleLessonPanel.showKey();
		contentPane.add(MiddleLessonPanel, BorderLayout.CENTER);
		LessonTimer = new Timer(10, this);
		LessonTimer.setActionCommand("LessonTimer");
		MiddleLessonPanel.setLesson();
	}

	public void CreateLessonPanel(int LessonNum){
		switch(LessonNum){
		case 1:CreateMiddleLessonPanel();
		}
	}

	public void CreateSellectPanel(){
		SellectPane = new SellectPanel();
		PS = PaneState.Sellect;
		SS = SellectState.Biginner;
		setDots(LevelNum, SS);
		contentPane.add(SellectPane, BorderLayout.CENTER);
/*		for(int i = 0; i<SellectPane.LessonButton.length;i++){
			SellectPane.LessonButton[i].addActionListener(this);
			SellectPane.LessonButton[i].setActionCommand(""+i);
		}
*/		for(int i = 0; i < SellectPane.SellectButton.length ;i++){
			SellectPane.SellectButton[i].addActionListener(this);
			SellectPane.SellectButton[i].setActionCommand("" +(10+i));
		}
		SellectPane.GoButton.addActionListener(new StartButtonAction());
//		SellectPane.SellectButton[0].setVisible(false);
	}

	public void CreateResultPanel(int typingNum,int missTipe,int fixTipe, int msec){
		ResultPane = new ResultPanel(typingNum,missTipe, fixTipe, msec);
		PS = PaneState.Result;
		ResultPane.Button_OK.addActionListener(this);
		ResultPane.Button_OK.setActionCommand("Button_OK");
		contentPane.add(ResultPane, BorderLayout.CENTER);
		contentPane.remove(MiddleLessonPanel);
	}
	/********** Panel Creater END **********/

	public void goSellectClick(int e){
/*		if(e<10){
			CreateShortLessonPanel(e);
			contentPane.remove(SellectPane);
		}else */{
			if(e==11){
				slideLeftTimer.start();
			}else {
				slideRightTimer.start();
			}
			
				//ここにレベルのパネルの移動の記述を入れる
				//enumでどのレベルかの状態を示すようにする
				//移動は位置の設定
				//位置を取得できる？
				//ｘだけ移動できる？
				//いまは飛ぶからtimeで徐々に動くように
		}
	}
	
	// set Selecting Level Dots
	public void setDots(int LevelNum, SellectState ss){
		
		int state;
		
		switch(ss){
		case Biginner:
			state = 0;
			break;
			
		case Middle:
			state = 1;
			break;
			
		case Senior:
			state = 2;
			break;
		default:
			state = -1;
		}
		
		SellectPane.setDots(state, LevelNum);
	}
	
	// if push or select OK Button in Result Panel
	public void ResultEND(){
		ResultPane.setVisible(false);
		CreateSellectPanel();
		contentPane.remove(ResultPane);
	}

	class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			super.keyTyped(e);
			char in = e.getKeyChar();
			switch(PS){
			case Sellect:
				if(in != KeyEvent.VK_1 && in !=KeyEvent.VK_2 && in != KeyEvent.VK_3)
					break;
				int Iin = Integer.parseInt("" + in);
				SellectPane.setVisible(false);
				CreateLessonPanel(Iin);
				contentPane.remove(SellectPane);
				break;
			case ShortLesson:
				if(MiddleLessonPanel.LessonNotEnd){
					break;
				}
				if(LessonTimer.isRunning() == false){
					LessonTimer.start();
				}

				MiddleLessonPanel.TypedKey(in);
				if(MiddleLessonPanel.LessonNotEnd){
					LessonTimer.stop();
					repaintTimer.start();
				}
				break;

			case Result:
				if(in == KeyEvent.VK_ENTER)
					ResultEND();
					break;
			}
			requestFocus();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(PS){
		case Sellect:
			goSellectClick(Integer.parseInt(e.getActionCommand()));
			break;
		case ShortLesson:
			if(e.getActionCommand() == "repaintTimer"){
				if(MiddleLessonPanel.LessonNotEnd){
					MiddleLessonPanel.LessonNotEnd = false;
					CreateResultPanel(MiddleLessonPanel.typingNum,
							MiddleLessonPanel.missTipe,MiddleLessonPanel.fixTipe, MiddleLessonPanel.msec);
					MiddleLessonPanel.setEnabled(false);
					repaintTimer.stop();
					setVisible(true);
				}
				break ;
			}
			if(e.getActionCommand()=="LessonTimer"){
				MiddleLessonPanel.callLessonTimer();
				repaint();
				break;
			}
			break;
		case Result:
			ResultEND();
			break;
		}
		requestFocus();
	}
	
	public void slideLevel(int TO){
		int dis = 6 * TO;
		
		for(int j = 0; j<3; j++){
			SellectPane.LessonLabel[j].setLocation(
					SellectPane.LessonLabel[j].getX()+dis, SellectPane.LessonLabel[j].getY());
		}
	}
	
	
	public class slideRightTimerClass implements ActionListener{
		int i = 0;
		
		private void slideEnd(SellectState ss){
			i=0;
			SS = ss;
			slideRightTimer.stop();
		}
		
		public void actionPerformed(ActionEvent e) {

			switch(SS){
			
			case Biginner:
				if(i == 1)
					setDots(LevelNum, SellectState.Senior);
				if(i<200){
					slideLevel(LEFT);
				}else {
					slideEnd(SellectState.Senior);
				}
				break;
				
			case Middle:
				if(i == 1)
					setDots(LevelNum, SellectState.Biginner);
				if(i<100){
					slideLevel(RIGHT);
				}else {
					slideEnd(SellectState.Biginner);
				}
				break;

			case Senior:
				if(i == 1)
					setDots(LevelNum, SellectState.Middle);
				if(i<100){
					slideLevel(RIGHT);
				}else {
					slideEnd(SellectState.Middle);
				}
				break;
			}
			
			i++;
		}
	}

	
	
	public class slideLeftTimerClass implements ActionListener{
		int i = 0;

		private void slideEnd(SellectState ss){
			i=0;
			slideLeftTimer.stop();
			SS = ss;
		}

		public void actionPerformed(ActionEvent e) {

			switch(SS){

			case Biginner:
				if(i == 1)
					setDots(LevelNum, SellectState.Middle);
				if(i<100){
					slideLevel(LEFT);
				}else {
					slideEnd(SellectState.Middle);
				}
				break;

			case Middle:
				if(i == 1)
					setDots(LevelNum, SellectState.Senior);
				if(i<100){
					slideLevel(LEFT);
				}else {
					slideEnd(SellectState.Senior);
				}
				break;

			case Senior:
				if(i == 1)
					setDots(LevelNum, SellectState.Biginner);
				if(i<200){
					slideLevel(RIGHT);
				}else {
					slideEnd(SellectState.Biginner);
				}
				break;
			}

			i++;
		}
		
	}
	
	
	public class StartButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CreateLessonPanel(1);
			contentPane.remove(SellectPane);
			requestFocus();
		}
		
	}
	
}
