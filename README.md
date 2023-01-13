# ConformalGeometricAlgebra

The conformal group is the group of transformations that preserve angles. This includes the rigid (euclidean) transformations. The conformal group on R^3 has a natural representation in terms of rotations in a 5-D space, with signature R^{4, 1} which means 4 basis vectors have a positive square, and 1 have a negative square. So, in the same way that projective transformations are linearised by working in a 4-D homogeneous space, conformal transformations are linearised in a 5-D space.

This repository contains two implementations of CGA in java. One is based on code generated by [Ganja.js](https://github.com/enkimute/ganja.js) and a second is based on the geometric algebra [reference implementation](https://geometricalgebra.org/reference_impl.html) of the [book Dorst2007](https://geometricalgebra.org/index.html). 

Ganja.js does is not yet able to generate code (for arbritray languages) for the inverse() function to calculate the inverse of an arbitrary multivector. The inverse-function is implemented in js only. That´s why an implementation is added based on [this](http://repository.essex.ac.uk/17282/1/TechReport_CES-534.pdf) paper.

It is planed also to add an implementation based on the [JClifford](http://jvclifford.sourceforge.net/) project. All implementations can be switched transparently for testing purposes. The API and its unit tests encapsulates the concrete underlaying implementation.

This repository includes code to decompose multivectors into geometric 3d parameters like location, attitude, radius/size/squaredSize, weight etc. and specialized classes to represent Vectors, Bivectors, Trivectors etc.

The project is used to learn about different possibilities to implement CGA and to find a good CGA api as a basis to integrate CGA based on a DSL into the Java virtual machine ([see here](https://github.com/MobMonRob/DSL4GeometricAlgebra)). The overall aim is to integrate CGA polyglott into the Java VM, supported by a complete development tool chain with syntax-highlighting and debugging etc. This work will be done based on the [Netbeans IDE](https://netbeans.apache.org/).

## Metric

The origin is defined as $\epsilon_0=0.5(\epsilon_5-\epsilon_4)$ and the point at infinity as $\epsilon_\infty=\epsilon_4+\epsilon_5$. This corresponds to ganja.js, the book [Hildenbrand2009] and also the papers [Klepper2016].
