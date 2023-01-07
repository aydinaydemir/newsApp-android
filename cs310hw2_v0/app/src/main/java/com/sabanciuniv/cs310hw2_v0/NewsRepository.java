package com.sabanciuniv.cs310hw2_v0;

import android.os.Message;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {


    public void getNewsByNewsId(ExecutorService srv, Handler uiHandler, int id) {

        srv.execute(() -> {

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String bufferString = buffer.toString();
                JSONObject respond = new JSONObject(bufferString);
                JSONArray realRespond = respond.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < realRespond.length(); i++) {
                    JSONObject currentNews = realRespond.getJSONObject(i);
                    int newsId = currentNews.getInt("id");
                    String newsTitle = currentNews.getString("title");
                    String newsText = currentNews.getString("text");
                    String newsDate = currentNews.getString("date");
                    newsDate = newsDate.substring(0, 10);
                    String newsDay = newsDate.substring(8, 10);
                    String newsMonth = newsDate.substring(5, 7);
                    String newsYear = newsDate.substring(0, 4);
                    newsDate = newsDay + "/" + newsMonth + "/" + newsYear;
                    String newsImage = currentNews.getString("image");
                    String newsCategoryName = currentNews.getString("categoryName");

                    data.add(new News(newsId, newsTitle, newsText, newsDate, newsImage, newsCategoryName));
                }
                Message msg = new Message();
                msg.obj = data.get(0);
                uiHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getNewsByCategoryId(ExecutorService srv, Handler uiHandler, int id) {
        List<News> data = new ArrayList<>();
        srv.execute(() -> {

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String bufferString = buffer.toString();
                JSONObject respond = new JSONObject(bufferString);
                JSONArray realRespond = respond.getJSONArray("items");


                for (int i = 0; i < realRespond.length(); i++) {
                    JSONObject currentNews = realRespond.getJSONObject(i);
                    int newsId = currentNews.getInt("id");
                    String newsTitle = currentNews.getString("title");
                    String newsText = currentNews.getString("text");
                    String newsDate = currentNews.getString("date");
                    newsDate = newsDate.substring(0, 10);
                    String newsDay = newsDate.substring(8, 10);
                    String newsMonth = newsDate.substring(5, 7);
                    String newsYear = newsDate.substring(0, 4);
                    newsDate = newsDay + "/" + newsMonth + "/" + newsYear;
                    String newsImage = currentNews.getString("image");
                    String newsCategoryName = currentNews.getString("categoryName");

                    data.add(new News(newsId, newsTitle, newsText, newsDate, newsImage, newsCategoryName));
                }
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }



    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int id) {

        srv.execute(() -> {

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String bufferString = buffer.toString();
                JSONObject respond = new JSONObject(bufferString);
                JSONArray realRespond = respond.getJSONArray("items");
                List<Comment> data = new ArrayList<>();

                for (int i = 0; i < realRespond.length(); i++) {
                    JSONObject currentComment = realRespond.getJSONObject(i);
                    int commentId = currentComment.getInt("id");
                    int commentNewsId = currentComment.getInt("news_id");
                    String commentText = currentComment.getString("text");
                    String commentName = currentComment.getString("name");


                    data.add(new Comment(commentId, commentNewsId, commentText, commentName));
                }
                Message msg = new Message();
                msg.obj = data;
                Log.i("DEV", "getCommentsByNewsId: " + data.size());
                uiHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


    }

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path) {
        srv.execute(() -> {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);
                Log.i("DEV", "Image downloaded");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

    public void saveComment(ExecutorService srv, Handler uiHandler, Comment comment) {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject outputData = new JSONObject();
                outputData.put("name", comment.getName());
                outputData.put("text", comment.getText());
                outputData.put("news_id", comment.getNews_id());
                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject retVal = new JSONObject(buffer.toString());
                String serviceMessage = retVal.getString("serviceMessageText");
                Log.i("DEV", "saveComment: " + serviceMessage);

                Message msg = new Message();
                msg.obj = serviceMessage;
                uiHandler.sendMessage(msg);


                conn.disconnect();






            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
