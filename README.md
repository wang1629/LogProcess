# LogProcess

This repo is used to process performance trace log.
Currently this is only a demo.

## logModule ## provide a tool to get trace information. Writen in javascript on nodejs.

## fakeServer ## is a simulation of cluster computing, actually they are different processes in one node.

## processChain ## is used to process perf log entries. This is a framework to process log entries. Currently, this framework support user to define his filters and groupby to dispatch entries to corresponding EntryProcess.

## httpServer ## a simple http server. Writen in nodejs (express module).

## display ## html file to  display performance chart and update periodically.
