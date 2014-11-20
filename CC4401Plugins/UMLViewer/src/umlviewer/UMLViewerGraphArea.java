package umlviewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UMLViewerGraphArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resource = "/img/hats.png";
	private File png = null;
	private boolean generated = false;

	public UMLViewerGraphArea() {
		super();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// g2.drawString("Gracias por usar UMLViewer", 10, 20);
		// FontMetrics metrics = g2.getFontMetrics(g2.getFont());
		try {
			BufferedImage img = null;
			if (!generated)
				img = ImageIO.read(getClass().getResource(resource));
			else
				img = ImageIO.read(png);
			g2.drawImage(img, 20, 20, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		/*
		 * Rectangle2D rect1 =
		 * g2.getFont().getStringBounds("Gracias por usar UMLViewer",
		 * g2.getFontRenderContext()); rect1.setRect(rect1.getX()-2,
		 * rect1.getY()-2, rect1.getWidth()+4, rect1.getHeight()+4);
		 * 
		 * g2.draw(rect1);
		 */
	}

	public void setResource(File source) {
		this.png = source;
		this.generated = true;
		repaint();
	}

}
