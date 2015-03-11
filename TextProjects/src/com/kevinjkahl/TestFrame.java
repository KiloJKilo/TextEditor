package com.kevinjkahl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.JSplitPane;

public class TestFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private final Action action = new SwingAction();

	/**
	 * Create the frame.
	 */
	public TestFrame() {
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//setBounds( 100, 100, 450, 300 );

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar( menuBar );

		JMenu mnFile = new JMenu( "File" );
		menuBar.add( mnFile );

		JMenuItem mntmOpen = new JMenuItem( "Open" );
		mnFile.add( mntmOpen );

		JMenuItem menuItem = mnFile.add( action );
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( contentPane );
		contentPane.setLayout( new GridLayout( 1, 0, 0, 0 ) );

		/* create frame */
		JFrame frame = new JFrame( "Text Editor" );
		frame.setBounds( 100, 100, 750, 750 );
		//frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		/* Create the main JPanel called contentPanel that will house our split pane. */
		JPanel contentPanel = new JPanel( new GridLayout( 1, 0, 0, 0 ) );

		/* Create a JSplitPane called splitPlane that will house our JTree and JTabbedPane */
		JSplitPane splitPane = new JSplitPane();

		/* Create a new JTree called dirTree representing our file system */
		JTree dirTree = new JTree();

		/* Create a new JTabbed pane called tabEditorPane */
		JTabbedPane tabEditorPane = new JTabbedPane();
		tabEditorPane.addTab( "Double Click to Create New", null );

		/* Add the dirTree and tabEditorPane to the splitPane, split horizontally */
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, dirTree, tabEditorPane );
		// splitPane.setDividerLocation( 175 );
		splitPane.setDividerSize( 15 );
		splitPane.setResizeWeight( 0.2 );
		splitPane.setOneTouchExpandable( true );

		/* Add the splitPane to the content panel */
		contentPanel.add( splitPane );

		/* Create a new menu and set it as the menu for the frame */
		//frame.setJMenuBar( createMenu( frame ) );

		/* Add the contentPanel to the frame */
		frame.add( contentPanel );

	}

	}
