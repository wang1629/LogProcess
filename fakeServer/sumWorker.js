
var net = require('net');

var pms = require('./logModule/perfLogModule');

pms.setOutput('res/sumWorker.log');

var HOST = '127.0.0.1';
var PORT = 2222;

var SUM_WORKER_PORT = 3333;
var SQRT_WORKER_PORT = 4444;

var reqID = 1;

function doProcessRequest(m, n, reqId) {
    pms.perfLog('Start', 'doProcessRequest', reqId);
    var res = m*m + n*n;
    pms.perfLog('Start', 'doProcessRequest', reqId);
    return res;
}

var sumReqTail = '';
function processRequests(data) {
    pms.perfLog('Start', 'processRequests');
    var str = sumReqTail + data;
    var lastIndex = str.lastIndexOf(';');
    sumReqTail = str.substr(lastIndex+1);
    var sumRequestsString = str.substr(0, lastIndex);
    var reply = '';
    
    sumRequests = sumRequestsString.split(';');

    for(var i=0; i<sumRequests.length; i++) {
        var request = sumRequests[i];
        var prefix = request.split(':')[0];
        var clientId = prefix.split(',')[0];
        var reqId = prefix.split(',')[1];
        pms.perfLog('Start', 'processRequest', reqId);
        var numPair = request.split(':')[1]
        var m = parseInt(numPair.split(',')[0]);
        var n = parseInt(numPair.split(',')[1]);
        var result = doProcessRequest(m, n, reqId);
        reply = reply + prefix + ':' + result + ';';
        pms.perfLog('End', 'processRequest', reqId);
    }
    pms.perfLog('Start', 'processRequests');
    return reply;
}


net.createServer( function(sock) {
    console.log('SumWorker Recv New Connection: ' + sock.remoteAddress + ':' + sock.remotePort);
    sock.on('data', function(data) {
        console.log('Recv data from Client (' + sock.remoteAddress + ':' + sock.remotePort + ') = ' + data);
        var reply = processRequests(data);
        console.log('reply to master = ' + reply);
        sock.write(reply);
    });
}).listen(SUM_WORKER_PORT, HOST);

console.log('SumWorker Server listening on ' + HOST + ':' + SUM_WORKER_PORT);


