package fr.usmb.m1isc.compilation.tp;

public class Leaf extends TreeNode{

    private Object value;

    public Leaf(Object value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
