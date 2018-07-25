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
                $("#wrap").show();
                $("#charts").hide();

                axios.get(web_service_URL + this.action);
            } else if ('CHARTS' === name) {
                this.action = 'charts';
                $("#charts").show();
                $("#wrap").hide();

                var req_data= {
                    startTime: "",
                    endTime: "",
                    searchKey: ""};
                axios.post(charts_URL, req_data)
                    .then(function (res) {
                        initCharts(res);
                    })
                    .catch(function (err) {
                        serverError(err);
                    });
            }
        }
    }
});
