import java.awt.*;

// Overlay showing a short flashing tick for a successful challenge
public class ChallengeSuccessOverlay extends WndInterface implements GeneralOverlayInterface {
    private double displayTimer; // Timer until overlay hides
    private final int[] polyXCoords; // X coords for tick graphic
    private final int[] polyYCoords; // Y coords for tick graphic

    // Constructor sets bounds and prepares graphic coords
    public ChallengeSuccessOverlay(Rectangle bounds) {
        super(bounds);
        setEnabled(false);

        int widthDiv6 = bounds.width / 6;
        int x = bounds.position.x;
        int y = bounds.position.y;
        int heightDiv6 = bounds.height / 6;

        polyXCoords = new int[] { x, x+widthDiv6, x+widthDiv6*2,
                x+widthDiv6*5, x+bounds.width, x+widthDiv6*2};
        polyYCoords = new int[] { y+heightDiv6*4, y+heightDiv6*3, y+heightDiv6*4,
                y+heightDiv6*2, y+heightDiv6*3, y+bounds.height};
    }

    // Show overlay and start timer
    @Override
    public void showOverlay() {
        setEnabled(true);
        displayTimer = 2000;
    }

    // Update timer and hide overlay if done
    @Override
    public void update(int deltaTime) {
        displayTimer -= deltaTime;
        if(displayTimer <= 0) setEnabled(false);
    }

    // Draw the tick (flashes 75% of the time)
    @Override
    public void paint(Graphics g) {
        if(displayTimer % 200 < 150) {
            g.setColor(new Color(106, 163, 22));
            g.fillPolygon(polyXCoords, polyYCoords, polyXCoords.length);
            g.setColor(Color.BLACK);
            g.drawPolygon(polyXCoords, polyYCoords, polyXCoords.length);
        }
    }
}
