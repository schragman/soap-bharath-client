package net.schrage.javasoapclient;

import net.schrage.soap.CustomerOrdersWsImplService;
import net.schrage.soap.CustomerOrdersPortType;
import net.schrage.soap.GetOrdersRequest;
import net.schrage.soap.GetOrdersResponse;
import net.schrage.soap.Order;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.WSConstants;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrderWsClient {

  public static void main(String[] args) throws MalformedURLException {
    CustomerOrdersWsImplService service = new CustomerOrdersWsImplService(
        new URL("http://localhost:8080/hellows/customerordersservice?wsdl"));

    CustomerOrdersPortType customerOrdersPortType = service.getCustomerOrdersWsImplPort();

    /* Adding Security*/
    Client client = ClientProxy.getClient(customerOrdersPortType);
    Endpoint endpoint = client.getEndpoint();
    Map<String, Object> props =new HashMap<>();
    props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
    props.put(WSHandlerConstants.USER, "Michael");
    props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
    props.put(WSHandlerConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());
    WSS4JOutInterceptor wssout = new WSS4JOutInterceptor(props);
    endpoint.getOutInterceptors().add(wssout);
    /*End of WSS4J Security*/

    GetOrdersRequest request = new GetOrdersRequest();
    request.setCustomerId(BigInteger.valueOf(1));

    GetOrdersResponse response = customerOrdersPortType.getOrders(request);
    List<Order> orders = response.getOrder();
    System.out.println("Number of orders of the customer are " + orders.size());

  }

}
