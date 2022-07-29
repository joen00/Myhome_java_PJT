package com.ssafy.rent.model.service;

import java.util.List;

import com.ssafy.rent.model.dao.HomeDao;
import com.ssafy.rent.model.dao.HomeDaoImpl;
import com.ssafy.rent.model.dto.HomeDeal;
import com.ssafy.rent.model.dto.HomePageBean;

public class HomeServiceImpl implements HomeService{
	private HomeDao dao;

	public HomeServiceImpl() {
		 dao = new HomeDaoImpl();
	}
	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 아파트 거래 정보(HomeInfo)를  검색해서 반환.  
	 * @param bean  검색 조건과 검색 단어가 있는 객체
	 * @return 조회한 주택 목록
	 */
	@Override
	public List<HomeDeal> searchAll(HomePageBean  bean){
		return dao.searchAll(bean);
	}
	
	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환. 
	 * @param no	검색할 아파트 식별 번호
	 * @return		아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	@Override
	public HomeDeal search(int no) {
		return dao.search(no);
		// complete code #03
		// null 을 return 하면 안됩니다. Dao Layer 의 적절한 method를 호출하여 Business Logic 을 완성하세요.
		
		
	}
}
