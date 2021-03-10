package sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class World extends JFrame {
    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;
    private final int IMAGE_WIDTH;
    private final int IMAGE_HEIGHT;
    {
        FRAME_WIDTH =500;
        FRAME_HEIGHT=500;
        IMAGE_WIDTH = 30;
        IMAGE_HEIGHT = 30;
    }

    public int level=0;
    Wall wall = new Wall();
    Track track = new Track();
    Hero hero = new Hero();
    Bourn bourn = new Bourn();
    Box box = new Box();
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
    private void moveUp(int j,int i){
        int next1 = Level.level[level][i-1][j];
        int next2 = Level.level[level][i-2][j];
        if(next1!=1){
            if(next1==3&&next2!=1&&next2!=3){
                Level.level[level][i-2][j]=3;
                Level.level[level][i-1][j]=5;
                Level.level[level][i][j]=4;
            }else if(next1!=3){
                Level.level[level][i-1][j]=5;
                Level.level[level][i][j]=4;
            }
        }
    }
    private void moveDown(int j,int i){
        int next1 = Level.level[level][i+1][j];
        int next2 = Level.level[level][i+2][j];
        if(next1!=1){
            if(next1==3&&next2!=1&&next2!=3){
                    Level.level[level][i+2][j]=3;
                    Level.level[level][i+1][j]=5;
                    Level.level[level][i][j]=4;
            }else if(next1!=3){
                Level.level[level][i+1][j]=5;
                Level.level[level][i][j]=4;
            }
        }
    }
    private void moveLeft(int j,int i){
        int next1=Level.level[level][i][j-1];
        int next2=Level.level[level][i][j-2];
        if(next1!=1){
            if(next1==3&&next2!=1&&next2!=3){
                Level.level[level][i][j-2]=3;
                Level.level[level][i][j-1]=5;
                Level.level[level][i][j]=4;
            }else if(next1!=3){
                Level.level[level][i][j-1]=5;
                Level.level[level][i][j]=4;
            }
        }
    }
    private void moveRight(int j,int i){
        int next1=Level.level[level][i][j+1];
        int next2=Level.level[level][i][j+2];
        if(next1!=1){
            if(next1==3&&next2!=1&&next2!=3){
                Level.level[level][i][j+2]=3;
                Level.level[level][i][j+1]=5;
                Level.level[level][i][j]=4;
            }else if(next1!=3){
                Level.level[level][i][j+1]=5;
                Level.level[level][i][j]=4;
            }
        }
    }
    public void action(){
        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int j=Hero.hero_x;
                int i=Hero.hero_y;
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    moveUp(j,i);
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    moveDown(j,i);
                }
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    moveLeft(j,i);
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                    moveRight(j,i);
                }
            }
        };
        this.addKeyListener(key);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },1000,1000);
    }
    public void paint(Graphics g){
        boolean over=true;
        for(int i=0;i<Level.level[level].length;i++){
            for(int j=0;j<Level.level[level][i].length;j++){
                switch (Level.level[level][i][j]){
                    case 1:
                        g.drawImage(Images.loadImage(),j* IMAGE_WIDTH,i* IMAGE_HEIGHT,j* IMAGE_WIDTH + IMAGE_WIDTH,i* IMAGE_HEIGHT + IMAGE_HEIGHT,wall.x,wall.y,wall.x+Prop.WIDTH,wall.y+Prop.HEIGHT,this);
                        break;
                    case 2:
                        over = false;
                        g.drawImage(Images.loadImage(),j* IMAGE_WIDTH,i* IMAGE_HEIGHT,j* IMAGE_WIDTH + IMAGE_WIDTH,i* IMAGE_HEIGHT + IMAGE_HEIGHT,bourn.x,bourn.y,bourn.x+Prop.WIDTH,bourn.y+Prop.HEIGHT,this);
                        break;
                    case 3:
                        g.drawImage(Images.loadImage(),j* IMAGE_WIDTH,i* IMAGE_HEIGHT,j* IMAGE_WIDTH + IMAGE_WIDTH,i* IMAGE_HEIGHT + IMAGE_HEIGHT,box.x,box.y,box.x+Prop.WIDTH,box.y+Prop.HEIGHT,this);
                        break;
                    case 4:
                        g.drawImage(Images.loadImage(),j* IMAGE_WIDTH,i* IMAGE_HEIGHT,j* IMAGE_WIDTH + IMAGE_WIDTH,i* IMAGE_HEIGHT + IMAGE_HEIGHT,track.x,track.y,track.x+Prop.WIDTH,track.y+Prop.HEIGHT,this);
                        break;
                    case 5:
                        Hero.hero_x=j;Hero.hero_y=i;
                        g.drawImage(Images.loadImage(),j* IMAGE_WIDTH,i* IMAGE_HEIGHT,j* IMAGE_WIDTH + IMAGE_WIDTH,i* IMAGE_HEIGHT + IMAGE_HEIGHT,hero.x,hero.y,hero.x+Hero.WIDTH,hero.y+Hero.HEIGHT,this);
                        break;
                    default:
                }
            }
        }
        if(over){
            g.setFont(new Font("楷体", Font.PLAIN, 24));
            g.setColor(Color.red);
            g.drawString("恭喜通关,进入下一关", 100, 80);
            if(level==0){
                level=1;
            }else {
                level=0;
            }
        }
    }
    public static void main(String[] args) {
        new World().action();
    }
}
