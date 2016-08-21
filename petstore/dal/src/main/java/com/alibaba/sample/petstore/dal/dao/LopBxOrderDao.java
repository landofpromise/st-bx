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

package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;

public interface LopBxOrderDao {
    List<LopBxOrder> getOrdersByUserId(String username);

    LopBxOrder getOrderById(int orderId);
    
    List<LopBxOrder> getOrderListByLeaderName(String leaderName);

    void insertOrder(LopBxOrder order);
    
    void updateOrder(LopBxOrder order);
    
    //void updateOrderStatus(Order order);
    
    void deleteOrderByOrderId(int orderId);
    
    List<LopBxOrder> getOrdersByHeadmanIdAndLeader(LopBxOrder order);
    
    List<LopBxOrder> getOrdersByLeaderAndDate(LopBxOrder order);
    
    LopBxOrder getOrderByUserIdAndDate(LopBxOrder order);
    
    List<LopBxOrder> getOrdersByStatus(String status);
    
    //void deleteOrderStatusByOrderId(String orderId);
}
