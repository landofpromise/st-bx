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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;

public class LopBxFinanceAnalysis {	
	private String      totalNum;			//应报人数：应该报销的总人数，即数据库中用户总数
    private String      knownNum;			//已报人数：已经报销的人数；
    private String     	sumOfMoney;  		//总金额：财务已经结算的总金额；
    private String     	averageMoney; 		//平均金额：平均每人每天的花销（在已结算的数据中统计的）；
    private String     	averageBreakfast; 	//早餐平均金额：平均每人每天早餐的花销（在已结算的数据中统计的）；
    private String     	averageLunch;		//午餐平均金额：平均每人每天午餐的花销（在已结算的数据中统计的）；
    private String     	averageDinner;		//晚餐平均金额：平均每人每天晚餐的花销（在已结算的数据中统计的）；
    private String     	averageDrink;		//饮料平均金额：平均每人每天饮料的花销（在已结算的数据中统计的）；
    private String     	averageCarfare;		//交通平均金额：平均每人每天交通的花销（在已结算的数据中统计的）；
    private String     	averageOther;		//其他平均金额：平均每人每天其他的花销（在已结算的数据中统计的）；
    
    public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getKnownNum() {
		return knownNum;
	}
	public void setKnownNum(String knownNum) {
		this.knownNum = knownNum;
	}
	public String getSumOfMoney() {
		return sumOfMoney;
	}
	public void setSumOfMoney(String sumOfMoney) {
		this.sumOfMoney = sumOfMoney;
	}
	public String getAverageMoney() {
		return averageMoney;
	}
	public void setAverageMoney(String averageMoney) {
		this.averageMoney = averageMoney;
	}
	public String getAverageBreakfast() {
		return averageBreakfast;
	}
	public void setAverageBreakfast(String averageBreakfast) {
		this.averageBreakfast = averageBreakfast;
	}
	public String getAverageLunch() {
		return averageLunch;
	}
	public void setAverageLunch(String averageLunch) {
		this.averageLunch = averageLunch;
	}
	public String getAverageDinner() {
		return averageDinner;
	}
	public void setAverageDinner(String averageDinner) {
		this.averageDinner = averageDinner;
	}
	public String getAverageDrink() {
		return averageDrink;
	}
	public void setAverageDrink(String averageDrink) {
		this.averageDrink = averageDrink;
	}
	public String getAverageCarfare() {
		return averageCarfare;
	}
	public void setAverageCarfare(String averageCarfare) {
		this.averageCarfare = averageCarfare;
	}
	public String getAverageOther() {
		return averageOther;
	}
	public void setAverageOther(String averageOther) {
		this.averageOther = averageOther;
	}		
}
