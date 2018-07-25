var web_service_URL = 'http://localhost:8090/';
var save_URL = web_service_URL + 'save';
var charts_URL = web_service_URL + 'charts';

//服务器异常提示信息
function serverError(err) {
    if(err.response){
        if (err.response.status === 500) {
            layer.msg('服务器异常');
        } else {
            layer.msg(err.response.data.desc);
        }
    }
}

function verifyUrl(value){
    var urlReg = /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i;
    return !value || (value && !urlReg.test(value));
}

function verifyShortKey(value) {
    var shortKeyReg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/i;
    return !value || (value && !shortKeyReg.test(value));
}

var option = {
    backgroundColor: "#404A59",
    color: ['#C71585', '#DC143C', '#BA55D3', '#4169E1', '#87CEFA', '#2E8B57', '#7CFC00', '#FFFF00', '#FFFAF0', '#008B8B', '#4682B4', '#FF00FF'],

    title: [{
        text: '',
        left: '1%',
        top: '6%',
        textStyle: {
            color: '#fff'
        }
    }, {
        text: '',
        left: '83%',
        top: '6%',
        textAlign: 'center',
        textStyle: {
            color: '#fff'
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c}"
    },
    legend: {
        x: 300,
        top: '7%',
        textStyle: {
            color: '#ffd285',
        },
        data: ['访问总数', '新增IP', '短码']
    },
    grid: {
        left: '1%',
        right: '35%',
        top: '16%',
        bottom: '6%',
        containLabel: true
    },
    toolbox: {
        "show": false,
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        "axisLine": {
            lineStyle: {
                color: '#FF4500'
            }
        },
        "axisTick": {
            "show": false
        },
        axisLabel: {
            textStyle: {
                color: '#fff'
            }
        },
        boundaryGap: false,
        data: []
    },
    yAxis: {
        "axisLine": {
            lineStyle: {
                color: '#fff'
            }
        },
        splitLine: {
            show: true,
            lineStyle: {
                color: '#fff'
            }
        },
        "axisTick": {
            "show": false
        },
        axisLabel: {
            textStyle: {
                color: '#fff'
            }
        },
        type: 'value'
    },
    series: [{
        name: '访问总数',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: []
    }, {
        name: '新增IP',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: []
    }, {
        name: '短码',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: []
    }, {
        name:'系统占比',
        type:'pie',
        center: ['83%', '33%'],
        radius: ['25%', '30%'],
        avoidLabelOverlap: false,
        label: {
            normal: {
                show: true,
                position: 'outside',
                formatter: "{b}:  {d}%",
                fontSize: 12
            },
            emphasis: {
                show: true,
                textStyle: {
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            }
        },
        labelLine: {
            normal: {
                show: true
            }
        },
        data:[

        ]
    }, {
        name:'浏览器占比',
        type:'pie',
        center: ['83%', '72%'],
        radius: ['25%', '30%'],
        avoidLabelOverlap: false,
        label: {
            normal: {
                show: true,
                position: 'outside',
                formatter: "{b}:  {d}%",
                fontSize: 12
            },
            emphasis: {
                show: true,
                textStyle: {
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            }
        },
        labelLine: {
            normal: {
                show: true
            }
        },
        data:[

        ]
    }
    ]
};

function initCharts(res) {
    if(res && res.status === 200){
        if(res.data){
            option.title[0].text = res.data.titles[0];
            option.title[1].text = res.data.titles[1];
            option.xAxis.data = res.data.dates;
            option.series[0].data = res.data.visitCounts;
            option.series[1].data = res.data.visitIpCounts;
            option.series[2].data = res.data.visitShortKeyCounts;
            option.series[3].data = res.data.devices;
            option.series[4].data = res.data.browsers;

            var shortUrlCharts = echarts.init(document.getElementById('charts-detail'));
            shortUrlCharts.setOption(option);
        }
    }
}