import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
  public static void main(String[] args) {

    Color c = Color.GREEN;
    Screen s = new Screen();
    EdgeMatrix edges = new EdgeMatrix();
    Matrix transform = new Matrix();

    if (args.length == 1) {
      //System.out.println(args[0]);
      try {
        gfxParse(new Scanner(new File(args[0])), edges, transform, s, c);
      }
      catch(FileNotFoundException e) {}
    }
    else {
      System.out.println("using system.in");
      gfxParse(new Scanner(System.in), edges, transform, s, c);
    }

  }//main

  /*======== void gfx_parse () ==========
    Inputs: Scanner input
            EdgeMatrix edges
            EdgeMatrix transform,
            Screen s, Color c
    Returns:

    Goes through the scanner object and performs all of the actions listed in that file.
    The file follows the following format:
    Every command is a single character that takes up a line
    Any command that requires arguments must have those arguments in the second line.
    The commands are as follows:
        line: add a line to the edge matrix -
        takes 6 arguemnts (x0, y0, z0, x1, y1, z1)
        ident: set the transform matrix to the identity matrix -
        scale: create a scale matrix,
               then multiply the transform matrix by the scale matrix -
               takes 3 arguments (sx, sy, sz)
        translate: create a translation matrix,
               then multiply the transform matrix by the translation matrix -
               takes 3 arguments (tx, ty, tz)
        rotate: create a rotation matrix,
               then multiply the transform matrix by the rotation matrix -
               takes 2 arguments (axis, theta) axis should be x y or z
        apply: apply the current transformation matrix to the edge matrix
        display: clear the screen, then
               draw the lines of the edge matrix to the screen
               display the screen
        save: clear the screen, then
               draw the lines of the edge matrix to the screen
               save the screen to a file -
               takes 1 argument (file name)
        quit: end parsing

    See the file script for an example of the file format
  */
  public static void gfxParse(Scanner input, EdgeMatrix edges, Matrix transform, Screen s, Color c) {

    String command = "";
    double[] xvals = new double[3];
    double[] yvals = new double[3];
    double[] zvals = new double[3];
    double theta;
    char axis;
    Matrix tmp = null;

    while (input.hasNext()) {
      command = input.nextLine();
      System.out.println(command);

      switch (command) {
        case "line":
          edges.addColumn(input.nextInt(), input.nextInt(), input.nextInt());
          edges.addColumn(input.nextInt(), input.nextInt(), input.nextInt());
          break;
        case "display":
          edges.drawEdges(s, new Color(255, 255, 255));
          s.display();
          break;
        case "ident":
          transform.ident();
          break;
        case "move":
          tmp = new Matrix(Matrix.TRANSLATE, input.nextInt(), input.nextInt(), input.nextInt());
          transform.mult(tmp);
          break;
        case "scale":
          tmp = new Matrix(Matrix.SCALE, input.nextInt(), input.nextInt(), input.nextInt());
          transform.mult(tmp);
          break;
        case "rotate":
          tmp = new Matrix(Matrix.ROTATE, input.next().charAt(0), input.nextInt());
          transform.mult(tmp);
          break;
        case "apply":
          System.out.println("applying:\n" + transform);
          edges.mult(transform);
          transform.ident();
          break;
        case "save":
          s.saveExtension(input.nextLine());
          break;
        case "":
          //scanner left an empty line for us
          break;
        default:
          throw new IllegalArgumentException("waaa command " + command + " not found");
      }
      //System.out.println(command);
      System.out.println("t:\n" + tmp);
      System.out.println("m:\n" + edges);
    }//read loop
  }//gfxParse

}//class Main
