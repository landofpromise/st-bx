var fieldIdArray = ["breakfastInput", "lunchInput", "dinnerInput", "drinkInput", "carfareInput", "otherInput", "commentArea"];

var fieldNameArray = ["早餐", "午餐", "晚餐", "饮料", "交通", "其他", "备注"];

function validate(){
	var slt=document.getElementById("dateSelect");
	if(slt != null && slt.value==""){
		alert("日期不能为空");
		return false;
	}
	
	
	var rs = validateRequired();
	if(!rs)
		return false;

	rs = validateIsNumber();

	if(!rs)
		return false;

	rs = validateTotal();
	if(!rs)
		return false;
	
	return true;
}

function validateRequired(){
	var errorMsg = null;
	for(var i=0;i<fieldIdArray.length;i++){
		var fieldId = fieldIdArray[i];
		var value = getFieldFloatValueById(fieldId);
		if(value == null){
			errorMsg = fieldNameArray[i] + "必须填写";
			alert(errorMsg);
			return false;
		}
	}

	return true;	
}

function validateIsNumber(){
	for(var i=0;i<6;i++){
		var fieldId = fieldIdArray[i];
		var value = getFieldFloatValueById(fieldId);
		if(isNaN(value)){
			errorMsg = fieldNameArray[i] + "必须是数字";
			alert(errorMsg);
			return false;
		}
	}

	return true;
}

function validateTotal(){
	var totalValue = 0;
	for(var i=0;i<5;i++){
		var fieldId = fieldIdArray[i];
		var value = getFieldFloatValueById(fieldId);
		if(value == null){
			value = 0;
		}

		totalValue += value;
	}

	if(totalValue > 80){
		alert("总数不能超过80元");
		return false;
	}

	return true;
}

function getFieldValueById(id){
	var elem = document.getElementById(id);
	var value = elem.value;
	
	return value;
}

function getFieldFloatValueById(id){
	var value = getFieldValueById(id);
    if(!value)
       return null;

    var floatValue = parseFloat(value);
	
	return floatValue;
}