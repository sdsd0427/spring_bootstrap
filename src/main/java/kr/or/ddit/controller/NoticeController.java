package kr.or.ddit.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/main")
	public void main() {}
	
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws Exception{
		String url = "notice/list";
		
		Map<String, Object> dataMap = null;
		
		try {
			
			dataMap = noticeService.getNoticeList(cri);
			
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mnv.addObject("dataMap", dataMap);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value = "/registForm",  method = RequestMethod.GET)
	public String registForm() {
		String url = "notice/regist";
		return url;
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(NoticeVO notice, RedirectAttributes rttr) throws Exception{
		String url = "notice/detail";
		
		noticeService.regist(notice);
		
		rttr.addFlashAttribute("from", "regist");
		rttr.addAttribute("nno", notice.getNno());
		
		return url;
	}
	
	
	
	
	
}
