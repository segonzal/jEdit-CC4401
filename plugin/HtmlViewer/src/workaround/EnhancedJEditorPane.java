package plugin.InfoViewer.infoviewer.workaround;

import plugin.InfoViewer.infoviewer.InfoViewer.ArrowKeyHandler;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.accessibility.AccessibleContext;
import javax.swing.JEditorPane;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.MiscUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.util.Log;

/**
 * this is workaround class for JEditorPane. It avoids a bug in accessibility
 * support.
 */
public class EnhancedJEditorPane extends JEditorPane
{
	ArrowKeyHandler handler = null;

	public EnhancedJEditorPane()
	{
		super();
	}

	public EnhancedJEditorPane(String type, String text)
	{
		super(type, text);
	}

	public EnhancedJEditorPane(String url) throws IOException
	{
		super(url);
	}

	public EnhancedJEditorPane(URL initialPage) throws IOException
	{
		super(initialPage);
	}

	protected void ProcessKeyEvent(KeyEvent ke)
	{
		if (handler != null)
			handler.processKeyEvent(ke);
		else 
			super.processKeyEvent(ke);
		
	}

	protected InputStream getStream(URL page) throws IOException
	{
		Buffer buffer = getBuffer(page);

		if (buffer != null)
		{
			String text = buffer.getText(0, buffer.getLength());
			// InputStream in = new
			// java.io.StringBufferInputStream(text);
			InputStream in = new java.io.ByteArrayInputStream(text.getBytes());
			Log.log(Log.DEBUG, this, "getStream(): got stream from jEdit buffer: "
				+ buffer);

			// determine and set content type of buffer:
			// String type = getContentType(in);
			String type = getContentType(page);

			if (type != null)
				setContentType(type);

			return in;
		}

		return super.getStream(page);
	}

	/**
	 * Check whether the url is already opened by jEdit and if true, return
	 * its Buffer.
	 * 
	 * @param page
	 *                the URL.
	 * @return the jEdit buffer, of null, if there is no open buffer with
	 *         this URL.
	 */
	private Buffer getBuffer(URL page)
	{
		String path = page.toString();

		// cut off reference part:
		int pos = path.indexOf('#');
		if (pos >= 0)
			path = path.substring(0, pos);

		// cut off query part:
		pos = path.indexOf('?');
		if (pos >= 0)
			path = path.substring(0, pos);

		// determine protocol:
		String protocol = "file";
		if (MiscUtilities.isURL(path))
		{
			protocol = MiscUtilities.getProtocolOfURL(path);
			if (protocol.equals("file"))
				path = path.substring(5);
		}

		// if file, canonize file url:
		if (protocol.equals("file"))
			path = MiscUtilities.constructPath(null, path);

		return jEdit.getBuffer(path);
	}

	/**
	 * Try to determine the content type of the url.
	 */
	private String getContentType(URL page) throws IOException
	{
		URLConnection conn = page.openConnection();
		String type = conn.getContentType();

		// need to disconnect http connections, because otherwise the
		// URL
		// would be cached somewhere... (dunno why)
		if (conn instanceof HttpURLConnection)
			((HttpURLConnection) conn).disconnect();
		conn = null;

		return type;
	}

	public void setArrowKeyHandler(ArrowKeyHandler akh)
	{
		handler = akh;

	}

	public AccessibleContext getAccessibleContext()
	{
		if (this.accessibleContext == null)
			this.accessibleContext = new MyAccessibleJEditorPaneHTML();
		return this.accessibleContext;
	}

	protected class MyAccessibleJEditorPaneHTML extends JEditorPane.AccessibleJEditorPaneHTML
	{

		public MyAccessibleJEditorPaneHTML()
		{
			super();
		}

		private static final long serialVersionUID = 3215023593258981917L;

	}

	private static final long serialVersionUID = 5302023859973568642L;

}
