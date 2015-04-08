package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.EditorKit;
import javax.swing.text.PlainDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class TextEditorWrap extends TextEditorPane {

	private final String extension = ".txt";
	private final String filetype = "text/plain";

	public TextEditorWrap() {
		// super( null, "", "Untitled", null );
		super();
		//this.setContentType( "text/plain" );
	}

	/**
	 * Constructor for tabs with content.
	 * 
	 * @param br
	 * @param path
	 * @param fileName
	 * @param color
	 */
	public TextEditorWrap( BufferedReader br, String path, String fileName, Color color, boolean saveEligible ) {
		super( path, fileName, color, saveEligible );
		super.getScrollableTracksViewportWidth();
		try {
			this.read( br, null );
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getDocument().putProperty( "file name", fileName );
		setEditor();
		}


	/**
	 * Constructor for new blank tabs
	 * 
	 * @param title
	 *            - call to super to set title of tab / file
	 * @param string
	 * @param color
	 *            - call to super to set border color of text pane
	 */
	public TextEditorWrap( String title, String path, Color color ) {
		super( path, title, color );
		setEditor();
	}

	public TextEditorWrap( String textToSave, String path, String fileName, Color color, boolean b ) {
		super( path, fileName, color, b );
		setEditor();
	}

	public void changeBorder( Color color ) {
		super.changeBorder( color );
	}

	public String getExtension() {
		return extension;
	}

	private void setEditor() {
		//this.setEditorKit( new DefaultEditorKit() );
		
	}

	@Override
	public boolean save(File file , String textToSave) {
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

	
	public BufferedReader open( File file ) {
		// TODO Auto-generated method stub
		return null;
	}
}
