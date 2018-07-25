var mainSidebar = new Vue({
    el: '#main-sidebar',
    data: {
        table: '',
        action: ''
    },
    created: function () {

    },
    methods: {
        menu: function (name) {
            if ('WRAP' === name) {
                this.action = 'index';
                $("#create").show();
                $("#list").hide();

                axios.get(web_service_URL + this.action);
                // window.location = web_service_URL + this.action;
            } else if ('COUNT' === name) {
                this.action = 'list';
                $("#list").show();
                $("#create").hide();

                layui.use('table', function(){
                    this.table = layui.table;

                    //第一个实例
                    this.table.render({
                        elem: '#url-list'
                        ,height: 315
                        ,url: 'list' //数据接口
                        ,page: true //开启分页
                        ,cols: [[ //表头
                            {field: 'id', title: 'ID', fixed: 'left'}
                            ,{field: 'hashKey', title: '哈希码'}
                            ,{field: 'shortKey', title: '短码'}
                            ,{field: 'url', title: '链接'}
                            ,{field: 'clickNum', title: '点击数'}
                        ]]
                    });

                });
            }
        }
    }
});
