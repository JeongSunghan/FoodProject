package com.example.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.dao.BoardDao;
import com.example.food.entity.Board;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired private BoardDao boardDao;

	@Override
	public Board getBoard(int bid) {
		return boardDao.getBoard(bid);
	}

	@Override
	public int getBoardCount(String field, String query) {
		query = "'%" + query + "%'";
		return boardDao.getBoardCount(field, query);
	}

	@Override
	public List<Board> getBoardList(String field, String query) {
//		int offset = (page - 1) * COUNT_PER_PAGE;
		query = "'%" + query + "%'";
		return boardDao.getBoardList(field, query);
	}

	@Override
	public void insertBoard(Board board) {
		boardDao.insertBoard(board);
	}

	@Override
	public void updateBoard(Board board) {
		boardDao.updateBoard(board);
	}

	@Override
	public void deleteBoard(int bid) {
		boardDao.deleteBoard(bid);
	}

	@Override
	public void increaseViewCount(int bid) {
		String field = "viewCount";
		boardDao.increaseCount(field, bid);
	}

	@Override
	public void increaseReplyCount(int bid) {
		String field = "replyCount";
		boardDao.increaseCount(field, bid);
	}

	@Override
	public void updateLikeCount(int bid, int count) {
		boardDao.updateLikeCount(bid, count);
	}

	//추가
	@Override
	public List<Board> getBestList(String field) {
		return boardDao.getBestList(field);
	}
	

	@Override
	public int replyCount(int bid) {
		return boardDao.replyCount(bid);
	}

	@Override
	public void updateReplyCount(int bid, int count) {
		boardDao.updateReplyCount(bid, count);
	}
	
	@Override
	public List<Board> getLikeList(String uid) {
		return boardDao.getLikeList(uid);
	}

	@Override
	public List<Board> getSearchList(String query) {
		query = query.replace("'", "''");
		query = "'%" + query + "%'";
		return boardDao.getSearchList(query);
	}
	//
}
