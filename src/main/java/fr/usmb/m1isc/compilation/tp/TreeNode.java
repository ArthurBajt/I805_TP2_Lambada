package fr.usmb.m1isc.compilation.tp;

public class TreeNode {

    private String ope;
    private TreeNode expr1, expr2;


    public TreeNode(){}

    public TreeNode(String ope, TreeNode expr1, TreeNode expr2){
        this.ope = ope;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }


    @Override
    public String toString() {
        return '(' +
                ope + ' ' +
                expr1.toString() + ' ' +
                expr2.toString() +
                ')';
    }
}
