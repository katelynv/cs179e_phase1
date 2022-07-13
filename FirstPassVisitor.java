import java.util.HashSet;
import java.util.Set;

import syntaxtree.*;
import visitor.*;

public class FirstPassVisitor extends DepthFirstVisitor {
    ErrorMsg error = new ErrorMsg();
    private SymbolTable symbol_table = new SymbolTable();
    private ClassSymbol class;
    private MethodSymbol method;

    public boolean errors() {
        return error.errors;
    }

    public SymbolTable getSymbolTable() {
        return this.symbol_table;
    }

    public void visit (Goal n) {
        NodeList classNames = new NodeList(n.f0);
        if (n.f1.size() > 0) {
            for (int i = 0; i < n.f1.size(); i++) {
                classNames.addNode(((TypeDeclaration) n.f1.elementAt(i)).f0.choice);
            }
        }
        if (Helper.checkClass(classNames)) {
            n.f0.accept(this);
            n.f1.accept(this);
        } else {
            error.sendError("ERROR: Class Names are the same");
        }
    }

    public void visit (MainClass n) {
        String className = VisitorFunctions.className(n);
        symbol_table.addClass(className);
        class = symbol_table.getClass(className);
        class.addMethod("main", "void");
        method = class.getMethod("main");
        n.f14.accept(this);
    }

    public void visit (ClassDeclaration n) {
        String className = VisitorFunctions.className(n);
        symbol_table.addClass(className);
        class = symbol_table.getClass(className);
        method = null;
        if (VisitorFunctions.checkId(n.f3)) {
            n.f3.accept(this);
        } else {
            error.sendError("ERROR: Id names are the same");
        }
        
        if (VisitorFunctions.checkMethod(n.f4)) {
            n.f4.accept(this);
        } else {
            errr.sendError("ERROR: Method names are the same");
        }
    }

    public void visit(ClassExtendsDeclaration n) {
        string className = VisitorFunctions.className(n);
        symbol_table.addClass(className);
        class = symbol_table.getClass(className);
        String temp_class = VisitorFunctions.getClass(n.f3);
        ClassSymbol temp_classSymbol = symbol_table.getClass(temp_class);
        Set<String> temp_methods = temp_classSymbol.getMethodNames();
        if (VisitorFunctions.checkSetContains(temp_methods, n.f6)) {
            if (VisitorFunctions.checkId(n.f5)) {
                n.f5.accept(this);
            } else {
                error.sendError("ERROR: Id names are the same");
            }

            if (VisitorFunctions.checkMethod(n.f6)) {
                n.f6.accept(this);
            } else {
                error.sendError("ERROR: Method names are the same");
            }
        } else {
            error.sendError("ERROR: Overloading method");
        }
    }

    public void visit(MethodDeclaration n) {
        class.addMethod(VisitorFunctions.methodName(n), VisitorFunctions.methodType(n));
        method = class.getMethod(VisitorFunctions.methodName(n));
        if (n.f4.node != null) {
            if (VisitorFunctions.checkParameter((FormalParameterList) n.f4.node)) {
                n.f4.accept(this);
            } else {
                error.sendError("ERROR: Parameter names are the same");
            }
        }

        if (VisitorFunctions.checkId(n.f7)) {
            n.f7.accept(this);
        } else {
            error.sendError("ERROR: Id names are the same");
        }
    }

    public void visit(VarDeclaration n) {
        if (method == null && class != null) {
            class.addFields(VisitorFunctions.getId(n.f1), VisitorFunctions.getType(n.f0));
        } else if (method != null) {
            method.addLocal(VisitorFunctions.getId(n.f1), VisitorFunctions.getType(n.f0));
        }
    }

    public void visit(FormalParameter n) {
        if (method != null && class != null) {
            method.addParam(VisitorFunctions.getId(n.f1), VisitorFunctions.getType(n.f0));
        }
    }
}


