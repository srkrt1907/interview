package com.sk.interview.c2;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class Result {
	public static int uniqueWolfs(List<Integer> arr) throws Exception {
		
		if(arr.size() < 5 && arr.size() > 200000) {
			throw new Exception("list size must be between 5 and 200000");
		}
		
		Map<Integer, Long> map = arr.stream()
			    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
		
		Integer key = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
		return key;
		
	}
}

