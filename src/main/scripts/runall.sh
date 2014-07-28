#!/bin/sh 
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main ADDER $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main ATOMIC $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main DIRTY $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main RWLOCK $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main TRYLOCK $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main FAIR $1 $2 10 2000000
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main SYNCHRONIZED $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main VOLATILE $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main STAMPED0 $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main STAMPED $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main STAMPED3 $1 $2
java -cp counters-benchmark-0.0.1-SNAPSHOT.jar com.takipi.tests.counters.Main STAMPED5 $1 $2
