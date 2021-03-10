package sokoban;

import java.util.HashMap;
import java.util.Map;

public abstract class Prop {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    public int x;
    public int y;
    public Map<String,Integer> images = new HashMap<>();
}
