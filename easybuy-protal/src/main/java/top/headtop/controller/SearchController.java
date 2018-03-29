package top.headtop.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.headtop.pojo.SearchResult;
import top.headtop.service.SolrService;

@Controller
public class SearchController {
	@Autowired
	private SolrService solrService;

	@RequestMapping("/search")
	public String ProtalSearch(String keyword,Model model,
			@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="30")Integer rows){
//		从数据库将数据导入sole
//		solrService.importDBtoSolr();
		try {
			keyword = new String(keyword.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SearchResult restSearch = solrService.restSearch(keyword, page, rows);
		model.addAttribute("itemList",restSearch.getItems());
		model.addAttribute("totalPages",restSearch.getTotalPages());
		model.addAttribute("page",restSearch.getPage());
		model.addAttribute("keyword",keyword);
		
		return "search";
	}
	
}
