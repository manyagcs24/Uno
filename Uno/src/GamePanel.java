import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static final int PANEL_HEIGHT = 720; // panel height
    public static final int PANEL_WIDTH = 1280; // panel width

    private final PauseInterface pauseWnd; // pause menu
    private WndInterface activeInterface; // current interface
    public static boolean DEBUG_MODE; // debug flag

    // constructor, sets up panel and starts timer
    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Theme.BACKGROUND);

        pauseWnd = new PauseInterface(new Rectangle(PANEL_WIDTH/2-100,PANEL_HEIGHT/2-100,200,200), this);
        pauseWnd.setEnabled(false);

        showLobby(); // start with lobby

        Timer updateTimer = new Timer(20, this); // 50 FPS update
        updateTimer.start();

        addMouseListener(this);
        addMouseMotionListener(this);
        DEBUG_MODE = false;
    }

    // show lobby interface
    public void showLobby() {
        if(!(activeInterface instanceof LobbyInterface)) {
            activeInterface = new LobbyInterface(new Rectangle(0, 0, PANEL_WIDTH, PANEL_HEIGHT), this);
        }
        setPauseState(false);
    }

    // show post-game interface
    public void showPostGame(List<Player> playerList, RuleSet ruleSet) {
        activeInterface = new PostGameInterface(new Rectangle(0,0,PANEL_WIDTH, PANEL_HEIGHT),
                                                playerList, ruleSet, this);
    }

    // start a new game from lobby
    public void startGame(List<LobbyPlayer> playerList, RuleSet ruleSet) {
        activeInterface = new CurrentGameInterface(new Rectangle(0,0,PANEL_WIDTH,PANEL_HEIGHT),
                                                    ruleSet, playerList, this);
    }

    // start next round
    public void startNextRound(List<Player> playerList, RuleSet ruleSet) {
        activeInterface = new CurrentGameInterface(new Rectangle(0,0,PANEL_WIDTH,PANEL_HEIGHT),
                playerList, ruleSet, this);
    }

    // draw panel
    public void paint(Graphics g) {
        super.paint(g);
        if(activeInterface != null) activeInterface.paint(g);
        if(pauseWnd.isEnabled()) pauseWnd.paint(g);
        if(DEBUG_MODE) {
            g.setColor(Theme.TEXT);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("DEBUG ON", 10,20);
        }
    }

    // pause/unpause game
    public void setPauseState(boolean isPaused) {
        if(activeInterface != null) activeInterface.setEnabled(!isPaused);
        pauseWnd.setEnabled(isPaused);
    }

    // quit game
    public void quitGame() {
        System.exit(0);
    }

    // handle key input
    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) setPauseState(!pauseWnd.isEnabled());
        else if(keyCode == KeyEvent.VK_0) DEBUG_MODE = !DEBUG_MODE;
        else activeInterface.handleInput(keyCode);
        repaint();
    }

    // handle mouse press
    @Override
    public void mousePressed(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        pauseWnd.handleMousePress(mousePosition, e.getButton() == 1);
        if(activeInterface != null) activeInterface.handleMousePress(mousePosition, e.getButton() == 1);
        repaint();
    }

    // draw UNO title
    public void paintUnoTitle(Graphics g, Rectangle bounds) {
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("UNO!", bounds.width/2-40, 50);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Developed by BMSCE", bounds.width/2-70, 65);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Card.getColourByID(0)); g.drawString("U", bounds.width/2-40+2, 48);
        g.setColor(Card.getColourByID(1)); g.drawString("N", bounds.width/2-40+32, 48);
        g.setColor(Card.getColourByID(2)); g.drawString("O", bounds.width/2-40+62, 48);
        g.setColor(Card.getColourByID(3)); g.drawString("!", bounds.width/2-40+92, 48);
    }

    // handle mouse movement
    @Override
    public void mouseMoved(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        pauseWnd.handleMouseMove(mousePosition);
        if(activeInterface != null) activeInterface.handleMouseMove(mousePosition);
        repaint();
    }

    // update game timer
    @Override
    public void actionPerformed(ActionEvent e) {
        if(activeInterface != null) activeInterface.update(20);
        repaint();
    }

    // unused mouse events
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
}
