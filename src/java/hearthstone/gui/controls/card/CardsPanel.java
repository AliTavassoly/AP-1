package hearthstone.gui.controls.card;

import hearthstone.HearthStone;
import hearthstone.data.DataBase;
import hearthstone.gui.game.GameFrame;
import hearthstone.logic.models.card.Card;
import hearthstone.util.HearthStoneException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardsPanel extends JPanel {
    private ArrayList<Card> cards;
    private ArrayList<JPanel> panels;
    private ArrayList<CardButton> cardButtons;
    private int cardHeight, cardWidth;

    private int disX = 10;
    private int disY = 5;
    private int rows;

    public CardsPanel(ArrayList<Card> cards, ArrayList<JPanel> panels,
                      int rows, int cardWidth, int cardHeight) {
        this.cards = cards;
        this.panels = panels;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.rows = rows;

        cardButtons = new ArrayList<>();

        for (Card card : cards) {
            CardButton cardButton = new CardButton(card,
                    cardWidth,
                    cardHeight);
            cardButtons.add(cardButton);
        }

        configPanel();

        layoutComponent();
    }

    private void configPanel() {
        disX = 10;
        disY = 5;
        disY += cardHeight;
        for (JPanel panel : panels) {
            if (panel != null) {
                disY += panel.getPreferredSize().getHeight();
                break;
            }
        }
        disX += cardWidth;

        setLayout(null);
        setBackground(new Color(0, 0, 0, 120));
        setPreferredSize(
                new Dimension((cards.size() + rows - 1) / rows * disX, rows * disY));
        setOpaque(false);
        setVisible(true);
    }

    public void addCard(Card card, JPanel panel) {
        CardButton cardButton = new CardButton(card,
                cardWidth,
                cardHeight);

        cardButtons.add(cardButton);
        cards.add(card);
        panels.add(panel);

        restart();
    }

    public void removeCard(Card card) {
        int ind = cards.indexOf(card);

        cardButtons.remove(ind);
        cards.remove(ind);
        panels.remove(ind);

        restart();
    }

    private void layoutComponent() {
        for (int i = 0; i < cards.size(); i++) {
            CardButton cardButton = cardButtons.get(i);
            JPanel panel = panels.get(i);

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

    private void restart() {
        try {
            DataBase.save();
        } catch (HearthStoneException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        removeAll();
        configPanel();
        layoutComponent();
        getParent().repaint();
    }
}
