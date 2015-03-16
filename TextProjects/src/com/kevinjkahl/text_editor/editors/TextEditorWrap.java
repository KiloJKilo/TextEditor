package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.event.DocumentListener;

public class TextEditorWrap extends EditorPane {

	public TextEditorWrap() {
		super( null, "", "Untitled", null );

	}

	public TextEditorWrap( DocumentListener dl , BufferedReader br , String fileName, String dir, Color color ) {
		super( dl, fileName, dir, color );
		super.getScrollableTracksViewportWidth();
		try {
			this.read( br, null );
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Constructor for new blank tabs
	 * 
	 * @param title
	 *            - call to super to set title of tab / file
	 * @param color
	 *            - call to super to set border color of text pane
	 */
	public TextEditorWrap( DocumentListener dl, String title, Color color ) {
		super( dl, title, null, color );
	}

	public void changeBorder( Color color ) {
		super.changeBorder( color );
	}

}
