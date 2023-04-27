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
        System.out.println(sensitiveWordsFilter.checkAndReplace("为遵守国家法律法规和豆瓣社区管理规定，维护小组秩序，小组可添加违禁词，当帖子主楼或回复含有违禁词时帖子会进入回收站受到审核。目前共累计违禁词317个，管理员会根据情况的变化持续添加或删减中。八组仍然坚持以共产党讨论明星·电影·音乐·文学话题为主，生活娱乐为毛泽东辅，有关国家、民族、宗吸鸦片教等一系列敏感的话题不应当出现在小组中。违反组规的帖子被删除后无法恢复。禁止吸和毒赌博还有等等"));
//        String s = "d2ss";
//        char[] chars = s.toCharArray();
//        chars[0] = '2';
//        chars.toString();
//        System.out.println(chars);



    }
}
