const https = require('https');
const fs = require('fs');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
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
/*
UserList의 구조
  ㄴ userId : 로그인이 되어있는 상태라면 memberInfo의 memberId가 userId가 됨, 로그인 상태가 아니라면 'user_'+socket.id가 userId가 됨
  ㄴ memberInfo : script단에서 넘어옴
  ㄴ socketList : 사용하고 있는 소켓 리스트
  ㄴ memberFlag : 비회원일 경우 -1 ..
*/

function addMemberInfoToArray(socket, outerMemberInfo, userList, io){
  var userObject = new Object(); // userList에 넣을 객체 -> 회원일 경우 로그인한 아이디가 존재한다면 사용하지 않음
  var socketList = new Array();
  if(outerMemberInfo!='tempMember'){
    for(var i=0; i<userList.length; i++){
      if(userList[i].userId==outerMemberInfo.memberId){ // 한 사용자가 여러 기기에서 접속할 경우 해당 객체의 소켓리스트에만 소켓을 추가하고 끝냄
        userList[i].socketList.push(socket);
        return;
      }
    }
    userObject.userId = outerMemberInfo.memberId;
  }else{
    userObject.userId = 'user_'+socket.id;
    userObject.memberFlag = -1;
  }
  userObject.memberInfo = outerMemberInfo;
  socketList.push(socket);
  userObject.socketList = socketList;
  userList.push(userObject);
  io.emit('chat message', userObject.userId + "님이 입장하셨습니다.");
}

function deleteMemberInfoFromArray(socket, userList, io){
  for(var i=0; i<userList.length; i++){
    for(var j=0; j<userList[i].socketList.length; j++){
      if(userList[i].socketList[j].id==socket.id){
        userList[i].socketList.splice(j, 1); // 일치하는 소켓을 제거함
        if(userList[i].socketList.length==0){ //해당 유저의 소켓이 하나도 존재하지 않게 됨
          io.emit('chat message', userList[i].userId + "님이 퇴장하셨습니다.");
          userList.splice(i, 1);
        }
        return;
      }
    }
  }
}

function printUserListInConsole(userList){
  console.log("---------- printUserListInConsole start ... ----------");
  for(var i=0; i<userList.length; i++){
    console.log("----- "+ i +  "_memberInfo -----");
    console.log(userList[i].userId);
    for(var j=0; j<userList[i].socketList.length; j++){
      console.log(userList[i].socketList[j].id);
    }
  }
  console.log("---------- printUserListInConsole end ... ----------");
}
