package com.wine.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wine.demo.repository.EventBoardRepository;
import com.wine.demo.repository.FreeBoardRepository;
import com.wine.demo.repository.JobBoardRepository;
import com.wine.demo.repository.ProducerBoardRepository;
import com.wine.demo.repository.SearchRepository;
import com.wine.demo.repository.ShopBoardRepository;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	

	 @Autowired
	    private SearchRepository searchRepository;
	    
	    @Autowired
	    private FreeBoardRepository freeboardRepository;
	    
	    @Autowired
	    private EventBoardRepository eventboardRepository;
	    
	    @Autowired
	    private ProducerBoardRepository producerRepository;
	    
	    @Autowired
	    private JobBoardRepository jobRepository;
	    
	    @Autowired
	    private ShopBoardRepository shopRepository;


    @GetMapping("/")
    public String main(Model model) {
    	
    	long wineCount = searchRepository.count();
        long communityCount = freeboardRepository.count() + eventboardRepository.count();
        long partnersCount = producerRepository.count() + jobRepository.count() + shopRepository.count();
       
        
        model.addAttribute("wineCount", wineCount);
        model.addAttribute("communityCount", communityCount);
        model.addAttribute("partnersCount", partnersCount);
    	
    	
    	
    	
        return "index";
    }
}
