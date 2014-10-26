# IRMA mirror of scuba

This repository contains the IRMA mirror of [SCUBA](http://scuba.sourceforge.net/). It contains a small amount of IRMA specific code. In particular IRMA uses the following sub projects:

 * scuba_sc_android
 * scuba_sc_j2se
 * scuba_smartcards
 * scuba_util

## Building using Gradle (recommended)

When you are using the Gradle build system, just run

    ./gradlew install

to install the all four libraries to your local repository.

Note that you'll have to use the wrapper (instead of calling gradle directly), to make sure you can build the android library.
