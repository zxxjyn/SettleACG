
package com.tcl.srm.control.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tcl.srm.control.common package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataExchange_QNAME = new QName("http://common.control.srm.tcl.com/", "dataExchange");
    private final static QName _DataExchangeResponse_QNAME = new QName("http://common.control.srm.tcl.com/", "dataExchangeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tcl.srm.control.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataExchange }
     * 
     */
    public DataExchange createDataExchange() {
        return new DataExchange();
    }

    /**
     * Create an instance of {@link DataExchangeResponse }
     * 
     */
    public DataExchangeResponse createDataExchangeResponse() {
        return new DataExchangeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataExchange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.control.srm.tcl.com/", name = "dataExchange")
    public JAXBElement<DataExchange> createDataExchange(DataExchange value) {
        return new JAXBElement<DataExchange>(_DataExchange_QNAME, DataExchange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataExchangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.control.srm.tcl.com/", name = "dataExchangeResponse")
    public JAXBElement<DataExchangeResponse> createDataExchangeResponse(DataExchangeResponse value) {
        return new JAXBElement<DataExchangeResponse>(_DataExchangeResponse_QNAME, DataExchangeResponse.class, null, value);
    }

}
