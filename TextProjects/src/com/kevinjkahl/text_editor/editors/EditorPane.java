package com.kevinjkahl.text_editor.editors;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;

public abstract class EditorPane extends JTextPane {
	
	public String fileName;
	public String dir;

	public EditorPane(String fileName, String dir, Color color) {
		super();
		this.setBorder( BorderFactory.createLineBorder( color, 2 ) );
		this.fileName = fileName;
		this.dir = dir;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		Component parent = getParent();
		ComponentUI ui = getUI();

		return parent != null ? ( ui.getPreferredSize( this ).width <= parent.getSize().width ) : true;
	}
}
