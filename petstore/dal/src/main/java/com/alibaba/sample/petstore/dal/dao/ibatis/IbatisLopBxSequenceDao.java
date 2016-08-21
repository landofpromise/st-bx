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

import static com.alibaba.citrus.util.Assert.*;

import com.alibaba.sample.petstore.dal.dao.LopBxSequenceDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxSequence;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisLopBxSequenceDao extends SqlMapClientDaoSupport implements LopBxSequenceDao {
    public synchronized int getNextId(String name) {    	
        LopBxSequence sequence = new LopBxSequence(name, -1);

        sequence = (LopBxSequence) getSqlMapClientTemplate().queryForObject("getSequence", sequence);

        assertNotNull(sequence, "unknown sequence name: {}", name);

        Object parameterObject = new LopBxSequence(name, sequence.getNextId() + 1);

        getSqlMapClientTemplate().update("updateSequence", parameterObject);

        return sequence.getNextId();
    }
    
    public synchronized int getId(String name)
    {    	
    	LopBxSequence sequence = new LopBxSequence(name, -1);

        sequence = (LopBxSequence) getSqlMapClientTemplate().queryForObject("getSequence", sequence);

        assertNotNull(sequence, "unknown sequence name: {}", name);
    	return sequence.getNextId();
    }
    
	@SuppressWarnings("deprecation")
	public synchronized void deleteSequence(LopBxSequence sequence) {
		getSqlMapClientTemplate().delete("deleteSequence", sequence);
	}

	@SuppressWarnings("deprecation")
	public synchronized void updateSequence(LopBxSequence sequence) {		
        assertNotNull(sequence, "unknown sequence name: {}", sequence.getName());

        getSqlMapClientTemplate().update("updateSequence", sequence);
	}
}
