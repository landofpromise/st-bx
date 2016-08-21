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
import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;

import org.springframework.beans.factory.annotation.Autowired;

public class Requistting {    
    @Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private TmpOrderDao TmpOrderDao;
    
    public void execute(/*HttpSession session, */Context context, @Param(name="orderId") String orderId) throws Exception {
        /*Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            cart = new Cart();
        }

        cart = storeManager.getCartItems(cart);

        context.put("cart", cart);*/
    	if(!orderId.equals(null))
    	{
    		int orderid = Integer.parseInt(orderId);    	
        	//Order order = orderDao.getOrderById(sequenceDao.getId("stmember"));
        	//Order order = orderDao.getOrderById(orderid); 
        	LopBxOrder order = TmpOrderDao.getTmpOrderById(orderid);
        	
        	/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        	Date date = order.getOrderDate();  
        	String orderdate = sdf.format(date);      	
            date = java.sql.Date.valueOf(orderdate);           
            order.setOrderDate(date);*/
        	
        	String leadername = order.getLeaderName();        	
        	int orderidDto = order.getOrderId();
        	LopBxOrderDto orderDto = new LopBxOrderDto();        	
        	orderDto.setOrderId(orderidDto);      
        	orderDto.setTotalprice("0");
        	orderDto.setCommonts(leadername);
        	
        	int dateError = 0;
        	List<LopBxOrder> orderList = orderDao.getOrdersByUserId(order.getUserId());        
            if(!orderList.isEmpty())
            {        	
            	  for(int i=0;i<orderList.size();i++)
            	  {
            		  LopBxOrder order1 = orderList.get(i);
            		  Date date1 = java.sql.Date.valueOf(order1.getOrderDate());  
            		  Date date = java.sql.Date.valueOf(order.getOrderDate());  
            		  if(date.equals(date1))
            		  {
            			  dateError = 1;
            			  break;
            		  }
            		  /*if(orderList.get(i).getOrderDate().equals(order.getOrderDate()))
            		  {
            			  dateError = 1;
            		  }*/
            	  }
            }     
            if(order.getSubprice().compareTo(new BigDecimal(500.00)) > 0)
            {
            	dateError = 2;
            }
        	context.put("leader", orderDto); 
        	context.put("dateError", dateError);
        	context.put("order", order);  
    	}    	 	
    }
}
