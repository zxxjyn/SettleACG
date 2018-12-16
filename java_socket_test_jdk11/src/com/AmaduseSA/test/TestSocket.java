package com.AmaduseSA.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestSocket {
    public static void main(String[] args) {
        try {
//            InetAddress addr = InetAddress.getByName("tms.tmt.tcl.com");
            InetAddress addr = InetAddress.getByName("10.126.8.21");


            System.out.println(addr.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
