import java.util.*;
import java.awt.*;

public class PolygonMatrix extends Matrix {

  // x, y, z is UPPER, LEFT, FRONT
  public void addBox( double x, double y, double z, double width, double height, double depth ) {
    //front facing side
    addPolygon(x, y, z, x, y - height, z, x + width, y - height, z);
    addPolygon(x, y, z, x + width, y - height, z, x + width, y, z);

    //left facing side
    addPolygon(x, y, z, x, y - height, z - depth, x, y - height, z);
    addPolygon(x, y, z, x, y, z - depth, x, y - height, z - depth);

    //right facing side
    addPolygon(x + width, y, z, x + width, y - height, z - depth, x + width, y, z - depth);
    addPolygon(x + width, y, z, x + width, y - height, z, x + width, y - height, z - depth);

    //top facing side
    addPolygon(x, y, z, x + width, y, z, x, y, z - depth);
    addPolygon(x + width, y, z, x + width, y, z - depth, x, y, z - depth);

    //bottom facing side
    addPolygon(x, y - height, z, x + width, y - height, z - depth, x + width, y - height, z);
    addPolygon(x, y - height, z, x, y - height, z - depth, x + width, y - height, z - depth);

    //back facing side
    addPolygon(x + width, y - height, z - depth, x, y - height, z - depth, x, y, z - depth);
    addPolygon(x + width, y - height, z - depth, x, y, z - depth, x + width, y, z - depth);
  }//addBox

  public void addSphere( double cx, double cy, double cz,
                         double r, int steps ) {
      Matrix pts = generateSphere(cx, cy, cz, r, steps);
      // assert steps % 2 == 0;
      // Screen s = new Screen(); //debug

      int circlects = steps; //number of circles
      int circlepts = steps + 1; //number of points in a circle

      for (int i = 0; i < circlects - 1; i++) {
        int a = i * circlepts; //index of first point of first semicircle
        int b = i * circlepts + circlepts; //index of first point of second semicircle
        addFromPts(pts.get(a), pts.get(a + 1), pts.get(b + 1));
        for (int k = 1; k < circlepts - 2; k++) {
          addFromPts(pts.get(a + k), pts.get(a + k + 1), pts.get(b + k + 1));
          addFromPts(pts.get(a + k), pts.get(b + k + 1), pts.get(b + k));
  
          // { //debug
          //   s.clearScreen();
          //   mult(new Matrix(Matrix.TRANSLATE, 60, 50, 0));
          //   drawPolygons(s, Color.YELLOW);
          //   mult(new Matrix(Matrix.TRANSLATE, -60, -50, 0));
          //   s.display();
          // }
        }
        addFromPts(pts.get(a + circlepts - 2), pts.get(a + circlepts - 1), pts.get(b + circlepts - 2));
      }

      { //do it one more time for end to beginning
        int a = (circlects - 1) * circlepts; //index of first point of first semicircle
        int b = 0; //index of first point of second semicircle
        addFromPts(pts.get(a), pts.get(a + 1), pts.get(b + 1));
        for (int k = 1; k < circlepts - 2; k++) {
          addFromPts(pts.get(a + k), pts.get(a + k + 1), pts.get(b + k + 1));
          addFromPts(pts.get(a + k), pts.get(b + k + 1), pts.get(b + k));
        }
        addFromPts(pts.get(a + circlepts - 2), pts.get(a + circlepts - 1), pts.get(b + circlepts - 2));
      }
  }//addSphere

  //sphere is made of 20 (steps) circles with 21 (steps + 1) pts each
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

  public void addFromPts(double[] p0, double[] p1, double[] p2) {
    m.add(Arrays.copyOf(p0, 4));
    m.add(Arrays.copyOf(p1, 4));
    m.add(Arrays.copyOf(p2, 4));
  }

  public void drawPolygons(Screen s, Color c) {
    assert m.size() % 3 == 0;
    if (m.size() < 3) {
      System.out.println("need at least 3 points to draw polygons");
      return;
    }

    for (int i = 0; i < m.size() - 2; i += 3) {
      double[] p0 = m.get(i);
      double[] p1 = m.get(i + 1);
      double[] p2 = m.get(i + 2);

      if (! Polygon.isVisible(p0[0], p0[1], p0[2], p1[0], p1[1], p1[2], p2[0], p2[1], p2[2])) continue;

      s.drawLine((int)p0[0], (int)p0[1], (int)p1[0], (int)p1[1], c);
      s.drawLine((int)p0[0], (int)p0[1], (int)p2[0], (int)p2[1], c);
      s.drawLine((int)p1[0], (int)p1[1], (int)p2[0], (int)p2[1], c);
    }
  }//drawPloygons

}//class PolygonMatrix
