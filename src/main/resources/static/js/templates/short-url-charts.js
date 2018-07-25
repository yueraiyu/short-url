option = {
    backgroundColor: "#404A59",
    color: ['#ffd285', '#ff733f', '#ec4863'],

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
        trigger: 'axis'
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
            type: 'pie',
            center: ['83%', '33%'],
            radius: ['25%', '30%'],
            label: {
                normal: {
                    position: 'right'
                }
            },
            data: [{
                value: 180,
                name: 'WINDOWS',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#ffd285'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#ffd285',
                        },
                        formatter: 'WINDOWS {d} % \n',
                        fontSize: 11
                    }
                }
            }, {
                value: 180,
                name: 'UNIX',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#ff733f'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#ff733f',
                        },
                        formatter: 'UNIX {d} %  \n',
                        fontSize: 11
                    }
                }
            }, {
                value: 335,
                name: 'LINUX',
                itemStyle: {
                    normal: {
                        color: '#ec4863'
                    }
                },
                label: {
                    normal: {
                        formatter: 'LINUX {d} %',
                        textStyle: {
                            color: '#ec4863',
                            fontSize: 11
                        }
                    }
                }
            }]
        },


        {
            type: 'pie',
            center: ['83%', '72%'],
            radius: ['25%', '30%'],
            label: {
                normal: {
                    position: 'right'
                }
            },
            data: [{
                value: 180,
                name: 'FIREFOX',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#ffd285'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#ffd285',
                        },
                        formatter: 'FIREFOX {d} % \n',
                        fontSize: 11
                    }
                }
            }, {
                value: 180,
                name: 'GOOGLE',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#AACD06'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#AACD06',
                        },
                        formatter: 'GOOGLE {d} %  \n',
                        fontSize: 11
                    }
                }
            }, {
                value: 335,
                name: 'OPERA',
                itemStyle: {
                    normal: {
                        color: '#ec4863'
                    }
                },
                label: {
                    normal: {
                        formatter: 'OPERA {d} %  \n',
                        textStyle: {
                            color: '#ec4863',
                            fontSize: 11
                        }
                    }
                }
            }, {
                value: 180,
                name: 'SAFARI',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#2568DA'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#2568DA',
                        },
                        formatter: 'SAFARI {d} %  \n',
                        fontSize: 11
                    }
                }
            }, {
                value: 180,
                name: 'EDGE',
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        color: '#ff733f'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            color: '#ff733f',
                        },
                        formatter: 'EDGE {d} %  \n',
                        fontSize: 11
                    }
                }
            }]
        }
    ]
};

var myChart = echarts.init(document.getElementById('charts-detail'));
myChart.setOption(option);