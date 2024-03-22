package com.example.food.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.food.entity.Board;

public interface BoardService {
//	public static final int COUNT_PER_PAGE = 10;	// 한 페이지당 글 목록의 갯수
//	public static final int PAGE_PER_SCREEN = 10;	// 한 화면에 표시되는 페이지 갯수
	
	Board getBoard(int bid);
	
	int getBoardCount(String field, String query);
	
	List<Board> getBoardList(String field, String query);
	
	void insertBoard(Board board);
	
	void updateBoard(Board board);
	
	void deleteBoard(int bid);
	
	void increaseViewCount(int bid);
	
	void increaseReplyCount(int bid);
	
	void updateLikeCount(int bid, int count);
	
	// 추가
	List<Board> getBestList(String field);
	
	int replyCount(int bid);
	
	void updateReplyCount(int bid, int count);
	
	List<Board> getLikeList(String uid);
	
	List<Board> getSearchList(String query);
	//
}
