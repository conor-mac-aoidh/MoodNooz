MoodNooz
========

An Affective Information Retrieval system for online news content.

Installation
========
This project uses the apache tomcat web server to serve jsp files. A copy of the
server is bundled with the MoodNooz application. In order to start the server
you must run:

Unix: 		./tomcat/bin/startup.sh
Windows: 	./tomcat/bin/startup.bat

Similarily to shutdown:
	
Unix: 		./tomcat/bin/shutdown.sh
Windows: 	./tomcat/bin/shutdown.bat	

The project should then be accessible by accessing http://localhost:8080 in a web browser.

Note: You will probably have to update the symbolic link ./tomcat/webapps/ROOT to point to ./

Building
=======

This project uses the ant build system. To build from the root directory execute ant:

./ant

This command compiles the source and deploys a warfile to the tomcat server. After this
simply restart the tomcat server and the changes will have taken effect.
