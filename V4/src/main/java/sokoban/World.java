package sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class World extends JPanel {
    private final JFrame jf = new JFrame();
    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;
    private final int IMAGE_WIDTH;
    private final int IMAGE_HEIGHT;
    private final List<Prop> props;
    private Hero hero;
    private final int level;
     {
        FRAME_WIDTH = 500;
        FRAME_HEIGHT = 500;
        IMAGE_WIDTH = 30;
        IMAGE_HEIGHT = 30;
        props = new ArrayList<>();
        level=1;
        start();//放在构造块中，在创建窗口之前初始化资源，如果放在其他方法中需要初始化资源后repaint()
    }
    /**
     * 初始化窗口
     */
    private World(){
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
    private void start(){
        Level l = new Level();
        for(int i=0;i<l.level[level].length;i++){
            for(int j=0;j<l.level[level][i].length;j++){
                switch (l.level[level][i][j]){
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
        props.add(prop);
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
     *人物前面一格的对象，不包括路径、终点、人物
     */
    private Prop getProp1(int next1_x,int next1_y){
        for(Prop prop:props) {
            if ((prop instanceof Wall||prop instanceof Box)&&(prop.dx1 == next1_x && prop.dy1 == next1_y)) {
                return prop;
            }
        }
        return null;
    }
    /**
     * 人物前两格的对象，不包括路径、终点、人物
     */
    private Prop getProp2(int next2_x,int next2_y){
        for(Prop prop:props) {
            if ((prop instanceof Wall||prop instanceof Box)&&(prop.dx1 == next2_x && prop.dy1 == next2_y)) {
                return prop;
            }
        }
        return null;
    }
    /**
     * 人物移动：是箱子可以移动，true或人物的前一格不是墙，true
     * 箱子移动：人物的前两格不是箱子，true&&人物的前两格不是墙，true
     */
    private void moveUp(){
        int next1_x = hero.dx1;
        int next1_y = hero.dy1-IMAGE_HEIGHT;
        int next2_x = hero.dx1;
        int next2_y = hero.dy1-2*IMAGE_HEIGHT;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){
            //前一格没有障碍物，直接移动
            hero.dy1 = hero.dy1-IMAGE_HEIGHT;
            hero.dy2 = hero.dy2-IMAGE_HEIGHT;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){
                //前两格没有障碍物，可以移动
                hero.dy1 = hero.dy1-IMAGE_HEIGHT;
                hero.dy2 = hero.dy2-IMAGE_HEIGHT;
                prop1.dy1 = prop1.dy1-IMAGE_HEIGHT;
                prop1.dy2 = prop1.dy2-IMAGE_HEIGHT;
            }
        }
    }
    private void moveDown(){
        int next1_x = hero.dx1;
        int next1_y = hero.dy1+IMAGE_HEIGHT;
        int next2_x = hero.dx1;
        int next2_y = hero.dy1+2*IMAGE_HEIGHT;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){
            //前一格没有障碍物，直接移动
            hero.dy1 = hero.dy1+IMAGE_HEIGHT;
            hero.dy2 = hero.dy2+IMAGE_HEIGHT;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){
                //前两格没有障碍物，可以移动
                hero.dy1 = hero.dy1+IMAGE_HEIGHT;
                hero.dy2 = hero.dy2+IMAGE_HEIGHT;
                prop1.dy1 = prop1.dy1+IMAGE_HEIGHT;
                prop1.dy2 = prop1.dy2+IMAGE_HEIGHT;
            }
        }
    }
    private void moveLeft(){
        int next1_x = hero.dx1-IMAGE_HEIGHT;
        int next1_y = hero.dy1;
        int next2_x = hero.dx1-2*IMAGE_HEIGHT;
        int next2_y = hero.dy1;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){
            //前一格没有障碍物，直接移动
            hero.dx1 = hero.dx1-IMAGE_HEIGHT;
            hero.dx2 = hero.dx2-IMAGE_HEIGHT;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){
                //前两格没有障碍物，可以移动
                hero.dx1 = hero.dx1-IMAGE_HEIGHT;
                hero.dx2 = hero.dx2-IMAGE_HEIGHT;
                prop1.dx1 = prop1.dx1-IMAGE_HEIGHT;
                prop1.dx2 = prop1.dx2-IMAGE_HEIGHT;
            }
        }
    }
    private void moveRight(){
        int next1_x = hero.dx1+IMAGE_HEIGHT;
        int next1_y = hero.dy1;
        int next2_x = hero.dx1+2*IMAGE_HEIGHT;
        int next2_y = hero.dy1;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){
            //前一格没有障碍物，直接移动
            hero.dx1 = hero.dx1+IMAGE_HEIGHT;
            hero.dx2 = hero.dx2+IMAGE_HEIGHT;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){
                //前两格没有障碍物，可以移动
                hero.dx1 = hero.dx1+IMAGE_HEIGHT;
                hero.dx2 = hero.dx2+IMAGE_HEIGHT;
                prop1.dx1 = prop1.dx1+IMAGE_HEIGHT;
                prop1.dx2 = prop1.dx2+IMAGE_HEIGHT;
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
        for(Prop prop:props){
            if(prop instanceof Wall||prop instanceof Track||prop instanceof Bourn){
                prop.paint(g);
            }
        }
    }

    /**
     * 重写paint()绘制移动的组件
     */
    public void paint(Graphics g){
        super.paint(g);//调用父类的paint()，不然无法加载重写的paintComponent()
        for(Prop prop:props){
            if(prop instanceof Box||prop instanceof Hero){
                prop.paint(g);
            }
        }
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
