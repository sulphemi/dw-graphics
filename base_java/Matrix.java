import java.util.*;

public class Matrix {

  public static int POINT_SIZE = 4;
  protected ArrayList<double []>m;

  Matrix() {
    m = new ArrayList<double []>();
  }//constructor

  /*======== void addColumn() ==========
    Inputs: double x, double y, double z
    Returns:
    Adds point (x, y, z) to the calling object.
    ====================*/
  public void addColumn(double x, double y, double z) {
  }//addColumn

  /*======== void ident() ==========
    Inputs: 
    Returns:
    Turn the calling object into a 4x4 identity matrix
    ====================*/
  public void ident() {
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

    return s;
  }
}//Matrix
