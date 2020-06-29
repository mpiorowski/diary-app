const io = require('socket.io')();

io.on('connection', (client) => {
  client.on('request', (message) => {
    console.log('message ', message);
    client.emit('response', message);
  });
});

const port = 12000;
io.listen(port);
console.log('listening on port ', port);
