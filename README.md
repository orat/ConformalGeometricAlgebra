# (Oriented) Conformal Geometric Algebra

This repository is my playground to learn geometric algebra and to test code-snipsets. It is focussed on conformal geometry algebra only, because I work with this model in the context of robotics where it is very useful to have the round objects of CGA available to model inverse kinematics. The conformal group is the group of transformations that preserve angles. This includes the rigid (euclidean) transformations. The conformal group on $R^3$ has a natural representation in terms of rotations in a 5D-space, with signature $R^{4, 1}$ which means 4 basis vectors have a positive square, and 1 have a negative square. So, in the same way that projective transformations are linearised by working in a 4D-homogeneous space, conformal transformations are linearised in a 5D-space.

## Disclaimer
The project is not intended to be used in real world applications but is is helpful to learn about geometric algebra, especially definition of signs, conversion to euclidean vector algebra, ... If you have feedback or feature suggestions, please create a new GitHub Issue.

Changes in the API are planned e.g. to correct some object namings and there are still known but not fixed bugs and sign-definition problems. This software is implemented for learning purposes only. It is not recommended to use it as an GA-implementation for application development.

## Description
This repository contains two implementations of CGA in the Java programming language. One is based on code generated by [ganja.js](https://github.com/enkimute/ganja.js) and a second is based on the geometric algebra [reference implementation](https://geometricalgebra.org/reference_impl.html) of the book [Dorst2007](https://geometricalgebra.org/index.html). 

[ganja.js](https://github.com/enkimute/ganja.js) does is not yet able to generate code (for arbritray languages) for the inverse() function to calculate the inverse of an arbitrary multivector. The inverse-function is implemented in js only. That´s why an implementation is added based on [this](http://repository.essex.ac.uk/17282/1/TechReport_CES-534.pdf) paper.

It is planed also to add an implementation based on the [JClifford](http://jvclifford.sourceforge.net/) project. All implementations can be switched transparently for testing purposes. The API and its unit tests encapsulates the concrete underlaying implementation.

This repository includes code to decompose multivectors into geometric 3d-parameters like location, attitude, radius/size/squaredSize, weight etc. and specialized classes to represent Vectors, Bivectors, Trivectors etc.

The project is used to learn about different possibilities to implement CGA and to find a good CGA API as a basis to integrate CGA based on a DSL into the Java virtual machine ([see here](https://github.com/MobMonRob/DSL4GeometricAlgebra)). The overall aim is to integrate CGA polyglott into the Java VM, supported by a complete development tool chain with syntax-highlighting and debugging etc. This work will be done based on the [Netbeans IDE](https://netbeans.apache.org/).

CGA can be implemented based on different metrics corresponding to different definitions of the origin and the point at infinity. Different metrics results in different formulas for composition of CGA-vectors and in the formulas for determining lenghts and angles. In this API the origin is defined as $\epsilon_0=0.5(\epsilon_--\epsilon_+)$ and the point at infinity as $\epsilon_\infty=\epsilon_+ +\epsilon_-$ with $\epsilon_-^2=-1$ and $\epsilon_+^2=1$. This corresponds to the book [Hildenbrand2013](https://link.springer.com/book/10.1007/978-3-642-31794-1) and also the paper [Kleppe 2016](https://www.researchgate.net/publication/301598674_Inverse_Kinematics_for_Industrial_Robots_using_Conformal_Geometric_Algebra).

## Dependencies Setup
The project depends on the [vecmath library](https://download.java.net/media/java3d/javadoc/1.4.0/javax/vecmath/package-summary.html) in the refactured version of [jogamp](https://jogamp.org/deployment/java3d/1.7.1-build-20200222/vecmath.jar). Unfortunately there is no maven repository available. That is why you need to download the jar file manually and add it as a local depency of the project. To do this in the nebeans ide: Right-click on the depencies of the project and add the dependency manually. The group id is "org.jogamp.java3d", the artifactId is "vecmath" and the type is "jar". If the dependency is already defined right-click on the dependency to manually add the needed jar-file.\ 
Alternatively clone it from [GitHub](https://github.com/JogAmp/vecmath/tree/dev1.7.1), update the compiler version in it's pom.xml and build it.

Clone and checkout

1. [GeometricAlgebra](https://github.com/orat/GeometricAlgebra)
2. [Euclid3dViewAPI](https://github.com/orat/Euclid3DViewAPI)

and build those projects to have them available in your local Maven cache. 

If you want to use the the default Visualizer, you have to clone and checkout [Euclid3dView](https://github.com/orat/EuclidView3d) and add it to your runtime dependencies.

## GA Implementation Setup
The GA imlemenntation can be switched by project configuration. In the Maven pom.xml file there is a property <cga.impl>. If the value is set to "default" then the reference implementation corresponding to the book [Dorst2007](https://geometricalgebra.org/index.html) is used. With "ganja" you can switch to an implementation with the nondegenerate metric $R^{4, 1}$ created by the code generator from [ganja.js](https://github.com/enkimute/ganja.js). Further implementations are work in progress.

## Useful formulas
### Formulae to compose conformal geometric objects
#### Geometric objects in outer product null space representation
| description | formula | grade | class |
| :---------- | :------ | :----| :-------- |
| Point pair from  two conformal points (p1, p2) | p1&#8743;p2 | 2 | round |
| (Flat) Finite-infinite point pair or Flat point from  one conformal point (p) | p&#8743;&#x03B5;&#7522; | 2 | flat |
| Circle from three ipns Points (p1, p2, p3) | p1&#8743;p2&#8743;p3 | 3 |  round |
| Line from two conformal planes (p1, p2) | p1&#8743;p2&#8743;&#x03B5;&#7522; | 3 | flat |
| Sphere from four ipns points (p1, p2, p3, p4) | p1&#8743;p2&#8743;p3&#8743;p4| 4 |  round |
| Plane from three ipns points (p1, p2, p3) | p1&#8743;p2&#8743;p3&#8743;&#x03B5;&#7522;| 4 | flat |
| Plane between two ipns points (p1, p2) | $ (\vec{p}_1\wedge\vec{p}_2)*\wedge\epsilon_\infinity $ | 4 | flat |

The conformal points in the table above have to be given in inner product null space represenation.


#### Geometric objects in inner product null space representation (dual)
| description | formula | grade |
| :---------- | :------ | :----|
| Point from euclidian vector (x) | x+0.5x&sup2;&#x03B5;&#7522;+&#x03B5;&#8320; | 1 |
| Sphere from conformal point (P) and radius (r) | P-0.5r&sup2;&#x03B5;&#7522; | 1 |
| Plane from euclidian normal vector (n) and distance to origin (d) | n+d&#x03B5;&#7522; | 1 |
| Circle from two conformal spheres (s1, s2) | s1&#8743;s2 | 2 |
| Line from two conformal planes (p1, p2) | p1&#8743;p2 | 2 |
| Point pair from  three conformal spheres (s1, s2, s3) | s1&#8743;s2&#8743;s3 | 3 |


### Formulae to decompose conformal object representations
| description | formula |
| :---------- | :------ |
| Backprojection of a conformal point (P) into an euclidian vector. The formula in the first bracket normalizes the point. Then this normalized point is rejected from the minkowski plane. | (P/(P&#x22C5;&#x03B5;&#7522;))&#x2227;E&#8320;E&#8320;&#x207B;&#x00B9; |
| Location of a round (X) or a tangent (X) represented in 3d coordinates | -0.5(X&#x03B5;&#7522;X)/(&#x03B5;&#7522;&#8901;X)&sup2; |
| Direction vector (attitude) of a dual line (L*) represented as 3d coordinates of (&#949;&#8321;, &#949;&#8322;, &#949;&#8323;). | (L*&#8901;&#x03B5;&#8320;)&#8901;&#x03B5;&#7522; |
| Radius (r) of a conformal sphere (S) | r&#x00B2; = (S&#x002A;)&#x00B2; = S&#x002A;&#x22C5;S&#x002A; |
| Distance (d) between the the center of a conformal sphere (S) and a conformal point (P) | d&#x00B2; = S&#x22C5;S-2S&#x22C5;P |


### Formulae to implement base functionalitity of CGA
| description | formula |
| :---------- | :------ |
| Matrix free implementation of the inverse | x&#x207B;&#x00B9; =  (x&#x2020; x&#x5e; x&#x02DC; negate14(x)(x x&#x2020; x&#x5e; x&#x02DC;))/(x x&#x2020; x&#x5e; x&#x02DC; negate14(x) (x x&#x2020; x&#x5e; x&#x02DC;)) |


### General useful equations
| name | equation | description |
| :---------- | :------------------ | ---------------------- |
| anticommutivity | u &#8743; v = - (v &#8743; u) | |
| distributivity | u &#8743; (v + w) = u &#8743; v + u &#8743; w | |
| associativity | u &#8743; (v &#8743; w) = (u &#8743; v) &#8743; w | |
| | (A &#8970; B)&#732; = B&#732; C&#8743; A&#732; | |
| | A &#8743; B * C = A * (B &#8971; C) | |
| | C * (B &#8743; A) = (C &#8970; B) * A | |
| intersection | (A &#x2228; B)* = B* &#8743; A* | Intersection = outer product in the ipns representation; B* &#8743; A* means computing the union of everything which is not B and everything that is not A. The dual of that must be what have A and B in common.|
| projection | (A &#x230B; B) B&#x207B;&#x00B9; | Projection of A onto B |
| rejection | (A &#x2227; B) B&#x207B;&#x00B9; | Rejection of A from B |
| duality | (A &#x230B; B)* = A ∧ B* | |
|| A &#x230B; (B &#x230B; C) = (A ∧ B) &#x230B; C ||
|| (A &#x230B; B) &#x230B; C = A ∧ (B &#x230B; C) | if C contains A|
|down projection| (&#x03B5;&#x1D62; ∧ &#x03B5;&#x2080;) &#x230B; (X ∧ (&#x03B5;&#x1D62; ∧ &#x03B5;&#x2080;))| extracts the pure euclidean part of the given multivector |
|| $P=\frac{1}{2}(\epsilon_0+L \epsilon_0 L))$ | Determines an arbitrary point $P$ on a line $L$ by reflecion of $\epsilon_0$ on the line. The midpoint between $\epsilon_0$ and its reflection $L \epsilon_0 L$, lays on the line $L$. This is equivalent to projecting the point $\epsilon_0$ onto the line $L$. |
