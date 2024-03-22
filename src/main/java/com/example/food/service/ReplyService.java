package com.example.food.service;

import java.util.List;

import com.example.food.entity.Reply;

public interface ReplyService {
	
	List<Reply> getReplyList(int bid);
	
	void insertReply(Reply reply);
	
	void deleteReply(int rid);
	
	void updateReply(Reply reply);
}
