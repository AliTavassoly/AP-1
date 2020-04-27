package hearthstone.gui.game.collection;

import hearthstone.HearthStone;
import hearthstone.data.DataBase;
import hearthstone.gui.BaseFrame;
import hearthstone.gui.DefaultSizes;
import hearthstone.gui.controls.ImageButton;
import hearthstone.gui.controls.SureDialog;
import hearthstone.gui.controls.TextField;
import hearthstone.gui.controls.card.CardsPanel;
import hearthstone.gui.controls.hero.HeroButton;
import hearthstone.gui.controls.icons.BackIcon;
import hearthstone.gui.controls.icons.CloseIcon;
import hearthstone.gui.controls.icons.LogoutIcon;
import hearthstone.gui.controls.icons.MinimizeIcon;
import hearthstone.gui.credetials.CredentialsFrame;
import hearthstone.gui.game.GameFrame;
import hearthstone.gui.util.CustomScrollBarUI;
import hearthstone.logic.models.Deck;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.hero.Hero;
import hearthstone.logic.models.hero.HeroType;
import hearthstone.util.HearthStoneException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DeckArrangement extends JPanel {
    private ImageButton backButton, minimizeButton, closeButton, logoutButton;
    private ImageButton searchButton, allCardsButton, myCardsButton, lockCardsButton, deleteButton;
    private CardsPanel cardsPanel, deckCardsPanel;
    private JScrollPane cardsScroll, deckCardsScroll;
    private HeroButton heroButton;
    private JLabel nameLabel, manaLabel;
    private TextField nameField, manaField;
    private int selectedButton;
    private Deck deck;
    private Hero hero;

    private final int iconX = 20;
    private final int startIconY = 20;
    private final int endIconY = DefaultSizes.gameFrameHeight - DefaultSizes.iconHeight - 20;
    private final int iconsDis = 70;

    private final int listDis = 50;
    private final int startListY = (DefaultSizes.gameFrameHeight - 2 * DefaultSizes.arrangementListHeight - listDis) / 2;
    private final int startListX = 100;
    private final int endListX = startListX + DefaultSizes.arrangementListWidth;

    private final int startHeroX = (DefaultSizes.gameFrameWidth + endListX) / 2 - DefaultSizes.bigHeroWidth / 2;
    private final int startHeroY = startListY;

    private final int filterX = (DefaultSizes.gameFrameWidth + endListX) / 2;
    private final int filterY = 350;
    private final int filterDisY = 50;
    private final int filterDisX = 10;

    public DeckArrangement(Hero hero, Deck deck) {
        this.hero = hero;
        this.deck = deck;

        selectedButton = 0;

        configPanel();

        makeIcons();

        makeLabels();

        makeFields();

        makeHeroButton();

        makeButtons();

        makeCardsPanel();

        makeDeckCardsPanel();

        layoutComponent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g = (Graphics2D) g;
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(
                    "/images/hero_selection_background.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        g.drawImage(image, 0, 0, null);
    }

    private void configPanel() {
        //setLayout(new BorderLayout());
        //add(layeredPane, BorderLayout.CENTER);
        setLayout(null);
        setVisible(true);
    }

    private void makeIcons() {
        backButton = new BackIcon("icons/back.png", "icons/back_active.png",
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight, new DeckSelection(hero));

        logoutButton = new LogoutIcon("icons/logout.png", "icons/logout_active.png",
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);

        minimizeButton = new MinimizeIcon("icons/minimize.png", "icons/minimize_active.png",
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);

        closeButton = new CloseIcon("icons/close.png", "icons/close_active.png",
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);
    }

    private void makeCardsPanel() {
        // MAKE CARDS FILTERING
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<JPanel> panels = new ArrayList<>();

        for (Card card : HearthStone.currentAccount.getCollection().getCards()) {
            Card card1 = card.copy();
            cards.add(card1);
            panels.add(getCardsPanel(card1));
        }

        cardsPanel = new CardsPanel(cards, panels,
                1, DefaultSizes.medCardWidth, DefaultSizes.medCardHeight);
        cardsScroll = new JScrollPane(cardsPanel);
        cardsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        cardsScroll.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        cardsScroll.setOpaque(false);
        cardsScroll.getViewport().setOpaque(true);
        cardsScroll.getViewport().setBackground(new Color(0, 0, 0, 150));
        cardsScroll.setBorder(null);
    }

    private void makeDeckCardsPanel() {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<JPanel> panels = new ArrayList<>();

        for (Card card : deck.getCards()) {
            Card card1 = card.copy();
            cards.add(card1);
            panels.add(getDeckCardsPanel(card1));
        }

        deckCardsPanel = new CardsPanel(cards, panels,
                1, DefaultSizes.medCardWidth, DefaultSizes.medCardHeight);
        deckCardsScroll = new JScrollPane(deckCardsPanel);
        deckCardsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        deckCardsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        deckCardsScroll.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        deckCardsScroll.setOpaque(false);
        deckCardsScroll.getViewport().setOpaque(true);
        deckCardsScroll.getViewport().setBackground(new Color(0, 0, 0, 150));
        deckCardsScroll.setOpaque(false);
        deckCardsScroll.setBorder(null);
    }

    private void makeHeroButton() {
        heroButton = new HeroButton(hero,   // REAL HERO SHOULD BE
                DefaultSizes.bigHeroWidth,
                DefaultSizes.bigHeroHeight);
    }

    private void makeLabels() {
        nameLabel = new JLabel("name  :  ");
        nameLabel.setForeground(new Color(255, 255, 68));
        nameLabel.setFont(GameFrame.getInstance().getCustomFont(0, 15));

        manaLabel = new JLabel("mana cost  :  ");
        manaLabel.setForeground(new Color(255, 255, 68));
        manaLabel.setFont(GameFrame.getInstance().getCustomFont(0, 15));
    }

    private void makeFields() {
        nameField = new TextField(8);

        manaField = new TextField(8);
    }

    private void makeButtons() {
        allCardsButton = new ImageButton("All Cards", "buttons/blue_background.png",
                0, Color.white, Color.yellow, true, 12, 0,
                DefaultSizes.smallButtonWidth,
                DefaultSizes.smallButtonHeight);
        allCardsButton.mouseEntered();

        myCardsButton = new ImageButton("Hero Cards", "buttons/blue_background.png",
                0, Color.white, Color.yellow, false, 12, 0,
                DefaultSizes.smallButtonWidth,
                DefaultSizes.smallButtonHeight);

        lockCardsButton = new ImageButton("Addable", "buttons/blue_background.png",
                0, Color.white, Color.yellow, false, 12, 0,
                DefaultSizes.smallButtonWidth,
                DefaultSizes.smallButtonHeight);

        searchButton = new ImageButton("search", "buttons/green_background.png",
                0, Color.white, Color.yellow, 14, 0,
                DefaultSizes.medButtonWidth,
                DefaultSizes.medButtonHeight);

        deleteButton = new ImageButton("delete", "buttons/red_background.png",
                0, Color.white, Color.yellow, 14, 0,
                DefaultSizes.medButtonWidth,
                DefaultSizes.medButtonHeight);

        allCardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedButton = 0;
                allCardsButton.setRadio(true);

                myCardsButton.setRadio(false);
                myCardsButton.mouseExited();

                lockCardsButton.setRadio(false);
                lockCardsButton.mouseExited();
            }
        });

        myCardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedButton = 1;
                allCardsButton.mouseExited();
                allCardsButton.setRadio(false);

                myCardsButton.setRadio(true);

                lockCardsButton.mouseExited();
                lockCardsButton.setRadio(false);
            }
        });

        lockCardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedButton = 2;
                allCardsButton.mouseExited();
                allCardsButton.setRadio(false);

                myCardsButton.mouseExited();
                myCardsButton.setRadio(false);

                lockCardsButton.setRadio(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                update();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    HearthStone.currentAccount.getDecks().remove(deck);
                    hero.getDecks().remove(deck);
                    DataBase.save();
                    GameFrame.getInstance().switchPanelTo(GameFrame.getInstance(), new DeckSelection(hero));
                } catch (HearthStoneException e) {
                    try {
                        hearthstone.util.Logger.saveLog("ERROR",
                                e.getClass().getName() + ": " + e.getMessage()
                                        + "\nStack Trace: " + e.getStackTrace());
                    } catch (Exception f) { }
                    System.out.println(e.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private ArrayList<Card> cardsInFilter(ArrayList<Card> cards) {
        ArrayList<Card> ans = new ArrayList<>();
        for (Card card : cards) {
            if (nameField.getText().length() != 0) {
                if (!card.getName().contains(nameField.getText()))
                    continue;
            }
            if (manaField.getText().length() != 0) {
                if (!String.valueOf(card.getManaCost()).equals(manaField.getText()))
                    continue;
            }
            if (selectedButton == 0) {
                if (card.getHeroType() != HeroType.ALL && card.getHeroType() != hero.getType())
                    continue;
            } else if (selectedButton == 1) {
                if (card.getHeroType() != hero.getType())
                    continue;
            } else if (selectedButton == 2) {
                if (!deck.canAdd(card, 1))
                    continue;

                int cardNumber = 1;
                for (Card card1 : ans) {
                    if (card1.getId() == card.getId()) {
                        cardNumber++;
                    }
                }

                if (!deck.canAdd(card, cardNumber))
                    continue;
            }
            ans.add(card.copy());
        }
        return ans;
    }

    private JPanel getDeckCardsPanel(Card card) {
        JPanel panel = new JPanel();
        ImageButton removeCard = new ImageButton("REMOVE", "buttons/red_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                DefaultSizes.smallButtonWidth, DefaultSizes.smallButtonHeight);

        removeCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    deck.remove(card, 1);
                    deckCardsPanel.removeCard(card);
                    cardsPanel.addCard(card, getCardsPanel(card));
                    DataBase.save();
                } catch (HearthStoneException e) {
                    try {
                        hearthstone.util.Logger.saveLog("ERROR",
                                e.getClass().getName() + ": " + e.getMessage()
                                        + "\nStack Trace: " + e.getStackTrace());
                    } catch (Exception f) { }
                    System.out.println(e.getMessage());
                    BaseFrame.error(e.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        panel.add(removeCard);
        panel.setOpaque(false);
        return panel;
    }

    private JPanel getCardsPanel(Card card) {
        JPanel panel = new JPanel();
        ImageButton addCard = new ImageButton("ADD", "buttons/green_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                DefaultSizes.smallButtonWidth, DefaultSizes.smallButtonHeight);

        addCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    deck.add(card, 1);
                    if (selectedButton != 0)
                        cardsPanel.removeCard(card);
                    deckCardsPanel.addCard(card, getDeckCardsPanel(card));
                    DataBase.save();
                } catch (HearthStoneException e) {
                    try {
                        hearthstone.util.Logger.saveLog("ERROR",
                                e.getClass().getName() + ": " + e.getMessage()
                                        + "\nStack Trace: " + e.getStackTrace());
                    } catch (Exception f) { }
                    System.out.println(e.getMessage());
                    BaseFrame.error(e.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        panel.add(addCard);
        panel.setOpaque(false);
        return panel;
    }

    private void layoutComponent() {
        // ICONS
        backButton.setBounds(iconX, startIconY,
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);
        add(backButton);

        logoutButton.setBounds(iconX, startIconY + iconsDis,
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);
        add(logoutButton);

        minimizeButton.setBounds(iconX, endIconY - iconsDis,
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);
        add(minimizeButton);

        closeButton.setBounds(iconX, endIconY,
                DefaultSizes.iconWidth,
                DefaultSizes.iconHeight);
        add(closeButton);

        // LISTS
        cardsScroll.setBounds(startListX, startListY,
                DefaultSizes.arrangementListWidth,
                DefaultSizes.arrangementListHeight);
        add(cardsScroll);

        deckCardsScroll.setBounds(startListX, startListY + DefaultSizes.arrangementListHeight + listDis,
                DefaultSizes.arrangementListWidth,
                DefaultSizes.arrangementListHeight);
        add(deckCardsScroll);

        // LABELS
        nameLabel.setBounds(filterX - (int) nameLabel.getPreferredSize().getWidth(), filterY,
                (int) nameLabel.getPreferredSize().getWidth(), (int) nameLabel.getPreferredSize().getHeight());
        add(nameLabel);

        manaLabel.setBounds(filterX - (int) manaLabel.getPreferredSize().getWidth(), filterY + filterDisY,
                (int) manaLabel.getPreferredSize().getWidth(), (int) manaLabel.getPreferredSize().getHeight());
        add(manaLabel);

        // FIELDS
        nameField.setBounds(filterX, filterY,
                (int) nameField.getPreferredSize().getWidth(), (int) nameField.getPreferredSize().getHeight());
        add(nameField);

        manaField.setBounds(filterX, filterY + filterDisY,
                (int) manaField.getPreferredSize().getWidth(), (int) manaField.getPreferredSize().getHeight());
        add(manaField);

        // HERO
        heroButton.setBounds(startHeroX, startHeroY,
                DefaultSizes.bigHeroWidth,
                DefaultSizes.bigHeroHeight);
        add(heroButton, JLayeredPane.DEFAULT_LAYER);

        // BUTTONS
        allCardsButton.setBounds(filterX - (int) (DefaultSizes.smallButtonWidth * 1.5) - filterDisX,
                filterY + 2 * filterDisY,
                DefaultSizes.smallButtonWidth, DefaultSizes.smallButtonHeight);
        add(allCardsButton);

        myCardsButton.setBounds(filterX - DefaultSizes.smallButtonWidth / 2,
                filterY + 2 * filterDisY,
                DefaultSizes.smallButtonWidth, DefaultSizes.smallButtonHeight);
        add(myCardsButton);

        lockCardsButton.setBounds(filterX + DefaultSizes.smallButtonWidth / 2 + filterDisX,
                filterY + 2 * filterDisY,
                DefaultSizes.smallButtonWidth, DefaultSizes.smallButtonHeight);
        add(lockCardsButton);


        searchButton.setBounds(filterX - DefaultSizes.medButtonWidth / 2,
                filterY + 3 * filterDisY + 20,
                DefaultSizes.medButtonWidth, DefaultSizes.medButtonHeight);
        add(searchButton);

        deleteButton.setBounds(filterX - DefaultSizes.medButtonWidth / 2,
                startListY + 2 * DefaultSizes.arrangementListHeight + listDis - DefaultSizes.medButtonHeight,
                DefaultSizes.medButtonWidth, DefaultSizes.medButtonHeight);
        add(deleteButton);

        // TEST
        /*ShowException showException = new ShowException("salam");
        showException.setBounds(0, 0, 500, 500);
        DeckArrangement.this.add(showException);*/

    }

    private void update() {
        ArrayList<Card> cards = cardsInFilter(HearthStone.currentAccount.getCollection().getCards());
        ArrayList<JPanel> panels = new ArrayList<>();

        for (Card card : cards) {
            panels.add(getCardsPanel(card));
        }

        cardsPanel.update(cards, panels);
    }
}
