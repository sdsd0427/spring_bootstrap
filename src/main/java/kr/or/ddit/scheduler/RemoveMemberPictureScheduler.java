package kr.or.ddit.scheduler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.dao.MemberDAOBean;

public class RemoveMemberPictureScheduler {

	private MemberDAOBean memberDAOBean;
	public void setMemberDAOBean(MemberDAOBean memberDAOBean) {
		this.memberDAOBean = memberDAOBean;
	}

	private String PicturePath;
	public void setPicturePath(String picturePath) {
		PicturePath = picturePath;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(RemoveMemberPictureScheduler.class);
	
	public void removePicture() throws Exception{
		File dir = new File(PicturePath);
		File[] files = dir.listFiles();
		
		if(files != null)
			for(File file : files) {
				if(memberDAOBean.selectMemberByPicture(file.getName()) == null) {
					file.delete();
					logger.info("delete file : " + file.getName()); 
				}
			}
	}
}
