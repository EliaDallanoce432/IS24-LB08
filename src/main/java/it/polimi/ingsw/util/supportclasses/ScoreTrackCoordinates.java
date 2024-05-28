package it.polimi.ingsw.util.supportclasses;

import java.util.HashMap;

/**
 * This class holds the score track coordinates for each point on the track.
 */
public class ScoreTrackCoordinates {

    static class Coordinate {
        double x, y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private static final HashMap<Integer, Coordinate> coordinates = new HashMap<>();

    static {
        coordinates.put(0, new Coordinate(0.269277846, 0.920970753));
        coordinates.put(1, new Coordinate(0.501835985, 0.920970753));
        coordinates.put(2, new Coordinate(0.734394125, 0.920970753));
        coordinates.put(3, new Coordinate(0.844553244, 0.81829496));
        coordinates.put(4, new Coordinate(0.611995104, 0.81829496));
        coordinates.put(5, new Coordinate(0.385556916, 0.81829496));
        coordinates.put(6, new Coordinate(0.159118727, 0.81829496));
        coordinates.put(7, new Coordinate(0.159118727, 0.709396391));
        coordinates.put(8, new Coordinate(0.385556916, 0.709396391));
        coordinates.put(9, new Coordinate(0.611995104, 0.709396391));
        coordinates.put(10, new Coordinate(0.844553244, 0.709396391));
        coordinates.put(11, new Coordinate(0.844553244, 0.606720597));
        coordinates.put(12, new Coordinate(0.611995104, 0.606720597));
        coordinates.put(13, new Coordinate(0.385556916, 0.606720597));
        coordinates.put(14, new Coordinate(0.159118727, 0.606720597));
        coordinates.put(15, new Coordinate(0.159118727, 0.504044804));
        coordinates.put(16, new Coordinate(0.385556916, 0.504044804));
        coordinates.put(17, new Coordinate(0.611995104, 0.504044804));
        coordinates.put(18, new Coordinate(0.844553244, 0.504044804));
        coordinates.put(19, new Coordinate(0.844553244, 0.398257623));
        coordinates.put(20, new Coordinate(0.501835985, 0.342252645));
        coordinates.put(21, new Coordinate(0.159118727, 0.398257623));
        coordinates.put(22, new Coordinate(0.159118727, 0.289359054));
        coordinates.put(23, new Coordinate(0.159118727, 0.183571873));
        coordinates.put(24, new Coordinate(0.269277846, 0.096453018));
        coordinates.put(25, new Coordinate(0.501835985, 0.077784692));
        coordinates.put(26, new Coordinate(0.734394125, 0.096453018));
        coordinates.put(27, new Coordinate(0.844553244, 0.183571873));
        coordinates.put(28, new Coordinate(0.844553244, 0.289359054));
        coordinates.put(29, new Coordinate(0.501835985, 0.208462974));
    }

    public static double getXCoordinate(int score) {
        if (score < 0) throw new IndexOutOfBoundsException();
        if (score > 29) return coordinates.get(29).getX();
        else return coordinates.get(score).getX();
    }

    public static double getYCoordinate(int score) {
        if (score < 0) throw new IndexOutOfBoundsException();
        if (score > 29) return coordinates.get(29).getY();
        else return coordinates.get(score).getY();
    }
}
