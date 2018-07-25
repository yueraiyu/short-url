new Vue({
    el: '#url-add-form',
    data: {
        url: "",
        shortKey: ""
    },
    methods: {
        save: function (callback) {
            var vm = this;

            if(verifyUrl(vm.$data.url)){
                layer.msg("请输入有效的请求地址!");
                return;
            }

            if(vm.$data.shortKey){
                if(verifyShortKey(vm.$data.shortKey)){
                    layer.msg("请输入含有数字、字母、下划线且不能以下划线开头和结尾");
                    return;
                }
            }

            axios.post(save_URL, vm.$data)
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
        resetData: function () {
            this.url = "";
            this.shortKey = "";
        }
    }
});
