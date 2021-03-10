package sokoban;

public class Box extends Prop {
    public Box() {
        ParseXml parseXml = new ParseXml();
        super.images = parseXml.parse("box");
        super.x = images.get("box_x_0");
        super.y = images.get("box_y_0");
    }
}
