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

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

import org.springframework.beans.factory.annotation.Autowired;

public class RequisitionEdit {    
    @Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;
    @Autowired
    private LopBxUserDao userDao;    

    public void execute(HttpSession session, Context context, @Param(name="orderId") String orderId) throws Exception {
    	PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	String userId = petstoreUser.getId(); 
    	LopBxUser user = userDao.getUserById(userId);   
    	
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
    			LopBxOrder order = orderDao.getOrderById(orderid);            	
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
            	String name = userDao.getUserById(order.getUserId()).getAccount().getName();
            	dto.setName(name);
    		}    		
        	/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        	Date date = order.getOrderDate();  
        	String orderdate = sdf.format(date);      	
            date = java.sql.Date.valueOf(orderdate);  
            order.setOrderDate(date);*/
        	
        	/*orderDao.deleteOrderByOrderId(order.getOrderId());
        	Sequence sequence = new Sequence("stmember", sequenceDao.getId("stmember") - 1);
        	sequenceDao.updateSequence(sequence);*/
    		LopBxOrderDto orderDto = new LopBxOrderDto();
        	orderDto.setCommonts("0");
        	orderDto.setTotalprice("0.00");
        	orderDto.setOrderId(dto.getOrderId());
        	
        	context.put("order", dto);  
        	context.put("leader", orderDto);  
    	}    	  	
    }
}
