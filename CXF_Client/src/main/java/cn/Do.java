package cn;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Do {
    public static void main(String[] args) {
        String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><operateType>query </operateType><dbType>sqlserver</dbType><orderBy></orderBy><userId>1</userId><start>0</start><limit>20</limit><processName></processName></request>";
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        String wsUrl = "http://localhost:8080/tms/services/esbService?wsdl";
        String method = "srmPushBusInfoData";
        Client client = dcf.createClient(wsUrl);
        Object[] res = null;
        try {
            res = client.invoke(method, xmlInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
