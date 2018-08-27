/**
 * Configurations for Risk Files Dropzone
 */

Dropzone.options.riskform = {
	autoProcessQueue: false,
	
	init: function() {
	    this.on("addedfile", function(file) { alert("Added file."); });
	  }
	
//	init: function(){
//		debugger;
//		var myDropzone = this;
//		var myForm = $(this.element).closest('#riskForm');
//		
//		myForm.on('submit', function(e){
//			e.preventDefault();
//			myDropzone.processQueue();
//		});
//	}
}

