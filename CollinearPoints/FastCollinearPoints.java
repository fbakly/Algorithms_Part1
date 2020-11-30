import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segs;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        checkNull(points);
        checkDuplicates(points);
        this.points = Arrays.copyOf(points, points.length);
        findSegments();
    }

    private void checkNull(Point[] points) {
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
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
        Point[] sortedPoints = Arrays.copyOf(this.points, this.points.length);

        Arrays.sort(sortedPoints);

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Arrays.sort(points, sortedPoints[i].slopeOrder());
            int len = 1;
            int j = 1;
            double prevSlope = sortedPoints[i].slopeTo(points[1]);
            for (; j < points.length; j++) {
                if (sortedPoints[i].slopeTo(points[j]) == prevSlope) {
                    len++;
                } else {
                    if (len >= 4) {
                        Arrays.sort(points, j - (len - 1), j);
                        if (sortedPoints[i].compareTo(points[j - (len - 1)]) < 0)
                            segs.add(new LineSegment(sortedPoints[i], points[j - 1]));
                    }
                    len = 2;
                    prevSlope = sortedPoints[i].slopeTo(points[j]);
                }
            }
            if (len >= 4) {
                Arrays.sort(points, j - (len - 1), j);
                if (sortedPoints[i].compareTo(points[j - (len - 1)]) < 0)
                    segs.add(new LineSegment(sortedPoints[i], points[j - 1]));
            }
        }
        this.segs = segs.toArray(new LineSegment[segs.size()]);
    }

    public LineSegment[] segments() {
        return this.segs;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
