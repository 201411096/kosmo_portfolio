//스케쥴러
// nodejs scheduler
// cron expression ..
/*
*     *    *     *    *     *
┬    ┬    ┬    ┬    ┬    ┬
│    │    │    │    │    │
│    │    │    │    │    └ day of week (0 - 7) (0 or 7 is Sun)
│    │    │    │    └───── month (1 - 12)
│    │    │    └────────── day of month (1 - 31)
│    │    └─────────────── hour (0 - 23)
│    └──────────────────── minute (0 - 59)
└───────────────────────── second (0 - 59, OPTIONAL)
*/
/* sample code ...

const schedule = require('node-schedule');
 
const j = schedule.scheduleJob('25 * * * * *', function(){
  console.log('매 25초에 실행');
});

*/

const schedule = require('node-schedule');
 
const j = schedule.scheduleJob('20 32 21 * * *', function(){
  console.log('매 25초에 실행');
});