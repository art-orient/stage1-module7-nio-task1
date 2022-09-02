package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class FileReader {

    public Profile getDataFromFile(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file.getAbsoluteFile(), "r");
             FileChannel fileChannel = randomAccessFile.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) randomAccessFile.length());
            StringBuilder sb = new StringBuilder();
            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
            }
            List<String> list = List.of(sb.toString().split(System.lineSeparator()));
            String name = list.get(0).substring(6);
            Integer age = Integer.parseInt(list.get(1).substring(5));
            String email = list.get(2).substring(7);
            Long phone = Long.parseLong(list.get(3).substring(7));
            return new Profile(name, age, email, phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
