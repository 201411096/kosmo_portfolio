package com.mycompany.domain;

public class CommunityVO {
	
	private String communityboardId;
	private String cityName;
	private String province;
	private String communityboardLocation;

	private String communityboardTitle;
	private String communityboardContent;
	private String communityboardUploadtime;
	private String communityboardReadcount;
	private String communityboardRecommend;
	private String memberId;
	private String keyWord;//검색어
	private String searchType;//검색타입
	private String commentCount; //댓글개수 가져오기
	//paging변수들
	private int startList; //페이징이 시작하는 페이지
	private int pageSize=10; //한 페이지에 보이는 글의 수
	private int lastList; 
    private int prevPage;//이전페이지
    private int nextPage;//다음페이지
	

	public int getStartList() {
		return startList;
	}
	public void setStartList(int startList) {
		this.startList = startList;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getLastList() {
		return lastList;
	}
	public void setLastList(int lastList) {
		this.lastList = lastList;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getCommunityboardId() {
		return communityboardId;
	}
	public void setCommunityboardId(String communityboardId) {
		this.communityboardId = communityboardId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCommunityboardLocation() {
		return communityboardLocation;
	}
	public void setCommunityboardLocation(String communityboardLocation) {
		this.communityboardLocation = communityboardLocation;
	}
	public String getCommunityboardTitle() {
		return communityboardTitle;
	}
	public void setCommunityboardTitle(String communityboardTitle) {
		this.communityboardTitle = communityboardTitle;
	}
	public String getCommunityboardContent() {
		return communityboardContent;
	}
	public void setCommunityboardContent(String communityboardContent) {
		this.communityboardContent = communityboardContent;
	}

	public String getCommunityboardRecommend() {
		return communityboardRecommend;
	}
	public void setCommunityboardRecommend(String communityboardRecommend) {
		this.communityboardRecommend = communityboardRecommend;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommunityboardUploadtime() {
		return communityboardUploadtime;
	}
	public void setCommunityboardUploadtime(String communityboardUploadtime) {
		this.communityboardUploadtime = communityboardUploadtime;
	}
	public String getCommunityboardReadcount() {
		return communityboardReadcount;
	}
	public void setCommunityboardReadcount(String communityboardReadcount) {
		this.communityboardReadcount = communityboardReadcount;
	}


}
