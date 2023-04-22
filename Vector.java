public class Vector {
    public static Vector view_vector = new Vector(0, 0, 1);

    private double x, y, z;

    public Vector(double x0, double y0, double z0, double x1, double y1, double z1) {
        x = x1 - x0;
        y = y1 - y0;
        z = z1 - z0;
    }

    public Vector(double a, double b, double c) {
        x = a;
        y = b;
        z = c;
    }

    public static Vector crossProduct(Vector A, Vector B) {
        return new Vector(A.y * B.z - A.z * B.y, A.z * B.x - A.x * B.z, A.x * B.y - A.y * B.x);
    }

    public static double dotProduct(Vector A, Vector B) {
        return A.x * B.x + A.y * B.y + A.z + B.z;
    }

    public boolean facingView() {
        return dotProduct(this, view_vector) >= 0;
    }
}