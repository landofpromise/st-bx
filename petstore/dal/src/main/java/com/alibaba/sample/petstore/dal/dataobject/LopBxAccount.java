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

import static java.util.Calendar.*;

import java.util.Calendar;
import java.util.Date;

public class LopBxAccount {
    private final LopBxUser   user;
    private       String userId;
    private       String email;
    private       String name;
    private 	  String leaderName;
    private       String phone;
    private       String alipayAccount;    

    public LopBxAccount(LopBxUser user) {
        this.user = user;
    }

    public LopBxUser getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    
    public String getAlipayAccount() {
        return alipayAccount;
    }
    
    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }
   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
 
   /* public int getCreditCardExpiryMonth() {
        return getCreditCardExpiryField(MONTH);
    }

    public void setCreditCardExpiryMonth(int month) {
        setCreditCardExpiryField(MONTH, month);
    }

    public int getCreditCardExpiryYear() {
        return getCreditCardExpiryField(YEAR);
    }

    public void setCreditCardExpiryYear(int year) {
        setCreditCardExpiryField(YEAR, year);
    }

    private int getCreditCardExpiryField(int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        if (creditCardExpiry != null) {
            calendar.setTime(creditCardExpiry);
        }

        switch (field) {
            case YEAR:
                return calendar.get(YEAR);

            case MONTH:
                return calendar.get(MONTH) + 1;

            default:
                throw new UnsupportedOperationException("unknown field #" + field);
        }
    }

    private void setCreditCardExpiryField(int field, int value) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        if (creditCardExpiry != null) {
            calendar.setTime(creditCardExpiry);
        }

        int year = calendar.get(YEAR);
        int month = calendar.get(MONTH) + 1;

        switch (field) {
            case YEAR:
                year = value;
                break;

            case MONTH:
                month = value;
                break;

            default:
                throw new UnsupportedOperationException("unknown field #" + field);
        }

        calendar.set(YEAR, year);
        calendar.set(MONTH, month - 1);

        creditCardExpiry = calendar.getTime();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }*/
}
