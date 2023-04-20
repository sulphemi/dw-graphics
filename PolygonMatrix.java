import java.util.*;
import java.awt.*;

public class PolygonMatrix extends Matrix {

  public void addBox( double x, double y, double z,
                       double width, double height, double depth ) {
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

  private Matrix generateTorus(double cx, double cy, double cz,
                               double r0, double r1, int steps ) {

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

  }//addColumn

  public void drawPolygons(Screen s, Color c) {

  }//drawPloygons

}//class PolygonMatrix
