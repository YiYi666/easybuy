package top.headtop.service;

import top.headtop.pojo.SearchResult;

public interface SolrService {
	public void importDBtoSolr();
	public SearchResult restSearch(String keyword,int page,int rows);
}
