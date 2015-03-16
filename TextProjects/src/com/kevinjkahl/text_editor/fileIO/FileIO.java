package com.kevinjkahl.text_editor.fileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class FileIO {

	JTextPane textPane;
	final JOptionPane fileOverWrite = new JOptionPane( "The only way to close this dialog is by\n" + "pressing one of the following buttons.\n" + "Do you understand?",
			JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION );

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

	public boolean saveAs( File file, String textToSave ) {

		try {

			FileWriter fWriter = new FileWriter( file.getAbsolutePath() );
			BufferedWriter bw = new BufferedWriter( fWriter );
			bw.write( textToSave );
			bw.close();
			return true;
		} catch ( Exception e ) {
			return false;
		}

	}

}
