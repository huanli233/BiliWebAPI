package com.huanli233.biliapi.api.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

public class RequestParamUtil {
	private static final String[] MP = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "10"
    };
    private static final int[] PCK = {8, 4, 4, 4, 12};
    private static final String CHARSET = "0123456789ABCDEF";

    public static String gen_b_lsid() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        String randomString = sb.toString();
        long currentTimeMillis = System.currentTimeMillis();
        return randomString + "_" + Long.toHexString(currentTimeMillis).toUpperCase();
    }

    public static String gen_uuid_infoc() {
        long t = System.currentTimeMillis() % 100000;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int len : PCK) {
            for (int i = 0; i < len; i++) {
                sb.append(MP[random.nextInt(16)]);
            }
            sb.append("-");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(String.format("%05d", t)).append("infoc");
        return sb.toString();
    }

    public static String gen_b_nut() {
        long timestampInSeconds = System.currentTimeMillis() / 1000;
        return String.valueOf(timestampInSeconds);
    }

    private static final BigInteger MOD = BigInteger.ONE.shiftLeft(64);
    private static final BigInteger C1 = new BigInteger("87C37B91114253D5", 16);
    private static final BigInteger C2 = new BigInteger("4CF5AD432745937F", 16);
    private static final BigInteger C3 = BigInteger.valueOf(0x52DCE729L);
    private static final BigInteger C4 = BigInteger.valueOf(0x38495AB5L);
    private static final int R1 = 27;
    private static final int R2 = 31;
    private static final int R3 = 33;
    private static final int M = 5;

    public static String gen_buvid_fp(String key, long seed) throws IOException {
        InputStream source = new ByteArrayInputStream(key.getBytes("ascii"));
        BigInteger m = murmur3_x64_128(source, BigInteger.valueOf(seed));
        return String.format("%016x%016x", m.mod(MOD), m.shiftRight(64).mod(MOD));
    }

    private static BigInteger rotateLeft(BigInteger x, int k) {
        return x.shiftLeft(k).or(x.shiftRight(64 - k)).mod(MOD);
    }

    private static BigInteger murmur3_x64_128(InputStream source, BigInteger seed) throws IOException {
        BigInteger h1 = seed;
        BigInteger h2 = seed;
        long processed = 0;
        byte[] buffer = new byte[16];
        while (true) {
            int bytesRead = source.read(buffer);
            processed += bytesRead;
            if (bytesRead == 16) {
                long k1 = ByteBuffer.wrap(buffer, 0, 8).getLong();
                long k2 = ByteBuffer.wrap(buffer, 8, 8).getLong();
                h1 = h1.xor(rotateLeft(BigInteger.valueOf(k1).multiply(C1).mod(MOD), R2).multiply(C2).mod(MOD));
                h1 = (rotateLeft(h1, R1).add(h2).multiply(BigInteger.valueOf(M)).add(C3)).mod(MOD);
                h2 = h2.xor(rotateLeft(BigInteger.valueOf(k2).multiply(C2).mod(MOD), R3).multiply(C1).mod(MOD));
                h2 = (rotateLeft(h2, R2).add(h1).multiply(BigInteger.valueOf(M)).add(C4)).mod(MOD);
            } else if (bytesRead == -1) {
                h1 = h1.xor(BigInteger.valueOf(processed));
                h2 = h2.xor(BigInteger.valueOf(processed));
                h1 = h1.add(h2).mod(MOD);
                h2 = h2.add(h1).mod(MOD);
                h1 = fmix64(h1);
                h2 = fmix64(h2);
                h1 = h1.add(h2).mod(MOD);
                h2 = h2.add(h1).mod(MOD);
                return h2.shiftLeft(64).or(h1);
            } else {
                long k1 = 0;
                long k2 = 0;
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
                if (bytesRead >= 15) {
                    k2 ^= (long) byteBuffer.get(14) << 48;
                }
                if (bytesRead >= 14) {
                    k2 ^= (long) byteBuffer.get(13) << 40;
                }
                if (bytesRead >= 13) {
                    k2 ^= (long) byteBuffer.get(12) << 32;
                }
                if (bytesRead >= 12) {
                    k2 ^= (long) byteBuffer.get(11) << 24;
                }
                if (bytesRead >= 11) {
                    k2 ^= (long) byteBuffer.get(10) << 16;
                }
                if (bytesRead >= 10) {
                    k2 ^= (long) byteBuffer.get(9) << 8;
                }
                if (bytesRead >= 9) {
                    k2 ^= (long) byteBuffer.get(8);
                    h2 = h2.xor(rotateLeft(BigInteger.valueOf(k2).multiply(C2).mod(MOD), R3).multiply(C1).mod(MOD));
                }
                if (bytesRead >= 8) {
                    k1 ^= (long) byteBuffer.get(7) << 56;
                }
                if (bytesRead >= 7) {
                    k1 ^= (long) byteBuffer.get(6) << 48;
                }
                if (bytesRead >= 6) {
                    k1 ^= (long) byteBuffer.get(5) << 40;
                }
                if (bytesRead >= 5) {
                    k1 ^= (long) byteBuffer.get(4) << 32;
                }
                if (bytesRead >= 4) {
                    k1 ^= (long) byteBuffer.get(3) << 24;
                }
                if (bytesRead >= 3) {
                    k1 ^= (long) byteBuffer.get(2) << 16;
                }
                if (bytesRead >= 2) {
                    k1 ^= (long) byteBuffer.get(1) << 8;
                }
                if (bytesRead >= 1) {
                    k1 ^= (long) byteBuffer.get(0);
                    h1 = h1.xor(rotateLeft(BigInteger.valueOf(k1).multiply(C1).mod(MOD), R2));
                }
            }
        }
    }

    private static BigInteger fmix64(BigInteger k) {
        final BigInteger C1 = new BigInteger("FF51AFD7ED558CCD", 16);
        final BigInteger C2 = new BigInteger("C4CEB9FE1A85EC53", 16);
        final int R = 33;
        k = k.xor(k.shiftRight(R)).multiply(C1).mod(MOD);
        k = k.xor(k.shiftRight(R)).multiply(C2).mod(MOD);
        k = k.xor(k.shiftRight(R)).mod(MOD);
        return k;
    }
}
