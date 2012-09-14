#!/bin/sh

mvn install:install-file -DgroupId=gluegen-rt -DartifactId=gluegen-rt -Dversion=2.0-beta07 -Dpackaging=jar -Dfile=gluegen-rt.jar

mvn install:install-file -DgroupId=jogl -DartifactId=jogl.all -Dversion=2.0-beta10 -Dpackaging=jar -Dfile=jogl.all.jar
mvn install:install-file -DgroupId=jogl -DartifactId=newt.all -Dversion=2.0-beta10 -Dpackaging=jar -Dfile=newt.all.jar
mvn install:install-file -DgroupId=jogl -DartifactId=nativewindow.all -Dversion=2.0-beta10 -Dpackaging=jar -Dfile=nativewindow.all.jar