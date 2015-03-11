package com.kevinjkahl.text_editor.fileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;

public class FileIO {

	private File file;
	private String fileName;
	private String directory;

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

	public void save( JTextPane mainEditorPane ) {

		int returnVal;

		JFileChooser fileChooser = new JFileChooser();

		returnVal = fileChooser.showSaveDialog( null );
		// TODO: add a method to write something other then text. Like .html or .php

		if ( returnVal == JFileChooser.APPROVE_OPTION ) {// if a file is selected
			fileName = fileChooser.getSelectedFile().getName();
			directory = fileChooser.getCurrentDirectory().toString();
			file = new File( directory + "\\" + fileName + ".txt" );

			try {
				FileWriter fWriter = new FileWriter( file.getAbsolutePath() );

				// if the file does not exist, create a new one
				if ( !file.exists() ) {
					file.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter( fWriter );
				bw.write( mainEditorPane.getText() );
				bw.close();
				mainEditorPane.requestFocus();

			} catch ( Exception error ) {
				error.printStackTrace();
			}
		}
	}

}
