package kr.or.ddit.datasource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jsp.dto.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/context/root-context.xml")
@Transactional
public class TestSqlSession {
	
	@Autowired
	private SqlSession session;	
	
	@Test
	public void testGetMember() throws Exception {
		String testID = "mimi";
		
		MemberVO member = session.selectOne("Member-Mapper.selectMemberById", testID);
		
		Assert.assertNotNull(member);
	}
	
	@Test
	@Rollback
	public void testInsertMember() throws Exception {
		
		MemberVO member = new MemberVO();
		member.setId("tototo");
		member.setPwd("tototo");
		member.setName("name");
		member.setAddress("dd");
		member.setEmail("e");
		member.setPhone("000");
		member.setPicture("dd");
		member.setAuthority("dd");
		
		session.update("Member-Mapper.insertMember", member);
		
		MemberVO result = session.selectOne("Member-Mapper.selectMemberById", member.getId());
		
		Assert.assertNotNull(result);
	}
}
