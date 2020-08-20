const https = require('https');
const fs = require('fs');
let {PythonShell} = require('python-shell');
const { time } = require('console');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};
// 공공데이터(파이썬 부분)-----
var systemPythonPath = 'C:/ProgramData/Anaconda3/python.exe';
var directoryPath = process.cwd();
var directoryPathArray = directoryPath.split('\\');
for(var i=0; i<2; i++){
  directoryPathArray.pop();
}
directoryPath = directoryPathArray[0];
for(var i=1; i<directoryPathArray.length; i++){
  directoryPath+="/";
  directoryPath+=directoryPathArray[i];
}
directoryPath+="/python project/pythonProject_kys/";
// 공공데이터(파이썬 부분)-----

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,Origin,Accept,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);

}).listen(3000, '192.168.0.18', () => {
// }).listen(60005, '115.91.88.227', () => {
// }).listen(3000, '121.171.119.57', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);
io.on('connection', function(socket){
  console.log('user connected');
  // socket.on('disconnecting', () => {
  // });
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
  // 공공데이터(파이썬 부분)-----
  socket.on('getPublicData', function(dataOptions){
    executePythonFileAndReadJsonFile(dataOptions, socket);
  })
  // 공공데이터(파이썬 부분)-----

  // 이미지 분류 ...
  socket.on('ClassifyingImage', function(data){
    console.log('받아온 데이터 타입 확인', typeof(data));
    executePythonFileForML(data);
  });
  // 이미지 분류 ...

  // 쪽지 알림 부분 -----
  socket.on('sendMessageData', function(sendMessageObject){
    var socketList = io.sockets.sockets;
    io.clients(function(error, clients){
      if (error) throw error;
      for(var i=0; i<clients.length; i++){
        if(socketList[clients[i]].nickname == sendMessageObject.messageTo){
          io.to(socketList[clients[i]].id).emit('sendMessageData', sendMessageObject);      
        }
      }
    });
  });
  socket.on('sendDelData', function(sendMessageObject){
    var socketList = io.sockets.sockets;
    io.clients(function(error, clients){
      if (error) throw error;
      for(var i=0; i<clients.length; i++){
        if(socketList[clients[i]].nickname == sendMessageObject.messageTo){
          io.to(socketList[clients[i]].id).emit('sendDelData', sendMessageObject);      
        }
      }
    });
  });
  // 쪽지 알림 부분 -----
  socket.on('setNickname', function(memberId){
    socket.nickname = memberId;
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

// 공공데이터(파이썬 부분)-----
function executePythonFileAndReadJsonFile(dataOptions, socket){
  var python_options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args:[1],
    // args: [0, '20200801', '20200831', '3']
    args: [0, dataOptions.startDate, dataOptions.endDate, dataOptions.dataCnt]
  }
  PythonShell.run(directoryPath+"PublicData.py", python_options, function (err, results) {
    if (err) throw err;
    fs.readFile('publicData.json', 'utf8', function(err, data){
        data = JSON.parse(data);
        // data = data.response.body.items.item; // 수정 위치
        console.log(data);
        console.log('data 길이...' + data.length);
        socket.emit('getPublicData', data);
    });    
  });
}
// machineLearning(파이썬 부분)-----
function executePythonFileForML(imageData){
  fs.writeFile('test.jpg', imageData, 'binary', function(err){
  });
  var python_options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args:[1],
    // args: [0, dataOptions.startDate, dataOptions.endDate, dataOptions.dataCnt]
    args:[0, "test.jpg"]
  }

  PythonShell.run(directoryPath+"ClassifyingImage.py", python_options, function (err, results) {
    if (err) throw err;
    //results = JSON.parse(results);
    console.log('pythonshell에서 ... results 확인', results);    
    console.log('results 타입 확인', typeof(results));
    console.log('results %j', results);
    fs.readFile("ClassifyingImage.txt", 'utf8', function(err, data){
      console.log('파일에서 읽은 값 : ' + data);
    });
  });
}
