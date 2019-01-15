package com.tcl.srm.control.common;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-12-26T21:52:13.560+08:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://common.control.srm.tcl.com/", name = "EsbService")
@XmlSeeAlso({ObjectFactory.class})
public interface EsbService {

    @WebMethod
    @RequestWrapper(localName = "dataExchange", targetNamespace = "http://common.control.srm.tcl.com/", className = "com.tcl.srm.control.common.DataExchange")
    @ResponseWrapper(localName = "dataExchangeResponse", targetNamespace = "http://common.control.srm.tcl.com/", className = "com.tcl.srm.control.common.DataExchangeResponse")
    @WebResult(name = "return", targetNamespace = "")
    public String dataExchange(
            @WebParam(name = "jsonObject", targetNamespace = "")
                    String jsonObject
    );
}
