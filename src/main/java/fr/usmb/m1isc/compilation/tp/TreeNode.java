package fr.usmb.m1isc.compilation.tp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TreeNode {

    private String ope;
    private TreeNode expr1, expr2;



    public TreeNode(){}

    public TreeNode(String ope, TreeNode expr1, TreeNode expr2){
        this.ope = ope;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public ArrayList<String> getVars() {
        ArrayList<String> vars = new ArrayList<>();
        if (ope == "let") {
            vars.add((String) ((Leaf)expr1).getValue());
        } else {
            ArrayList<String> expr1Vars = expr1.getVars();
            expr1Vars.forEach(var -> vars.add(var));
            if(expr2 != null) {
                ArrayList<String> expr2Vars = expr2.getVars();
                expr2Vars.forEach(var -> vars.add(var));
            }
        }
        return vars;
    }

    public String getCode(){
        String code = "";
        switch(ope){
            case "let":
                code += expr2.getCode();
                code += "\tmov "+((Leaf)expr1).getValue()+", eax\n";
                break;
            case "*":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tmul eax, ebx\n";
                break;
            case "/":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tdiv ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case ";":
                code += expr1.getCode();
                if(expr2 != null){
                    code += expr2.getCode();
                }
        }
        return code;
    }

    public String compile(){
        ArrayList<String> vars = this.getVars();
        String dataSegment = "DATA SEGMENT\n";
        for(int i = 0; i < vars.size(); ++i){
            dataSegment += "\t"+vars.get(i)+" DD\n";
        }
        dataSegment += "DATA ENDS\n";
        String codeSegment = "CODE SEGMENT\n";
        codeSegment += this.getCode();
        codeSegment += "CODE ENDS\n";
        return dataSegment+codeSegment;
    }

    @Override
    public String toString() {
        String str = "";
        str += '(';
        str += ope + ' ';
        str += expr1.toString();
        if(expr2 != null){
            str += ' ';
            str += expr2.toString();
        }
        str += ')';
        return str;
    }
}
