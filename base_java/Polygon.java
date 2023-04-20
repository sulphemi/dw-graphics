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

  }//calculateNormal

}//class Polygon
