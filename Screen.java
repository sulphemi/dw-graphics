import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class Screen {

  public static final int XRES = 500;
  public static final int YRES = 500;
  public static final int YRES_OFFSET = 70;
  public static final int MAX_COLOR = 255;
  public static final Color DEFAULT_COLOR = new Color(0, 0, 0);

  private int width;
  private int height;

  private int lineCt = 0;
  private int windowCt = 0;

  private BufferedImage img;
  private JFrame frame;
  private volatile boolean stupidWayToBlockWhenICouldHaveUsedListenersAndNotWastedHoweverManyCPUCycles = true;

  public Screen() {
    width = XRES;
    height = YRES;

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    clearScreen();

    frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(img.getWidth(), img.getHeight() + YRES_OFFSET);
    frame.setTitle("why are jframes so hard QwQ");

    JButton button = new JButton("next image! (hopefully)");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        stupidWayToBlockWhenICouldHaveUsedListenersAndNotWastedHoweverManyCPUCycles = false;
      }
    });
    button.setSize(img.getWidth(), YRES_OFFSET);
    button.setBounds(0, img.getHeight(), img.getWidth(), 50);

    frame.add(button);
  }//constructor

  public void clearScreen() {

    Graphics2D g = img.createGraphics();
    g.setColor(DEFAULT_COLOR);
    g.fillRect(0, 0, img.getWidth(), img.getHeight());
    g.dispose();

    lineCt = 0;
  }//clearScreen

  public void drawLine(int x0, int y0, int x1, int y1, Color c) {
    int x, y, d, A, B;
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
          plot( c, x, y );
          if ( d > 0 ) {
            y+= 1;
            d+= B;
          }
          x++;
          d+= A;
        } //end octant 1 while
        plot( c, x1, y1 );
      } //end octant 1

      //octant 8
      else {
        d = A - B/2;

        while ( x < x1 ) {
          plot( c, x, y );
          if ( d < 0 ) {
            y-= 1;
            d-= B;
          }
          x++;
          d+= A;
        } //end octant 8 while
        plot( c, x1, y1 );
      } //end octant 8
    }//end octants 1 and 8

    //octants 2 and 7
    else {

      //octant 2
      if ( A > 0 ) {
        d = A/2 + B;

        while ( y < y1 ) {
          plot( c, x, y );
          if ( d < 0 ) {
            x+= 1;
            d+= A;
          }
          y++;
          d+= B;
        } //end octant 2 while
        plot( c, x1, y1 );
      } //end octant 2

      //octant 7
      else {
        d = A/2 - B;

        while ( y > y1 ) {
          plot( c, x, y );
          if ( d > 0 ) {
            x+= 1;
            d+= A;
          }
          y--;
          d-= B;
        } //end octant 7 while
        plot(c, x1, y1 );
      } //end octant 7
    }//end octants 2 and 7

    lineCt++;
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
    stupidWayToBlockWhenICouldHaveUsedListenersAndNotWastedHoweverManyCPUCycles = true;

    if (lineCt == 0) {
      System.out.println("[screen] no lines in image, skipping...");
      return;
    }

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
    
    while (stupidWayToBlockWhenICouldHaveUsedListenersAndNotWastedHoweverManyCPUCycles);
  }//display

}//class Screen
