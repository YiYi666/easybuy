package top.headtop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.headtop.dao.SolrDao;
import top.headtop.pojo.SearchResult;
import top.headtop.pojo.SolrDB;

@Component
public class SolrDaoImpl implements SolrDao {
	@Autowired
	private HttpSolrServer httpSolrServer;
	@Override
	public SearchResult excuteQuery(SolrQuery solrQuery) {
		SearchResult searchResult = new SearchResult();
		try {
			QueryResponse response = httpSolrServer.query(solrQuery);
			
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			
			SolrDocumentList solrDocumentList = response.getResults();
			searchResult.setTotalNum(solrDocumentList.getNumFound());
			List<SolrDB> list = new ArrayList<>();
			for (SolrDocument solrDocument : solrDocumentList) {
				SolrDB solrDB = new SolrDB();
				solrDB.setId((String)solrDocument.get("id"));
				solrDB.setCatName((String)solrDocument.get("item_category_name"));
				solrDB.setSellPoint((String)solrDocument.get("item_sell_point"));
				solrDB.setPrice((long)solrDocument.get("item_price"));
				solrDB.setImage((String)solrDocument.get("item_image"));
				solrDB.setItemDesc((String)solrDocument.get("item_desc"));
				
				List<String> highlightingList = highlighting.get((String)solrDocument.get("id")).get("item_title");
				if(highlightingList!=null&&highlightingList.size()>0){
					String item_title = highlightingList.get(0);
					solrDB.setTitle(item_title);
				}else {
					solrDB.setTitle((String)solrDocument.get("item_title"));
				}
				list.add(solrDB);
			}
			searchResult.setItems(list);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return searchResult;
	}

}
