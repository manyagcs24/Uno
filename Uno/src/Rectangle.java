// Represents a rectangle using a top-left position and size
public class Rectangle {
    // Top-left corner position
    protected final Position position;  

    // Rectangle dimensions
    protected final int width;
    protected final int height;

    //create rectangle using position
    public Rectangle(Position position, int width, int height) {  
        this.position = position;
        this.width = width;
        this.height = height;
    }

    // Create rectangle using x and y coordinates
    public Rectangle(int x, int y, int width, int height) {
        this(new Position(x,y),width,height);
    }

    // Get rectangle height
    public int getHeight() {
        return height;
    }

    // Get rectangle width
    public int getWidth() {
        return width;
    }

    // Get top-left position
    public Position getPosition() {
        return position;
    }

    // Get center point of the rectangle
    public Position getCentre() {
        return new Position(position.x + width/2, position.y + height/2);
    }

    // Check if a position is inside the rectangle
    public boolean isPositionInside(Position targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }

    // Check if this rectangle intersects another
    public boolean isIntersecting(Rectangle otherRectangle) {
        // break if any of the following are true because it means they don't intersect
        if(position.y + height < otherRectangle.position.y) return false;
        if(position.y > otherRectangle.position.y + otherRectangle.height) return false;
        if(position.x + width < otherRectangle.position.x) return false;
        if(position.x > otherRectangle.position.x + otherRectangle.width) return false;

        // the bounding boxes do intersect
        return true;
    }
}
