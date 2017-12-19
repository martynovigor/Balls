package model;

import model.Board;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
      //  Board b = new Board(3, 3);
        Scanner in = new Scanner(System.in);
       // System.out.println("If you want to play in console press 1. Or if you want play with interface press 2");
        if (in.nextInt() == 1) {
      //      b.start();
        } else if (in.nextInt() == 2){
            //new gui.Main(b).setVisible(true);
        }

      /*  for ( i = 0; i < b.getSize(); i++) {
            System.out.println(i + " " + (b.getSize() - i - 1)); //second diag
        }*/

/*        for (int i = 0, k = 1; k < b.getSize(); k++) {        //first low
            System.out.println("stop");
            for (int j = 0; j < b.getSize() - k; j++, i++) {
                System.out.println((j + k) +" " + i);
            }
            i = 0;
        }
*/

    }

}
