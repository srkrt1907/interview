package com.sk.interview.c3.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sk.interview.c3.model.ThesaurusResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ThesaurusService {
	
	private WebClient webClient;
	
	public ThesaurusService(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	private final String API_KEY = "api_key";
	private final String URL = "https://www.dictionaryapi.com/api/v3/references/thesaurus/json/{text}?key=" + API_KEY;
	
	public List<String> getSynText(String word) {
			
		String url = URL.replace("{text}", word);
		ResponseEntity<List<ThesaurusResponse>> response = webClient.get()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON).retrieve().toEntityList(ThesaurusResponse.class).block();			
		
		ThesaurusResponse theasurusResponse =  response.getBody().get(0);
		String[][] syns = theasurusResponse.getMeta().getSyns();
		
		List<String> list = new ArrayList<String>();
	    for (String[] array : syns) {
	        list.addAll(Arrays.asList(array));
	    }
	    list.add(word);
	    return list;	
		
	}

}
