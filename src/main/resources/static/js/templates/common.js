var web_service_URL = 'http://localhost:8090/';
var save_URL = web_service_URL + 'save';

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