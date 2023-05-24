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
  private double[][] zbuffer;

  public Screen() {
    width = XRES;
    height = YRES;

    zbuffer = new double[XRES][YRES];
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    clearScreen();
  }//constructor

  public void clearScreen() {

    Graphics2D g = img.createGraphics();
    g.setColor(DEFAULT_COLOR);
    g.fillRect(0, 0, img.getWidth(), img.getHeight());
    g.dispose();

    //clear zbuffer
    for (int i = 0; i < zbuffer.length; i++) {
      for (int k = 0; k < zbuffer[i].length; k++) {
        zbuffer[i][k] = Double.NEGATIVE_INFINITY;
      }
    }


  }//clearScreen


/*======== void draw_scanline() ==========
  Inputs: int x0, double z0
          int x1, double z1
          int y
          color c
  Returns:

  Draws a horzontal line between (x0, y) and (x1, y)
  Calculates z values across the line for z-buffering.
  ====================*/
  public void drawScanline(double _x0, double _z0, double _x1, double _z1, double _y, Color c) {
    int x0 = (int)_x0;
    int x1 = (int)_x1;
    int y = (int)_y;

    //swap points if going left to right
    if (x0 > x1) {
      int swap = x0;
      x0 = x1;
      x1 = swap;
    }
    
    double delta_x = _x1 - _x0;
    double delta_z = _z1 - _z0;
    double z_change = delta_z / delta_x;
    double z = _z0;
    for (int i = x0; i <= x1; i++) {
      plot(c, i, y, z);
      z += z_change;
    }
    //drawLine((int)_x0, (int)_y, (int)_x1, (int)_y, c);
  }

  public void drawLine(int x0, int y0, int x1, int y1, Color c) {
    int x, y, d, A, B;
    double z = 0;
    //swap points if going right -> left
    int xt, yt;
    if (x0 > x1) {
      xt = x0;
      yt = y0;
      x0 = x1;
      y0 = y1;
      x1 = xt;
      y1 = yt;
    }

    x = x0;
    y = y0;
    A = 2 * (y1 - y0);
    B = -2 * (x1 - x0);

    //octants 1 and 8
    if ( Math.abs(x1 - x0) >= Math.abs(y1 - y0) ) {

      //octant 1
      if ( A > 0 ) {

        d = A + B/2;
        while ( x < x1 ) {
          plot( c, x, y, z );
          if ( d > 0 ) {
            y+= 1;
            d+= B;
          }
          x++;
          d+= A;
        } //end octant 1 while
        plot( c, x1, y1, z );
      } //end octant 1

      //octant 8
      else {
        d = A - B/2;

        while ( x < x1 ) {
          plot( c, x, y, z );
          if ( d < 0 ) {
            y-= 1;
            d-= B;
          }
          x++;
          d+= A;
        } //end octant 8 while
        plot( c, x1, y1, z );
      } //end octant 8
    }//end octants 1 and 8

    //octants 2 and 7
    else {

      //octant 2
      if ( A > 0 ) {
        d = A/2 + B;

        while ( y < y1 ) {
          plot( c, x, y, z );
          if ( d < 0 ) {
            x+= 1;
            d+= A;
          }
          y++;
          d+= B;
        } //end octant 2 while
        plot( c, x1, y1, z );
      } //end octant 2

      //octant 7
      else {
        d = A/2 - B;

        while ( y > y1 ) {
          plot( c, x, y, z);
          if ( d > 0 ) {
            x+= 1;
            d+= A;
          }
          y--;
          d-= B;
        } //end octant 7 while
        plot(c, x1, y1, z );
      } //end octant 7
    }//end octants 2 and 7
  }//drawLine

  public void plot(Color c, int x, int y, double z) {
    int newy = width - 1 - y;
    if (x >= 0 && x < width && newy >= 0 && newy < height ) {
      //compare to zbuffer
      if (z > zbuffer[x][y]) {
        zbuffer[x][y] = z;
        img.setRGB(x, newy, c.getRGB());
      }
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
    //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //clearScreen();
    frame.add(pane);
    frame.setVisible(true);
  }//display

}//class Screen
