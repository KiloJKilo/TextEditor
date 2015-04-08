package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public abstract class TextEditorPane extends JTextPane {

	private String fileName;
	private String path;
	private boolean saveEligible = false;
	private DocumentListener docListener;

	/**
	 * Blank tab constructor.
	 * 
	 * @param dl
	 *            - the document listener instance
	 * @param path
	 *            - path of file
	 * @param fileName
	 *            - filename
	 * @param color
	 *            - color of border and tab
	 * @param extension
	 */
	public TextEditorPane( String path, String fileName, Color color ) {
		super();
		this.setBorder( BorderFactory.createLineBorder( color, 2 ) );
		this.fileName = "Untitled";
		this.path = path;
		this.getDocument().putProperty( "file name", fileName );
		// this.getDocument().addDocumentListener( dl );
	}

	/**
	 * Content tab constructor for tabs that read data into a text component.
	 * 
	 * @param path
	 * @param fileName
	 * @param color
	 */
	public TextEditorPane( String path, String fileName, Color color, Boolean saveElgible ) {
		super();
		this.setBorder( BorderFactory.createLineBorder( color, 2 ) );
		this.fileName = fileName;
		this.path = path;
		this.getDocument().putProperty( "file name", fileName );
		this.saveEligible = saveElgible;
	}

	public TextEditorPane() {

	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		Component parent = getParent();
		ComponentUI ui = getUI();

		return parent != null ? ( ui.getPreferredSize( this ).width <= parent.getSize().width ) : true;
	}

	public void changeBorder( Color color ) {
		this.setBorder( BorderFactory.createLineBorder( color, 2 ) );
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String dir ) {
		this.path = dir;
	}

	public boolean isSaveEligible() {
		return saveEligible;
	}

	public void setSaveEligible( boolean saveEligible ) {
		this.saveEligible = saveEligible;
	}

	public DocumentListener getDocListener() {
		return docListener;
	}

	public void setDocListener( DocumentListener docListener ) {
		this.docListener = docListener;
	}

	public abstract boolean save( File file, String textToSave );

}
