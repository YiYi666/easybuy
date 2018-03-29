package top.headtop.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import top.headtop.mapper.SolrImport;
import top.headtop.pojo.SolrDB;

public class ItemChangedListener implements MessageListener {

	@Autowired
	private SolrImport solrImport;
	@Autowired
	private HttpSolrServer httpSolrServer;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			Long itemId = new Long(text);
			SolrDB solrDB = solrImport.importDBtoSolrSingle(itemId);
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", solrDB.getId());
			doc.addField("item_title", solrDB.getTitle());
			doc.addField("item_sell_point", solrDB.getSellPoint());
			doc.addField("item_price", solrDB.getPrice());
			doc.addField("item_image", solrDB.getImage());
			doc.addField("item_category_name", solrDB.getCatName());
			doc.addField("item_desc", solrDB.getItemDesc());
			httpSolrServer.add(doc);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
