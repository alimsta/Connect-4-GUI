import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

public class BoardTest {
    
    /* -----------Board State Tests------------ */ 
    
    @Test 
    public void BoardColEmptyTest() {
        Board board = new Board();
        int slot = 1;
        int num = board.checkSpace(slot);
        assertEquals(num, 5);
    }
    
    @Test 
    public void BoardNonEmptyTest() {
        Board board = new Board();
        int slot = 1;
        board.setTile(1,5,1);
        int num = board.checkSpace(slot);
        assertEquals(num, 4);
    }
    
    @Test 
    public void BoardColumnFullTest() {
        Board board = new Board();
        int slot = 1;
        board.setTile(1,0,1);
        board.setTile(1,1,1);
        board.setTile(1,2,2);
        board.setTile(1,3,2);
        board.setTile(1,4,2);
        board.setTile(1,5,1);
        int num = board.checkSpace(slot);
        assertEquals(num, -1);
    }
    
    @Test 
    public void BoardHorizontalWinTest() {
        Board board = new Board();
        GameLogic gl = new GameLogic(board);
        board.setTile(1,1,1);
        board.setTile(2,1,1);
        board.setTile(3,1,1);
        board.setTile(1,0,2);
        board.setTile(2,0,1);
        board.setTile(3,0,2);
        board.setTile(4,0,1);
        boolean bool = gl.move(4,1,1);
        assertTrue(bool);
    }
    
    @Test 
    public void BoardVerticalWinTest() {
        Board board = new Board();
        GameLogic gl = new GameLogic(board);
        gl.move(1,0,1);
        gl.move(1,1,1);
        gl.move(1,2,1);
        boolean bool = gl.move(1,3,1);
        assertTrue(bool);
    }
    
    @Test 
    public void BoardDiagonalWinTest1() {
        Board board = new Board();
        GameLogic gl = new GameLogic(board);
        gl.move(1,0,1);
        gl.move(2,1,1);
        gl.move(3,2,1);
        boolean bool = gl.move(4,3,1);
        assertTrue(bool);
    }
    
    @Test 
    public void BoardDiagonalWinTest2() {
        Board board = new Board();
        GameLogic gl = new GameLogic(board);
        gl.move(1,3,1);
        gl.move(2,2,1);
        gl.move(3,1,1);
        boolean bool = gl.move(4,0,1);
        assertTrue(bool);
    }
    
    @Test 
    public void BoardTieTest() {
        Board board = new Board();
        GameLogic gl = new GameLogic(board);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gl.move(i, j, 1);
            }
        }
        assertTrue(gl.noTilesLeft());
    }
    
    /* ----------------Method Tests----------------- */
    
    @Test 
    public void getRemTilesTest() {
        Board board = new Board();
        int val = 42;
        int num = board.getRemTiles();
        assertEquals(num, val);
    }
    
    @Test 
    public void switchPlayerTest1() {
        Board board = new Board();
        int player = 1;
        int num = board.switchPlayer(player);
        assertEquals(num, 2);
    }
    
    @Test 
    public void switchPlayerTest2() {
        Board board = new Board();
        int player = 2;
        int num = board.switchPlayer(player);
        assertEquals(num, 1);
    }
    
    @Test 
    public void checkSpaceTest() {
        Board board = new Board();
        int slot = 0;
        int num = board.checkSpace(slot);
        assertEquals(num, 5);
    }
    
    @Test 
    public void setTileMatchTrueTest() {
        Board board = new Board();
        int slot = 1;
        board.setTile(1,5,1);
        assertTrue(board.tileMatch(1,5,1));
    }
    
    @Test 
    public void setTileMatchFalseTest1() {
        Board board = new Board();
        int slot = 1;
        board.setTile(1,5,1);
        assertFalse(board.tileMatch(1,5,2));
    }
    
    @Test 
    public void setTileMatchFalseTest2() {
        Board board = new Board();
        int slot = 1;
        board.setTile(1,5,1);
        assertFalse(board.tileMatch(1,4,2));
    }
    
}
