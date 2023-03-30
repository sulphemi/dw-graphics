import java.util.*;
import java.awt.*;

public class EdgeMatrix extends Matrix {

/*======== void addBox() ==========
  Inputs:   double x, double y, double z,
            double width, double height, double depth

  add the points for a rectagular prism whose
  upper-left-front corner is (x, y, z) with width,
  height and depth dimensions.
  ====================*/    
  public void addBox( double x, double y, double z, double width, double height, double depth ) {
    addEdge(x, y, z, x + width, y, z); //upper-left-front, rightward
    addEdge(x, y, z, x, y - height, z); //upper-left-front, downward
    addEdge(x, y, z, x, y, z - depth); //upper-left-front, backward
    addEdge(x + width, y - height, z - depth, x, y - height, z - depth); //lower-right-back, leftward
    addEdge(x + width, y - height, z - depth, x + width, y, z - depth); //lower-right-back, upward
    addEdge(x + width, y - height, z - depth, x + width, y - height, z); //lower-right-back, forward
    addEdge(x + width, y, z, x + width, y - height, z); //upper-right-front, downward
    addEdge(x + width, y - height, z, x, y - height, z); //lower-right-front, leftward
    addEdge(x + width, y, z - depth, x, y, z - depth); //upper-right-back, leftward
    addEdge(x + width, y, z - depth, x + width, y, z); //upper-right-back, forward
    addEdge(x, y - height, z - depth, x, y, z - depth); //lower-left-back, upward
    addEdge(x, y - height, z - depth, x, y - height, z); //lower-left-back, forward
  }//addBox


/*======== void addSphere() ==========
  Inputs:   double cx, double cy, double cz,
            double r, int step

  adds all the points for a sphere with center (cx, cy, cz)
  and radius r using step points per circle/semicircle.

  Since edges are drawn using 2 points, add each point twice,
  or add each point and then another point 1 pixel away.

  should call generateSphere to create the necessary points
  ====================*/    
  public void addSphere(double cx, double cy, double cz, double r, int step) {
    Matrix pts = generateSphere(cx, cy, cz, r, step);
    for (double[] d : pts.m) {
      addEdge(d[0], d[1], d[2], d[0] + 1, d[1] + 1, d[2] + 1);
    }

  }//addSphere

 /*======== void generateSphere() ==========
  Inputs:   double cx, double cy, double cz
            double r, int step
	    
  Returns: Generates all the points along the surface
           of a sphere with center (cx, cy, cz) and
           radius r using step points per circle/semicircle.
           Returns a Matrix of those points
  ====================*/
  private Matrix generateSphere(double cx, double cy, double cz, double r, int step) {
    Matrix points = new Matrix();
    for (int i = 0; i < step; i++) {
      for (int k = 0; k < step; k++) {
        double p = (double)i / step * Math.PI * 2; //phi for sphere
        double t = (double)k / step * Math.PI; //theta for semicircle
        double x = r * Math.cos(t) + cx;
        double y = r * Math.sin(t) * Math.cos(p) + cy;
        double z = r * Math.sin(t) * Math.sin(p) + cz;
        points.addColumn(x, y, z);
      }
    }
    return points;
  }//generateSphere

    
/*======== void add_torus() ==========
  Inputs:   double cx, double cy, double cz,
            double r1, double r2, double step
	    
  Returns:

  adds all the points required for a torus with center (cx, cy, cz),
  circle radius r1 and torus radius r2 using step points per circle.

  should call generateTorus to create the necessary points
  ====================*/
  public void addTorus(double cx, double cy, double cz, double r0, double r1, int step) {
    Matrix pts = generateTorus(cx, cy, cz, r0, r1, step);
    for (double[] d : pts.m) {
      addEdge(d[0], d[1], d[2], d[0] + 1, d[1] + 1, d[2] + 1);
    }
  }//addTorus

/*======== Matrix generateTorus() ==========
  Inputs:   double cx, double cy, double cz,
            double r, int step
	    
  Returns: Generates all the points along the surface
           of a torus with center (cx, cy, cz),
           circle radius r1 and torus radius r2 using
           step points per circle.
           Returns a matrix of those points
  ====================*/
  private Matrix generateTorus(double cx, double cy, double cz,
                               double r0, double r1, int step ) {

    Matrix points = new Matrix();
    return points;
  }//generateTorus

  public void addCricle(double cx, double cy, double cz,
                        double r, int step) {
    double x0, y0, x1, y1, t;

    x0 = r + cx;
    y0 = cy;
    for (int i=1; i<step; i++) {
      t = (double)i/step;

      x1 = r * Math.cos(2 * Math.PI * t) + cx;
      y1 = r * Math.sin(2 * Math.PI * t) + cy;

      addEdge(x0, y0, cz, x1, y1, cz);
      x0 = x1;
      y0 = y1;
    }
  }//addCircle


  public void addCurve( double x0, double y0,
                         double x1, double y1,
                         double x2, double y2,
                         double x3, double y3,
                         int step, int curveType ) {

    double t, x, y;
    Matrix xcoefs = new Matrix(curveType, x0, x1, x2, x3);
    Matrix ycoefs = new Matrix(curveType, y0, y1, y2, y3);

    double[] xm = xcoefs.get(0);
    double[] ym = ycoefs.get(0);

    for (int i=1; i<step; i++) {
      t = (double)i/step;

      x = xm[0]*t*t*t + xm[1]*t*t+ xm[2]*t + xm[3];
      y = ym[0]*t*t*t + ym[1]*t*t+ ym[2]*t + ym[3];
      addEdge(x0, y0, 0, x, y, 0);
      x0 = x;
      y0 = y;
    }
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
