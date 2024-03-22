package com.example.food.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.food.entity.Board;

@Mapper
public interface BoardDao {

	@Select("SELECT b.*, u.uname FROM board b"
            + " JOIN users u ON b.uid=u.uid"
            + " WHERE b.bid=#{bid} AND b.isDeleted=0")
    Board getBoard(int bid);
	
	@Select("SELECT COUNT(b.bid) FROM board b"
            + " JOIN users u ON b.uid=u.uid"
            + " WHERE b.isDeleted=0 AND ${field} LIKE ${query}")
    int getBoardCount(String field, String query);
	
	@Select("SELECT b.*, u.uname FROM board b"
            + " JOIN users u ON b.uid=u.uid"
            + " WHERE b.isDeleted=0 AND ${field} LIKE ${query}"
            + " ORDER BY b.modTime DESC")
    List<Board> getBoardList(String field, String query);
	
    @Insert("INSERT INTO board (title, content, uid, category, foodName, openClosed, address, phoneNumber, titleImage, reviewStar)"
            + " VALUES (#{title}, #{content}, #{uid}, #{category}, #{foodName}, #{openClosed}, #{address}, #{phoneNumber}, #{titleImage}, #{reviewStar})")
    void insertBoard(Board board);
	
	@Update("UPDATE board SET title=#{title}, content=#{content}, modTime=NOW(), "
            + " category=#{category}, foodName=#{foodName}, openClosed=#{openClosed}, address=#{address}, phoneNumber=#{phoneNumber}, titleImage=#{titleImage}, reviewStar=#{reviewStar}"
            + " WHERE bid=#{bid} AND isDeleted=0")
    void updateBoard(Board board);
	
	@Update("update board set isDeleted=1 where bid=#{bid}")
	void deleteBoard(int bid);
	
	@Update("update board set ${field}=${field}+1 where bid=#{bid}")
	void increaseCount(String field, int bid);	
	
	@Update("update board set likeCount=#{count} where bid=#{bid}")
	void updateLikeCount(int bid, int count);
	
	// 추가
	@Select("SELECT * FROM board WHERE isDeleted=0 ORDER BY #{field} DESC")
	List<Board> getBestList(String field);
	
	@Select("SELECT COUNT(*) AS totalCount FROM (SELECT bid FROM reply WHERE isDeleted = 0 AND bid=${bid} UNION ALL SELECT bid FROM nreply WHERE isDeleted = 0 AND bid=${bid}) AS sum GROUP BY bid ORDER BY totalCount DESC")
	int replyCount(int bid);
	
	@Update("UPDATE board SET replyCount=${count} WHERE bid=${bid}")
	void updateReplyCount(int bid, int count);
	
	@Select("SELECT b.* FROM board b JOIN likes l ON b.bid=l.bid WHERE l.uid=#{uid} AND isDeleted = 0 AND value=1")
	List<Board> getLikeList(String uid);
	
	@Select("SELECT * FROM board WHERE CONCAT(address, title, uid, foodName, category, foodName, content) LIKE ${query} AND isDeleted = 0")
	List<Board> getSearchList(String query);
	//

}
