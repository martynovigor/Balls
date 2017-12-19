package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class Connection implements Runnable{

    private Socket socket;
    private Session session;
    private PrintStream socketOutputWriter;
    private int playerNum;


    public void send(String str) {
        socketOutputWriter.println(str);
    }

    public Connection(Socket s) {
        socket = s;
        try {
            socketOutputWriter = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            BufferedReader socketInputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String recievedString;
            while (true) {
                recievedString = socketInputReader.readLine();
                if(recievedString.equals("SESSIONSDATA")){
                    socketOutputWriter.println(Sessions.generateList());
                } else if (recievedString.equals("NEWSESSION")) {
                    session = new Session(this, Integer.toString(Sessions.getCount()));
                    Sessions.inc();
                    Sessions.getSessions().add(session);
                } else if (recievedString.startsWith("CONNECTTO=")) {
                    Session sess = Sessions.getByName(recievedString.split("=")[1]);
                    if(sess == null) {
                        continue;
                    }else {
                        sess.setUser(this);
                        session = sess;
                    }
                } else {
                    session.handlePacket(playerNum + ":" + recievedString);
                }
                System.out.println(recievedString);
            }

        } catch (IOException e) {
            //e.printStackTrace();
            return;
        }
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
