import java.util.*;

public class Matrix {

  public static int POINT_SIZE = 4;
  protected ArrayList<double []>m;

  Matrix() {
    m = new ArrayList<double []>(POINT_SIZE);
  }//constructor

  private void addColumn(double a, double b, double c, double d) {
    double[] column = {a, b, c, d};
    m.add(column);
  }

  /*======== void addColumn() ==========
    Inputs: double x, double y, double z
    Returns:
    Adds point (x, y, z) to the calling object.
    ====================*/
  public void addColumn(double x, double y, double z) {
    assert POINT_SIZE == 4;
    addColumn(x, y, z, 1);
  }

  /*======== void ident() ==========
    Inputs:
    Returns:
    Turn the calling object into a 4x4 identity matrix
    ====================*/
  public void ident() {
    assert POINT_SIZE == 4;
    clear();
    addColumn(1, 0, 0, 0);
    addColumn(0, 1, 0, 0);
    addColumn(0, 0, 1, 0);
    addColumn(0, 0, 0, 1);
  }//ident

  /*======== void mult() ==========
    Inputs:  Matrix a
    Returns
    multiply the calling matrix by a, modifying the
    calling matrix to be the product
   ====================*/
  public void mult(Matrix a) {
    assert n_columns() == a.n_columns();
  }//mult


  public void clear() {
    m = new ArrayList<double[] >();
  }//clear


  /*======== void toString() ==========
    Inputs:
    Returns: A String representation of the matrix
   ====================*/
  public String toString() {
    String s = "";

    for (int i = 0; i < POINT_SIZE; i++) {
      for (double[] d : m) {
        s += d[i];
        s += ' ';
      }
      s += '\n';
    }

    return s;
  }

  public double[] getCol(int i) {
    return m.get(i);
  }

  public double[] getRow(int index) {
    double[] d = new double[m.size()];
    for (int i = 0; i < d.length; i++) {
      d[i] = m.get(i)[index];
    }
    return d;
  }

  public int n_columns() {
    return m.size();
  }

  public static void main(String[] args) {
    System.out.println("make matrix:");
    Matrix owo = new Matrix();
    owo.addColumn(1, 2, 3);
    owo.addColumn(5, 6, 7);
    System.out.println(owo);
    System.out.println();

    System.out.println("get row 0");
    System.out.println(Arrays.toString(owo.getRow(0)));
    System.out.println("get col 0");
    System.out.println(Arrays.toString(owo.getCol(0)));
    System.out.println();

    System.out.println("ident");
    Matrix id = new Matrix();
    id.ident();
    System.out.println(id);
  }

}//Matrix
