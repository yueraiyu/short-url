
var content_vue = new Vue({
    el: '#content',
    data: {
        wrap: {
            url: "",
            shortKey: ""
        },
        charts: {
            startTime: "",
            endTime: "",
            searchKey: ""
        }
    },
    methods: {
        save: function () {
            var vm = this;

            if(verifyUrl(vm.$data.wrap.url)){
                layer.msg("请输入有效的请求地址!");
                return;
            }

            if(vm.$data.wrap.shortKey){
                if(verifyShortKey(vm.$data.wrap.shortKey)){
                    layer.msg("请输入含有数字、字母、下划线且不能以下划线开头和结尾");
                    return;
                }
            }

            axios.post(save_URL, vm.$data.wrap)
                .then(function (res) {
                    if(res.data.code != 400){
                        var shortUrl = res.data.data;
                        layer.open({
                            title: '短码链接'
                            ,btn: ['test']
                            ,content: shortUrl
                            ,yes: function(index, layero){
                                window.open(shortUrl);
                            }
                            ,cancel: function(){

                            }
                        });
                    }else{
                        layer.msg(res.data.msg);
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        shortUrlCharts: function () {
            var vm = this;

            if(vm.$data.charts.searchKey){
                if(verifyShortKey(vm.$data.charts.searchKey)){
                    layer.msg("请输入含有数字、字母、下划线且不能以下划线开头和结尾");
                    return;
                }
            }

            axios.post(charts_URL, vm.$data.charts)
                .then(function (res) {
                    initCharts(res);
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        resetData: function () {
            this.wrap.url = "";
            this.wrap.shortKey = "";
        }
    }
});
