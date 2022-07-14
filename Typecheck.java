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
            }
        } catch (ParseException e) {
            System.out.println("Error: Type error");
            System.exit(1);
        }
    }
}