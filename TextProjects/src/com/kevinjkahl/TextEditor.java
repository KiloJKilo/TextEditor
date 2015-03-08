package com.kevinjkahl;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ComponentUI;

import com.kevinjkahl.text_editor.fileIO.FileIO;

public class TextEditor {

	private static TextEditor TextEditor;
	private JPanel contentPane;
	private JTextPane mainEditorPane;
	private static FileIO fileIO;

	public static void main( String[] args ) {
		new TextEditor();
		
		

	}

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
			super( "Text Editor" );

			/* create frame */
			JFrame frame = new JFrame( "Text" );
			frame.setBounds( 100, 100, 316, 300 );
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

			/* create content pane and add the call the method TextEditorBox to create or editor pane, and then add it into contentPane */
			contentPane = new TextEditorPanel();
			frame.setJMenuBar( createMenu( frame ) );
			frame.add( contentPane );
			
			

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
			setMainEditorPane( new TextEditorPaneS() );
			scrollPane = new JScrollPane( getMainEditorPane() );
			scrollPane.setViewportView( getMainEditorPane() );
			add( scrollPane );
			fileIO = new FileIO( getMainEditorPane() );

			// // text field that displays output
			// // txtOutput = new JTextField();
			// txtOutput = new JTextArea();
			// add( txtOutput );
			// txtOutput.setLineWrap( true );
			// txtOutput.setBorder( BorderFactory.createLineBorder( Color.BLUE, 2 ) );

			// JButton btnReverse = new JButton( "Reverse" );
			// add( btnReverse );
			// btnReverse.addActionListener( this );
			//
			// JButton btnVowelCount = new JButton( "Vowel Count" );
			// add( btnVowelCount );
			// btnVowelCount.addActionListener( this );
			//
			// JButton btnPalindrome = new JButton( "Palindrome" );
			// add( btnPalindrome );
			// btnPalindrome.addActionListener( this );
			//
			// JButton btnWordCount = new JButton( "Word Count" );
			// add( btnWordCount );
			// btnWordCount.addActionListener( this );
			//
			// JButton btnOpen = new JButton( "Select" );
			// add( btnOpen );
			// btnOpen.addActionListener( this );
		}

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

	
	
	public class TextEditorPaneS extends JTextPane {

		public TextEditorPaneS() {
			super();
			this.setBorder( BorderFactory.createLineBorder( Color.green, 2 ) );
		}
		
		
		@Override
		public boolean getScrollableTracksViewportWidth() {
			Component parent = getParent();
			ComponentUI ui = getUI();

			return parent != null ? ( ui.getPreferredSize( this ).width <= parent.getSize().width ) : true;
		}
	
	}
	
	

	private String reverse( String string ) {
		// txtInput.setText( "" );
		StringBuffer s = new StringBuffer( string );
		// s.append( string );
		return s.reverse().toString();

	}

	private String vowelCount( String string ) {
		// txtInput.setText( "" );

		short a = 0;
		short e = 0;
		short i = 0;
		short o = 0;
		short u = 0;

		string = string.toLowerCase();

		for ( int c = 0; c < string.length(); c++ ) {

			switch ( string.charAt( c ) ) {
			case 'a':
				a++;
				break;
			case 'e':
				e++;
				break;
			case 'i':
				i++;
				break;
			case 'o':
				o++;
				break;
			case 'u':
				u++;
				break;
			default:
				break;
			}

		}

		int[] vowels = new int[ 5 ];
		vowels[ 0 ] = a;
		vowels[ 1 ] = e;
		vowels[ 2 ] = i;
		vowels[ 3 ] = o;
		vowels[ 4 ] = u;

		int vowelTotal = a + e + i + o + u;

		String vowelCount = "";

		vowelCount = "The total occurences of A is: " + a + ". The total occurences of B is: " + e + ". The Total occurences of I is: " + i + ". The total occureces of O is:" + o
				+ ". The total occureces of U is: " + u + ". Giving us " + vowelTotal + " total occureces of vowels.";
		return vowelCount;

	}

	private String palindrome( String string ) {
		StringBuffer s = new StringBuffer( string );
		String reversed = s.reverse().toString();

		if ( string.equals( reversed ) ) {
			return "This is a palindrome!";
		} else {
			return "This is not a palindrome";
		}

	}

	private String wordCount( String string ) {
		String trimmed = string.trim();
		int words = trimmed.isEmpty() ? 0 : trimmed.split( "\\s+" ).length;

		return words + " word(s) entered.";
	}

	
	/**
	 * Method to create menu
	 * 
	 * @param frame
	 *            - the main frame
	 * @return menu - the menu created inside created
	 */
	private JMenuBar createMenu( JFrame frame ) {

		JMenuBar menuBar = new JMenuBar(); // create menu bar called menuBar

		JMenu mnFile = new JMenu( "File" );// create menu file
		menuBar.add( mnFile );// nest it under menu bar

		JMenuItem mntmOpen = new JMenuItem( "Open" );// create open menu item
		mntmOpen.addActionListener( new ActionListener() {

			public void actionPerformed( ActionEvent arg0 ) {
				fileIO.open(mainEditorPane);
			}
		} );

		mnFile.add( mntmOpen );// nest it under file
		JMenuItem mntmSave = new JMenuItem( "Save" );// create the save menu item
		mntmSave.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				fileIO.save(mainEditorPane);

			}
		} );
		mnFile.add( mntmSave );

		JMenuItem mntmClose = new JMenuItem( "Close" );// create the save menu item
		mntmSave.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				close();

			}

		} );
		mnFile.add( mntmClose );

		return menuBar;
	}

	private void close() {
		// txaMainEditor.

	}

	public JTextPane getMainEditorPane() {
		return mainEditorPane;
	}

	public void setMainEditorPane( JTextPane mainEditorPane ) {
		this.mainEditorPane = mainEditorPane;
	}
}
