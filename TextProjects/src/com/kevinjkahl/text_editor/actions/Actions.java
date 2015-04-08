package com.kevinjkahl.text_editor.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.SwitchPoint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;

import com.kevinjkahl.TextEditor;
import com.kevinjkahl.text_editor.editors.TextEditorPane;
import com.kevinjkahl.text_editor.editors.RichTextEditor;
import com.kevinjkahl.text_editor.editors.TextEditorWrap;
import com.kevinjkahl.text_editor.fileIO.FileIO;

public class Actions {

	private TextEditor textEditor;
	private JTabbedPane tabEditorPane;
	private final FileNameExtensionFilter textFilter = new FileNameExtensionFilter( "Text Based Files (*.txt, *.htm, *.html, *.htm, *.php)", "txt", "htm", "html", "php" );
	private final FileNameExtensionFilter richFilter = new FileNameExtensionFilter( "Rich Text (*.rtf)", "rtf" );

	private static FileIO fileIO = new FileIO();

	/**
	 * Constructor
	 * 
	 * @param textEditor
	 */
	public Actions( TextEditor textEditor ) {
		this.textEditor = textEditor;

	}

	public class OpenFileAction extends AbstractAction {

		private JTabbedPane tabbedPane;

		public OpenFileAction( String name, JTabbedPane tabbedPane  ) {
			super( name );
			this.tabbedPane = tabbedPane;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			
			final int index = tabbedPane.getSelectedIndex();
			TextEditorPane tab = ( TextEditorPane ) tabbedPane.getComponentAt( index );
			TextEditorWrap teWrap = (TextEditorWrap) tabbedPane.getComponentAt(index);
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter( textFilter );
			fileChooser.addChoosableFileFilter( richFilter );
			File file;
			String fileType = null;
			TextEditorPane ep = null;
			//Object stream;

			if ( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {// if a file is selected
				file = fileChooser.getSelectedFile();

				//Object stream = fileIO.open( file );
				
				Path target = Paths.get( file.getAbsolutePath() );
				try {
					fileType = Files.probeContentType( target );
				} catch ( IOException e2 ) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				switch ( fileType ) {
				case "text/richtext":
				case "application/msword":
					RichTextEditor rte = ( RichTextEditor ) tabbedPane.getComponentAt( index );
					FileInputStream fis = RichTextEditor.open( file );
					try {
						ep = textEditor.newContentTab( fis, file.getPath(), file.getName(), fileType );
					} catch ( BadLocationException e1 ) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "text/plain":
				default:
					TextEditorWrap tew = ( TextEditorWrap ) tabbedPane.getComponentAt( index );
					BufferedReader br = tew.open( file );
					ep = textEditor.newContentTab( br, file.getPath(), file.getName(), fileType );
					break;
				}

				
				textEditor.registerDocListener( ep );

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

				TextEditorPane tab = ( TextEditorPane ) tabbedPane.getComponentAt( index );

				if ( e.getActionCommand() == "Save" ) {
					file = new File( tab.getPath() );

					if ( index != -1 ) {

						String textToSave = tab.getText();

						if ( fileIO.saveAs( file, textToSave, tab ) ) {
							textEditor.asteriskManagement( tab.getFileName(), "saved" );
							textEditor.updateTab( tab.getFileName(), tab.getPath(), index );
						} else {
							// something went wrong
						}

					}
				} else if ( e.getActionCommand() == "Save As" ) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.addChoosableFileFilter( richFilter );
					fileChooser.addChoosableFileFilter( textFilter );
					
					switch ( tab.getContentType() ) {
					case "text/rtf":
						fileChooser.setFileFilter( richFilter );
						break;
					case "text/plain":	
					default:
						fileChooser.setFileFilter( textFilter );
						break;
					}
					

					if ( fileChooser.showSaveDialog( null ) == JFileChooser.APPROVE_OPTION ) {// if a file is selected
						// String fileName = fileChooser.getSelectedFile().getName();
						file = getSelectedFileWithExtension( fileChooser );
						String path = file.getPath();
						// file = new File( path );

						Path target = Paths.get( file.getAbsolutePath() );

						// tab.setFileName( fileName );
						// tab.setPath( path );
						// textEditor.updateTab( fileName, path, index );

						String textToSave = tab.getText();

						if ( !file.exists() ) {// if no file exists, write it
							try {
								file.createNewFile();
							} catch ( IOException e1 ) {
								e1.printStackTrace();
							}

							if ( fileIO.saveAs( file, textToSave, tab ) ) {
								TextEditorPane ep = textEditor.fillExisitingTab( file.getPath(), file.getName(), tab.getContentType(), textToSave );
								textEditor.registerDocListener( ep );

								textEditor.asteriskManagement( tab.getFileName(), "saved" );
							} else {
								// something went wrong
							}
						} else {
							int response = JOptionPane.showConfirmDialog( null, "Do you wish to overwrite?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );

							if ( response == JOptionPane.YES_OPTION ) {
								if ( fileIO.saveAs( file, textToSave, tab ) ) {
									/* create a new tab with the file type that was just selected */
									try {
										TextEditorPane ep = textEditor.fillExisitingTab( file.getPath(), file.getName(), Files.probeContentType( target ), textToSave );
										textEditor.registerDocListener( ep );
									} catch ( IOException e1 ) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									textEditor.asteriskManagement( tab.getFileName(), "saved" );
								} else {
									// something went wrong
								}

							}

						}

					} else {
						// did not select anything
					}

				}

			}

		}

		public File getSelectedFileWithExtension( JFileChooser c ) {
			File file = c.getSelectedFile();
			if ( c.getFileFilter() instanceof FileNameExtensionFilter ) {
				String[] exts = ( ( FileNameExtensionFilter ) c.getFileFilter() ).getExtensions();
				String nameLower = file.getName().toLowerCase();
				for ( String ext : exts ) { // check if it already has a valid extension
					if ( nameLower.endsWith( '.' + ext.toLowerCase() ) ) {
						return file; // if yes, return as-is
					}
				}
				// if not, append the first extension from the selected filter
				file = new File( file.toString() + '.' + exts[ 0 ] );
			}
			return file;
		}
	}

	public class NewFileAction extends AbstractAction {

		public NewFileAction( String name ) {
			super( name );
		}

		@Override
		public void actionPerformed( ActionEvent e ) {

			switch ( e.getActionCommand() ) {
			case "New Plain Text":
				textEditor.newBlankTab( ".txt" );
				// textEditor.registerDocListener( ep );
				break;
			case "New Rich Text":
				textEditor.newBlankTab( ".rtf" );
			default:
				break;
			}

		}

	}

	public void setTabEditorPane( JTabbedPane tabEditorPane ) {
		this.tabEditorPane = tabEditorPane;
	}

}
