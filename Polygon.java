import java.util.*;
import java.io.*;
import java.awt.*;


/*
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

  }//calculateNormal

}//class Polygon
*/

public class Polygon {
  public static Vector calcNormal(double x0, double y0, double z0, double x1, double y1, double z1, double x2, double y2, double z2) {
    return Vector.crossProduct(new Vector(x0, y0, z0, x1, y1, z1), new Vector(x0, y0, z0, x2, y2, z2));
  }

  public static boolean facingView(double x0, double y0, double z0, double x1, double y1, double z1, double x2, double y2, double z2) {
    return calcNormal(x0, y0, z0, x1, y1, z1, x2, y2, z2).facingView();
  }
}