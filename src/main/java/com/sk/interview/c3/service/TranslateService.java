package com.sk.interview.c3.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TranslateService {

	private final String GOOGLE_KEY = "api_key";

	public List<String> translate(List<String> wordList) {

		try {
			Translate t = new Translate.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					GsonFactory.getDefaultInstance(), null)
							// Set your application name
							.setApplicationName("Stackoverflow-Example").build();
			Translate.Translations.List list = t.new Translations().list(wordList, "en");

			list.setKey(GOOGLE_KEY);
			TranslationsListResponse response = list.execute();
			
			List<String> responseList = new ArrayList<String>();
			for (TranslationsResource translationsResource : response.getTranslations()) {
				responseList.add(translationsResource.getTranslatedText());
			}
			
			return responseList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("google apis call error: ", e);
			throw new RuntimeException("not found exception");
		}
	
	}

}
