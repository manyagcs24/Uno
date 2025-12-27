import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Overlay for Challenge/Decline/Stack actions against +4
public class ChallengeOverlay extends WndInterface implements TurnDecisionOverlayInterface {
    private final List<Button> buttonList; // Buttons in the overlay
    private TurnActionFactory.TurnDecisionAction currentAction; // Current turn action
    private final Player playerReference; // Player for stacking cards
    private final boolean allowStacking; // True if stacking is allowed

    // Constructor sets up buttons and checks stacking rule
    public ChallengeOverlay(Rectangle bounds) {
        super(bounds);
        setEnabled(false);
        buttonList = new ArrayList<>();
        Position centre = bounds.getCentre();
        if(!CurrentGameInterface.getCurrentGame().getRuleSet().getNoBluffingRule()) {
            buttonList.add(new Button(new Position(centre.x - 150, centre.y + 100), 100, 40, "Challenge", 1));
        }
        buttonList.add(new Button(new Position(centre.x + 50, centre.y + 100), 100, 40, "Decline", 0));

        allowStacking = CurrentGameInterface.getCurrentGame().getRuleSet().canStackCards();
        playerReference = CurrentGameInterface.getCurrentGame().getBottomPlayer();
    }

    // Not used
    @Override
    public void update(int deltaTime) {}

    // Draw all buttons
    @Override
    public void paint(Graphics g) {
        buttonList.forEach(button -> button.paint(g));
    }

    // Show overlay and set current action
    @Override
    public void showOverlay(TurnActionFactory.TurnDecisionAction currentAction) {
        this.currentAction = currentAction;
        setEnabled(true);
    }

    // Update hover state for buttons
    @Override
    public void handleMouseMove(Position mousePosition) {
        if(!isEnabled()) return;
        for (Button button : buttonList) {
            button.setHovering(button.isPositionInside(mousePosition));
        }
    }

    // Handle button clicks or stacking +4 cards
    @Override
    public void handleMousePress(Position mousePosition, boolean isLeft) {
        if(!isEnabled()) return;

        for (Button button : buttonList) {
            if(button.isPositionInside(mousePosition)) {
                currentAction.injectProperty("isChaining", 0);
                currentAction.injectFlagProperty(button.getActionID());
                setEnabled(false);
                return;
            }
        }

        if(allowStacking) {
            Card clickedCard = playerReference.chooseCardFromClick(mousePosition);
            if(clickedCard != null && clickedCard.getFaceValueID() == 13) {
                currentAction.injectProperty("faceValueID", clickedCard.getFaceValueID());
                currentAction.injectProperty("colourID", clickedCard.getColourID());
                currentAction.injectProperty("cardID", clickedCard.getCardID());
                currentAction.injectProperty("isChaining", 1);
                currentAction.injectFlagProperty(0);
                setEnabled(false);
            }
        }
    }
}
