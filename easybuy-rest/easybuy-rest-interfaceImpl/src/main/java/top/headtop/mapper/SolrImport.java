package top.headtop.mapper;

import java.util.List;

import top.headtop.pojo.SolrDB;

public interface SolrImport {
	public List<SolrDB> importDBtoSolr();
	public SolrDB importDBtoSolrSingle(long itemId);
}
