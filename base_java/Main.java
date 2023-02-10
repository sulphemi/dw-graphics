import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
  public static void main(String[] args) {

    Screen s = new Screen();
    Color c = Color.GREEN;

    //octants 1 and 5
    s.drawLine(0, 0, Screen.XRES-1, Screen.YRES-1, c);
    s.drawLine(0, 0, Screen.XRES-1, Screen.YRES / 2, c);
    s.drawLine(Screen.XRES-1, Screen.YRES-1, 0, Screen.YRES / 2, c);

    //octants 8 and 4
    c = Color.CYAN;
    s.drawLine(0, Screen.YRES-1, Screen.XRES-1, 0, c);
    s.drawLine(0, Screen.YRES-1, Screen.XRES-1, Screen.YRES/2, c);
    s.drawLine(Screen.XRES-1, 0, 0, Screen.YRES/2, c);

    //octants 2 and 6
    c = Color.RED;
    s.drawLine(0, 0, Screen.XRES/2, Screen.YRES-1, c);
    s.drawLine(Screen.XRES-1, Screen.YRES-1, Screen.XRES/2, 0, c);

    //octants 7 and 3
    c = Color.MAGENTA;
    s.drawLine(0, Screen.YRES-1, Screen.XRES/2, 0, c);
    s.drawLine(Screen.XRES-1, 0, Screen.XRES/2, Screen.YRES-1, c);

    //horizontal and vertical
    c = Color.YELLOW;
    s.drawLine(0, Screen.YRES/2, Screen.XRES-1, Screen.YRES/2, c);
    s.drawLine(Screen.XRES/2, 0, Screen.XRES/2, Screen.YRES-1, c);

    s.display();
    s.saveExtension("lines.png");
  }//main
}//class Main
