var fs = require('fs');
var moment = require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");


appendToLogFile('abc', 'defghy', 'asdfilsadhfalisdhflisah s sldiafhlsdf');
function appendToLogFile(msgFrom, msgTo, msgContent){
    var day = moment().format('YYYYMMDD');
    var time = moment().format('YYYY-MM-DD HH:mm:ss');
    var logContent = '\n__msgFrom__:'+msgFrom+',__msgTo__:'+msgTo+',__msgContent__:'+msgContent;
    fs.appendFile('.\\log\\chat_log\\log_'+ day +'.txt', logContent, function (err) {
        if (err) throw err;
        console.log('Saved!');
    });
}