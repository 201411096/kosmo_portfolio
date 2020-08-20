var moment = require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");



console.log(moment().format('YYYY-MM-DD HH:mm:ss'));
for(var i=0; i<1000000; i++){
    console.log(moment().format('YYYY-MM-DD HH:mm:ss'));
}

var arr1 = ['cde', 'bcd', 'abc'];
console.log(arr1);
console.log(arr1.sort());