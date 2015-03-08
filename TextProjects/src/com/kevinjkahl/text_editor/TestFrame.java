package com.kevinjkahl.text_editor;

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
		setBounds( 100, 100, 450, 300 );

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

		JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, new JTree(), new JTabbedPane( JTabbedPane.TOP ) );
		contentPane.add( splitPane );

		//contentPane.add( tree );

		JTabbedPane tabbedPane = new JTabbedPane( JTabbedPane.TOP );
		tabbedPane.addTab( "Title", new JTextPane() );
		tabbedPane.addTab( "HTML", null, new JTextPane(), "HTML" );
		contentPane.add( tabbedPane );

	}

	private class SwingAction extends AbstractAction {

		public SwingAction() {
			putValue( NAME, "SwingAction" );
			putValue( SHORT_DESCRIPTION, "Some short description" );
		}

		public void actionPerformed( ActionEvent e ) {
		}
	}
}
