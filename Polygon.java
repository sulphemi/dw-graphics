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
    if (getY(bot) < getY(mid)) {
        if (getY(top) < getY(bot)) {
          double[] temp;
          temp = top;
          top = bot;
          bot = temp;
        } //swap(bot, top);
    } else {
      if (getY(mid) < getY(top)) {
        double[] temp;
        temp = bot;
        bot = mid;
        mid = temp;
      } //swap(bot, mid);
      else {
        double[] temp;
        temp = bot;
        bot = top;
        top = temp;
      } //swap(bot, top);
    }
    if (getY(top) < getY(mid)) {
      double[] temp;
      temp = mid;
      mid = top;
      top = temp;
    } //swap(mid, top);
    //end swap
    
    //do stuff i guess
    System.out.println(Arrays.toString(top));
    System.out.println(Arrays.toString(mid));
    System.out.println(Arrays.toString(bot));
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
