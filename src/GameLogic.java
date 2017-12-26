public class GameLogic {

    private int remTiles;
    Board board;

    public GameLogic(Board board1) {
        board = board1;
        remTiles = board.getRemTiles();
    }

    public boolean noTilesLeft() {
        return remTiles == 0;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < 7 && y < 6;
    }

    public boolean lineOf4(int x, int y, int a, int b, int p) {
        int numInRow = 0;
        int xc = x;
        int yc = y;

        while (numInRow < 4 && inBounds(xc, yc)) {
            if (!board.tileMatch(xc, yc, p)) {
                break;
            }
            xc += a;
            yc += b;
            numInRow++;
        }
        xc = x - a;
        yc = y - b;
        while (numInRow < 4 && inBounds(xc, yc)) {
            if (!board.tileMatch(xc, yc, p)) {
                break;
            }
            xc -= a;
            yc -= b;
            numInRow++;
        }
        return numInRow == 4;
    }

    public boolean move(int x, int y, int p) {
        board.setTile(x, y, p);
        remTiles--;
        return lineOf4(x, y, 1, 1, p) ||
                lineOf4(x, y, -1, 0, p) ||
                lineOf4(x, y, -1, 1, p) ||
                lineOf4(x, y, 0, 1, p);
    }
}