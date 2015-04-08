package com.kevinjkahl.text_editor.mouse_adapters;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.kevinjkahl.TextEditor;
import com.kevinjkahl.text_editor.actions.Actions;
import com.kevinjkahl.text_editor.actions.Actions.NewFileAction;
import com.kevinjkahl.text_editor.editors.TextEditorPane;

public class TabPaneMouseAdapter extends MouseAdapter {

	private JTabbedPane tabbedPane;
	private TextEditor textEditor;

	public TabPaneMouseAdapter( JTabbedPane tabbedPane, TextEditor textEditor ) {
		super();
		this.tabbedPane = tabbedPane;
		this.textEditor = textEditor;
	}

	public void mousePressed( MouseEvent e ) {
		final int index = tabbedPane.getUI().tabForCoordinate( tabbedPane, e.getX(), e.getY() );// get the current index

		if ( index == 0 ) {// the new tab (0) tab is selected
			if ( e.getClickCount() % 2 == 0 ) {// if the user double clicks the left mouse on the first tab (the new tab button)
				// e.consume();
				/* Create a new untitled tab */
				TextEditorPane ep = textEditor.newBlankTab( "text/plain" );
				// textEditor.registerDocListener( ep );
			}

		} else {// zero wasn't selected
			if ( index != -1 ) {// if the index does not equal -1

				if ( SwingUtilities.isLeftMouseButton( e ) ) {// user clicks left mouse

					if ( e.isControlDown() ) {
						TextEditorPane tab = ( TextEditorPane ) tabbedPane.getComponentAt( index );
						// change color
						System.out.println( "color" );
						Color color[] = textEditor.randomColor();
						tabbedPane.setBackgroundAt( index, color[ 1 ] );
						tab.changeBorder( color[ 0 ] );
						
					} else {// control wasn't pressed

						if ( tabbedPane.getSelectedIndex() != index ) {// if the selected index does not equal the index
							tabbedPane.setSelectedIndex( index );
							tabbedPane.getComponentAt( index ).requestFocus();// give the child component the focus
						} else if ( tabbedPane.isRequestFocusEnabled() ) {
							// tabbedPane.requestFocusInWindow();
							System.out.println( "request focus true" );
							tabbedPane.getComponentAt( index ).requestFocus();// give the child component the focus
						}
					}

				} else if ( SwingUtilities.isMiddleMouseButton( e ) ) {
					tabbedPane.removeTabAt( index );
				} else if ( SwingUtilities.isRightMouseButton( e ) ) {
					final JPopupMenu popupMenu = new JPopupMenu();

					final JMenuItem addNew = new JMenuItem( "Add new" );
					addNew.addActionListener( new ActionListener() {

						public void actionPerformed( ActionEvent e ) {
							tabbedPane.addTab( "tab", new JLabel( "" ) );
						}
					} );
					popupMenu.add( addNew );

					final JMenuItem close = new JMenuItem( "Close" );
					close.addActionListener( new ActionListener() {

						public void actionPerformed( ActionEvent e ) {
							tabbedPane.removeTabAt( index );
						}
					} );
					popupMenu.add( close );

					final JMenuItem closeAll = new JMenuItem( "Close all" );
					closeAll.addActionListener( new ActionListener() {

						public void actionPerformed( ActionEvent e ) {
							while ( tabbedPane.getTabCount() > 1 )
								tabbedPane.remove( 1 );

							// tabbedPane.removeAll();
						}
					} );
					popupMenu.add( closeAll );

					final Rectangle tabBounds = tabbedPane.getBoundsAt( index );
					popupMenu.show( tabbedPane, tabBounds.x, tabBounds.y + tabBounds.height );
				}
			}

		}
	}
}