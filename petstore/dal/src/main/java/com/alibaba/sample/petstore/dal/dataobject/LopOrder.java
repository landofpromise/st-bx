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

package com.alibaba.sample.petstore.dal.dataobject;

import static com.alibaba.citrus.util.CollectionUtil.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 在session中保存shopping cart对象的key。
 *
 * @author Michael Zhou
 */
public class LopOrder implements Serializable {
    private static final long                  serialVersionUID = 43557847549256390L;
    private              Map<String, LopBxOrder> orderList        = createLinkedHashMap();
    private int         itemId;
    
    public LopBxOrder getOrder(String itemId) {
        return orderList.get(itemId);
    }

    public List<LopBxOrder> getOrderList() {
        return createArrayList(orderList.values());
    }

   /* public void addOrder(Order order) {    	

       orderList.put(itemId, order);
       
       incrementItemId();
    }

    public void removeCartItem(String itemId) {
        cartItems.remove(itemId);
    }

    public void setQuantity(String itemId, int quantity) {
        CartItem cartItem = cartItems.get(itemId);

        if (cartItem == null) {
            addCartItem(itemId);
            cartItem = cartItems.get(itemId);
        }

        cartItem.setQuantity(quantity);
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0");

        for (CartItem cartItem : getCartItemList()) {
            total = total.add(cartItem.getTotal());
        }

        return total;
    }
    
    public void incrementItemId() {
    	itemId++;
    }*/
}
