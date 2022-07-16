import syntaxtree.*;
import visitor.*;

public class SecondPassVisitor extends GJDepthFirst<string, Symboltable> {
    private SymbolTable symbol_table;
    private ClassSymbol current_class;
    private FunctionSymbol current_function;
    private boolean errorCheck = false;

    public SecondPassVisitor(SymbolTable Table)
    {
        this.symbol_table=Table;
    }

    public string visit(Goal var,SymbolTable tableVar)
    {
        var.f0.accept(this,tableVar);
        var.f1.accept(this,tableVar);
        return null;
    }

    public string visit(MainClass var,SymbolTable tableVar)
    {
        current_class=symbol_table.getClass(VisitorFunctions.className(n));
        current_function= current_class.getFunction("main");
        var.f15.accept(this,tableVar);
        return null;

    }

    public string visit(ClassDeclaration var,SymbolTable tableVar)
    {
        current_class = symbol_table.getClass(VisitorFunctions.className(x));
        current_function=null;
        var.f4.accept(this,symbol_table);
        return null;
    }

   

    public string visit(MethodDeclaration var,SymbolTable tableVar){  current_function=current_class.getFunction(VisitorFunctions.methodName(n));
        String funt_type= VisitorFunctions.getType(n.f1);
        String return_type= var.f10.accept(this,symbol_table);
        if(funt_type!= null||funt_type!= ""||return_type!="")
        {
            return_type=getIDtype(returnType);
            if(funt_type!=return_type)
            {
                errorCheck = true;
                return null;
            }
        }
        var.f8.accept(this,symbol_table);
        return null;
    }

    public string visit(Statement var,SymbolTable tableVar)
    {
        var.f0.choice.accept(this,tableVar);
        return null;
    }

    public string visit(AssignmentStatement var,SymbolTable tableVar)
    {
        String id= VisitorFunctions.getId(var.f0);
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
        String functionName=VisitorFunctions.getId(var.f2);
        String className=getIDType(var.f0.accept(this,tableVar));
        ClassSymbol classCheck = symbol_table.getClass(className);
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
					int listSize = getListSize(n.f4.node);
					int functionSize = classfuncCheck.paramSize();
					if(functionSize != listSize){
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
        return VisitorFunctions.getId(var);
    }

    public string visit(ThisExpression var,SymbolTable tableVar)
    {
        return current_class.getClassID();
    }

    public string visit(BracketExpression var,SymbolTable tableVar)
    {
        return var.f1.accept(this,tableVar);
    }

    public string visit(Allocationexpression var,SymbolTable tableVar)
    {
        String className= VisitorFunctions.getId(var.f1);
        return className;
    }

    public string visit(PrimaryExpression var,SymbolTable tableVar)
    {
        String className= var.f0.choice.accept(this,tableVar);
        return className;
    }
    public boolean errors()
    {
        return errorCheck;
    }
}
