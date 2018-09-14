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
})