new Vue({
    el: '#url-add-form',
    data: {
        url: "",
        shortKey: ""
    },
    methods: {
        validate: function () {
            return $('#url-add-form').validate({
                rules: {
                    url: {
                        required: true,
                        url: true
                    }
                }
            });
        },
        save: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
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
