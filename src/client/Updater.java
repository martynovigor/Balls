package client;

import client.Main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Updater implements Runnable {
    private JButton[][] buttons;
    private BufferedReader reader;
    private boolean b;

    public Updater(JButton[][] but, BufferedReader reader, boolean b) {
        Main.setActive(false);
        buttons = but;
        this.reader = reader;
        this.b = b;
    }

    @Override
    public void run() {
        try {
            String rec = reader.readLine();
            System.out.println("start game: " + rec);
            String tokens[] = rec.split(":");

            if(rec.startsWith("Game done")) {
                if (rec.contains(":")) {
                    int i = Integer.parseInt(tokens[1]);
                    int j = Integer.parseInt(tokens[2]);
                    ImageIcon img_x = (b == true)
                            ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png")
                            : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png");
                    buttons[i][j].setIcon(img_x);
                    buttons[i][j].setEnabled(false);
                }
                JOptionPane.showMessageDialog(null, "GAME OVER");

            } else {
                int i = Integer.parseInt(tokens[0]);
                int j = Integer.parseInt(tokens[1]);
                ImageIcon img_x = (b == true)
                        ? new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\o_new.png")
                        : new ImageIcon("C:\\Users\\Igor\\IdeaProjects\\Balls\\src\\img\\x_new.png");
                buttons[i][j].setIcon(img_x);
                buttons[i][j].setEnabled(false);
                Main.setActive(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
