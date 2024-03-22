package com.example.food.service;

import java.util.List;

import com.example.food.entity.Like;

public interface LikeService {
	
	Like getLike(int bid, String uid);
	
	Like getLikeByLid(int lid);
	
	List<Like> getLikeList(int bid);
	
	void insertLike(Like like);
	
	int toggleLike(Like like);		// value가 0 이면 1로 바꾸고, 1면 0으로 바꿈 = toggle토글
	
	int getLikeCount(int bid);
}
