function getValuesFromInputs(inputs) {
	var arr = new Array();
	for (var i = 0; i < inputs.length; i++) {
		arr.push(inputs[i].value);
	}
	return arr;
}
function getIntegersFromInputs(inputs) {
	var arr = new Array();
	for (var i = 0; i < inputs.length; i++) {
		arr.push(parseInt(inputs[i].value));
	}
	return arr;
}
function queryGroup() {
	var param = {
		"requestType" : "0"
	}
	$.ajax({
		type : "POST",
		url : "wf/main/activity/query",
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			console.log(result);
			qq = result;
			$("#activityList").html(result);
//			$("#choose").html(result);
		},
		error : function() {
			console.log("error");
		}
	});
}