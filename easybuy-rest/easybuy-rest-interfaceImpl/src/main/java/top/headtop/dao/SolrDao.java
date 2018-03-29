package top.headtop.dao;

import org.apache.solr.client.solrj.SolrQuery;

import top.headtop.pojo.SearchResult;

public interface SolrDao {
	public SearchResult excuteQuery(SolrQuery solrQuery);
}
