package com.zharker;

import com.zharker.chapter8.command.*;
import com.zharker.chapter8.strategy.Compressor;
import org.junit.Test;

import javax.crypto.Mac;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class Chapter8Test {

    @Test
    public void strategyTest() throws IOException {
        Path path = Paths.get("E:\\idea_\\projects\\java8_lambda\\src\\main\\resources\\java_learn.md");
        File outFile = new File("E:\\idea_\\projects\\java8_lambda\\src\\main\\resources\\zipp.zip");
        Compressor gzipCompressor = new Compressor(GZIPOutputStream::new);
        gzipCompressor.compress(path, outFile);

        Path path2 = Paths.get("E:\\idea_\\projects\\java8_lambda\\src\\main\\resources\\java_learn2.md");
        File outFile2 = new File("E:\\idea_\\projects\\java8_lambda\\src\\main\\resources\\zipp2.zip");
        Compressor zipCompressor = new Compressor(ZipOutputStream::new);
        zipCompressor.compress(path2, outFile2);
    }

    @Test
    public void commandTest(){
        Editor editor = new Editor() {
            @Override
            public void save() {
                System.out.println("editor save action");
            }

            @Override
            public void open() {
                System.out.println("editor open action");

            }

            @Override
            public void close() {
                System.out.println("editor close action");

            }
        };
        Macro m1 = new Macro();
        m1.record(new Open(editor));
        m1.record(new Save(editor));
        m1.record(new Close(editor));
        m1.run();

        Macro m2 = new Macro();
        m2.record(editor::open);
        m2.record(()->editor.save());
        m2.record(new Action() {
            @Override
            public void perform() {
                editor.open();
            }
        });
        m2.record(()->editor.save());
        m2.record(()->editor.close());
        m2.run();

    }
}
