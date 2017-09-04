$(document).ready(function() {

	$('#check_all').on('click',function(){
		$("input[name='checkbox_list']").prop("checked", $(this).is(":checked"));
	});
	
	$('.order_by').on('click',function(event){
        basicOper.order_by(event.target);
    });
    
    $("#confirm_delete").on('click', function(event){
        var delete_url = $('#delete_url').attr('data-url');
        basicOper.delete_by_ids(delete_url);
    });
    
    $(".modify_one").on('click',function(event){
    	basicOper.show_modify_modal(event.target);
    });

    $(".modify_trade_item").on('click',function(event){
        basicOper.show_modify_trade_item_modal(event.target);
    });

    $(".modify_trade_list").on('click',function(event){
        var ids = new Array();
        var dbNum = $('#query_db_num option:selected').val();
        var tbNum = $('#query_table_num option:selected').val();
        $("input:checkbox[name='checkbox_list']:checked").each(function() {
            ids.push($(this).val());
        });
        if(ids.length == 0){
            basicOper.warning('请选择任务');
            return;
        }
        basicOper.show_modify_trade_list_modal(event.target, ids, dbNum, tbNum);
    });

	$(".modify_normaltrade_item").on('click',function(event){
		basicOper.show_modify_normal_item_modal(event.target);
	});

    $(".modify_one_js").on('click',function(event){
    	basicOper.show_modify_modal_js(event.target);
    });

    $(".modify_cache").on('click',function(event){
        basicOper.modify_cache_modal(event.target);
    });

    $(".delete_cache_by_key").on('click',function(event){
        basicOper.delete_cache_modal(event.target);
    });

});

