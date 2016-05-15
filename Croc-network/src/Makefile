NET=net
HEAD=headers
SERV=server
CLIE=client

TESTENV=exec
RUNHEAD=$(TESTENV)/headers
RUNSERV=$(TESTENV)/server
RUNCLIE=$(TESTENV)/client
CLIENTPUBLIC=$(TESTENV)/public
CONFDIR=$(TESTENV)/config
PORTSERVEUR=1346

tryAgain: clear build startServer

build: buildHeaders buildServer buildClient

buildHeaders:
	javac $(NET)/$(HEAD)/ComputeInterface.java $(NET)/$(HEAD)/GameStateInterface.java 
	mkdir $(RUNHEAD)
	jar cvf $(RUNHEAD)/computeInterface.jar $(NET)/$(HEAD)/*.class

buildServer:
	javac -cp $(RUNHEAD)/computeInterface.jar $(NET)/$(SERV)/ComputeEngine.java
	mkdir $(RUNSERV)
	jar cvf $(RUNSERV)/server.jar $(NET)/$(SERV)/*.class
	
buildClient:
	javac -cp $(RUNHEAD)/computeInterface.jar $(NET)/$(CLIE)/Client.java $(NET)/$(CLIE)/GameStateImpl.java
	mkdir $(RUNCLIE)
	jar cvfm $(RUNCLIE)/client.jar $(CONFDIR)/Manifest.txt $(NET)/$(CLIE)/*.class
	#mkdir $(CLIENTPUBLIC)
	#mkdir $(CLIENTPUBLIC)/classes
	#mkdir $(CLIENTPUBLIC)/classes/client
	#cp  $(CLIE)/Truc.class $(CLIENTPUBLIC)/classes/client/
	
startRegistry:
	rmiregistry $(PORTSERVEUR) &
	
startServer: 
	#startRegistry
	#mkdir $(TESTENV)/publicserver
	java -Djava.rmi.server.codebase=file:$(RUNHEAD)/computeInterface.jar -Djava.security.policy=$(CONFDIR)/client.policy $(NET).$(SERV).ComputeEngine
	#-Djava.rmi.server.hostname=$(TESTENV)/publicserver
	
startClient:
	java -cp $(RUNCLIE)/client.jar:$(RUNHEAD)/computeInterface.jar -Djava.rmi.server.codebase=file:$(CLIENTPUBLIC)/classes/client/ $(NET).client.Client 5
	#java -cp neXt/client:$(TESTENV)/computeInterface.jar -Djava.rmi.server.codebase=$(CLIENTPUBLIC)/classes/client/ -Djava.security.policy=exec/pol/client.policy $(CLIE)/Client.class 5
	#$(TESTENV)/publicserver

cleanHeader:
	rm $(NET)/$(HEAD)/*.class || true
cleanServer:
	rm $(NET)/$(SERV)/*.class || true
cleanClient:
	rm  $(NET)/$(CLIE)/*.class || true

clean: cleanHeader cleanServer cleanClient

clear: clean
	rm exec/*.*  || true
	rm -r $(RUNHEAD) || true
	rm -r $(RUNSERV) || true
	rm -r $(RUNCLIE) || true
	#rm $(CLIENTPUBLIC)/classes/client/*  || true
	#rmdir $(CLIENTPUBLIC)/classes/client
	#rmdir $(CLIENTPUBLIC)/classes
	#rmdir $(CLIENTPUBLIC)
	#touch $(TESTENV)/publicserver/a
	#rm $(TESTENV)/publicserver/*
	#rmdir $(TESTENV)/publicserver
