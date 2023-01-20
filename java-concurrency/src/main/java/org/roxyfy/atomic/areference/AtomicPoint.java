package org.roxyfy.atomic.areference;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicPoint {

    private record Point(int x, int y) {
        Point rotateClockwise() {
            return new Point(y, -x);
        }
    }

    private final AtomicReference<Point> point = new AtomicReference<>(new Point(0, 1));

    public Point rotateClockwise() {
        return point.updateAndGet(Point::rotateClockwise);
    }
}
