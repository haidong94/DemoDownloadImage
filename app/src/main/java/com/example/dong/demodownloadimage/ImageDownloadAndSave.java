package com.example.dong.demodownloadimage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DONG on 23-Sep-17.
 */

public class ImageDownloadAndSave extends AsyncTask<String, Void, Bitmap>
{
    @Override
    protected Bitmap doInBackground(String... arg0)
    {
        downloadImagesToSdCard("");
        return null;
    }

    private void downloadImagesToSdCard(String downloadUrl)
    {
        try
        {
            URL url = new URL("https://png.pngtree.com/thumb_back/fh260/back_pic/00/01/80/73560a545c6ae6b.jpg");


            /* making a directory in sdcard */
            String sdCard= Environment.getExternalStorageDirectory().toString();
            File myDir = new File(sdCard+"/image");

            /*  if specified not exist create new */
            if(!myDir.exists())
            {
                myDir.mkdir();
                Log.v("", "inside mkdir");
            }

            /* checks the file and if it already exist delete */
            String fname = "test.jpg";
            File file = new File (myDir, fname);
            if (file.exists ())
                file.delete ();

            /* Open a connection */
            URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            HttpURLConnection httpConn = (HttpURLConnection)ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = httpConn.getInputStream();
            }

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = httpConn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) >0 )
            {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
            }

            fos.close();
            Log.d("test", "Image Saved in sdcard..");
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}