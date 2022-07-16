//Should be our main file
import syntaxtree.*;

public class Typecheck {
    public static void main(String[] args) {
        try {
            MiniJavaParser parser = new MiniJavaParser(System.in);
            Node root = parser.Goal();
            FirstPassVisitor first = new FirstPassVisitor();
            root.accept(first);
            if (first.errors()) {
                System.out.println("Error!!!");
                System.exit(1);
            } else {
                SecondPassVisitor second = new SecondPassVisitor(first.getSymbolTable());
                root.accept(second, first.getSymbolTable());
                if (second.errors()) {
                    System.out.println("Type error");
                    System.exit(1);
                } else {
                    System.out.println("Program type checked successfully");
                }
            }
        } catch (ParseException e) {
            System.out.println("Type error");
            System.exit(1);
        }
    }
}