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

import java.util.List;

import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;
import com.alibaba.sample.petstore.dal.dataobject.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisLopBxOrderDao extends SqlMapClientDaoSupport implements LopBxOrderDao {
	
	@SuppressWarnings("unchecked")
    public List<LopBxOrder> getOrdersByUserId(String username) {
        return getSqlMapClientTemplate().queryForList("getOrdersByUserId", username);
    }

    @SuppressWarnings("unchecked")
    public LopBxOrder getOrderById(int orderId) {
        LopBxOrder order = null;
        Object parameterObject = new Integer(orderId);

        order = (LopBxOrder) getSqlMapClientTemplate().queryForObject("getOrder", parameterObject);
        /*order.setOrderItems(getSqlMapClientTemplate().queryForList("getOrderItemsByOrderId",
                                                                   new Integer(order.getOrderId())));
*/
        return order;
    }
    
    @SuppressWarnings("unchecked")
    public List<LopBxOrder> getOrderListByLeaderName(String leaderName) {         
        return getSqlMapClientTemplate().queryForList("getOrderListByLeaderName", leaderName);
    }

    public void insertOrder(LopBxOrder order) {     	
        getSqlMapClientTemplate().update("insertOrder", order);
        order.getOrderStatus().setOrderId(order.getOrderId());
        if(null == order.getOrderStatus().getStatus())
        {
        	order.getOrderStatus().setStatus("待审核");
        }
        getSqlMapClientTemplate().update("insertOrderStatus", order);

        /*for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrderId(order.getOrderId());
            getSqlMapClientTemplate().update("insertOrderItem", orderItem);
        }*/
    }

	@SuppressWarnings("deprecation")
	public void updateOrder(LopBxOrder order) {
		getSqlMapClientTemplate().update("updateOrder", order);
		getSqlMapClientTemplate().update("updateOrderStatus", order);
		
	}
	
	@SuppressWarnings("deprecation")
	public void deleteOrderByOrderId(int orderId) {
		getSqlMapClientTemplate().delete("deleteOrderByOrderId", orderId);
		getSqlMapClientTemplate().delete("deleteOrderStatusByOrderId", orderId);
	}

	public List<LopBxOrder> getOrdersByHeadmanIdAndLeader(LopBxOrder order) {
		return getSqlMapClientTemplate().queryForList("getOrdersByHeadmanId", order);
	}

	public List<LopBxOrder> getOrdersByLeaderAndDate(LopBxOrder order) {
		return getSqlMapClientTemplate().queryForList("getOrdersByLeaderAndDate", order);		
	}

	public LopBxOrder getOrderByUserIdAndDate(LopBxOrder order) {		
		return (LopBxOrder)getSqlMapClientTemplate().queryForObject("getOrdersByUserIdAndDate", order);
	}

	public List<LopBxOrder> getOrdersByStatus(String status) {
		return getSqlMapClientTemplate().queryForList("getOrdersByStatus", status);
	}
	
}
