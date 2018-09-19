/**
 * Customizations for Risk Batch Page
 */

//Callback events for Dropzone
var myDropzone = Dropzone.forElement("#dzFiles");
myDropzone.on("sending", function(file, obj, formData){
	debugger;
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
	$("#modalMessage").modal("show");
});

//Redirecting page on Modal Hidden
$("#modalMessage").on("hidden.bs.modal", function(e){
	$(location).attr('href', returnAddr);
});

//Submitting Form Event
$("#batchForm").on("submit", function(e){
	debugger;
	var myDropzone = Dropzone.forElement("#dzFiles");
	if(myDropzone.files.length > 0){
		e.stopPropagation();
		e.preventDefault();
		myDropzone.processQueue();
	}
});