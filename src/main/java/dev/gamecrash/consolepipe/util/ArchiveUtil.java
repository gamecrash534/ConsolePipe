package dev.gamecrash.consolepipe.util;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class ArchiveUtil {
    private static byte[] gzipMagicNumber = {0x1F, (byte) 0x8B};

    public static @Nullable List<String> decompressGzip(InputStream is) {
        try (GZIPInputStream zip = new GZIPInputStream(is)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(zip));
            return br.readAllLines();
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean isGzip(InputStream is) throws IOException {
        is.reset();
        byte[] isMagicNum = is.readNBytes(2);
        is.reset();
        return Arrays.equals(isMagicNum, gzipMagicNumber);
    }
}
