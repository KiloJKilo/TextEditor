package com.kevinjkahl;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.Random;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledEditorKit;

import com.kevinjkahl.text_editor.actions.Actions;
import com.kevinjkahl.text_editor.editors.TextEditorPane;
import com.kevinjkahl.text_editor.editors.RichTextEditor;
import com.kevinjkahl.text_editor.editors.TextEditorWrap;
import com.kevinjkahl.text_editor.mouse_adapters.TabPaneMouseAdapter;

public class TextEditor {

	private JTextPane mainEditorPane;
	private JTabbedPane tabEditorPane;
	private final int frameW = 700;
	private final int frameH = 700;
	private final double leftSplitConstraint = .25;
	private TextEditor textEditor = this;
	private Actions Actions = new Actions( textEditor );
	private Icon newFileIcon = new ImageIcon( getClass().getResource( "/File-New-icon.png" ) );
	private JMenuItem mnFileSave;
	private JMenuItem mnFileSaveAs;
	private short tc = 1;
	private final String appDataPath = System.getenv( "APPDATA" ) + "\\TextEditor\\";

	/**
	 * Constructor. No arguments. Creates the main frame of text editor.
	 */
	public TextEditor() {
		EventQueue.invokeLater( new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
				} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex ) {
					ex.printStackTrace();
				}

