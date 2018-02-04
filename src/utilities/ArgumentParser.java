/*
    Lukasz Lepak, 277324
    AAL 17Z, projekt
    Tytuł projektu: Generacja spirali ze zbioru punktów
    prowadzący: dr inż. Tomasz Gambin
 */
package utilities;

import algorithms.ConvexHullAlgorithm;
import algorithms.RisingRadiusAlgorithm;
import algorithms.SpiralAlgorithm;
import exceptions.WrongArgumentException;
import model.Point;
import view.SpiralView;

import java.util.*;

public class ArgumentParser {

    private List<String> args;

    public ArgumentParser(List<String> args) {
        this.args = args;
    }

    private SpiralAlgorithm chooseAlgorithm(String alg) throws WrongArgumentException {
        switch (alg) {
            case "-aCH":
                return new ConvexHullAlgorithm();
            case "-aRR":
                return new RisingRadiusAlgorithm(36);
            default:
                throw new WrongArgumentException("Wrong algorithm!");
        }
    }

    private void readDataAndDraw() throws WrongArgumentException {
        Scanner s = new Scanner(System.in);
        int dataSize = s.nextInt();
        if (dataSize < 3)
            throw new WrongArgumentException("Minimum three points have to be chosen!");
        Set<Point> points = new HashSet<>(dataSize);
        for (int i = 0; i < dataSize; ++i) {
            s.nextLine();
            double x = s.nextDouble();
            double y = s.nextDouble();
            points.add(new Point(x, y));
        }
        List<Point> distinctPoints = new ArrayList<>(points);
        String secondArg = args.get(1);
        SpiralAlgorithm sa = chooseAlgorithm(secondArg);
        SpiralView sv = new SpiralView(sa.findSpiral(distinctPoints));
        sv.drawSpiral();
    }

    private void generateAndDraw() throws WrongArgumentException {
        if (args.size() < 3)
            throw new WrongArgumentException("Not enough arguments!");
        String algorithm = args.get(1);
        String amount = args.get(2);
        int dataAmount = parseInt(amount, "-n");
        if (dataAmount < 3)
            throw new WrongArgumentException("Minimum three points have to be chosen!");
        List<Point> points = new PointGenerator().generatePoints(dataAmount);
        if (args.contains("-si")) {
            System.out.println(points.size());
            points.stream().forEach(p -> System.out.println(p.getX() + " " + p.getY()));
            System.out.println();
        }
        SpiralAlgorithm sa = chooseAlgorithm(algorithm);
        SpiralView sv = new SpiralView(sa.findSpiral(points));
        if (args.contains("-so")) {
            System.out.println(sa.getSpiralPoints().size());
            sa.getSpiralPoints().stream().forEach(p -> System.out.println(p.getX() + " " + p.getY()));
            System.out.println();
        }
        sv.drawSpiral();
    }

    private void doCalculations() throws WrongArgumentException {
        if (args.size() < 5)
            throw new WrongArgumentException("Not enough arguments!");
        String startSize = args.get(1);
        int start = parseInt(startSize, "-n");
        if (start < 3)
            throw new WrongArgumentException("Minimum three points have to be chosen!");
        String stepSize = args.get(2);
        int step = parseInt(stepSize, "-s");
        if (step < 0)
            throw new WrongArgumentException("Step cannot be less than zero!");
        String tests = args.get(3);
        int testCount = parseInt(tests, "-t");
        if (testCount < 1)
            throw new WrongArgumentException("Test samples cannot be less than one!");
        String loops = args.get(4);
        int iterations = parseInt(loops, "-i");
        if (iterations < 1)
            throw new WrongArgumentException("Iteration count cannot be less than one!");
        new Measurement().calculateQ(start, step, testCount, iterations);
    }

    public void parseArgs() throws WrongArgumentException {
        if (args.size() < 2)
            throw new WrongArgumentException("Not enough arguments!");
        String firstArg = args.get(0);
        switch (firstArg) {
            case "-m1":
                readDataAndDraw();
                break;
            case "-m2":
                generateAndDraw();
                break;
            case "-m3":
                doCalculations();
                break;
            case "-help":
                printHelp();
                break;
            default:
                throw new WrongArgumentException("Wrong option!");
        }
    }

    public void printHelp() {
        System.out.println("HELP");
        System.out.println("AAL 17Z - Spiral generator");
        System.out.println("Author - Lukasz Lepak");
        System.out.println();
        System.out.println("Available program calls:");
        System.out.println("1. java AAL.AAL -m1 -a{algorithm}");
        System.out.println("2. java AAL.AAL -m2 -a{algorithm} -n{points_count} (-si) (-so)");
        System.out.println("3. java AAL.AAL -m3 -n{points_count} -s{step} -t{tests} -i{iterations}");
        System.out.println("4. java AAL.AAL -help");
        System.out.println();
        System.out.println("-m1 - Read data from standard input, print results to standard output, visualise results");
        System.out.println("-m2 - Generate a data set of a given size, calculate results, visualise results");
        System.out.println("-m3 - Make time measurements and check algorithms' complexity by testing on multiple data sizes");
        System.out.println("-help - print help to standard output");
        System.out.println("Possible variable values:");
        System.out.println("{algorithm} - CH (ConvexHull) or RR (Sort)");
        System.out.println("{points_count} - integer not less than 3");
        System.out.println("{step} - integer that is not negative");
        System.out.println("{tests} - and odd integer not less than one, even integers will be incremented to become odd");
        System.out.println("{iterations} - integer higher than zero");
        System.out.println("-si, -so - optional print to standard output of generated input/calculated output (for -m2)");
    }

    private int parseInt(String number, String begin) throws WrongArgumentException {
        if (!number.startsWith(begin)) {
            throw new WrongArgumentException("Unknown argument!");
        }
        number = number.substring(2);
        return Integer.parseInt(number);
    }
}
