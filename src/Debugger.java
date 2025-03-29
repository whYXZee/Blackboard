import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Variable;

public class Debugger {
    public static void main(String[] args) throws Exception {
        Random rng = new Random();
        try {
            FileWriter io = new FileWriter("output.txt");
            Variable var = new Variable('x', 2, 1) {
            };

            PolynomialTerm poly_1 = new PolynomialTerm(5, var);
            PolynomialTerm poly_2 = new PolynomialTerm(3, var);

            ArrayList<PolynomialTerm> polynomials = new ArrayList<PolynomialTerm>() {
                {
                    add(poly_1);
                    add(poly_2);
                }
            };
            io.write("sum: " + PolynomialTerm.add(polynomials));
            io.close();
        } catch (IOException e) {
            System.out.println("io failed to initialize");
        }
    }
}
