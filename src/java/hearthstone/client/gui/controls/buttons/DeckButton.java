package hearthstone.client.gui.controls.buttons;

import hearthstone.client.data.ClientData;
import hearthstone.client.gui.game.GameFrame;
import hearthstone.models.Deck;
import hearthstone.shared.GUIConfigs;
import hearthstone.util.FontType;
import hearthstone.util.getresource.ImageResource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DeckButton extends ImageButton {
    int width, height;
    private Deck deck;

    private BufferedImage heroImage;
    private static BufferedImage deckImage;

    private final int stringDis = 36;
    private final int stringStartY = 45;
    private final int maxCardNameWidth = 200;

    public DeckButton(Deck deck, int width, int height) {
        this.deck = deck;
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // DRAW IMAGE
        Graphics2D g2 = (Graphics2D) g;

        try {
            if (heroImage == null)
                heroImage = ImageResource.getInstance().getImage(
                        "/images/heroes/circle_heroes/" +
                                ClientData.getHeroByType(deck.getHeroType()).getName().toLowerCase().replace(' ', '_')
                                + ".png");

            if (deckImage == null)
                deckImage = ImageResource.getInstance().getImage("/images/deck.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        g2.drawImage(heroImage.getScaledInstance(GUIConfigs.bigCircleHeroWidth, GUIConfigs.bigCircleHeroHeight,
                Image.SCALE_SMOOTH), 20, 20,
                GUIConfigs.bigCircleHeroWidth, GUIConfigs.bigCircleHeroHeight, null);

        g2.drawImage(deckImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,
                width, height, null);

        drawStringOnDeck(g2);
    }

    private int getSize(String text, int maxWidth) {
        int size = 20;
        Font font = GameFrame.getInstance().getCustomFont(FontType.TEXT, 0, size);
        while (getFontMetrics(font).stringWidth(text) > maxWidth) {
            size--;
            font = GameFrame.getInstance().getCustomFont(FontType.TEXT, 0, size);
        }
        return size;
    }

    private void drawStringOnDeck(Graphics2D g2) {
        Font font = GameFrame.getInstance().getCustomFont(FontType.TEXT, 0, 15);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setFont(font);
        g2.setColor(new Color(69, 27, 27));

        g2.drawString("Cup: " + deck.getCup(), 380, stringStartY);
        g2.drawString("Win rate: " + deck.getWinPercentage() + "%", 380, stringStartY + stringDis);
        g2.drawString("Wins: " + deck.getWinGames(), 380, stringStartY + 2 * stringDis);

        g2.setFont(font);
        g2.drawString("Mana average: " + String.valueOf(deck.getManaAv()), 155, stringStartY);
        g2.drawString("Name: " + deck.getName(), 163, stringStartY + stringDis);

        if (deck.getBestCardName() == null) {
            String text = "Favorite card: no card";
            font = GameFrame.getInstance().getCustomFont(FontType.TEXT, 0, getSize(text, maxCardNameWidth));
            g2.setFont(font);

            g2.drawString("Favorite card: no card", 155, stringStartY + 2 * stringDis);
        } else {
            String text = "Favorite card: " + deck.getBestCardName();
            font = GameFrame.getInstance().getCustomFont(FontType.TEXT, 0, getSize(text, maxCardNameWidth));
            g2.setFont(font);

            g2.drawString("Favorite card: " + deck.getBestCardName(), 155, stringStartY + 2 * stringDis);
        }
    }
}
