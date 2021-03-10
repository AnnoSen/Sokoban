package sokoban;

public class Hero extends Prop {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 80;
    public static int hero_x;
    public static int hero_y;
    public Hero() {
        ParseXml parseXml = new ParseXml();
        super.images = parseXml.parse("hero");
        super.x = images.get("down_x");
        super.y = images.get("down_y");
    }
}
