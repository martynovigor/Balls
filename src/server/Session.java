package server;

import model.Board;

import java.net.Socket;

public class Session {

    private int playerNum;
    private Connection user1;
    private Connection user2;
    private String name;
    private Board b;

    public Session (Connection user, String name) {
        user1 = user;
        user1.setPlayerNum(1);
        this.name = name;
        playerNum = 1;
    }

    public void setUser(Connection user2) {
        this.user2 = user2;
        user2.setPlayerNum(2);
        playerNum = 2;
        b = new Board(10, 3);
    }

    public void handlePacket(String str) {
        String[] coor = str.split(":");
        String playerNum = coor[0];
        String i = coor[1];
        String j = coor[2];
        b.setElement(Integer.parseInt(i), Integer.parseInt(j));

        if (b.gameOver(Integer.parseInt(i), Integer.parseInt(j))) {
            user1.send("Game done");
            user2.send("Game done");
        } else {
            if(Integer.parseInt(playerNum) == 1) {
                user2.send(i + ":" + j);
            } else {
                user1.send(i + ":" + j);
            }
        }
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNum() {
        return playerNum;
    }


}
