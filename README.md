
MASTER THESIS
===============================
"VISUALISATION OF THE GENE ONTOLOGY AND CLUSTER ANALYSIS RESULTS"
================================

ISOVIS GROUP IN  LINNEUNIVERSITETET, VÄXJÖ, SWEDEN (http://lnu.se)

SOURCE CODE REPOSITORY LOCATION: [git://github.com/vadyalex/thesis.git](git://github.com/vadyalex/thesis.git)

RELEASES LOCATION: [http://sourceforge.net/projects/go-cluster/files/releases/](http://sourceforge.net/projects/go-cluster/files/releases/)

GENE ONTOLOGY AND CLUSTER GRAPHS FOR VISUALISATION ARE AVAILABLE: [http://sourceforge.net/projects/go-cluster/files/data/](http://sourceforge.net/projects/go-cluster/files/data/)

BY VLADYSLAV ALEKSAKHIN (vladyslav.aleksakhin at gmail.com)

SUPERVISER: PROF. DR. ANDREAS KERREN (andreas.kerren at lnu.se)
CO-SUPERVISER: PH.D STUDENT ILIR JUSUFI (ilir.jusufi at lnu.se)

2009 - 2011


CONTENT:
--------------------------------

1.  Requirements

GoClusterViz is Java application make sure you have installed JRE version 5.0 or above and configured java in path.


2.  HOW TO RUN

GoClusterViz has builds for three main platform: Windows, Linux and MacOSX.

Under Windows just start:

    GoClusterViz.bat

On Linux and MacOSX run from terminal:

    GoClusterViz.sh

3.  LOAD DATA

Data graphs available on the sourceforge.net, location url is above.

Real data is located under "data" directory.

To load Gene Ontology andcluster graph use main menu:
    
    "File -> Open GO graph" and select downloaded RealGOGraph.gml
    
    
    "File -> Open cluster graph" and select RealClusterGraph.gml

4.  STARTUP PARAMETERS

It if possible to load graphs immediately after start:

    ./GoClusterViz.sh load --type=gml --go=/some/where/go.gml --cluster=/any/where/cluster.gml

    ./GoClusterViz.sh load --t=gml --g=/some/where/go.gml --c=/any/where/cluster.gml


To run using Maven

    mvn clean compile exec:java -Dexec.mainClass=GoClusterViz "-Dexec.args=load --type=gml --go=data/GOGraph.gml --cluster=data/ClusterGraph.gml"

