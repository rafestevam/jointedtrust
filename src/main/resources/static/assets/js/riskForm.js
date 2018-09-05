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
myDropzone.on("sending", function(file, obj, formData){
	console.log("SENDING EVENT");
	var formDataArray = $("#riskForm").serializeArray();
	for(var i = 0; i < formDataArray.length; i++){
		var formDataItem = formDataArray[i];
		formData.append(formDataItem.name, formDataItem.value);
	}
});
myDropzone.on("success", function(data){
	console.log("REDIRECTING...");
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