import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segs;

    public BruteCollinearPoints(Point[] points) {
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
            for (int j = i+1; i < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    private void findSegments() {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; k < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                        && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            LineSegment tempSeg = new LineSegment(points[i], points[l]);
                            if (!segs.contains(tempSeg))
                                segs.add(tempSeg);
                        }
                    }
                }
            }
        }
        this.segs = segs.toArray(new LineSegment[segs.size()]);
    }

    public LineSegment[] segments() {
        return this.segs;
    }

    public int numberofSegments() {
        return this.segs.length;
    }

}
