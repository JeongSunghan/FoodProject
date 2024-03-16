package com.example.food.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import com.example.food.entity.Address;

@Mapper
public interface AddressDao {
    @Select("SELECT * FROM board")
    List<Address> findAll();
    
    @Insert("INSERT INTO board (title, address) VALUES (#{title}, #{address})")
    void insert(Address address);
    
    @Update("UPDATE board SET title=#{title}, address=#{address} WHERE bid=#{bid}")
    void update(Address address);

    @Select("SELECT * FROM board WHERE bid = #{bid}")
    Optional<Address> findById(Long bid);
}