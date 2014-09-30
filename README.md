# LogProcess

This repo is used to process performance trace log.
Currently this is only a demo.

## logModule
provide a tool to get trace information. Writen in javascript on nodejs.

## fakeServer
is a simulation of cluster computing, actually they are different processes in one node.

## processChain
is used to process perf log entries. This is a framework to process log entries. Currently, this framework support user to define his filters and groupby to dispatch entries to corresponding EntryProcess.

## httpServer
a simple http server. Writen in nodejs (express module).

## display
html file to  display performance chart and update periodically.


=== More Detailed Information ===
1.  To simulate the server to be monitored, this demo writes three simple fake servers and a client. The three servers just compute the sqrt(m^2+n^2) and return the result back to client. Currently, the three fake server is three processes in one node(just a simulation for a distributed system).
![image](https://github.com/wang1629/LogProcess/blob/master/images/FakeServer.png)
2.  The result is processed by processChain:
LogStream will receive new entries from fake servers.
Every log entry will be processed by Filter/GroupBy/LogProcessor and generate some result item.
![image](https://github.com/wang1629/LogProcess/blob/master/images/PMS.png)
3.  The result will display in the web page using an existing JavaScript framework named highchart. A very simple httpServer is writen to server web page data.
4.  Open browser page, the page will periodically fetch new result item to display.
![image](https://github.com/wang1629/LogProcess/blob/master/images/display.png)
