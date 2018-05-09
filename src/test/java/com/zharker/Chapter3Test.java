package com.zharker;

import com.zharker.chapter1.Album;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chapter3Test {

    @Test
    public void test1(){

        List<Character> ls = new ArrayList<>();

        long count = ls.stream().filter(Character::isDigit).count();

        Stream<Number> numberStream = Stream.of(11,12.3,15,-11.111,13.14);

        Number firstN = numberStream.filter((Number n)->{
            return n.doubleValue()>11;
        }).findFirst().orElse(null);
/*
        Number foundN = numberStream.findAny().orElse(null);

        List<Number> lss = numberStream.collect(Collectors.toList());

        List<Double> lsd = numberStream.map(n->n.doubleValue()).collect(Collectors.toList());
*/

        Stream.of(Arrays.asList(1,2,3),Arrays.asList(3,4,5,6),Arrays.asList(2,3,6,7,9))
                .flatMap(numberLs->numberLs.stream().filter(n->n.intValue()>3))
                .collect(Collectors.toSet())
                .forEach(System.out::println);


        int[][] arr = new int[][]{{11,22,13,24},{15,66,77,88},{33,10,3,0}};

        Arrays.stream(arr).flatMapToInt(subarr->IntStream.of(subarr));

        Character ch = Stream.of('a','A','b','B','c','C').min(Comparator.comparing(c->c.charValue())).get();
        System.out.println(ch);

        int total = Stream.of(1,2,3,4,5).reduce(10, (acc,ele)->acc+ele);
        System.out.println(total);


        List<Album> albumses = new ArrayList<>();



        albumses.stream()
                .flatMap(album ->album.getTracks()
//                        .filter(t->t.getLength()>60)

                )
                .filter(t->t.getLength()>60)
                .map(t->t.getName())
                .collect(Collectors.toSet());


    }
}
