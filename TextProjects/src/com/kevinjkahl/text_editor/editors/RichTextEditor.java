package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class RichTextEditor extends TextEditorPane {

	private final String extension = ".rtf";
	private final String filetype = "text/richtext";

	public RichTextEditor() {
		// super( null, "", "Untitled", null );
		super();
		// this.setContentType( "text/richtext" );
	}

	/**
	 * Constructor for tabs with content.
	 * 
	 * @param stream
	 * @param path
	 * @param fileName
	 * @param color
	 */
	public RichTextEditor( FileInputStream stream, String path, String fileName, Color color, boolean saveEligible ) {
		super( path, fileName, color, saveEligible );
		super.getScrollableTracksViewportWidth();
		try {
			this.read( stream, null );
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
	public RichTextEditor( String title, String path, Color color ) {
		super( path, title, color );
		setEditor();

	}

	public RichTextEditor( String textToSave, String path, String fileName, Color color, boolean b ) {
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
		this.setEditorKit( new RTFEditorKit() );

	}

	@Override
	public boolean save( File file, String textToSave ) {
		// System.out.println( this.getStyledDocument() );
		String fileName = file.getAbsolutePath();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream( fileName );
		} catch ( FileNotFoundException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		RTFEditorKit kit = ( RTFEditorKit ) this.getEditorKit();
		StyledDocument doc = this.getStyledDocument();
		int len = doc.getLength();
		System.out.println( doc );
		try {
			kit.write( fos, doc, 0, len );
			fos.close();
			return true;
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch ( BadLocationException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static FileInputStream open( File file ) {

		try {
			FileInputStream fis = new FileInputStream( file );
			return fis;
		} catch ( FileNotFoundException e ) {
			return null;
		}

	}
}
