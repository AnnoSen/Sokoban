package sokoban;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Prop {
    protected int width = 80;
    protected int height = 80;
    public int x;
    public int y;
    protected int dx1;
    protected int dy1;
    protected int dx2;
    protected int dy2;
    protected int sx1;
    protected int sy1;
    protected int sx2;
    protected int sy2;
    protected Map<String,Integer> images = new HashMap<>();

    /**
     * 获取截取图像所需的坐标
     * @param tag 父标签
     * @param tag1 标签对应的x
     * @param tag2 标签对应的y
     */
    protected void get_dx(String tag,String tag1,String tag2){
        ParseXml parseXml = new ParseXml();
        images = parseXml.parse(tag);
        sx1 = images.get(tag1);
        sy1 = images.get(tag2);
        sx2 = sx1+width;
        sy2 = sy1+height;
    }
    public void paint(Graphics g){
        g.drawImage(Images.loadImage(),dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
    }
}
