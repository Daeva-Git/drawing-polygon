import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Images {

  public static BufferedImage gradientSetRaster(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    WritableRaster raster = img.getRaster();
    int[] pixel = new int[3]; // RGB

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

  private static boolean listenToMouseInput = true;
  private static final ArrayList<Point> vertexPositions = new ArrayList<>();
  private static Graphics g;

  public static void main(String... args) {
    Frame w = new Frame("Raster"); // window
    final int imageWidth = 500;
    final int imageHeight = 500;

    w.setSize(imageWidth, imageHeight);
    w.setLocation(100, 100);
    w.setVisible(true);

    g = w.getGraphics();

    img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    gradientSetRaster(img);
    update();

    w.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          if (!listenToMouseInput)
            return;

          final int x = e.getX();
          final int y = e.getY();

          if (vertexPositions.size() > 0) {
            final Point lastVertex = vertexPositions.get(vertexPositions.size() - 1);
            drawLine(lastVertex.x, lastVertex.y, x, y);
          } else {
            drawPixel(x, y);
          }

          vertexPositions.add(new Point(x, y));
          System.out.println(x + " " + y);
          update();
        }
      }
    });

    w.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        final int keyCode = e.getKeyChar();
        switch (keyCode) {
          // start end drawing polygon
          case KeyEvent.VK_ENTER:
            listenToMouseInput = !listenToMouseInput;
            formPolygon();
            break;
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        switch (keyCode) {
          // polygon movement
          case KeyEvent.VK_LEFT:
            movePolygon(vertexPositions, Direction.LEFT);
            break;
          case KeyEvent.VK_UP:
            movePolygon(vertexPositions, Direction.UP);
            break;
          case KeyEvent.VK_RIGHT:
            movePolygon(vertexPositions, Direction.RIGHT);
            break;
          case KeyEvent.VK_DOWN:
            movePolygon(vertexPositions, Direction.DOWN);
            break;

          // polygon rotation
          case KeyEvent.VK_R:
            rotatePolygon(vertexPositions, Direction.LEFT);
            break;
          case KeyEvent.VK_T:
            rotatePolygon(vertexPositions, Direction.RIGHT);
            break;

          // polygon scaling
          case KeyEvent.VK_S:
            break;
          case KeyEvent.VK_D:
            break;
          default:
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });

    w.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        w.dispose();
        g.dispose();
        System.exit(0);
      }
    });
  }

  public static void drawLine(int x1, int y1, int x2, int y2) {
    float m = (y2 - y1) * 1f / (x2 - x1) * 1f;
    if (Float.isNaN(m))
      m = 0;

    if (y1 < y2) {
      // bottom half
      if (1 >= m && m >= 0) {
        // octant 1
        drawLineInOctant(x1, y1, x2, y2, false, false, false);
      } else if (Float.POSITIVE_INFINITY >= m && m >= 1) {
        // octant 2
        drawLineInOctant(y1, x1, y2, x2, true, false, false);
      } else if (-1 >= m && m >= Float.NEGATIVE_INFINITY) {
        // octant 3
        drawLineInOctant(y1, x1, y2, x2, true, false, true);
      } else if (0 >= m && m >= -1) {
        // octant 4
        drawLineInOctant(x1, y1, x2, y2, false, true, true);
      }
    } else {
      // upper half
      if (1 >= m && m >= 0 && x1 > x2) {
        // octant 5
        drawLineInOctant(x1, y1, x2, y2, false, true, false);
      } else if (Float.POSITIVE_INFINITY >= m && m >= 1) {
        // octane 6
        drawLineInOctant(y1, x1, y2, x2, true, true, false);
      } else if (-1 >= m && m >= Float.NEGATIVE_INFINITY) {
        // octant 7
        drawLineInOctant(y1, x1, y2, x2, true, true, true);
      } else if (0 >= m && m >= -1) {
        // octant 8
        drawLineInOctant(x1, y1, x2, y2, false, false, true);
      }
    }
  }

  private static void drawLineInOctant(int x1, int y1, int x2, int y2, boolean swaped, boolean reverse,
      boolean decrement) {
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

  private static void formPolygon() {
    final Point lastVertex = vertexPositions.get(vertexPositions.size() - 1);
    final Point firstVertex = vertexPositions.get(0);
    drawLine(firstVertex.x, firstVertex.y, lastVertex.x, lastVertex.y);

    update();
  }

  private static void drawPolygon(ArrayList<Point> vertices) {
    // some kind of clearing
    gradientSetRaster(img);

    for (int i = 1; i < vertices.size(); i++) {
      final Point previousVertex = vertices.get(i - 1);
      final Point currentVertex = vertices.get(i);

      drawLine(previousVertex.x, previousVertex.y, currentVertex.x, currentVertex.y);
    }
    formPolygon();
  }

  static enum Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN
  }

  private static void movePolygon(ArrayList<Point> vertices, Direction direction) {
    // TODO: make matrix multiplication
    for (int i = 0; i < vertices.size(); i++) {
      final Point vertex = vertices.get(i);
      switch (direction) {
        case LEFT:
          vertex.x--;
          break;
        case UP:
          vertex.y--;
          break;
        case RIGHT:
          vertex.x++;
          break;
        case DOWN:
          vertex.y++;
          break;
        default:
          break;
      }
    }

    drawPolygon(vertices);
  }

  private static void rotatePolygon(ArrayList<Point> vertices, Direction direction) {
    float angle = 1;
    float angleInRadiants = (float) Math.toRadians(angle);

    int n = vertices.size();

    int x = 0;
    int y = 0;

    float[][] matrix = new float[3][n];
    for (int i = 0; i < n; i++) {
      Point vertex = vertices.get(i);
      matrix[0][i] = vertex.x;
      matrix[1][i] = vertex.y;
      matrix[2][i] = 1;

      x += vertex.x;
      y += vertex.y;
    }
    y /= n;

    Matrix.printMatrix(matrix);



    float sinA = (float) Math.sinh(angleInRadiants);
    float cosA = (float) Math.cos(angleInRadiants);

    float[][] rotationMatrix = {
        { cosA, -sinA, x - x * cosA - y * sinA },
        { sinA, cosA, y - x * sinA + y * cosA },
        { 0, 0, 1 }
    };

    float[][] newMatrix = Matrix.multiplyMatrix(rotationMatrix, matrix);

    Matrix.printMatrix(newMatrix);

    // for (int i = 0; i < n; i++) {
    //   Point vertex = vertices.get(i);
    //   vertex.x = (int) newMatrix[0][i];
    //   vertex.y = (int) newMatrix[1][i];
    // }
    drawPolygon(vertices);
    update();
  }

  private static void update() {
    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);
  }

  private static BufferedImage img;
  private static int[] pixel = { 255, 255, 255 };

  public static void drawPixel(int x, int y) {
    img.getRaster().setPixel(x, y, pixel);
  }
}
