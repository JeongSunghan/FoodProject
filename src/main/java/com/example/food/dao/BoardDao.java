package com.example.food.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
            + " WHERE b.isDeleted=0 AND ${field} LIKE '%${query}%'")
    int getBoardCount(String field, String query);

    @Select("SELECT b.*, u.uname FROM board b"
            + " JOIN users u ON b.uid=u.uid"
            + " WHERE b.isDeleted=0 AND ${field} LIKE '%${query}%'"
            + " ORDER BY b.modTime DESC"
            + " LIMIT #{count} OFFSET #{offset}")
    List<Board> getBoardList(String field, String query, int count, int offset);

    @Insert("INSERT INTO board (title, content, uid, category, foodName, openClosed, address, phoneNumber, titleImage)"
            + " VALUES (#{title}, #{content}, #{uid}, #{category}, #{foodName}, #{openClosed}, #{address}, #{phoneNumber}, #{titleImage})")
    void insertBoard(Board board);

    @Update("UPDATE board SET title=#{title}, content=#{content}, modTime=NOW(), "
            + " category=#{category}, foodName=#{foodName}, openClosed=#{openClosed}, address=#{address}, phoneNumber=#{phoneNumber}, titleImage=#{titleImage}"
            + " WHERE bid=#{bid} AND isDeleted=0")
    void updateBoard(Board board);

    @Update("UPDATE board SET isDeleted=1 WHERE bid=#{bid}")
    void deleteBoard(int bid);

    @Update("UPDATE board SET ${field} = ${field} + 1 WHERE bid=#{bid} AND isDeleted=0")
    void increaseCount(String field, int bid);

    @Update("UPDATE board SET likeCount=#{count} WHERE bid=#{bid} AND isDeleted=0")
    void updateLikeCount(int bid, int count);
}
