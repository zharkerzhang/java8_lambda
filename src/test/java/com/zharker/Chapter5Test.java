package com.zharker;

import com.zharker.chapter5.StringCombiner;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chapter5Test {

    @Test
    public void execTest(){
        Stream.of("ava","3ian","gg","ziVie").map(String::toUpperCase).forEach(System.out::println);

        Integer count = Stream.of(1,2,3,4,5,6,7,8,9).reduce(0,(pre,cur)->pre+1);
        System.out.println(count);


        LinkedList[] arrll = {new LinkedList(),new LinkedList(),new LinkedList(),new LinkedList()};

        long countt = Stream.of(arrll).flatMap(ls->{
            return Stream.of(ls);
        }).count();
        System.out.println(countt);

        Map<String,Long> mapp = Stream.of("a","a","bb","cc","bb","cc","ddd","ddd","ddd")
                .collect(Collectors.groupingBy(str->str,Collectors.counting()));
        System.out.println(mapp);

    }

    @Test
    public void ComputeMapTest(){
        Map<String,Double> map = Stream.of(1,2,3,4,5,6,7,8,9).collect(Collectors.toMap(String::valueOf,i->Math.pow(i,3)));
        System.out.println(map);

        Double dd = map.compute("0",(str,dou)->Double.valueOf(str));
        System.out.println(dd);

        Double dd2 = map.computeIfAbsent("0",(str)->Double.valueOf(str)+2333);
        System.out.println(dd2);


        map.forEach((key,value)->{
            System.out.println(key+":"+value);

        });
    }


    @Test
    public void defineCollectionTest(){
        List<Integer> ls = Stream.of(1,2,3,4,5,6,7,8,9).collect(Collectors.toList());

        StringBuffer sb1 = new StringBuffer("[");
        ls.stream().map(String::valueOf).forEach(s->{
            if(sb1.length()>1){
                sb1.append(", ");
            }
            sb1.append(s);
        });
        sb1.append("]");
        String result1 = sb1.toString();
        System.out.println(result1);

        StringBuffer sb2 = ls.stream().map(String::valueOf).reduce(new StringBuffer(), (StringBuffer builder, String iStr)->{
            if(builder.length() > 0){
                builder.append(", ");
            }
            builder.append(iStr);
            return builder;
        },(left, right)->left.append(right));
        sb2.insert(0,"[");
        sb2.append("]");
        System.out.println(sb2.toString());

        System.out.println("reduce: ");
        StringCombiner combined = ls.stream().map(String::valueOf)
                .reduce(new StringCombiner(", ","[","]"),
                        StringCombiner::add,
                        StringCombiner::merge);
        System.out.println(combined.toString());

/*
        Integer totall = ls.stream()
                .reduce(1,
                        (resultFromPrex,curr)->{
//                            System.out.println(resultFromPrex+"-"+curr);
                            return resultFromPrex-curr;
                        },
                        (left,right)->{
//                            System.out.println(left+"+"+right);
                            return left+right;
                        });
        System.out.println(totall);
        */


        System.out.println("Collectors.reducing: ");
        String result = ls.stream().map(String::valueOf)
                .collect(Collectors.reducing(new StringCombiner(", ","[","]"),
                        iStr->new StringCombiner(", ","[","]").add(iStr),
                        StringCombiner::merge))
                .toString();
        System.out.println(result);

    }

    @Test
    public void streamCollectionTest(){
        Stream.of(1,2,3,4,5,6,7,8,8).forEachOrdered(System.out::println);

        Set<Integer> set = Stream.of(1,2,33,4,5).collect(Collectors.toSet());
        set.stream().forEachOrdered(System.out::println);
        set.stream().forEach(System.out::println);

        TreeSet<String> strSet = set.stream().map(String::valueOf).collect(Collectors.toCollection(TreeSet::new));

        String maxLengthStr = strSet.stream().collect(Collectors.maxBy(Comparator.comparing(s->s.length()))).orElse("");
        System.out.println(maxLengthStr);

        Double avg = set.stream().collect(Collectors.averagingInt(s->Integer.valueOf(s.toString())));
        System.out.println(avg);

        Map<Boolean, List<Integer>> map = set.stream().collect(Collectors.partitioningBy(i->i%2==0));
        map.forEach((b,l)->{
            System.out.println("b: "+b);
            l.forEach(System.out::println);
        });

        Map<Integer, List<Integer>> map2 = set.stream().collect(Collectors.groupingBy(i->i%3));
        map2.forEach((i,l)->{
            String result = l.stream().map(litem->litem.toString()).collect(Collectors.joining(", ","[","]"));
            System.out.println(i+": "+result);
        });

        Map<Integer,Long> map3 = set.stream().collect(Collectors.groupingBy(i->i%3, Collectors.counting()));
        map3.forEach((i,l)->{
            System.out.println(i+": "+l);
        });

        System.out.println("map4:");
        Map<Integer,List<Double>> map4 = set.stream().collect(Collectors.groupingBy(i->Integer.valueOf(i%5),Collectors.mapping(i->Double.valueOf(i)/5, Collectors.toList())));
        map4.forEach((i,l)->{
            String result = l.stream().map(String::valueOf).collect(Collectors.joining(" "));
            System.out.println(i+": "+result);
        });







    }
}
