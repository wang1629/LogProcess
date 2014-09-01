
var pms = require('./logModule/perfLogModule');

var reqId = 1;
var jobId = 1;



function closeTheDoor() {
    pms.perfLog( "Start", "closeDoor", reqId, jobId);

    var s = 0;
    for(var i=0; i<10000000; i++){
        s = s + i;
    }

    pms.perfLog( "End", "closeDoor", reqId, jobId);
}


pms.setOutput('loaaaa.data');

closeTheDoor();

