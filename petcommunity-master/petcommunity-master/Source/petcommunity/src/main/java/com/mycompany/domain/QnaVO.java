package com.mycompany.domain;

public class QnaVO {
	
	private int questionboardId;
	private int questionboardGroupId;
	private String questionboardTitle;
	private String questionboardContent;
	private String questionboardUploadtime;
	private String questionboardReadcount;
	private String memberId;
	private int questionboardAnswerflag;
	
	
	public int getQuestionboardAnswerflag() {
		return questionboardAnswerflag;
	}
	public void setQuestionboardAnswerflag(int questionboardAnswerflag) {
		this.questionboardAnswerflag = questionboardAnswerflag;
	}
	public int getQuestionboardId() {
		return questionboardId;
	}
	public void setQuestionboardId(int questionboardId) {
		this.questionboardId = questionboardId;
	}
	public int getQuestionboardGroupId() {
		return questionboardGroupId;
	}
	public void setQuestionboardGroupId(int questionboardGroupId) {
		this.questionboardGroupId = questionboardGroupId;
	}
	public String getQuestionboardTitle() {
		return questionboardTitle;
	}
	public void setQuestionboardTitle(String questionboardTitle) {
		this.questionboardTitle = questionboardTitle;
	}
	public String getQuestionboardContent() {
		return questionboardContent;
	}
	public void setQuestionboardContent(String questionboardContent) {
		this.questionboardContent = questionboardContent;
	}
	public String getQuestionboardUploadtime() {
		return questionboardUploadtime;
	}
	public void setQuestionboardUploadtime(String questionboardUploadtime) {
		this.questionboardUploadtime = questionboardUploadtime;
	}
	public String getQuestionboardReadcount() {
		return questionboardReadcount;
	}
	public void setQuestionboardReadcount(String questionboardReadcount) {
		this.questionboardReadcount = questionboardReadcount;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
		
	
}
