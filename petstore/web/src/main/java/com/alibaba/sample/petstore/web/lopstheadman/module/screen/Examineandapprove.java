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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

import org.springframework.beans.factory.annotation.Autowired;

public class Examineandapprove {    
    @Autowired
    private LopBxSequenceDao sequenceDao;
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private TmpOrderDao tmpOrderDao;
    @Autowired
    private LopBxUserDao userDao;

    public void execute(HttpSession session, Context context,/* @FormGroup("leader") Group group,*/@Param(name="orderId") String orderId) throws Exception {
    	int orderid = 0;   
    	BigDecimal totalprice = new BigDecimal(0.00);   
    	LopBxOrderDto leader = new LopBxOrderDto();
    	List<LopBxOrderDto> orderDtoList = createLinkedList(); 
    	List<LopBxOrder> orders = createLinkedList(); 
        int leaderError = 0;
        int processType = 0;
        int leaderErrorIndex = 0;
        
    	if(null != orderId)
    	{
    		if(!orderId.isEmpty())
        	{
        		orderid = Integer.parseInt(orderId);     		
        	}
        	if(0 == orderid)
        	{
        		//leader.setCommonts("备注：");
        		//leader.setTotalprice(totalprice);
        	}
        	else
        	{
        		orderid = Integer.parseInt(orderId); 
        		LopBxOrder order = orderDao.getOrderById(orderid);    		
        		order.getOrderStatus().setStatus("组长ok");
        		order.getOrderStatus().setOrderId(order.getOrderId());
        		orderDao.updateOrder(order);    		
        	}
    	}
    	
    	PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);
    	String userId = petstoreUser.getId(); 
    	LopBxUser user = userDao.getUserById(userId);  	
    	//判断是否有未处理的数据
    	LopBxOrder order1 = new LopBxOrder();
    	orders = orderDao.getOrdersByUserId(userId);
    	if(0 == orders.size())
    	{
    		leaderError = 1;//组长还没有提交申请
    	}
    	order1.setHeadmanId(0);
    	for(int i=0;i<orders.size();i++)
    	{
    		LopBxOrder orderTmp = orders.get(i);
    		if(orderTmp.getOrderStatus().getStatus().equals("待审核")||(orderTmp.getOrderStatus().getStatus().equals("组长ok")))
    		{
    			if(orders.get(i).getHeadmanId()>0)
    			{    				
    				order1.setHeadmanId(orders.get(i).getHeadmanId());
    				processType = 1;//处理数据来自财务
    				break;
    			}
    		}	  
    	}    	
    	order1.setLeaderName(user.getAccount().getLeaderName());
    	orders = orderDao.getOrdersByHeadmanIdAndLeader(order1);
    	
    	for(int i=0;i<orders.size();i++)
    	{
    		LopBxOrder orderTmp = orders.get(i);
    		if(orderTmp.getUserId().equals(userId))
    		{
    			leaderErrorIndex = 1;
    			break;
    		}	  
    	}  
    	if(0 == leaderErrorIndex)
    	{
    		leaderError = 1;//组长还没有提交申请
    	}
    	
