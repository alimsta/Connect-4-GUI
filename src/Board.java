public class Board {

    private int[][] tiles;
    private int remTiles;

    public Board() {
        tiles = new int[7][6];
        remTiles = 42;

    }

    public int getRemTiles() {
        return remTiles;
    }

    public int switchPlayer(int p) {
        if (p == 1) {
            return 2;
        }
        return 1;
    }

    public int checkSpace(int ColNum) {
        int val = -1;
        for (int i = 0; i < 6; i++) {
            if (tiles[ColNum][i] == 0) {
                val = i;
            }
        }
        return val;
    }

    public boolean tileMatch(int i, int j, int p) {
        return tiles [i][j] == p;
    }

    public void setTile(int i, int j, int p) {
        tiles[i][j] = p;
    }

}