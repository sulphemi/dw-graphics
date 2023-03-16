import java.util.*;

public class Matrix {

  public static final int POINT_SIZE = 4;
  public static final int TRANSLATE = 0;
  public static final int SCALE = 1;
  public static final int ROTATE = 2;
  public static final int HERMITE = 3;
  public static final int BEZIER = 4;

  protected ArrayList<double []>m;

  Matrix() {
    m = new ArrayList<double []>();
  }//constructor

  Matrix(int transformType, double x, double y, double z) {
    ident();
    if (transformType == TRANSLATE)
      makeTranslate(x, y, z);
    else if (transformType == SCALE)
      makeScale(x, y, z);
  }//translate/scale constructor

/*======== coeficient constructor ==========
  Inputs:   double p1, double p2,
            double p3, double p4
            int type

  Constructs a matrix containing the values for a, b, c and d of the
  equation at^3 + bt^2 + ct + d for the curve defined by p1, p2, p3 and p4.

  Type determines whether the curve is bezier or hermite.
  ====================*/
  Matrix(int curveType, double x0, double y0, double x1, double y1, double m0, double n0, double m1, double n1) {
    this();
    m.add(new double[] {x0, x1, m0, n0});
    m.add(new double[] {y0, y1, m1, n1});
    mult(new Matrix(curveType));
  }//coefiecient constructor

    /*======== curve type constructor ==========
      Creates a 4x4 Matrix that can be used in the curve
      coeficient constructor. curveType is one of the constants
      defined above.
      ====================*/
  Matrix(int curveType) {
    this();
    switch (curveType) {
      case HERMITE:
        m.add(new double[] {0, 1, 0, 3});
        m.add(new double[] {0, 1, 0, 2});
        m.add(new double[] {0, 1, 1, 1});
        m.add(new double[] {1, 1, 0, 0});
        break;
      case BEZIER:
        m.add(new double[] {-1, 3, -3, 1});
        m.add(new double[] {3, -6, 3, 0});
        m.add(new double[] {-3, 3, 0, 0});
        m.add(new double[] {1, 0, 0, 0});
        break;
      default:
        throw new RuntimeException("aaaaaaa");
    }
  }//translate/scale constructor

  Matrix(int transformType, double theta, char axis) {
    ident();
    if (transformType == ROTATE) {
      if (axis == 'X')
        makeRotX(theta);
      else if (axis == 'Y')
        makeRotY(theta);
      else if (axis == 'Z')
        makeRotZ(theta);
    }
  }//roate constrcutor

  private void makeTranslate(double x, double y, double z){
    double[] lastCol = m.get(3);
    lastCol[0] = x;
    lastCol[1] = y;
    lastCol[2] = z;
  }//makeTranslate

  private void makeScale(double x, double y, double z) {
    m.get(0)[0] = x;
    m.get(1)[1] = y;
    m.get(2)[2] = z;
  }//makeScale

  private void makeRotX(double theta) {
    m.get(1)[1] = Math.cos(theta);
    m.get(2)[1] = -1*Math.sin(theta);
    m.get(1)[2] = Math.sin(theta);
    m.get(2)[2] = Math.cos(theta);
  }//makeRotX

  private void makeRotY(double theta) {
    m.get(0)[0] = Math.cos(theta);
    m.get(0)[2] = -1*Math.sin(theta);
    m.get(2)[0] = Math.sin(theta);
    m.get(2)[2] = Math.cos(theta);
  }//makeRotX

  private void makeRotZ(double theta) {
    m.get(0)[0] = Math.cos(theta);
    m.get(1)[0] = -1*Math.sin(theta);
    m.get(0)[1] = Math.sin(theta);
    m.get(1)[1] = Math.cos(theta);
  }//makeRotX

  public void addColumn(double x, double y, double z) {
    double[] col = {x, y, z, 1};
    m.add(col);
  }//addColumn

  public void ident() {
    m = new ArrayList<double []>();
    for (int i=0; i<POINT_SIZE; i++) {
      double[] point = new double[POINT_SIZE];
      point[i] = 1;
      m.add(point);
    }
  }//ident



  public void mult(Matrix a) {
    double[] tmp = new double[POINT_SIZE];
    for (int c=0; c<m.size(); c++) {
      double[] point = m.get(c);
      //copy values from m over
      for (int r=0; r < point.length; r++)
        tmp[r] = point[r];

      for (int r=0; r < point.length; r++) {
        m.get(c)[r] = a.m.get(0)[r] * tmp[0] +
          a.m.get(1)[r] * tmp[1] +
          a.m.get(2)[r] * tmp[2] +
          a.m.get(3)[r] * tmp[3];
      }
    }
  }//mult

  public void clear() {
    m = new ArrayList<double[] >();
  }//clear

  public double[] get(int i) {
    return m.get(i);
  }

  public String toString() {

    String s = "";
    if (m.size() == 0) {
      return s;
    }

    for (int i=0; i<POINT_SIZE; i++) {
      for (double[] p : m) {
        s+= p[i] + " ";
      }
      s+= "\n";
    }
    return s;
  }
}//Matrix
