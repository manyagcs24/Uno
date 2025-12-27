import java.awt.*;

// Button to call out players who didn’t say UNO
public class AntiUnoButton extends UnoButton implements GeneralOverlayInterface {
    
    // Constructor sets position
    public AntiUnoButton(Position position) {
        super(position);
    }

    // Updates if any player is vulnerable to being called out
    @Override
    public void update(int deltaTime) {
        isActive = false;
        for(Player player : CurrentGameInterface.getCurrentGame().getAllPlayers()) {
            if(player != bottomPlayer && !player.isSafe() && player.getHand().size() == 1) {
                isActive = true;
            }
        }
    }

    // Draws the button if active
    @Override
    public void paint(Graphics g) {
        if(!isActive) return;

        drawButtonBackground(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        int strWidth = g.getFontMetrics().stringWidth("!");
        g.drawString("!", bounds.position.x+bounds.width/2-strWidth/2-2, bounds.position.y+bounds.height/2+22);
        g.setColor(new Color(226, 173, 67));
        g.drawString("!", bounds.position.x+bounds.width/2-strWidth/2, bounds.position.y+bounds.height/2+20);
    }

    // Checks click to call out players
    @Override
    public void handleMousePress(Position mousePosition, boolean isLeft) {
        if(isActive && bounds.isPositionInside(mousePosition)) {
            for(Player player : CurrentGameInterface.getCurrentGame().getAllPlayers()) {
                if(player != bottomPlayer && !player.isSafe() && player.getHand().size() == 1) {
                    CurrentGameInterface.getCurrentGame().applyAntiUno(player.getPlayerID());
                }
            }
        }
    }
}
