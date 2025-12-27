import java.awt.*;

// Special button for calling "UNO" when player has 2 or fewer cards
public class UnoButton extends WndInterface implements GeneralOverlayInterface {
    public static final int WIDTH = 80;  // Button width
    public static final int HEIGHT = 60; // Button height

    private boolean isHovered;            // Hover state
    protected final Player bottomPlayer; // Reference to bottom player
    protected boolean isActive;          // Active/visible state

    // Constructor sets position and gets bottom player
    public UnoButton(Position position) {
        super(new Rectangle(position, WIDTH, HEIGHT));
        isHovered = false;
        setEnabled(true);
        bottomPlayer = CurrentGameInterface.getCurrentGame().getBottomPlayer();
        isActive = false;
    }

    // Show overlay
    @Override
    public void showOverlay() {
        setEnabled(true);
    }

    // Updates button availability
    @Override
    public void update(int deltaTime) {
        isActive = bottomPlayer.getUnoState() == Player.UNOState.NotSafe
                || (bottomPlayer.getUnoState() == Player.UNOState.Safe
                        && CurrentGameInterface.getCurrentGame().getCurrentPlayer() == bottomPlayer
                        && bottomPlayer.getHand().size() == 2);
    }

    // Draws the button
    @Override
    public void paint(Graphics g) {
        if(!isActive) return;

        drawButtonBackground(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        int strWidth = g.getFontMetrics().stringWidth("UNO");
        g.drawString("UNO", bounds.position.x+bounds.width/2-strWidth/2-2, bounds.position.y+bounds.height/2+12);
        g.setColor(new Color(226, 173, 67));
        g.drawString("UNO", bounds.position.x+bounds.width/2-strWidth/2, bounds.position.y+bounds.height/2+10);
    }

    // Draws background with hover effect
    protected void drawButtonBackground(Graphics g) {
        g.setColor(new Color(147, 44, 44));
        int expandAmount = isHovered ? 20 : 0;
        g.fillOval(bounds.position.x-expandAmount/2, bounds.position.y-expandAmount/2,
                bounds.width+expandAmount, bounds.height+expandAmount);
    }

    // Updates hover state
    @Override
    public void handleMouseMove(Position mousePosition) {
        isHovered = bounds.isPositionInside(mousePosition);
    }

    // Handles clicking the button
    @Override
    public void handleMousePress(Position mousePosition, boolean isLeft) {
        if(isActive && bounds.isPositionInside(mousePosition)) {
            bottomPlayer.setUnoState(Player.UNOState.Called);
            CurrentGameInterface.getCurrentGame().showGeneralOverlay("UNOCalled"+bottomPlayer.getPlayerID());
        }
    }
}
 