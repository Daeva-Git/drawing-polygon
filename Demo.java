import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import library.Matrix;
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

    public static void pipeline(Cube cube, Camera camera, BufferedImage image) {
        // transformation matrix = projectionMatrix x viewMatrix x worldMatrix
        final float[][] projectionMatrix = camera.getProjectionMatrix();
        final float[][] viewMatrix = camera.getViewMatrix();
        final float[][] worldMatrix = cube.getWorldMatrix();
        final float[][] transformationMatrix = Matrix.matrixMultiplication(projectionMatrix,
                Matrix.matrixMultiplication(viewMatrix, worldMatrix));

        // create a new cube
        final Cube newCube = new Cube();

        // iterate over all its polygones
        for (Polygon polygon : newCube.getPolygons()) {
            // iterate over all their points
            for (Point point : polygon.getPoints()) {
                // multiply them with the transformation matrix
                point.setPointMatrix(Matrix.matrixMultiplication(transformationMatrix, point.getPointMatrix()));

                // perform the W division
                point.setX(point.getX() / -point.getW());
                point.setY(point.getY() / -point.getW());
                point.setZ(point.getZ() / -point.getW());

                // perform the viewport transformation
                point.setX((point.getX() + 1) * camera.getWidth() / 2.0f);
                point.setY((point.getY() + 1) * camera.getHeight() / 2.0f);
            }
        }

        // draw the cube
        newCube.draw(image);
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

        // create a new cube
        final Cube cube = new Cube();

        // scale and translate to take the cube to its initial position in the world
        // space
        cube.scale(2.0f, 2.0f, 2.0f);
        cube.translate(0.0f, 0.0f, -5.0f);

        // create a new camera
        final Camera camera = new Camera(90.0f, imageWidth, imageHeight, 1.0f, 5.0f);

        // translate to take the camera to its initial position in the world space
        camera.translateCamera(0.0f, 0.0f, 0.0f);

        // call the pipeline method
        pipeline(cube, camera, image);

        // draw the image
        g.drawImage(image, 0, 0, (img1, infoflags, x, y, width, height) -> false);

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
                    // rotation
                    case KeyEvent.VK_R:
                        // rotate the cube CCW around the y axis using R key
                        cube.rotateYOrigin(-1f);
                        break;
                    case KeyEvent.VK_T:
                        // rotate the cube CW around the y axis using T key
                        cube.rotateYOrigin(1f);
                        break;

                    // FOV
                    case KeyEvent.VK_G:
                        // increase the FOV using G key
                        camera.setTheta(camera.getTheta() + 1.0f);
                        break;
                    case KeyEvent.VK_F:
                        // decrease the FOV using F key
                        camera.setTheta(camera.getTheta() - 1.0f);
                        break;

                    // camera
                    case KeyEvent.VK_W:
                        // translate the camera forward using W key
                        camera.translateCamera(0.0f, 0.0f, 0.05f);
                        break;
                    case KeyEvent.VK_S:
                        // translate the camera backward using S key
                        camera.translateCamera(0.0f, 0.0f, -0.05f);
                        break;
                    case KeyEvent.VK_A:
                        // translate the camera left using A key
                        camera.translateCamera(-0.05f, 0.0f, 0.0f);
                        break;
                    case KeyEvent.VK_D:
                        // translate the camera right using D key
                        camera.translateCamera(0.05f, 0.0f, 0.0f);
                        break;
                    case KeyEvent.VK_E:
                        // translate the camera up using E key
                        camera.translateCamera(0.0f, -0.05f, 0.0f);
                        break;
                    case KeyEvent.VK_Q:
                        // translate the camera down using Q key
                        camera.translateCamera(0.0f, 0.05f, 0.0f);
                        break;
                    default:
                        // do nothing
                        break;
                }

                // clearf
                solidSetRaster(image);

                // call the pipeline method
                pipeline(cube, camera, image);

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
