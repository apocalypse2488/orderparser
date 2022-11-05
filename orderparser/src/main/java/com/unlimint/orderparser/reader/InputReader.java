package com.unlimint.orderparser.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InputReader {
		
	public Map<String,List<String>> readFileContent(String[] filenames) throws IOException  {
		List<String> filesList = Arrays.asList(filenames);
		Map<String,List<String>> map = new LinkedHashMap<>();
				
		for(String file : filesList) {
			try {
				List<String> stringLines =new ArrayList<>();
				BufferedReader br = new BufferedReader(new FileReader(file));	
				String line;
				while ((line = br.readLine()) != null) 
				{	
					  stringLines.add(line.toString());
				}			
				br.close();
				map.put(file, stringLines);

			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		return map;
	}
}


