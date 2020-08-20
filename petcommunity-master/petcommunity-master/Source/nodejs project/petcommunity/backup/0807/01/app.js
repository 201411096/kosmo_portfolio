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
// }).listen(3000, '121.171.119.57', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);
io.on('connection', function(socket){
  console.log('user connected');
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
  socket.on('joinRoom', function(roomInfo){
    var socketList = io.sockets.sockets; // 소켓 리스트_ 소켓 아이디로 소켓의 정보를 가져올 수 있음
    if(roomInfo.memberId!="tempMember"){ // 회원일 경우 socket의 nickname 지정
      socket.nickname = roomInfo.memberId;
    }
    else{ // 비회원일 경우 socket의 nickname 지정
      roomInfo.memberId='guest__'+socket.id;
      socket.nickname = roomInfo.memberId;
    }
    if(roomInfo.prev == roomInfo.cur){
      socket.join(roomInfo.prev);
      io.to(roomInfo.prev).emit('chat message', roomInfo.memberId+"님이 입장하셨습니다.");
    }else{
      io.to(roomInfo.prev).emit('chat message', roomInfo.memberId+"님이 퇴장하셨습니다.");
      socket.leave(roomInfo.prev);
      socket.join(roomInfo.cur);
      io.to(roomInfo.cur).emit('chat message', roomInfo.memberId+"님이 입장하셨습니다.");
    }

  });
  socket.on('chat message', (msg) => {
    var memberId;
    if(msg.memberId!='tempMember'){
      memberId = msg.memberId;
    }else{
      memberId = 'guest__'+socket.id;
    }
    messageHandling(memberId, msg, socket);
  });
});

function messageHandling(memberId, msg, socket){ // memberId가 보내는 사람
  var firstArgument="";
  var secondArgument="";
  var socketList = io.sockets.sockets; // 소켓 리스트_ 소켓 아이디로 소켓의 정보를 가져올 수 있음
  if(msg.messageContent!=""){          // 메시지가 비어있는 경우가 아니라면 첫번째 인자 추출
    var tempMsg = msg.messageContent.split(" ");
    firstArgument = tempMsg[0];
  }
  if(firstArgument!=""){        // 첫번째 인자가 있는지 확인
    if(firstArgument[0]=="/"){                                                                                                                      // 명령어 처리 부분 시작 ---
      if(firstArgument=="/help" || firstArgument=="/도움말" || firstArgument=="/h"){   // 도움말 처리 시작 ---
        io.to(socket.id).emit('chat message', '========================================');
        io.to(socket.id).emit('chat message', '귓속말 사용방법 ==> [/귓속말][/whisper][/w][/ㅈ] 상대방닉네임 내용');
        io.to(socket.id).emit('chat message', '채팅창 지우기 ==> /clear');
        io.to(socket.id).emit('chat message', '채팅창 닫기 ==> /exit');
        io.to(socket.id).emit('chat message', '========================================');
        return;
      }
      if(firstArgument=="/clear" || firstArgument=="/c"){
        io.to(socket.id).emit('eventHandling', 'clearMessageBox');
        return;
      }
      if(firstArgument=="/exit"){
        io.to(socket.id).emit('eventHandling', 'exitChat');
        return;
      }                                                                              // 도움말 처리 끝 ---
      if(firstArgument=="/w" || firstArgument=="/ㅈ" || firstArgument=="/귓속말" || firstArgument=="/whisper"){                                                                                                // 귓속말 처리 시작 ---
        secondArgument = tempMsg[1]; // 귓속말의 대상이 있는지 확인하는데 사용
        thridArgument = tempMsg[2];  // 귓속말의 내용이 있는지 확인하는데 새용
        var whisperContent="";
        for(var i=2; i<tempMsg.length; i++){
          whisperContent +=tempMsg[i] + " ";
        }
        if(secondArgument==undefined || thridArgument==undefined){
          io.to(socket.id).emit('chat message', '귓속말 형식 => /w 상대방닉네임 내용');
          return;
        }
        io.clients(function(error, clients) { // 현재 접속중인 socket의 목록을 다 가져옴
          if (error) throw error;
          var flagForCheckingTarget=0; // 귓속말의 대상이 존재하는지...
          for (var i=0; i<clients.length; i++){ // 현재 접속중인 socket들을 하나씩 확인함
            if(secondArgument == memberId){
              io.to(socket.id).emit('chat message', '자기 자신에게는 귓속말을 전송할 수 없습니다.');
              return;
            }
            if(socketList[clients[i]].nickname == secondArgument){ // socket의 nickname이 귓속말의 대상과 같다면 메시지를 전달함
              io.to(socketList[clients[i]].id).emit('chat message', memberId + "님으로부터의 귓속말 : " + whisperContent);
              flagForCheckingTarget++;
            }
            // if(socketList[clients[i]].nickname == memberId){ // 자신이 보내는 귓속말은 방과 상관없이 자신에게도 기록이 됨
            //   io.to(socketList[clients[i]].id).emit('chat message', secondArgument + "님에게 귓속말 : " + whisperContent);
            // }
          }
          if(flagForCheckingTarget==0){ // 귓속말의 대상이 접속중이지 않다면...
            io.to(socket.id).emit('chat message', '귓속말의 대상이 존재하지 않습니다.');
          }else{
            io.to(socket.id).emit('chat message', secondArgument + "님에게 귓속말 : " + whisperContent); 
          }         
        });
        return;
      }                                                                                                                         // 귓속말 처리 끝 ---
      io.to(socket.id).emit('chat message', '적절하지 않은 명령어입니다.');    
      return;
    }                                                                                                                                                 // 명령어 처리 부분 끝 ---
  }
  io.to(msg.roomName).emit('chat message', memberId+' : '+msg.messageContent); // 특별한 명령어 처리가 없을 경우 그 방에 포함된 사람들에게만 메시지를 보냄
}