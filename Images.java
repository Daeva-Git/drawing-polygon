import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

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

    final float m = (y2 - y1) * 1f / (x2 - x1) * 1f;

    if (y1 < y2){
      // bottom half
      if (1 >= m && m >= 0) {
        // octane 1
        draw(x1, y1, x2, y2, false, false, false);
      } else if (Integer.MAX_VALUE >= m && m >= 1) {
        // octane 2
        draw(y1, x1, y2, x2, true, false, false);
      } else if (-1 >= m && m >= Integer.MIN_VALUE) {
        // octane 3
        draw(y1, x1, y2, x2, true, false, true);
      } else if (0 >= m && m >= -1) {
        // octane 4
        draw(x1, y1, x2, y2, false, true, true);
      }
    } else {
      // upper half
      if (1 >= m && m >= 0) {
        // octane 5
        draw(x1, y1, x2, y2, false, true, false);
      } else if (Integer.MAX_VALUE >= m && m >= 1) {
        // octane 6
        draw(y1, x1, y2, x2, true, true, false);
      } else if (-1 >= m && m >= Integer.MIN_VALUE) {
        // octane 7
        draw(y1, x1, y2, x2, true, true, true);
      } else if (0 >= m && m >= -1) {
        // octane 8
        draw(x1, y1, x2, y2, false, false, true);
      }
    }
  }

  private static void draw (int x1, int y1, int x2, int y2, boolean swaped, boolean reverse, boolean decrement) {
    final int dx = Math.abs(x2 - x1);
    final int dy = Math.abs(y2 - y1);
    final int endX = reverse ? x1 : x2;

    int x = reverse ? x2 : x1;
    int y = reverse ? y2 : y1;
    int d = 2 * dy - dx;

    while (x < endX) {
        if (d <= 0) {
          // E
          d = d + 2 * dy;
        } else {
          // NE
          d = d + 2 * (dy - dx);
          y = y + (decrement ? -1 : 1);
        } 
        x = x + 1;

        drawPixel(swaped ? y : x, swaped ? x : y);
      }
  }

  private static BufferedImage img;
  private static int[] pixel = { 255, 255, 255 };
  public static void drawPixel(int x, int y) {
    img.getRaster().setPixel(x, y, pixel);
  }
}