				new MainFrame();
			}
		} );
	}

	/**
	 *
	 * This class represents the main frame of the text editor. It sets up the frame, adds a menu bar and menu items. It also has action listeners for the menu. The main frame that houses the
	 * TextEditorBox JPanel.
	 *
	 * @author Kevin
	 */
	@SuppressWarnings ( "serial" )
	private class MainFrame extends JFrame {

		public MainFrame() {

			/* create frame */
			JFrame frame = new JFrame( "Text Editor" );
			frame.setBounds( 100, 100, frameW, frameH );
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

			/* Create the main JPanel called contentPanel that will house our split pane. */
			JPanel contentPanel = new JPanel( new GridLayout( 1, 0, 0, 0 ) );

			/* Create a JSplitPane called splitPlane that will house our JTree and JTabbedPane */
			JSplitPane splitPane = new JSplitPane();

			/* Create a new JTree called dirTree representing our file system */
			JTree dirTree = new JTree();

			/* Create a new JTabbed pane called tabEditorPane */
			tabEditorPane = new JTabbedPane();

			tabEditorPane.addTab( "", newFileIcon, null );
			tabEditorPane.setDisabledIconAt( 0, newFileIcon );
			tabEditorPane.setEnabledAt( 0, false );
			tabEditorPane.getUI();
			tabEditorPane.addMouseListener( new TabPaneMouseAdapter( tabEditorPane, textEditor ) );

			ChangeListener changeListener = new ChangeListener() {

				public void stateChanged( ChangeEvent changeEvent ) {
					JTabbedPane sourceTabbedPane = ( JTabbedPane ) changeEvent.getSource();

					if ( sourceTabbedPane.getSelectedIndex() <= 0 ) {// if the first tab or no tab is selected, save as and save will be deactivated
						mnFileSave.setEnabled( false );
						mnFileSaveAs.setEnabled( false );
					} else {
						mnFileSaveAs.setEnabled( true );
						int index = sourceTabbedPane.getSelectedIndex();
						TextEditorPane tab = ( TextEditorPane ) sourceTabbedPane.getComponentAt( sourceTabbedPane.getSelectedIndex() );
						if ( tab.isSaveEligible() && sourceTabbedPane.getTitleAt( index ).contains( "*" ) ) {// if tab is save eligible and if the tab title has a star, it is savable
							// System.out.println( tab.getPath() + tab.getFileName() );
							mnFileSave.setEnabled( true );
						} else {
							mnFileSave.setEnabled( false );
						}
					}

				}
			};

			tabEditorPane.addChangeListener( changeListener );
			/* Set this Editor Pane inside the actions class */
			Actions.setTabEditorPane( tabEditorPane );

			/* Add the dirTree and tabEditorPane to the splitPane, split horizontally */
			splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, dirTree, tabEditorPane );
			// splitPane.setDividerLocation( 175 );
			splitPane.setDividerLocation( calcSplitWidth() );
			splitPane.setDividerSize( 15 );
			splitPane.setResizeWeight( 0.2 );
			splitPane.setOneTouchExpandable( true );

			/* Add the splitPane to the content panel */
			contentPanel.add( splitPane );

			/* Create a new menu and set it as the menu for the frame */
			frame.setJMenuBar( createMenu( frame ) );

			/* Add the contentPanel to the frame */
			frame.add( contentPanel );

			// frame.pack();
			frame.setLocationRelativeTo( null );
			frame.setVisible( true );
		}
	}

	@SuppressWarnings ( "serial" )
	public class TextEditorPanel extends JPanel implements ActionListener {

		public TextEditorPanel() {
			this.setLayout( new GridLayout( 1, 1 ) );
			JScrollPane scrollPane;
			setMainEditorPane( new TextEditorWrap() );
			scrollPane = new JScrollPane( getMainEditorPane() );
			scrollPane.setViewportView( getMainEditorPane() );
			add( scrollPane );
			// fileIO = new FileIO( getMainEditorPane() );

		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			String event = e.getActionCommand();
			switch ( event ) {
			case "Reverse":
				// txtOutput.setText( reverse( mainEditorPane.getText() ) );
				break;
			case "Vowel Count":
				// txtOutput.setText( vowelCount( mainEditorPane.getText() ) );
				break;
			case "Palindrome":
				// txtOutput.setText( palindrome( mainEditorPane.getText() ) );
				break;
			case "Word Count":
				// txtOutput.setText( wordCount( mainEditorPane.getText() ) );
				break;
			case "Select":

				break;

			default:
				break;
			}

		}
	}

	/**
	 * Method to create menu
	 *
	 * @param frame
	 *            - the main frame
	 * @return menu - the menu created inside created
	 */
	private JMenuBar createMenu( JFrame frame ) {
		/* Create the menu bar */
		JMenuBar mainMenuBar = new JMenuBar();
		/* Create the menus under the main menu bar and nest them */
		JMenu mmFile = new JMenu( "File" );
		JMenu mmDoc = new JMenu( "Document" );
		mainMenuBar.add( mmFile );
		mainMenuBar.add( mmDoc );

		/* Create the menus under file and add them */
		JMenuItem mnFileOpen = new JMenuItem( Actions.new OpenFileAction( "Open", tabEditorPane ) );// create open menu item
		mnFileOpen.setText( "Open" );
		mmFile.add( mnFileOpen );// nest it under file

		mnFileSaveAs = new JMenuItem( Actions.new SaveFileAction( tabEditorPane ) );// create the save menu item
		mnFileSaveAs.setText( "Save As" );
		mmFile.add( mnFileSaveAs );
		mnFileSaveAs.setEnabled( false );

		mnFileSave = new JMenuItem( Actions.new SaveFileAction( tabEditorPane ) );// create the save menu item
		mnFileSave.setText( "Save" );
		mmFile.add( mnFileSave );
		mnFileSave.setEnabled( false );

		/* Create the menus under document and add them */
		JMenuItem mnDocPText = new JMenuItem( Actions.new NewFileAction( "New Plain Text" ) );
		mmDoc.add( mnDocPText );

		JMenuItem mnDocRText = new JMenuItem( Actions.new NewFileAction( "New Rich Text" ) );
		mmDoc.add( mnDocRText );

		// JMenuItem mntmNew = new JMenuItem( new NewFileAction( "New" ) );// create the save menu item
		// ***JMenuItem mntmNew = new JMenuItem( menuActions.new NewFileAction( "New" ) );// create the save menu item
		// mntmSave.addActionListener();
		// mnFile.add( mntmNew );

		JMenuItem mntmClose = new JMenuItem( "Close" );// create the save menu item
		mnFileSave.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {

			}

		} );
		mmFile.add( mntmClose );

		JMenu menuStyle = new JMenu( "Style" );

		Action action = new StyledEditorKit.BoldAction();
		action.putValue( Action.NAME, "Bold" );
		menuStyle.add( action );

		action = new StyledEditorKit.ItalicAction();
		action.putValue( Action.NAME, "Italic" );
		menuStyle.add( action );

		action = new StyledEditorKit.UnderlineAction();
		action.putValue( Action.NAME, "Underline" );
		menuStyle.add( action );

		menuStyle.addSeparator();

		menuStyle.add( new StyledEditorKit.FontSizeAction( "12", 12 ) );
		menuStyle.add( new StyledEditorKit.FontSizeAction( "14", 14 ) );
		menuStyle.add( new StyledEditorKit.FontSizeAction( "18", 18 ) );

		menuStyle.addSeparator();

		menuStyle.add( new StyledEditorKit.FontFamilyAction( "Serif", "Serif" ) );
		menuStyle.add( new StyledEditorKit.FontFamilyAction( "SansSerif", "SansSerif" ) );

		menuStyle.addSeparator();

		menuStyle.add( new StyledEditorKit.ForegroundAction( "Red", Color.red ) );
		menuStyle.add( new StyledEditorKit.ForegroundAction( "Green", Color.green ) );
		menuStyle.add( new StyledEditorKit.ForegroundAction( "Blue", Color.blue ) );
		menuStyle.add( new StyledEditorKit.ForegroundAction( "Black", Color.black ) );

		mainMenuBar.add( menuStyle );

		return mainMenuBar;
	}

	public JTextPane getMainEditorPane() {
		return mainEditorPane;
	}

	public void setMainEditorPane( JTextPane mainEditorPane ) {
		this.mainEditorPane = mainEditorPane;
	}

	/**
	 * method to calculate the width in pixels, that is 25 percent the frame
	 *
	 * @return int pixel width of 25% of frame.
	 */
	private int calcSplitWidth() {

		return ( int ) ( getFrameW() * leftSplitConstraint );

	}

	public int getFrameW() {
		return frameW;
	}

	public int getFrameH() {
		return frameH;
	}

	/**
	 * Method to create a new tab with content (such as opening a text file).
	 *
	 * @param br
	 *            the content gathered from file
	 * @param path
	 *            the string representation of the directory of the file
	 * @param fileName
	 *            the string representation of the file name
	 */
	public TextEditorPane newContentTab( BufferedReader br, String path, String fileName, String fileType ) {
		Color color[] = randomColor();
		TextEditorPane tab = null;

		tab = new TextEditorWrap( br, path, fileName, color[ 0 ], true );

		tabEditorPane.addTab( tab.getFileName(), tab );

		/* Move to the just created tab */
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( fileName ) );

		/* Set tooltip text and color */
		tabEditorPane.setToolTipTextAt( tabEditorPane.indexOfTab( fileName ), path );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( fileName ), color[ 1 ] );

		return tab;

	}

	/**
	 * Method to create a new tab with content (such as opening a text file).
	 *
	 * @param fis
	 *            the content gathered from file
	 * @param path
	 *            the string representation of the directory of the file
	 * @param fileName
	 *            the string representation of the file name
	 * @throws BadLocationException 
	 */
	public TextEditorPane newContentTab( FileInputStream fis, String path, String fileName, String fileType ) throws BadLocationException {
		Color color[] = randomColor();
		TextEditorPane tab = null;

		tab = new RichTextEditor( fis, path, fileName, color[ 0 ], true );

		tabEditorPane.addTab( tab.getFileName(), tab );

		/* Move to the just created tab */
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( fileName ) );

		/* Set tooltip text and color */
		tabEditorPane.setToolTipTextAt( tabEditorPane.indexOfTab( fileName ), path );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( fileName ), color[ 1 ] );

		return tab;

	}

	public TextEditorPane newBlankTab( String extension ) {
		Color color[] = randomColor();

		/* Create a new instance of an editor tab */
		String title = "Untitled" + tc;
		TextEditorPane tab;

		switch ( extension ) {
		case ".rtf":
			tab = new RichTextEditor( title, appDataPath, color[ 0 ] );
			break;
		case ".txt":
		default:
			tab = new TextEditorWrap( title, appDataPath, color[ 0 ] );
			break;
		}

		tabEditorPane.add( title, tab );
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( title ) );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( title ), color[ 1 ] );
		tabEditorPane.getComponentAt( tabEditorPane.indexOfTab( title ) ).requestFocus();// give the child component the focus
		tc++;
		return tab;

	}

	public TextEditorPane fillExisitingTab( String path, String fileName, String fileType, String textToSave ) {
		Color color[] = randomColor();
		int index = tabEditorPane.getSelectedIndex();
		TextEditorPane listener = ( TextEditorPane ) tabEditorPane.getComponentAt( index );
		// listener.getDocument().removeDocumentListener( listener.getDocListener() );
		// tabEditorPane.remove( index );
		TextEditorPane tab = null;

		switch ( fileType ) {
		case "text/rtf":
			tab = new RichTextEditor( textToSave, path, fileName, color[ 0 ], true );
			break;
		case "text/plain":
			tab = new TextEditorWrap( textToSave, path, fileName, color[ 0 ], true );
		default:
			break;
		}

		tabEditorPane.addTab( tab.getFileName(), tab );

		/* Move to the just created tab */
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( fileName ) );
		tab.setText( textToSave );
		/* Set tooltip text and color */
		tabEditorPane.setToolTipTextAt( tabEditorPane.indexOfTab( fileName ), path );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( fileName ), color[ 1 ] );

		return tab;
	}

	public Color[] randomColor() {
		Color[] color = new Color[ 2 ];
		Random random = new Random();
		final float hue = random.nextFloat();
		final float textBorderSaturation = 0.9f;// 1.0 for brilliant, 0.0 for dull
		final float tabBRSaturation = 0.3f;// 1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; // 1.0 for brighter, 0.0 for black
		color[ 0 ] = Color.getHSBColor( hue, textBorderSaturation, luminance );
		color[ 1 ] = Color.getHSBColor( hue, tabBRSaturation, luminance );
		return color;

	}

	public void updateTab( String fileName, String path, int index ) {

		tabEditorPane.setTitleAt( index, fileName );
		tabEditorPane.setToolTipTextAt( index, path );

	}

	public void asteriskManagement( String name, String state ) {

		int index = tabEditorPane.getSelectedIndex();
		TextEditorPane tab = ( TextEditorPane ) tabEditorPane.getComponentAt( index );
		System.out.println( tab.getFileName() );

		/* We do not want to add an asterisk to an untitled unsaved tab */
		if ( tab.getFileName().contains( "Untitled" ) ) {
			// do nothing
		} else if ( state == "saved" ) {
			// tabEditorPane.setTitleAt( index, tab.getFileName() );
			mnFileSave.setEnabled( false );
		} else if ( state == "update" ) {
			String newTitle = "*" + tab.getFileName();
			tabEditorPane.setTitleAt( index, newTitle );
			mnFileSave.setEnabled( true );
		}

	}

	class MyDocumentListener implements DocumentListener {

		// final String newline = "\n";
		int count;

		public MyDocumentListener() {
			this.count = 0;
		}

		public void changedUpdate( DocumentEvent documentEvent ) {
			printIt( documentEvent );
		}

		public void insertUpdate( DocumentEvent documentEvent ) {
			printIt( documentEvent );
		}

		public void removeUpdate( DocumentEvent documentEvent ) {
			printIt( documentEvent );
		}

		private void printIt( DocumentEvent documentEvent ) {
			DocumentEvent.EventType type = documentEvent.getType();

			if ( count < 1 ) {
				count++;
			} else {
				asteriskManagement( documentEvent.getDocument().getProperty( "file name" ).toString(), "update" );
			}
			// String typeString = null;

			// System.out.println( documentEvent.getDocument().getProperty( "file name" ) );

			if ( type.equals( DocumentEvent.EventType.CHANGE ) ) {
				// typeString = "Change";
			} else if ( type.equals( DocumentEvent.EventType.INSERT ) ) {
				// typeString = "Insert";
			} else if ( type.equals( DocumentEvent.EventType.REMOVE ) ) {
				// typeString = "Remove";
			}
			// System.out.print("Type : " + typeString);
			// Document source = documentEvent.getDocument();
			// int length = source.getLength();
			// System.out.println("Length: " + length);
		}

		// public void updateLog(DocumentEvent e, String action) {
		// Document doc = (Document)e.getDocument();
		// int changeLength = e.getLength();
		// displayArea.append(
		// changeLength + " character"
		// + ((changeLength == 1) ? " " : "s ")
		// + action + " " + doc.getProperty("name") + "."
		// + newline
		// + "  Text length = " + doc.getLength() + newline);
		// displayArea.setCaretPosition(displayArea.getDocument().getLength());
		// }

	}

	public void registerDocListener( TextEditorPane ep ) {
		TextEditorPane tab = ( TextEditorPane ) tabEditorPane.getComponentAt( tabEditorPane.indexOfTab( ep.getFileName() ) );
		MyDocumentListener dl = new MyDocumentListener();
		tab.getDocument().addDocumentListener( dl );
		tab.setDocListener( dl );
	}

}
