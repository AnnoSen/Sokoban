package sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class World extends JPanel {
    private final JFrame jf = new JFrame();
    private static final int FRAME_WIDTH;
    private static final int FRAME_HEIGHT;
    private static final int IMAGE_WIDTH;
    private static final int IMAGE_HEIGHT;
    private static final List<Wall> walls;
    private static final List<Bourn> bourns;
    private static final List<Box> boxs;
    private static final List<Track> tracks;
    private static Hero hero;
    private static final int level;
    static {
        FRAME_WIDTH = 500;
        FRAME_HEIGHT = 500;
        IMAGE_WIDTH = 30;
        IMAGE_HEIGHT = 30;
        walls = new ArrayList<>();
        bourns = new ArrayList<>();
        boxs = new ArrayList<>();
        tracks = new ArrayList<>();
        level=1;
        start();
    }
    /**
     * 初始化窗口
     */
    public World(){
        jf.add(this);
        jf.setTitle("推箱子");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    /**
     * 初始化所有需要显示的对象
     */
    private static void start(){
        for(int i=0;i<Level.level[level].length;i++){
            for(int j=0;j<Level.level[level][i].length;j++){
                switch (Level.level[level][i][j]){
                    case 1:
                        create(new Wall(),j,i);
                        break;
                    case 2:
                        create(new Bourn(),j,i);
                        break;
                    case 3:
                        create(new Box(),j,i);
                        break;
                    case 4:
                        create(new Track(),j,i);
                        break;
                    case 5:
                        create(new Hero(),j,i);
                       break;
                    default:
                        break;
                }
            }
        }
    }
    private static void create(Prop prop,int j,int i){
        prop.dx1=j*IMAGE_WIDTH;
        prop.dy1=i*IMAGE_HEIGHT;
        prop.dx2=prop.dx1+IMAGE_WIDTH;
        prop.dy2=prop.dy1+IMAGE_HEIGHT;
        if(prop instanceof Wall){
            walls.add((Wall) prop);
        }
        if(prop instanceof Bourn){
            bourns.add((Bourn) prop);
        }
        if(prop instanceof Box){
            boxs.add((Box) prop);
        }
        if(prop instanceof Track){
            tracks.add((Track) prop);
        }
        if(prop instanceof Hero){
            hero = (Hero) prop;
        }
    }

    /**
     * 键盘监听
     * 继承JPanel时，键盘监听需要添加到JFrame中，不能添加到this中
     */
    private void keyListener(){
        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    moveUp();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    moveDown();
                }
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    moveLeft();
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                    moveRight();
                }
                repaint();
            }
        };
        jf.addKeyListener(key);
    }

    /**
     * 人物移动：箱子可以移动，true或人物的前一格不是墙，true
     * 箱子移动：人物的前两格不是箱子，true&&人物的前两格不是墙，true
     */
    private void moveUp(){
        boolean b = true;
        //人物的前一格是墙,b=false
        for(Wall wall:walls){
            if(wall.dx1==hero.dx1&&wall.dy1==hero.dy1-IMAGE_HEIGHT){
                b = false;
                break;
            }
        }
        //人物的前一格是路,角色移动后return;
        if(b){
            for(Track track:tracks){
                if(track.dx1==hero.dx1&&track.dy1==hero.dy1-IMAGE_HEIGHT){
                    //角色前移，地板后移
                    hero.dy1 = hero.dy1-IMAGE_HEIGHT;
                    hero.dy2 = hero.dy2-IMAGE_HEIGHT;
                    track.dy1 = track.dy1+IMAGE_HEIGHT;
                    track.dy2 = track.dy2+IMAGE_HEIGHT;
                    return;
                }
            }
        }
        //人物的前一格箱子,b=true;
        Box box = null;
        if(b){
            boolean b1 = false;
            for(Box box1:boxs){
                if (box1.dx1 == hero.dx1 && box1.dy1 == hero.dy1-IMAGE_HEIGHT) {
                    box = box1;
                    b1 = true;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一个是箱子,b=false
        if(b){
            boolean b1 = true;
            for(Box box1:boxs){
                if (box != box1 && box1.dx1 == box.dx1 && box1.dy1 == box.dy1 - IMAGE_HEIGHT) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一格是墙,b=false
        if(b){
            boolean b1 = true;
            for(Wall wall:walls){
                if (wall.dx1 == box.dx1 && wall.dy1 == box.dy1 - IMAGE_HEIGHT) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //b=true时,角色移动,箱子移动
        if(b){
            //删除原位置的路，在人物的位置添加路
            Track track = new Track();
            track.dx1=hero.dx1;
            track.dy1=hero.dy1;
            track.dx2=hero.dx2;
            track.dy2=hero.dy2;
            tracks.add(track);

            hero.dy1 = hero.dy1-IMAGE_HEIGHT;
            hero.dy2 = hero.dy2-IMAGE_HEIGHT;
            box.dy1 = box.dy1-IMAGE_HEIGHT;
            box.dy2 = box.dy2-IMAGE_HEIGHT;

            //删除箱子位置的路
            for(Track track1:tracks){
                if(track1.dx1==box.dx1&&track1.dy1==box.dy1){
                    tracks.remove(track1);
                    break;
                }
            }
        }
    }
    private void moveDown(){
        boolean b = true;
        //人物的前一格是墙,b=false
        for(Wall wall:walls){
            if(wall.dx1==hero.dx1&&wall.dy1==hero.dy1+IMAGE_HEIGHT){
                b = false;
                break;
            }
        }
        //人物的前一格是路,角色移动后return;
        if(b){
            for(Track track:tracks){
                if(track.dx1==hero.dx1&&track.dy1==hero.dy1+IMAGE_HEIGHT){
                    //角色前移，地板后移
                    hero.dy1 = hero.dy1+IMAGE_HEIGHT;
                    hero.dy2 = hero.dy2+IMAGE_HEIGHT;
                    track.dy1 = track.dy1-IMAGE_HEIGHT;
                    track.dy2 = track.dy2-IMAGE_HEIGHT;
                    return;
                }
            }
        }
        //人物的前一格箱子,b=true;
        Box box = null;
        if(b){
            boolean b1 = false;
            for(Box box1:boxs){
                if (box1.dx1 == hero.dx1 && box1.dy1 == hero.dy1+IMAGE_HEIGHT) {
                    box = box1;
                    b1 = true;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一个是箱子,b=false
        if(b){
            boolean b1 = true;
            for(Box box1:boxs){
                if (box != box1 && box1.dx1 == box.dx1 && box1.dy1 == box.dy1 + IMAGE_HEIGHT) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一格是墙,b=false
        if(b){
            boolean b1 = true;
            for(Wall wall:walls){
                if (wall.dx1 == box.dx1 && wall.dy1 == box.dy1 + IMAGE_HEIGHT) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //b=true时,角色移动,箱子移动
        if(b){
            //在人物的位置添加路
            Track track = new Track();
            track.dx1=hero.dx1;
            track.dy1=hero.dy1;
            track.dx2=hero.dx2;
            track.dy2=hero.dy2;
            tracks.add(track);

            hero.dy1 = hero.dy1+IMAGE_HEIGHT;
            hero.dy2 = hero.dy2+IMAGE_HEIGHT;
            box.dy1 = box.dy1+IMAGE_HEIGHT;
            box.dy2 = box.dy2+IMAGE_HEIGHT;
            //删除箱子位置的路
            for(Track track1:tracks){
                if(track1.dx1==box.dx1&&track1.dy1==box.dy1){
                    tracks.remove(track1);
                    break;
                }
            }
        }
    }
    private void moveLeft(){
        boolean b = true;
        //人物的前一格是墙,b=false
        for(Wall wall:walls){
            if(wall.dx1==hero.dx1-IMAGE_WIDTH&&wall.dy1==hero.dy1){
                b = false;
                break;
            }
        }
        //人物的前一格是路,角色移动后return;
        if(b){
            for(Track track:tracks){
                if(track.dx1==hero.dx1-IMAGE_WIDTH&&track.dy1==hero.dy1){
                    //角色前移，地板后移
                    hero.dx1 = hero.dx1-IMAGE_WIDTH;
                    hero.dx2 = hero.dx2-IMAGE_WIDTH;
                    track.dx1 = track.dx1+IMAGE_WIDTH;
                    track.dx2 = track.dx2+IMAGE_WIDTH;
                    return;
                }
            }
        }
        //人物的前一格箱子,b=true;
        Box box = null;
        if(b){
            boolean b1 = false;
            for(Box box1:boxs){
                if (box1.dx1 == hero.dx1-IMAGE_WIDTH && box1.dy1 == hero.dy1) {
                    box = box1;
                    b1 = true;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一个是箱子,b=false
        if(b){
            boolean b1 = true;
            for(Box box1:boxs){
                if (box != box1 && box1.dx1 == box.dx1-IMAGE_WIDTH && box1.dy1 == box.dy1) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一格是墙,b=false
        if(b){
            boolean b1 = true;
            for(Wall wall:walls){
                if (wall.dx1 == box.dx1-IMAGE_WIDTH && wall.dy1 == box.dy1) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //b=true时,角色移动,箱子移动
        if(b){
            //删除原位置的路，在人物的位置添加路
            Track track = new Track();
            track.dx1=hero.dx1;
            track.dy1=hero.dy1;
            track.dx2=hero.dx2;
            track.dy2=hero.dy2;
            tracks.add(track);

            hero.dx1 = hero.dx1-IMAGE_WIDTH;
            hero.dx2 = hero.dx2-IMAGE_WIDTH;
            box.dx1 = box.dx1-IMAGE_WIDTH;
            box.dx2 = box.dx2-IMAGE_WIDTH;
            //删除箱子位置的路
            for(Track track1:tracks){
                if(track1.dx1==box.dx1&&track1.dy1==box.dy1){
                    tracks.remove(track1);
                    break;
                }
            }
        }
    }
    private void moveRight(){
        boolean b = true;
        //人物的前一格是墙,b=false
        for(Wall wall:walls){
            if(wall.dx1==hero.dx1+IMAGE_WIDTH&&wall.dy1==hero.dy1){
                b = false;
                break;
            }
        }
        //人物的前一格是路,角色移动后return;
        if(b){
            for(Track track:tracks){
                if(track.dx1==hero.dx1+IMAGE_WIDTH&&track.dy1==hero.dy1){
                    //角色前移，地板后移
                    hero.dx1 = hero.dx1+IMAGE_WIDTH;
                    hero.dx2 = hero.dx2+IMAGE_WIDTH;
                    track.dx1 = track.dx1-IMAGE_WIDTH;
                    track.dx2 = track.dx2-IMAGE_WIDTH;
                    return;
                }
            }
        }
        //人物的前一格箱子,b=true;
        Box box = null;
        if(b){
            boolean b1 = false;
            for(Box box1:boxs){
                if (box1.dx1 == hero.dx1+IMAGE_WIDTH && box1.dy1 == hero.dy1) {
                    box = box1;
                    b1 = true;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一个是箱子,b=false
        if(b){
            boolean b1 = true;
            for(Box box1:boxs){
                if (box != box1 && box1.dx1 == box.dx1+IMAGE_WIDTH && box1.dy1 == box.dy1) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //箱子的前一格是墙,b=false
        if(b){
            boolean b1 = true;
            for(Wall wall:walls){
                if (wall.dx1 == box.dx1+IMAGE_WIDTH && wall.dy1 == box.dy1) {
                    b1 = false;
                    break;
                }
            }
            b = b1;
        }
        //b=true时,角色移动,箱子移动
        if(b){
            //删除原位置的路，在人物的位置添加路
            Track track = new Track();
            track.dx1=hero.dx1;
            track.dy1=hero.dy1;
            track.dx2=hero.dx2;
            track.dy2=hero.dy2;
            tracks.add(track);

            hero.dx1 = hero.dx1+IMAGE_WIDTH;
            hero.dx2 = hero.dx2+IMAGE_WIDTH;
            box.dx1 = box.dx1+IMAGE_WIDTH;
            box.dx2 = box.dx2+IMAGE_WIDTH;
            //删除箱子位置的路
            for(Track track1:tracks){
                if(track1.dx1==box.dx1&&track1.dy1==box.dy1){
                    tracks.remove(track1);
                    break;
                }
            }
        }
    }

    /**
     * 启动
     */
    private void action(){
        keyListener();
    }

    /**
     * 绘制图像
     * 重写paintComponent()绘制背景
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        walls.forEach(prop -> prop.paint(g));
        tracks.forEach(prop ->prop.paint(g));
        bourns.forEach(prop ->prop.paint(g));
    }

    /**
     * 重写paint()绘制移动的组件
     */
    public void paint(Graphics g){
        super.paint(g);//调用父类的paint()，不然无法加载重写的paintComponent()
        boxs.forEach(prop ->prop.paint(g));
        hero.paint(g);
    }

    /**
     * 重写update()，当调用repaint()重绘时不刷新背景图
     */
    public void update(Graphics g){
        paint(g);
    }

    public static void main(String[] args) {
        new World().action();
    }
}
