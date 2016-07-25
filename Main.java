

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;
//TODO: player card hands and player list


public class Main {

    public static JPanel startUpScreen;
    static JTextField nameField = new JTextField();
    static JButton ok = new JButton("OK");
    static JButton okToName = new JButton("OK");
    static JLabel nameLabel = new JLabel();
    static LinkedList<String> identities;
    static JComboBox numberOfPlayers = new JComboBox();
    static int nofPlayers = 3;
    static LinkedList<PlayerData> players;
    private static String currentPlayerName;
    public static String[] map = new String[45];
    public static int presidentsLocation;
    private static JLabel infoName;
    private static JLabel infoSupport;
    private static JLabel infoALLEGIANCE;
    static String[] playernames;
    static JFrame mainFrame = new JFrame();
    static JPanel[] cityStreets = new JPanel[9 * 5];
    static JLabel[][] street = new JLabel[3][3];

    static public int currentPlayer = 0;
    static JPanel cardPanel = new JPanel();
    static Card currentCard;
    static JPanel board = new JPanel(new GridLayout(5, 9));
    static JPanel data = new JPanel();
    static boolean clickable = true;
    static JButton endTurn;
    static LinkedList<Card> cards = Hand.cards;
    static LinkedList<Card> deck = GameData.generateDeck();
    static LinkedList<Card> currentTechHand = new LinkedList<Card>();
    static JButton infoCards;
    static int winnerID = -1;
    private final int nofRounds = 3;
    final static int NAME = 1;
    final static int SUPPORT = 3;
    final static int ALLEGIANCE = 5;
    static int countPlayerNames = 0;
    static Model modelOfGame = new Model();

    public static void main(String[] args) {

        new Main();

    }

