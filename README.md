App Direct Challenge
====================

This project is a standard Servlet 3.1 Web Application showcasing a minimal integration with App Direct APIs.
It also provides a UI for viewing summary of events received from App Direct as well as a minimal in-memory account management module
which processes those events.

To run, it requires a Servlet 3.1-compliant Servlet Container (such as Apache Tomcat 8.x) running on Java 8.

Design considerations:
- The goal is to showcase a working integration with App Direct APIs, some design shortcuts have been taken, e.g:
- In-memory implementation of event logging and account management - a real product implementation would back this by a persistence layer like a RDBMS, likely fronted by an ORM such as Hibernate and using Spring-data. Indeed current impl would blow memory at some point...
- Proper validation of all used elements of events not thoroughly implemented - to some extent mitigated by Jersey's builtin robustness where uncaught exceptions are translated by a 500 Server Error (a real product would implement stronger input validation).
- Minimal (but working nonetheless) OAuth signature verification through a custom servlet filter, piggybacking on spring-security-oauth primitives.
