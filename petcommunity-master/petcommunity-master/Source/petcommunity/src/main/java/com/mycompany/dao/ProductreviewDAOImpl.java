package com.mycompany.dao;

import java.util.List;
import java.util.Map;



import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.ProductreviewVO;

@Repository("productreviewDAO")
public class ProductreviewDAOImpl implements ProductreviewDAO{
	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Override
	public ProductreviewVO reviewCheck(Map searchMap) {
		ProductreviewVO result = mybatis.selectOne("ProductreviewDAO.reviewCheck", searchMap);
		return result;
	}

	@Override
	public int insertReview(Map searchMap) {
		int result = mybatis.insert("ProductreviewDAO.insertReview", searchMap);
		return result;
	}

	@Override
	public List<ProductreviewVO> selectListReview(Map searchMap) {
		List<ProductreviewVO> result = mybatis.selectList("ProductreviewDAO.selectListReview",searchMap);
		return result;
	}

	@Override
	public int deleteReview(Map searchMap) {
		int result = mybatis.delete("ProductreviewDAO.reviewDelete", searchMap);
		return result;
	}

	@Override
	public String selectAReview(Map searchMap) {
		String result = mybatis.selectOne("ProductreviewDAO.selectAReview", searchMap);
		return result;
	}

	@Override
	public List<ProductreviewVO> selectReviewByTagWithPaging(Map searchMap) {
		List<ProductreviewVO> result = mybatis.selectList("ProductreviewDAO.selectReviewByTagWithPaging", searchMap);
		return result;
	}

	@Override
	public int selectBuy(Map searchMap) {
		int result = mybatis.selectOne("ProductreviewDAO.selectBuy", searchMap);
		return result;
	}

}
