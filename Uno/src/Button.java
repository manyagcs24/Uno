import java.awt.*;

// Simple button with rectangle, text, hover state, and actionID
public class Button extends Rectangle {

    private final int actionID;    // Custom ID to identify button action
    private boolean isHovered;      // True if mouse is over button
    private final String text;      // Text shown on button

    // Constructor
    public Button(Position position, int width, int height, String text, int actionID) {
        super(position, width, height);
        this.actionID = actionID;
        this.isHovered = false;
        this.text = text;
    }

    // Draw the button (changes color if hovered)
    public void paint(Graphics g) {
        // Background color
        if(isHovered) g.setColor(new Color(63, 78, 123));
        else g.setColor(new Color(123, 133, 163));

        g.fillRect(position.x - (isHovered ? 3 : 0), 
                   position.y - (isHovered ? 3 : 0), 
                   width + (isHovered ? 6 : 0), 
                   height + (isHovered ? 6 : 0));

        // Border color
        g.setColor(isHovered ? Color.WHITE : Color.BLACK);
        g.drawRect(position.x - (isHovered ? 3 : 0), 
                   position.y - (isHovered ? 3 : 0), 
                   width + (isHovered ? 6 : 0), 
                   height + (isHovered ? 6 : 0));

        // Text
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, position.x + width/2 - strWidth/2, position.y + height/2 + 8);
    }

    // Get button's actionID
    public int getActionID() {
        return actionID;
    }

    // Set hover state
    public void setHovering(boolean isHovering) {
        this.isHovered = isHovering;
    }
}