    /**
     * joonistab tänava
     *
     * @param c
     */
    private static JPanel drawStreet(Card c) {
        Color n;
        
        JLabel[][] street = new JLabel[3][3];
        String s = c.data;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int stringIndex = x * 3 + y;
                street[x][y] = new JLabel();
                if (s.charAt(0) == '#') {//if it is a mappiece
                    if (s.charAt(stringIndex) == (' ')) n = Color.BLACK;
                    else if (s.charAt(stringIndex) == ('H')) n = Color.gray;
                    else if (s.charAt(stringIndex) == ('¤')) n = Color.white;
                    else if (s.charAt(stringIndex) == ('P')) n = Color.yellow;
                    else {
                        n = Color.red;
                    }
                    street[x][y].setOpaque(true);
                    street[x][y].setBackground(n);
                    street[x][y].setPreferredSize(new Dimension(35, 35));
                }
            }
        }
        JPanel givenStreet = new JPanel();
        givenStreet.setLayout(new GridLayout(3, 3));
        givenStreet.add(street[0][0]);
        givenStreet.add(street[0][1]);
        givenStreet.add(street[0][2]);
        givenStreet.add(street[1][0]);
        givenStreet.add(street[1][1]);
        givenStreet.add(street[1][2]);
        givenStreet.add(street[2][0]);
        givenStreet.add(street[2][1]);
        givenStreet.add(street[2][2]);
        return givenStreet;

    }
    private static JPanel drawStreet(String s) {
        Color n;
        JLabel[][] street = new JLabel[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int stringIndex = x * 3 + y;
                street[x][y] = new JLabel();
                if (s.charAt(0) == '#') {//if it is a mappiece
                    if (s.charAt(stringIndex) == (' ')) n = Color.BLACK;
                    else if (s.charAt(stringIndex) == ('H')) n = Color.gray;
                    else if (s.charAt(stringIndex) == ('¤')) n = Color.white;
                    else if (s.charAt(stringIndex) == ('P')) n = Color.yellow;
                    else {
                        n = Color.red;
                    }
                    street[x][y].setOpaque(true);
                    street[x][y].setBackground(n);
                    street[x][y].setPreferredSize(new Dimension(35, 35));
                }
            }
        }
        JPanel givenStreet = new JPanel();
        givenStreet.setLayout(new GridLayout(3, 3));
        givenStreet.add(street[0][0]);
        givenStreet.add(street[0][1]);
        givenStreet.add(street[0][2]);
        givenStreet.add(street[1][0]);
        givenStreet.add(street[1][1]);
        givenStreet.add(street[1][2]);
        givenStreet.add(street[2][0]);
        givenStreet.add(street[2][1]);
        givenStreet.add(street[2][2]);
        return givenStreet;

    }
    // annab 1 kaardi kätte
    private static int getRandomCard() {
        return (int) (Math.random() * GameData.routeShape.length);
    }

    // näitab alguse ekraani
    public void display(JPanel startUpScreen) {

        startUpScreen.setLayout(new GridLayout(5, 2));
        nameLabel = new JLabel("Name : ");

        JLabel numberOfPlayersLabel = new JLabel("Players : ");
        String[] players = new String[]{"3", "4", "5", "6", "7", "8", "9", "10"};
        numberOfPlayers = new JComboBox(players);


        OkListener okListener = new OkListener();
        ok.addActionListener(okListener);
        mainFrame.setSize(800, 640);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startUpScreen.add(numberOfPlayersLabel);
        startUpScreen.add(numberOfPlayers);


        startUpScreen.add(ok);
        startUpScreen.setVisible(true);
        mainFrame.add(startUpScreen);
        mainFrame.pack();
        mainFrame.setVisible(true);


    }

    //konstruktor, stardib mängu
    public Main() {
        //initial
        clearMap();
        // GameData game = new GameData();

        startUpScreen = new JPanel();
        display(startUpScreen);//also runs the mainFrame once startupscreen completes
        players = createPlayerData(nofPlayers);

        ///////////////////////
        //game routine
        //////////////////////
        for (int i = 0; i < nofRounds; i++) {
            deck = GameData.generateDeck();

            currentCard = GameData.drawCard(deck);
            presidentsLocation = 1 + 2 * (int) (Math.random() * 3);
            System.out.println("presidentsLocation :" + presidentsLocation);
            Model gameMechanics = new Model();

            do {
                //game play in single player mode
                for (int j = 0; j < players.size(); j++) {

                    (infoName).setText(players.get(j).name);
                    (infoALLEGIANCE).setText(players.get(j).allegiance);
                    (infoSupport).setText(players.get(j).supportGained + "");
                    players.get(j).drawCardHand(deck);

                    if (currentPlayer < nofPlayers) {
                        currentPlayer++;
                    } else {
                        currentPlayer = 0;
                    }
                }
                //gameplay in multiplayer mode
                //TODO:
            } while (isEndOfRound());
            gameMechanics.distributeBounty();
            //clear Board
            board.removeAll();
            //clear map
            clearMap();
        }
        //find the winner of the 3 game rounds
        int max = 0;
        for (int i = 0; i < nofPlayers; i++) {
            if (max < players.get(i).supportGained) {
                max = players.get(i).supportGained;
            }
        }
        //VICTORY MESSAGE
        data.removeAll();
        JLabel victoryMessage = new JLabel();

        data.add(victoryMessage);
        for (int i = 0; i < nofPlayers; i++) {
            if (players.get(i).supportGained == max) victoryMessage.setText("Winner: " + players.get(i).name);

        }
    }

    //kirjeldab numbrid tähtedena
    public enum DataTable {

        NAME(1),
        ALLEGIANCE(5),
        SUPPORT(3);
        private int index;

        DataTable(int index) {
            this.index = index;
        }

        public int geti() {
            return index;
        }

    }

    //koristab kaardi puhtaks
    private void clearMap() {
        for (int i = 0; i < 45; i++) {
            map[i] = "";
        }
    }

    //kas on mängu lõpp
    private boolean isEndOfRound() {
        boolean endOfRound = false;
        if (GameData.deckDepleted && isAllPlayersPass()) {
            //loyalists win
            endOfRound = true;
            //revolutionaries win

        } else {
        }

        return endOfRound;
    }
