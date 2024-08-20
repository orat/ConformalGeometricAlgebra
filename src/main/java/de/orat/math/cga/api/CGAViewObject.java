package de.orat.math.cga.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAViewObject {
    
    private long id;
    private CGAMultivector mv;
    private String label;
    private CGAViewObject parent;
    private List<CGAViewObject> children = new ArrayList<>();
    
    CGAViewObject(CGAMultivector mv, String label, CGAViewObject parent, long id){
        setParent(parent);
        this.label = label;
        this.mv = mv;
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
        // TODO
        // alle children müssen auch transformiert werden
        getCGAViewer().transform(this, motor);
    }
	public void remove() {
		throw new UnsupportedOperationException("To be implemented by subclasses.");
	}
}
