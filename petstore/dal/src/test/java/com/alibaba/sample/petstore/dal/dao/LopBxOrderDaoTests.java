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

package com.alibaba.sample.petstore.dal.dao;

import static com.alibaba.citrus.util.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderStatus;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LopBxOrderDaoTests extends AbstractDataAccessTests {
    @Autowired
    private LopBxOrderDao orderDao;    
    @Autowired
    private LopBxSequenceDao sequenceDao;

    @Test
    public void zsy_test() throws Exception {    	
        
    }   
    
    public void order() throws Exception {    	
        insertOrder();
        getOrderById();
        getOrdersByUserId();
        getOrderListByLeaderName();   
        deleteOrder();
    }    

	private void insertOrder() throws Exception {
        LopBxOrder order = new LopBxOrder();

        order.setOrderId(sequenceDao.getNextId("stmember")/*5*/);
        order.setUserId("stmember_1");
        order.setLeaderName("zsy");
        //order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-29"));
        order.setOrderDate("2015-05-29");
        order.setBreakfast(new BigDecimal("5.50"));
        order.setLunch(new BigDecimal("12.11"));
        order.setDinner(new BigDecimal("10.05"));
        order.setDrink(new BigDecimal("00.00"));
        order.setCarfare(new BigDecimal("4.00"));
        order.setOther(new BigDecimal("10"));
        //order.setSubprice(new BigDecimal("31.66"));
        //order.computeSubprice();
        order.setSubprice(new BigDecimal("0"));
        order.setTotalprice(new BigDecimal("00.0"));
        order.setCommonts("1234566789090");
        
        LopBxOrderStatus orderStatus = order.getOrderStatus();
        orderStatus.setStatus("待审核");
        //order.
        /*order.setShipAddress1("shipAddress1");
        order.setShipAddress2("shipAddress2");
        order.setShipCity("shipCity");
        order.setShipState("shipState");
        order.setShipZip("shipZip");
        order.setShipCountry("shipCountry");
        order.setBillAddress1("billAddress1");
        order.setBillAddress2("billAddress2");
        order.setBillCity("billCity");
        order.setBillState("billState");
        order.setBillZip("billZip");
        order.setBillCountry("billCountry");
        order.setCourier("courier");
        order.setBillToFirstName("billToFirstName");
        order.setBillToLastName("billToLastName");
        order.setShipToFirstName("shipToFirstName");
        order.setShipToLastName("shipToLastName");
        order.setCreditCard("creditCard");
        order.setExpiryDate("expDate");
        order.setCardType("cardType");
        order.setLocale("locale");
        order.setStatus("status");
        order.setTotalPrice(new BigDecimal("50.0"));*/

        /*OrderItem orderItem;

        orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setOrderItemId(sequenceDao.getNextId("orderitemnum"));
        orderItem.setProductItemId("EST-1");
        orderItem.setQuantity(1);
        orderItem.setUnitPrice(new BigDecimal("10.0"));

        order.addOrderItem(orderItem);

        orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setOrderItemId(sequenceDao.getNextId("orderitemnum"));
        orderItem.setProductItemId("EST-3");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(new BigDecimal("20.0"));

        order.addOrderItem(orderItem);*/

        orderDao.insertOrder(order);
        
        order.setOrderId(sequenceDao.getNextId("stmember")/*6*/);
        order.setUserId("stmember_2");
        order.setLeaderName("lcc");
        //order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-29"));
        order.setOrderDate("2015-05-29");
        order.setBreakfast(new BigDecimal("5.10"));
        order.setLunch(new BigDecimal("12.51"));
        order.setDinner(new BigDecimal("10.05"));
        order.setDrink(new BigDecimal("00.00"));
        order.setCarfare(new BigDecimal("4.00"));
        order.setOther(new BigDecimal("10"));
        //order.setSubprice(new BigDecimal("31.66"));
        //order.computeSubprice();
        order.setSubprice(new BigDecimal("0"));
        order.setTotalprice(new BigDecimal("00.0"));
        order.setCommonts("1234566789091");
        
        orderStatus = order.getOrderStatus();
        orderStatus.setStatus("待审核");
        
        orderDao.insertOrder(order);
        
        order.setOrderId(sequenceDao.getNextId("stmember")/*7*/);
        order.setUserId("headman_1");
        order.setLeaderName("zsy");
        //order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-29"));
        order.setOrderDate("2015-05-29");
        order.setBreakfast(new BigDecimal("5.10"));
        order.setLunch(new BigDecimal("12.51"));
        order.setDinner(new BigDecimal("10.05"));
        order.setDrink(new BigDecimal("00.00"));
        order.setCarfare(new BigDecimal("4.00"));
        order.setOther(new BigDecimal("10"));
        //order.setSubprice(new BigDecimal("31.66"));
        //order.computeSubprice();
        order.setSubprice(new BigDecimal("0"));
        order.setTotalprice(new BigDecimal("00.0"));
        order.setCommonts("1234566700000");
        orderStatus = order.getOrderStatus();
        orderStatus.setStatus("待审核");
        
        orderDao.insertOrder(order);
        
        order.setOrderId(sequenceDao.getNextId("stmember")/*8*/);
        order.setUserId("headman_2");
        order.setLeaderName("lcc");
        //order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-29"));
        order.setOrderDate("2015-05-29");
        order.setBreakfast(new BigDecimal("5.10"));
        order.setLunch(new BigDecimal("12.51"));
        order.setDinner(new BigDecimal("10.05"));
        order.setDrink(new BigDecimal("00.00"));
        order.setCarfare(new BigDecimal("4.00"));
        order.setOther(new BigDecimal("10"));
        //order.setSubprice(new BigDecimal("31.66"));
        //order.computeSubprice();
        order.setSubprice(new BigDecimal("0"));
        order.setTotalprice(new BigDecimal("00.0"));
        order.setCommonts("1234566711111");
        orderStatus = order.getOrderStatus();
        orderStatus.setStatus("待审核");
        
        orderDao.insertOrder(order);
    }

    private void getOrderById() {
        LopBxOrder order = orderDao.getOrderById(8);

        assertEquals("1234566789091", order.getCommonts());       
    }

    private void getOrdersByUserId() {
        List<LopBxOrder> orders = orderDao.getOrdersByUserId("stmember_2");

        assertEquals(1, orders.size());

        LopBxOrder order = orders.get(0);

        assertEquals("1234566789091", order.getCommonts());
    }
    
    private void getOrderListByLeaderName() {
    	List<LopBxOrder> orders = orderDao.getOrderListByLeaderName("zsy");
        //assertEquals(2, orders.size());
        LopBxOrder order = orders.get(0);
        LopBxOrder order1 = orders.get(1);
        assertEquals("1234566789090", order.getCommonts());
        assertEquals("1234566700000", order1.getCommonts());
        
        List<LopBxOrder> orders1 = orderDao.getOrderListByLeaderName("lcc");
        //assertEquals(2, orders1.size());
        order = orders1.get(0);
        order1 = orders1.get(1);
        assertEquals("1234566789091", order.getCommonts());
        assertEquals("1234566711111", order1.getCommonts());
	}
    private void deleteOrder() {
    	//orderDao.deleteOrderByOrderId(sequenceDao.getId("stmember"));
    	orderDao.deleteOrderByOrderId(sequenceDao.getId("stmember"));
    	LopBxOrder order = orderDao.getOrderById(sequenceDao.getId("stmember"));
    	assertNull("order is not null", order);
    	
    	int nextId = sequenceDao.getId("stmember");
    	LopBxSequence sequence = new LopBxSequence("stmember", nextId - 1);
    	sequenceDao.updateSequence(sequence);
    	int nextId1 = sequenceDao.getId("stmember");
    	if(nextId == nextId1)
    	{
    		assertNull("sequence is not null", sequence);
    	}    		
    
    }
}
