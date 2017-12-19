package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void gameOver() throws Exception {
        Board b = new Board(10, 3);
        b.setElement(0,0);
        b.setElement(2,2);
        b.setElement(0,1);
        b.setElement(3,3);
        b.setElement(0,2);
        assertTrue(b.gameOver(0, 2));
    }

    @org.junit.Test
    public void getSize() throws Exception {
        Board b = new Board(10, 5);

        assertEquals(10, b.getSize());
    }
}