package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.ProductreviewVO;

public interface ProductreviewDAO {
	public ProductreviewVO reviewCheck(Map searchMap);
	public int insertReview(Map searchMap);
	public List<ProductreviewVO> selectListReview(Map searchMap);
	public int deleteReview(Map searchMap);
	public String selectAReview(Map searchMap);
	public List<ProductreviewVO> selectReviewByTagWithPaging(Map searchMap);
	public int selectBuy(Map searchMap);
}
