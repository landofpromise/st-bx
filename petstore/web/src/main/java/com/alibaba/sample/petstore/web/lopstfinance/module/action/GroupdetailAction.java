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

package com.alibaba.sample.petstore.web.lopstfinance.module.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;

public class GroupdetailAction {
	@Autowired
    private LopBxSequenceDao sequenceDao;	
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private LopBxUserDao userDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;     
 
	public void doCheckorder(HttpSession session, @FormGroup("leader") Group group, Navigator nav)
            throws Exception {
		// 在session中创建petstoreUser对象
        //PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
        //String[] roles_1 = petstoreUser.getRoles();   
        
        //OrderDto orderDto = new OrderDto();
        //FileItem picture = group.getField("picture").getFileItem();
    	String leaderName = "test"; 
    	leaderName = group.getField("name").getStringValue();
    	//String orderId = group.getField("orderId").getStringValue();    	
    	
        nav.redirectTo("lopStFinanceModule").withTarget("financehome").withParameter("leaderName", leaderName);
    }
	
	public void doClearing(HttpSession session, @FormGroup("leader") Group group, Navigator nav)
            throws Exception {
		int orderid = -1;
		orderid = group.getField("orderId").getIntegerValue();
		String leaderName = group.getField("name").getStringValue();
				
		if(orderid>=0)
		{
			LopBxOrder order = orderDao.getOrderById(orderid);
			//String leader = order.getLeaderName();    	
	    	
	    	//List<Order> orders = orderDao.getOrderListByLeaderName(leader);
	    	List<LopBxOrder> orders = orderDao.getOrdersByHeadmanIdAndLeader(order);
	    	for(int i=0;i<orders.size();i++)
	    	{
	    		orders.get(i).getOrderStatus().setStatus("已审核");
	    		orderDao.updateOrder(orders.get(i));
	    	}
		}
		nav.redirectTo("lopStFinanceModule").withTarget("financehome").withParameter("leaderName", leaderName);
	}
	
	public void doRefuse(HttpSession session, @FormGroup("leader") Group group, Navigator nav)
            throws Exception {
		int orderid = -1;
		orderid = group.getField("orderId").getIntegerValue();
		String leaderName = group.getField("name").getStringValue();
				
		if(orderid>=0)
		{
			LopBxOrder order = orderDao.getOrderById(orderid);
	    	//String leader = order.getLeaderName();    	
	    	
	    	//List<Order> orders = orderDao.getOrderListByLeaderName(leader);
	    	List<LopBxOrder> orders = orderDao.getOrdersByHeadmanIdAndLeader(order);
	    	for(int i=0;i<orders.size();i++)
	    	{
	    		orders.get(i).getOrderStatus().setStatus("待审核");
	    		orderDao.updateOrder(orders.get(i));
	    	}
		}
		//nav.redirectTo("lopStFinanceModule").withTarget("financehome").withParameter("leaderName", leaderName);
		nav.redirectTo("lopStFinanceModule").withTarget("financehome");
	}
}
