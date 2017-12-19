package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import model.*;
//import org.omg.CORBA.PUBLIC_MEMBER;

public class Main extends JFrame{
    // two selected players
    private boolean isX = false;
    private static boolean active = false;
    public static JPanel welcome;
    private Board b;
    private Socket socket;
    private PrintStream socketOutputWriter;
    private BufferedReader socketInputReader;

    public boolean gameover = false;
    public JButton[][] buttons;


    private void startGame() {
        remove(welcome);
        //JPanel panel = new JPanel();
        welcome = new JPanel();
        //b = new Board(10, 3);
        Color BG = Color.BLACK;
        Dimension BTN_PREF_SIZE = new Dimension(60, 60);
        int GAP = 3;
        buttons = new JButton[b.getSize()][b.getSize()];
        welcome.setBackground(BG);
        welcome.setLayout(new GridLayout(b.getSize(), b.getSize(), GAP, GAP));
        welcome.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        for(int i = 0; i < b.getSize(); i++)                      //Create grid of buttons
        {
            for(int j = 0; j < b.getSize(); j++)
            {

                buttons[i][j] = new JButton();                //Instantiating buttons
                buttons[i][j].setText("");
                ImageIcon img = new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\bg.png");
                buttons[i][j].setIcon(img);
                buttons[i][j].setPreferredSize(BTN_PREF_SIZE);
                buttons[i][j].setVisible(true);
                welcome.add(buttons[i][j]);
                buttons[i][j].addActionListener(new myActionListener(i, j));        //Adding response event to buttons
            }
        }

        add(welcome, BorderLayout.CENTER);

        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (isX) {
            return;
        }
        new Thread(new Updater(buttons, socketInputReader, isX)).start();
        /*try {
            String rec = socketInputReader.readLine();
            System.out.println("start game: " + rec);
            String tokens[] = rec.split(":");

            if(rec.equals("Game done")) {
                JOptionPane.showMessageDialog(null, "GAME OVER");
            } else {
                int i = Integer.parseInt(tokens[0]);
                int j = Integer.parseInt(tokens[1]);
                ImageIcon img_x = (isX == true)
                        ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png")
                        : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png");
                buttons[i][j].setIcon(img_x);
                buttons[i][j].setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public Main(Board b, Socket socket) {
        super("Balls");
        try {
            this.socket = socket;
            socketOutputWriter = new PrintStream(socket.getOutputStream());
            socketInputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.b = b;
        welcome = new Welcome();
        this.add(welcome);

        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        this.setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static synchronized boolean getActive() {
        return active;
    }

    public static synchronized void setActive(boolean act) {
        active = act;
    }

    private class myActionListener implements ActionListener {

        private int i, j;

        public myActionListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void actionPerformed(ActionEvent a) {
            if(!getActive()) {
                return;
            }
            ImageIcon img_x = (isX == true)
                    ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png")
                    : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png");
            buttons[i][j].setIcon(img_x);
            buttons[i][j].setEnabled(false);
            socketOutputWriter.println(i + ":" + j);

            new Thread(new Updater(buttons, socketInputReader, isX)).start();
            /*try {
                String rec = socketInputReader.readLine();

                System.out.println("action performed: " + rec);
                String tokens[] = rec.split(":");

                if(rec.equals("Game done")) {
                    JOptionPane.showMessageDialog(null, "GAME OVER");
                } else {
                    i = Integer.parseInt(tokens[0]);
                    j = Integer.parseInt(tokens[1]);
                    img_x = (isX == true)
                            ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png")
                            : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png");
                    buttons[i][j].setIcon(img_x);
                    buttons[i][j].setEnabled(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
            /*  public void actionPerformed(ActionEvent a) {
            int i = 0, j = 0;
            int ii = 0, jj = 0;
            boolean flag = false;
            for (ii = 0; ii < b.getSize(); ii++) {
                if(flag)
                    break;
                for (jj = 0; jj < b.getSize(); jj++) {
                    if (a.getSource() == buttons[ii][jj])                  //Checking which button is pressed
                    {
                        ImageIcon img_x = (b.getTurn() == true)
                                ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png")
                                : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png");
                        buttons[ii][jj].setIcon(img_x);
                        buttons[ii][jj].setEnabled(false);
                        flag = true;
                        break;
                    }
                }
            }
            b.setElement(ii - 1, jj);
            if(b.gameOver(ii - 1, jj)) {
                for (int k = 0; k < b.getSize(); k++) {
                    for (int l = 0; l < b.getSize(); l++) {
                        buttons[k][l].setEnabled(false);
                    }
                }
                System.out.println("done");
            }
        }*/
        }


    private class Welcome extends JPanel {

        private JList<String> jList;
        private DefaultListModel<String> listModel;

        public Welcome() {
            super();
           // this.setLayout(new BorderLayout());
            JButton start = new JButton("Start game");
            //this.add(start, BorderLayout.NORTH);
            start.setBounds(20, 20, 100, 30);
            listModel = new DefaultListModel<>();
            //requestSessionsInfo();
            requestSessionData();

            jList = new JList<>(listModel);
            jList.setBounds(200, 20, 100, 200);
            //jList.setSize(100, 100);

            JButton updateButton = new JButton("Update");
            updateButton.setBounds(20, 60, 100, 30);
           // updateButton.addActionListener(e -> requestSessionsInfo());
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    requestSessionData();
                }
            });

            JButton createNewButton = new JButton("New session");
            createNewButton.setBounds(20, 90, 150, 30);
           // createNewButton.addActionListener(e -> requestNewSession());
            createNewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    socketOutputWriter.println("NEWSESSION");
                    isX = true;
                    setActive(true);
                    startGame();
                }
            });
            this.add(start);
            this.add(createNewButton);
            this.add(jList);
            this.add(updateButton);
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String session = jList.getSelectedValue();
                    socketOutputWriter.println("CONNECTTO=" + session.split(" ")[1]);
                    isX = false;
                    setActive(false);
                    startGame();
                    //nessess
                }
            });

        }

        private void requestSessionData() {
            socketOutputWriter.println("SESSIONSDATA");
            String recievedString = null;

            try {
                recievedString = socketInputReader.readLine();
                System.out.println("request session data: " + recievedString);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(recievedString == null) {
                return;
            }

            String[] sessions;
            sessions = recievedString.split(":");

            if (!sessions[0].equals("DATA")) {
                return;
            }

            listModel.clear();

            for (int i = 1; i < sessions.length; i++) {
                if (sessions[i].equals("")) {
                    continue;
                }

                listModel.addElement("Session " + sessions[i]);
            }
        }

    }


    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            new Main(new Board(10, 3), socket).setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}