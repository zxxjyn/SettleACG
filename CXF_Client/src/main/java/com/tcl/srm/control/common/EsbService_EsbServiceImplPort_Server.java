
package com.tcl.srm.control.common;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-12-26T21:52:13.566+08:00
 * Generated source version: 3.2.7
 *
 */

public class EsbService_EsbServiceImplPort_Server{

    protected EsbService_EsbServiceImplPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new EsbServiceImplPortImpl();
        String address = "http://10.117.159.28:8080/tms/services/esbService";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws Exception {
        new EsbService_EsbServiceImplPort_Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
