package top.headtop.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SolrDB> items;
	private long totalNum;
	private long totalPages;
	private long page;
	public List<SolrDB> getItems() {
		return items;
	}
	public void setItems(List<SolrDB> items) {
		this.items = items;
	}
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	
}
