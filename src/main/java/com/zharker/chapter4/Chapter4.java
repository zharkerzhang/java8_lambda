package com.zharker.chapter4;

import com.zharker.chapter1.Artist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Chapter4 {

    public static void main(String[] args){
        Parent p = new ParentImpl();
        p.welcome();

        Child c = new ChildImpl();
        c.welcome();

        Parent op = new OverrideParent();
        op.welcome();

        Child oc = new OverrideChild();
        oc.welcome();

        int value = Stream.of(1,2,3,4).reduce((acc, idx)-> acc+idx).orElse(100);
        System.out.println(value);


    }

}


interface Parent{
    public void message(String body);
    public default void welcome(){
        message("parent hi");
    }
}

interface Child extends Parent{

    public default void welcome(){
        message("child hi");
    }
}

class ParentImpl implements Parent {
    @Override
    public void message(String body) {
        System.out.println("from parentImpl");
        System.out.println(body);
    }
}

class ChildImpl implements Child{
    @Override
    public void message(String body) {
        System.out.println(body);
    }
}

class OverrideParent extends ParentImpl{

    public void welcome(){
        message("overrideParent hi");
    }

}

class OverrideChild extends OverrideParent implements Child{

}

class Artists{
    private List<Artist> artists;

    Artists(List<Artist> artists) {
        this.artists = artists;
    }
/*

    public Artist getArtist(int index){
        if(index < 0|| index > artists.size()){
            indexException(index);
        }
        return artists.get(index);
    }
*/

    public Optional<Artist> getArtist(int index){
        if(index < 0|| index > artists.size()){
            return Optional.empty();
        }
        return Optional.of(artists.get(index));
    }


    private void indexException(int index) {
        throw new IllegalArgumentException(index+" do not correspond to an artist");
    }
/*
    public String getArtistName(int index){
        Artist artist = getArtist(index);
        return artist.getName();
    }
*/

    public Optional<String> getArtistName(int index){
        return getArtist(index).isPresent() ? Optional.of(getArtist(index).get().getName()) : Optional.empty();
    }

}

