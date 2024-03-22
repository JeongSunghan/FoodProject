package com.example.food.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.food.entity.Reply;

@Mapper
public interface ReplyDao {
	
	@Select("select r.*, u.uname from reply r"
			+ " join users u on r.uid=u.uid"
			+ " where r.bid=#{bid} and r.isDeleted=0")
	List<Reply> getReplyList(int bid);
	
	@Insert("insert into reply values(default, #{comment}, default, #{uid}, #{bid}, #{isMine}, default)")
	void insertReply(Reply reply);
	
	@Update("update reply set isDeleted=1 where rid=#{rid}")
	void deleteReply(int rid);
	
	@Update("update reply set comment=#{comment}, regTime=NOW() where rid=#{rid}")
	void updateReply(Reply reply);
}
