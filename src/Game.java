public class Game {

    public static void main(String[] args) {
        int gameMode = 1;
        Gui gui = new Gui();

        while (gameMode != 0) {

            if (gameMode == 1) {
                gui.repaint();
                if (gui.getWin()) {
                    gameMode = 2;
                } else if (gui.getTie()) {
                    gameMode = 3;
                } else if (gui.getNew()) {
                    gui = new Gui();
                    gameMode = 1;
                }
            }
            if (gameMode == 2) {
                gui.win();
                if (gui.getEnd()) {
                    gameMode = 0;
                } else if (gui.getNew()) {
                    gui = new Gui();
                    gameMode = 1;
                }
            }
            if (gameMode == 3) {
                gui.tie();
                if (gui.getEnd()) {
                    gameMode = 0;
                } else if (gui.getNew()) {
                    gui = new Gui();
                    gameMode = 1;
                }
            }
        }
    }
}