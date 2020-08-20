// var fs = require('fs');
// var publicDataFileFlag = -1;

// fs.stat('test1.js', function(err, stat) {
//     if(err == null) {
//         console.log('File exists');
//     } else if(err.code === 'ENOENT') {
//         // file does not exist
//         fs.writeFile('log.txt', 'Some log\n');
//     } else {
//         console.log('Some other error: ', err.code);
//     }
// });

// var today = new Date();
// today = today.getFullYear().toString()+(today.getMonth()+1).toString()+today.getDate().toString();
// console.log(today.toString().substring(0,4));


// var schedule = require('node-schedule'); 
// var rule = new schedule.RecurrenceRule(); 
// rule.second = 1; //매 시간 30분 마다 수행 
// var j = schedule.scheduleJob({second:1}, console.log(1));
// j;

// var schedule = require("node-schedule");
// var num = 0;

// schedule.scheduleJob({second:1}, function() {

// console.log("10초에 한번씩 실행됩니다. : " + num);
// num++;
// });


// 되는거 확인함
// const schedule = require('node-schedule');
 
// const j = schedule.scheduleJob('25 * * * * *', function(){
//   console.log('매 10초에 실행');
// });
var abc = 'guest_abcdes';
console.log(abc);