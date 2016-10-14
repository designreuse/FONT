(function($){
var snakerflow = $.snakerflow;
function splitUsersAndAccounts( userNamesAndAccount )
{	
	var userNames = "";
	var accounts = "";

	var array = userNamesAndAccount.split( ";" );
	for(i=0; i<array.length; i++)
	{
		var temp = splitUserNameAndAccount(array[i]);
		userNames += temp[0] + ",";
		accounts += temp[1] + ",";
	}
	userNames = userNames.substr(0, userNames.length - 1);
	accounts = accounts.substr(0, accounts.length - 1);
	var result = new Array(2);
	result[0] = userNames;
	result[1] = accounts;
	return result;
}

function splitUserNameAndAccount( userNameAndAccount )
{
	var temp = new Array(2);
	if(userNameAndAccount.indexOf( "(" ) != -1)
	{
		temp[0] = userNameAndAccount.substring( 0,
      	userNameAndAccount.indexOf( "(" ) );
    	temp[1] = userNameAndAccount.substring( userNameAndAccount.indexOf( "(" ) + 1,
        userNameAndAccount.indexOf( ")" ) );
    }
    else
    {
    	temp[0] = userNameAndAccount;
    	temp[1] = userNameAndAccount;
    }
    return temp;
}

$.extend(true, snakerflow.editors, {
	inputEditor : function(){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			
			$('<input style="width:98%;"/>').val(props[_k].value).change(function(){
				props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	},
	selectEditor : function(arg){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;

			var sle = $('<select  style="width:99%;"/>').val(props[_k].value).change(function(){
				props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			
			if(typeof arg === 'string'){
				$.ajax({
				   type: "GET",
				   url: arg,
				   success: function(data){
					  var opts = eval(data);
					 if(opts && opts.length){
						for(var idx=0; idx<opts.length; idx++){
							sle.append('<option value="'+opts[idx].value+'">'+opts[idx].name+'</option>');
						}
						sle.val(_props[_k].value);
					 }
				   }
				});
			}else {
				for(var idx=0; idx<arg.length; idx++){
					sle.append('<option value="'+arg[idx].value+'">'+arg[idx].name+'</option>');
				}
				sle.val(_props[_k].value);
			}
			
			$('#'+_div).data('editor', this);
		};
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		};
	},
	assigneeEditor : function(arg){//添加参与人.[添加Tree]
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;

			$('<input style="width:88%;" readonly="true" id="dialogEditor"/>').val(props[_k].value).appendTo('#'+_div);
			$('<input style="width:10%;" type="button" value="选择"/>').click(function(){
//				alert("选择:" + snakerflow.config.ctxPath + arg);
				var element = document.getElementById("dialogEditor");
				var l  = window.showModalDialog(snakerflow.config.ctxPath + arg," ","dialogWidth:800px;dialogHeight:540px;center:yes;scrolling:yes");
				if (l == null )
					return;							
				var result = splitUsersAndAccounts(l);
				console.log(result[1]+"---------------"+result[0]);
				element.title = result[1];//id
				element.value = result[1];//id
				props[_k].value = result[1];//id
				props['assignee'].value = result[0];//名称
			}).appendTo('#'+_div);

			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			//
		}
	}
});

})(jQuery);