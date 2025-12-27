import java.awt.*;

// Overlay to show status info about a TurnDecisionAction
public class StatusOverlay extends WndInterface implements TurnDecisionOverlayInterface {
    private String statusText;       // Status message to display
    private final Font statusFont = new Font("Arial", Font.BOLD, 20); // Font for text
    private final Position centre;   // Centre of overlay
    private double timeOut;          // Time left for action
    private String timeOutStr;       // Display string for timer

    // Init overlay with bounds
    public StatusOverlay(Rectangle bounds) {
        super(bounds);
        setEnabled(false);
        centre = bounds.getCentre();
        timeOutStr = "";
    }

    // Update timer
    @Override
    public void update(int deltaTime) {
        timeOut -= deltaTime / 1000.0;
        if(timeOut < 0) timeOut = 0;
        timeOutStr = (int)timeOut + "s";
    }

    // Draw overlay text and timer
    @Override
    public void paint(Graphics g) {
        g.setFont(statusFont);
        int strWidth = g.getFontMetrics().stringWidth(statusText);
        g.setColor(new Color(184, 154, 143, 204));
        g.fillRect(centre.x-strWidth/2-10, centre.y-65, strWidth+20, 60);
        g.setColor(Color.BLACK);
        g.drawString(statusText, centre.x-strWidth/2, centre.y-20);

        strWidth = g.getFontMetrics().stringWidth(timeOutStr);
        g.drawString(timeOutStr, centre.x-strWidth/2-2, centre.y-38);
        g.setColor(timeOut < 6 ? Color.RED : Color.YELLOW);
        g.drawString(timeOutStr, centre.x-strWidth/2, centre.y-40);
    }

    // Show overlay with status text and timer
    @Override
    public void showOverlay(TurnActionFactory.TurnDecisionAction currentAction) {
        setEnabled(true);
        statusText = createContextString(currentAction);
        timeOut = CurrentGameInterface.getCurrentGame().getRuleSet().getDefaultTimeOut();
        timeOutStr = (int)timeOut + "s";
    }

    // Generate status message based on current action
    private String createContextString(TurnActionFactory.TurnDecisionAction currentAction) {
        String playerName = CurrentGameInterface.getCurrentGame().getCurrentPlayer().getPlayerName();
        String result;
        switch(currentAction.flagName) {
            case "keepOrPlay" -> result = "choosing to Keep or Play.";
            case "wildColour" -> result = "choosing Wild Colour.";
            case "isChallenging" -> result = "choosing Response to +4.";
            case "otherPlayer" -> result = "choosing Other Player to Swap With.";
            default -> result = "thinking...";
        }
        if(CurrentGameInterface.getCurrentGame().getCurrentPlayer().getPlayerType() == Player.PlayerType.ThisPlayer) {
            return "You are " + result;
        }
        return playerName + " is " + result;
    }
}
