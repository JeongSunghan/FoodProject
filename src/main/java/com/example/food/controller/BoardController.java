package com.example.food.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.food.entity.Board;
import com.example.food.entity.Like;
import com.example.food.entity.Reply;
import com.example.food.service.BoardService;
import com.example.food.service.LikeService;
import com.example.food.service.ReplyService;
import com.example.food.util.ImageUtil;
import com.example.food.util.JsonUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
	// 서비스 및 유틸리티 클래스를 주입하기 위한 어노테이션 사용
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private JsonUtil jsonUtil;
	@Autowired
	private ImageUtil imageUtil;
	// 파일 업로드 디렉토리 설정값을 주입받기 위한 필드
	@Value("${spring.servlet.multipart.location}")
	private String uploadDir;
	// 메뉴 관련 정보를 저장하기 위한 필드
	private String menu = "board";

	@GetMapping("/list")
	public String list(@RequestParam(name = "p", defaultValue = "1") int page,
			@RequestParam(name = "f", defaultValue = "title") String field,
			@RequestParam(name = "q", defaultValue = "") String query, HttpSession session, Model model) {
		// 게시글 목록을 가져오는 서비스 메소드 호출
		List<Board> boardList = boardService.getBoardList(page, field, query);

		// 페이지네이션 정보 설정

		// 전체 게시물 수를 가져오기 위해 게시물 서비스의 getBoardCount 메서드 호출
		int totalBoardCount = boardService.getBoardCount(field, query);
		// 전체 페이지 수 계산 (한 페이지당 게시물 수를 나눈 뒤 올림 처리)
		int totalPages = (int) Math.ceil(totalBoardCount / (double) BoardService.COUNT_PER_PAGE);
		// 시작 페이지 계산
		int startPage = (int) Math.ceil((page - 0.5) / BoardService.PAGE_PER_SCREEN - 1) * BoardService.PAGE_PER_SCREEN
				+ 1;
		// 종료 페이지 계산 (총 페이지 수와 시작 페이지를 기준으로 계산)
		int endPage = Math.min(totalPages, startPage + BoardService.PAGE_PER_SCREEN - 1);
		// 페이지 번호 목록 생성을 위한 리스트
		List<Integer> pageList = new ArrayList<>();
		// 시작 페이지부터 종료 페이지까지 반복하면서 페이지 목록에 추가
		for (int i = startPage; i <= endPage; i++)
			pageList.add(i);

		// 세션 및 모델에 필요한 정보 저장 후 목록 페이지로 이동
		session.setAttribute("currentBoardPage", page);
		model.addAttribute("boardList", boardList);
		model.addAttribute("field", field);
		model.addAttribute("query", query);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageList", pageList);
		model.addAttribute("menu", menu);

		return "board/list";
	}

	// 새로운 게시글을 작성하는 페이지로 이동하는 메소드
	@GetMapping("/insert")
	public String insertForm(Model model) {
		model.addAttribute("menu", menu);
		return "board/insert";
	}

	// 새로운 게시글을 등록하는 메소드
	@PostMapping("/insert")
	public String insertProc(String title, String content, MultipartHttpServletRequest req, HttpSession session,
			String titleImage, String category, String foodName, String openTime, String closeTime, String address,
			String phoneNumber) {
		// 세션으로부터 사용자 아이디를 가져옴
		String sessUid = (String) session.getAttribute("sessUid");
		String openClosed = openTime + " - " + closeTime;
		String filename = null;
		MultipartFile filePart = req.getFile("profile");

		// 첨부 파일 리스트를 가져옴
		List<MultipartFile> uploadFileList = req.getFiles("files");

		if (filePart.getContentType().contains("image")) {
			filename = filePart.getOriginalFilename();
			String path = uploadDir + "profile/" + filename;
			try {
				filePart.transferTo(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
			filename = imageUtil.squareImage(sessUid, filename);
		}

		// 업로드된 파일을 서버에 저장하고 파일명을 리스트에 추가
		List<String> fileList = new ArrayList<>();
		for (MultipartFile part : uploadFileList) {
			// 첨부 파일이 없는 경우 건너뜀
			// 첨부 파일의 컨텐츠 타입이 "octet-stream"을 포함하면 다음 반복을 건너뜁니다.
			if (part.getContentType().contains("octet-stream"))
				continue;
		}
		// 파일명 리스트를 JSON 형태로 변환
		String files = jsonUtil.list2Json(fileList);

		// 카테고리 설정
		if (category == null || category.trim().isEmpty()) {
			category = "기본 카테고리"; // 기본값 설정
		}

		// 게시글 객체 생성 후 등록 => 수정해야함
		Board board = new Board(title, content, sessUid, filename, category, foodName, openClosed);
		board.setAddress(address);
		board.setPhoneNumber(phoneNumber);
		// 기타 필드 설정
		boardService.insertBoard(board);

		return "redirect:/board/list";
	}

	@GetMapping("/detail/{bid}/{uid}")
	public String detail(@PathVariable int bid, @PathVariable String uid, String option, HttpSession session,
			Model model) {
		// 본인이 조회한 경우 또는 댓글 작성후에는 조회수 증가시키지 않음
		String sessUid = (String) session.getAttribute("sessUid");
		if (!uid.equals(sessUid) && (option == null || option.equals("")))
			boardService.increaseViewCount(bid);

		Board board = boardService.getBoard(bid);
		model.addAttribute("board", board);

		// 좋아요 처리
		Like like = likeService.getLike(bid, sessUid);
		if (like == null)
			session.setAttribute("likeClicked", 0);
		else
			session.setAttribute("likeClicked", like.getValue());
		model.addAttribute("count", board.getLikeCount());

		List<Reply> replyList = replyService.getReplyList(bid);
		model.addAttribute("replyList", replyList);
		return "board/detail";
	}

	// 게시글 삭제 처리 메소드
	@GetMapping("/delete/{bid}")
	public String delete(@PathVariable int bid, HttpSession session) {
		boardService.deleteBoard(bid);
		return "redirect:/board/list?p=" + session.getAttribute("currentBoardPage");
	}

	// 댓글 작성 처리 메소드
	@PostMapping("/reply")
	public String reply(int bid, String uid, String comment, HttpSession session) {
		String sessUid = (String) session.getAttribute("sessUid");
		int isMine = (sessUid.equals(uid)) ? 1 : 0;
		Reply reply = new Reply(comment, sessUid, bid, isMine);

		replyService.insertReply(reply);
		boardService.increaseReplyCount(bid);

		return "redirect:/board/detail/" + bid + "/" + uid + "?option=DNI";
	}

	// AJAX 처리 - 타임리프에서 세팅하는 값을 변경하기 위한 방법
	@GetMapping("/like/{bid}")
	public String like(@PathVariable int bid, HttpSession session, Model model) {
		String sessUid = (String) session.getAttribute("sessUid");
		Like like = likeService.getLike(bid, sessUid);
		if (like == null) {
			likeService.insertLike(new Like(sessUid, bid, 1));
			session.setAttribute("likeClicked", 1);
		} else {
			int value = likeService.toggleLike(like);
			session.setAttribute("likeClicked", value);
		}
		int count = likeService.getLikeCount(bid);
		boardService.updateLikeCount(bid, count);
		model.addAttribute("count", count);
		return "board/detail::#likeCount";
	}

	@GetMapping("/update/{bid}")
	public String updateForm(@PathVariable int bid, HttpSession session, Model model) {
		Board board = boardService.getBoard(bid);
		model.addAttribute("board", board);
		return "board/update";
	}

	@PostMapping("/update")
	public String updateProc(int bid, String uid, MultipartHttpServletRequest req, HttpSession session, String category,
			String foodName, String openTime, String closeTime, String address, String phoneNumber) {
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String sessUid = (String) session.getAttribute("sessUid");
		String openClosed = openTime + " - " + closeTime;
		String filename = null;
		MultipartFile filePart = req.getFile("profile");

		// 타이틀 업데이트 로직
		if (filePart.getContentType().contains("image")) {
			filename = filePart.getOriginalFilename();
			String path = uploadDir + "profile/" + filename;
			try {
				filePart.transferTo(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
			filename = imageUtil.squareImage(sessUid, filename);
		}

		List<String> additionalFileList = (List<String>) session.getAttribute("fileList");
		if (additionalFileList == null)
			additionalFileList = new ArrayList<>();
		String[] delFileList = req.getParameterValues("delFile");
		if (delFileList != null) {
			for (String delName : delFileList) {
				File delFile = new File(uploadDir + "upload/" + delName);
				delFile.delete();
				additionalFileList.remove(delName);
			}
		}

		List<MultipartFile> fileList = req.getFiles("files");
		for (MultipartFile part : fileList) {
			if (part.getContentType().contains("octet-stream"))
				continue;
			filename = part.getOriginalFilename();
			additionalFileList.add(filename);
			String uploadFile = uploadDir + "upload/" + filename;
			File file = new File(uploadFile);
			try {
				part.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (category == null || category.trim().isEmpty()) {
			category = "기본 카테고리"; // 기본값 설정
		}

		// 게시글 객체 업데이트 로직
		Board board = boardService.getBoard(bid);
		board.setTitle(title);
		board.setContent(content);
		board.setCategory(category);
		board.setFoodName(foodName);
		board.setOpenClosed(openClosed);
		board.setTitleImage(filename);
		board.setAddress(address);
		board.setPhoneNumber(phoneNumber);

		// 게시글 정보를 데이터베이스에 업데이트
		boardService.updateBoard(board);

		return "redirect:/board/detail/" + bid + "/" + sessUid;
	}

}
