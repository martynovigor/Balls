package server;

import java.util.ArrayList;

public class Sessions {

    private static ArrayList<Session> list = new ArrayList<>();
    private static int count;

    public synchronized static void inc() {
        count++;
    }

    public synchronized static int getCount() {
        return count;
    }

    public synchronized static ArrayList<Session> getSessions() {
        return list;
    }

    public static synchronized Session getByName(String n) {
        for(Session s : getSessions()) {
            if(s.getName().equals(n)) {
                return s;
            }
        }
        return null;
    }

    public static synchronized String generateList() {
        StringBuilder sb = new StringBuilder();
        sb.append("DATA:");
        for(Session s : getSessions()) {
            if(s.getPlayerNum() == 1) {
                sb.append(s.getName()).append(":");
            }
        }
        return sb.toString();
    }
}
