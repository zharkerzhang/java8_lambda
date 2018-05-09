package com.zharker;

import com.zharker.chapter1.Album;
import com.zharker.chapter1.Track;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chapter6Test {
    @Test
    public void getArraySumTest(){
        List<Album> albums = new ArrayList<>();

        int serialArraySum = albums.stream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
        int paralleArraySum = albums.parallelStream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();

        System.out.println(serialArraySum);
        System.out.println(paralleArraySum);
    }

    @Test
    public void addIntegersTest(){

        Integer[] array = Stream.of(1,3,4,5,6,7,8,9,0,0,1,2,3,4,5,3,6,78,9,0,2).toArray(Integer[]::new);
        List<Integer> ls = Arrays.asList(array);
        int result = addIntegers(ls);
        System.out.println(result);
    }

    private int addIntegers(List<Integer> values) {
        return values.parallelStream().mapToInt(Integer::intValue).sum();
    }

    @Test
    public void parallelInitializeTest(){
        int size = 23;
        double[] result = parallelInitialize(size);
        Arrays.stream(result).forEach(System.out::println);
    }

    private double[] parallelInitialize(int size) {
        double[] values = new double[size];
        Arrays.parallelSetAll(values, i->i);
        return values;
    }

    @Test
    public void simpleMovingAverageTest(){
        double[] values = parallelInitialize(10);
        int n = 1;
        double[] results = simpleMovingAverage(values, n);
        Arrays.stream(results).forEach(System.out::println);
    }

    private double[] simpleMovingAverage(double[] values, int n) {
        double[] sums = Arrays.copyOf(values,values.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = n - 1;
        return IntStream.range(start, sums.length)
                .mapToDouble(i->{
                    double prefix = i == start ? 0 : sums[i-n];
                    return (sums[i]-prefix) / n;
                })
                .toArray();

//        return IntStream.range(start, sums.length)
//                .mapToDouble(Double::valueOf).toArray();
    }

    @Test
    public void sequentialSumOfSquaresTest(){
        IntStream range1 = IntStream.of(1,2,3,4,5,6,7,8);
        IntStream range2 = IntStream.of(1,2,3,4,5,6,7,8);
        int result1 = sequentialSumOfSquares(range1);
        int result2 = parallelSumOfSquares(range2);
        System.out.println(result1+", "+result2);
    }

    private int parallelSumOfSquares(IntStream range) {
        return range.parallel().map(x->x*x).sum();
    }

    private int sequentialSumOfSquares(IntStream range) {
        return range.map(x->x*x).sum();
    }

    @Test
    public void multiplyThroughTest(){
        List<Integer> ls = Stream.of(1,2,3,4).collect(Collectors.toCollection(LinkedList::new));

        int result = multiplyThrough(ls);

        System.out.println(result);

    }

    private int multiplyThrough(List<Integer> linkedListOfNumber) {
        ArrayList<Integer> ls = new ArrayList<>();
        ls.addAll(linkedListOfNumber);
        return 5 * ls.parallelStream().reduce(1, (acc, x)->x*acc);
        /*
        return 1 * ls.parallelStream().reduce(5, (acc, x)->{
            System.out.println("acc:"+acc+", x:"+x) ;
            return x*acc;
        });
        */
    }

    @Test
    public void slowSumOfSquaresTest(){
//        List<Integer> ls = Stream.of(1,2,3,4).collect(Collectors.toCollection(LinkedList::new));


        List<Integer> ls = Arrays.stream(IntStream.range(1,1_000_000).toArray()).mapToObj(Integer::valueOf).collect(Collectors.toCollection(LinkedList::new));

        Date result1Begin = new Date();
        int result1 = slowSumOfSquares(ls);
        Date result1End = new Date();
        System.out.println(result1);
        System.out.println(result1Begin);
        System.out.println(result1End);
        System.out.println("using time: "+(result1End.getTime()-result1Begin.getTime()));

        Date result2Begin = new Date();
        int result2 = fastSumOfSquares(ls);
        Date result2End = new Date();
        System.out.println(result1);
        System.out.println(result2Begin);
        System.out.println(result2End);
        System.out.println("using time: "+(result2End.getTime()-result2Begin.getTime()));

    }

    private int fastSumOfSquares(List<Integer> ls) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        ls.forEach(arrayList::add);
        return arrayList.parallelStream().mapToInt(x->x*x).sum();
    }

    private int slowSumOfSquares(List<Integer> ls) {
        return ls.parallelStream().map(x->x*x).reduce(0,(acc, x)->acc+x);
    }
}
