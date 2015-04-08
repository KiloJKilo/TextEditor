package com.kevinjkahl.text_editor.fileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import com.kevinjkahl.text_editor.editors.TextEditorPane;

public class FileIO {

	JTextPane textPane;

	public FileIO() {

	}

	public BufferedReader open( File file ) {

		BufferedReader br;

		try {
			FileReader fReader = new FileReader( file );
			try {
				br = new BufferedReader( fReader );
				return br;
			} catch ( Exception e ) {
				return null;
			}
		} catch ( Exception error ) {
			error.printStackTrace();
		}
		return null;
	}

	public boolean saveAs( File file, String textToSave, TextEditorPane tab  ) {

		return tab.save(file, textToSave);
		
//		try {
//
//			FileWriter fWriter = new FileWriter( file.getAbsolutePath() );
//			BufferedWriter bw = new BufferedWriter( fWriter );
//			bw.write( textToSave );
//			bw.close();
//			return true;
//		} catch ( Exception e ) {
//			return false;
//		}

	}

}
