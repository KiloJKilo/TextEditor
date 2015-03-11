package com.kevinjkahl.text_editor.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import com.kevinjkahl.TextEditor;
import com.kevinjkahl.text_editor.fileIO.FileIO;

public class MenuActions {

	TextEditor textEditor;
	private static FileIO fileIO = new FileIO( );

	/**
	 * Constructor
	 * 
	 * @param textEditor
	 */
	public MenuActions( TextEditor textEditor ) {
		this.textEditor = textEditor;
	}

	public class OpenFileAction extends AbstractAction {

		private String name;

		// private File file;

		public OpenFileAction( String name ) {
			super( name );
			//this.name = name;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			JFileChooser fileChooser = new JFileChooser();
			File file;

			if ( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {// if a file is selected
				file = fileChooser.getSelectedFile();
				// BufferedReader br = fileIO.open( file );
				fileIO.open( file );
				textEditor.newContentTab(fileIO.open( file ), file.getAbsolutePath(), file.getName());

			}
		}



	}
	


}
