import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.xml.transform.Templates;

public class Images {

  public static BufferedImage gradientSetRaster(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    WritableRaster raster = img.getRaster();
    int[] pixel = new int[3]; //RGB

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
    Frame w = new Frame("Raster"); //window
    final int imageWidth = 500;
    final int imageHeight = 500;

    w.setSize(imageWidth, imageHeight);
    w.setLocation(100, 100);
    w.setVisible(true);

    Graphics g = w.getGraphics();

    img =
      new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    gradientSetRaster(img);
    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false); //draw the image. You can think of this as the display method.

    w.addMouseListener(
      new MouseAdapter() {
        private int x1 = Integer.MAX_VALUE, y1 = Integer.MAX_VALUE, x2, y2;

        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println(e.getX() + " " + e.getY());
            if (x1 == Integer.MAX_VALUE) {
              x1 = e.getX();
              y1 = e.getY();

              drawPixel(x1, y1);
            } else {
              x2 = e.getX();
              y2 = e.getY();

              drawLine(img, x1, y1, x2, y2);

              x1 = Integer.MAX_VALUE;
              y1 = Integer.MAX_VALUE;
            }

            g.drawImage(
              img,
              0,
              0,
              (img1, infoflags, x, y, width, height) -> false
            );
          }
        }
      }
    );

    w.addWindowListener(
      new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
          w.dispose();
          g.dispose();
          System.exit(0);
        }
      }
    );
  }

  public static void drawLine(
    BufferedImage img,
    int x1,
    int y1,
    int x2,
    int y2
  ) {
    
    // octant 1 WORKING
    // draw(x1, y1, x2, y2, 1);

    // octant 2 WORKING
    draw(y1, x1, y2, x2, 2);

    // octant 4 NOT WORKING

    // octant 5 WORKING
    // draw(x1, y1, x2, y2, 5);

    // octant 6
    // draw(y1, x1, y2, x2, 6);
    // int x = x2;
    // int y = y2;
    // int dx = x1 - x2;
    // int dy = y1 - y2;
    // int d = 2 * dy - dx;

    // while (x < x1) {
    //     if (d >= 0) {
    //         y = y + 1;
    //         d = d + 2 * (dy - dx);
    //     } else {
    //         d = d + 2 * dy;
    //     }
    //     x = x + 1;
    //     drawPixel(x, y);
    // }
  }

  private static void draw (int x1, int y1, int x2, int y2, int octant) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int d = 2 * dy - dx;
    // p0 -> p1

    int x = octant == 4 || octant == 5 || octant == 6 ? x2 : x1;
    int y = octant == 4 || octant == 5 || octant == 6 ? y2 : x1;
    int endX = octant == 4 || octant == 5 || octant == 6 ? x1 : x2;

    while (x < endX) {
        if (d >= 0) {
          y = y + 1;
          d = d + 2 * (dy - dx);
        } else {
          d = d + 2 * dy;
        }
        x = x + 1;

        if (octant == 2 || octant == 3 || octant == 7) drawPixel(y, x);
        else drawPixel(x, y);
      }
  }

  private static BufferedImage img;
  private static int[] pixel = { 255, 255, 255 };

  public static void drawPixel(int x, int y) {
    img.getRaster().setPixel(x, y, pixel);
  }
}
