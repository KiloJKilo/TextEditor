package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;

public class TextEditorWrap extends EditorPane {

	public TextEditorWrap() {
		super( "", "Untitled", null );

	}

	public TextEditorWrap( BufferedReader br, String fileName, String dir, Color color ) {
		super( fileName, dir, color );
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
	 * @param title - call to super to set title of tab / file
	 * @param color - call to super to set border color of text pane
	 */
	public TextEditorWrap( String title, Color color ) {
		super(title, null, color);
	}

}
