//同步appName
var synchroapp=true,clickId;
function synchroApp(obj){
    if(!synchroapp){
        return;
    }
    synchroapp=false;
    var app=$(obj).attr('data-app');
    var environment=$("#environment").val();
    var isSynchro=false;
    $.ajax({
        url:'/jade/sync/'+environment+'/'+app,
        type:'GET',
        dataType:'json',
        success:function(data){
            if(data.success){
                layer.msg("同步成功", {
                    offset: 't'
                });
                isSynchro=true;
                $(".main-body").attr("data-app",app);
                listLoadStyle("appInquiry");
                if(isSynchro) {
                    setTimeout(function(){
                        $(obj).parent().find("#btn-info span").click();
                    },1000);
                }
            }
            synchroapp=true;
        },
        error:function(data){
            layer.msg("同步失败", {
                offset: 't'
            });
            synchroapp=true;
        }
    });
}
var tableName,shardColumn;
function btnInfo(obj){
    shardColumn=$(obj).attr("data-shardColumn");
    tableName=$(obj).attr("data-tableName");
    newDeploy(tableName+"查询结果",['80%', '70%'],'Inquiryresult.html');
    for(var i=0;i<eval(shardColumn).length;i++){
        $("<option value='"+eval(shardColumn)[i]+"'>"+eval(shardColumn)[i]+"</option>").appendTo("#shard-column")
    }
    $("#shardColumn").text(shardColumn);
}
function newDeploy(t,a){
    layer.open({
        type: 1,
        title: t,
        closeBtn: 0,
        area: a, //宽高,
        shadeClose: true,
        content:$(".newLook")
    });
}


function formatDate(str){
    var date = new Date(str),Y,M,D,h,m,s;
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = (date.getDate()>=10?date.getDate():"0"+date.getDate()) + ' ';
    h = (date.getHours()<=0?'00':(date.getHours()>=10?date.getHours():"0"+date.getHours())) + ':';
    m = (date.getMinutes()<=0?'00':(date.getMinutes()>=10?date.getMinutes():"0"+date.getMinutes())) + ':';
    s = (date.getSeconds()<=0?'00':(date.getSeconds()>=10?date.getSeconds():"0"+date.getSeconds()));
    return (Y+M+D+h+m+s);
}


var inquiryresult=true;
function inquiryResultBtn(){
    var index = layer.load(2, {shade: false});
    if(!inquiryresult){
        return;
    }
    inquiryresult=false;
    $("#inquiryThead").html("");
    $("#inquiryTbody").html("");
    var shardC=$("#shard-column").val();
    var inputVal=$("#input-box").val();
    var limit=$("#limit").val();
    if(inputVal==""){
        layer.msg("请填写字段值", {
            offset: 't'
        });
        inquiryresult=true;
        setTimeout(function(){layer.close(index);},1000);
        return;
    }
    var theadTr,tbodyTr;
    var app=$("#appName").val();
    var url='/dbplus/logictable/fetch?tableName='+tableName+'&appName='+app+'&columnName='+shardC+'&columnValue='+inputVal+'&extraFileds='+limit;
    $.ajax({
        url:url,
        type:'GET',
        dataType:'json',
        success:function(data){
            console.log(data);
            theadTr="<tr>";
            for(var i=0;i<data.column.length;i++){
                theadTr+="<td>"+data.column[i]+"</td>"
            }
            theadTr+="</tr>";
            $(theadTr).appendTo("#inquiryThead");

            for(var j=0;j<data.data.length;j++){
                tbodyTr="<tr>";
                var datas=data.data[j];
                for(var k=0;k<eval(datas).length;k++){
                    tbodyTr+="<td>"+eval(datas)[k]+"</td>"
                }
                tbodyTr+="</tr>";
                $(tbodyTr).appendTo("#inquiryTbody");
            }
            inquiryresult=true;
            setTimeout(function(){layer.close(index);},1000);
        },
        error:function(data){
            layer.msg("数据获取失败", {
                offset: 't'
            });
            inquiryresult=true;
            setTimeout(function(){layer.close(index);},1000);
        }
    });
}