//kas kõik mängijad passivad/kõik käed on tühjad

    private boolean isAllPlayersPass() {
        boolean b = false;
        boolean passingToken = true;
        for (int i = 0; i < nofPlayers; i++) {
            passingToken = passingToken && players.get(i).pass;
        }
        b = passingToken;
        return b;
    }


    // teeb linkedlisti mängijatest
    private static LinkedList<PlayerData> createPlayerData(int nofPlayers) {
        //adds a copy of playerDAta to a list of players
        players = new LinkedList<PlayerData>();
        for (int i = 0; i < GameData.numberOfPlayers; i++) {
            PlayerData n = new PlayerData();
            players.add(n);
        }
        //currentPlayerName the players
        players.get(0).name = currentPlayerName;
        for (int i = 0; i < GameData.numberOfPlayers; i++) {
            players.get(i).name = "AIbot" + i;
        }
        //set allegiance
        LinkedList allegianceDeck = setAllegianceDeck();
        for (int i = 0; i < nofPlayers; i++) {
            players.get(i).allegiance = (String) allegianceDeck.getFirst();
            allegianceDeck.removeFirst();
        }
        //set ids
        for (int i = 0; i < nofPlayers; i++) {
            players.get(i).id = i;
        }
        return players;
    }

    // võtab linkedlisti mängijatest serverilt
    private static LinkedList<PlayerData> getPlayerDataFromServer() {
        //check that the server exists
        //connect to server
        //get the data as a BufferedStream?/String
        //add all the data substrings to PlayerData LinkedList
        return null;
    }

    // teeb linkedlisti poolte kaardipakkidest
    private static LinkedList setAllegianceDeck() {
        LinkedList<String> allegianceDeck = new LinkedList();
        for (int i = 0; i < GameData.nofActivists[Main.nofPlayers]; i++) {
            allegianceDeck.add("activist");
        }
        for (int i = 0; i < GameData.nofLoyalists[Main.nofPlayers]; i++) {
            allegianceDeck.add("loyalist");
        }
        Collections.shuffle(allegianceDeck);
        return allegianceDeck;
    }


    // reageerib "OK" vajutustele
    public static class OkListener implements ActionListener {


        private JFrame optionsFrame = new JFrame();

        // reaktsioon
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                nameField.setText("");
                //select nofplayerrs
                nofPlayers = numberOfPlayers.getSelectedIndex() + 3;
                GameData.numberOfPlayers = nofPlayers;
                startUpScreen.removeAll();
                startUpScreen.add(nameLabel);
                startUpScreen.add(nameField);
                okToName.addActionListener(this);
                startUpScreen.add(okToName);

                startUpScreen.revalidate();
                startUpScreen.repaint();
                Main.playernames = new String[nofPlayers];
            }
            if (e.getSource() == okToName) {

                if (countPlayerNames >= nofPlayers-1) {

                    mainFrame.remove(startUpScreen);
                    createGUI();
                } else {
                    Main.playernames[countPlayerNames] = nameField.getText();
                    nameField.setText("");
                    countPlayerNames++;
                }
            }
            JButton command = null;
            if (e.getSource() == infoCards) {
                //presents a selection of cards and then a selection of opposing players

                optionsFrame.setPreferredSize(new Dimension(200, 300));
                optionsFrame.setLayout(new GridLayout(3, 1));
                //contents for options frame
                command = new JButton("Make it so!");
                OkListener cl = new OkListener();
                command.addActionListener(cl);

                String[] playerNames = new String[nofPlayers];
                int i = 0;
                for (PlayerData p : players) {
                    playerNames[i] = p.name;
                }

                JComboBox<String> techCardToPlay = new JComboBox<String>(
                        currentTechHand.toArray(new String[currentTechHand.size()]));
                JComboBox<String> playersList = new JComboBox<String>(playerNames);//later limit it to a selection based
                // on the current selected tech card


                //the targeted player
                optionsFrame.add(playersList);
                optionsFrame.add(techCardToPlay);
                optionsFrame.add(command);
                optionsFrame.pack();
                optionsFrame.setVisible(true);

            }
            if (e.getSource() == command) {

                //optionsFrame.dispose();
            }

        }
    }


    // kuulab käigu lõppu
    public static class EndListener implements MouseListener {
        //kuulab hiireklõpse
        public void mouseClicked(MouseEvent e) {
            String techCardList = "";
            if (e.getSource() == endTurn) {
                clickable = true;
                currentCard = GameData.drawCard(deck);
                if (currentCard.data.charAt(0) != '#') {
                    currentTechHand.add(currentCard);
                    for (int i = 0; i < currentTechHand.size(); i++) techCardList += currentTechHand.get(i).data;
                    infoCards.setText(techCardList);
                }
                //new card

                cardPanel.removeAll();

                cardPanel.add(drawStreet(currentCard));

                cardPanel.repaint();
                mainFrame.pack();
                cardPanel.setVisible(true);
            }
            if (e.getSource() == cityStreets[8]) {
                if (presidentsLocation == 1 && Model.Actions.informerSpeaks) {
                    System.out.println("President is here!");
                } else {
                    System.out.println("President has escaped!");
                }
            }
            if (e.getSource() == cityStreets[17]) {
                if (presidentsLocation == 3 && Model.Actions.informerSpeaks) {
                    System.out.println("President is here!");
                } else {
                    System.out.println("President has escaped!");
                }
            }
            if (e.getSource() == cityStreets[44]) {
                if (presidentsLocation == 5 && Model.Actions.informerSpeaks) {
                    System.out.println("President is here!");
                } else {
                    System.out.println("President has escaped!");
                }
            }

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    public static class MListener implements MouseListener {


        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == cardPanel) {

                currentCard.data = turnCard(currentCard.data);
                cardPanel.remove(0);
                cardPanel.add(drawStreet(currentCard));
                cardPanel.repaint();
                mainFrame.pack();
                cardPanel.setVisible(true);

            }
            for (int i = 0; i < 45; i++) {

                if (e.getComponent() == cityStreets[i] &&
                        clickable && //make sure there is only one effective click
                        map[i].equals("") && //checks that there are no existing mappieces
                        isNearMapPiece(i)//checks a nearby mappiece to fit the new one in
                        ) {

                    cardPanel.setVisible(false);
                    cityStreets[i].removeAll();
                    cityStreets[i].add(drawStreet(currentCard));
                    map[i] = currentCard.data;//add currentcard to map
                    if (i == 7 ||
                            i == 25 ||
                            i == 43 ||
                            i == 16 ||
                            i == 35) nearPalace(map);
                    board.repaint();
                    mainFrame.pack();
                    cityStreets[i].setVisible(true);
                    //currentCard = null;//TODO: should be stored instead!!!
                    clickable = false;
                    break;
                }

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        private Point indexToPoint(int mapIndex) {
            Point ma = new Point();
            ma.y = (mapIndex - (mapIndex % 9)) / 9;
            ma.x = (mapIndex % 9);
            return ma;
        }

        //TODO: parandada bug, saab kaarte panna "majade" juurde igal antud ajal
        private boolean isNearMapPiece(int indexOfNewPiece) {
            int x = (int) indexToPoint(indexOfNewPiece).getX();
            int y = (int) indexToPoint(indexOfNewPiece).getY();

            //one up
            //is there a location
            if (y - 1 >= 0) {
                //is there a piece at the location
                if (!map[indexOfNewPiece - 9].equals("")) return true;
            }
            //one right
            //is there a location
            if (x + 1 < 9) {
                //is there a piece at the location
                if (!map[indexOfNewPiece + 1].equals("")) return true;
            }
            //one at bottom
            //is there a location
            if (y + 1 < 5) {
                //is there a piece at the location
                if (!map[indexOfNewPiece + 9].equals("")) return true;
            }


            //one left
            if (x - 1 >= 0) {
                //is there a piece at the location
                if (!map[indexOfNewPiece - 1].equals("")) return true;
            }
            return false;
        }


    }

    //kas on majade juures//TODO:Saboteur win missing
    private static void nearPalace(String[] map) {
        int width = 27;
        int height = 15;
        System.out.println("checking for possiblity of a win");
        int[][] maze = new Model.Maze(map).generateMaze(map);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(maze[x][y]);
            }
            System.out.println();
        }
        if (isRouteComplete()) {
            System.out.println("Revolutionaries won!");
        } else {
            System.out.println("NOT YET COMPLETE");
        }


    }

    //vaatab, kas tee on valmis
    private static boolean isRouteComplete() {
        return modelOfGame.isCompletedRoute();
    }

    //puudub
    public static void createCardPanel() {

    }

    //teeb GUI
    public static void createGUI() {
        //main
        JPanel mainPanel = new JPanel(new BorderLayout());
        //board
        board = new JPanel(new GridLayout(5, 9));
        board.setPreferredSize(new Dimension(800, 500));
        //TODO add listener here for the palaces when hints are played
        //contents of the board
        for (int x = 0; x < 45; x++) {
            cityStreets[x] = new JPanel();
        }
        //create the streets as a testjava
        //setup the streets
        EndListener ml = new EndListener();
        int cardValue = -1;
        int index = 0;
        for (int x = 0; x < 45; x++) {
            JPanel givenStreet = drawStreet(GameData.mapPieceHIDDEN);
            cityStreets[x].add(givenStreet);
        }

        for (int x = 0; x < 45; x++) {
            //first row


            //add start
            if (x == (9 + 9) - 1 + 1) {
                JPanel street = drawStreet(GameData.mapPieceStart);
                cityStreets[x].removeAll();
                cityStreets[x].add(street);
                map[18] = GameData.mapPieceStart;
            } else {
            }
            //last row AREA of PALACES
            if (x == 9 - 1) {
                JPanel street = drawStreet(GameData.mapPiecePalace);
                cityStreets[x].removeAll();
                cityStreets[x].add(street);
                cityStreets[x].addMouseListener(ml);
                map[8] = GameData.mapPiecePalace;
            } else {
            }
            if (x == 3 * 9 - 1) {
                JPanel street = drawStreet(GameData.mapPiecePalace);
                cityStreets[x].removeAll();
                cityStreets[x].add(street);
                cityStreets[x].addMouseListener(ml);
                map[26] = GameData.mapPiecePalace;
            } else {
            }
            if (x == 5 * 9 - 1) {
                JPanel street = drawStreet(GameData.mapPiecePalace);
                cityStreets[x].removeAll();
                cityStreets[x].add(street);
                cityStreets[x].addMouseListener(ml);
                map[44] = GameData.mapPiecePalace;
            } else {
            }

            MListener el = new MListener();
            cityStreets[x].addMouseListener(el);
        }

        //ADD ALL CITYSTREETS TO THE BOARD
        for (int x = 0; x < 9 * 5; x++) {
            JPanel street = cityStreets[x];
            board.add(street);
        }

        board.setVisible(true);


        //data table
        data.setLayout(new GridLayout(4, 2));

        JLabel labelName = new JLabel("NAME: ");
        infoName = new JLabel(currentPlayerName);
        data.add(labelName);
        data.add(infoName);

        JLabel labelSUPPORT = new JLabel("SUPPORT:");
        infoSupport = new JLabel(Integer.toString(players.get(0).supportGained));
        data.add(labelSUPPORT);
        data.add(infoSupport);

        JLabel labelALLEGIANCE = new JLabel("ALLEGIANCE:");
        infoALLEGIANCE = new JLabel(players.get(0).allegiance);

        data.add(labelALLEGIANCE);
        data.add(infoALLEGIANCE);

        endTurn = new JButton("End Turn");

        endTurn.addMouseListener(ml);

        infoCards = new JButton();
        OkListener icl = new OkListener();
        infoCards.addActionListener(icl);
        data.add(infoCards);
        data.add(endTurn);

        //player screen

        JPanel playerScreenNorth = new JPanel(new GridLayout(1, 3));
        JPanel playerScreenWest = new JPanel(new GridLayout(3, 1));
        JPanel playerScreenSouth = new JPanel(new GridLayout(1, 3));
        JPanel playerScreenEast = new JPanel(new GridLayout(3, 1));

        //mainFrame is set


        mainFrame.setTitle("Peaceful Power");
        mainPanel.setPreferredSize(new Dimension(800, 640));
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(playerScreenNorth, BorderLayout.NORTH);
        mainPanel.add(playerScreenEast, BorderLayout.EAST);
        mainPanel.add(playerScreenWest, BorderLayout.WEST);
        playerScreenSouth.setPreferredSize(new Dimension(70, 120));
        playerScreenSouth.add(data);
        //Current Card Panel
        createCardPanel(icl, playerScreenSouth);
        mainPanel.add(playerScreenSouth, BorderLayout.SOUTH);
        //completion of mainFrame
        mainPanel.setVisible(true);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    //teeb kaartidepaneeli
    private static void createCardPanel(OkListener icl, JPanel playerScreenSouth) {


        if (currentCard.data.charAt(0) == '#') {
            cardPanel.add(drawStreet(currentCard.data));
        } else {
            players.get(0).techHand.add(currentCard.data.substring(0, 1));

            //TODO: peab saama kõikide techkaartide andmed
            // players.get(0).techHand.toString()
            infoCards.setText("Hand");
        }

        MListener el = new MListener();
        cardPanel.addMouseListener(el);
        playerScreenSouth.add(cardPanel);
    }

    //pöörab kaarti vajutusel
    public static String turnCard(String card) {
        String twistedCard = "";
        twistedCard += card.substring(6, 7);
        twistedCard += card.substring(3, 4);
        twistedCard += card.substring(0, 1);
        twistedCard += card.substring(7, 8);
        twistedCard += card.substring(4, 5);
        twistedCard += card.substring(1, 2);
        twistedCard += card.substring(8);
        twistedCard += card.substring(5, 6);
        twistedCard += card.substring(2, 3);

        return twistedCard;
    }
}
