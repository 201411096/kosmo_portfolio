const https = require('https');
const fs = require('fs');
var express = require('express');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);

}).listen(3000, '192.168.0.18', () => {
    console.log('listening on *:3000');
});

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

var io = require('socket.io')(app);

io.on('connection', (socket) => {
  console.log('a user connected');
  socket.on('disconnect', () => {
    console.log('user disconnected');
  });
  socket.on('chat message', (msg) => {
    io.emit('chat message', msg);
    console.log('message: ' + msg);
  });
});
app.post('/imageUploadToNodeJS', (req, res) => {
    var contentType = req.headers['content-type'];
    console.log('contentType: ', contentType);
    var buffer = "";
    req.on('data', function (chunk) {
        buffer += chunk.toString();
    });

    req.on('end', function () {
        console.log('buffer: ', buffer);
        res.end('hello world');
    });
});
