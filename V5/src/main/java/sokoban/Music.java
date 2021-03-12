package sokoban;

import javax.swing.*;
import java.applet.Applet;
import java.io.File;
public class Music{
    JFrame jf;
    public Music(JFrame jf){
        this.jf = jf;

    }
    public void loop(){
        try {
            File file = new File("./music/bgm1.wav");
            Applet.newAudioClip(file.toURI().toURL()).loop();
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("没找到文件！");
        }
    }
}
