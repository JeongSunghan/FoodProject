package com.example.food.controller;

import java.io.File;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.food.entity.User;
import com.example.food.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired private UserService uSvc;
	@Autowired private ResourceLoader resourceLoader;
	
	@GetMapping("/register")
	public String registerForm() {
		return "user/register";
	}
	
	@PostMapping("/register")	// req - 파일을 받기위해 사용
	public String registerProc(Model model, String uid, String pwd, String pwd2, String uname, 
			String email){
		String filename = null;
		
		if(uSvc.getUserByUid(uid) != null) {
			model.addAttribute("msg", "사용자 ID가 중복되었습니다.");
			model.addAttribute("url", "/food/user/register");	
			return "common/alertMsg";
		}
		if (pwd.equals(pwd2) && pwd != null) {
			User user = new User(uid, pwd, uname, email);
			uSvc.registerUser(user);
			model.addAttribute("msg", "등록을 마쳤습니다. 로그인하세요");
			model.addAttribute("url", "/food/user/login");	
			return "common/alertMsg";
		} else {
			
			model.addAttribute("msg", "패스워드 입력이 잘못되었습니다.");
			model.addAttribute("url", "/food/user/register");	
			return "common/alertMsg";
		}
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginProc(String uid, String pwd, HttpSession session, Model model){
		int result = uSvc.login(uid, pwd);
		switch(result) {
		case UserService.CORRECT_LOGIN:
			User user = uSvc.getUserByUid(uid);
			session.setAttribute("sessUid", uid);
			session.setAttribute("sessUname", user.getUname());
			session.setAttribute("email", user.getEmail());
			// 환영 메세지
			log.info("Info Login: {}, {}", uid, user.getUname());
			model.addAttribute("msg", user.getUname() + "님 환영합니다.");
			model.addAttribute("url", "/food/board/list");
			break;
			
		case UserService.USER_NOT_EXIST:
			model.addAttribute("msg", "ID가 없습니다. 회원가입 페이지로 이동합니다.");
			model.addAttribute("url", "/food/user/register");
			break;
			
		case UserService.WRONG_PASSWORD:
			model.addAttribute("msg", "패스워드 입력이 잘못되었습니다. 다시 입력하세요.");
			model.addAttribute("url", "/food/user/login");	
		}
		return "common/alertMsg";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/login";
	}
	
	@GetMapping("/update/{uid}")
	public String update(@PathVariable String uid, Model model) {
		User user = uSvc.getUserByUid(uid);
		model.addAttribute("user", user);
		return "user/update";
	}
	
	@PostMapping("/update")
	public String updateProc(String uid, String pwd, String pwd2, String uname, String email) {
		User user = uSvc.getUserByUid(uid);
		if (pwd != null && pwd.equals(pwd2)) {
			String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			user.setPwd(hashedPwd);
		}
		user.setUname(uname);
		user.setEmail(email);
		uSvc.updateUser(user);
		return "redirect:/board/list";
	}
	
	@GetMapping("/delete/{uid}")
	public String delete(@PathVariable String uid, HttpSession session) {
		uSvc.deleteUser(uid);
		return "redirect:/dashBoard/userList";
	}
	
}
