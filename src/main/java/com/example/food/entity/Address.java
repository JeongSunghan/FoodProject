package com.example.food.entity;

public class Address {
    private Long bid; // 게시글 ID
    private String title; // 식당 이름
    private String address; // 주소

    public Address() { }
    public Address(Long bid, String title, String address) {
		this.bid = bid;
		this.title = title;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address [bid=" + bid + ", title=" + title + ", address=" + address + "]";
	}
	
	public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
