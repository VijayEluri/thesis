#!/bin/sh
java -Xms512M -Xmx512M -Djava.library.path=native/ -jar lib/GoClusterViz.jar load --type=gml --go=GOGraph.gml --cluster=ClusterGraph.gml