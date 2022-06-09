package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchCriteria;
import com.jsp.dao.PdsDAO;
import com.jsp.dto.PdsVO;

public class PdsDAOBeanImpl implements PdsDAOBean {

	private SqlSession session;
	private PdsDAO pdsDAO;	
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	public void setPdsDAO(PdsDAO pdsDAO) {
		this.pdsDAO = pdsDAO;
	}
	
	@Override
	public List<PdsVO> selectPdsCriteria(SearchCriteria cri) throws SQLException {
		return pdsDAO.selectPdsCriteria(session, cri);
	}

	@Override
	public int selectPdsCriteriaTotalCount(SearchCriteria cri) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PdsVO selectPdsByPno(int pno) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPds(PdsVO pds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePds(PdsVO pds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePds(int pno) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void increaseViewCnt(int pno) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSeqNextValue() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
