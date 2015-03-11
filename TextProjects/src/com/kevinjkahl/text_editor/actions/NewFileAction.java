package com.kevinjkahl.text_editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.kevinjkahl.TextEditor;

public class NewFileAction extends AbstractAction {

	private TextEditor textEditor;

	public NewFileAction(String string  ) {
		super( string );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println( e );

	}

}
