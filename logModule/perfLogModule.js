

var fs = require('fs');

function timestamp() {
    return (new Date()).valueOf();
}

/*
function __FILE__() {
    return (new Error).stack.split('/').slice(-1).join().split(':')[0];
}

function __LINE__() {
    return parseInt((new Error).stack.split('\n')[1].split(':')[1]);
}
*/

var initialized = false;
var output = 'stdout';
var fd;

exports.setOutput = function(outValue) {
    output = outValue;
};

exports.perfLog = function(trace, counter, reqId, jobId, cpu, network) {

    if(!initialized) {
        if(output != 'stdout'){
            fd = fs.open(output, 'w+');
        }
        initialized = true;
    }

    var entry = {'hostname':'', 'processId':'' ,'time':0, 'trace':'START', 'counter':'', 'reqId':0, 'jobId':0, 'cpu':0, 'network':0};
    var hostname = process.env.HOSTNAME;
    var processId = process.pid;
    entry.hostname = hostname;
    entry.processId = processId;
    entry.time = timestamp();
    entry.trace = trace;
    entry.counter = counter;
    entry.reqId = reqId;
    entry.jobId = jobId;
    entry.CPU = cpu;
    entry.network = network;
    var line = 'PERFLOG ' + JSON.stringify(entry) + '\n';
    if(output === 'stdout') {
        console.log(line);
    } else {
        fs.appendFile(output, line);
    }
};


