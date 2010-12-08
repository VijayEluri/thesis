#!/bin/bash
java -Xms512M -Xmx512M -Djava.library.path=native/ -classpath GoClusterViz.jar:lib/collections-generic-4.01.jar:lib/commons-collections-3.2.jar:lib/commons-lang-2.5.jar:lib/google-collections-1.0.jar:lib/jung-algorithms-2.0.1.jar:lib/jung-api-2.0.1.jar:lib/jung-graph-impl-2.0.1.jar:lib/log4j-1.2.12.jar:lib/gluegen-rt.jar:lib/jogl.jar GoClusterViz
