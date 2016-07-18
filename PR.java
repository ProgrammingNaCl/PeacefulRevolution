import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;


public class PR {

    public static JPanel startUpScreen;
    static JButton ok = new JButton("OK");
    static LinkedList<String> identities;
    static JComboBox numberOfPlayers = new JComboBox();
    static LinkedList<PlayerData> playerData = new LinkedList<PlayerData>();
    static JFrame mainFrame = new JFrame();
    static JPanel[][] cityStreets = new JPanel[9][5];
    static JLabel[][] street = new JLabel[3][3];

    public PR() {
        startUpScreen = new JPanel();
        display(startUpScreen);

    }
//viib kõik ekraanile välja
    public static void main(String[] args) {

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                cityStreets[x][y] = new JPanel();

            }
        }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                street[0][0] = new JLabel();
                street[0][1]=new JLabel();
                street[0][2]=new JLabel();
                street[1][0]=new JLabel();
                street[1][1]=new JLabel();
                street[1][2]=new JLabel();
                street[2][0]=new JLabel();
                street[2][1]=new JLabel();
                street[2][2]=new JLabel();

                cityStreets[x][y].add(street[0][0]);
                cityStreets[x][y].add(street[0][1]);
                cityStreets[x][y].add(street[0][2]);
                cityStreets[x][y].add(street[1][0]);
                cityStreets[x][y].add(street[1][1]);
                cityStreets[x][y].add(street[1][2]);
                cityStreets[x][y].add(street[2][0]);
                cityStreets[x][y].add(street[2][1]);
                cityStreets[x][y].add(street[2][2]);


            }
        }
        //TEST define a street on the map
        cityStreets[0][0].setPreferredSize(new Dimension(15,15));
        cityStreets[0][0].getComponent(0).setBackground(Color.black);
        cityStreets[0][0].setVisible(true);
        new PR();

    }
//näitab startupscreeni
    public void display(JPanel startUpScreen) {

        startUpScreen.setLayout(new GridLayout(5, 2));
        JLabel name = new JLabel("Name : ");
        JTextField nameField = new JTextField();
        JLabel numberOfPlayersLabel = new JLabel("Players : ");
        String[] players = new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11"};
        numberOfPlayers = new JComboBox(players);

        OkListener okListener = new OkListener();
        ok.addActionListener(okListener);
        mainFrame.setSize(800, 600);
        startUpScreen.add(name);
        startUpScreen.add(nameField);
        startUpScreen.add(numberOfPlayersLabel);
        startUpScreen.add(numberOfPlayers);
        startUpScreen.add(ok);
        startUpScreen.setVisible(true);
        mainFrame.add(startUpScreen);
        mainFrame.pack();
        mainFrame.setVisible(true);


    }
//kooatab mängijate data
    private void createPlayerData(int nofPlayers) {

        for (int i = 0; i < nofPlayers; i++) {
            playerData.add(new PlayerData());
        }
    }
//annab idetiteedi
    private LinkedList<String> identityRoller() {
        LinkedList<String> ids = new LinkedList<String>();
        ids.add("loyalist");
        ids.add("loyalist");
        ids.add("loyalist");
        ids.add("loyalist");
        ids.add("activist");
        ids.add("activist");
        ids.add("activist");
        ids.add("activist");
        ids.add("activist");
        ids.add("activist");
        ids.add("activist");
        Collections.shuffle(ids);
        return ids;
    }
//valmistab GUI
    public void createGUI() {
        //main
        JPanel mainPanel = new JPanel(new BorderLayout());
        //board
        JPanel board = new JPanel(new GridLayout(5, 9));
        //data table
        JPanel data = new JPanel(new GridLayout(8, 2));
        //player screen

        JPanel playerScreenNorth = new JPanel(new GridLayout(1, 3));
        JPanel playerScreenWest = new JPanel(new GridLayout(3, 1));
        JPanel playerScreenSouth = new JPanel(new GridLayout(3, 1));
        JPanel playerScreenEast = new JPanel(new GridLayout(1, 3));

        //boardContents
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                board.add(cityStreets[x][y]);
            }
        }
        mainFrame.setTitle("Peaceful Power");
        mainPanel.setPreferredSize(new Dimension(800, 640));
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(playerScreenNorth, BorderLayout.NORTH);
        mainPanel.add(playerScreenEast, BorderLayout.EAST);
        mainPanel.add(playerScreenWest, BorderLayout.WEST);
        playerScreenSouth.add(data);
        mainPanel.add(playerScreenSouth, BorderLayout.SOUTH);
        mainPanel.setVisible(true);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
//kuulab ´OK´ vajutust
    public class OkListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                System.out.println("MSG: reached the button");
                int nofPlayers = numberOfPlayers.getSelectedIndex() + 3;
                createPlayerData(nofPlayers);
                LinkedList<String> playerID = identityRoller();
                for (int i = 0; i < nofPlayers; i++) {
                    PlayerData pd = playerData.get(i);
                    pd.allegiance = playerID.get(i);
                }
                mainFrame.remove(startUpScreen);
                createGUI();
            }


        }

    }
}
