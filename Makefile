parser: minijava.jj.txt jtb.jar
		java -jar jtb.jar minijava.jj.txt
		javacc jtb.out.jj

typecheck: Typecheck.java	
		javac Typecheck.java
		java Typecheck < P.java