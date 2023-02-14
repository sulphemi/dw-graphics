import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Screen {

  public static final int XRES = 500;
  public static final int YRES = 500;
  public static final int MAX_COLOR = 255;
  public static final int YRES_OFFSET = 40;
  public static final Color DEFAULT_COLOR = new Color(0, 0, 0);

  private int width;
  private int height;

  private BufferedImage img;

  public Screen() {
    width = XRES;
    height = YRES;

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    clearScreen();
  }//constructor

  public void clearScreen() {

    Graphics2D g = img.createGraphics();
    g.setColor(DEFAULT_COLOR);
    g.fillRect(0, 0, img.getWidth(), img.getHeight());
    g.dispose();

  }//clearScreen

    /*=============================
      PUT YOUR LINE ALGORITHM CODE HERE
      ===========================*/
  public void drawLine(int x0, int y0, int x1, int y1, Color c) {
    if (Math.abs(y1 - y0) > Math.abs(x1 - x0)) {
      //top-heavy slope

      if (y0 > y1) {
        int xtemp = x0;
        x0 = x1;
        x1 = xtemp;
        int ytemp = y0;
        y0 = y1;
        y1 = ytemp;
      }

      int A = y1 - y0;
      int B = x0 - x1;

      int d = A + B * 2;
      int currentX = x0;
      for (int i = y0; i < y1; i++) {
        plot(c, currentX, i);
        if (x1 >= x0) {
          if (d < 0) {
            currentX++;
            d += A * 2;
          }
        } else {
          if (false && d > 0) {
            currentX--;
            d += A * 2;
          }
        }
        d += B * 2;
      }

      if (currentX != x1) {
        System.out.println(c + "didnt draw correctly");
      }
    } else {
      //bottom-heavy slope

      if (x0 > x1) {
        int xtemp = x0;
        x0 = x1;
        x1 = xtemp;
        int ytemp = y0;
        y0 = y1;
        y1 = ytemp;
      }

      int A = y1 - y0;
      int B = x0 - x1;

      int d = A * 2 + B;
      int currentY = y0;
      for (int i = x0; i < x1; i++) {
        plot(c, i, currentY);
        if (y1 >= y0) {
          if (d > 0) {
            currentY++;
            d += B * 2;
          }
        } else {
          if (d < 0) {
            currentY--;
            d += B * 2;
          }
        }

        d += A * 2;
      }

      if (currentY != y1) {
        System.out.println(c + "didnt draw correctly");
      }
    }
  }//drawLine




  public void plot(Color c, int x, int y) {
    int newy = width - 1 - y;
    if (x >= 0 && x < width && newy >= 0 && newy < height) {
      img.setRGB(x, newy, c.getRGB());
    }
  }//plot

  public void savePpm(String filename) {
    String ppmFile = "P3\n";
    ppmFile+= width + " " + height + "\n";
    ppmFile+= MAX_COLOR + "\n";

    //int[] raster = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, 1);
    for (int y=0; y < height; y++) {
      for (int x=0; x<width; x++) {
        Color c = new Color(img.getRGB(x, y));
        ppmFile+= c.getRed() + " ";
        ppmFile+= c.getGreen() + " ";
        ppmFile+= c.getBlue() + " ";
      }
      ppmFile+= "\n";
    }
    try {
      FileWriter ppmWriter = new FileWriter(filename);
      ppmWriter.write(ppmFile);
      ppmWriter.close();
    }
    catch(IOException e) {
      System.out.println("Unable to write to file");
      e.printStackTrace();
    }
  }//savePpm

  public void saveExtension(String filename) {
    String ext = filename.split("\\.")[1];
    boolean goodType = false;
    for (String typ : ImageIO.getWriterFormatNames()) {
      if (ext.equals(typ)) {
        goodType = true;
        break;
      }
    }//type check
    if ( !goodType ) {
      System.out.println("Bad File Extension: " + ext);
      return;
    }//bad extension
    try {
      File outputfile = new File(filename);
      ImageIO.write(img, ext, outputfile);
    }
    catch (IOException e) {
      System.out.println("Unable to write to file");
      e.printStackTrace();
    }

  }//saveExtension

  public void display() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(img.getWidth(), img.getHeight() + YRES_OFFSET);

    ColorModel colorModel = img.getColorModel();
    WritableRaster raster = img.copyData(null);
    boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
    BufferedImage cpy = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);

    JPanel pane = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(cpy, 0, 0, null);
        }
      };

    frame.add(pane);
    frame.setVisible(true);
  }//display

}//class Screen
