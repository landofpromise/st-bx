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

package com.alibaba.sample.petstore.web.lopstfinance.module.control;

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Form;
import com.alibaba.citrus.service.form.FormService;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxAccount;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;

import org.springframework.beans.factory.annotation.Autowired;

public class Financehome {
    @Autowired
    private StoreManager storeManager;
    @Autowired
    private LopBxUserDao userDao;    
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private FormService formService; 
    
    public void execute(HttpSession session, Context context, @Param(name="leaderName") String leadername) throws Exception {
    	List<LopBxOrderDto> orderDtoList = createLinkedList();
    	List<LopBxOrder> orders = createLinkedList(); 
    	List<LopBxUser> userList = createLinkedList(); 
    	List<LopBxOrder> orderList = createLinkedList(); 
    	//List<User> users = createLinkedList();
    	LopBxOrder order = new LopBxOrder();
    	String name = null;
    	Group group = null;
    	int headmanId = 0;
    	int saveHeadmanId = 0;
    	int leaderTotal = 0;
    	int[] userIndex = null;
    	
    	Form form = formService.getForm(); 

        if (form.isValid()) { 
            //group = form.getGroup("leaderName"); 
            //name = group.getField("name").getStringValue();            
        }
        else
        {
        	name = null;
        	leadername = null;
        }    		
    	if(leadername != null)
    	{
    		userList = userDao.getUsersByName(leadername);
    		if(userList.size() == 0)
    		{
    			leadername = null;
    		}
    	}
    	if((leadername == null)||(leadername == "test"))
    	{
    		leadername = "请输入全名，如：宋宜涛";
    		userList = userDao.getLeaderListByRole("headman"); 
    	}    	
    	/*else
    	{
    		userList = userDao.getUsersByName(leaderName);
    	}*/
    	/*List<User> usertmp = userList;
    	for(int i=1;i<=orders.size();i++)
    	{
    		if(usertmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
    		{
    			userList.remove(i-1);
    			i -= 1;    			
    		}
    	}*/
		//PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	//String userId = petstoreUser.getId(); 
    	//User user = userDao.getUserById(userId);        
    	/*List<Order> orders = orderDao.getOrderListByLeaderName(user.getAccount().getLeaderName());
    	List<String> userNameList = createLinkedList();    	
    	for(int i=0;i<orders.size();i++)
    	{
    		userNameList.add(userDao.getUserById(orders.get(i).getUserId()).getAccount().getName());
    	}*/
    	//List<OrderDto> orderDtoList = orderToOrderDto(orders, userNameList);
    	//userList = userDao.getLeaderListByRole("headman"); 
    	//List<Interge> userIndex = createLinkedList();
    	
    	if(userList.size()>0)
    	{
    		userIndex = new int[userList.size()];     		
    	}    	
    	for(int i=0;i<userList.size();i++)
    	{    		
    		orderList = orderDao.getOrdersByUserId(userList.get(i).getUserId());
    		//userIndex[i] = orderList.size();
    		
    		for(int j=0;j<orderList.size();j++)
    		{
    			order = orderList.get(j);
    			headmanId = order.getHeadmanId();    			
    			if(order.getOrderStatus().getStatus().equals("待审核"))
    			{    				
    				//
    			}
    			else if(order.getOrderStatus().getStatus().equals("组长ok"))
    			{    				
    				//
    			}
    			else//已审核，财务待审核
    			{    				
    				if(saveHeadmanId != headmanId)
    				{
    					orders.add(orderList.get(j));
    					saveHeadmanId = headmanId;
    					leaderTotal++;
    					//Collections.sort(orderList, c);    					
    				}
    				
    			}    			
    		}  
    		userIndex[i] = leaderTotal;
    		leaderTotal = 0;
    	}
    	
    	orderDtoList = orderToOrderDto(orders, userList, userIndex);
    	
    	//未结算放前边，已结算放后边
    	Comparator<LopBxOrderDto> comparator = new Comparator<LopBxOrderDto>() {
            public int compare(LopBxOrderDto o1, LopBxOrderDto o2) {
                // TODO Auto-generated method stub
                return ~o1.getStatus().compareTo(o2.getStatus());
            }
        };
    	Collections.sort(orderDtoList, comparator);
    	
    	//重新排列申请的编号
    	for(int i=0;i<orderDtoList.size();i++)
    	{
    		orderDtoList.get(i).setNum(i+1);
    	}
    	
    	if((orderDtoList.size() > 0) && (orderDtoList.get(0).getLeaderName() != null)&& (!leadername.equals("请输入全名，如：宋宜涛")))
    	{
    		leadername = orderDtoList.get(0).getLeaderName();    		
    	}
    	LopBxOrderDto orderDto = new LopBxOrderDto();
    	//orderDto.setName(leadername);
    	orderDto.setLeaderName(leadername);
    	if(leadername.equals("请输入全名，如：宋宜涛"))
    	{
    		/*if(orderDtoList.size()>0)
    		{
    			orderDto.setLeaderName("***");
    		}
    		else
    		{
    			orderDto.setLeaderName("test");
    		}*/
    		orderDto.setLeaderName("(*_*)");
    	}    	
    	orderDto.setOrderId(0);    	
    	
    	context.put("orders", orderDtoList);
    	context.put("leader", orderDto);
    	
    }
    
