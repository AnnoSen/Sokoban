package sokoban;

import java.io.*;
import java.util.List;

public class ParseObj {
    public void write(List<Prop> props){
        try (
                FileOutputStream fis = new FileOutputStream("./obj/data.obj");
                ObjectOutputStream oos = new ObjectOutputStream(fis)
        ){
            oos.writeObject(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Prop> read(){
        try (
                FileInputStream fis = new FileInputStream("./obj/data.obj");
                ObjectInputStream oos = new ObjectInputStream(fis)
        ) {
            return (List<Prop>)oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
