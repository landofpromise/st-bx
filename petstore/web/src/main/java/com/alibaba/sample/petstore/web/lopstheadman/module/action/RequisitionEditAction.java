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

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.math.BigDecimal;
import java.util.Date;
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
import com.alibaba.sample.petstore.web.common.PetstoreUser;

public class RequisitionEditAction {
	@Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;    
    @Autowired
    private LopBxUserDao userDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;     
 
	public void doEditorder(HttpSession session, @FormGroup("leader") Group group1, @FormGroup("requisition") Group group, Navigator nav)
            throws Exception {
    	// 在session中创建petstoreUser对象
        PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
        String[] roles_1 = petstoreUser.getRoles();   
        
    	LopBxOrder order = new LopBxOrder();
        //FileItem picture = group.getField("picture").getFileItem();
    	//String string = "asdf";       	
        group.setProperties(order);   
        int orderId = group1.getField("orderId").getIntegerValue();
        order.setOrderId(orderId);
        
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
        /*order.setUserId(roles_1[0]);
        //order.setSubprice(new BigDecimal("0"));
        order.computeSubprice();
        //获取并设置小组长名
        User user = userDao.getUserById(order.getUserId());
        order.setLeaderName(user.getAccount().getLeaderName());
        //order.setOrderId(sequenceDao.getNextId("stmember"));
     */   
       /* List<Order> orderList = tmpOrderDao.getTmpOrdersByUserId(order.getUserId());
        if(!orderList.isEmpty())
        {
        	int nextId = sequenceDao.getId("stmember");    
        	//List<Order> orderList1 = orderDao.getOrdersByUserId(order.getUserId());
        	sequenceDao.updateSequence(new Sequence("stmember", nextId - orderList.size()));
        	tmpOrderDao.deleteTmpOrderByUserId(order.getUserId());        
        	tmpOrderDao.deleteAll();
        }    */    
        
        //orderDao.insertOrder(order);
        //order.setOrderId(sequenceDao.getNextId("stmember"));
        //tmpOrderDao.insertTmpOrder(order);
        LopBxOrder order1 = orderDao.getOrderById(order.getOrderId());    		
		//order.getOrderStatus().setStatus("组长ok");
		//order.getOrderStatus().setOrderId(order.getOrderId());	
        if(null != order1)
        {
        	String orderdate = order.getOrderDate();
            Date date= java.sql.Date.valueOf(orderdate); 
            
            order1.setOrderDate(date.toString());             
            order1.setBreakfast(order.getBreakfast());            
            order1.setLunch(order.getLunch());
            order1.setDinner(order.getDinner());
            order1.setDrink(order.getDrink());
            order1.setCarfare(order.getCarfare());
            order1.setOther(order.getOther());  
            order1.setCommonts(order.getCommonts());
            order1.computeSubprice();
            
        	orderDao.updateOrder(order1);
        }        
        
        nav.redirectTo("lopStHeadmanModule").withTarget("examineandapprove").withParameter("orderId", String.valueOf(0));

        /*try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }*/
    }
}
