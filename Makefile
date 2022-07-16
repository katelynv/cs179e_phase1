parser: minijava.jj.txt jtb.jar
		java -jar jtb.jar minijava.jj.txt
		javacc jtb.out.jj

typecheck: Typecheck.java	
		javac Typecheck.java
		java Typecheck < Factorial.java

test: Typecheck.java
		mkdir hw1
		cp Typecheck.java ClassSymbol.java ErrorMsg.java FirstPassVisitor.java FunctionSymbol.java Symbol.java SymbolTable.java VisitorFunctions.java SecondPassVisitor.java hw1/
		tar zcf hw1.tgz hw1
		rm -rf hw1
		chmod +x Phase1Tester/run
		Phase1Tester/run Phase1Tester/SelfTestCases hw1.tgz

clean folder:
	rm -rf hw1

phase1 tester: 
	java Typecheck < Phase1Tester/SelfTestCases/BinaryTree.java