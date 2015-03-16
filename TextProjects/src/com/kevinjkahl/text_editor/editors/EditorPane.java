package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ComponentUI;

public abstract class EditorPane extends JTextPane {
	
	private String fileName;
	private String path;
	private DocumentListener documentListener;
	

	public EditorPane(DocumentListener dl , String fileName, String path, Color color) {
		super();
		this.setBorder( BorderFactory.createLineBorder( color, 2 ) );
		this.fileName = fileName;
		this.path = path;
		this.documentListener = dl;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		Component parent = getParent();
		ComponentUI ui = getUI();

		return parent != null ? ( ui.getPreferredSize( this ).width <= parent.getSize().width ) : true;
	}
	
	public void changeBorder(Color color) {
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
	
	public void setDocumentListener(DocumentListener dl) {
		this.documentListener = dl;
	}
}
