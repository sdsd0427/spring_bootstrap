package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.LoginUserLogDAO;
import kr.or.ddit.dto.LoginUserLogVO;

public class LoginUserLogServiceImpl implements LoginUserLogService {

	private LoginUserLogDAO loginUserLogDAO;
	public void setLoginUserLogDAO(LoginUserLogDAO loginUserLogDAO) {
		this.loginUserLogDAO = loginUserLogDAO;
	}
	
	@Override
	public void write(List<LoginUserLogVO> logList) throws Exception {
		loginUserLogDAO.deleteLoginUserLog();
		
		for(LoginUserLogVO logVO : logList) {
			loginUserLogDAO.insertLoginUserLog(logVO);
		}
	}

}
