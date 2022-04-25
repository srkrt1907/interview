package com.sk.interview.c2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
		int arrCount = Integer.parseInt(bufferedReader.readLine().trim());
		List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split("")).map(Integer::parseInt).collect(toList());
		int result = Result.uniqueWolfs(arr);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();
		bufferedReader.close();
		bufferedWriter.close();
	}
}