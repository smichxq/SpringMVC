package com.example.springmvc;

import com.example.springmvc.util.SensitiveWordsFilter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class TempTest {

    @Test
    public void test() {
//        System.out.println(System.getProperty("user.dir"));

        try {
            File file = new File("src/main/resources/sensitive/sensitive_demo.txt");
            FileReader fileReader = new FileReader(file);
            int ch;

            while ((ch = fileReader.read()) != -1 ) {
                System.out.println((char) ch);
            }



        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }



    }

//    @Test
//    public void test2(){
//
//        try {
//            char[] chars = SensitiveWordsFilter.inputStreamToCharacter(new FileReader(
//                    new File("src/main/resources/sensitive/sensitive_demo.txt")
//            ));
//
//            System.out.println(chars);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Test
    public void test3() {
        HashMap<String, Node> hashMap = new HashMap<>();
        Node node = new Node();
        node.aBoolean= true;
        node.string = "s";

        Node node1 = new Node();
        node.aBoolean=true;
        node1.string="s";

        hashMap.put(
                "s1",node
        );
        System.out.println(hashMap.toString());
        hashMap.put("s1",node1);
        System.out.println(hashMap.toString());

    }

    class Node {
        public String string;
        public boolean aBoolean;
    }


    @Test
    public void test4() throws Exception {
        SensitiveWordsFilter sensitiveWordsFilter = new SensitiveWordsFilter();
//        char[] chars = sensitiveWordsFilter.inputStreamToCharacter(new FileReader(new File("src/main/resources/sensitive/sensitive_demo.txt")));
//
//        for (char ch :
//                chars) {
//            System.out.println(ch);
//        }

        sensitiveWordsFilter.treeInit(new File("src/main/resources/sensitive/sensitive_demo.txt"));
        sensitiveWordsFilter.getTireTreeNodeRoot();


    }
}
