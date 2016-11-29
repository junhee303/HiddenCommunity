package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.BoardData;
import data.CommentData;

/**
 * Created by HongjunLim on 11/27/2016.
 */

public class JsonParser {
    BoardData boardData;
    CommentData commentData;

    ArrayList<String> authorList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> bodyList = new ArrayList<>();

    JSONArray commentArray = new JSONArray();

    public BoardData getData(JSONObject reponse) {
        JSONObject board;
        String tagString = null;

        System.out.println("JsonParser의 getData 부분");

        if (reponse != null || reponse.length() > 0) {
            try {
                board = reponse.getJSONObject("board");
                String boardId = board.getString("_id");
                System.out.println("boardId 받아오기 :" + boardId);
                String postCategory = "건축·토목";
                System.out.println("category 받아오기 :" + postCategory);
                String postTitle = board.getString("title");
                String postAuthor = board.getString("author");
                String postDate = board.getString("date");
                String postBody = board.getString("body");
                JSONArray postTag = board.getJSONArray("tag");
                JSONObject Meta = board.getJSONObject("meta");

                int Hit = Meta.getInt("hit");
                int Like = Meta.getInt("like");
                int Hate = Meta.getInt("hate");

                ArrayList tags = new ArrayList();

                if (tags != null) {
                    int len = postTag.length();
                    for (int i = 0; i < len; i++) {
                        tags.add(postTag.get(i).toString());
                        tagString += tags.get(i);
                    }
                }



                Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle + " / 글쓴이: " + postAuthor);
                Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + tagString + " / 조회 수: " + Hit + " / 좋아요 수: " + Like + " / 신고하기 수: " + Hate);

                boardData = new BoardData(boardId, postCategory, postTitle, postAuthor, postDate, postBody,tagString, Hit, Like, Hate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return boardData;
    }

    public void getComment(JSONObject reponse) {
        JSONObject comment;

        System.out.println("JsonParser의 getComment 부분");

        if (reponse != null || reponse.length() > 0) {
            try {
                comment = reponse.getJSONObject("board");
                String boardId = comment.getString("_id");
                commentArray = comment.getJSONArray("comment");
                for (int i = 0; i < commentArray.length(); i++) {
                    JSONObject jsonObject = commentArray.getJSONObject(i);
                    authorList.add(jsonObject.getString("name"));
                    dateList.add(jsonObject.getString("gender"));
                    bodyList.add(jsonObject.getString("gender"));
                }
                System.out.println("" + authorList + "\n" + "" + dateList + "\n" + bodyList);

                Log.d("JsonParser: ", "boardId: " + boardId + " /  글쓴이: " + authorList.get(0) + " / 날짜: " + dateList.get(0) + " / 글 내용: " + bodyList.get(0));

                commentData = new CommentData(boardId, authorList.get(0), dateList.get(0), bodyList.get(0));
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return ;
    }
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
//      /*
//       * for(int i = 0; i < listItems.size(); i++){ Log.i("json",
//       * listItems.get(i).toString()); }
//       */
//        // Log.i("json", "사이즈"+listItems.size());
//        return listItems;
//    }
