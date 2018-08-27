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
})

//Submitting values of Quill areas to the related hidden textarea fields
$("#riskForm").on("submit", function(){
	$("#description").val($("#quillDescription").html());
	$("#cause").val($("#quillCause").html());
	$("#consequence").val($("#quillConsequence").html());
});