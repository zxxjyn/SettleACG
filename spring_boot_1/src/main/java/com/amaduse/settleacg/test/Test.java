package com.amaduse.settleacg.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for(int i=0; i<10;i++){
            list.add("haha"+i);
        }
        list.forEach(System.out::println);
    }
}
