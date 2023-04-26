public class Add {
    public static void main(String[] args) {
	String[] keyValuePairs = args[0].split("&");
	String firstOperand = keyValuePairs[0].split("=")[1];
	String secondOperand = keyValuePairs[1].split("=")[1];
	int a = Integer.parseInt(firstOperand);
	int b = Integer.parseInt(secondOperand);
	int sum = a + b;
	System.out.println("{ \"answer\": "+sum+", \"a\":"+a+", \"b\":"+b+" }");
    }
}
