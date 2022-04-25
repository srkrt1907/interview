package com.sk.interview.c3.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.sk.interview.c3.model.WordModel;

@Service
public class C3Service {
	
	
	private FileReadService fileReadService;
	
	private ThesaurusService thesaurusService;
	
	private TranslateService translateService;

	public C3Service(FileReadService fileReadService, ThesaurusService thesaurusService,
			TranslateService translateService) {
		super();
		this.fileReadService = fileReadService;
		this.thesaurusService = thesaurusService;
		this.translateService = translateService;
	}
	
	@PostConstruct
	public void process() {
			
		List<WordModel> wordModels = fileReadService.readFile();
		
		for (WordModel wordModel : wordModels) {
			
			List<String> wordList = wordModel.getWordList();
			List<String> translatedList =  translateService.translate(wordList);
			List<String> synmList = thesaurusService.getSynText(translatedList.get(0));
			
			System.out.println(translatedList);
			System.out.println(synmList);	
			
			List<String> newList = new ArrayList<>();
			for (int i = 0 ; i < translatedList.size() ; i++) {			
				if(synmList.contains(translatedList.get(i))) {
					newList.add(wordList.get(i));
				}						
			}
			newList.add(translatedList.get(0));
			System.out.println(newList);			
			wordModel.setWordList(newList);
		}	
		
		fileReadService.writeFile(wordModels);
	}
	
	

}
