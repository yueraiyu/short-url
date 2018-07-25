option = {
    backgroundColor: "#404A59",
    color: ['#C71585', '#DC143C', '#BA55D3', '#4169E1', '#87CEFA', '#2E8B57', '#7CFC00', '#FFFF00', '#FFFAF0', '#008B8B', '#4682B4', '#FF00FF'],

    title: [{
        text: '访问统计',
        left: '1%',
        top: '6%',
        textStyle: {
            color: '#fff'
        }
    }, {
        text: '访问分类',
        left: '83%',
        top: '6%',
        textAlign: 'center',
        textStyle: {
            color: '#fff'
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
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
        data: ['2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018']
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
        data: [90, 110, 139, 142, 130, 142, 130,133]
    }, {
        name: '新增IP',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: [70, 50, 50, 87, 90, 80, 70,72]
    }, {
        name: '短码',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: [20, 22, 20, 32, 15, 20, 19,22]
    },
        {
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
                {value:335, name:'Windows'},
                {value:310, name:'Mac'},
                {value:234, name:'Unix'},
                {value:135, name:'Android'},
                {value:1548, name:'IPhone'},
                {value:154, name:'UnKnown'}
            ]
        },
        {
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
                {value:335, name:'Edge'},
                {value:310, name:'Msie'},
                {value:234, name:'Safari'},
                {value:135, name:'Opera'},
                {value:1548, name:'Chrome'},
                {value:1350, name:'Firefox'},
                {value:115, name:'Netscape'},
                {value:105, name:'IE'},
                {value:145, name:'UnKnown'},
                {value:125, name:'OPR'}
            ]
        }
    ]
};

var myChart = echarts.init(document.getElementById('charts-detail'));
myChart.setOption(option);