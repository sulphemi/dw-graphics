import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
  public static void main(String[] args) {

    Color c = Color.GREEN;
    Screen s = new Screen();
    EdgeMatrix edges = new EdgeMatrix();

    Matrix m2 = new Matrix();
    m2.addColumn(1, 2, 3);
    m2.addColumn(4, 5, 6);
    System.out.println(m2);

    Matrix m1 = new Matrix();
    m1.ident();
    System.out.println(m1);

    m2.mult(m1);
    System.out.println(m2);

    m1.clear();
    m1.addColumn(1, 2, 3);
    m1.addColumn(4, 5, 6);
    m1.addColumn(7, 8, 9);
    m1.addColumn(10, 11, 12);
    System.out.println(m1);

    m2.mult(m1);
    System.out.println(m2);

    edges.addEdge(50, 450, 0, 100, 450, 0);
    edges.addEdge(50, 450, 0, 50, 400, 0);
    edges.addEdge(100, 450, 0, 100, 400, 0);
    edges.addEdge(100, 400, 0, 50, 400, 0);

    edges.addEdge(200, 450, 0, 250, 450, 0);
    edges.addEdge(200, 450, 0, 200, 400, 0);
    edges.addEdge(250, 450, 0, 250, 400, 0);
    edges.addEdge(250, 400, 0, 200, 400, 0);

    edges.addEdge(150, 400, 0, 130, 360, 0);
    edges.addEdge(150, 400, 0, 170, 360, 0);
    edges.addEdge(130, 360, 0, 170, 360, 0);

    edges.addEdge(100, 340, 0, 200, 340, 0);
    edges.addEdge(100, 320, 0, 200, 320, 0);
    edges.addEdge(100, 340, 0, 100, 320, 0);
    edges.addEdge(200, 340, 0, 200, 320, 0);

    edges.drawEdges(s, c);
    s.display();

  }//main
}//class Main
