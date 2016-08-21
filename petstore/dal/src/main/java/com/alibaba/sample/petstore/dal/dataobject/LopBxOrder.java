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

public class LopBxOrder {
    private int        orderId;
    private String     userId;  
    private String     leaderName;  
    private String       orderDate;
    private BigDecimal     breakfast;
    private BigDecimal     lunch;
    private BigDecimal     dinner;
    private BigDecimal     drink;
    private BigDecimal     carfare;
    private BigDecimal     other;
    private BigDecimal     subprice;
    private BigDecimal     totalprice;
    private String     commonts;  
    private String     commonts1; 
    private int        headmanId;
    private final LopBxOrderStatus orderStatus = new LopBxOrderStatus(this);  
    //private String     status;
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

    public BigDecimal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(BigDecimal breakfast) {
        this.breakfast = breakfast;
    }

    public BigDecimal getLunch() {
        return lunch;
    }

    public void setLunch(BigDecimal lunch) {
        this.lunch = lunch;
    }

    public BigDecimal getDinner() {
        return dinner;
    }

    public void setDinner(BigDecimal dinner) {
        this.dinner = dinner;
    }
    
    public BigDecimal getDrink() {
        return drink;
    }

    public void setDrink(BigDecimal drink) {
        this.drink = drink;
    }

    public BigDecimal getCarfare() {
        return carfare;
    }

    public void setCarfare(BigDecimal carfare) {
        this.carfare = carfare;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getSubprice() {
        return subprice;
    }

    public void setSubprice(BigDecimal subprice) {
    	if(new BigDecimal("0") == subprice)
    	{    		
    		computeSubprice();
    	}
    	else
    	{
    		this.subprice = subprice;
    	}    		
    }
    
    public void computeSubprice() {    
    	BigDecimal     subprice= new BigDecimal("0");    	
    	subprice = subprice.add(breakfast);
        subprice = subprice.add(lunch);
        subprice = subprice.add(dinner);
        subprice = subprice.add(drink);
        subprice = subprice.add(carfare);
        subprice = subprice.add(other);
        this.subprice = subprice;
    }
    
    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public String getCommonts() {
        return commonts;
    }

    public void setCommonts(String commonts) {
        this.commonts = commonts;
    }  

    public LopBxOrderStatus getOrderStatus() {
        return orderStatus;
    }
    
/*    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void initOrder(LopBxAccount account, Cart cart) {
        userId = account.getUser().getUserId();
        //orderDate = new Date();

        /*shipToFirstName = account.getFirstName();
        shipToLastName = account.getLastName();
        shipAddress1 = account.getAddress1();
        shipAddress2 = account.getAddress2();
        shipCity = account.getCity();
        shipState = account.getState();
        shipZip = account.getZip();
        shipCountry = account.getCountry();

        billToFirstName = account.getFirstName();
        billToLastName = account.getLastName();
        billAddress1 = account.getAddress1();
        billAddress2 = account.getAddress2();
        billCity = account.getCity();
        billState = account.getState();
        billZip = account.getZip();
        billCountry = account.getCountry();

        totalPrice = new BigDecimal("0"); //cart.getSubTotal();

        creditCard = "999 9999 9999 9999";
        expiryDate = "12/03";
 		cardType = "Visa";
        courier = "UPS";
        locale = "CA";
        status = "P";*/

        for (CartItem cartItem : cart.getCartItemList()) {
            addOrderItem(cartItem);
        }
    }

    public void addOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem(orderItems.size() + 1, cartItem);

        addOrderItem(orderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
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
