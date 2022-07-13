import syntaxtree.*;
import visitor.*;

public class SecondPassVisitor extends GJDepthFirst<string, Symboltable> {
    private SymbolTable sTable;
    private ClassSymbol classC;
    private MethodSymbol currFunction;
    private boolean errorCheck = false;

    public SecondPassVisitor(SymbolTable Table){}

    public string visit(Goal x,){}

    public string visit(MainClass x,){}

    public string visit(ClassDeclaration x,){}

    public string visit(ClassExtendsDeclaration x,){}

    public string visit(MethodDeclaration x,){}

    public string visit(Statement x,){}

    public string visit(AssignmentStatement x,){}

    public string visit(PrintStatement x,){}

    public string visit(Expression x, ){}

    public string visit(ArrayLookup x,){}

    public string visit(Bool x,){}

    public string visit(Int_type x,){}

    public string visit(PlusExpression x,){}

    public string visit(MinusExpression x,){}

    public string visit(TimesExpression x,){}

    public string visit(MessageSend x,){}

    public string visit(IntegerLiteral x,){}

    public string visit(TrueLiteral x,){}

    public string visit(FalseLiteral x,){}

    public string visit(Identifier x,){}

    public string visit(ThisExpression x,){}

    public string visit(BracketExpression x,){}

    public string visit(Allocationexpression x,){}

    public string visit(PrimaryExpression x,){}

}
