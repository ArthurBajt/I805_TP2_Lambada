package fr.usmb.m1isc.compilation.tp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TreeNode {

    private String ope;
    private TreeNode expr1, expr2;
    private static int NB_WHILE = 0;
    private static int NB_IF = 0;
    private static int NB_COND = 0;




    public TreeNode(){}

    public TreeNode(String ope, TreeNode expr1, TreeNode expr2){
        this.ope = ope;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public TreeNode getExpr1(){
        return expr1;
    }

    public TreeNode getExpr2(){
        return expr2;
    }

    public ArrayList<String> getVars() {
        ArrayList<String> vars = new ArrayList<>();
        if (ope == "let") {
            vars.add((String) ((Leaf)expr1).getValue());
        } else {
            ArrayList<String> expr1Vars = expr1.getVars();
            for(int i = 0; i < expr1Vars.size(); ++i){
                String var = expr1Vars.get(i);
                if(vars.indexOf(var)==-1){
                    vars.add(var);
                }
            }
            if(expr2 != null) {
                ArrayList<String> expr2Vars = expr2.getVars();
                for(int i = 0; i < expr2Vars.size(); ++i){
                    String var = expr2Vars.get(i);
                    if(vars.indexOf(var)==-1){
                        vars.add(var);
                    }
                }
            }
        }
        return vars;
    }

    public String getCode(){
        String code = "";
        int no_cond = 0;
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
            case "+":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tadd eax, ebx\n";
                break;
            case "/":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tdiv ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "-":
                code += expr1.getCode();
                code += "\tpush eax\n";
                if(expr2 != null){
                    code += expr2.getCode();
                    code += "\tpop ebx\n";
                } else {
                    code += "\tmov ebx, 0\n";
                }
                code += "\tsub ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "%":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tpush ebx\n";
                code += "\tdiv ebx, eax\n";
                code += "\tmul eax, ebx\n";
                code += "\tpop ebx\n";
                code += "\tsub ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "output":
                code += expr1.getCode();
                code += "\tout eax\n";
                break;
            case ";":
                code += expr1.getCode();
                if(expr2 != null) {
                    code += expr2.getCode();
                }
                break;
            case "while":
                int no_while = ++NB_WHILE;
                code += "debut_while_" + no_while + ":\n";
                code += expr1.getCode();
                code += "\tjz sortie_while_" + no_while + "\n";
                code += expr2.getCode();
                code += "\tjmp debut_while_" + no_while + "\n";
                code += "sortie_while_" + no_while + ":\n";
                break;
            case "if":
                int no_if = ++NB_IF;
                code += expr1.getCode();
                code += "\tjz else_" + no_if + "\n";
                code += expr2.getExpr1().getCode();
                code += "\tjmp sortie_if_" + no_if + "\n";
                code += "else_" + no_if + ":\n";
                code += expr2.getExpr2().getCode();
                code += "sortie_if_" + no_if +":\n";
                break;
            case "<":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjle faux_cond_" + no_cond + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_cond_" + no_cond + "\n";
                code += "faux_cond_" + no_cond + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_cond_" + no_cond + ":\n";
                break;
            case "<=":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjl faux_cond_" + no_cond + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_cond_" + no_cond + "\n";
                code += "faux_cond_" + no_cond + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_cond_" + no_cond + ":\n";
                break;
            case ">":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjge faux_cond_" + no_cond + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_cond_" + no_cond + "\n";
                code += "faux_cond_" + no_cond + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_cond_" + no_cond + ":\n";
                break;
            case ">=":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjg faux_cond_" + no_cond + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_cond_" + no_cond + "\n";
                code += "faux_cond_" + no_cond + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_cond_" + no_cond + ":\n";
                break;
            case "not":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tjnz faux_cond_" + no_cond + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_cond_" + no_cond + "\n";
                code += "faux_cond_" + no_cond + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_cond_" + no_cond + ":\n";
                break;
            case "or":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tjnz sortie_cond" + no_cond + "\n";
                code += expr2.getCode();
                code += "sortie_cond" + no_cond + ":\n";
                break;
            case "and":
                no_cond = ++NB_COND;
                code += expr1.getCode();
                code += "\tjz sortie_cond" + no_cond + "\n";
                code += expr2.getCode();
                code += "sortie_cond" + no_cond + ":\n";
                break;
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
