package com.example.springmvc.util;

import java.util.Map;

public class SensitiveWordsFilter {
//    Map<String,TreeNode> leafs;

    //前缀树数据结构
    class TreeNode {

        String word;
        //后继(多个)
        Map<String,TreeNode> next;
        //前驱(单个)
        TreeNode before;
        //结尾
        boolean end;

    }









}
