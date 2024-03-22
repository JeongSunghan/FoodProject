package com.example.food.service;

import java.util.List;

import com.example.food.entity.NReply;

public interface NReplyService {
	
	List<NReply> getNReplyList(int rid);
	
	void insertNReply(NReply nReply);
	
	void deleteNReply(int nid);

	void updateNReply(NReply nreply);
}
