import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//represents player in a game
public class Player {
    //player type
    public enum PlayerType { ThisPlayer, AIPlayer}

    //uno safety state
    public enum UNOState { Safe, Called, NotSafe }

    //player identity
    private final int playerID;   
    private final String playerName; 
    private final PlayerType playerType;  

    //area where cards are drawn
    private final Rectangle bounds;
    
    //players hand
    private final List<Card> hand;   //collection of cards in players hands as array
    private Card hoveredCard;      //The card that the player is currently hovering their mouse over.
    private boolean showCards;    //When true the cards for this player are revealed face-up.
  
    //scoring
    private int totalScore;   
    private int currentRoundScore;   
    private boolean wonRound;
    
    // Name display position
    private final boolean showPlayerNameLeft;
    
    // Current UNO state
    private UNOState unoState;

    // Create a player with empty hand
    public Player(int playerID, String playerName, PlayerType playerType, Rectangle bounds, boolean showPlayerNameLeft) {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerType = playerType;
        this.bounds = bounds;
        this.showPlayerNameLeft = showPlayerNameLeft;
        hand = new ArrayList<>();
        showCards = playerType == PlayerType.ThisPlayer;
        wonRound = false;
        totalScore = currentRoundScore = 0;
        unoState = UNOState.Safe;
    }

    // Update player (unused)
    public void update(int deltaTime) {

    }

