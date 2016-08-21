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

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Field;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
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

public class FinancehomeAction {
	@Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private LopBxUserDao userDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;    
 
	public void doCheckorder(HttpSession session, @FormGroup("leader") Group group,/*@Param(name="leaderName") OrderDto orderDto,*/ Navigator nav)
            throws Exception {
		// 在session中创建petstoreUser对象
        PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
        String[] roles_1 = petstoreUser.getRoles();   
        
        //OrderDto orderDto = new OrderDto();
        //FileItem picture = group.getField("picture").getFileItem();
    	String leaderName = "test"; 
    	leaderName = group.getField("name").getStringValue();
    	//String orderId = group.getField("orderId").getStringValue();
    	//List<Order> orderList = orderDao.getOrderListByLeaderName(leaderName);
                
        //List<Order> orderList = tmpOrderDao.getTmpOrdersByUserId(roles_1[0]);
        /*if(!orderList.isEmpty())
    	{    		
    		orderDao.insertOrder(orderList.get(orderList.size()-1)); 
    	}*/
        nav.redirectTo("lopStFinanceModule").withTarget("financehome").withParameter("leaderName", leaderName);

        /*try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }*/
    }
}
