/*
    Łukasz Lepak, 277324
    AAL 17Z, projekt
    Tytuł projektu: Generacja spirali ze zbioru punktów
    prowadzący: dr inż. Tomasz Gambin
 */
package algorithms;

import model.Point;

import java.util.List;

public interface SpiralAlgorithm {
    List<Point> findSpiral(List<Point> points);
    List<Point> getSpiralPoints();
}
