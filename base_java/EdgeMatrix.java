import java.util.*;
import java.awt.*;

public class EdgeMatrix extends Matrix {

    /*======== void addEdge() ==========
      Inputs: int x0, int y0, int z0, int x1, int y1, int z1
      Returns:
      add the line connecting (x0, y0, z0) to (x1, y1, z1) to the
      calling Matrix Object.
      should use addColumn
      ====================*/
  public void addEdge(double x0, double y0, double z0, double x1, double y1, double z1) {
    addColumn(x0, y0, z0);
    addColumn(x1, y1, z1);
  }//addColumn

  /*======== void drawEdges() ==========
    Inputs:  screen s color c
    Returns:
    Go through points 2 at a time and call drawLine to add that line
    to the screen
    ====================*/
  public void drawEdges(Screen s, Color c) {
    for (int i = 0; i < m.size(); i += 2) {
      double[] a = getCol(i);
      double[] b = getCol(i + 1);
      s.drawLine((int)a[0], (int)a[1], (int)b[0], (int)b[1], c);
    }
  }//drawEdges


}//class EdgeMatrix
