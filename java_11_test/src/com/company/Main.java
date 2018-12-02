package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        var a = 1;
        var list = new ArrayList<>();
        for (var i=0; i<10;i++){
            list.add("haha"+i);
        }
        list.stream().forEach((var x)-> {
            if(x.toString().length()>5){
                System.out.println(x.toString().length());
            }

        });
    }
}
