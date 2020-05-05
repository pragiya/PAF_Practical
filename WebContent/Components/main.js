$(document).ready(function(){
	if ($("#alertSuccess").text().trim() == ""){
	 $("#alertSuccess").hide();
	}
	 $("#alertError").hide(); 
	
});


//==================Request Algorithm==========================
$(document).on("click", "#btnSave", function(event){
	
// Clear status msges-------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
 
 // Form validation----------------
 var status = validateItemForm();
 // If not valid-------------------
 if (status != true){
	 $("#alertError").text(status);
	 $("#alertError").show();
	 return;
 }
 
 //If valid------------------------
 var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
 
	 $.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
	 
});

//============Response Algorithm======================
function onItemSaveComplete(response, status){
	if (status == "success"){
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error"){
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event){
	
 $("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
 $("#paymentName").val($(this).closest("tr").find('td:eq(0)').text());
 $("#aptId").val($(this).closest("tr").find('td:eq(1)').text());
 $("#time").val($(this).closest("tr").find('td:eq(2)').text());
 $("#paymentDate").val($(this).closest("tr").find('td:eq(3)').text());
 $("#paymentAmount").val($(this).closest("tr").find('td:eq(4)').text());
 
});

//================================Delete=================
$(document).on("click", ".btnRemove", function(event) {
	
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "payId=" + $(this).data("payid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
	
});

function onItemDeleteComplete(response, status){
	if (status == "success"){
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Deleted Successfully.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error"){
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
 }
}

 
// CLIENTMODEL=========================================================================
function validateItemForm(){
	
	// NAME
	if ($("#paymentName").val().trim() == ""){
		return "Insert Patient Name.";
	}
	// Number
	if ($("#aptId").val().trim() == ""){
		return "Insert Appointment Number.";
	}
	// Time
	if ($("#time").val().trim() == "") {
		return "Insert Payment Time.";
	 
	}
	
	// Date
	if ($("#paymentDate").val().trim() == "") {
		return "Insert Payment Date.";

	}
	
	// Amount-------------------------------
	if ($("#paymentAmount").val().trim() == "") {
		return "Insert Payment Amount.";
	 
	}
	// is numerical value
	var tmpAmt = $("#paymentAmount").val().trim();
	if (!$.isNumeric(tmpAmt)){
		return "Insert a numerical value for Payment Amount.";
	 }
	
	return true; 
}