package hearthstone.client.gui.controls.panels;

import hearthstone.client.gui.controls.buttons.CardButton;
import hearthstone.models.card.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardsPanel extends JPanel {
    private ArrayList<Card> originalCards, compressedCards;
    private ArrayList<JPanel> originalPanels, compressedPanels;
    private ArrayList<CardButton> originalButtons, compressedButtons;
    private int cardHeight, cardWidth;

    private int disX = 10;
    private int disY = 5;
    private int rows;

    public CardsPanel(ArrayList<Card> originalCards, ArrayList<JPanel> originalPanels,
                      int rows, int cardWidth, int cardHeight) {
        this.originalCards = originalCards;
        this.originalPanels = originalPanels;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.rows = rows;

        originalButtons = new ArrayList<>();

        makeCompressedCards();

        configPanel();

        layoutComponent();
    }

    private int numberOfCards(int id) {
        int ans = 0;
        for (Card card : originalCards) {
            if (card.getId() == id)
                ans++;
        }
        return ans;
    }

    private void makeCompressedCards() {
        compressedCards = new ArrayList<>();
        compressedPanels = new ArrayList<>();
        compressedButtons = new ArrayList<>();

        originalButtons = new ArrayList<>();

        for(int i = 0; i < originalCards.size(); i++){
            Card card = originalCards.get(i);

            CardButton cardButton = new CardButton(card,
                    cardWidth,
                    cardHeight, numberOfCards(card.getId()));
            originalButtons.add(cardButton);
        }

        for (int i = 0; i < originalCards.size(); i++) {
            boolean shouldAdd = true;
            for (int j = 0; j < i; j++) {
                if (originalCards.get(i).getId() == originalCards.get(j).getId()) {
                    shouldAdd = false;
                }
            }
            if (shouldAdd) {
                compressedCards.add(originalCards.get(i));
                compressedPanels.add(originalPanels.get(i));
                compressedButtons.add(originalButtons.get(i));
            }
        }
    }

    private void configPanel() {
        disX = 10;
        disY = 5;
        disY += cardHeight;
        for (JPanel panel : originalPanels) {
            if (panel != null) {
                disY += panel.getPreferredSize().getHeight();
                break;
            }
        }
        disX += cardWidth;

        setLayout(null);
        setBackground(new Color(0, 0, 0, 120));
        setPreferredSize(
                new Dimension((compressedCards.size() + rows - 1) / rows * disX,
                        rows * disY));
        setOpaque(false);
        setVisible(true);
    }

    public void addCard(Card card, JPanel panel) {
        int number = numberOfCards(card.getId());
        CardButton cardButton = new CardButton(card,
                cardWidth,
                cardHeight, number + 1);
        originalButtons.add(cardButton);
        originalCards.add(card);
        originalPanels.add(panel);

        restart();
    }

    public void removeCard(Card card) {
        for(int i = 0; i < originalCards.size(); i++){
            Card card1 = originalCards.get(i);
            if(card.getId() == card1.getId()) {
                originalButtons.remove(i);
                originalCards.remove(i);
                originalPanels.remove(i);
                break;
            }
        }

        restart();
    }

    private void layoutComponent() {
        for (int i = 0; i < compressedCards.size(); i++) {
            CardButton cardButton = compressedButtons.get(i);
            JPanel panel = compressedPanels.get(i);

            int col = (i - (i % rows)) / rows;
            int row = i % rows;

            cardButton.setBounds(col * disX, row * disY,
                    cardWidth, cardHeight);
            if (panel != null) {
                panel.setBounds(col * disX + cardWidth / 2
                                - (int) panel.getPreferredSize().getWidth() / 2,
                        row * disY + cardHeight,
                        (int) panel.getPreferredSize().getWidth(),
                        (int) panel.getPreferredSize().getHeight());
            }
            add(cardButton);
            if (panel != null)
                add(panel);
        }
    }

    public void update(ArrayList<Card> cards, ArrayList<JPanel> panels) {
        ArrayList<Card> temp = new ArrayList<>();
        for (Card card : this.originalCards)
            temp.add(card);

        for (Card card : temp) {
            removeCard(card);
        }

        for (int i = 0; i < cards.size(); i++) {
            addCard(cards.get(i), panels.get(i));
        }

        restart();
    }

    private void restart() {
        removeAll();

        makeCompressedCards();
        configPanel();
        layoutComponent();

        revalidate();
        getParent().repaint();
    }
}
