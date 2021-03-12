package save;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        Student stu = new Student();
        stu.setName("zhangSan");
        list.add(stu);
        Save save = new Save();
        //save.write(list);
        save.read();
    }
}
