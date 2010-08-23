package com.example.android.recipe022;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

public class Recipe022 extends Activity {

    private static final String zipFileName = "recipe_022_sample.zip";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // res/rawに置いたファイルからFileオブジェクトを作りたいので
        // 一旦ローカルファイルに書き出す
        res2file();

        // ここから本番
        String filepath = "/data/data/" + getPackageName() + "/files/" + zipFileName;
        unzip(this, filepath);
    }

    // 指定されたZIPファイルをSDカードに解凍します。
    // SDカードの有無のチェックや容量のチェックは省略しています。
    public void unzip(Context context, String filepath) {
        // SDカード/パッケージ名なディレクトリを表すFileを生成
        File outDir = new File(Environment.getExternalStorageDirectory(),
                               context.getPackageName());
        // パッケージ名のディレクトリがなければ作成します。
        if (outDir.exists() == false) {
            outDir.mkdir();
        }

        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            // ZipFileのインスタンスを生成
            ZipFile zipFile = new ZipFile(filepath);
            // ZIPファイルのエントリのリストを取得
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            // エントリの最後までループ
            while (entries.hasMoreElements()) {
                // 次のエントリを取得
                ZipEntry entry = entries.nextElement();
                // 出力ファイルのインスタンスを生成
                File outFile = new File(outDir, entry.getName());

                // ディレクトリの場合は、ディレクトリを作成
                if (entry.isDirectory()) {
                    outFile.mkdir();

                // ファイルの場合は、ファイルを作成
                } else {
                    // 入力
                    InputStream in;
                    in = zipFile.getInputStream(entry);
                    bin = new BufferedInputStream(in);

                    // 出力
                    bout = new BufferedOutputStream(
                               new FileOutputStream(outFile));

                    // 入力から出力へ
                    int bytedata = 0;
                    while((bytedata = bin.read()) != -1) {
                        bout.write(bytedata);
                    }
                    bout.flush();
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) bin.close();
                if (bout != null) bout.close();
            } catch (IOException e) {
            }
        }
    }

    private void res2file() {
        InputStream in;
        in = getResources().openRawResource(R.raw.recipe_022_sample);
        OutputStream out;
        byte[] buffer = new byte[1024];
        try {
            out = openFileOutput(zipFileName, MODE_PRIVATE);
            while(true) {
                int size = in.read(buffer);
                if (size <= 0) break;
                out.write(buffer, 0, size);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}