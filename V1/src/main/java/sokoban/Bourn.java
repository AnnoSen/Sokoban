package sokoban;

public class Bourn extends Prop {
    public Bourn() {
        ParseXml parseXml = new ParseXml();
        super.images = parseXml.parse("bourn");
        super.x = images.get("bourn_x");
        super.y = images.get("bourn_y");
    }
}
