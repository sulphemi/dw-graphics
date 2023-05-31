import java.util.*;
import java.io.*;
import java.awt.*;

public class Polygon {
  public static final double DEFAULT_R_AMBIENT = 0.1;
  public static final double DEFAULT_R_SPECULAR = 0.5;
  public static final double DEFAULT_R_DIFFUSE = 0.5;
  public static final double SPECULAR_EXP = 4;

  private double[] p0;
  private double[] p1;
  private double[] p2;

  private GfxVector normal;

  private double[] rAmbient;
  private double[] rDiffuse;
  private double[] rSpecular;

  private Color c;

  public Polygon(double[] pt0, double[] pt1, double[] pt2, Color co) {
    p0 = Arrays.copyOf(pt0, 3);
    p1 = Arrays.copyOf(pt1, 3);
    p2 = Arrays.copyOf(pt2, 3);
    calculateNormal();
    c = co;

    rAmbient = new double[]{DEFAULT_R_AMBIENT, DEFAULT_R_AMBIENT, DEFAULT_R_AMBIENT};
    rDiffuse = new double[]{DEFAULT_R_DIFFUSE, DEFAULT_R_DIFFUSE, DEFAULT_R_DIFFUSE};
    rSpecular = new double[]{DEFAULT_R_SPECULAR, DEFAULT_R_SPECULAR, DEFAULT_R_SPECULAR};
  }//constructor

  public GfxVector getNormal() {
    return normal;
  }//getNormal

  private void calculateNormal() {
    GfxVector A = new GfxVector(p0, p1);
    GfxVector B = new GfxVector(p0, p2);

    normal = A.crossProduct(B);
  }//calculateNormal


  public void setReflection(double[] ar, double[] dr, double[] sr) {
    rAmbient = Arrays.copyOf(ar, 3);
    rDiffuse = Arrays.copyOf(dr, 3);
    rSpecular = Arrays.copyOf(sr, 3);
  }//setReflection

  public void calculteLighting(GfxVector view, Color amb, GfxVector lightPos, Color lightColor ) {

    int[] ambient, diffuse, specular, color;
    color = new int[3];

    ambient = calculateAmbient(amb);
    diffuse = calculateDiffuse(lightPos, lightColor);
    specular = calculateSpecular(lightPos, lightColor, view);

    c = new Color(
      ambient[0] + diffuse[0] + specular[0],
      ambient[1] + diffuse[1] + specular[1],
      ambient[2] + ambient[2] + specular[2]
    );
  }//calculteLighting

  private int[] calculateAmbient(Color amb) {
    return colorArray(
      (int)(amb.getRed() * rAmbient[0]),
      (int)(amb.getGreen() * rAmbient[1]),
      (int)(amb.getBlue() * rAmbient[2])
    );
  }//calculateAmbient

  private int[] calculateDiffuse(GfxVector lightPos, Color lightColor) {
    double dot_product = getNormal().dotProduct(lightPos, true);
    int red = (int)(lightColor.getRed() * rDiffuse[0] * dot_product);
    int green = (int)(lightColor.getGreen() * rDiffuse[1] * dot_product);
    int blue = (int)(lightColor.getBlue() * rDiffuse[2] * dot_product);
    return colorArray(red, green, blue);
  }//calculateDiffuse

  private int[] calculateSpecular(GfxVector lightPos, Color lightColor, GfxVector view) {
    GfxVector thingy = getNormal();
    thingy.scalarMultiplty(2 * (getNormal().dotProduct(lightPos, true)));
    thingy.subtract(lightPos);
    double vector_magic = Math.pow(thingy.dotProduct(view, true), SPECULAR_EXP);
    int red = (int)(lightColor.getRed() * rSpecular[0] * vector_magic);
    int green = (int)(lightColor.getGreen() * rSpecular[1] * vector_magic);
    int blue = (int)(lightColor.getBlue() * rSpecular[2] * vector_magic);
    return colorArray(red, green, blue);
  }//calculateSpecular

  private void sanitizeColor(int[] color) {
    for (int i = 0; i < color.length; i++) {
      if (color[i] > 255) color[i] = 255;
      if (color[i] < 0) color[i] = 0;
    }
  }

  private int[] colorArray(int R, int G, int B) {
    int[] color = new int[] {R, G, B};
    sanitizeColor(color);
    return color;
  }

  public void scanlineConvert(Screen s) {

    int y;
    double[] top;
    double[] mid;
    double[] bot;
    int distance0, distance1, distance2;
    double x0, x1, y0, y1, y2, dx0, dx1, z0, z1, dz0, dz1;
    boolean flip = false;

    z0 = z1 = dz0 = dz1 = 0;

    y0 = p0[1];
    y1 = p1[1];
    y2 = p2[1];

    //find bot, mid, top
    if ( y0 <= y1 && y0 <= y2) {
      bot = p0;
      if (y1 <= y2) {
        mid = p1;
        top = p2;
      }
      else {
        mid = p2;
        top = p1;
      }
    }//end y0 bottom
    else if (y1 <= y0 && y1 <= y2) {
      bot = p1;
      if (y0 <= y2) {
        mid = p0;
        top = p2;
      }
      else {
        mid = p2;
        top = p0;
      }
    }//end y1 bottom
    else {
      bot = p2;
      if (y0 <= y1) {
        mid = p0;
        top = p1;
      }
      else {
        mid = p1;
        top = p0;
      }
    }//end y2 bottom
    //printf("ybot: %0.2f, ymid: %0.2f, ytop: %0.2f\n", (points->m[1][bot]),(points->m[1][mid]), (points->m[1][top]));
    /* printf("bot: (%0.2f, %0.2f, %0.2f) mid: (%0.2f, %0.2f, %0.2f) top: (%0.2f, %0.2f, %0.2f)\n", */

    x0 = bot[0];
    x1 = bot[0];//points->m[0][bot];
    z0 = bot[2];//points->m[2][bot];
    z1 = bot[2];//points->m[2][bot];
    y = (int)(bot[1]);

    distance0 = (int)(top[1]) - y + 1;
    distance1 = (int)(mid[1]) - y + 1;
    distance2 = (int)(top[1]) - (int)(mid[1]) + 1;

    //printf("distance0: %d distance1: %d distance2: %d\n", distance0, distance1, distance2);
    dx0 = distance0 > 0 ? (top[0] - bot[0])/distance0 : 0;
    dx1 = distance1 > 0 ? (mid[0] - bot[0])/distance1 : 0;
    dz0 = distance0 > 0 ? (top[2] - bot[2])/distance0 : 0;
    dz1 = distance1 > 0 ? (mid[2] - bot[2])/distance1 : 0;

    while ( y <= (int)top[1] ) {
      //printf("\tx0: %0.2f x1: %0.2f y: %d\n", x0, x1, y);

      if ( !flip && y >= (int)(mid[1]) ) {
        flip = true;
        dx1 = distance2 > 0 ? (top[0] - mid[0])/distance2 : 0;
        dz1 = distance2 > 0 ? (top[2] - mid[2])/distance2 : 0;
        x1 = mid[0];
        z1 = mid[2];
      }//end flip code
      //draw_line(x0, y, z0, x1, y, z1, s, zb, c);
      s.drawScanline((int)x0, z0, (int)x1, z1, y, c);

      x0+= dx0;
      x1+= dx1;
      z0+= dz0;
      z1+= dz1;
      y++;
    }//scanline loop
  }//scanlineConvert

}//class Polygon
