import java.awt.*;

// Overlay for choosing to Decline or Stack against a +2
public class StackChoiceOverlay extends WndInterface implements TurnDecisionOverlayInterface {
    private final Button declineButton;                    // Decline button
    private TurnActionFactory.TurnDecisionAction currentAction; // Action triggering overlay
    private final Player playerReference;                 // Bottom player reference

    // Init overlay with decline button and player reference
    public StackChoiceOverlay(Rectangle bounds) {
        super(bounds);
        setEnabled(false);
        Position centre = bounds.getCentre();
        declineButton = new Button(new Position(centre.x-50, centre.y+100), 100, 40, "Decline", 0);
        playerReference = CurrentGameInterface.getCurrentGame().getBottomPlayer();
    }

    // No update needed
    @Override
    public void update(int deltaTime) {}

    // Draw decline button
    @Override
    public void paint(Graphics g) {
        declineButton.paint(g);
    }

    // Show overlay
    @Override
    public void showOverlay(TurnActionFactory.TurnDecisionAction currentAction) {
        this.currentAction = currentAction;
        setEnabled(true);
    }

    // Update hover state
    @Override
    public void handleMouseMove(Position mousePosition) {
        if(!isEnabled()) return;
        declineButton.setHovering(declineButton.isPositionInside(mousePosition));
    }

    // Handle mouse press for decline button or stacking
    @Override
    public void handleMousePress(Position mousePosition, boolean isLeft) {
        if(!isEnabled()) return;

        if(declineButton.isPositionInside(mousePosition)) {
            currentAction.injectFlagProperty(0);
            setEnabled(false);
            return;
        }

        Card clickedCard = playerReference.chooseCardFromClick(mousePosition);
        if(clickedCard != null && clickedCard.getFaceValueID() == 10) {
            currentAction.injectProperty("faceValueID", clickedCard.getFaceValueID());
            currentAction.injectProperty("colourID", clickedCard.getColourID());
            currentAction.injectProperty("cardID", clickedCard.getCardID());
            currentAction.injectFlagProperty(1);
            setEnabled(false);
        }
    }
}