var basicOper = {
	
	/**
	 * 显示表单错误
	 */
	error_help : function(id, msg){
		var target = $("#"+id+"_control .help-inline");
		if(!target.hasClass('error')){
			target.attr('data', target.text());
		}
		target.html(msg);
		target.addClass('error');
		target = $("#"+id);
		target.focus();
	},
	
	/**
	 * 显示表单错误
	 */
	error : function(target, msg){
		$(target).show();
		$(target).find('span').html(msg);
		window.location.href = "#form_error";
	},
	
	/**
	 * 消除表单错误提示
	 */
	success : function(target){
		$(target).hide();
	},
	
	/**
	 * 消除表单错误提示
	 */
	success_help : function(id){
		var target = $("#"+id+"_control .help-inline");
		var msg = target.attr('data');
		if(msg != undefined && msg != null){
			target.html(msg);
		}else{
			if(target.hasClass('error')){
				target.html('');
			}
		}
		target.removeClass('error');
	},
	

    /**
     * 获取字符串真实长度
     */
    get_length : function(str){
    	if(str == undefined || str == null){
    		return 0;
    	}
        return str.replace(/[^\u0000-\u007f]/g,"**").replace(/[\r\n,\n]/g,"**").length;
    },
    
    /**
     * 往url的queryString添加参数
     */
    url_search : function(url, query){

    	question = '';
    	sharp = '';
    	
    	urls0 = url.split('#');
    	urls1 = urls0[0].split('?');
    	
    	if(urls0[1] == undefined || urls0[1] == ''){
    		sharp = '';
    	}else{
    		sharp = '#' +  urls0[1];
    	}
    	
    	if(urls1[1] == undefined || urls1[1] == ''){
    		question = '?' + query;
    	}else{
    		question = '?' + urls1[1] + '&' + query;
    	}
    	return urls1[0] + question + sharp;
    },
    
    /**
     * 翻页
     */
    page : function(query){
    	search = document.location.search;
    	search = search.replace(/\?page=[^&]*&?/gm,'?').replace(/&page=[^&]*/gm,'');
    	document.location.href = basicOper.url_search(search, query);
    },
    
    /**
     * 提醒
     */
    alert : function(str, url, time){
    	if(time == null || time == undefined){time=3;}
    	var sec = time;
    	time=time*1000;
    	$('#alert_pop').modal("show");
    	$('#alert_text').html(str);
    	for(var i=sec;i>0;i--) { 
            window.setTimeout("basicOper.update_sec(" + i + ")", (sec-i) * 1000); 
        } 
    	var is_redirect = $('#is_not_redirect').attr('data') == 'false';
    	if(url!=null && url !=undefined && url !="null"){
    		window.setTimeout(function(){
    			if(is_redirect){ 
    				var reg = new RegExp("[?&]random=[^&#]*","i");
    				url = url.replace(reg,'');
    				var uris = url.split('?');
    				var uris1 = url.split('#');
    				if(uris1.length > 1){
    					if(uris.length > 1){
    						window.location.href = url.split('#')[0] + '&random=' + Math.random() + '#' + url.split('#')[1];
    					}else{
    						window.location.href = url.split('#')[0] + '?random=' + Math.random() + '#' + url.split('#')[1];
    					}
    				}else{
    					window.location.href = url
    				}
    			}else{
    				$('#alert_pop').modal("hide");
    			}
    		}, time);
    	} else {
    		window.setTimeout(function(){
    			$('#alert_pop').modal("hide");
    		}, time);
    	}
    	
    },

    /**
     * 警告
     */
    warning : function(str, url, time){
    	var sec = time!=null || time !=undefined?time:3;
    	if(time!=null || time !=undefined){time=time*1000;}else{time=2500;}
    	$('#warning_pop').modal("show");
    	$('#warning_text').html(str);
    	for(var i=sec;i>0;i--) { 
            window.setTimeout("basicOper.update_sec(" + i + ")", (sec-i) * 1000); 
        } 
    	var is_redirect = $('#is_not_redirect').attr('data') == 'false';
    	if(url!=null && url !=undefined){
    		window.setTimeout(function(){
    			if(is_redirect){
    				window.location.href = url;
    			}else{
    				$('#warning_pop').modal("hide");
    			}
    		}, time);
    	} else {
    		window.setTimeout(function(){
    			$('#warning_pop').modal("hide");
    		}, time);
    	}
        
        
    },
    
    update_sec : function(num){
    	$("#redirect_sec").html(num);
    },
    
    delete_by_ids : function(url){
        $.ajax({
            type : "post",
            url : url,
            data : $('#delete_form').serialize(),
            async : false,
            dataType : 'json',
            success : function(data){
            	$('#deleteModal').modal('hide');
                if(!data.is_success){
                    basicOper.alert(data.msg);
                }else{
                	basicOper.alert(data.msg + "10秒后刷新页面", window.location.href, 10);
                }
            }
        });
    },
    
    order_by : function(target){
        var order_by_value = $(target).attr('data');
        var raw_value = $('#order_by_value').attr('value');
        $('#order_by_value').attr('value', order_by_value);
        var order_by_type = $('#order_by_type').attr('value');
        if(order_by_value == raw_value && order_by_type == 'desc'){
            $(target).removeClass("desc");
            $('#order_by_type').attr('value', 'asc');
        }else{
            $(target).addClass("desc");
            $('#order_by_type').attr('value', 'desc');
        }
        query_form.action = window.location.href;
        //提交表单
		setTimeout(function(){$("#query_form").submit();},0);
    },

    // 获取网页内容
    get_html : function(url) {
        var data = $.ajax({
            url: url,
            async: false
        }).responseText;
        return data;
    },
    
    pop : function(url, modal_id){
        var html = basicOper.get_html(url);
        $('#pop_here').append(html);
        $(modal_id).modal('show');
    },
    
    show_modify_modal : function(target){
        var id = $(target).attr('data-id');
        var url = $(target).attr('data-url');
    	$('#modifyModal').remove();
    	$('.modal-backdrop').remove();
        basicOper.pop(url + id, '#modifyModal');
    },

    show_modify_trade_item_modal : function(target){
        var id = $(target).attr('data-id');
        var dbNum = $(target).attr('data-dnum');
        var tbNum = $(target).attr('data-tnum');
        var url = $(target).attr('data-url');
        $('#modifyModal').remove();
        $('.modal-backdrop').remove();
        basicOper.pop(url + id + "/" + dbNum + "/" + tbNum, '#modifyModal');
    },

    show_modify_trade_list_modal: function(target, ids, dbNum, tbNum){
        $('#modifyModal').remove();
        $('.modal-backdrop').remove();
        var url = $(target).attr('data-url');
        basicOper.pop(url + ids + "/" + dbNum + "/" + tbNum, '#modifyModal');
    },

	show_modify_normal_item_modal : function(target){
		var id = $(target).attr('data-id');
		// var application=$(target).attr('data-app');
        var application = $('#select_appname option:selected') .val();

        var tbNum = $(target).attr('data-tnum');
		var url = $(target).attr('data-url');
		$('#modifyModal').remove();
		$('.modal-backdrop').remove();
		basicOper.pop(url + id + "/" +application+"/"+tbNum, '#modifyModal');
	},

    modify_cache_modal : function(target){
        var key = $('#query_cache_key').val();
        var url = $(target).attr('data-url');

        if(key == ''){
            basicOper.warning('请填写缓存键！');
            return;
        }

        $('#modifyModal').remove();
        $('.modal-backdrop').remove();
        basicOper.pop(url + encodeURIComponent(key), '#modifyModal');
    },

    delete_cache_modal : function(target){
        var key = $(target).attr('data-key');
        var url = $(target).attr('data-url');
        $('#modifyModal').remove();
        $('.modal-backdrop').remove();
        basicOper.pop(url + key, '#modifyModal');
    },

    show_modify_modal_js : function(target){
        var id = $('#logistics_no').val();
        if(id == undefined || id == null || id == ''){
        	$('#modifyModal').remove();
        	$('.modal-backdrop').remove();
        	basicOper.error($('#list_error'), "请扫描物流跟踪号");
        }else{
        	var url = $(target).attr('data-url');
        	$('#modifyModal').remove();
        	$('.modal-backdrop').remove();
            basicOper.pop(url + id, '#modifyModal');
        }
        
    }

}