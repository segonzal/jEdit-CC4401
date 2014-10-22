package umlviewer;


import javax.swing.JTextArea;

public class UMLViewerTextArea extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4250092978658924348L;
	public UMLViewerTextArea() {
		super("HelloWorld!");
		setLineWrap(true);
		setWrapStyleWord(true);
		setTabSize(4);
		setEditable(false);
	}
}
