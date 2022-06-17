package kr.or.ddit.controller.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/main")
	public void main() throws Exception {}
	
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws Exception{
		String url = "board/list";
		
		Map<String, Object> dataMap = boardService.getBoardList(cri);
		
		mnv.addObject("dataMap", dataMap);
		mnv.setViewName(url);
		return mnv;
	}
	
	@RequestMapping("/registForm")
	public String registForm() {
		String url = "board/regist";
		return url;
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(BoardVO board, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/board/list";
		
		board.setTitle((String) request.getAttribute("XSStitle"));
		
		boardService.regist(board);
	
		rttr.addFlashAttribute("from", "regist");
		rttr.addFlashAttribute("board", board);
		
		return url;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(int bno, @RequestParam(defaultValue = "") String from, ModelAndView mnv) throws Exception{
		String url = "board/detail";
		
		BoardVO board = null;
		
		if(!from.equals("list")) {
			board = boardService.getBoardForModify(bno);
		} else {
			board = boardService.getBoard(bno);
			url = "redirect:/board/detail.do?bno=" + bno;
		}
		
		mnv.addObject("board", board);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int bno, ModelAndView mnv) throws Exception{
		String url = "board/modify";
		
		BoardVO board = boardService.getBoardForModify(bno);
		
		mnv.addObject("board", board);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO board, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/board/detail.do";
		
		board.setTitle((String) request.getAttribute("XSStitle"));
		
		boardService.modify(board);
		
		rttr.addAttribute("bno", board.getBno());
		rttr.addFlashAttribute("from", "modify");
		
		return url;
	}
	
	@RequestMapping("/remove")
	public String remove(int bno, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/board/detail.do";
		
		boardService.remove(bno);
		
		rttr.addAttribute("bno", bno);
		rttr.addFlashAttribute("from", "remove");
		
		return url;
	}
	
}










