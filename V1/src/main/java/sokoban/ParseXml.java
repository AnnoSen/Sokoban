package sokoban;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Map;

public class ParseXml {
    public Map<String,Integer> parse(String tagName){
        Map<String,Integer> map = new HashMap<>();
        try {
            //1：创建SAXReader
            SAXReader reader = new SAXReader();
            //2：使用SAXReader读取xml文档并生成Document对象
            Document doc = reader.read("./xml/images.xml");
            //3：通过Document对象获取根元素
            Element root = doc.getRootElement();
            //4：按照xml文档的结构从根元素开始逐级获取元素以达到解析目的
            //获取指定标签名的标签
            Element element = root.element(tagName);
            //获取该标签下的所有子标签
            element.elements().forEach((e)-> map.put(e.getName(),Integer.parseInt(e.getText())));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }
}
