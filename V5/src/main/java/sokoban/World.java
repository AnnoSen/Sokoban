package sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Jpanel是容器，JFrame是窗口
 * Jpanel容器里放需要绘制的的内容,并添加到JFrame窗口中，且相比java.awt.Panel于javax.swing.Jpanel提供画布功能
 * 虽然JFrame也可以绘制内容，
 */
public class World extends JPanel {
    private final JFrame jf = new JFrame();
    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;
    private final int IMAGE_WIDTH;
    private final int IMAGE_HEIGHT;
    private List<Prop> props;
    private Hero hero;
    private int level;
    private int bournNumber;//在加载时确定箱子的数量，便于通关时的判断
    private boolean pass=false;
     {
         FRAME_WIDTH = 500;
         FRAME_HEIGHT = 500;
         IMAGE_WIDTH = 30;
         IMAGE_HEIGHT = 30;
         props = new ArrayList<>();
         level=0;
         //在创建窗口之前初始化资源
         start();
         button();
    }

    /**
     * 初始化窗口
     */
    private World(){
        jf.add(this);//将存放资源的容器添加到窗口中
        jf.setTitle("推箱子");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    /**
     * 将按钮添加到Jpanel容器中，而不能添加到窗口中，否则会出现无法显示的问题
     * 需要注意的是添加按钮时需要清空默认布局：setLayout(null);
     */
    private void button(){
        JButton last = new JButton("上一关");
        JButton next = new JButton("下一关");
        JButton read = new JButton("读取");
        JButton write = new JButton("保存");
        last.setBounds(130,420,100,30);
        next.setBounds(250,420,100,30);
        read.setBounds(420,380,60,30);
        write.setBounds(420,420,60,30);
        this.add(last);
        this.add(next);
        this.add(read);
        this.add(write);
        this.setLayout(null);
        this.setVisible(true);
        last.setFocusable(false);//使按钮失去焦点，否则键盘监控会失效
        next.setFocusable(false);
        read.setFocusable(false);
        write.setFocusable(false);
        last.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next(-1);
            }
        });
        next.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next(1);
            }
        });
        read.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParseObj po = new ParseObj();
                props = po.read();
                for(Prop prop:props){
                    if(prop instanceof Hero){
                        hero = (Hero) prop;
                    }
                }
                repaint();
            }
        });
        write.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParseObj po = new ParseObj();
                po.write(props);
            }
        });
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
        props.add(prop);
        if(prop instanceof Hero){
            hero = (Hero) prop;
        }
        if(prop instanceof Bourn){
            bournNumber++;
        }
    }

    /**
     * 人物前一格的对象，不包括路径、终点、人物
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
     * y方向移动
     */
    private void moveY(int number){
        int next1_x = hero.dx1;
        int next1_y = hero.dy1+number;
        int next2_x = hero.dx1;
        int next2_y = hero.dy1+number*2;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){//前一格没有障碍物，直接移动
            hero.dy1 = hero.dy1+number;
            hero.dy2 = hero.dy2+number;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){//前两格没有障碍物，可以移动
                hero.dy1 = hero.dy1+number;
                hero.dy2 = hero.dy2+number;
                prop1.dy1 = prop1.dy1+number;
                prop1.dy2 = prop1.dy2+number;
            }
        }
    }

    /**
     * x方向移动
     */
    private void moveX(int number){
        int next1_x = hero.dx1+number;
        int next1_y = hero.dy1;
        int next2_x = hero.dx1+number*2;
        int next2_y = hero.dy1;
        Prop prop1 = getProp1(next1_x,next1_y);
        if(prop1==null){
            //前一格没有障碍物，直接移动
            hero.dx1 = hero.dx1+number;
            hero.dx2 = hero.dx2+number;
        }else if(prop1 instanceof Box){
            Prop prop2 = getProp2(next2_x,next2_y);
            if(prop2==null){
                //前两格没有障碍物，可以移动
                hero.dx1 = hero.dx1+number;
                hero.dx2 = hero.dx2+number;
                prop1.dx1 = prop1.dx1+number;
                prop1.dx2 = prop1.dx2+number;
            }
        }
    }

    private void moveUp(){
        moveY(-IMAGE_HEIGHT);
    }
    private void moveDown(){
        moveY(IMAGE_HEIGHT);
    }
    private void moveLeft(){
        moveX(-IMAGE_WIDTH);
    }
    private void moveRight(){
        moveX(IMAGE_WIDTH);
    }

    /**
     * 通关，判断与箱子重合的是终点吗，如果不是为false
     */
    private void pass(){
        int bournNumber = 0;
        for(Prop prop1:props){
            if(prop1 instanceof Bourn){
                for(Prop prop2:props){
                    //如果目的地与箱子重合boxNumber++;
                    if(prop1.dx1==prop2.dx1&&prop1.dy1==prop2.dy1&&prop2 instanceof Box){
                        bournNumber++;
                    }
                }
            }
        }
        //当重合的数量与初始化时目的地的数量相同，则通关
        if(bournNumber==this.bournNumber){
            pass = true;
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
                pass();
            }
        };
        jf.addKeyListener(key);
    }

    /**
     * 启动
     */
    private void action(){
        keyListener();
    }

    /**
     * 下一关
     */
    private void next(int next){
        level+=next;
        //小于零跳转到最后一关，大于最大关卡回到第一关
        if(level<0){
            level = Level.level.length-1;
        }else if(level>=Level.level.length){
            level = 0;
        }
        pass = false;
        bournNumber=0;
        props = new ArrayList<>();
        start();
        repaint();
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
        if(pass){
            g.setFont(new Font(null, Font.BOLD,20));
            g.setColor(Color.RED);
            g.drawString("恭喜通关",190,50);
            next(1);
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
