package com.example.food.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.food.entity.Board;
import com.example.food.entity.User;
import com.example.food.service.BoardService;
import com.example.food.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashBoard")
public class DashBoardController {
	@Autowired private BoardService boardService;
	@Autowired private UserService uSvc;
	
	@GetMapping("/status")
	public String Status(Model model, HttpSession session) {
		if ("admin".equals(session.getAttribute("sessUid"))) {
			String likeCount = "likeCount";
			List<Board> likeBestList = boardService.getBestList(likeCount);
			Collections.sort(likeBestList, Comparator.comparingInt(Board::getLikeCount).reversed());
			likeBestList = likeBestList.stream().limit(5).collect(Collectors.toList());
			model.addAttribute("likeBestList",likeBestList);
			
			List<Board> viewBestList = boardService.getBestList("viewCount");
			Collections.sort(viewBestList, Comparator.comparingInt(Board::getViewCount).reversed());
			viewBestList = viewBestList.stream().limit(5).collect(Collectors.toList());
			model.addAttribute("viewBestList",viewBestList);
			
			List<Board> replyBestList = boardService.getBestList("replyCount");
			Collections.sort(replyBestList, Comparator.comparingInt(Board::getReplyCount).reversed());
			replyBestList = replyBestList.stream().limit(5).collect(Collectors.toList());
			model.addAttribute("replyBestList",replyBestList);
	        return "dashBoard/status";
	    } else {
	        return "redirect:/board/list"; 
	    }
	}
	
	@GetMapping(value={"/userList/{page}", "/userList"})
	public String list(@PathVariable(required=false) Integer page, Model model,
			 HttpSession session) {
		if ("admin".equals(session.getAttribute("sessUid"))) {
			page = (page == null) ? 1 : page;
			List<User> list = uSvc.getUserList(page);
			
			int totalUserCount = uSvc.getUserCount();
			int totalPages = (int) Math.ceil(totalUserCount / (double) uSvc.COUNT_PER_PAGE);
			int startPage = (int) Math.ceil((page - 0.5) / uSvc.COUNT_PER_PAGE - 1) * uSvc.COUNT_PER_PAGE + 1;
			int endPage = Math.min(totalPages, startPage + uSvc.COUNT_PER_PAGE - 1);
			List<Integer> pageList = new ArrayList<>();
			for (int i = startPage; i <= endPage; i++)
				pageList.add(i);

			session.setAttribute("currentUserPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("pageList", pageList);
			model.addAttribute("userList", list);
			return "dashBoard/userList";
	    } else {
	    	return "redirect:/board/list"; 
    	}  
	}
}
