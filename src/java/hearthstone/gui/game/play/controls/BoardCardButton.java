package hearthstone.gui.game.play.controls;

import hearthstone.gui.SizeConfigs;
import hearthstone.gui.controls.ImageButton;
import hearthstone.gui.credetials.CredentialsFrame;
import hearthstone.gui.game.GameFrame;
import hearthstone.models.card.Card;
import hearthstone.models.card.CardType;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.card.weapon.WeaponCard;
import hearthstone.util.SoundPlayer;
import hearthstone.util.getresource.ImageResource;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class BoardCardButton extends ImageButton implements MouseListener, MouseMotionListener {
    private int width, height;
    private double initialRotate, rotate;
    private boolean showBig;
    private Card card;
    private boolean isBack, isInLand;

    private SoundPlayer soundPlayer;

    private BufferedImage minionImage;
    private BufferedImage cardImage;
    private BufferedImage frameImage;

    private static BufferedImage cardBackImage;
    private static BufferedImage deathRattleImage;
    private static BufferedImage divineShieldImage;
    private static BufferedImage triggeredEffectImage;
    private static BufferedImage shieldFrameImage;
    private static BufferedImage ovalFrameImage;
    private static BufferedImage shieldFrameImageActive;
    private static BufferedImage ovalFrameImageActive;

    private int id;

    public BoardCardButton(Card card, int width, int height) {
        this.card = card;
        this.width = width;
        this.height = height;
        isBack = true;

        configButton();
    }

    public BoardCardButton(Card card, int width, int height, int id) {
        this.card = card;
        this.id = id;

        this.width = width;
        this.height = height;

        configButton();
    }

    public BoardCardButton(Card card, int width, int height, int initialRotate, int id) {
        this.card = card;
        this.initialRotate = initialRotate;
        this.id = id;
        rotate = initialRotate;

        this.width = width;
        this.height = height;

        configButton();
    }

    public BoardCardButton(Card card, int width, int height, boolean showBig, int id) {
        this.card = card;
        this.id = id;
        this.showBig = showBig;

        this.width = width;
        this.height = height;

        configButton();
    }

    public BoardCardButton(Card card, int width, int height, boolean showBig, int id, boolean isInLand) {
        this.card = card;
        this.id = id;
        this.showBig = showBig;
        this.isInLand = true;

        this.width = width;
        this.height = height;

        configButton();
    }

    public BoardCardButton(Card card, int width, int height, int initialRotate, boolean showBig, int id) {
        this.card = card;

        this.width = width;
        this.height = height;

        this.initialRotate = initialRotate;
        rotate = initialRotate;

        this.showBig = showBig;

        this.id = id;

        configButton();
    }

    public boolean isShowBig() {
        return showBig;
    }

    private void configButton() {
        setPreferredSize(new Dimension(width, height));
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(this);

        try {
            if (cardBackImage == null)
                cardBackImage = ImageResource.getInstance().getImage("/images/cards/cards_back/" + "card_back2" + ".png");

            if (deathRattleImage == null)
                deathRattleImage = ImageResource.getInstance().getImage(
                        "/images/death_rattle.png");
            if (divineShieldImage == null)
                divineShieldImage = ImageResource.getInstance().getImage(
                        "/images/divine_shield.png");
            if (triggeredEffectImage == null)
                triggeredEffectImage = ImageResource.getInstance().getImage(
                        "/images/triggered_effect.png");

            if (minionImage == null && card.getCardType() == CardType.MINIONCARD)
                minionImage = ImageResource.getInstance().getImage("/images/cards/oval_minions/" + card.getName().
                        toLowerCase().replace(' ', '_').replace("'", "") + ".png");
            if (cardImage == null)
                cardImage = ImageResource.getInstance().getImage(
                        "/images/cards/" + card.getName().toLowerCase().
                                replace(' ', '_').replace("'", "") + ".png");
            if (shieldFrameImage == null)
                shieldFrameImage = ImageResource.getInstance().getImage("/images/minion_shield.png");
            if (shieldFrameImageActive == null)
                shieldFrameImageActive = ImageResource.getInstance().getImage("/images/minion_shield_active.png");
            if (ovalFrameImage == null)
                ovalFrameImage = ImageResource.getInstance().getImage("/images/minion_played.png");
            if (ovalFrameImageActive == null)
                ovalFrameImageActive = ImageResource.getInstance().getImage("/images/minion_played_active.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        String path;

        switch (card.getCardType()) {
            case MINIONCARD:
                path = "/sounds/cards/" + card.getName().toLowerCase().replace(' ', '_') + ".wav";
                break;
            case REWARDCARD:
                path = "/sounds/rewards/" + "reward" + ".wav";
                break;
            case SPELL:
                path = "/sounds/spells/" + "spell" + ".wav";
                break;
            case WEAPONCARD:
                path = "/sounds/weapons/" + "weapon" + ".wav";
                break;
            default:
                return;
        }
        if (soundPlayer == null)
            soundPlayer = new SoundPlayer(path);
        soundPlayer.playOnce();
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public double getInitialRotate() {
        return initialRotate;
    }

    public int getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    @Override
    protected void paintComponent(Graphics g) {
        drawCard(g);
    }

    public void drawCard(Graphics g) {
        // DRAW IMAGE
        Graphics2D g2 = (Graphics2D) g;

        if (card.getCardType() == CardType.MINIONCARD && isInLand) {
            drawMinionInLand(g2);
            return;
        }

        BufferedImage image = null;

        try {
            if (!isBack) {
                image = cardImage;
            } else {
                image = cardBackImage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rotate > 0) {
            g2.rotate(Math.toRadians(rotate), width / 2, height / 2);
        }

        g2.drawImage(image.getScaledInstance(width, height,
                Image.SCALE_SMOOTH),
                0, 0, width, height, null);

        if (isBack)
            return;

        Font font = CredentialsFrame.getInstance().getCustomFont(0, 30);
        FontMetrics fontMetrics = g2.getFontMetrics(font);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setFont(font);

        // DRAW TEXT
        drawStringOnCard(g2, Color.WHITE, fontMetrics);
    }

    void drawMinionInLand(Graphics2D g) {
        try {
            if (frameImage == null && ((MinionCard) card).isTaunt()) {
                frameImage = shieldFrameImage;
            } else if (frameImage == null && !((MinionCard) card).isTaunt()) {
                frameImage = ovalFrameImage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.drawImage(minionImage.getScaledInstance(
                width - 18 - 10, height - 28 - 15,
                Image.SCALE_SMOOTH),
                9, 5,
                width - 18 - 10, height - 28 - 15,
                null);

        g.drawImage(frameImage.getScaledInstance(
                width - 10, height - 15,
                Image.SCALE_SMOOTH),
                0, 0,
                width - 10, height - 15,
                null);

        try {
            if (((MinionCard) card).isDeathRattle()) {
                g.drawImage(deathRattleImage.getScaledInstance(
                        SizeConfigs.minionTypeWidth, SizeConfigs.minionTypeHeight,
                        Image.SCALE_SMOOTH),
                        (width - 10) / 2 - SizeConfigs.minionTypeWidth / 2, height - 42,
                        SizeConfigs.minionTypeWidth, SizeConfigs.minionTypeHeight,
                        null);
            }
            if (((MinionCard) card).isDivineShield()) {
                g.drawImage(divineShieldImage.getScaledInstance(
                        width - 10, height - 5,
                        Image.SCALE_SMOOTH),
                        0, 0,
                        width - 10, height - 5,
                        null);
            }
            if (((MinionCard) card).isTriggeredEffect()) {
                g.drawImage(triggeredEffectImage.getScaledInstance(
                        SizeConfigs.minionTypeWidth, SizeConfigs.minionTypeHeight,
                        Image.SCALE_SMOOTH),
                        (width - 10) / 2 - SizeConfigs.minionTypeWidth / 2, height - 42,
                        SizeConfigs.minionTypeWidth, SizeConfigs.minionTypeHeight,
                        null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font font = CredentialsFrame.getInstance().getCustomFont(0, 30);
        FontMetrics fontMetrics = g.getFontMetrics(font);

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setFont(font);

        // DRAW TEXT

        drawStringOnMinionOnLand(g, Color.WHITE, fontMetrics);
    }

    void drawStringOnMinionOnLand(Graphics2D g, Color color, FontMetrics fontMetrics) {
        String text;
        int textWidth;

        final int minionAttackX = 25 - 5;
        final int minionAttackY = height - 37;
        final int minionHealthX = width - 30;
        final int minionHealthY = height - 37;

        g.setColor(color);
        Font font = GameFrame.getInstance().getCustomFont(0, 30);
        g.setFont(font);

        text = String.valueOf(((MinionCard) card).getAttack());
        textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, minionAttackX - textWidth / 2,
                minionAttackY);

        text = String.valueOf(((MinionCard) card).getHealth());
        textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, minionHealthX - textWidth / 2,
                minionHealthY);
    }

    void drawStringOnCard(Graphics2D g, Color color, FontMetrics fontMetrics) {
        final int spellManaX = 14;
        final int spellManaY = 25;

        final int heroPowerManaX = width / 2 + 3;
        final int heroPowerManaY = 20;

        final int minionManaX = 14;
        final int minionManaY = 25;
        final int minionAttackX = 25 - 10;
        final int minionAttackY = height - 15 + 10;
        final int minionHealthX = width - 15 + 10 - 3;
        final int minionHealthY = height - 15 + 10;

        final int weaponManaX = 14;
        final int weaponManaY = 25;
        final int weaponDurabilityX = width - 15 + 10;
        final int weaponDurabilityY = height - 15 + 10;
        final int weaponAttackX = 25 - 10;
        final int weaponAttackY = height - 15 + 10;

        g.setColor(color);
        Font font = GameFrame.getInstance().getCustomFont(0, 20);
        g.setFont(font);

        String text;
        int textWidth;
        switch (card.getCardType()) {
            case SPELL:
            case REWARDCARD:
                text = String.valueOf(card.getManaCost());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, spellManaX - textWidth / 2,
                        spellManaY);
                break;
            case HEROPOWER:
                text = String.valueOf(card.getManaCost());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, heroPowerManaX - textWidth / 2,
                        heroPowerManaY);
                break;
            case MINIONCARD:
                text = String.valueOf(card.getManaCost());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, minionManaX - textWidth / 2,
                        minionManaY);

                text = String.valueOf(((MinionCard) card).getAttack());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, minionAttackX - textWidth / 2,
                        minionAttackY);

                text = String.valueOf(((MinionCard) card).getHealth());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, minionHealthX - textWidth / 2,
                        minionHealthY);
                break;
            case WEAPONCARD:
                text = String.valueOf(card.getManaCost());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, weaponManaX - textWidth / 2,
                        weaponManaY);

                text = String.valueOf(((WeaponCard) card).getAttack());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, weaponAttackX - textWidth / 2,
                        weaponAttackY);

                text = String.valueOf(((WeaponCard) card).getDurability());
                textWidth = fontMetrics.stringWidth(text);
                g.drawString(text, weaponDurabilityX - textWidth / 2,
                        weaponDurabilityY);
                break;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }

    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if (card.getCardType() == CardType.MINIONCARD && isInLand) {
            if (((MinionCard) card).isTaunt()) {
                frameImage = shieldFrameImageActive;
            } else {
                frameImage = ovalFrameImageActive;
            }
        }
        this.repaint();
        this.revalidate();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if (card.getCardType() == CardType.MINIONCARD && isInLand) {
            if (((MinionCard) card).isTaunt()) {
                frameImage = shieldFrameImage;
            } else {
                frameImage = ovalFrameImage;
            }
        }
        this.repaint();
        this.revalidate();
    }
}