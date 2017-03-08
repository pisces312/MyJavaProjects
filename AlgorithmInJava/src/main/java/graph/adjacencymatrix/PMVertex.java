package graph.adjacencymatrix;

import graph.IPathVertex;

public class PMVertex extends MVertex implements IPathVertex {
    public PMVertex(String name) {
        super(name);
    }

    int                 d;
    IPathVertex precedence;


    public int getDistance() {
        return d;
    }


    public void setDistance(int d) {
        this.d = d;
    }


    public IPathVertex getPrecedence() {
        return precedence;
    }


    public void setPrecedence(IPathVertex vertex) {
        this.precedence = vertex;
    }

    @Override
    public String toString() {
        if (precedence != null)
            return "<" + super.toString() + ", d:" + d + ", pre:" + precedence.getName() + ">";
        return "<" + super.toString() + ", d:" + d + ">";
    }

}
