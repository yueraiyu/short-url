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

function verifyUrl(value){
    var urlReg = /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i;
    return !value || (value && !urlReg.test(value));
}

function verifyShortKey(value) {
    var shortKeyReg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/i;
    return !value || (value && !shortKeyReg.test(value));
}