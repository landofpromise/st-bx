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

package com.alibaba.sample.petstore.web.lopstheadman.module.screen;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;

import org.springframework.beans.factory.annotation.Autowired;

public class Requisition {    
    @Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;

    public void execute(/*HttpSession session, */Context context, @Param(name="orderId") String orderId) throws Exception {
        /*Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            cart = new Cart();
        }

        cart = storeManager.getCartItems(cart);

        context.put("cart", cart);*/    	
    	int dateSelected = 0;
    	
    	if(orderId.isEmpty())
    	{
    		LopBxOrderDto dto = new LopBxOrderDto();
    		context.put("order", dto);     		
    	}
    	else
    	{
    		int orderid = Integer.parseInt(orderId);    	
        	//Order order = orderDao.getOrderById(sequenceDao.getId("stmember"));
        	//Order order = orderDao.getOrderById(orderid);   
    		LopBxOrderDto dto = new LopBxOrderDto();
    		if(0 == orderid)
    		{
    			
    		}
    		else
    		{
    			LopBxOrder order = tmpOrderDao.getTmpOrderById(orderid);            	
            	dto.setBreakfast(order.getBreakfast().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setCarfare(order.getCarfare().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setCommonts(order.getCommonts());
            	dto.setDinner(order.getDinner().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setDrink(order.getDrink().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setLeaderName(order.getLeaderName());
            	dto.setLunch(order.getLunch().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setOrderDate(order.getOrderDate());
            	dto.setOrderId(order.getOrderId());
            	dto.setOther(order.getOther().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setSubprice(order.getSubprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            	dto.setUserId(order.getUserId());
            	
            	if(dto.getOrderDate().equals("2015-09-01"))
            	{
            		dateSelected = 1;
            	}
            	else if(dto.getOrderDate().equals("2015-09-02"))
            	{
            		dateSelected = 2;
            	}
            	else if(dto.getOrderDate().equals("2015-09-03"))
            	{
            		dateSelected = 3;
            	}
            	else if(dto.getOrderDate().equals("2015-09-04"))
            	{
            		dateSelected = 4;
            	}
    		}    		
        	/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        	Date date = order.getOrderDate();  
        	String orderdate = sdf.format(date);      	
            date = java.sql.Date.valueOf(orderdate);  
            order.setOrderDate(date);*/
        	
        	/*orderDao.deleteOrderByOrderId(order.getOrderId());
        	Sequence sequence = new Sequence("stmember", sequenceDao.getId("stmember") - 1);
        	sequenceDao.updateSequence(sequence);*/
        	
        	context.put("order", dto);  
        	context.put("dateSelected", dateSelected);  
    	}    	  	
    }
}