    	if((orders.size()>0)/*&&(0 == leaderError)*/)
    	{
    		//List<LopBxOrder> orders_st = orderDao.getOrdersByUserId(userId);
        	List<LopBxOrder> orderstmp = orders;
        	//LopBxOrder order_leader = new LopBxOrder();
        	/*for(int i=1;i<=orders.size();i++)
        	{
        		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
        		{
        			orders.remove(i-1);
        			i -= 1;    			
        		}
        	}*/
        	for(int i=0;i<orders.size();i++)
        	{
        		totalprice = orders.get(i).getSubprice().add(totalprice);		
        	}
        	
        	List<String> userNameList = createLinkedList();    	
        	for(int i=0;i<orders.size();i++)
        	{
        		userNameList.add(userDao.getUserById(orders.get(i).getUserId()).getAccount().getName());
        	}
        	
        	//List<String> userNameList = userDao.getUserIdList();
        	
        	orderDtoList = orderToOrderDto(orders, userNameList);	
        	
        	//List<LopBxOrder> leaderOrders = orderDao.getOrdersByUserId(userId);  
        	List<LopBxOrder> leaderOrders = orders;
        	orderstmp = leaderOrders;    		 
        	/*for(int i=1;i<=leaderOrders.size();i++)
        	{
        		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
        		{
        			leaderOrders.remove(i-1);
        			i -= 1;    			
        		}    		
        	}*/
        	int firstTmp = 0;    				
        	for(int i=0;i<leaderOrders.size();i++)
        	{
        		if(orderstmp.get(i).getUserId().equals(userId))
        		{       
        			if(0 == processType)//处理数据来自组员
        			{
        				//firstTmp = orderstmp.get(i).getHeadmanId();
            			firstTmp = i;
            			break;
        			}
        			else if(1 == processType)//处理数据来自财务
                	{
                		if(null != orderstmp.get(i).getCommonts1())
                		{
                			firstTmp = i;
                			break;
                		}
                	}            			
        		}    		
        	}  
        	
    		if(leaderOrders.size()>0)
        	{
        		leaderOrders.get(firstTmp).setTotalprice(totalprice);
        		leader = orderToOrderDto(leaderOrders.get(firstTmp),"");
        		order1 = leaderOrders.get(firstTmp);
        		if((null == leaderOrders.get(firstTmp).getCommonts1())||("" == leaderOrders.get(firstTmp).getCommonts1()))
        		{
        			leader.setCommonts("空");
        		}
        		else
        		{
        			leader.setCommonts(leaderOrders.get(firstTmp).getCommonts1());
        		}            	
            	leader.setTotalprice(totalprice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        	} 
    		//对报销状态排序
        	Comparator<LopBxOrderDto> comparator = new Comparator<LopBxOrderDto>() {
                public int compare(LopBxOrderDto o1, LopBxOrderDto o2) {
                    // TODO Auto-generated method stub
                    return o1.getStatus().compareTo(o2.getStatus());
                }
            };
        	Collections.sort(orderDtoList, comparator);
    	}
    	else
    	{
    		leader.setOrderId(0);
    		leader.setCommonts("无");
    		leader.setTotalprice("0");    
    	}
    	
    	/*if(orders.size()>0)
    	{
    		//List<Order> orders = orderDao.getOrderListByLeaderName(user.getAccount().getLeaderName());
        	//List<Order> orders = orderDao.getOrdersByUserId(userId);
        	List<LopBxOrder> orderstmp = orders;
        	for(int i=1;i<=orders.size();i++)
        	{
        		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
        		{
        			orders.remove(i-1);
        			i -= 1;    			
        		}
        	}
        	for(int i=0;i<orders.size();i++)
        	{
        		totalprice = orders.get(i).getSubprice().add(totalprice);		
        	}
        	
        	List<String> userNameList = createLinkedList();    	
        	for(int i=0;i<orders.size();i++)
        	{
        		userNameList.add(userDao.getUserById(orders.get(i).getUserId()).getAccount().getName());
        	}
        	
        	//List<String> userNameList = userDao.getUserIdList();
        	
        	orderDtoList = orderToOrderDto(orders, userNameList);	
        	
        	//List<LopBxOrder> leaderOrders = orderDao.getOrdersByUserId(userId);  
        	List<LopBxOrder> leaderOrders = orders;
        	orderstmp = leaderOrders;    		 
        	for(int i=1;i<=leaderOrders.size();i++)
        	{
        		if(orderstmp.get(i-1).getOrderStatus().getStatus().equals("已审核"))
        		{
        			leaderOrders.remove(i-1);
        			i -= 1;    			
        		}    		
        	}
        	int firstTmp = 0;
        	for(int i=0;i<leaderOrders.size();i++)
        	{
        		if(orderstmp.get(i).getHeadmanId()==0)
        		{       
        			//firstTmp = orderstmp.get(i).getHeadmanId();
        			firstTmp = i;
        			break;
        		}    		
        	}
        	
        	if(leaderOrders.size()>0)
        	{
        		leaderOrders.get(firstTmp).setTotalprice(totalprice);
        		leader = orderToOrderDto(leaderOrders.get(firstTmp),"");
        		order1 = leaderOrders.get(firstTmp);
        		if((null == leaderOrders.get(firstTmp).getCommonts1())||("" == leaderOrders.get(firstTmp).getCommonts1()))
        		{
        			leader.setCommonts("空");
        		}
        		else
        		{
        			leader.setCommonts(leaderOrders.get(firstTmp).getCommonts1());
        		}            	
            	leader.setTotalprice(totalprice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        	} 
    	}
    	else
    	{    		
    		leader.setOrderId(0);
    		leader.setCommonts("无");
    		leader.setTotalprice("0");    		
    	}    	*/
    	context.put("orderList", orderDtoList);//
        context.put("leader", leader); 
        context.put("leaderError", leaderError); 
    	
    }
    
    /*private Order orderDtoToOrder(OrderDto dto)
    {
    	Order order = new Order();   
    
    	order.setBreakfast(stringToBigDecimal(dto.getBreakfast()));
    	order.setCarfare(stringToBigDecimal(dto.getCarfare()));
    	order.setCommonts(dto.getCommonts());
    	order.setDinner(stringToBigDecimal(dto.getCarfare()));
    	order.setDrink(stringToBigDecimal(dto.getCarfare()));
    	order.setLeaderName(dto.getLeaderName());
    	order.setLunch(stringToBigDecimal(dto.getCarfare()));
    	order.setOrderDate(dto.getOrderDate());
    	order.setOrderId(dto.getOrderId());
    	order.setOther(stringToBigDecimal(dto.getCarfare()));
    	order.setSubprice(stringToBigDecimal(dto.getCarfare()));
    	order.setUserId(dto.getUserId());
    	order.getOrderStatus().setStatus(dto.getStatus());
    	//order.setName(name);
    	
    	return order;
    }
    
    private BigDecimal stringToBigDecimal(String StrBd)
    {    	
    	BigDecimal bd=new BigDecimal(StrBd); 
    	bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP); 
    	
    	return bd;
    }*/
    
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
    	//dto.setTotalprice(order.getTotalprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
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
