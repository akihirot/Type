package com.akihirot.type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	final int LEFT = -1;
	final int RIGHT = 1;

	Container contentPane;
	BeginnerLessonPanel BLP;
	MiddleLessonPanel MLP;
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
		BeginnerLesson,
		MiddleLesson,
		Sellect,
		Start,
		Result
	}
	enum SellectState{
		Beginner,
		Middle,
		Senior
	}
	PaneState PS = PaneState.Sellect;
	SellectState SS = SellectState.Beginner;

	MyWindow() {
		super("ATType");

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

		MLP = new MiddleLessonPanel();
		PS = PaneState.MiddleLesson;
		MLP.showKey();
		contentPane.add(MLP, BorderLayout.CENTER);
		LessonTimer = new Timer(100, this);
		LessonTimer.setActionCommand("LessonTimer");
		MLP.setLesson();
	}

	public void CreateBeginnerLessonPanel(){
		SellectPane.setVisible(false);
		BLP = new BeginnerLessonPanel();
		PS = PaneState.BeginnerLesson;
		BLP.showKey();
		contentPane.add(BLP, BorderLayout.CENTER);
		LessonTimer = new Timer(100, this);
		LessonTimer.setActionCommand("LessonTimer");
		BLP.setLesson();
	}

	public void CreateLessonPanel(int LessonNum){
		switch(LessonNum){
			case 0: CreateBeginnerLessonPanel();
			        break;
			case 1: CreateMiddleLessonPanel();
			        break;
			default: break; 
		}
	}

	public void CreateSellectPanel(){
		SellectPane = new SellectPanel();
		PS = PaneState.Sellect;
		SS = SellectState.Beginner;
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

	public void CreateResultPanel(int typingNum,int missType,int fixType, int msec){
		ResultPane = new ResultPanel(typingNum,missType, fixType, msec);
		ResultPane.Button_OK.addActionListener(this);
		ResultPane.Button_OK.setActionCommand("Button_OK");
		contentPane.add(ResultPane, BorderLayout.CENTER);
		switch(PS) {
			case BeginnerLesson: contentPane.remove(BLP);
				      break;
			case MiddleLesson: contentPane.remove(MLP);
				    break;
			default: break;
		}
		PS = PaneState.Result;
	}
	/********** Panel Creater END **********/

	public void goSellectClick(int e){
/*		if(e<10){
			CreateMiddleLessonPanel(e);
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
		case Beginner:
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
				if(in == KeyEvent.VK_ENTER){
					SSEnter();
				}else
				if(in == KeyEvent.VK_LEFT) {
					goSellectClick(11);
				}
				else if(in == KeyEvent.VK_RIGHT) {
					goSellectClick(10);
				}
				else if(in == KeyEvent.VK_1 || in ==KeyEvent.VK_2 || in == KeyEvent.VK_0) {
					SP123(in);
				}
				break;
			
			case BeginnerLesson:
				if(BLP.LessonNotEnd) {
					break;
				}
				if(LessonTimer.isRunning() == false) {
					LessonTimer.start();
				}
				BLP.TypedKey(in);
				if(BLP.LessonNotEnd) {
					BLP.deletePointer();
					if(BLP.getCounter() == 9) {
						BLP.incCounter();
						LessonTimer.stop();
						repaintTimer.start();
					}
					else {
						BLP.incCounter();
						BLP.newText();
						BLP.LessonNotEnd = false;
					}
					
				}
				break;

			case MiddleLesson:
				if(MLP.LessonNotEnd){
					break;
				}
				if(LessonTimer.isRunning() == false){
					LessonTimer.start();
				}

				MLP.TypedKey(in);
				if(MLP.LessonNotEnd){
					MLP.deletePointer();
					LessonTimer.stop();
					repaintTimer.start();
				}
				break;

			case Result:
				if(in == KeyEvent.VK_ENTER)
					ResultEND();
					break;
			default: break;
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
		case BeginnerLesson:
			if(e.getActionCommand() == "repaintTimer") {
				if(BLP.LessonNotEnd) {
					BLP.LessonNotEnd = false;
					CreateResultPanel(BLP.allNum,
							BLP.missType,
							BLP.fixType,
							BLP.msec);
					BLP.setEnabled(false);
					repaintTimer.stop();
					setVisible(true);
				}
				break;
			}
			if(e.getActionCommand()=="LessonTimer"){
				BLP.updateCounter();
				repaint();
			}
			break;
		case MiddleLesson:
			if(e.getActionCommand() == "repaintTimer"){
				if(MLP.LessonNotEnd){
					MLP.LessonNotEnd = false;
					CreateResultPanel(MLP.typingNum,
							MLP.missType,
							MLP.fixType, 
							MLP.msec);
					MLP.setEnabled(false);
					repaintTimer.stop();
					setVisible(true);
				}
				break ;
			}
			if(e.getActionCommand()=="LessonTimer"){
				MLP.updateTime();
				repaint();
				break;
			}
			break;
		case Result:
			ResultEND();
			break;
		default: break;
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
			
			case Beginner:
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
					setDots(LevelNum, SellectState.Beginner);
				if(i<100){
					slideLevel(RIGHT);
				}else {
					slideEnd(SellectState.Beginner);
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
			default: break;
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

			case Beginner:
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
					setDots(LevelNum, SellectState.Beginner);
				if(i<200){
					slideLevel(RIGHT);
				}else {
					slideEnd(SellectState.Beginner);
				}
				break;
			default: break;
			}

			i++;
		}
		
	}
	
	
	public class StartButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch(SS) {
				case Beginner:
					CreateLessonPanel(0);
					break;
				case Middle:	
					CreateLessonPanel(1);
					break;
				default: break;
			}
			contentPane.remove(SellectPane);
			requestFocus();
		}
		
	}
	
	public void SSEnter() {
		switch(SS) {
			case Beginner:
				CreateLessonPanel(0);
				break;
			case Middle:	
				CreateLessonPanel(1);
				break;
			default: break;
		}
		contentPane.remove(SellectPane);
		requestFocus();
	}
	
	public void SP123(char in) {
		int Iin = Integer.parseInt("" + in);
		SellectPane.setVisible(false);
		CreateLessonPanel(Iin);
		contentPane.remove(SellectPane);
	}
	
}
