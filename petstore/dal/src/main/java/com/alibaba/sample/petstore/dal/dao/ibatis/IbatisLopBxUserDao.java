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

import static com.alibaba.citrus.util.StringUtil.*;

import java.util.List;

import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;
import com.alibaba.sample.petstore.dal.dataobject.LopBxAccount;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisLopBxUserDao extends SqlMapClientDaoSupport implements LopBxUserDao {
    public LopBxUser getUserById(String userId) {
        return (LopBxUser) getSqlMapClientTemplate().queryForObject("getUserByUserId", userId);
    }

    public LopBxUser getAuthenticatedUser(String userId, String password) {
        LopBxUser user = new LopBxUser();

        user.setUserId(userId);
        user.setPassword(password);

        return (LopBxUser) getSqlMapClientTemplate().queryForObject("getUserByUserIdAndPassword", user);
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserIdList() {
        return getSqlMapClientTemplate().queryForList("getUserIdList", null);
    }
    
    @SuppressWarnings("unchecked")
    public List<LopBxUser> getLeaderListByRole(String role) {
    	String isDzz = parseRole2Int(role) + "";
    	
        return getSqlMapClientTemplate().queryForList("getLeaderListByRole", isDzz);
    }
    
    private int parseRole2Int(String role){
    	if("stmember".equalsIgnoreCase(role)){
    		return 0;
    	}else if("headman".equalsIgnoreCase(role)){
    		return 1;
    	}else{
    		return 2;
    	}
    }

    public void insertUser(LopBxUser user) {
        getSqlMapClientTemplate().update("insertUser", user);
        getSqlMapClientTemplate().update("insertAccount", user);        
    }

    public void updateUser(LopBxUser user) {
        getSqlMapClientTemplate().update("updateAccount", user);       

        if (!isEmpty(user.getPassword())) {
            getSqlMapClientTemplate().update("updateUser", user);
        }
    }

	public List<LopBxUser> getUsersByName(String name) {
		return getSqlMapClientTemplate().queryForList("getUsersByName", name);
	}
}
