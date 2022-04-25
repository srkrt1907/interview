package com.sk.interview.c3.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.sk.interview.c3.model.WordModel;


@Component
public class FileReadService {

	public List<WordModel> readFile() {

		List<WordModel> wordList = new ArrayList<>();
		String line = "";
		String splitBy = ";";
		try {
			// parsing a CSV file into BufferedReader class constructor
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("file_path"),StandardCharsets.UTF_8));
			

			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] word = line.split(splitBy); // use comma as separator
				WordModel model = new WordModel();
				model.setWordList(Arrays.asList(word));
				wordList.add(model);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return wordList;
	}

	public void writeFile(List<WordModel> list) {

		File file = new File("file_path");
		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file);

			// create CSVWriter with '|' as separator
			CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

			// create a List which contains String array
			List<String[]> data = new ArrayList<String[]>();
			for (WordModel model : list) {
				data.add(model.getWordList().toArray(new String[0]));
			}			
			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
