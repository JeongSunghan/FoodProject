package com.example.food.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.food.entity.NReply;

@Mapper
public interface NReplyDao {

	@Select("select n.*, u.uname from nreply n"
			+ " join users u on n.uid=u.uid"
			+ " where n.rid=#{rid} and n.isDeleted=0")
	List<NReply> getNReplyList(int rid);
	
	@Insert("insert into nreply values(default, #{comment}, default, #{uid}, #{bid}, #{rid}, #{isMine}, default)")
	void insertNReply(NReply nReply);
	
	@Update("update nreply set isDeleted=1 where nid=#{nid}")
	void deleteNReply(int nid);

	@Update("update nreply set comment=#{comment}, regTime=NOW() where nid=#{nid}")
	void updateNReply(NReply nreply);
}
