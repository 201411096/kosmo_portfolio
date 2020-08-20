package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.ProductreviewDAOImpl;
import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.ProductreviewVO;

@Service("productreviewService")
public class ProductreviewServiceImpl implements ProductreviewService{
	@Autowired
	ProductreviewDAOImpl ProductreviewDAO;
	@Override
	public ProductreviewVO reviewCheck(Map searchMap) {
		ProductreviewVO result = ProductreviewDAO.reviewCheck(searchMap);
		return result;
	}
	@Override
	public int insertReview(Map searchMap) {
		int result = ProductreviewDAO.insertReview(searchMap);
		return result;
	}
	@Override
	public List<ProductreviewVO> selectListReview(Map searchMap) {
		List<ProductreviewVO> result = ProductreviewDAO.selectListReview(searchMap);
		return result;
	}
	@Override
	public int deleteReview(Map searchMap) {
		int result = ProductreviewDAO.deleteReview(searchMap);
		return result;
	}
	@Override
	public String selectAReview(Map searchMap) {
		String result = ProductreviewDAO.selectAReview(searchMap);
		return result;
	}
	@Override
	public List<ProductreviewVO> selectReviewByTagWithPaging(Map searchMap) {
		List<ProductreviewVO> result = ProductreviewDAO.selectReviewByTagWithPaging(searchMap);
		return result;
	}
	@Override
	public int selectBuy(Map searchMap) {
		int result = ProductreviewDAO.selectBuy(searchMap);
		return result;
	}
	
}
