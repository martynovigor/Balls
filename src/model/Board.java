package model;

import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Board {

    private int[][] board;
    private int size, gamePoint;
    private boolean turnFirst = true;
    private boolean gameOver = false;
  //  private Socket client;

    public boolean getTurn () {
        return  turnFirst;
    }

    public void changeTurn() {
        turnFirst = !turnFirst;
    }

    public boolean getGameOver() {
        return gameOver;
    }


    public Board (int size, int gamePoint) {
        this.size = size;
        this.gamePoint = gamePoint;
        board = new int[size][size];
     //   this.client = client;
    }

    public int getElement(int i, int j) {
        if(i < 0 || i > getSize() || j < 0 || j > getSize()) {
            return -1;
        } else {
            return board[i][j];
        }
    }

    public int getSize() {
        return size;
    }

    public boolean setElement(int i, int j) {
        if(i < 0 || i >= getSize() || j < 0 || j >= getSize() || getElement(i, j) != 0) {
            return false;
        } else {
            board[i][j] = (turnFirst) ? 1 : 2;
            turnFirst = !turnFirst;
            return true;
        }
    }


    public boolean gameOver(int i, int j) {
        int indexi = i;
        int indexj = j;
        int value = getElement(i, j);
        if (value == 0) {
            return false;
        }
        int counter1 = 1, counter2 = 1;

        while (getElement(indexi, indexj) == value) {
            if(indexj + 1 < getSize()) {
                if (getElement(indexi, indexj + 1) == value) {
                    indexj++;
                    counter1++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        indexj = j;
        while (getElement(indexi, indexj) == value) {
            if(indexj - 1 >= 0) {
                if (getElement(indexi, indexj - 1) == value) {
                    indexj--;
                    counter2++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        if (counter1 + counter2 - 1>= gamePoint) {
            System.out.println("Winner:" + ((value == 1)?"First":"Second"));
            gameOver = true;
            return true;                 // horizontal
        }
        indexj = j;
        counter1 = counter2 = 1;
        while (getElement(indexi, indexj) == value) {
            if (indexi - 1 >= 0) {
                if (getElement(indexi - 1, indexj) == value) {
                    indexi--;
                    counter1++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        indexi = i;
        while (getElement(indexi, indexj) == value) {
            if(indexi + 1 < getSize()) {
                if (getElement(indexi + 1, indexj) == value) {
                    indexi++;
                    counter2++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        if (counter1 + counter2 - 1 >= gamePoint) {
            System.out.println("Winner:" + ((value == 1)?"First":"Second"));
            gameOver = true;
            return true;                 // vertical
        }
        indexi = i;
        counter1 = counter2 = 1;
        while (getElement(indexi, indexj) == value) {               //testi ih na 10 10
            if(indexi + 1 < getSize() && indexj + 1 < getSize()) {
                if (getElement(indexi + 1, indexj + 1) == value) {
                    indexi++;
                    indexj++;
                    counter1++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        indexi = i;
        indexj = j;
        while (getElement(indexi, indexj) == value) {
            if(indexi - 1 >= 0 && indexj - 1 >= 0) {
                if (getElement(indexi - 1, indexj - 1) == value) {
                    indexi--;
                    indexj--;
                    counter2++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        if (counter1 + counter2 - 1 >= gamePoint) {
            System.out.println("Winner:" + ((value == 1)?"First":"Second"));
            gameOver = true;
            return true;                 // osnov
        }
        indexi = i;
        indexj = j;
        counter1 = counter2 = 1;
        while (getElement(indexi, indexj) == value) {
            if(indexi + 1 < getSize() && indexj - 1 >= 0) {
                if (getElement(indexi + 1, indexj - 1) == value) {
                    indexi++;
                    indexj--;
                    counter2++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        indexi = i;
        indexj = j;
        while (getElement(indexi, indexj) == value) {
            if(indexi - 1 >= 0 && indexj + 1 < getSize()) {
                if (getElement(indexi - 1, indexj + 1) == value) {
                    indexi--;
                    indexj++;
                    counter1++;
                } else {
                    break;
                }
            }  else {
                break;
            }
        }
        if (counter1 + counter2 - 1 >= gamePoint) {
            System.out.println("Winner:" + ((value == 1)?"First":"Second"));
            gameOver = true;
            return true;                 // pobochn
        } else if(checkFull()){
            System.out.println("Draw");
            gameOver = true;
            return true;
        } else {
            return false;
        }

    }

    public boolean checkFull() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if(getElement(i, j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean start() {
        int i = 0, j = 0;
        while (!gameOver(i, j)) {
            //System.out.println(this);
            System.out.println(XOoutput());
            do {
                Scanner in = new Scanner(System.in);
                i = in.nextInt();
                j = in.nextInt();
                //Random random = new Random();
                //i = random.nextInt() % getSize();
                //j = random.nextInt() % getSize();
            } while (!setElement(i, j));
        }
        System.out.println("Final");
        //System.out.println(this);
        System.out.println(XOoutput());
        return true;
    }

    public String XOoutput() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getSize(); i++, sb.append('\n')) {
            for (int j = 0; j < getSize(); j++) {
                //sb.append(board[i][j]);
                if(getElement(i, j) == 1) {
                    sb.append("X ");
                } else if (getElement(i, j) == 2) {
                    sb.append("O ");
                } else {
                    sb.append(". ");
                }
            }
        }
        return sb.toString();
    }
  /*  public void start () {
        while (!gameOver()) {
            System.out.println(this);
            int i, j;
            do {
                Scanner in = new Scanner(System.in);
                i = in.nextInt();
                j = in.nextInt();
                //Random random = new Random();
                //i = random.nextInt() % getSize();
                //j = random.nextInt() % getSize();
            } while (!setElement(i, j));
        }
        System.out.println(this);
        System.out.println("Final view!");
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getSize(); i++, sb.append('\n')) {
            for (int j = 0; j < getSize(); j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }
}
