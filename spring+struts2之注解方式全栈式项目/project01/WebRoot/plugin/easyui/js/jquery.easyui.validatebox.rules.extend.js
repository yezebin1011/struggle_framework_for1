  
 $(function(){
	 
	 $.extend($.fn.validatebox.defaults.rules,{
		 
		 repassword : {
			 
			 validator : function(value,param){
				 
				   return value == $(param[0]).val();
				 
			 },
	 
	         message : '重复密码与输入密码不一致'
			 
		 },
		 
		 radio : {
			 
			 validator : function(value,param){
				 
				 var form = param[0];
				 
				 var radioName = param[1];
				 
				 var result = false;
				 
				 $("#"+form+" input[type= 'radio'][name='"+radioName+"']").each(function(){
					 
					 if(this.checked){
						 
						 result = true;
						 
						 return false;
					 }
					 
				 });
				 
				 return result;
			 },
			 
			 message : "需要选择一项"
		 }
		  
	  });
	 
 });