    // Draw cards and player name
    public void paint(Graphics g) {
        if(showCards) {
            hand.forEach(card -> card.paint(g));
        } else {
            hand.forEach(card -> Card.paintCardBack(g, card));
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(playerName);
        g.setColor(new Color(1,1,1, 204));
        int nameXOffset = bounds.position.x + (showPlayerNameLeft ? -(strWidth-50) : (bounds.width/2-(strWidth+30)/2));
        int nameYOffset = bounds.position.y + (showPlayerNameLeft ? (bounds.height/2-20) : -10);
        g.fillRect(nameXOffset, nameYOffset, strWidth+30, 40);
        g.setColor(CurrentGameInterface.getCurrentGame().getCurrentPlayer().getPlayerID() == getPlayerID()
                ? Color.ORANGE : Color.WHITE);
        g.drawString(playerName, nameXOffset+15, nameYOffset+25);
    }

    // Add a card and reposition hand
    public void addCardToHand(Card card) {
        hand.add(card);
        recalculateCardPositions();
    }

  
    public void emptyHand() {   //empties the hand
        hand.clear();
    }

    // Show or hide cards
    public void revealHand(boolean reveal) {   
        showCards = reveal;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public int getPlayerID() {
        return playerID;
    }

    // Get playable cards for current pile
    public List<Card> getValidMoves(int curFaceValue, int curColourValue) {
        List<Card> result = new ArrayList<>();
        for(Card card : hand) {
            if(card.getFaceValueID() == curFaceValue || card.getColourID() == curColourValue
            || card.getFaceValueID() == 13 || card.getFaceValueID() == 14) {
                result.add(card);
            }
        }
        return result;
    }

    // Sort cards by colour then face value
    public void sortHand() {
        Comparator<Card> compareByCard = Comparator
                .comparing(Card::getColourID)
                .thenComparing(Card::getFaceValueID);
        hand.sort(compareByCard);
        recalculateCardPositions();
    }

    // Update hovered card
    public void updateHover(Position mousePosition) {
        if(hoveredCard != null && !hoveredCard.isPositionInside(mousePosition)) {
            hoveredCard = null;
        }
        for(Card card : hand) {
            if(card.isPositionInside(mousePosition)) {
                hoveredCard = card;
                break;
            }
        }
        recalculateCardPositions();
    }

    // Remove a card from hand
    public void removeCard(Card card) {
        hand.remove(card);
        recalculateCardPositions();
    }

    // Find card by ID
    public Card getCardByID(int cardID) {
        for(Card card : hand) {
            if(card.getCardID() == cardID) {
                return card;
            }
        }
        return null;
    }

    /**
     * Updates the hovering position. Then returns any currently hovered card.
     *
     * @param mousePosition Position of the mouse.
     * @return The currently hovered card (can be null if none).
     */
    public Card chooseCardFromClick(Position mousePosition) {
        updateHover(mousePosition);
        return hoveredCard;
    }

    /**
     * Gets all the cards in the player's hand.
     *
     * @return The list of cards in this player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Gets the player name.
     *
     * @return The player name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Adds up the score of all cards in the current hand.
     *
     * @return A total score for all the cards in the hand.
     */
    public int getHandTotalScore() {
        int score = 0;
        for (Card card : hand) {
            score += card.getScoreValue();
        }
        return score;
    }

    /**
     * Gets the centre of the player's region.
     *
     * @return Centre of the bounds where cards are drawn.
     */
    public Position getCentreOfBounds() {
        return bounds.getCentre();
    }

    /**
     * Recalculates positions for all cards by calculating numbers of
     * rows and columns then centring inside the region and applying
     * positions to all cards in the hand.
     */
    private void recalculateCardPositions() {
        int paddingX = -15;
        int paddingY = (playerType == PlayerType.ThisPlayer) ? 10 : -Card.CARD_HEIGHT/2-10;
        int elementsPerRow = (bounds.width+paddingX)/Card.CARD_WIDTH;
        int rows = (int)Math.ceil(hand.size()/(double)elementsPerRow);
        int startY = bounds.position.y+bounds.height/2-rows*(Card.CARD_HEIGHT+paddingY)/2;
        int x = 0;
        int y = 0;
        int remainingElements = hand.size();
        int rowXOffset = bounds.width/2-(int)(elementsPerRow*(Card.CARD_WIDTH+paddingX)/2.0);

        // True when there is only one not-full row (used to centre in that row).
        if(remainingElements < elementsPerRow) {
            rowXOffset = bounds.width/2-(int)(remainingElements*(Card.CARD_WIDTH+paddingX)/2.0);
        }
        for(Card card : hand) {
            // Apply a visual offset to the hovered card
            int hoverOffset = (card == hoveredCard) ? -10 : 0;
            card.position.setPosition(bounds.position.x + rowXOffset + x*(Card.CARD_WIDTH+paddingX),
                                     startY + y*(Card.CARD_HEIGHT+paddingY) + hoverOffset);
            x++;
            remainingElements--;
            // Check for iterating to the next row.
            if(x >= elementsPerRow) {
                x = 0;
                y++;
                rowXOffset = bounds.width/2-(int)(elementsPerRow*(Card.CARD_WIDTH+paddingX)/2.0);
                // Once a not full row has been found (used to centre in that row).
                if(remainingElements < elementsPerRow) {
                    rowXOffset = bounds.width/2-(int)(remainingElements*(Card.CARD_WIDTH+paddingX)/2.0);
                }
            }
        }
    }

    // Update round score and total score
    public void setCurrentRoundScore(int newCurrentRoundScore) {
        this.currentRoundScore = newCurrentRoundScore;
        totalScore += currentRoundScore;
    }

    // Mark player as winner
    public void setWon() {
        wonRound = true;
    }

    public boolean getWon() {
        return wonRound;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getCurrentRoundScore() {
        return currentRoundScore;
    }

    // Reset all scores and UNO state
    public void resetScore() {
        totalScore = 0;
        currentRoundScore = 0;
        wonRound = false;
        unoState = UNOState.Safe;
    }

    // Update UNO state with valid transitions
    public void setUnoState(UNOState unoState) {
        if(this.unoState == UNOState.Called && unoState == UNOState.NotSafe) {
            return;
        }
        this.unoState = unoState;
    }

    //check if player is safe
    public boolean isSafe() {
        return unoState != UNOState.NotSafe;
    }

    public UNOState getUnoState() {
        return unoState;
    }
}
