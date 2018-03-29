package top.headtop.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SolrDB implements Serializable {
	private static final long serialVersionUID = 3490605061335638085L;
	private String id;
	private String title;
	private String sellPoint;
	private long price;
	private String image;
	private String catName;
	private String itemDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@JsonIgnore
	public String[] getImages() {
		if (image != null) {
			return image.split(",");
		}
		return null;
	}
}
