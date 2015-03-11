package com.kevinjkahl;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.util.Random;

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

import com.kevinjkahl.text_editor.actions.MenuActions;
import com.kevinjkahl.text_editor.editors.TextEditorWrap;

public class TextEditor {

	// private static TextEditor TextEditor;
	// private JPanel contentPane;
	private JTextPane mainEditorPane;
	private JTabbedPane tabEditorPane;
	private final int frameW = 700;
	private final int frameH = 700;
	private final double leftSplitConstraint = .25;
	private short tc = 1;
	private MenuActions menuActions = new MenuActions( this );
	private Icon newFileIcon = new ImageIcon( getClass().getResource( "/File-New-icon.png" ) );

	// public static void main( String[] args ) {
	// new TextEditor();
	//
	//
	//
	// }

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
			tabEditorPane.addMouseListener( new MouseAdapter() {

				@Override
				public void mousePressed( MouseEvent e ) {
					int button = e.getButton();
					JTabbedPane tabs = ( JTabbedPane ) e.getSource();
					// System.out.println( tabs.findComponentAt( e.getX(), e.getY() ) );
					// System.out.println( tabs.findComponentAt( e.getX(), e.getY() ) == tabs && tabs.indexAtLocation( e.getX(), e.getY() ) == -1 ? "No tab at location."
					// : "Tab at location." );

					if ( button == 0 ) {// no button
						System.out.println( "no button" );
					} else if ( button == 1 ) {// left click
						if ( e.getClickCount() % 2 == 0 ) {
							e.consume();
							System.out.println( "left double click" );

							/* Create a new untitled tab */
							newBlankTab();

						}
					} else if ( button == 2 ) {// wheel click
						System.out.println( "Wheel click" );
						// TODO Implement save and close
					} else if ( button == 3 ) {// right click
						System.out.println( "right click" );

					}

				}

			} );

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

		JMenuBar menuBar = new JMenuBar(); // create menu bar called menuBar

		JMenu mnFile = new JMenu( "File" );// create menu file
		menuBar.add( mnFile );// nest it under menu bar

		JMenuItem mntmOpen = new JMenuItem( menuActions.new OpenFileAction( "Open" ) );// create open menu item
		// mntmOpen.addActionListener( new ActionListener() {
		//
		// public void actionPerformed( ActionEvent arg0 ) {
		// fileIO.open( mainEditorPane );
		// }
		// } );
		//
		mnFile.add( mntmOpen );// nest it under file
		JMenuItem mntmSave = new JMenuItem( menuActions.new OpenFileAction( "Save" ) );// create the save menu item
		// mntmSave.addActionListener( newMenuActions.new NewSaveAction( "Save" ) );

		// mntmSave.addActionListener( new ActionListener() {
		//
		// @Override
		// public void actionPerformed( ActionEvent e ) {
		// fileIO.save( mainEditorPane );
		//
		// }
		// } );
		mnFile.add( mntmSave );

		// JMenuItem mntmNew = new JMenuItem( new NewFileAction( "New" ) );// create the save menu item
		// ***JMenuItem mntmNew = new JMenuItem( menuActions.new NewFileAction( "New" ) );// create the save menu item
		// mntmSave.addActionListener();
		// mnFile.add( mntmNew );

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
	 * @param dir
	 *            the string representation of the directory of the file
	 * @param fileName
	 *            the string representation of the file name
	 */
	public void newContentTab( BufferedReader br, String dir, String fileName ) {
		Color color[] = randomColor();
		/* add a tab with the dir and filename as the title, setting the component as type TextEditorWrap */
		// TODO: do something about creating a new instance of an editor on the fly
		tabEditorPane.addTab( fileName, new TextEditorWrap( br, dir, fileName, color[ 0 ] ) );

		/* Move to the just created tab */
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( fileName ) );
		tabEditorPane.setToolTipTextAt( tabEditorPane.indexOfTab( fileName ), dir );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( fileName ), color[ 1 ] );

	}

	public void newBlankTab() {
		Color color[] = randomColor();
		tabEditorPane.add( "Untitled" + tc, new TextEditorWrap( "Untitled" + tc, color[ 0 ] ) );
		tabEditorPane.setSelectedIndex( tabEditorPane.indexOfTab( "Untitled" + tc ) );
		tabEditorPane.setBackgroundAt( tabEditorPane.indexOfTab( "Untitled" + tc ), color[ 1 ] );
		tc++;

	}

	private Color[] randomColor() {
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

}
