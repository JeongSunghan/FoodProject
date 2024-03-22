package com.example.food.entity;

import java.time.LocalDateTime;

public class Board {
	private int bid;
	private String title;
	private String content;
	private String uid;
	private LocalDateTime modTime;
	private int isDeleted;
	private int viewCount;
	private int likeCount;
	private String titleImage;
	private String category;
	private String foodName;
	private String openClosed;
	private String address;
	private String phoneNumber;
	private int replyCount;
	private String uname;
	private String lat;
	private String lon;
	private int reviewStar;
	
	
	
	public Board(int bid, String title, String content, String uid, LocalDateTime modTime, int isDeleted, int viewCount,
			int likeCount, String titleImage, String category, String foodName, String openClosed, String address,
			String phoneNumber, int replyCount, String uname, String lat, String lon, int reviewStar) {
		super();
		this.bid = bid;
		this.title = title;
		this.content = content;
		this.uid = uid;
		this.modTime = modTime;
		this.isDeleted = isDeleted;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.titleImage = titleImage;
		this.category = category;
		this.foodName = foodName;
		this.openClosed = openClosed;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.replyCount = replyCount;
		this.uname = uname;
		this.lat = lat;
		this.lon = lon;
		this.reviewStar = reviewStar;
	}
	public Board() { }
	public Board(int bid, String title, String content, String uid, LocalDateTime modTime, int isDeleted, int viewCount,
			int likeCount, String titleImage, String category, String foodName, String openClosed, String address,
			String phoneNumber, int replyCount, String uname) {
		this.bid = bid;
		this.title = title;
		this.content = content;
		this.uid = uid;
		this.modTime = modTime;
		this.isDeleted = isDeleted;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.titleImage = titleImage;
		this.category = category;
		this.foodName = foodName;
		this.openClosed = openClosed;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.replyCount = replyCount;
		this.uname = uname;
	}
	
	public Board(String title, String content, String uid, String titleImage, String category, String foodName,
			String openClosed, int reviewStar) {
		this.title = title;
		this.content = content;
		this.uid = uid;
		this.titleImage = titleImage;
		this.category = category;
		this.foodName = foodName;
		this.openClosed = openClosed;
		this.reviewStar = reviewStar;
	}
	
	@Override
	public String toString() {
		return "Board [bid=" + bid + ", title=" + title + ", content=" +   ", uid=" + uid + ", modTime="
				+ modTime + ", isDeleted=" + isDeleted + ", viewCount=" + viewCount + ", likeCount=" + likeCount
				+ ", titleImage=" + titleImage + ", category=" + category + ", foodName=" + foodName + ", openClosed="
				+ openClosed + ", address=" + address + ", phoneNumber=" + phoneNumber + ", replyCount=" + replyCount
				+ ", uname=" + uname + ", lat=" + lat + ", lon=" + lon + ", reviewStar=" + reviewStar + "]";
	}
	
	public int getReviewStar() {
		return reviewStar;
	}
	public void setReviewStar(int reviewStar) {
		this.reviewStar = reviewStar;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public LocalDateTime getModTime() {
		return modTime;
	}
	public void setModTime(LocalDateTime modTime) {
		this.modTime = modTime;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getOpenClosed() {
		return openClosed;
	}
	public void setOpenClosed(String openClosed) {
		this.openClosed = openClosed;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	
}