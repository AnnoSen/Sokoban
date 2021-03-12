package sokoban;

public class Hero extends Prop{
    private int level;
    public Hero() {
        width=50;
        height=80;
        get_dx("hero","down_x","down_y");
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