    private LopBxOrderDto orderToOrderDto(LopBxOrder order, LopBxAccount account, int num)
    {
    	LopBxOrderDto dto = new LopBxOrderDto();
    	          	
    	//dto.setBreakfast(order.getBreakfast().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	//dto.setCarfare(order.getCarfare().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	dto.setCommonts(order.getCommonts());
    	if(null != order.getCommonts1())
    	{
    		dto.setCommonts1(order.getCommonts1());
    	}    	
    	//dto.setDinner(order.getDinner().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	//dto.setDrink(order.getDrink().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	//dto.setLeaderName(order.getLeaderName());
    	//dto.setLunch(order.getLunch().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	//dto.setOrderDate(order.getOrderDate());
    	dto.setOrderId(order.getOrderId());
    	//dto.setOther(order.getOther().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	//dto.setSubprice(order.getSubprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	dto.setUserId(order.getUserId());
    	String str = order.getOrderStatus().getStatus();
    	if(str.equals("已审核"))
    	{    		
    		dto.setStatus("已结算");
    	}
    	else
    	{
    		dto.setStatus("未结算");    		
    	}    	
    	dto.setName(account.getName());
    	dto.setNum(num);
    	dto.setLeaderName(account.getLeaderName());
    	dto.setPhone(account.getPhone()); 
    	if(null == order.getTotalprice())
    	{
    		dto.setTotalprice("0.00");
    	}
    	else
    	{
    		dto.setTotalprice(order.getTotalprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	}
    	
    	return dto;
    }
    
    private List<LopBxOrderDto> orderToOrderDto(List<LopBxOrder> orderList, List<LopBxUser> userList, int[] userIndex)
    {    
    	List<LopBxOrderDto> dtoList = createLinkedList();    	
    	int index = 0;
    	int sum = 0;
    	int errorIndex = 0;
    	
    	LopBxUser user = null;
    	if(userList.size()>0)
    	{
    		user = userList.get(0);    	
    		sum = userIndex[index];
    		user = userList.get(index);
    		
	    	if(orderList.size()>0)
			{				    		
				for(int i=0;i<orderList.size();i++)
				{
					/*if(i+1 > sum)
					{				
						index++;
						sum = sum + userIndex[index];
						errorIndex = 0;
						if(0 == userIndex[index])
						{
							index++;
							for(int j=index;j<userIndex.length;j++)
							{
								if(0 == userIndex[index])
								{
									index++;
								}
								else
								{
									sum = sum + userIndex[index];
									errorIndex = 1;
									break;
								}
							}
						}						
						if(1 == errorIndex)
						{
							break;
						}
						user = userList.get(index);						
					}*/
					user = userDao.getUserById(orderList.get(i).getUserId());
					dtoList.add(orderToOrderDto(orderList.get(i), user.getAccount(), i));			
				}
			}	 
    	}
    	
    	return dtoList;
    }
}
