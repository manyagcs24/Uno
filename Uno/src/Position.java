/**
 * Position class:
 * Used to represent a single position x,y.
 */
public class Position {

    //Common direction vectors
    public static final Position DOWN = new Position(0,1);  //Down moving unit vector
    public static final Position UP = new Position(0,-1);   //up moving unit vector
    public static final Position LEFT = new Position(-1,0);   //left moving unit vector
    public static final Position RIGHT = new Position(1,0);   //right moving unit vector
    public static final Position ZERO = new Position(0,0);   //zero vector

    public int x;   //x coordinate
    public int y;   //y coordinate

    public Position(int x, int y) {   //to set value of position
        this.x = x;
        this.y = y;
    }

    // Copy constructor
    public Position(Position positionToCopy) {
        this.x = positionToCopy.x;
        this.y = positionToCopy.y;
    }

    //set new x and y values
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Add another position to this one
    public void add(Position otherPosition) {
        this.x += otherPosition.x;
        this.y += otherPosition.y;
    }

    // Get distance to another position
    public double distanceTo(Position otherPosition) {
        return Math.sqrt(Math.pow(x-otherPosition.x,2)+Math.pow(y-otherPosition.y,2));
    }

    // Multiply both coordinates by a value
    public void multiply(int amount) {
        x *= amount;
        y *= amount;
    }

    // Subtract another position from this one
    public void subtract(Position otherPosition) {
        this.x -= otherPosition.x;
        this.y -= otherPosition.y;
    }

    // Check if two positions are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    // Return position as (x, y)
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
