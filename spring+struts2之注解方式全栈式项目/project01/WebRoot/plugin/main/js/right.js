  
   $(function(){
	   
	   var mainFrameWindow = window.top.document.getElementById("mainFrame").contentWindow;
	   
	   var loginOutLink = mainFrameWindow.document.getElementById("loginOutLink");
	   
	   loginOutLink.onclick = function(){
		   
		   $.messager.confirm('退出提示','确定退出系统吗？',function(r){
			   
			   if(r){
				   
				   top.location.href = 'j_spring_security_logout';
				   
			   }else{
				   
				   return;
			   }
			   
		   });
		   
	   };
	   
   });