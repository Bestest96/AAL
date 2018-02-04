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

public class Measurement {

    public double measureAverageTime(SpiralAlgorithm algorithm, int dataSize, int loops) {
        double time = 0;
        PointGenerator pg = new PointGenerator();
        for (int i = 0 ; i < loops ; ++i) {
            double start = System.currentTimeMillis();
            algorithm.findSpiral(pg.generatePoints(dataSize));
            double end = System.currentTimeMillis();
            time += end - start;
        }
        return time / loops;
    }

    public void calculateQ(int startSize, int step, int tests, int loops){
        System.out.println("Preparing for measurements");
        warmUp();
        if (tests % 2 == 0) {
            System.out.println("Increasing test count by one to make it an odd number");
            ++tests;
        }
        int medianN = startSize + step * tests / 2;
        ConvexHullAlgorithm cha = new ConvexHullAlgorithm();
        RisingRadiusAlgorithm rra = new RisingRadiusAlgorithm(36);
        double risingRadiusMedian = risingRadiusComplexity(medianN);
        System.out.println("Calculating Rising Radius Algorithm median time");
        double risingRadiusMedianTime = measureAverageTime(rra, medianN, loops);
        System.out.println("Starting measure for Rising Radius Algorithm - " + tests + " tests");
        for (int i = 0 ; i < tests ; ++i) {
            int n = startSize + i * step;
            double avgRRTime = i != tests / 2 ? measureAverageTime(rra, n, loops) : risingRadiusMedianTime;
            double q = i != tests / 2 ? (avgRRTime * risingRadiusMedian) / (risingRadiusComplexity(n) * risingRadiusMedianTime) : 1.0;
            System.out.println("Data size: " + n + ", time(ms): " + avgRRTime + ", q: " + q);
        }
        System.out.println();
        double convexMedian = convexHullComplexity(medianN);
        System.out.println("Calculating Convex Hull Algorithm median time");
        double convexMedianTime = measureAverageTime(cha, medianN, loops);
        System.out.println("Starting measure for Convex Hull Algorithm - " + tests + " tests");
        for (int i = 0 ; i < tests ; ++i) {
            int n = startSize + i * step;
            double avgCHTime = i != tests / 2 ? measureAverageTime(cha, n, loops) : convexMedianTime;
            double q = i != tests / 2 ? (avgCHTime * convexMedian) / (convexHullComplexity(n) * convexMedianTime) : 1.0;
            System.out.println("Data size: " + n + ", time(ms): " + avgCHTime + ", q: " + q);
        }
        System.out.println();
        System.out.println("Measurements ended. Exiting.");
    }

    private void warmUp() {
        System.out.println("Warming up");
        PointGenerator pg = new PointGenerator();
        ConvexHullAlgorithm cha = new ConvexHullAlgorithm();
        RisingRadiusAlgorithm rra = new RisingRadiusAlgorithm(36);
        for (int i = 0; i < 100; ++i) {
            int bars = (i + 1) / 5;
            StringBuilder loading = new StringBuilder("\r[");
            for (int j = 0; j < 20; ++j) {
                if (j < bars)
                    loading.append("=");
                else
                    loading.append(" ");
            }
            loading.append("]");
            loading.append(i + 1);
            loading.append("%");
            System.out.print(loading.toString());
            cha.findSpiral(pg.generatePoints((i + 1) * 100));
            rra.findSpiral(pg.generatePoints((i + 1) * 100));
        }
        System.out.println();
        System.out.println("Warm-up complete");
    }

    private double convexHullComplexity(double n) {
        return n * n * Math.log(n);
    }

    private double risingRadiusComplexity(double n) {
        return n * Math.log(n);
    }
}
