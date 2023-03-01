package net.schrage.javasoapclient;

import net.schrage.soap.CustomerOrdersWsImplService;
import net.schrage.soap.CustomerOrdersPortType;
import net.schrage.soap.GetOrdersRequest;
import net.schrage.soap.GetOrdersResponse;
import net.schrage.soap.Order;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomerOrderWsClient {

  public static void main(String[] args) throws MalformedURLException {
    CustomerOrdersWsImplService service = new CustomerOrdersWsImplService(
        new URL("http://localhost:8080/hellows/customerordersservice?wsdl"));

    CustomerOrdersPortType customerOrdersPortType = service.getCustomerOrdersWsImplPort();

    GetOrdersRequest request = new GetOrdersRequest();
    request.setCustomerId(BigInteger.valueOf(1));

    GetOrdersResponse response = customerOrdersPortType.getOrders(request);
    List<Order> orders = response.getOrder();
    System.out.println("Number of orders of the customer are " + orders.size());

  }

}
