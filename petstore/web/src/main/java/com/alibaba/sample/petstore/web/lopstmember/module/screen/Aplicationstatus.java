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

package com.alibaba.sample.petstore.web.lopstmember.module.screen;

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

import org.springframework.beans.factory.annotation.Autowired;

public class Aplicationstatus {   
    @Autowired
    private LopBxSequenceDao sequenceDao;    
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private LopBxUserDao userDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;

    public void execute(HttpSession session, Context context, @Param(name="orderDate") String orderDate) throws Exception {
        
    	PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	String userId = petstoreUser.getId(); 
    	//User user = userDao.getUserById(userId);        
    	List<LopBxOrder> orders = orderDao.getOrdersByUserId(userId/*user.getAccount().getName()*/);
    	/*List<Order> orderstmp = orders;
    	for(int i=1;i<=orders.size();i++)
    	{
    		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
    		{
    			orders.remove(i-1);
    			i -= 1;    			
    		}
    	}*/
    	/*List<Order> orderList = tmpOrderDao.getTmpOrdersByUserId(userId);        
        if(!orderList.isEmpty())
        {        	
        	tmpOrderDao.deleteAll();        
        }  */   
    	LopBxOrder order = new LopBxOrder();    	
    	if(orders.size()>0)
    	{
    		order = orders.get(0);
    		//order.setOrderDate(orderDate);
    		if(null != orderDate)
        	{
    			
    			order = orderDao.getOrderByUserIdAndDate(order);
        	}    		
    	}
    	
    	//对报销状态排序
    	Comparator<LopBxOrder> comparator = new Comparator<LopBxOrder>() {
            public int compare(LopBxOrder o1, LopBxOrder o2) {
                // TODO Auto-generated method stub
                return ~o1.getOrderStatus().getStatus().compareTo(o2.getOrderStatus().getStatus());
            }
        };
    	Collections.sort(orders, comparator);
    	
    	context.put("orders", orders);
    	context.put("order_ph", order);
    	
    	//orders.isEmpty();
    }
}
