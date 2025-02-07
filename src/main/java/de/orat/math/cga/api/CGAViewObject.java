package de.orat.math.cga.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAViewObject {
    
    private long id; // id != -1, wenn das Objekt im viewer visualisiert werden kann, else wenn es nur der parent von children-objekten ist
    private CGAMultivector mv;
    private String label;
    private CGAViewObject parent;
    private List<CGAViewObject> children;
    
    CGAViewObject(CGAMultivector mv, String label, CGAViewObject parent, long id){
        this.children = new ArrayList<>();
        setParent(parent);
        this.label = label;
        this.mv = mv;
        this.id = id;
    }
    final void setParent(CGAViewObject parent){
        this.parent = parent;
    }
    void addChild(CGAViewObject child){
        children.add(child);
    }
    long getId(){
        return id;
    }
    CGAViewObject addCGAObject(CGAViewObject parent, CGAKVector m, String label){
        throw new RuntimeException("Invocation only of the overwritten method in CGAAviewer allowed!");
    }
    public CGAViewObject addCGAObject(CGAKVector m, String label){
        if (parent != null){
            return parent.addCGAObject(this, m, label);
        } else {
            return addCGAObject(this, m, label);
        }
    }
    
    CGAViewer getCGAViewer(){
        if (parent != null){
            return parent.getCGAViewer();
        } else return (CGAViewer) this;
    }
    
    public CGAViewObject addCGAObject(CGAKVector m, String label, Color color){
        return getCGAViewer().addCGAObject(this, m, label, color);
    }

    public void transform(CGAScrew motor){
        CGAViewer viewer = getCGAViewer();
        if (id != -1) viewer.transform(this, motor);
        for (CGAViewObject obj: children){
            viewer.transform(obj, motor);
        }
    }
    
    public void remove() {
        System.out.println("remove \""+this.label+"\"!");
        CGAViewer viewer = getCGAViewer();
        if (id != -1) viewer.remove(id);
        for (CGAViewObject obj: children){
            viewer.remove(obj.getId());
        }
    }
}