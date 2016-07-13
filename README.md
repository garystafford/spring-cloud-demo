## Diving Deeper into ‘Getting Started with Spring Cloud’
_Explore the integration of Spring Cloud and Spring Cloud Netflix tooling, through a deep dive into Pivotal’s ‘Getting Started with Spring Cloud’ presentation._

Project repository for accompanying blog post: [Diving Deeper into ‘Getting Started with Spring Cloud’](http://wp.me/p1RD28-1N1)   

#### Introduction
The presentation, [Getting Started with Spring Cloud](https://youtu.be/cCEvFDhe3os), given by Josh Long and Dr. Dave Syer, at SpringOne2GX 2015, provides an excellent end-to-end technical overview of the latest Spring and Netflix technologies.

The accompanying post examines the technologies, components, code, and configuration presented in Getting Started with Spring Cloud. The goal of the post is to provide a greater understanding of the Spring Cloud and Spring Cloud Netflix technologies.

To clone project locally:
```
git clone https://github.com/garystafford/spring-cloud-demo-config-repo.git
git clone --recursive https://github.com/garystafford/spring-cloud-demo.git
```


#### System Overview

![Overall System Diagram](https://programmaticponderings.files.wordpress.com/2016/02/reservation-system-diagram.png "Overall System Diagram")

The presentation’s example introduces a dizzying array of technologies, which include:

**Spring Boot**  
Stand-alone, production-grade Spring-based applications

**Spring Data REST / Spring HATEOAS**  
Spring-based applications following HATEOAS principles

**Spring Cloud Config**  
Centralized external configuration management, backed by Git

**Netflix Eureka**  
REST-based service discovery and registration for failover and load-balancing

**Netflix Ribbon**  
IPC library with built-in client-side software load-balancers

**Netflix Zuul**  
Dynamic routing, monitoring, resiliency, security, and more

**Netflix Hystrix**  
Latency and fault tolerance for distributed system

**Netflix Hystrix Dashboard**  
Web-based UI for monitoring Hystrix

**Spring Cloud Stream**  
Messaging microservices, backed by Redis

**Spring Data Redis**  
Configuration and access to Redis from a Spring app, using Jedis

**Spring Cloud Sleuth**  
Distributed tracing solution for Spring Cloud, sends traces via Thrift to the Zipkin collector service

**Twitter Zipkin**  
Distributed tracing system, backed by Apache Cassandra

**H2**  
In-memory Java SQL database, embedded and server modes

**Docker**  
Package applications with dependencies into standardized Linux containers


#### Project URLs
**Reservation Service**  
[http://localhost:8000/reservations](http://localhost:8000/reservations)  
[http://localhost:8000/reservations/search/by-name?rn=Amit](http://localhost:8000/reservations/search/by-name?rn=Amit)  
[http://localhost:8000/configprops](http://localhost:8000/configprops)  
[http://localhost:8000/metrics](http://localhost:8000/metrics)  
[http://localhost:8000/health](http://localhost:8000/health)  

**Reservation Client**  
[http://localhost:8050/reservation-service/reservations](http://localhost:8050/reservation-service/reservations)  
[http://localhost:8050/reservations/names](http://localhost:8050/reservations/names)  
[http://localhost:8050/reservations](http://localhost:8050/reservations)  
[http://localhost:8050/reservations/service-message](http://localhost:8050/reservations/service-message)  
[http://localhost:8050/reservations/client-message](http://localhost:8050/reservations/client-message)  

**Eureka Server**  
[http://localhost:8761](http://localhost:8761)  

**Spring Cloud Config**  
[http://localhost:8888/reservation-service/master](http://localhost:8888/reservation-service/master)  
[http://localhost:8888/reservation-client/master](http://localhost:8888/reservation-client/master)  

**Hystrix Dashboard**  
[http://localhost:8050/hystrix.stream](http://localhost:8050/hystrix.stream)  
[http://localhost:8010/hystrix.html](http://localhost:8010/hystrix.html)  

**Zipkin**  
[http://192.168.99.100:8080](http://192.168.99.100:8080)  
[http://192.168.99.101:9990/admin](http://192.168.99.101:9990/admin)  

**H2**
[http://192.168.99.1:6889](http://192.168.99.1:6889)  
