package com.mycompany.domain;

public class ProductreviewVO {
	private int productreviewId;
	private String productreviewContent;
	private int productreviewScore;
	private String memberId;
	private int productId;
	private String productreviewUploadtime;
	
	public String getProductreviewUploadtime() {
		return productreviewUploadtime;
	}
	public void setProductreviewUploadtime(String productreviewUploadtime) {
		this.productreviewUploadtime = productreviewUploadtime;
	}
	public int getProductreviewId() {
		return productreviewId;
	}
	public void setProductreviewId(int productreviewId) {
		this.productreviewId = productreviewId;
	}
	public String getProductreviewContent() {
		return productreviewContent;
	}
	public void setProductreviewContent(String productreviewContent) {
		this.productreviewContent = productreviewContent;
	}
	public int getProductreviewScore() {
		return productreviewScore;
	}
	public void setProductreviewScore(int productreviewScore) {
		this.productreviewScore = productreviewScore;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
