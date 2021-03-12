package save;

import java.io.*;
import java.util.List;

public class Save  {
    public void write(List<Student> stus) {
        try (
                FileOutputStream fos = new FileOutputStream("./obj/data.obj");
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(stus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void read(){
        try (
                FileInputStream fis = new FileInputStream("./obj/data.obj");
                ObjectInputStream ois = new ObjectInputStream(fis);
        ){
            List<Student> stus = (List<Student>) ois.readObject();
            System.out.println(stus.get(0).getName());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
