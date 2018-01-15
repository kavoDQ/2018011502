package forfun.good.a2018011502;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView img ,img2 ;
    TextView tv , tv2, tv3 ;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        img2 = findViewById(R.id.imageView2);
        tv = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        pb = findViewById(R.id.progressBar);
    }
    public void CL1(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String str_url = "http://img3.okgo.tw/titlepic/b3409_2.jpg";
                URL url;
                try{
                    url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    final int totalLength = conn.getContentLength(); //總長度
                    int sum = 0 ;
                    int length;
                    while ((length = inputStream.read(buf)) !=-1 )
                    {
                        sum += length;
                        final int tmp = sum;
                        bos.write(buf,0,length);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(String.valueOf(tmp)+"/"+totalLength);
                                pb.setProgress(100 * tmp / totalLength);
                            }
                        });
                    }
                    byte[] results = bos.toByteArray();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(results,0, results.length);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bmp);
                        }
                    });
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void CL2(View v)
    {
        Mytask task = new Mytask();
        task.execute(20);
    }
    public void CL3(View v)
    {
        Mytast2 task = new Mytast2();
        task.execute("http://img3.okgo.tw/titlepic/b3409_2.jpg");
    }

    class Mytast2 extends  AsyncTask<String,Integer, Bitmap> //抓圖片的
    {
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            super.onPostExecute(bitmap);
            img2.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str_url = strings [0]; //抓第一個
            URL url;
            try{
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                final int totalLength = conn.getContentLength(); //總長度
                int sum = 0 ;
                int length;
                while ((length = inputStream.read(buf)) !=-1 )
                {
                    sum += length;
                    final int tmp = sum;
                    bos.write(buf,0,length);

                }
                byte[] results = bos.toByteArray();
                final Bitmap bmp = BitmapFactory.decodeByteArray(results,0, results.length);
                return bmp; //回傳
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    class Mytask extends AsyncTask<Integer,Integer, String>
    {
        @Override
        protected void onPostExecute(String s) { //之後
            super.onPostExecute(s);
            tv3.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv2.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int i;
            for (i=0;i<=integers[0];i++)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TASK","doInBackground, i:"+i);
                publishProgress(i);
            }
            return "okay";

        }


    }
}
