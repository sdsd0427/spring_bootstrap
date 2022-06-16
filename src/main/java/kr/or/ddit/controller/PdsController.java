package kr.or.ddit.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.SearchCriteria;
import com.jsp.controller.FileDownloadResolver;
import com.jsp.controller.MakeFileName;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

import kr.or.ddit.command.PdsModifyCommand;
import kr.or.ddit.command.PdsRegistCommand;

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
	
	@Resource(name = "fileUploadPath")
	private String fileUploadPath;
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String regist(PdsRegistCommand registReq, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/pds/list";
		
		//파일저장
		String savePath = this.fileUploadPath;
		List<AttachVO> attachList = GetAttachesByMultipartFileAdapter.save(registReq.getUploadFile(), savePath);
		
		//db
		PdsVO pds = registReq.toPdsVO();
		pds.setTitle((String) request.getAttribute("XSStitle"));
		pds.setAttachList(attachList);
		pdsService.regist(pds);
		
		//output
		rttr.addFlashAttribute("from", "regist");
		return url;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(int pno, @RequestParam(defaultValue = "") String from, ModelAndView mnv) throws Exception{
		String url = "pds/detail";
		
		PdsVO pds = null;
		
		if(!from.equals("list")) {
			pds = pdsService.getPds(pno);
		} else {
			pds = pdsService.read(pno);
			url = "redirect:/pds/detail.do?pno=" + pno;
		}
		
		//파일명 재정의
		if(pds != null) {
			List<AttachVO> attachList = pds.getAttachList();
			if(attachList != null) {
				for(AttachVO attach : attachList) {
					String fileName = attach.getFileName().split("\\$\\$")[1];
					attach.setFileName(fileName);
				}
			}
			
		}
		
		mnv.addObject("pds", pds);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping("/getFile")
	public String getFile(int ano, Model model) throws Exception{
		String url ="downloadFile";
		
		AttachVO attach = pdsService.getAttachByAno(ano);
		
		model.addAttribute("savedPath", attach.getUploadPath());
		model.addAttribute("fileName", attach.getFileName());
		
		return url;
	}
	
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int pno, ModelAndView mnv) throws Exception{
		String url = "pds/modify";
		
		PdsVO pds = pdsService.getPds(pno);
		//파일명 재정의
		if(pds != null) {
			List<AttachVO> attachList = pds.getAttachList();
			if(attachList != null) {
				for(AttachVO attach : attachList) {
					String fileName = attach.getFileName().split("\\$\\$")[1];
					attach.setFileName(fileName);
				}
			}
			
		}
		
		mnv.addObject("pds", pds);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String modifyPost(PdsModifyCommand modifyReq, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/pds/detail.do";
		//이전파일 삭제
		if(modifyReq.getDeleteFile() != null && modifyReq.getDeleteFile().length > 0) {
			for(String anoStr : modifyReq.getDeleteFile()) {
				int ano = Integer.parseInt(anoStr);
				AttachVO attach = pdsService.getAttachByAno(ano);
				
				File deleteFile = new File(attach.getUploadPath(), attach.getFileName());
				
				if(deleteFile.exists()) {
					deleteFile.delete();
				}
				pdsService.removeAttachByAno(ano);
			}
		}
		
		//추가된 파일 저장
		List<AttachVO> attachList = GetAttachesByMultipartFileAdapter.save(modifyReq.getUploadFile(), fileUploadPath);
		
		PdsVO pds = modifyReq.toPdsVO();
		pds.setTitle((String) request.getAttribute("XSStitle"));
		pds.setAttachList(attachList);
		
		pdsService.modify(pds);
		
		rttr.addAttribute("pno", pds.getPno());
		rttr.addFlashAttribute("from", "modify");
		
		return url;
	}
	
	@RequestMapping("/remove")
	public String remove(int pno, RedirectAttributes rttr) throws Exception{
		String url = "redirect:/pds/detail.do";
		
		List<AttachVO> attachList = pdsService.getPds(pno).getAttachList();
		if(attachList != null) {
			for(AttachVO attach : attachList) {
				File file = new File(attach.getUploadPath(), attach.getFileName());
				
				if(file.exists()) {
					file.delete();
				}
			}
		}
		
		pdsService.remove(pno);
		
		rttr.addAttribute("pno", pno);
		rttr.addFlashAttribute("from", "remove");
		
		return url;
	}
}



















