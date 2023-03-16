import java.util.*;
import java.awt.*;

public class EdgeMatrix extends Matrix {



    /*======== void add_circle() ==========
      Inputs:   double cx, double cy, double cz
      double r
      double step

      Adds the circle at (cx, cy, cz) with radius r to the calling matrix
      ====================*/
  public void addCircle(double cx, double cy, double cz, double r, double step) {
    step /= r; //something??
    for (double i = 0; i <= 1; i += step) { //replace with integer comps
      double n = Math.PI * 2 * i;
      double _x0 = r * Math.cos(n) + cx;
      double _y0 = r * Math.sin(n) + cy;
      double _z0 = cz;
      i += step;
      double _x1 = r * Math.cos(n) + cx;
      double _y1 = r * Math.sin(n) + cy;
      double _z1 = cz;
      addEdge(_x0, _y0, _z0, _x1, _y1, _z1);
    }
  }//addCircle


    /*======== void add_curve() ==========
      Inputs: double x0, double y0,
              double x1, double y1,
              double x2, double y2,
	      double x3, double y3,
	      double step
	      int type

      Adds the curve bounded by the 4 points passsed as parameters
      of type specified in type (see Matrix.java for curve type constants)
      to the calling matrix.
      ====================*/
  public void addCurve( double x0, double y0,
                         double x1, double y1,
                         double x2, double y2,
                         double x3, double y3,
                         double step, int curveType ) {
  }//addCurve

  public void addEdge(double x0, double y0, double z0,
                      double x1, double y1, double z1) {
    double[] col0 = {x0, y0, z0, 1};
    double[] col1 = {x1, y1, z1, 1};
    m.add(col0);
    m.add(col1);
  }//addColumn

  public void drawEdges(Screen s, Color c) {
    if ( m.size() < 2) {
      System.out.println("Need at least 2 edges to draw a line");
      return;
    }//not enough points

    for(int point=0; point<m.size()-1; point+=2) {
      double[] p0 = m.get(point);
      double[] p1 = m.get(point+1);
      s.drawLine((int)p0[0], (int)p0[1], (int)p1[0], (int)p1[1], c);
    }//draw lines
  }//drawEdges


}//class EdgeMatrix
