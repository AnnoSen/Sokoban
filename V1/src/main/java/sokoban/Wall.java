package sokoban;
public class Wall extends Prop {
    public Wall() {
        ParseXml parseXml = new ParseXml();
        super.images = parseXml.parse("wall");
        super.x = images.get("wall_x");
        super.y = images.get("wall_y");
    }
}
