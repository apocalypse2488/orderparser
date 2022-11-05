package com.unlimint.orderparser.parser;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlimint.orderparser.entity.Order;
import com.unlimint.orderparser.exception.OrderConversionException;

public class InputParser {
	
	public List<Order> orderParser(Map<String,List<String>> orderLinesMap) throws IOException  {
		
		List<Order> orders =new ArrayList<>();
		
		long orderIdSerialCounter = 0;
		for(Map.Entry<String, List<String>> entry : orderLinesMap.entrySet()) {
			
			List<String> orderLines = entry.getValue();
			
			long orderLineCounter = 0;
		
			for (String orderString : orderLines) {
				Order order = null;
				try {
						if(orderString.contains("{\"")){
							order =converJsonStringToOrder(orderString);
						}
						else{
							order =converCSVStringToOrder(orderString);
						}
					} catch (OrderConversionException e) {
						order = new Order();
						order.setOrderId(-1);
						order.setResult(e.getMessage());
					}catch (Exception e) {
						order = new Order();
						order.setOrderId(-1);
						order.setResult(e.getMessage());
					}
				 			
				if(order !=null) {
					order.setId(Long.valueOf(++orderIdSerialCounter));
					order.setFileName(entry.getKey());
					order.setLine(BigInteger.valueOf(Long.valueOf(++orderLineCounter)));
					orders.add(order);
				}					
			}
 			orderLineCounter = 0;
		}
		return orders;
	}
	
	private Order converCSVStringToOrder(String orderString)throws OrderConversionException{
		Order order =null;
		if(orderString != null && !orderString.isEmpty() && !orderString.isBlank()){
			order = new Order(orderString);
			order.setResult("OK");
		}
		return order;
	}

	public Order converJsonStringToOrder(String orderString) throws JsonMappingException, JsonProcessingException,OrderConversionException {
		Order order =null;
		if(orderString != null && !orderString.isEmpty() && !orderString.isBlank()){
			JsonObject a = JsonParser.parseString(orderString).getAsJsonObject();
			order = new Order();
			order.setOrderId(a.get("orderId").getAsLong());
			order.setAmount(a.get("amount").getAsBigDecimal());
			order.setCurrency(a.get("currency").getAsString());
			order.setComment(a.get("comment").getAsString());
			order.setResult("OK");
		}
		return order;	
	}

}
