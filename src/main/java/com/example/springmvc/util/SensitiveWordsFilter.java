package com.example.springmvc.util;

import com.example.springmvc.demo.A;
import com.sun.source.tree.Tree;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class SensitiveWordsFilter {
//    Map<String,TreeNode> leafs;
    private static final String replaceWith = "*";

    TreeNode treeNodeRoot = new TreeNode();

    /**
     * 前缀树
     * 只有根节点为空
     * 节点的字符不能重复
     */

     class TreeNode {

//        String word;
        Character word;
        //后继(多个)
//        HashSet<TreeNode> next;

        //不使用Hash的原因：在生成树时，无法根据字符去重
        //使用HashMap的原因：可以根据key去重
        HashMap<Character,TreeNode> next;
        //结尾
        boolean end;

        public Character getWord() {
            return word;
        }

        public TreeNode setWord(Character word) {
            this.word = word;
            return this;
        }

//        public HashSet<TreeNode> getNext() {
//            return next;
//        }
//
//        public TreeNode setNext(HashSet<TreeNode> next) {
//            this.next = next;
//            return this;
//        }


        public HashMap<Character, TreeNode> getNext() {
            return next;
        }

        public TreeNode setNext(HashMap<Character, TreeNode> next) {
            this.next = next;
            return this;
        }

        public boolean isEnd() {
            return end;
        }

        public TreeNode setEnd(boolean end) {
            this.end = end;
            return this;
        }
    }


    /**
     *
     * @param file
     * @return TreeNode
     * 建议将要屏蔽的文字按照示例存放到文件中
     * eg: 赌博 吸毒 毛泽东
     */
    public TreeNode treeInit(File file) throws Exception {
        this.treeNodeRoot.setEnd(false);
        HashMap<Character,TreeNode> hashMapRoot = new HashMap<>();
        this.treeNodeRoot.setNext(hashMapRoot);

        //根节点为空
        char[] chars = inputStreamToCharacter(new FileReader(file));

        Queue<Character> queue = new ArrayDeque<>();

        for (char ch:
        chars){

            if (ch == ' ' || ch == '\n') {
                //新建一个节点
                TreeNode treeNode;
                TreeNode treeNodeLast = null;

                Character chTemp;
                chTemp = queue.peek();



                HashMap<Character,TreeNode> hashMap = null;

                if (!(this.treeNodeRoot.getNext().containsKey(chTemp))) {
                    treeNode = new TreeNode();

                    this.treeNodeRoot.getNext().put(chTemp,treeNode);


                    //可以根据字符去重
                    hashMap = new HashMap<>();
                    //先将HashMap存入，后续提供HashMap维护子节点
                    treeNode.setNext(hashMap);
                    treeNode.setWord(chTemp);
                    treeNode.setEnd(false);

                }

                //已经存在，那么只需要获取该节点的Map并维护
                else {

                     treeNode = this.treeNodeRoot.getNext().get(chTemp);
                     //已存在节点，从节点中获取Map即可
                     hashMap = treeNode.getNext();


                }

                treeNodeLast = treeNode;





                Character character;

                while (!queue.isEmpty()) {

                    character = queue.peek();


                    //根节点维护的map与其子节点将要插入的值相同
                    if (hashMap.containsKey(character)) {
                        //上一个节点为结尾
//                        treeNode = treeNodeLast;
                        //尽快回收
//                        treeNodeLast = null;
//                        System.gc();
                        //每次移动当前指针时，先记录上一个指针
                        treeNodeLast = treeNode;

                        treeNode = hashMap.get(character);

                        hashMap = treeNode.getNext();

                        continue;
                    }

                    //每次移动当前指针时，先记录上一个指针
                    treeNodeLast = treeNode;

                    //初始化下一个节点
                    treeNode = new TreeNode();

//                    TreeNode treeNodeT = new TreeNode();

//                    treeNodeLast.getNext().put(character,treeNode);
//                    treeNode.getNext().put(character,treeNodeT);


                    treeNode.setWord(character);
                    treeNode.setEnd(false);

                    //方便修改相对于下一次循环的上一个节点
//                    treeNodeLast = treeNode;





//                    treeNode.setEnd(true);


                }

                treeNode = treeNodeLast;

                if (treeNode != null) {
                    throw new NullPointerException("TreeNode is Null!");
                }

                treeNode.setEnd(true);


                continue;
            }

            queue.add(ch);
        }


        return null;

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
            arrayList.add(ch);
        }
        arrayList.add(-1);

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











}
