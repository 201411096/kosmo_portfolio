package com.mycompany.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.domain.CommentVO;
import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;


public interface CommunityService {
	public int writeIntoBoard(CommunityVO vo);
	public List<CommunityVO> getBoardList();
	public List<CommunityVO> communityBoardListByPaging(CommunityVO vo);
	public CommunityVO getBoardContent(CommunityVO vo);
	public List<CommunityVO> getBoardListBySearch(CommunityVO vo);
	public List<CommunityVO> getBoardListBySearchWithPaging(Map map);
	public List<CommunityVO> getBoardListByLocation(CommunityVO vo);
	public List<CommunityVO> getBoardListByLocationWithPaging(Map map);
	public List<CommunityVO> getBoardListByReadCount();
	public List<CommunityVO> getBoardListByReadCountWithPaging(Map map);
	public List<CommunityVO> getBoardListByRecommendWithPaging(Map map);
	public List<CommunityVO> getBoardListByRecommend();
	public void addReadCount(CommunityVO vo);
	public void writeComment(CommentVO vo);
	public List<CommentVO> getCommentContent(CommentVO vo);
	public void deleteOnCommunityBoard(CommunityVO vo);
	public void deleteBoardComment(CommentVO vo);
	public void modifyBoardContent(CommunityVO vo);
	public void commentDelete(CommentVO vo);
	public void likeContent(CommunityVO vo);
	public void dislikeContent(CommunityVO vo);
	public List<CommunityVO> communityBoardListBySearchPaging(CommunityVO vo);
	public List<CommunityVO> getBoardRank();
	public List<LostBoardVO> getLostRank();
	public List<FindBoardVO> getFindRank();
}
