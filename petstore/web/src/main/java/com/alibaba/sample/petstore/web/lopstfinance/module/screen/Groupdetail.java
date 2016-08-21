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

package com.alibaba.sample.petstore.web.lopstfinance.module.screen;

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;

import org.springframework.beans.factory.annotation.Autowired;

public class Groupdetail {    
    @Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;    
    @Autowired
    private LopBxUserDao userDao;

    public void execute(/*HttpSession session, */Context context, @Param(name="orderId") String orderId) throws Exception {
    	int control = 0;
    	int orderid = Integer.parseInt(orderId);   
    	LopBxOrder order = orderDao.getOrderById(orderid);
    	String leader = order.getLeaderName();
    	String totalprice = order.getTotalprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    	
    	if(order.getOrderStatus().getStatus().equals("已审核"))
    	{
    		control = 1;
    	}
    	//List<Order> orders = orderDao.getOrderListByLeaderName(order.getLeaderName());
    	List<LopBxOrder> orders = orderDao.getOrdersByHeadmanIdAndLeader(order);
    	List<String> userNameList = createLinkedList();    	
    	for(int i=0;i<orders.size();i++)
    	{
    		userNameList.add(userDao.getUserById(orders.get(i).getUserId()).getAccount().getName());
    	}
    	List<LopBxOrderDto> orderDtoList = orderToOrderDto(orders, userNameList);
    	LopBxOrderDto orderDto = new LopBxOrderDto();
    	orderDto.setName(leader);
    	orderDto.setLeaderName(leader);
    	orderDto.setOrderId(orderid);
    	if(null == order.getCommonts1())
    	{
    		orderDto.setCommonts1("空");
    	}
    	else
    	{
    		orderDto.setCommonts1(order.getCommonts1());
    	}    	
    	
    	context.put("orders", orderDtoList); 
    	context.put("totalprice", totalprice); 
    	context.put("leaderName", leader); 
    	context.put("leader", orderDto); 
    	context.put("ctrl", control); 
    	
    	/*if(orderId.isEmpty())
    	{
    		OrderDto dto = new OrderDto();
    		context.put("order", dto); 
    	}
    	else
    	{
    		int orderid = Integer.parseInt(orderId);    	
        	//Order order = orderDao.getOrderById(sequenceDao.getId("stmember"));
        	//Order order = orderDao.getOrderById(orderid);   
    		OrderDto dto = new OrderDto();
    		if(0 == orderid)
    		{
    			
    		}
    		else
    		{
    			Order order = tmpOrderDao.getTmpOrderById(orderid);            	
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
    		} 
        	context.put("order", dto);  
    	} */     	
    }
    
    private LopBxOrderDto orderToOrderDto(LopBxOrder order, String name)
    {
    	LopBxOrderDto dto = new LopBxOrderDto();
    	          	
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
    	dto.setStatus(order.getOrderStatus().getStatus());
    	dto.setName(name);
    	//dto.setTotalprice(order.getTotalprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	
    	return dto;
    }
    
    private List<LopBxOrderDto> orderToOrderDto(List<LopBxOrder> orderList, List<String> userNameList)
    {    
    	List<LopBxOrderDto> dtoList = createLinkedList();
    	
    	if(!orderList.isEmpty())
		{			
			for(int i=0;i<orderList.size();i++)
			{
				dtoList.add(orderToOrderDto(orderList.get(i), userNameList.get(i)));			
			}
		}	 
    	
    	return dtoList;
    }
    
}
