const https = require('https');
const fs = require('fs');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    //res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,Origin,Accept,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);

}).listen(3000, '192.168.0.18', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);
var userList = new Array();

io.on('connection', (socket) => {
  console.log('user connected');
  socket.on('disconnect', () => {
    console.log('user disconnected');
    deleteMemberInfoFromArray(socket, userList, io);
    printUserListInConsole(userList);
  });
  socket.on('chat message', (msg) => {
    io.emit('chat message', msg);
    console.log('message: ' + msg);
  });
  socket.on('memberInfo', function(memberInfo){
    addMemberInfoToArray(socket, memberInfo, userList, io);
    printUserListInConsole(userList);
  });
});

function addMemberInfoToArray(socket, outerMemberInfo, userList, io){
  var tempObject = new Object();
  var tempMemberInfo = new Object();
  tempObject.socketId = socket.id;
  if(outerMemberInfo!='tempMember'){
    console.log(outerMemberInfo);
    tempMemberInfo=outerMemberInfo;
    console.log('undefined 아님');
  }else{
    console.log('tempMember');
    tempMemberInfo.memberId = "user_"+socket.id;
    tempMemberInfo.memberFlag = -1; // -1이면 비회원
  }
  tempObject.memberInfo=tempMemberInfo;
  io.emit('chat message', tempObject.memberInfo.memberId + "님이 입장하셨습니다.");
  userList.push(tempObject);
}

function deleteMemberInfoFromArray(socket, userList, io){
  for(var i=0; i<userList.length; i++){
    if(userList[i].socketId==socket.id){
      io.emit('chat message', userList[i].memberInfo.memberId + "님이 퇴장하셨습니다.");
      userList.splice(i, 1);
    }
  }
}

function printUserListInConsole(userList){
  console.log("---------- printUserListInConsole start ... ----------");
  for(var i=0; i<userList.length; i++){
    console.log("----- "+ i +  "_memberInfo -----");
    console.log(userList[i].memberInfo.memberId);
    // console.log(userList[i].memberInfo.memberPassword);
    // console.log(userList[i].memberInfo.memberName);
    // console.log(userList[i].memberInfo.memberAddress);
    // console.log(userList[i].memberInfo.memberTel);
    // console.log(userList[i].memberInfo.memberEmail);
    // console.log(userList[i].memberInfo.memberBirthday);
    // console.log(userList[i].memberInfo.memberFlag);
    // console.log("----- "+ i +  "_memberInfo -----");
  }
  console.log("---------- printUserListInConsole end ... ----------");
}
