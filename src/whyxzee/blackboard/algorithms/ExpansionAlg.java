package whyxzee.blackboard.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.numbers.Complex;

public class ExpansionAlg {
    private ArrayList<Complex> solutions;
    private boolean shouldRun = true; // volatile means other threads can read it

    private MathEQ eqThis;

    List<CompletableFuture<Void>> futures;

    public ExpansionAlg(MathEQ eq) {
        eqThis = eq;
        solutions = new ArrayList<Complex>();
        futures = Arrays.asList(CompletableFuture.runAsync(new RayExpand(eqThis, Complex.cmplx(0.05, 0))),
                CompletableFuture.runAsync(new RayExpand(eqThis, Complex.cmplx(-0.05, 0))));
    }

    public final ArrayList<Complex> run() {
        // runs all of this
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        allOf.join(); // blocks until all are done

        return solutions;
    }

    /**
     * 
     * @param complex
     */
    public synchronized void add(Complex complex) {
        // synchronized makes it so if multiple threads run this method, then they run
        // it one at a time and wait for the thread actively using this method to finish
        solutions.add(complex);
        System.out.println("added " + complex + "!");
    }

    class RayExpand implements Runnable {
        // if an error is thrown, then it instantly ends the runnable

        /* Variables */
        private MathEQ eq;
        private Complex dx;
        private Complex c;

        public RayExpand(MathEQ eq, Complex dx) {
            this.eq = eq;
            this.dx = dx;
            this.c = new Complex(0, 0, dx.getType());
        }

        @Override
        public void run() {
            PowerTerm zed = new PowerTerm(0);
            PowerTerm prevFofC = eq.solve("x", new PowerTerm(c.clone().add(dx.negate())));
            PowerTerm fOfC = eq.solve("x", new PowerTerm(c));

            while (shouldRun) {
                prevFofC = fOfC;
                fOfC = eq.solve("x", new PowerTerm(c));

                if (fOfC.equals(zed)) {
                    add(c.clone());
                }

                // seesaw algorithm
                // else if (Complex.inClosedRange(prevFofC.getCoef(), 0, fOfC.getCoef())) {
                // add(c.clone());
                // } else if (Complex.inClosedRange(fOfC.getCoef(), 0, prevFofC.getCoef())) {
                // add(c.clone());
                // }
                if (c.getA().abs() > 20) {
                    break;
                }
                System.out.println("dx: " + dx + " x: " + c);
                c.add(dx);
            }
        }
    }

    /**
     * A runnable that "seesaws" around the value zero. It alternates
     */
    class SeesawZero implements Runnable {
        public SeesawZero() {

        }
    }

    // Completable future is used for async

}
