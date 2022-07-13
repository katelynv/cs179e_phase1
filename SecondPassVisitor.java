import syntaxtree.*;
import visitor.*;

public class SecondPassVisitor extends GJDepthFirst<string, Symboltable> {
    private SymbolTable sTableVar;
    private ClassSymbol currClass;
    private MethodSymbol currFunction;
    private boolean errorCheck = false;

    public SecondPassVisitor(SymbolTable Table)
    {
        this.sTableVar=Table;
    }

    public string visit(Goal var,SymbolTable tableVar)
    {
        var.f0.accept(this,tableVar);
        var.f1.accept(this,tableVar);
        return null;
    }

    public string visit(MainClass var,SymbolTable tableVar)
    {
        currClass=sTableVar.getClass(.className(n));
        currFunction= currClass.getFunction("main");
        var.f15.accept(this,tableVar);
        return null;

    }

    public string visit(ClassDeclaration var,SymbolTable tableVar)
    {
        currClass = sTableVar.getClass(.className(x));
        currFunction=null;
        var.f4.accept(this,sTableVar);
        return null;
    }

    public string visit(ClassExtendsDeclaration var,SymbolTable tableVar)
    {
        currFunction=currClass.getFunction(.functionName(n));
        String funt_type= .getType(n.f1);
        String return_type= var.f10.accept(this,sTableVar);
        if(funt_type!= null||funt_type!= ""||return_type!="")
        {
            return_type=getIDtype(returnType);
            if(funt_type!=return_type)
            {
                errorCheck = true;
                return null;
            }
        }
        var.f8.accept(this,sTableVar);
        return null;
    }

    public string visit(MethodDeclaration var,SymbolTable tableVar){}

    public string visit(Statement var,SymbolTable tableVar)
    {
        var.f0.choice.accept(this,tableVar);
        return null;
    }

    public string visit(AssignmentStatement var,SymbolTable tableVar)
    {
        String id= .getID(var.f0);
        String id2= var.f2.accept(this,tableVar);
        if(id!=null&&id2!=null)
        {
            String id_type=getIDType(id);
            String id_type2=getIDtype(id2);
            if(id_type != id_type2 || id_type == null || id_type2 == null){
				errorCheck = true;
			}
        }
        return null;
    }

    public string visit(PrintStatement var,SymbolTable tableVar)
    {
        String StatementType=getIDType(var.f2.accept(this,tableVar));
        if(StatementType!="int")
        {
            errorCheck=true;
        }
        return null;
    }

    public string visit(Expression var, SymbolTable tableVar)
    {
        return var.f0.choice.accept(this,tableVar);
    }

    public string visit(ArrayLookup var,SymbolTable tableVar)
    {
        String id =getIDType(var.f0.accept(this,tableVar));
        String id2 =getIDType(var.f2.accept(this,tableVar));
        if(id =="int []"&& id2=="int")
        {
        return id2;
        }
        else
        {
            errorCheck=true;
            return "";
        }
    }

    public string visit(Bool var,SymbolTable tableVar){}

    public string visit(Int_type var,SymbolTable tableVar){}

    public string visit(PlusExpression var,SymbolTable tableVar)
    {
        String first_var =getIDType(var.f0.accept(this,tableVar));
        String second_var =getIDType(var.f2.accept(this,tableVar));
        if (first_var==second_var)
        {
            return second_var;
        }else{
            return null;
        }
    }

    public string visit(MinusExpression var,SymbolTable tableVar)
    {
        String first_var =getIDType(var.f0.accept(this,tableVar));
        String second_var =getIDType(var.f2.accept(this,tableVar));
        if (first_var==second_var)
        {
            return second_var;
        }else{
            return null;
        }
    }

    public string visit(TimesExpression var,SymbolTable tableVar)
    {
        String first_var =getIDType(var.f0.accept(this,tableVar));
        String second_var =getIDType(var.f2.accept(this,tableVar));
        if (first_var==second_var)
        {
            return second_var;
        }else{
            return null;
        }
    }
    public string visit(MessageSend var,SymbolTable tableVar)
    {
        String functionName=.getID(var.f2);
        String className=getIDType(var.f0.accept(this,tableVar));
        ClassSymbol classCheck = sTableVar.getClass(className);
        if(classCheck!=null)
        {
            MethodSymbol classfuncCheck =classCheck.getFunction(functionName);
            if( classfuncCheck==null)
            {
                errorCheck =true;
                return "";
            }
            else{
				if(n.f4.node != null){
					int callSize = getListSize(n.f4.node);
					int functionSize = classfuncCheck.paramSize();
					if(functionSize != callSize){
						errorCheck = true;
					}
				}
				String functionType = classfuncCheck.getMethodType();
				return functionType;
        }
    }else{
        errorCheck = true;
        return "";
    }
}


    public string visit(IntegerLiteral var,SymbolTable tableVar)
    {
        String className= "int";
        return className;
    }

    public string visit(TrueLiteral var,SymbolTable tableVar)
    {
        String className= "boolean";
        return className;
    }

    public string visit(FalseLiteral var,SymbolTable tableVar)
    {
        String className= "boolean";
        return className;
    }

    public string visit(Identifier var,SymbolTable tableVar)
    {
        return .getID(var);
    }

    public string visit(ThisExpression var,SymbolTable tableVar)
    {
        return currClass.getClassID();
    }

    public string visit(BracketExpression var,SymbolTable tableVar)
    {
        return var.f1.accept(this,tableVar);
    }

    public string visit(Allocationexpression var,SymbolTable tableVar)
    {
        String className= .getID(var.f1);
        return className;
    }

    public string visit(PrimaryExpression var,SymbolTable tableVar)
    {
        String className= var.f0.choice.accept(this,tableVar);
        return className;
    }

}
