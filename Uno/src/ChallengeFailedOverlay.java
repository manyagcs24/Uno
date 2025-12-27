import java.awt.*;

// Overlay showing a red cross when a challenge fails
public class ChallengeFailedOverlay extends WndInterface implements GeneralOverlayInterface {
    private double displayTimer; // time to show overlay
    private final int[] polyXCoords; // X coordinates of cross
    private final int[] polyYCoords; // Y coordinates of cross

    // constructor, sets up cross shape and disables initially
    public ChallengeFailedOverlay(Rectangle bounds) {
        super(bounds);
        setEnabled(false);

        int x = bounds.position.x;
        int y = bounds.position.y;
        int sValX = bounds.width / 8;
        int sValY = bounds.height / 8;

        polyXCoords = new int[] {x+sValX,x+2*sValX,x+4*sValX,x+6*sValX,x+7*sValX,
                x+5*sValX,x+7*sValX,x+6*sValX,x+4*sValX,x+2*sValX,x+sValX,x+3*sValX};
        polyYCoords = new int[] {y+2*sValY,y+sValY,y+3*sValY,y+sValY,y+2*sValY,y+4*sValY,
                y+6*sValY,y+7*sValY,y+5*sValY,y+7*sValY,y+6*sValY,y+4*sValY};
    }

    // show overlay and reset timer
    @Override
    public void showOverlay() {
        setEnabled(true);
        displayTimer = 2000; // show for 2 seconds
    }

    // update timer and hide overlay when expired
    @Override
    public void update(int deltaTime) {
        displayTimer -= deltaTime;
        if(displayTimer <= 0) setEnabled(false);
    }

    // draw flashing red cross
    @Override
    public void paint(Graphics g) {
        if(displayTimer % 200 < 150) { // visible 75% of the time
            g.setColor(new Color(179, 50, 38));
            g.fillPolygon(polyXCoords, polyYCoords, polyXCoords.length);
            g.setColor(Color.BLACK);
            g.drawPolygon(polyXCoords, polyYCoords, polyXCoords.length);
        }
    }
}
