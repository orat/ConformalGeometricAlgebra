package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * * 
 * Die Wirklinie oder Wirkungslinie ist in der Technischen Mechanik die Gerade, 
 * die die Lage einer Kraft im Raum beschreibt. Daher werden in CGA Kräfte durch
 * Linien beschrieben.<p>
 * 
 * Zusammen mit dem Richtungssinn ergibt sie die Richtung. Durch Angabe des 
 * Betrages, der Richtung und des Angriffspunktes kann ein Kraftvektor 
 * beschrieben werden.<p>
 * FIXME Formulierung behagt mir nicht: Warum wird der Kraftangriffspunkt zum 
 * Kraftvektor gezählt?<p>
 * 
 * FIXME CGALine ist doch eigentlich ein Pfeil, weight der Line ist die Länge und das
 * Vorzeichen die Richtung, Pfeile können überall auf der Linie starten, daher ist
 * kein Punkt enthalten. Und die Darstellung ist eindeutig egal auf Basis welchen
 * Punkts sie erzeugt wird. Wie passt das mit Plückerkoordinaten zusammen?
 * Andererseits können Linien mit Ebenen geschnitten werden, d.h. sie werden dann
 * als unendlich lang angesehen
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAForceIPNS extends CGALineIPNS {
    
    public CGAForceIPNS(Point3d location, Vector3d force) {
        super(location, normalize(force), force.length());
    }
    
    private static Vector3d normalize(Vector3d force){
        Vector3d normalizedForce = new Vector3d(force);
        normalizedForce.normalize();
        return normalizedForce;
    }
    
    public CGAForqueIPNS add(CGAForceIPNS force){
        return new CGAForqueIPNS(super.add(force));
    }
    public CGAForqueIPNS sub(CGAForceIPNS force){
        return new CGAForqueIPNS(super.sub(force));
    }
}
