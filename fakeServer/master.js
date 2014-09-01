
var net = require('net');
var events = require('events');

var pms = require('./logModule/perfLogModule');

pms.setOutput('res/master.log');

var emitter = new events.EventEmitter();

var HOST = '127.0.0.1';
var PORT = 2222;
var SUM_HOST = '127.0.0.1';
var SUM_PORT = 3333;
var SQRT_HOST = '127.0.0.1';
var SQRT_PORT = 4444;

var toSumWorker;
var toSqrtWorker;

var clientUid = 1;
var allClientConns = {};

var requestUid = 100; 

function initConnToWorker(){

    pms.perfLog('Start', 'initConnToWorker');
    pms.perfLog('Start', 'initConnToWorker-sum');
    toSumWorker = new net.Socket();
    toSumWorker.connect(SUM_PORT, SUM_HOST, function() {
        console.log('Connecte to SumWorker OK.');
    });
    toSumWorker.on('data', recvSumResult);
    toSumWorker.on('error', function() {
        console.log('Error: Cannot connect to Sum Server');
    });
    pms.perfLog('End', 'initConnToWorker-sum');

    pms.perfLog('Start', 'initConnToWorker-sqrt');
    toSqrtWorker = new net.Socket();
    toSqrtWorker.connect(SQRT_PORT, SQRT_HOST, function() {
        console.log('Connecte to SqrtWorker OK.');
    });
    toSqrtWorker.on('data', recvSqrtResult);
    toSqrtWorker.on('error', function() {
        console.log('Error: Cannot connect to Sqrt Server');
    });
    console.log('Init connection to workers');
    pms.perfLog('End', 'initConnToWorker-sqrt');
    pms.perfLog('End', 'initConnToWorker');
}

function recvSumResult(data) {
    console.log('Recv data from SumWorker = ' + data);
    parseSumResult(data);
}

function recvSqrtResult(data) {
    console.log('Recv data from SqrtWorker = ' + data);
    parseSqrtResult(data);
}


var sumDataTail = '';
function parseSumResult(data) {
    pms.perfLog('Start', 'parseSumResult');
    var str = sumDataTail + data;
    var length = str.length;
    var lastIndex = str.lastIndexOf(';');
    sumDataTail = str.substr(lastIndex+1);
    var sumResultsString = str.substr(0, lastIndex);
    
    var sumResults = sumResultsString.split(';');
    for(var i=0; i<sumResults.length; i++) {
        var reqId = sumResults[i].split(':')[0].split(',')[1];
        pms.perfLog('Start', 'parseSumResult', reqId);
        toSqrtWorker.write(sumResults[i] + ';');
        pms.perfLog('End', 'parseSumResult', reqId);
    }
    pms.perfLog('End', 'parseSumResult');
}

function sendBackToClient(sqrtResult) {
    var clientId = (sqrtResult.split(':')[0]).split(',')[0];
    var reqId = (sqrtResult.split(':')[0]).split(',')[1];
    pms.perfLog('Start', 'sendBackToClient', reqId);
    var sqrtRes = (sqrtResult.split(':')[1]);
    var clientConn = allClientConns[parseInt(clientId)];
    clientConn.write(sqrtRes + ';')
    pms.perfLog('End', 'sendBackToClient', reqId);
}

var sqrtTail = '';
function parseSqrtResult(data) {
    pms.perfLog('Start', 'parseSqrtResult');
    var str = sqrtTail + data;
    var length = str.length;
    var lastIndex = str.lastIndexOf(';');
    sqrtTail = str.substr(lastIndex+1);
    var sqrtResultsString = str.substr(0, lastIndex);
    
    var sqrtResults = sqrtResultsString.split(';');
    for(var i=0; i<sqrtResults.length; i++) {
        var reqId = sqrtResults[i].split(':')[0].split(',')[1];
        pms.perfLog('Start', 'parseSqrtResult', reqId);
        sendBackToClient(sqrtResults[i]);
        pms.perfLog('End', 'parseSqrtResult', reqId);
    }
    pms.perfLog('End', 'parseSqrtResult');
}

var requestDataTail = '';
function parseRequest(clientId, data) {
    pms.perfLog('Start', 'parseResult');
    var str = requestDataTail + data;
    var length = str.length;
    var lastIndex = str.lastIndexOf(';');
    requestDataTail = str.substr(lastIndex+1);
    var requestsString = str.substr(0, lastIndex);

    requests = requestsString.split(';');
    
    for(var i=0; i<requests.length; i++) {
        var requestId = requestUid;
        requestUid++;
        pms.perfLog('Start', 'parseResult' + i, requestId);
        var forwardRequest = '' + clientId + ',' + requestId + ':' + requests[i] + ';';
        console.log('Forword to SumWorker: ' + forwardRequest);
        toSumWorker.write(forwardRequest);
        pms.perfLog('End', 'parseResult' + i, requestId);
    }
    pms.perfLog('End', 'parseResult');
};

emitter.on('client-data', function(clientId, data) {
    console.log('Recv data from client('+ clientId + ')=' + data);
    parseRequest(clientId, data);
});

net.createServer( function(socket) {
    console.log('new connection.');
    var clientId = clientUid;
    allClientConns[clientUid] = socket;
    clientUid++;
    socket.on('data', function(data) {
        emitter.emit('client-data', clientId, data);
    });
}).listen(PORT, HOST);

console.log('Serve listen at ' + PORT);
initConnToWorker();


