import java.util.*;
import java.io.*;
import java.awt.*;

public class Polygon {

  private double[] p0;
  private double[] p1;
  private double[] p2;

  private double[] normal;
  private Color c;

  public Polygon(double[] pt0, double[] pt1, double[] pt2, Color co) {
    p0 = Arrays.copyOf(pt0, 3);
    p1 = Arrays.copyOf(pt1, 3);
    p2 = Arrays.copyOf(pt2, 3);
    normal = new double[3];
    calculateNormal();
    c = co;
  }//constructor

  public double[] getNormal() {
    return normal;
  }//getNormal

  private void calculateNormal() {
    double[] A = new double[3];
    double[] B = new double[3];

    A[0] = p1[0] - p0[0];
    A[1] = p1[1] - p0[1];
    A[2] = p1[2] - p0[2];

    B[0] = p2[0] - p0[0];
    B[1] = p2[1] - p0[1];
    B[2] = p2[2] - p0[2];

    normal[0] = A[1] * B[2] - A[2] * B[1];
    normal[1] = A[2] * B[0] - A[0] * B[2];
    normal[2] = A[0] * B[1] - A[1] * B[0];
  }//calculateNormal


  /*======== void scanline_convert() ==========
  Inputs: Screen s

  Returns:

  Fills in polygon i by drawing consecutive horizontal (or vertical) lines.

  Color should be set differently for each polygon.
  ====================*/

  public void scanlineConvert(Screen s) {
    double[] top = p0;
    double[] mid = p1;
    double[] bot = p2;

    //swap the thingies
    {
      double[] temp;
      if (getY(bot) < getY(mid)) {
          if (getY(top) < getY(bot)) {
            temp = top;
            top = bot;
            bot = temp;
          } //swap(bot, top);
      } else {
        if (getY(mid) < getY(top)) {
          temp = bot;
          bot = mid;
          mid = temp;
        } //swap(bot, mid);
        else {
          temp = bot;
          bot = top;
          top = temp;
        } //swap(bot, top);
      }
      if (getY(top) < getY(mid)) {
        temp = mid;
        mid = top;
        top = temp;
      } //swap(mid, top);
    }
    //end swap

    //draw lines from bottom to mid
    double x0 = getX(bot);
    double x1 = getX(bot);
    double z0 = getZ(bot);
    double z1 = getZ(bot);
    {
      double delta_X0 = (getX(top) - getX(bot)) / (getY(top) - getY(bot) + 1);
      double delta_X1 = (getX(mid) - getX(bot)) / (getY(mid) - getY(bot) + 1);
      double delta_Z0 = (getZ(top) - getZ(bot)) / (getY(top) - getY(bot) + 1);
      double delta_Z1 = (getZ(mid) - getZ(bot)) / (getY(mid) - getY(bot) + 1);
      double delta_Y = 1;
      double y = getY(bot);
      while (y < getY(mid)) {
        s.drawScanline(x0, z0, x1, z1, y, c);
        x0 += delta_X0;
        x1 += delta_X1;
        z0 += delta_Z0;
        z1 += delta_Z1;
        y += delta_Y;
        //z += delta_Z;
      }
    }

    //draw lines from mid to top
    {
      double delta_X0 = (getX(top) - getX(bot)) / (getY(top) - getY(bot) + 1);
      double delta_X1 = (getX(top) - getX(mid)) / (getY(top) - getY(mid) + 1);
      double delta_Z0 = (getZ(top) - getZ(bot)) / (getY(top) - getY(bot) + 1);
      double delta_Z1 = (getZ(top) - getZ(mid)) / (getY(top) - getY(mid) + 1);
      double delta_Y = 1;
      x1 = getX(mid);
      z1 = getZ(mid);
      double y = getY(mid);
      while (y < getY(top)) {
        s.drawScanline(x0, z0, x1, z1, y, c);
        x0 += delta_X0;
        x1 += delta_X1;
        z0 += delta_Z0;
        z1 += delta_Z1;
        y += delta_Y;
        //z += delta_Z;
      }
    }
  }

  public static void main(String[] args) {
    Polygon aaa = new Polygon(ra(), ra(), ra(), new Color(80, 80, 80));
    aaa.scanlineConvert(null);
  }

  private double getX(double[] pt) {
    return pt[0];
  }

  private double getY(double[] pt) {
    return pt[1];
  }

  private double getZ(double[] pt) {
    return pt[2];
  }

  public static double[] ra() {
    double[] d = new double[4];
    for (int i = 0; i < d.length - 1; i++) {
      d[i] = (int)(Math.random() * 1000);
    }
    d[3] = 1;
    return d;
  }

}//class Polygon
