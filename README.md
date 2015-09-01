App Direct Challenge
====================

This project is a standard Servlet 3.1 Web Application showcasing a minimal integration with App Direct APIs.
It also provides a UI for viewing summary of events received from App Direct as well as a minimal in-memory account management module
which processes those events.

To run, it requires a Servlet 3.1-compliant Servlet Container (such as Apache Tomcat 8.x) running on Java 8.

For a WAR deployed on a self-managed Tomcat, the admin UI is available on http://<hostname>:<port>/adchallenge/ui/admin

My default Heroku deployment for the admin UI is http://cryptic-castle-9029.herokuapp.com/ui/admin

Implemented features:
- Subscriptions notifications APIs (ORDER, CHANGE, etc.)
- Access Management APIs
- OAuth signature verification for inbound requests from AppDirect, and OAuth signing for outbound requests to AppDirect.
- OpenId Authentication   

Limitations:
- Quick UI impl - you might need to click "refresh" more often than you wish to see updated content, even if you toggle between event logs and account tabs.
- In-memory implementation of event logging and account management - a real product implementation would back this by a persistence layer like a RDBMS, likely fronted by an ORM such as Hibernate and using Spring-data. Indeed current impl would blow memory at some point...
- Proper validation of all used elements of events not thoroughly implemented - to some extent mitigated by Jersey's builtin robustness where uncaught exceptions are translated by a 500 Server Error (a real product would implement stronger input validation).
- Minimal (but working nonetheless) OAuth signature verification through a custom servlet filter, piggybacking on spring-security-oauth primitives.
- OpenId integration currently accepts all authenticated users - however real app should also authorize access based on user assignemnt on the accessed app.
- Admin page would obviously requires some authentication as well (it's slightly more effort than I could squeeze to have simultaneous authentication schemes for different URIs as I leveraged the automatically generated forms and other spring security URLs)
- Of course for a real product I'd written Unit Tests :)
