package com.kevinjkahl.text_editor.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.kevinjkahl.TextEditor;
import com.kevinjkahl.text_editor.editors.EditorPane;
import com.kevinjkahl.text_editor.fileIO.FileIO;

public class MenuActions {

	private TextEditor textEditor;
	private JTabbedPane tabbedPane;

	private static FileIO fileIO = new FileIO();

	/**
	 * Constructor
	 * 
	 * @param textEditor
	 */
	public MenuActions( TextEditor textEditor ) {
		this.textEditor = textEditor;
	}

	public class OpenFileAction extends AbstractAction {

		public OpenFileAction( String name ) {
			super( name );
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			JFileChooser fileChooser = new JFileChooser();
			File file;

			if ( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {// if a file is selected
				file = fileChooser.getSelectedFile();
				fileIO.open( file );
				textEditor.newContentTab( fileIO.open( file ), file.getAbsolutePath(), file.getName() );

			}
		}

	}

	public class SaveFileAction extends AbstractAction {

		private JTabbedPane tabbedPane;

		public SaveFileAction( JTabbedPane tabbedPane ) {
			this.tabbedPane = tabbedPane;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {

			final int index = tabbedPane.getSelectedIndex();// get the current index

			if ( index <= 0 ) {// no tab with content is active
			} else {
				File file = null;

				EditorPane tab = ( EditorPane ) tabbedPane.getComponentAt( tabbedPane.getSelectedIndex() );

				if ( e.getActionCommand() == "Save" ) {
					file = new File( tab.getPath() + "\\", tab.getFileName() + ".txt" );

					if ( index != -1 ) {

						String textToSave = tab.getText();

						if ( fileIO.saveAs( file, textToSave ) ) {

						} else {
							// something went wrong
						}

					}
				} else if ( e.getActionCommand() == "Save As" ) {
					JFileChooser fileChooser = new JFileChooser();
					if ( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {// if a file is selected
						String fileName = fileChooser.getSelectedFile().getName();
						String path = fileChooser.getCurrentDirectory().toString();
						String lastFour = fileName.substring( Math.max( 0, fileName.length() - 4 ) );

						file = new File( path + "\\" + fileName + ".txt" );
						tab.setFileName( fileName );
						tab.setPath( path );
						textEditor.updateTab( fileName, path, index );

					}

					if ( index != -1 ) {//if a tab is selected

						String textToSave = tab.getText();

						if ( !file.exists() ) {// if no file exists, write it
							try {
								file.createNewFile();
							} catch ( IOException e1 ) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if ( fileIO.saveAs( file, textToSave ) ) {

							} else {
								// something went wrong
							}
						} else {
							int response = JOptionPane.showConfirmDialog( null, "Do you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );

							if ( response == JOptionPane.YES_OPTION ) {
								if ( fileIO.saveAs( file, textToSave ) ) {

								} else {
									// something went wrong
								}

							}

						}
					}

				}

			}

		}
	}

}
