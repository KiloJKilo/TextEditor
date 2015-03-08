

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;


public class TextEditorPane extends JTextPane {

	public TextEditorPane() {
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