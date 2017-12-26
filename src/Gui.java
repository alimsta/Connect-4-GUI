import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;

public class Gui {

    Board board;
    GameLogic gl;
    private JFrame frame;
    private JLabel[][] tiles;
    private JButton[] colButtons;
    private List<String> moveList;
    private JLabel moves;
    private JPanel cPane;
    private List<Player> highScores;
    private int curr;
    private boolean gameNew;
    private boolean gameEnd;
    private boolean gameWin;
    private boolean gameTie;
    File scores = new File("highScores.txt");

    public Gui() {

        board = new Board();
        gl = new GameLogic(board);
        frame = new JFrame("Connect Four!");
        tiles = new JLabel[7][6];
        colButtons = new JButton[7];
        cPane = new JPanel();
        curr = 1;
        gameNew = false;
        gameEnd = false;
        gameWin = false;
        gameTie = false;
        moveList = new ArrayList<>();
        moves = new JLabel(" Move #0 (Player " + curr +"'s turn): ");
        highScores = new ArrayList<>();

        cPane.setLayout(new GridLayout(7, 7 ));

        for (int i = 0; i < 7; i++) {
            colButtons[i] = new JButton("Column " + Integer.toString(i+1));
            colButtons[i].setActionCommand(Integer.toString(i));
            colButtons[i].addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int x = Integer.parseInt(e.getActionCommand());
                            int Col = board.checkSpace(x);
                            if (Col != -1) {
                                if (gl.move(x, Col, curr)) {
                                    gameWin = true;
                                } else if (gl.noTilesLeft()) {
                                    gameTie = true;
                                } else {
                                    curr = board.switchPlayer(curr);
                                }
                                moveList.add(Integer.toString(x + 1));
                                moves.setText(" Move #" + moveList.size() + " (Player " + curr +"'s turn): "
                                        + String.join(", ", moveList));
                            } else {
                                JOptionPane.showMessageDialog(null, "Choose a different column.",
                                        "Column  full!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            cPane.add(colButtons[i]);
        }

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                tiles[i][j] = new JLabel();
                tiles[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                tiles[i][j].setOpaque(true);
                tiles[i][j].setBackground(Color.blue);
                tiles[i][j].setBorder(new LineBorder(Color.black));
                cPane.add(tiles[i][j]);
            }
        }

        frame.add(cPane);
        frame.setSize(
                850, 700);
        JPanel movePanel = new JPanel();
        frame.add(movePanel, BorderLayout.SOUTH);
        movePanel.setPreferredSize(new Dimension(frame.getWidth(), 18));
        movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.X_AXIS));
        movePanel.add(moves);
        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar(mb);
        JMenu help = new JMenu("Instructions/Help");
        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String msg = "This game is a traditional rendition of the classic game Connect Four.\n\n" +
                                "To play just click the button at the top of the screen directly above the column\n" +
                                " you want to place your marker in. To win, you must get four of your markers in\n" +
                                " a row, vertically, horizontally, or diagonally. If nobody gets four in a row\n" +
                                " by the end of the game, it's a tie. If there is a winner, that player can\n" +
                                " add his/her name to the High Scores file and if the score is good enough\n" +
                                " it'll appear in the High Scores pane within the game.";
                        JOptionPane.showMessageDialog(frame, msg, "Instructions" , JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );
        help.add(instructions);
        mb.add(help);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void repaint() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board.tileMatch(i, j, 1)) {
                    tiles[i][j].setBackground(Color.red);
                }
                if (board.tileMatch(i, j, 2)) {
                    tiles[i][j].setBackground(Color.yellow);
                }
            }
        }
    }

    public boolean getNew() {
        return gameNew;
    }

    public boolean getEnd() {
        return gameEnd;
    }

    public boolean getWin() {
        return gameWin;
    }

    public boolean getTie() {
        return gameTie;
    }

    public void win() {
        String[] options = {"New Game", "End Game", "View High Scores", "Add your Score"};
        int optionsPane = JOptionPane.showOptionDialog(
                frame,
                "What would you like to do:",
                "Player " + curr + " wins in " + moveList.size() + " turns!",0,
                JOptionPane.QUESTION_MESSAGE,null, options,0);
        if (optionsPane == 0) {
            gameNew = true;
            frame.dispose();
        } else if (optionsPane == 1) {
            gameEnd = true;
            frame.dispose();
        } else if (optionsPane == 2) {
            addScores();
            scoreSort();
            String msg = ScoreLen();
            JOptionPane.showMessageDialog(frame, msg, "High Scores (lower is better)",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (optionsPane == 3) {
            String name = JOptionPane.showInputDialog("Enter name:");
            updateScores(name, moveList.size());
        }
    }

    public void tie() {
        String[] options = {"New Game", "End Game", "View High Scores"};
        int optionsPane = JOptionPane.showOptionDialog(
                frame,
                "What would you like to do:",
                "Tie Game!",0, JOptionPane.QUESTION_MESSAGE,null,
                options,0);
        if (optionsPane == 0) {
            gameNew = true;
            frame.dispose();
        } else if (optionsPane == 1) {
            gameEnd = true;
            frame.dispose();
        } else if (optionsPane == 2) {
            addScores();
            scoreSort();
            String msg = ScoreLen();
            JOptionPane.showMessageDialog(frame, msg, "High Scores (lower is better)",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateScores(String name, int score)  {
       try {
           FileWriter fw = new FileWriter(scores, true);
           BufferedWriter bw = new BufferedWriter(fw);
           if (name != null) {
               bw.newLine();
               bw.write(score + ": " + name);
           }
           bw.close();
       } catch (IOException e) {
           System.out.println("IOException occurred");
       }

    }

    public void addScores() {
       try {
           String line;
           FileReader fr = new FileReader(scores);
           BufferedReader br = new BufferedReader(fr);
           highScores.clear();
           while ((line = br.readLine()) != null) {
               if (line.length() != 0) {
                   String[] str = line.split(":");
                   highScores.add(new Player(Integer.parseInt(str[0]), str[1]));
               }
           }
           br.close();
       } catch (IOException e) {
           System.out.println("IOException occurred");
       }
    }

    public String ScoreLen() {
        if (highScores.size() == 0) {
            return "No High Scores Yet!";
        } else if (highScores.size() == 1) {
            return highScores.get(0).getScore() + ": " + highScores.get(0).getName();
        } else if (highScores.size() == 2) {
            return highScores.get(0).getScore() + ": " + highScores.get(0).getName() + "\n" +
                    highScores.get(1).getScore() + ": " + highScores.get(1).getName();
        } else {
            return highScores.get(0).getScore() + ": " + highScores.get(0).getName() + "\n"
                    + highScores.get(1).getScore() + ": " + highScores.get(1).getName() + "\n"
                    + highScores.get(2).getScore() + ": " + highScores.get(2).getName();
        }
    }

    public void scoreSort() {
        Comparator<Player> comparator = (p1, p2) -> p1.compareTo(p2);
        highScores.sort(comparator);
    }
}