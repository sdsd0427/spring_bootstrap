package kr.or.ddit.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	private PdsService pdsService;
	
	@RequestMapping("/main")
	public void main() throws Exception {}
	
	@RequestMapping("/list")
	public String list(SearchCriteria cri, Model model) throws Exception{
		String url = "pds/list";
		
		Map<String, Object> dataMap = pdsService.getList(cri);
		
		model.addAttribute("dataMap", dataMap);
		
		return url;
	}
	
	@RequestMapping("registForm")
	public String registForm() {
		String url = "pds/regist";
		return url;
	}
	
	final private int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
	final private int MAX_FILE_SIZE = 1024 * 1024 * 40; //40MB
	final private int MAX_REQUEST_SIZE = 1024 *1024 * 200; //200MB
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(PdsVO pds, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/pds/list";
		
		pds.setTitle((String) request.getAttribute("XSStitle"));
		
		pdsService.regist(pds);
		
		rttr.addFlashAttribute("from", "regist");
		rttr.addFlashAttribute("pds", pds);
		
		return url;
	}
}



















