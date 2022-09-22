import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;

public class Demo {
    public static BufferedImage solidSetRaster(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();

        final WritableRaster raster = img.getRaster();
        final int[] pixel = new int[3]; // RGB

        for (int y = 0; y < height; y++) {
            int val = (int) (y * 255f / height);
            for (int shift = 1; shift < 3; shift++) {
                pixel[shift] = val;
            }

            for (int x = 0; x < width; x++) {
                raster.setPixel(x, y, pixel);
            }
        }

        return img;
    }

    public static void main(String... args) {
        // create new frame
        final Frame w = new Frame("Raster");

        // define image dimensions
        final int imageWidth = 500;
        final int imageHeight = 500;
        w.setSize(imageWidth, imageHeight);
        w.setLocation(100, 100);
        w.setVisible(true);

        // get graphics
        final Graphics g = w.getGraphics();

        // create new buffered image
        final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        solidSetRaster(image);

        // draw the image
        g.drawImage(image, 0, 0, (img1, infoflags, x, y, width, height) -> false);

        // create new polygone
        final Polygon polygon = new Polygon();

        // mouseListener
        w.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // mouseClicked
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // in case of a left click add point
                    polygon.addPoint(new Point(e.getX(), e.getY()));

                    // drawOpen (draw a polyline)
                    polygon.drawOpen(image);

                    // draw image
                    g.drawImage(image, 0, 0, (img1, infoflags, x, y, width, height) -> false);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // in case of a right click

                    // drawClosed (draw a polyline)
                    polygon.drawClosed(image);

                    // draw the image
                    g.drawImage(image, 0, 0, (img1, infoflags, x, y, width, height) -> false);
                }
            }
        });

        // keyListener
        w.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // keyPressed
                final int keyCode = e.getKeyCode();
                switch (keyCode) {
                    // polygon movement
                    case KeyEvent.VK_LEFT:
                        // translate left
                        polygon.translate(-5, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        // translate right
                        polygon.translate(5, 0);
                        break;
                    case KeyEvent.VK_UP:
                        // translate up
                        polygon.translate(0, -5);
                        break;
                    case KeyEvent.VK_DOWN:
                        // translate down
                        polygon.translate(0, 5);
                        break;

                    // polygon rotation
                    case KeyEvent.VK_R:
                        // rotate left
                        polygon.rotate(-5);
                        break;
                    case KeyEvent.VK_T:
                        // rotate right
                        polygon.rotate(5);
                        break;

                    // polygon scaling
                    case KeyEvent.VK_D:
                        // scale down
                        polygon.scale(1.1f, 1.1f);
                        break;
                    case KeyEvent.VK_S:
                        // scale up
                        polygon.scale(0.9f, 0.9f);
                        break;
                    default:
                        // do nothing
                        break;
                }

                // reset
                solidSetRaster(image);
                polygon.drawOpen(image);
                polygon.drawClosed(image);

                // draw the image
                g.drawImage(image, 0, 0, (img1, infoflags, x, y, width, height) -> false);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // do nothing
            }
        });

        // windowListener
        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // windowClosing
                w.dispose();
                g.dispose();
                System.exit(0);
            }
        });
    }
}
