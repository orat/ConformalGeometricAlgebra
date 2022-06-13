This is the ReadMe for jclifford package, version 0.9  (May 2003)

For further informations please refer to:
     http://jvclifford.sourceforge.net
     http://sourceforge.net/projects/jvclifford


1. Overview
===========

This package provides for Clifford algebra operations.

In this package all the implementation of the Clifford element are blade-value TreeMap based.
Wrapper classes are used for blade and value manipulation and storing.
Specific implementations differ on blade wrapper class basis, in particular:

   - integer-based bynary representation of blades is used for high performance
     Clifford computation in signed spaces with dimension not higher of 8

   - BitMask-based bynary representation of blades is used for Clifford computation
     in arbitrary dimension spaces

   - TreeSet-based representation of blades is used for Clifford computation in arbitrary
     dimension spaces and expecially for graph working


2. Distribution
===============

Deploy jclifford package as an "installed optional package".

Optional packages are packages of Java classes and associated native code that application
developers can use to extend the functionality of the core platform.
The extension mechanism allows the Java virtual machine (VM) to use the optional-package classes in much
the same way as the VM uses bootstrap classes.

(Bootstrap classes are those implementing the core platform, contained in jre/lib/rt.jar and jre/lib/i18n.jar.
These include classes of the public API such as java.lang, java.io, etc., and classes supporting the
platform's internationalization/localization features.).

Like bootstrap classes, classes in optional packages do not have to be placed on the class path.
The extension mechanism also provides a way for needed optional packages to be retrieved from specified URLs
when they are not already installed in the Java 2 Runtime Environment or Java 2 SDK.

Installed optional packages are JAR files in the directory:

lib/ext       [in the Java 2 Runtime Environment]
jre/lib/ext   [in the Java 2 SDK]

Classes within JAR files in this directory can be used by applets and applications much as if they were part of
the set of bootstrap classes, without having to explicitly include them in the class path.


3. License
==========

jclifford package is licensed under the MIT License
(cf. file MIT-LICENSE).


4. Authors
==========

Realized by Giorgio Vassallo (vassallo@csai.unipa.it), Pietro Brignola (pietro.brignola@libero.it), November 2002.

Current version is: 0.9
