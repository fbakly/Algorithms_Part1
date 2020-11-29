import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segs;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        checkNull(points);
        checkDuplicates(points);
        points = Arrays.copyOf(points, points.length);
        findSegments();
    }

    private void checkNull(Point[] points) {
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; i < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return this.segs.length;
    }

    private void findSegments() {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            int len = 0;
            for (int j = i + 1; j < points.length; j++) {
                
            }
        }
    }

    public LineSegment[] segments() {
        return this.segs;
    }
    
}
