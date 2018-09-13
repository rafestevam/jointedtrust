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
	$("#aCustomize").removeClass("active");
	$("#customize").collapse('hide');
});

$("#aFiles").click(function(){
	$(this).addClass("active");
	$("#files").collapse('show');
	$("#aOverview").removeClass("active");
	$("#overview").collapse('hide');
	$("#aCustomize").removeClass("active");
	$("#customize").collapse('hide');
});

$("#aCustomize").click(function(){
	$(this).addClass("active");
	$("#customize").collapse('show');
	$("#aOverview").removeClass("active");
	$("#overview").collapse('hide');
	$("#aFiles").removeClass("active");
	$("#files").collapse('hide');
})