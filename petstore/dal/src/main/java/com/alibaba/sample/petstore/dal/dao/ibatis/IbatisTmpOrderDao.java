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

package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.TmpOrderDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.TmpOrder;

@SuppressWarnings("deprecation")
public class IbatisTmpOrderDao extends SqlMapClientDaoSupport implements TmpOrderDao {
	
	@SuppressWarnings({ "unchecked" })
    public List<LopBxOrder> getTmpOrdersByUserId(String username) {
		List<TmpOrder> tmpOrderList = getSqlMapClientTemplate().queryForList("getTmpOrdersByUserId", username);
		List<LopBxOrder> orderList = createLinkedList();
		
		if(!tmpOrderList.isEmpty())
		{			
			for(int i=0;i<tmpOrderList.size();i++)
			{
				orderList.add(tmpOrderToOrder(tmpOrderList.get(i)));			
			}
		}		
		
		return orderList;
    }

    public LopBxOrder getTmpOrderById(int orderId) {
        LopBxOrder order = null;
        TmpOrder tmpOrder = null;
        
        Object parameterObject = new Integer(orderId);

        tmpOrder = (TmpOrder) getSqlMapClientTemplate().queryForObject("getTmpOrder", parameterObject);
        order = tmpOrderToOrder(tmpOrder);
        
        return order;
    }
    
    @SuppressWarnings({ "unchecked" })
    public List<LopBxOrder> getTmpOrderListByLeaderName(String leaderName) {         
    	List<LopBxOrder> orderList = createLinkedList();;
    	List<TmpOrder> tmpOrderList = getSqlMapClientTemplate().queryForList("getTmpOrderListByLeaderName", leaderName);
    	
    	if(!tmpOrderList.isEmpty())
    	{
    		for(int i=0;i<tmpOrderList.size();i++)
    		{
    			orderList.add(tmpOrderToOrder(tmpOrderList.get(i)));			
    		}
    	}    	
    	
    	return orderList;
    }

    public void insertTmpOrder(LopBxOrder order) {   
    	TmpOrder tmpOrder = orderToTmpOrder(order);
    	
        getSqlMapClientTemplate().update("insertTmpOrder", tmpOrder);
        /*order.getOrderStatus().setOrderId(order.getOrderId());
        if(null == order.getOrderStatus().getStatus())
        {
        	order.getOrderStatus().setStatus("待审核");
        }
        getSqlMapClientTemplate().update("insertOrderStatus", order);
*/
        /*for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrderId(order.getOrderId());
            getSqlMapClientTemplate().update("insertOrderItem", orderItem);
        }*/
    }

	public void updateTmpOrder(LopBxOrder order) {
		TmpOrder tmpOrder = orderToTmpOrder(order);		
		
		getSqlMapClientTemplate().update("updateTmpOrder", tmpOrder);
		/*getSqlMapClientTemplate().update("updateOrderStatus", order);
		*/
	}
	
	public void deleteTmpOrderByOrderId(int orderId) {
		getSqlMapClientTemplate().delete("deleteTmpOrderByOrderId", orderId);
		/*getSqlMapClientTemplate().delete("deleteOrderStatusByOrderId", orderId);*/
	}

	public void deleteTmpOrderByUserId(String userId) {
		getSqlMapClientTemplate().delete("deleteTmpOrderByUserId", userId);
	}

	public void deleteAll() {
		getSqlMapClientTemplate().delete("deleteAll");
	}
	
	/*public int tmpOrderIsEmpty() {
	return (Integer) getSqlMapClientTemplate().queryForObject("tmpOrderIsEmpty");
	}*/
	
	public LopBxOrder tmpOrderToOrder(TmpOrder tmpOrder) {
		LopBxOrder order = new LopBxOrder();
		
		order.setBreakfast(tmpOrder.getBreakfast());
		order.setCarfare(tmpOrder.getCarfare());
		order.setCommonts(tmpOrder.getCommonts());
		order.setDinner(tmpOrder.getDinner());
		order.setDrink(tmpOrder.getDrink());
		order.setLeaderName(tmpOrder.getLeaderName());
		order.setLunch(tmpOrder.getLunch());
		order.setOrderDate(tmpOrder.getOrderDate());
		order.setOrderId(tmpOrder.getOrderId());
		order.setOther(tmpOrder.getOther());
		order.setSubprice(tmpOrder.getSubprice());
		order.setTotalprice(tmpOrder.getTotalprice());
		order.setUserId(tmpOrder.getUserId());
		return order;
	}

	public TmpOrder orderToTmpOrder(LopBxOrder order) {
		TmpOrder tmpOrder = new TmpOrder();
		
		tmpOrder.setBreakfast(order.getBreakfast());
		tmpOrder.setCarfare(order.getCarfare());
		tmpOrder.setCommonts(order.getCommonts());
		tmpOrder.setDinner(order.getDinner());
		tmpOrder.setDrink(order.getDrink());
		tmpOrder.setLeaderName(order.getLeaderName());
		tmpOrder.setLunch(order.getLunch());
		tmpOrder.setOrderDate(order.getOrderDate());
		tmpOrder.setOrderId(order.getOrderId());
		tmpOrder.setOther(order.getOther());
		tmpOrder.setSubprice(order.getSubprice());
		tmpOrder.setTotalprice(order.getTotalprice());
		tmpOrder.setUserId(order.getUserId());
		return tmpOrder;
	}
	
}
