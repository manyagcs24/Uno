import java.awt.*;
//Shows UNO! for a player when they called UNO!
public class UNOCalledOverlay extends PlayerFlashOverlay {
    //sets up the overlay ready to show
    public UNOCalledOverlay(Position position) {
        super(position, "UNO!", Color.RED, 40);
        setEnabled(false);
    }

    //draws the UNO! text
    @Override
    public void paint(Graphics g) {
        if(displayTimer % 200 < 150) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString(message, bounds.position.x, bounds.position.y);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            for(int i = 0; i < message.length(); i++) {
                g.setColor(Card.getColourByID(i % 4));
                g.drawString(message.charAt(i)+"", bounds.position.x+2+i*30, bounds.position.y-2);
            }
        }
    }
}
