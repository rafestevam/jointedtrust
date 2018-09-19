/**
 * Customizations for Risk Info Page
 */
$(function(){
	$("#overview").collapse('show');
});

$("#aOverview").click(function(){
	$(this).addClass("active");
	$("#overview").collapse('show');
	$("#aFiles").removeClass("active");
	$("#files").collapse('hide');
	$("#aAssigned").removeClass("active");
	$("#assigned").collapse('hide');
});

$("#aFiles").click(function(){
	$(this).addClass("active");
	$("#files").collapse('show');
	$("#aOverview").removeClass("active");
	$("#overview").collapse('hide');
	$("#aAssigned").removeClass("active");
	$("#assigned").collapse('hide');
});

$("#aAssigned").click(function(){
	$(this).addClass("active");
	$("#assigned").collapse('show');
	$("#aOverview").removeClass("active");
	$("#overview").collapse('hide');
	$("#aFiles").removeClass("active");
	$("#files").collapse('hide');
});

//Opening modal for Files Attachment
$("#attachButton").on("click", function(e){
	$("#modalFiles").modal('show');
});

//Submitting values of Dropzone Form
$("#dzForm").on("submit", function(e){
	//Files Queue Processor for Dropzone
	var myDropzone = Dropzone.forElement("#dzFiles");
	if(myDropzone.files.length > 0){
		e.stopPropagation();
		e.preventDefault();
		myDropzone.processQueue();
	}	
});

//Callback events for Dropzone
var myDropzone = Dropzone.forElement("#dzFiles");
myDropzone.on("sendingmultiple", function(file, obj, formData){
	console.log("SENDING EVENT");
	debugger;
	var formDataArray = $("#dzForm").serializeArray();
	for(var i = 0; i < formDataArray.length; i++){
		var formDataItem = formDataArray[i];
		formData.append(formDataItem.name, formDataItem.value);
	}
	for(var j = 0; j < file.length; j++){
		formData.append("file", file[j]);
	}
});
myDropzone.on("success", function(data){
	console.log("SUCCESS...");
	var responseMsg = JSON.parse(data.xhr.responseText)
	$("#responseMessage").text(responseMsg.successMessage[0]);
	$("#responseMessage").addClass("alert-success");
	$("#responseMessageTitle").text(responseMsg.successTitle[0]);
	$("#modalFiles").modal('hide');
	$("#modalMessage").modal("show");
	//$(location).attr('href', returnAddr);
});

//Reloading page on Modal Hidden
$("#modalMessage").on("hidden.bs.modal", function(e){
	location.reload(true);
});