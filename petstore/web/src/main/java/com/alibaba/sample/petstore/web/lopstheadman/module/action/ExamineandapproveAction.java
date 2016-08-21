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

package com.alibaba.sample.petstore.web.lopstheadman.module.action;

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

import org.springframework.beans.factory.annotation.Autowired;

public class ExamineandapproveAction {        
	@Autowired
    private LopBxUserDao userDao;	
	@Autowired
    private LopBxOrderDao orderDao;	
    @Autowired
    private LopBxSequenceDao sequenceDao;
    
    public void doAddorders(HttpSession session, @FormGroup("leader") Group group, Navigator nav)
            throws Exception {
    	LopBxOrderDto orderDto = new LopBxOrderDto();             	
        group.setProperties(orderDto);  
        
        PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	String userId = petstoreUser.getId(); 
    	LopBxUser user = userDao.getUserById(userId);
    	int submitType = 0;//组员
    	
    	LopBxOrder orderLeader = orderDao.getOrderById(orderDto.getOrderId());
    	if(orderLeader.getUserId().equals(userId))
    	{
    		submitType = 1;//组长
    	}
    	
    	if((null != orderLeader)&&(1 == submitType))
    	{
    		orderLeader.setCommonts1(orderDto.getCommonts());
            //orderLeader.setCommonts(orderDto.getCommonts());
            orderLeader.setTotalprice(stringToBigDecimal(orderDto.getTotalprice()));
            orderDao.updateOrder(orderLeader); 
            List<LopBxOrder> orderList = orderDao.getOrdersByHeadmanIdAndLeader(orderLeader);  
            if(orderList.size() > 0)
            {
            	if(orderList.get(0).getHeadmanId() == 0)
            	{
            		int index = sequenceDao.getNextId("headman");
                    for(int i=0;i<orderList.size();i++)
                    {
                    	orderList.get(i).setHeadmanId(index);
                    	orderDao.updateOrder(orderList.get(i)); 
                    }
            	}
            }
            
            //orderLeader.setHeadmanId(index);    
            //List<Order> orderList = orderDao.getOrderListByLeaderName(user.getAccount().getLeaderName());
        	//List<Order> orders = orderDao.getOrdersByUserId(userId);
        	//List<Order> orderstmp = orderList;              
            
            LopBxOrder order = new LopBxOrder();            
        	for(int i=0;i<orderList.size();i++)
        	{
        		order = orderList.get(i);
        		if(order.getOrderStatus().getStatus().equals("已审核"))
        		{
        			//null
        		}
        		/*else if(order.getOrderStatus().getStatus().equals("组长ok"))
        		{
        			//null
        		}*/
        		else
        		{
        			order.getOrderStatus().setStatus("财务待审核");
            		order.getOrderStatus().setOrderId(order.getOrderId());
            		orderDao.updateOrder(order);    	
        		}
        	}       
            
            nav.redirectTo("lopStHeadmanModule").withTarget("aplicationstatus");
    	}
    	else
    	{
    		nav.redirectTo("lopStHeadmanModule").withTarget("examineandapprove");
    	}
        

        /*try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }*/
    }
    private BigDecimal stringToBigDecimal(String StrBd)
    {    	
    	BigDecimal bd=new BigDecimal(StrBd); 
    	bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP); 
    	
    	return bd;
    }
    
}
