package sokoban;

public class Track extends Prop {
    public Track() {
        ParseXml parseXml = new ParseXml();
        super.images = parseXml.parse("track");
        super.x = images.get("track_x");
        super.y = images.get("track_y");
    }
}
