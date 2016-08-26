package com.notepad.app;

import java.awt.EventQueue;
import com.notepad.Notepad;

public class NotepadApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Notepad notepad = new Notepad();
				notepad.setVisible(true);
			}
		});
	}
}
