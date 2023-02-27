import java.util.*;

public class Matrix {

  public static int POINT_SIZE = 4;
  protected ArrayList<double []>m;

  Matrix() {
    m = new ArrayList<double []>(POINT_SIZE);
  }//constructor

  /*======== void addColumn() ==========
    Inputs: double x, double y, double z
    Returns:
    Adds point (x, y, z) to the calling object.
    ====================*/
  public void addColumn(double x, double y, double z) {
    assert POINT_SIZE == 4;
    double[] column = {x, y, z, 1};
    m.add(column);
  }

  /*======== void ident() ==========
    Inputs:
    Returns:
    Turn the calling object into a 4x4 identity matrix
    ====================*/
  public void ident() {
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
}//Matrix
