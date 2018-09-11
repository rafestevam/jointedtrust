/**
 * Customizations for Risk Form
 */
$(function(){
	$("#formBasic").collapse('show');		
});

//Hiding other containers when FormBasic is showed
$("#aFormBasic").click(function(){
	$(this).addClass("active");
	$("#formCustom").collapse('hide');
	$("#aFormCustom").removeClass("active");
	$("#formDocument").collapse('hide');
	$("#aFormDocument").removeClass("active");
});

//Hiding other containers when FormCustom is showed
$("#aFormCustom").click(function(){
	$(this).addClass("active");
	$("#formBasic").collapse('hide');
	$("#aFormBasic").removeClass("active");
	$("#formDocument").collapse('hide');
	$("#aFormDocument").removeClass("active");
});

//Hiding other containers when FormDocument is showed
$("#aFormDocument").click(function(){
	$(this).addClass("active");
	$("#formBasic").collapse('hide');
	$("#aFormBasic").removeClass("active");
	$("#formCustom").collapse('hide');
	$("#aFormCustom").removeClass("active");
});

//Callback events for Dropzone
var myDropzone = Dropzone.forElement("#dzFiles");
myDropzone.on("sendingmultiple", function(file, obj, formData){
	console.log("SENDING EVENT");
	debugger;
	var formDataArray = $("#riskForm").serializeArray();
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
	$("#modalMessage").modal("show");
	//$(location).attr('href', returnAddr);
});
myDropzone.on("error", function(data, msg){
	console.log("ERROR");
	var responseMsg = JSON.parse(data.xhr.responseText);
	
	//Form Validations Feedback
	Object.keys(responseMsg).forEach(function(k){
		var field = "#" + k;
		var eField = "#e_" + k;
		$(field).addClass("is-invalid");
		$(eField).text(responseMsg[k][0]);
	});
	
	myDropzone.removeAllFiles(true);	
	
	$("#formBasic").collapse('show');
	$("#aFormBasic").addClass("active");
	$("#formCustom").collapse('hide');
	$("#aFormCustom").removeClass("active");
	$("#formDocument").collapse('hide');
	$("#aFormDocument").removeClass("active");
	
//	//Form Validations Feedback
//	$("#risk_id").addClass("is-invalid");
//	$("#e_risk_id").text("ERROR");
});

//Redirecting page on Modal Hidden
$("#modalMessage").on("hidden.bs.modal", function(e){
	$(location).attr('href', returnAddr);
});

//Submitting values of Quill areas to the related hidden textarea fields
$("#riskForm").on("submit", function(e){
	$("#description").val($("#quillDescription").html());
	$("#cause").val($("#quillCause").html());
	$("#consequence").val($("#quillConsequence").html());

	//Files Queue Processor for Dropzone
	var myDropzone = Dropzone.forElement("#dzFiles");
	if(myDropzone.files.length > 0){
		e.stopPropagation();
		e.preventDefault();
		myDropzone.processQueue();
	}	
});

