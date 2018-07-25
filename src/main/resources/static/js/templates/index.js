layui.use(['element', 'laydate'], function(){
    var element = layui.element;
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#start_time' //指定元素
        ,done: function(value, date, endDate){
            content_vue.charts.startTime = value;
        }
    });
    laydate.render({
        elem: '#end_time' //指定元素
        ,done: function(value, date, endDate){
            content_vue.charts.endTime = value;
        }
    });
});
