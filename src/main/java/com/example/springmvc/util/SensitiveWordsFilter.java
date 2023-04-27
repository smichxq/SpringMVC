package com.example.springmvc.util;

import com.example.springmvc.demo.A;
import com.sun.source.tree.Tree;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class SensitiveWordsFilter {
//    Map<String,TireTreeNode> leafs;
    private static final String replaceWith = "*";

    private TireTreeNode TireTreeNodeRoot = new TireTreeNode();

    /**
     * 前缀树
     * 只有根节点为空
     * 节点的字符不能重复
     */

     class TireTreeNode {

//        String word;
        Character word;
        //后继(多个)
//        HashSet<TireTreeNode> next;

        //不使用Hash的原因：在生成树时，无法根据字符去重
        //使用HashMap的原因：可以根据key去重
        HashMap<Character,TireTreeNode> next;
        //结尾
        boolean end;

        public Character getWord() {
            return word;
        }

        public TireTreeNode setWord(Character word) {
            this.word = word;
            return this;
        }

//        public HashSet<TireTreeNode> getNext() {
//            return next;
//        }
//
//        public TireTreeNode setNext(HashSet<TireTreeNode> next) {
//            this.next = next;
//            return this;
//        }


        public HashMap<Character, TireTreeNode> getNext() {
            return next;
        }

        public TireTreeNode setNext(HashMap<Character, TireTreeNode> next) {
            this.next = next;
            return this;
        }

        public boolean isEnd() {
            return end;
        }

        public TireTreeNode setEnd(boolean end) {
            this.end = end;
            return this;
        }
    }


    /**
     *
     * @param file
     * @return TireTreeNode
     * 建议将要屏蔽的文字按照示例存放到文件中
     * eg: 赌博 吸毒 毛泽东
     */
    public TireTreeNode treeInit(File file) throws Exception {
        this.TireTreeNodeRoot.setEnd(false);
        HashMap<Character,TireTreeNode> hashMapRoot = new HashMap<>();
        this.TireTreeNodeRoot.setNext(hashMapRoot);

        //根节点为空
        char[] chars = inputStreamToCharacter(new FileReader(file));

        Queue<Character> queue = new ArrayDeque<>();

        for (char ch:
        chars){
            //假设文件开头必为字符
            if (ch == ' ' || ch == '\n') {
                //新建一个节点
                TireTreeNode TireTreeNode;
                TireTreeNode TireTreeNodeLast = null;

                Character chTemp;
                chTemp = queue.peek();
                queue.remove();



                HashMap<Character,TireTreeNode> hashMap = null;

                if (!(this.TireTreeNodeRoot.getNext().containsKey(chTemp))) {
                    TireTreeNode = new TireTreeNode();

                    this.TireTreeNodeRoot.getNext().put(chTemp,TireTreeNode);


                    //可以根据字符去重
                    hashMap = new HashMap<>();
                    //先将HashMap存入，后续提供HashMap维护子节点
                    TireTreeNode.setNext(hashMap);
                    TireTreeNode.setWord(chTemp);
                    TireTreeNode.setEnd(false);

                }

                //已经存在，那么只需要获取该节点的Map并维护
                else {

                     TireTreeNode = this.TireTreeNodeRoot.getNext().get(chTemp);
                     //已存在节点，从节点中获取Map即可
                     hashMap = TireTreeNode.getNext();


                }

                TireTreeNodeLast = TireTreeNode;





                Character character;

                while (!queue.isEmpty()) {

                    character = queue.peek();
                    queue.remove();


                    //根节点维护的map与其子节点将要插入的值相同
                    if (hashMap != null && hashMap.containsKey(character)) {
                        //上一个节点为结尾
//                        TireTreeNode = TireTreeNodeLast;
                        //尽快回收
//                        TireTreeNodeLast = null;
//                        System.gc();
                        //每次移动当前指针时，先记录上一个指针
                        TireTreeNodeLast = TireTreeNode;

                        TireTreeNode = hashMap.get(character);

                        hashMap = TireTreeNode.getNext();

                        continue;
                    }

                    //每次移动当前指针时，先记录上一个指针
                    TireTreeNodeLast = TireTreeNode;

                    //初始化下一个节点
                    TireTreeNode = new TireTreeNode();

//                    TireTreeNode TireTreeNodeT = new TireTreeNode();

                    if (TireTreeNodeLast.getNext() == null) {
                        TireTreeNodeLast.setNext(new HashMap<Character,TireTreeNode>());
                    }

                    TireTreeNodeLast.getNext().put(character,TireTreeNode);
//                    TireTreeNode.getNext().put(character,TireTreeNodeT);


                    TireTreeNode.setWord(character);
                    TireTreeNode.setEnd(false);


                    //方便修改相对于下一次循环的上一个节点
//                    TireTreeNodeLast = TireTreeNode;





//                    TireTreeNode.setEnd(true);


                }
                TireTreeNode.setEnd(true);

                TireTreeNode = TireTreeNodeLast;

                if (TireTreeNode == null) {
                    throw new NullPointerException("TireTreeNode is Null!");
                }




                continue;
            }

            queue.add(ch);
        }


        return this.TireTreeNodeRoot;

    }


    /**
     *
     * @param in
     * @return char[]
     * @throws Exception
     * 传入的IO流在内部会被关闭
     */

    public char[] inputStreamToCharacter(FileReader in) throws Exception {
        char[] chars;
        int ch;
        List<Integer> arrayList = new ArrayList<>();

        CharBuffer charBuffer;

        if (in == null) {
            in.close();
            throw new IOException("传入的FileReader为空");
        }

        //先利用Arraylist的自动扩容去存放文件内容
        while ( ( ch = in.read() ) != -1){
            if (ch != '\r'){
                arrayList.add(ch);
            }

        }
//        arrayList.add(-1);

        if (arrayList.size() < 0) {
            in.close();
            throw new Exception("读取的文件内容为空");
        }

        //读取ArrayList的容量，使用Buffer动态初始化大小
        charBuffer = CharBuffer.allocate(arrayList.size());

        for (int ch1:
             arrayList) {

            charBuffer.put((char)ch1);

        }


        in.close();

        return charBuffer.array();


    }

    public TireTreeNode getTireTreeNodeRoot() {
        return TireTreeNodeRoot;
    }

//    public SensitiveWordsFilter setTireTreeNodeRoot(TireTreeNode tireTreeNodeRoot) {
//        TireTreeNodeRoot = tireTreeNodeRoot;
//        return this;
//    }
}
