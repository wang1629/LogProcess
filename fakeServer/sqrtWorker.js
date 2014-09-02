
var net = require('net');

var pms = require('../logModule/perfLogModule');

pms.setOutput('res/sqrtWorker.log');

var HOST = '127.0.0.1';
var PORT = 2222;

var SUM_WORKER_PORT = 3333;
var SQRT_WORKER_PORT = 4444;

var reqID = 1;

function doProcessRequest(square) {
    return Math.sqrt(square);
//    return (-1)*square;
}


var sqrtReqTail = '';
function processRequests(data) {
    pms.perfLog('Start', 'processRequests');
    var str = sqrtReqTail + data; // 1,1:25;
    var length = str.length;
    var lastIndex = str.lastIndexOf(';');
    sqrtReqTail = str.substr(lastIndex+1);
    var sqrtRequestsString = str.substr(0, lastIndex);
    var reply = '';
    
    var sqrtRequests = sqrtRequestsString.split(';');

    for(var i=0; i<sqrtRequests.length; i++) {
        var request = sqrtRequests[i];
        var prefix = request.substr(0, request.indexOf(':')+1);
        var reqId = prefix.split(',')[0];
        pms.perfLog('Start', 'processRequests', reqId);
        var numPair = request.substr(request.indexOf(':')+1);
        var square = parseInt(numPair);
        var result = doProcessRequest(square);
        pms.perfLog('End', 'processRequests', reqId);
        reply = reply + prefix + result + ';';
        console.log('reply = ' + reply);
    }
    pms.perfLog('End', 'processRequests');
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

}).listen(SQRT_WORKER_PORT, HOST);

console.log('SumWorker Server listening on ' + HOST + ':' + SQRT_WORKER_PORT);


