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

package com.alibaba.sample.petstore.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

import org.springframework.beans.factory.annotation.Autowired;

public class Account {
    @Autowired
    private UserManager userManager;    

    public void execute(Context context, @Param(name="userType") String usertype) throws Exception {
        int usertp = 0;
    	if(null == usertype)
        {
    		usertp = 0;
        }	
    	else if(usertype.equals("member"))
    	{
    		usertp = 1;
    	}
    	else if(usertype.equals("headman"))
    	{
    		usertp = 2;
    	}
    	else if(usertype.equals("finance"))
    	{
    		usertp = 3;
    	}
    	LopBxUser user = userManager.getUser(PetstoreUser.getCurrentUser().getId());
        context.put("user", user);
        context.put("userType", usertp);
    }
}
