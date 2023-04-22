import java.util.*;
import java.awt.*;

public class PolygonMatrix extends Matrix {

  public void addBox( double x, double y, double z, double width, double height, double depth ) {
    
  }//addBox

  public void addSphere( double cx, double cy, double cz,
                         double r, int steps ) {

  }//addSphere

  private Matrix generateSphere(double cx, double cy, double cz,
                                double r, int steps ) {

    Matrix points = new Matrix();
    int circle, rotation, rot_start, rot_stop, circ_start, circ_stop;
    double x, y, z, rot, circ;

    rot_start = 0;
    rot_stop = steps;
    circ_start = 0;
    circ_stop = steps;

    for (rotation = rot_start; rotation < rot_stop; rotation++) {
      rot = (double)rotation / steps;

      for(circle = circ_start; circle <= circ_stop; circle++){
        circ = (double)circle / steps;

        x = r * Math.cos(Math.PI * circ) + cx;
        y = r * Math.sin(Math.PI * circ) *
          Math.cos(2*Math.PI * rot) + cy;
        z = r * Math.sin(Math.PI * circ) *
          Math.sin(2*Math.PI * rot) + cz;

        /* printf("rotation: %d\tcircle: %d\n", rotation, circle); */
        /* printf("rot: %lf\tcirc: %lf\n", rot, circ); */
        /* printf("sphere point: (%0.2f, %0.2f, %0.2f)\n\n", x, y, z); */
        points.addColumn(x, y, z);
      }
    }
    return points;
  }//generateSphere

  public void addTorus( double cx, double cy, double cz,
                        double r0, double r1, int steps ) {

  }//addTorus

  private Matrix generateTorus(double cx, double cy, double cz, double r0, double r1, int steps ) {

    Matrix points = new Matrix();
    int circle, rotation, rot_start, rot_stop, circ_start, circ_stop;
    double x, y, z, rot, circ;

    rot_start = 0;
    rot_stop = steps;
    circ_start = 0;
    circ_stop = steps;

    for (rotation = rot_start; rotation < rot_stop; rotation++) {
      rot = (double)rotation / steps;

      for(circle = circ_start; circle < circ_stop; circle++){
        circ = (double)circle / steps;

        x = Math.cos(2*Math.PI * rot) *
          (r0 * Math.cos(2*Math.PI * circ) + r1) + cx;
        y = r0 * Math.sin(2*Math.PI * circ) + cy;
        z = -1*Math.sin(2*Math.PI * rot) *
          (r0 * Math.cos(2*Math.PI * circ) + r1) + cz;

        /* printf("rotation: %d\tcircle: %d\n", rotation, circle); */
        /* printf("rot: %lf\tcirc: %lf\n", rot, circ); */
        /* printf("sphere point: (%0.2f, %0.2f, %0.2f)\n\n", x, y, z); */
        points.addColumn(x, y, z);
      }
    }
    return points;
  }//generateTorus


  public void addPolygon(double x0, double y0, double z0,
                         double x1, double y1, double z1,
                         double x2, double y2, double z2) {
    //new Polygon(new double[] {x0, y0, z0}, new double[] {x1, y1, z1}, new double[] {x2, y2, z2}, co);
    m.add(new double[] {x0, y0, z0, 1});
    m.add(new double[] {x1, y1, z1, 1});
    m.add(new double[] {x2, y2, z2, 1});
  }//addColumn

  public void drawPolygons(Screen s, Color c) {
    assert m.size() % 3 == 0;
    if (m.size() < 3) {
      System.out.println("need at least 3 points to draw polygons");
      return;
    }

    for (int i = 0; i < m.size() - 2; i += 3) {
      double[] p0 = m[i];
      double[] p1 = m[i + 1];
      double[] p2 = m[i + 2];

      s.drawLine((int)p0[0], (int)p0[1], (int)p1[0], (int)p1[1], c);
      s.drawLine((int)p0[0], (int)p0[1], (int)p2[0], (int)p2[1], c);
      s.drawLine((int)p1[0], (int)p1[1], (int)p2[0], (int)p2[1], c);
    }
  }//drawPloygons

}//class PolygonMatrix
