p1: # lists the inline comments in a java file: //
	grep -P "\/\/" BaseGraph.java
p2: # lists the opening comment blocks in a java file: /*
	grep -P "\/\*" BaseGraph.java	
p3: # lists the public constructors in a java file
	grep -P "public ([A-Z])\w+(\()+.*" BaseGraph.java	
p4: # lists the public java method definitions in a java file
	grep -P "public (.*)?\((.*){" BaseGraph.java
p5: # lists the protected java fields in a java file
	grep -P "protected (.*);" BaseGraph.java