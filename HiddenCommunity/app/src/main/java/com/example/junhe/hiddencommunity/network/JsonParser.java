package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import data.BoardData;

/**
 * Created by HongjunLim on 11/27/2016.
 */

public class JsonParser {
    BoardData data;
    public BoardData getData(JSONObject reponse) {
        JSONObject board;

        System.out.println("JsonParser의 getData 부분");
        String ip;
        if (reponse != null || reponse.length() > 0) {
            try {
                board = reponse.getJSONObject("board");
                String boardId = URLEncoder.encode(board.getString("_id"), "utf-8");
                //String postCategory = URLEncoder.encode(board.getString("category"), "utf-8");
                String postCategory = "건축·토목";
                String postTitle1 = URLEncoder.encode(board.getString("title"), "utf-8");
                String postTitle2 = URLEncoder.encode(URLEncoder.encode(board.getString("title"), "utf-8"), "utf-8");
                //URLEncoder.encode(board.getString("title"), "utf-8");
                String postAuthor = URLEncoder.encode(board.getString("author"), "utf-8");
                String postDate = URLEncoder.encode(board.getString("date"), "utf-8");
                String postBody = URLEncoder.encode(board.getString("body"), "utf-8");
                String postTag = URLEncoder.encode(board.getString("tag"), "utf-8");

                String Meta = URLEncoder.encode(board.getString("meta"), "utf-8");

//                int postHit = URLEncoder.encode(Meta.getInt("hit"), "utf-8");
//                int postLike = URLEncoder.encode(Meta.getInt("like"), "utf-8");
//                int postComment = URLEncoder.encode(Meta.getInt("comment"), "utf-8");

                System.out.println(postTitle1 + "\n" + postTitle2);

//                String boardId = board.getString("_id");
//                System.out.println("boardId 받아오기 :" + boardId);
//                //String postCategory = URLEncoder.encode(board.getString("category"), "utf-8");
//                String postCategory = "건축·토목";
//                System.out.println("category 받아오기 :" + postCategory);
//                String postTitle =board.getString("title");
//                String postAuthor = board.getString("author");
//                String postDate = board.getString("date");
//                String postBody = board.getString("body");
//                String postTag = board.getString("tag");
//                String postMeta = board.getString("meta");

                Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle1 + " / 글쓴이: " + postAuthor);
                Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + postTag + " / 싫어요 좋아요 조회수: " + Meta);

                data = new BoardData(postCategory, postTitle1, postAuthor, postDate, postBody, postTag, 0,0,0);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    //    private List<BaseData> getListData(JSONObject reponse) {
//        ArrayList<BaseData> listItems = null;
//        JSONArray arrayItems;
//        JSONObject rowCount;
//        if (reponse != null || reponse.length() > 0) {
//            try {
//                listItems = new ArrayList<BaseData>();
//
//                rowCount = reponse.getJSONObject(KEY_ROW_COUNT);
//                ROW_COUNT = rowCount.getInt(KEY_ROW_CNT);
//
//                arrayItems = reponse.getJSONArray("tag");
//
//                for (int i = 0; i < arrayItems.length(); i++) {
//                    JSONObject currentItem = arrayItems.getJSONObject(i);
//                    String barcode = currentItem.getString(KEY_BARCODE);
//                    String name = currentItem.getString(KEY_NAME);
//                    int sPrice = currentItem.getInt(KEY_S_PRICE);
//                    int price = currentItem.getInt(KEY_PRICE);
//                    String imageUrlLow = currentItem
//                            .getString(KEY_IMAGE_URL_LOW);
//                    String imageUrlHigh = currentItem
//                            .getString(KEY_IMAGE_URL_HIGH);
//
//                    ItemData item = new ItemData();
//                    item.setBarcode(barcode);
//                    item.setName(name);
//                    item.setSPrice(sPrice);
//                    item.setPrice(price);
//                    item.setImageUrlLow(imageUrlLow);
//                    item.setImageUrlHigh(imageUrlHigh);
//                    listItems.add(item);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//		/*
//		 * for(int i = 0; i < listItems.size(); i++){ Log.i("json",
//		 * listItems.get(i).toString()); }
//		 */
//        // Log.i("json", "사이즈"+listItems.size());
//        return listItems;
//    }
}
