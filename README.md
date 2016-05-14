

Introduction
---------------

This is a minimal project that aims to be fast and reflect the real world problems of a web service: handle requests and read data from database.

Because it is simple, so the profiling and tunning would be easier. To achieves high performance, there are two key ideas:

* Use async IO while possible.
* Tunning the number of working threads to be the same as CPU cores to lower the cost of context switch.


### Use Async IO

Using async IO becomes common sense while handle connections and requests while write a server. But not all the people using async IO to fetch data from database. This project uses [Spary.io](http://spary.io) to handle requests and [Rediscala](https://github.com/etaty/rediscala) to fetch data from Redis. Both of them uses Akka, so the IO is async.


### Tunning Number of Working Threads

Akka is powerful but need some configurations to reach better performance. One of them is to config the number of threads. You can see the configurations in `src/main/resources`.


Run
--------------

* You should have `sbt` installed in order to compile and run this project.
* Run `sbt dist` will generate a zip package in `target/universal`. Then unzip it.
* Change the default Redis address under `conf/application.conf` if needed.
* Unzip it and run the scripts under `bin/`.
* There are two urls you can test: `/get?key=1` and `/set?key=k&value=v`. Which will get and set key for Redis.


Performance
------------

You can use wrk or ab to do load test. This project achieves about **70k QPS** on an c4.8xlarge EC2 instance.

