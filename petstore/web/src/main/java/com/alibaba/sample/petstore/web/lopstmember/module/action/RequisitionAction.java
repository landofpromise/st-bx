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

package com.alibaba.sample.petstore.web.lopstmember.module.action;

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.LOGIN_RETURN_DEFAULT_LINK;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.LOP_ST_HEADMAN_LINK;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

public class RequisitionAction {
	@Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;    
    @Autowired
    private LopBxUserDao userDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;     
 
	public void doAddorder(HttpSession session,@FormGroup("requisition") Group group, Navigator nav, ParameterParser params)
            throws Exception {
    	// 在session中创建petstoreUser对象
        PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
        //String[] roles_1 = petstoreUser.getRoles();
        String userId = petstoreUser.getId();
        
        int orderid = -1;
               
    	LopBxOrder order = new LopBxOrder();
        //FileItem picture = group.getField("picture").getFileItem();
    	//String string = "asdf";       	
        group.setProperties(order);   
        String orderDateTime = params.getString("lopDateTime");
        if (StringUtil.isEmpty(orderDateTime)) {
        	//dateError = 1;//未选择
        }
    	else //if (orderDateTime.equalsIgnoreCase("2015-08-24"))
    	{
    		order.setOrderDate(orderDateTime);
    	}
    	
        /*String orderdate = group.getField("orderDate").getStringValue(); 
        //order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(orderdate));        
              
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(orderdate);
        date = java.sql.Date.valueOf(orderdate);  
        order.setOrderDate(date);
        // 2007-1-18  
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        //format = DateFormat.getDateInstance(DateFormat.MEDIUM);  
        //str = format.format(date);  
*/              
        order.setUserId(userId);
        //order.setSubprice(new BigDecimal("0"));
        order.computeSubprice();
        //获取并设置小组长名
        LopBxUser user = userDao.getUserById(userId);
        order.setLeaderName(user.getAccount().getLeaderName());
        //order.setOrderId(sequenceDao.getNextId("stmember"));
        
        List<LopBxOrder> orderList = tmpOrderDao.getTmpOrdersByUserId(order.getUserId());        
        if(!orderList.isEmpty())
        {
        	/*int nextId = sequenceDao.getId("stmember");    
        	//List<Order> orderList1 = orderDao.getOrdersByUserId(order.getUserId());
        	sequenceDao.updateSequence(new Sequence("stmember", nextId - orderList.size()));
        	tmpOrderDao.deleteTmpOrderByUserId(order.getUserId());    */            	
        	//tmpOrderDao.deleteAll();
        	tmpOrderDao.deleteTmpOrderByUserId(petstoreUser.getId());
        	
        	/*Sequence sequence = new Sequence("stmember", sequenceDao.getId("stmember"));
        	sequenceDao.updateSequence(sequence);*/
        }        
        
        //orderDao.insertOrder(order);      
        /*if(-1 == orderid)
        {
        	orderid = sequenceDao.getNextId("stmember");
        }*/        
        orderid = sequenceDao.getNextId("tmporder");
        order.setOrderId(orderid);        
        String orderdate = order.getOrderDate();
        Date date= java.sql.Date.valueOf(orderdate); 
        order.setOrderDate(date.toString());   
        order.setHeadmanId(0);
        tmpOrderDao.insertTmpOrder(order);              
        
        nav.redirectTo("lopStMemberModule").withTarget("requistting").withParameter("orderId", String.valueOf(order.getOrderId()));
        
        /*try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }*/
    }
	public void doDatecheck(HttpSession session, @FormGroup("LopBxOrderDate") Group group, Navigator nav)
            throws Exception {
    	// 在session中创建petstoreUser对象
        PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
        //String[] roles_1 = petstoreUser.getRoles();
        String userId = petstoreUser.getId();   
        
        String orderDate = group.getField("orderDate").getStringValue();
        
    	       
        
        nav.redirectTo("lopStMemberModule").withTarget("aplicationstatus").withParameter("orderDate", orderDate);

        /*try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }*/
    }
}
