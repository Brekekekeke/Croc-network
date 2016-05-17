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
PORTSERVEUR=1351

tryAgain: clear build startServer


build: buildEngine buildNet

buildEngine: buildController buildException buildModel

buildNet: buildHeaders buildClient buildServer


buildController:
	javac engine/controller/*.java
	jar cvf $(TESTENV)/controller.jar engine/controller/*.class
	
buildException:
	javac engine/exceptions/*.java
	jar cvf $(TESTENV)/exceptions.jar engine/exceptions/*.class

buildModel:
	javac engine/models/*.java
	jar cvf $(TESTENV)/models.jar engine/models/*.class
	
buildHeaders:
	javac $(NET)/$(HEAD)/*.java
	mkdir $(RUNHEAD)
	jar cvf $(RUNHEAD)/computeInterface.jar $(NET)/$(HEAD)/*.class

buildServer:
	javac -cp $(TESTENV)/controller.jar:$(TESTENV)/exceptions.jar:$(TESTENV)/models.jar:$(RUNHEAD)/computeInterface.jar $(NET)/$(SERV)/*.java
	mkdir $(RUNSERV)
	jar cvfm $(RUNSERV)/server.jar $(CONFDIR)/ServerManifest.MF $(NET)/$(SERV)/*.class
	
buildClient:
	javac -cp  $(TESTENV)/models.jar:$(RUNHEAD)/computeInterface.jar $(NET)/$(CLIE)/Client.java $(NET)/$(CLIE)/GameStateImpl.java
	mkdir $(RUNCLIE) || true
	jar cvfm $(RUNCLIE)/client.jar $(CONFDIR)/ClientManifest.MF $(NET)/$(CLIE)/*.class
	#mkdir $(CLIENTPUBLIC)
	#mkdir $(CLIENTPUBLIC)/classes
	#mkdir $(CLIENTPUBLIC)/classes/client
	#cp  $(CLIE)/Truc.class $(CLIENTPUBLIC)/classes/client/



startRegistry:
	rmiregistry $(PORTSERVEUR) &

# Utiliser le jar: Could not find or load main class
startServer: 
	#startRegistry
	#mkdir $(TESTENV)/publicserver
	java -Djava.rmi.server.codebase=file:$(RUNHEAD)/computeInterface.jar -Djava.security.policy=$(CONFDIR)/client.policy $(NET).$(SERV).ComputeEngine $(PORTSERVEUR)
	#java -cp $(RUNSERV)/server.jar -Djava.rmi.server.codebase=file:$(RUNHEAD)/computeInterface.jar -Djava.security.policy=$(CONFDIR)/client.policy $(RUNSERV)/server.jar $(PORTSERVEUR)
	#-Djava.rmi.server.hostname=$(TESTENV)/publicserver


startClient:
	java -cp  $(TESTENV)/models.jar:$(RUNCLIE)/client.jar:$(RUNHEAD)/computeInterface.jar -Djava.rmi.server.codebase=file:$(CLIENTPUBLIC)/classes/client/ $(NET).client.Client $(PORTSERVEUR)
	#java -cp $(TESTENV)/computeInterface.jar -Djava.rmi.server.codebase=$(CLIENTPUBLIC)/classes/client/ -Djava.security.policy=exec/pol/client.policy $(RUNCLIE)/client.jar 5
	#$(TESTENV)/publicserver



clean: cleanHeader cleanServer cleanClient

cleanHeader:
	rm $(NET)/$(HEAD)/*.class || true

cleanServer:
	rm $(NET)/$(SERV)/*.class || true

cleanClient:
	rm  $(NET)/$(CLIE)/*.class || true

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
