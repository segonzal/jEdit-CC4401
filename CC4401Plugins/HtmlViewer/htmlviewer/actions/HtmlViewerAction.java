package htmlviewer.actions;

import htmlviewer.HtmlViewer;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.DefaultInputHandler;
import org.gjt.sp.jedit.gui.KeyEventTranslator;
import org.gjt.sp.util.Log;


public abstract class HtmlViewerAction extends AbstractAction 
{
	/** Base name for properties */
	String name;

	JToggleButton.ToggleButtonModel toggleModel;
	
	
	// {{{ isToggle() method

	public boolean isToggle()
	{
		return jEdit.getBooleanProperty(name + ".toggle");
	} // }}}

	
	/* public void setSelected(boolean selected) {
		toggleModel.setSelected(selected);
		// jEdit.setBooleanProperty(name + ".selected", selected);
	}
	*/
	public boolean isSelected()
	{
		return toggleModel.isSelected();
//		return jEdit.getBooleanProperty(name + ".selected");
	}

	public JMenuItem menuItem() 
	{
		JMenuItem retval = null;
		if (isToggle()) 
		{

			JCheckBoxMenuItem cmi = new JCheckBoxMenuItem(this);
			cmi.setModel(toggleModel);
			retval = cmi;
		}
		else 
		{
			retval = new JMenuItem(this);
		}
		return retval;
	}

	public static KeyStroke parseKeyStroke(String keyStroke)
	{
		if (keyStroke == null)
			return null;
		int modifiers = 0;
		int index = keyStroke.indexOf('+');
		if (index != -1)
		{
			for (int i = 0; i < index; i++)
			{
				switch (Character.toUpperCase(keyStroke.charAt(i)))
				{
				case 'A':
					modifiers |= InputEvent.ALT_MASK;
					break;
				case 'C':
					modifiers |= InputEvent.CTRL_MASK;
					break;
				case 'M':
					modifiers |= InputEvent.META_MASK;
					break;
				case 'S':
					modifiers |= InputEvent.SHIFT_MASK;
					break;
				}
			}
		}
		String key = keyStroke.substring(index + 1);
		if (key.length() == 1)
		{
			char ch = key.charAt(0);
			if (modifiers == 0)
				return KeyStroke.getKeyStroke(ch);
			else
			{
				return KeyStroke.getKeyStroke(Character.toUpperCase(ch), modifiers);
			}
		}
		else if (key.length() == 0)
		{
			Log.log(Log.ERROR, DefaultInputHandler.class, "Invalid key stroke: "
				+ keyStroke);
			return null;
		}
		else
		{
			int ch;

			try
			{
				ch = KeyEvent.class.getField("VK_".concat(key)).getInt(null);
			}
			catch (Exception e)
			{
				Log.log(Log.ERROR, DefaultInputHandler.class,
					"Invalid key stroke: " + keyStroke);
				return null;
			}

			return KeyStroke.getKeyStroke(ch, modifiers);
		}
	}

	/**
	 * Creates a new <code>HtmlViewerAction</code>. This constructor
	 * should be used by HtmlViewer's own actions only.
	 * 
	 * @param name_key
	 *                a jEdit property with the name for the action. Other
	 *                resources are determined by looking up the following
	 *                keys in the jEdit properties:
	 *                <ul>
	 *                <li><code>name.icon</code> the icon filename</li>
	 *                <li><code>name.description</code> a short
	 *                description</li>
	 *                <li><code>name.mnemonic</code> a menu mnemonic</li>
	 *                <li><code>name.shortcut</code> an keybord shortcut</li>
	 *                </ul>
	 * @see java.awt.KeyStroke#getKeyStroke
	 */
	HtmlViewerAction(String name_key)
	{
		super(jEdit.getProperty(name_key));
		name = name_key;

		if (isToggle()) { 
			toggleModel = new ToggleButtonModel();
			toggleModel.setSelected(true);
		}
		
		String icon = jEdit.getProperty(name_key + ".icon");
		String desc = jEdit.getProperty(name_key + ".description");
		String mnem = jEdit.getProperty(name_key + ".mnemonic");
		String shrt = jEdit.getProperty(name_key + ".shortcut");
		String label =  jEdit.getProperty(name_key + ".label") ;

		
		
		if (icon != null)
		{
			Icon i = GUIUtilities.loadIcon(icon);
			if (i != null)
				putValue(SMALL_ICON, i);
		}

		if (desc != null)
		{
			putValue(SHORT_DESCRIPTION, desc);
			putValue(LONG_DESCRIPTION, desc);
		}

		if (label != null) {
			putValue(NAME, label);
		}
		
		if (mnem != null)
			putValue(MNEMONIC_KEY, new Integer(mnem.charAt(0)));

		if (shrt != null)
		{
			KeyStroke keyStroke = parseKeyStroke(shrt);
			putValue(ACCELERATOR_KEY, keyStroke);
		}
	}

	/**
	 * Determines the HtmlViewer to use for the action.
	 */
	public static HtmlViewer getViewer(EventObject evt)
	{
		if (evt == null)
			return null; // this shouldn't happen

		Object o = evt.getSource();
		if (o instanceof Component)
			return getViewer((Component) o);
		else
			return null;
	}

	/**
	 * Finds the HtmlViewer parent of the specified component.
	 */
	public static HtmlViewer getViewer(Component comp)
	{
		for (;;)
		{
			if (comp instanceof HtmlViewer)
				return (HtmlViewer) comp;
			else if (comp instanceof JPopupMenu)
				comp = ((JPopupMenu) comp).getInvoker();
			else if (comp != null)
				comp = comp.getParent();
			else
				break;
		}
		return null;
	}

	/**
	 * Finds the Frame parent of the source component of the given
	 * EventObject.
	 */
	public static Frame getFrame(EventObject evt)
	{
		if (evt == null)
			return null; // this shouldn't happen

		Object source = evt.getSource();

		if (source instanceof Component)
		{
			Component comp = (Component) source;
			for (;;)
			{
				if (comp instanceof Frame)
					return (Frame) comp;
				else if (comp instanceof JPopupMenu)
					comp = ((JPopupMenu) comp).getInvoker();
				else if (comp != null)
					comp = comp.getParent();
				else
					break;
			}
		}

		return null;
	}

}
