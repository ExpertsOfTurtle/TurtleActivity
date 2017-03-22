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
			// $("#choose").html(result);
		},
		error : function() {
			console.log("error");
		}
	});
}
function insertSudokuActivity() {
	var hour = $("#hour").val();
	var minute = $("#minute").val();
	var second = $("#second").val();
	if (hour == "")
		hour = "0";
	if (minute == "")
		minute = "0";
	if (second == "")
		second = "0";
	hour = parseInt(hour);
	minute = parseInt(minute);
	second = parseInt(second);
	second += hour * 3600 + minute * 60;
	var param = {
		"requestType" : "0",
		"type" : $("#sudokuType select").val(),
		"username" : $("#username").val(),
		"second" : second
	}

	$.ajax({
		type : "POST",
		url : "wf/main/activity/sudoku",
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			console.log(result);
			qq = result;

		},
		error : function() {
			console.log("error");
		}
	});
	$("#hour").val("");
	$("#minute").val("");
	$("#second").val("");
}
function changeActivityType() {
	var val = $("#activityType").val();
	if (val == "SUDOKU") {
		$("#sudokuType").show();
		$("#time_hour").show();
		$("#time_minute").show();
		$("#time_second").show();
	} else if (val == "CF") {
		$("#sudokuType").hide();
		$("#time_hour").hide();
		$("#time_minute").hide();
		$("#time_second").hide();
	}
}