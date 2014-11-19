/*
 * jEdit - Programmer's Text Editor
 * :tabSize=8:indentSize=8:noTabs=false:
 * :folding=explicit:collapseFolds=1:
 *
 * Copyright © 2014 jEdit contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package Nav;

import java.awt.Font;

import java.awt.*;
import java.awt.TextArea;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JScrollBar;

import org.gjt.sp.jedit.syntax.SyntaxStyle;
import org.gjt.sp.jedit.textarea.*;

/**
 * Created by luism on 18-10-14.
 */

/**
 * NavTextArea is the class that represents the textArea, where the visualization is embedded
 */
public class NavTextArea extends JEditEmbeddedTextArea
{
	private TextAreaScrollListener textAreaScrollListener;
	private JScrollBar scrollBar;
	private JEditTextArea textArea;
	private MouseMotionListener mouseMotionListener;
	private MouseListener mouseListener;
	private int DragStarLineJEdit;
	private int DragStarLineText;
	private Point dragStart;
	/**
	 * Constructor of the class
	 * @param textArea The panel to which it is associated the plugin.
	 */
	public NavTextArea(JEditTextArea textArea){
		this.textArea = textArea;
		mouseMotionListener=new NavMouseMotionListener();
		mouseListener=new NavMouseListener();
		textAreaScrollListener = new TextAreaScrollListener();
		scrollBar=getScrollBar(this);
		scrollBar.setVisible(false);
		painter.addMouseMotionListener(mouseMotionListener);
		painter.addMouseListener(mouseListener);
		textArea.addScrollListener(textAreaScrollListener);
		getBuffer().setProperty("folding", "explicit");
		setBuffer(textArea.getBuffer());
		getPainter().setCursor(
			Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getPainter().setWrapGuidePainted(false);
		getPainter().setAntiAlias(new AntiAlias(0));
		resizeFont();
		getPainter().setSelectionColor(Color.WHITE);
		addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				setEnabled(true);
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				setEnabled(false);
			}
		});
	}

	/**
	 * Resize the font in the textAre
	 */
	private void resizeFont(){
		TextAreaPainter painter = getPainter();
		Font f = deriveFont(painter.getFont());
		painter.setFont(f);
		SyntaxStyle [] styles = painter.getStyles();
		updateStyles(styles);
	}

	/**
	 * Takes a Font and returns de new font (smaller)
	 * @param f the font of the JeditTextArea
	 * @return The new font for the text Area
	 */
	private static Font deriveFont(Font f) {
		return new Font("Verdana", Font.PLAIN, 7);
	}

	/**
	 * Update the style of the textArea
	 * @param styles, syntax Style of the JEditTextArea
	 */
	private static void updateStyles(SyntaxStyle[] styles) {
		for (int i = 0; i < styles.length; i++) {
			SyntaxStyle style = styles[i];
			styles[i] = new SyntaxStyle(style.getForegroundColor(),
						    style.getBackgroundColor(),
						    deriveFont(style.getFont()));
		}
	}

	/**
	 *
	 * @param container, the place where the scrollBar is search
	 * @return the scrollBar of the textArea if exists
	 */
	private JScrollBar getScrollBar(Container container)
	{
		for (Component comp: container.getComponents()) {
			if (comp instanceof JScrollBar)
				return (JScrollBar) comp;
			if (comp instanceof Container) {
				JScrollBar sb = getScrollBar((Container) comp);
				if (sb != null)
					return sb;
			}
		}
		return null;
	}
	@Override
	/**
	 * Draws a red rectangle in the textArea, the size of the rectangle is the same of JeditTextArea
	 */
	public void paint(Graphics graphics){
		super.paint(graphics);
		//Cantidad de lineas visibles en textArea
		int width = painter.getWidth() - 1;
		int h = painter.getFontMetrics().getHeight();
		int firstPhysicalLine = textArea.getFirstPhysicalLine();
		Point reference = new Point();
		reference = offsetToXY(firstPhysicalLine, 0, reference);
		if (reference == null){
			return;
		}
		int height = textArea.getVisibleLines() * h - 1;
		graphics.setColor(Color.BLUE);
		int y = (int) reference.getY();
		graphics.drawRect(0,y,width,height);//Hay que arreglar esto!! el segundo valor no tiene que ser 0
	}

	/**
	 *
	 * @param line, the new physical line of the textArea
	 */
	private void scrollTextArea(int line){
		int visibleLines = textArea.getVisibleLines();
		int count = textArea.getLineCount();
		if (line > count - visibleLines)
			line = count - visibleLines;
		if (line < 0)
			line = 0;
		textArea.setFirstPhysicalLine(line);
		int first = getFirstPhysicalLine();
		if (line < first)
			setFirstPhysicalLine(line);
		else if (line + visibleLines > getLastPhysicalLine())
			setFirstPhysicalLine(line + visibleLines - getVisibleLines());
		repaint();

	}
	private void scrollToMakeTextAreaVisible() {
		int otherFirst = textArea.getFirstPhysicalLine();
		int thisFirst = getFirstPhysicalLine();
		if (otherFirst < thisFirst)
			setFirstPhysicalLine(otherFirst);
		else {
			int otherLast = otherFirst + textArea.getVisibleLines() - 1;
			int thisLast = thisFirst + getVisibleLines() - 1;
			if (otherLast > thisLast)
				setFirstPhysicalLine(thisFirst + otherLast - thisLast);
		}
		repaint();
	}
	private class TextAreaScrollListener implements ScrollListener
	{
		@Override
		public void scrolledVertically(org.gjt.sp.jedit.textarea.TextArea textArea)
		{
			if (textArea.getBuffer() == getBuffer())
				scrollToMakeTextAreaVisible();
		}

		@Override
		public void scrolledHorizontally(org.gjt.sp.jedit.textarea.TextArea textArea)
		{

		}
	}
	private class NavMouseMotionListener extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent mouseEvent)
		{
			if (dragStart == null)
				return;
			TextAreaPainter painter = getPainter();
			int h = painter.getFontMetrics().getHeight();
			int amount = (int) (mouseEvent.getY() - dragStart.getY()) / h;
			int line = DragStarLineJEdit + getFirstPhysicalLine() - DragStarLineText;
			if (amount < 0)
			{
				for (int i = 0;i<-amount;i++)
				{
					int prevLine = getDisplayManager().getPrevVisibleLine(line);
					if (prevLine != -1)
						line = prevLine;
					else
						break;
				}
			}
			else if (amount > 0)
			{
				for (int i = 0;i<amount;i++)
				{
					int nextLine = getDisplayManager().getNextVisibleLine(line);
					if (nextLine != -1)
						line = nextLine;
					else
						break;
				}
			}
			scrollTextArea(line);
			mouseEvent.consume();
		}
	}
	private class NavMouseListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e){
			if (e.getButton() != MouseEvent.BUTTON1)
				return;
			int offset = xyToOffset(0, e.getY());
			int line = getLineOfOffset(offset);
			if (line > textArea.getLastPhysicalLine() || line < textArea.getFirstPhysicalLine())
			{
				int visibleLines = textArea.getVisibleLines();

				int back = visibleLines >> 1;
				for (int i =0;i<back;i++)
				{
					line = getDisplayManager().getPrevVisibleLine(line);
					if (line == 0)
						break;
				}
				scrollTextArea(line);
			}
			dragStart = e.getPoint();
			DragStarLineJEdit = textArea.getFirstPhysicalLine();
			DragStarLineText = getFirstPhysicalLine();
			e.consume();
		}
		@Override
		public void mouseReleased(MouseEvent e){
			if (e.getButton() != MouseEvent.BUTTON1)
				return;
			if (dragStart != null)
				e.consume();

			dragStart = null;
		}
	}
}
