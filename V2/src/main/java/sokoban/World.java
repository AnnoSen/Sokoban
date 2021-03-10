package sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class World extends JFrame {
    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;
    private final int IMAGE_WIDTH;
    private final int IMAGE_HEIGHT;
    private final List<Prop> list;
    private final List<Prop> boxs;
    private Prop hero;
    private final int level;
    {
        FRAME_WIDTH =500;
        FRAME_HEIGHT=500;
        IMAGE_WIDTH = 30;
        IMAGE_HEIGHT = 30;
        list = new ArrayList<>();
        boxs =new ArrayList<>();
        level=0;
    }
    /**
     * 初始化窗口
     */
    public World(){
        setTitle("推箱子");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * 初始化所有需要显示的对象
     */
    private void start(){
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
                        create(new Track(),j,i);
                        create(new Box(),j,i);
                        break;
                    case 4:
                        create(new Track(),j,i);
                        break;
                    case 5:
                        create(new Track(),j,i);
                        create(new Hero(),j,i);
                       break;
                    default:
                        break;
                }
            }
        }
    }
    private void create(Prop prop,int j,int i){
        prop.dx1=j*IMAGE_WIDTH;
        prop.dy1=i*IMAGE_HEIGHT;
        prop.dx2=prop.dx1+IMAGE_WIDTH;
        prop.dy2=prop.dy1+IMAGE_HEIGHT;
        if(prop instanceof Wall||prop instanceof Track||prop instanceof Bourn){
            list.add(prop);
        }
        if(prop instanceof Hero){
            hero = prop;
        }
        if(prop instanceof Box){
            boxs.add(prop);
        }
    }

    /**
     * 键盘监听
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
        this.addKeyListener(key);
    }

    private void moveUp(){
        boolean b = true;
        for(Prop prop:list){
            if (prop instanceof Wall && prop.dx1 == hero.dx1 && prop.dy1 == hero.dy1 - IMAGE_HEIGHT) {//人物前面一格是墙
                b = false;
                break;
            }
        }
        if(b){
            for(Prop prop:list) {
                if (prop instanceof Track && prop.dx1 == hero.dx1 && prop.dy1 == hero.dy1 - IMAGE_HEIGHT) {//人物前面一格是路
                    hero.dy1 = hero.dy1 - IMAGE_HEIGHT;
                    hero.dy2 = hero.dy2 - IMAGE_HEIGHT;
                    break;
                } else {//人物前一格不是路
                    for (Prop box : boxs) {
                        if (box.dx1 == hero.dx1 && box.dy1 == hero.dy1 - IMAGE_HEIGHT) {//人物的前面一个是箱子
                            for (Prop p : list) {
                                if (p instanceof Track || p instanceof Bourn && p.dx1 == box.dx1 && p.dy1 == box.dy1 - IMAGE_HEIGHT) {//箱子的前一格是路或终点
                                    hero.dy1 = hero.dy1 - IMAGE_HEIGHT;
                                    hero.dy2 = hero.dy2 - IMAGE_HEIGHT;
                                    box.dy1 = box.dy1 - IMAGE_HEIGHT;
                                    box.dy2 = box.dy2 - IMAGE_HEIGHT;
                                    return;
                                }else  if(p instanceof Wall && p.dx1 == box.dx1 && p.dy1 == box.dy1 - IMAGE_HEIGHT){//如果箱子前面一格是墙则退出
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void moveDown(){
        boolean b = true;
        for(Prop prop:list){
            if (prop instanceof Wall && prop.dx1 == hero.dx1 && prop.dy1 == hero.dy1 + IMAGE_HEIGHT) {//人物前面一格是墙
                b = false;
                break;
            }
        }
        if(b){
            for(Prop prop:list) {
                if (prop instanceof Track && prop.dx1 == hero.dx1 && prop.dy1 == hero.dy1 + IMAGE_HEIGHT) {//人物前面一格是路
                    hero.dy1 = hero.dy1 + IMAGE_HEIGHT;
                    hero.dy2 = hero.dy2 + IMAGE_HEIGHT;
                    break;
                } else {//人物前一格不是路
                    for (Prop box : boxs) {
                        if (box.dx1 == hero.dx1 && box.dy1 == hero.dy1 + IMAGE_HEIGHT) {//人物的前面一个是箱子
                            for (Prop p : list) {
                                if (p instanceof Track || p instanceof Bourn && p.dx1 == box.dx1 && p.dy1 == box.dy1 + IMAGE_HEIGHT) {//箱子的前一格是路或终点
                                    hero.dy1 = hero.dy1 + IMAGE_HEIGHT;
                                    hero.dy2 = hero.dy2 + IMAGE_HEIGHT;
                                    box.dy1 = box.dy1 + IMAGE_HEIGHT;
                                    box.dy2 = box.dy2 + IMAGE_HEIGHT;
                                    return;
                                }else  if(p instanceof Wall && p.dx1 == box.dx1 && p.dy1 == box.dy1 + IMAGE_HEIGHT){//如果箱子前面一格是墙则退出
                                    System.out.println("...");
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void moveLeft(){
        boolean b = true;
        for(Prop prop:list){
            if(prop instanceof Wall&&prop.dx1+IMAGE_WIDTH==hero.dx1&&prop.dy1==hero.dy1){
                b=false;
            }
        }
        if(b){
            hero.dx1 = hero.dx1-IMAGE_WIDTH;
            hero.dx2 = hero.dx2-IMAGE_WIDTH;
            boxs.forEach((box)->{
                if(box.dx1==hero.dx1&&box.dy1 == hero.dy1){
                    box.dx1 = box.dx1-IMAGE_HEIGHT;
                    box.dx2 = box.dx2-IMAGE_HEIGHT;
                }
            });
        }
    }
    private void moveRight(){
        boolean b = true;
        for(Prop prop:list){
            if(prop instanceof Wall&&prop.dx1-IMAGE_WIDTH==hero.dx1&&prop.dy1==hero.dy1){
                b=false;
            }
        }
        if(b){
            hero.dx1 = hero.dx1+IMAGE_WIDTH;
            hero.dx2 = hero.dx2+IMAGE_WIDTH;
            boxs.forEach((box)->{
                if(box.dx1==hero.dx1&&box.dy1 == hero.dy1){
                    box.dx1 = box.dx1+IMAGE_HEIGHT;
                    box.dx2 = box.dx2+IMAGE_HEIGHT;
                }
            });
        }
    }

    /**
     * 启动
     */
    private void action(){
        start();
        repaint();
        keyListener();
    }

    /**
     * 绘制图像
     */
    public void paint(Graphics g){
        list.forEach((prop)-> prop.paint(g));
        if(hero!=null){
            hero.paint(g);
        }
        if(boxs.size()>0){
            boxs.forEach((box)-> box.paint(g));
        }
    }

    public static void main(String[] args) {
        new World().action();
    }
}
