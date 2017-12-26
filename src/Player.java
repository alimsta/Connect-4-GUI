public class Player {
    private int score;
    private String name;

    public Player (int num, String str) {
        score = num;
        name = str;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    
    public int compareTo(Player p) {
        if ((this.getScore() - p.getScore()) < 0) {
            return -1;
        } else if ((this.getScore() - p.getScore()) > 0) {
            return 1;
        } else
            return 0;
    }
}