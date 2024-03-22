package com.example.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.dao.NReplyDao;
import com.example.food.entity.NReply;

@Service
public class NReplyServiceImpl implements NReplyService{
	@Autowired private NReplyDao nReplyDao;
	
	@Override
	public List<NReply> getNReplyList(int rid) {
		return nReplyDao.getNReplyList(rid);
	}

	@Override
	public void insertNReply(NReply nReply) {
		nReplyDao.insertNReply(nReply);
	}

	@Override
	public void deleteNReply(int nid) {
		nReplyDao.deleteNReply(nid);
	}

	@Override
	public void updateNReply(NReply nreply) {
		nReplyDao.updateNReply(nreply);
		
	}
}
