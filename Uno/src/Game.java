import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Entry point and main frame for UNO
public class Game implements KeyListener {

    // The panel that handles game logic and rendering
    private final GamePanel gamePanel;

    // Main method to start the game
    public static void main(String[] args) {
        new Game();
    }

    // Create JFrame, add GamePanel, attach key listener
    public Game() {
        JFrame frame = new JFrame("Uno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        gamePanel = new GamePanel();
        frame.getContentPane().add(gamePanel);

        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }

    // Pass key press events to GamePanel
    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleInput(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}   // Not used
    @Override
    public void keyReleased(KeyEvent e) {} // Not used
}
