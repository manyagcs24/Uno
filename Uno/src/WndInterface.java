import java.awt.*;

// Generic interface for all UI windows
public abstract class WndInterface {
    private boolean isEnabled;     // Whether this interface is active
    protected final Rectangle bounds; // Bounds of the interface

    // Constructor sets bounds and enables the interface
    public WndInterface(Rectangle bounds) {
        isEnabled = true;
        this.bounds = bounds;
    }

    // Update interface (override in subclasses)
    public abstract void update(int deltaTime);

    // Draw interface elements (override in subclasses)
    public abstract void paint(Graphics g);

    // Handle mouse press (optional override)
    public void handleMousePress(Position mousePosition, boolean isLeft) {}

    // Handle mouse movement (optional override)
    public void handleMouseMove(Position mousePosition) {}

    // Enable or disable this interface
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    // Check if interface is enabled
    public boolean isEnabled() {
        return isEnabled;
    }

    // Handle key input (optional override)
    public void handleInput(int keyCode) {}
}
