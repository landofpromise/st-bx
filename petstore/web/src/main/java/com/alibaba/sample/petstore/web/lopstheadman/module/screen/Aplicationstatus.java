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

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.PETSTORE_USER_SESSION_KEY;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
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

    public void execute(HttpSession session, Context context) throws Exception {
        
    	PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	String userId = petstoreUser.getId(); 
    	LopBxUser user = userDao.getUserById(userId);        
    	List<LopBxOrder> orders = orderDao.getOrderListByLeaderName(user.getAccount().getLeaderName());
    	//List<Order> orders = orderDao.getOrdersByUserId(userId);
    	/*List<Order> orderstmp = orders;
    	for(int i=1;i<=orders.size();i++)
    	{
    		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
    		{
    			orders.remove(i-1);
    			i -= 1;    			
    		}
    	}*/
    	List<String> userNameList = createLinkedList();  
    	for(int i=0;i<orders.size();i++)
    	{
    		userNameList.add(userDao.getUserById(orders.get(i).getUserId()).getAccount().getName());
    	}
    	List<LopBxOrderDto> orderDtoList = orderToOrderDto(orders, userNameList);
    	
    	//对报销状态排序
    	Comparator<LopBxOrderDto> comparator = new Comparator<LopBxOrderDto>() {
            public int compare(LopBxOrderDto o1, LopBxOrderDto o2) {
                // TODO Auto-generated method stub
                return ~o1.getStatus().compareTo(o2.getStatus());
            }
        };
    	Collections.sort(orderDtoList, comparator);
    	
    	context.put("orders", orderDtoList);
    	//orders.isEmpty();
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
