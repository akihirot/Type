package com.akihirot.type;


public class ShortLessonPanel extends LessonPanel {
	private static final long serialVersionUID = 1L;

	// String for Tipe
	final String[] stLesson ={ "Well done is better than well said" ,"dd.","Wouldn't it be nice"};

	ShortLessonPanel(){
		super();

		ansLabel.setBounds(100,80,600,100);
		typeLabel.setBounds(100,115,600,100);
		timeLabel.setBounds(600,50,100,50);

	}
}
