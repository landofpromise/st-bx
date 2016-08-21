/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.sample.petstore.dal.dataobject;

import static com.alibaba.citrus.util.CollectionUtil.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LopBxOrderDto {
    private int        orderId;
    private int        headmanId;
    private String     userId;  
    private String     leaderName; 
    private String     name; 
    private String     orderDate;
    private String     breakfast;
    private String     lunch;
    private String     dinner;
    private String     drink;
    private String     carfare;
    private String     other;
    private String     subprice;
    private String     totalprice;
    private String     commonts; 
    private String     commonts1; 
    private String     status;
    private int        num;
    private String     phone;
    private List<OrderItem> orderItems = createLinkedList();
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}
	public String getLunch() {
		return lunch;
	}
	public void setLunch(String lunch) {
		this.lunch = lunch;
	}
	public String getDinner() {
		return dinner;
	}
	public void setDinner(String dinner) {
		this.dinner = dinner;
	}
	public String getDrink() {
		return drink;
	}
	public void setDrink(String drink) {
		this.drink = drink;
	}
	public String getCarfare() {
		return carfare;
	}
	public void setCarfare(String carfare) {
		this.carfare = carfare;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getSubprice() {
		return subprice;
	}
	public void setSubprice(String subprice) {
		this.subprice = subprice;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getCommonts() {
		return commonts;
	}
	public void setCommonts(String commonts) {
		this.commonts = commonts;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCommonts1() {
		return commonts1;
	}
	public void setCommonts1(String commonts1) {
		this.commonts1 = commonts1;
	}
	public int getHeadmanId() {
		return headmanId;
	}
	public void setHeadmanId(int headmanId) {
		this.headmanId = headmanId;
	}

   
}
