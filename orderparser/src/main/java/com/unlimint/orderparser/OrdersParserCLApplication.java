package com.unlimint.orderparser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.orderparser.entity.Order;
import com.unlimint.orderparser.parser.InputParser;
import com.unlimint.orderparser.reader.InputReader;

@Component
public class OrdersParserCLApplication implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {		
		try {
			Map<String,List<String>> map = getInputReader().readFileContent(args);
			List<Order> orders = getInputParser().orderParser(map);
			
			for(Order order: orders) {
				ObjectMapper mapper = new ObjectMapper();
				String orderData = mapper.writeValueAsString(order);
				System.out.println(orderData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public InputReader getInputReader() {
		return new InputReader();
	}
	
	@Bean
	public InputParser getInputParser() {
		return new InputParser();
	}

}
