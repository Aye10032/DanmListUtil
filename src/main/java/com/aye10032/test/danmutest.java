package com.aye10032.test;

import java.io.*;
import java.util.zip.CRC32;


public class danmutest {
    public static void main(String[] args) throws IOException {
        java.util.zip.CRC32 crc = new CRC32();
        crc.update("40077740".getBytes());
        System.out.println(Long.toHexString(crc.getValue()));
    }


}
