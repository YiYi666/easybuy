package top.headtop.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.headtop.dao.SolrDao;
import top.headtop.mapper.SolrImport;
import top.headtop.pojo.SearchResult;
import top.headtop.pojo.SolrDB;
import top.headtop.service.SolrService;

@Service
public class SolrServiceImpl implements SolrService {

	@Autowired
	private HttpSolrServer httpSolrServer;
	@Autowired
	private SolrImport solrImport;
	
	@Autowired
	private SolrDao solrDao;

	@Override
	public void importDBtoSolr() {
		List<SolrDB> list = solrImport.importDBtoSolr();
		try {
			for (SolrDB solrDB : list) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", solrDB.getId());
				doc.addField("item_title", solrDB.getTitle());
				doc.addField("item_sell_point", solrDB.getSellPoint());
				doc.addField("item_price", solrDB.getPrice());
				doc.addField("item_image", solrDB.getImage());
				doc.addField("item_category_name", solrDB.getCatName());
				doc.addField("item_desc", solrDB.getItemDesc());
				httpSolrServer.add(doc);
			}
			httpSolrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SearchResult restSearch(String keyword, int page, int rows) {
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyword);
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		solrQuery.set("df", "item_keywords");
		
		//开启高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		
		//在solr中查询
		SearchResult searchResult = solrDao.excuteQuery(solrQuery);
		long totalNum = searchResult.getTotalNum();
		long totalPage = totalNum/rows;
		if(totalNum%rows>0){
			totalPage++;
		}
		searchResult.setTotalPages(totalPage);
		return searchResult;
	}

}

























