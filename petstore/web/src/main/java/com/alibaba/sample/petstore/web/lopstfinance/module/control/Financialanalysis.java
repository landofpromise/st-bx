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

package com.alibaba.sample.petstore.web.lopstfinance.module.control;

import static com.alibaba.citrus.util.CollectionUtil.createLinkedList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Form;
import com.alibaba.citrus.service.form.FormService;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dao.LopBxOrderDao;
import com.alibaba.sample.petstore.dal.dao.LopBxUserDao;
import com.alibaba.sample.petstore.dal.dataobject.LopBxAccount;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.LopBxFinanceAnalysis;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrder;
import com.alibaba.sample.petstore.dal.dataobject.LopBxOrderDto;
import com.alibaba.sample.petstore.dal.dataobject.LopBxUser;

import org.springframework.beans.factory.annotation.Autowired;

public class Financialanalysis {
    @Autowired
    private StoreManager storeManager;
    @Autowired
    private LopBxUserDao userDao;    
    @Autowired
    private LopBxOrderDao orderDao;
    @Autowired
    private FormService formService; 
    
    public void execute(HttpSession session, Context context) throws Exception {
    	
    	/*
    	orderDtoList = orderToOrderDto(orders, userList, userIndex);
    	
    	//未结算放前边，已结算放后边
    	Comparator<LopBxOrderDto> comparator = new Comparator<LopBxOrderDto>() {
            public int compare(LopBxOrderDto o1, LopBxOrderDto o2) {
                // TODO Auto-generated method stub
                return ~o1.getStatus().compareTo(o2.getStatus());
            }
        };
    	Collections.sort(orderDtoList, comparator);
    	
    	
    	
    	try 
    	{
	    	Connection connect = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/lop_mysql-schema-v11","root","admin");
	    	Statement stmt = connect.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) totalCount FROM lop_bx_orders");
	        if(rs.next())
	        {
	        	int i = rs.getInt("totalCount");
	        	
		        i = 0;
	        }	        
    	}
    	catch (Exception e) 
    	{
            System.out.print("get data error!");
            e.printStackTrace();
        }
    	context.put("orders", orderDtoList);
    	context.put("leader", orderDto);*/
    	int      		totalNum = 0;			//应报人数：应该报销的总人数，即数据库中用户总数
        int      		knownNum = 0;			//已报人数：已经报销的人数；
        BigDecimal     	sumOfMoney = new BigDecimal(0.00);  		//总金额：财务已经结算的总金额；
        BigDecimal     	averageMoney = new BigDecimal(0.00); 		//平均金额：平均每人每天的花销（在已结算的数据中统计的）；
        BigDecimal     	averageBreakfast = new BigDecimal(0.00); 	//早餐平均金额：平均每人每天早餐的花销（在已结算的数据中统计的）；
        BigDecimal     	averageLunch = new BigDecimal(0.00);		//午餐平均金额：平均每人每天午餐的花销（在已结算的数据中统计的）；
        BigDecimal     	averageDinner = new BigDecimal(0.00);		//晚餐平均金额：平均每人每天晚餐的花销（在已结算的数据中统计的）；
        BigDecimal     	averageDrink = new BigDecimal(0.00);		//饮料平均金额：平均每人每天饮料的花销（在已结算的数据中统计的）；
        BigDecimal     	averageCarfare = new BigDecimal(0.00);		//交通平均金额：平均每人每天交通的花销（在已结算的数据中统计的）；
        BigDecimal     	averageOther = new BigDecimal(0.00);		//其他平均金额：平均每人每天其他的花销（在已结算的数据中统计的）；

        List<LopBxOrderDto> orderDtoList = createLinkedList();
    	List<LopBxOrder> orders = createLinkedList();     	
    	List<LopBxOrder> orderList = createLinkedList(); 
    	LopBxOrder order = new LopBxOrder();     	
    	
    	LopBxFinanceAnalysis financeAnalysis = new LopBxFinanceAnalysis();
    	//List<LopBxOrder> orders = orderDao.getOrdersByHeadmanIdAndLeader(order);    	
    	
    	orderList = orderDao.getOrdersByStatus("已审核");   
    	knownNum = orderList.size();					//已报人数：已经报销的人数；
    	if(orderList.size()>0)
    	{
    		for(int j=0;j<orderList.size();j++)
    		{
    			order = orderList.get(j);			
    			sumOfMoney = order.getSubprice().add(sumOfMoney);  		//总金额：财务已经结算的总金额；
    	        //averageMoney.add(order); 		//平均金额：平均每人每天的花销（在已结算的数据中统计的）；
    			averageBreakfast = order.getBreakfast().add(averageBreakfast); 	//早餐平均金额：平均每人每天早餐的花销（在已结算的数据中统计的）；
    			averageLunch = order.getLunch().add(averageLunch);		//午餐平均金额：平均每人每天午餐的花销（在已结算的数据中统计的）；
    			averageDinner = order.getDinner().add(averageDinner);		//晚餐平均金额：平均每人每天晚餐的花销（在已结算的数据中统计的）；
    			averageDrink = order.getDrink().add(averageDrink);		//饮料平均金额：平均每人每天饮料的花销（在已结算的数据中统计的）；
    			averageCarfare = order.getCarfare().add(averageCarfare);		//交通平均金额：平均每人每天交通的花销（在已结算的数据中统计的）；
    			averageOther = order.getOther().add(averageOther);			   			
    		}      		
    		averageMoney = sumOfMoney.divide(new BigDecimal(knownNum));//平均金额：平均每人每天的花销（在已结算的数据中统计的）；
    		averageBreakfast = averageBreakfast.divide(new BigDecimal(knownNum));
    		averageLunch = averageLunch.divide(new BigDecimal(knownNum));
    		averageDinner = averageDinner.divide(new BigDecimal(knownNum));
    		averageDrink = averageDrink.divide(new BigDecimal(knownNum));
    		averageCarfare = averageCarfare.divide(new BigDecimal(knownNum));
    		averageOther = averageOther.divide(new BigDecimal(knownNum));
    	}
    	
        try 
    	{
        	//LOP_BX_USER
	    	/*Connection connect = DriverManager.getConnection(
	                "jdbc:mysql://127.0.0.1:3306/test","root","lovelopy");*/
        	Connection connect = DriverManager.getConnection(
	                "jdbc:mysql://121.41.230.253:3306/zp20v1_db","st2015","lovelop2015");
	    	Statement stmt = connect.createStatement();
	    	
	    	//计算应报人数
	    	ResultSet rs = stmt.executeQuery("SELECT COUNT(*) totalNum FROM st2016 where come=1");
	        if(rs.next())
	        {
	        	totalNum = rs.getInt("totalNum");  
	        }	
	          
    	}
    	catch (Exception e) 
    	{
            System.out.print("get data error!");
            e.printStackTrace();
        }
        
        financeAnalysis.setTotalNum(Integer.toString(totalNum));
        financeAnalysis.setKnownNum(Integer.toString(knownNum));        
        financeAnalysis.setSumOfMoney(sumOfMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageMoney(averageMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageBreakfast(averageBreakfast.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageLunch(averageLunch.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageDinner(averageDinner.setScale(2, BigDecimal.ROUND_HALF_UP).toString());        
        financeAnalysis.setAverageDrink(averageDrink.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageCarfare(averageCarfare.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        financeAnalysis.setAverageOther(averageOther.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
       
        
        context.put("financeAnal", financeAnalysis);
    }    
}
