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
    CommentData commentData;

    ArrayList<String> authorList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> bodyList = new ArrayList<>();

    JSONArray commentArray = new JSONArray();


    //JSONObject
    public ArrayList<BoardData> getBoardData(JSONObject response) {
        ArrayList<BoardData> boardDataList = new ArrayList<>();
        BoardData boardData;
        String tagString = "";

        System.out.println("JsonParser의 getBoardData 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONArray postBoardsList =  response.getJSONArray("boards");

                ArrayList<JSONObject> boardsList = new ArrayList();

                System.out.println("postBoardList의 길이" + postBoardsList.length());

                if (boardsList != null) {
                    int len = postBoardsList.length();
                    for (int i = 0; i < len; i++) {
                        boardsList.add(postBoardsList.getJSONObject(i));

                        String boardId = boardsList.get(i).getString("_id");
                        System.out.println("boardId 받아오기 :" + boardId);
                        String postCategory = boardsList.get(i).getString("category");
                        // String postCategory = board.getString("자유");
                        System.out.println("category 받아오기 :" + postCategory);
                        String postTitle = boardsList.get(i).getString("title");
                        String postAuthor = boardsList.get(i).getString("author");
                        String postDate = boardsList.get(i).getString("date");
                        String postBody = boardsList.get(i).getString("body");
                        JSONArray postTag = boardsList.get(i).getJSONArray("tag");
                        JSONObject Meta = boardsList.get(i).getJSONObject("meta");

                        commentArray = boardsList.get(i).getJSONArray("comment"); // 댓글 수 받아오기 위해서 댓글 Array 불러옴
                        int Comment = commentArray.length(); // 댓글 수

                        int Hit = Meta.getInt("hit");
                        int Like = Meta.getInt("like");
                        int Hate = Meta.getInt("hate");

                        ArrayList tags2 = new ArrayList();


//                            int len2 = postTag.length();
//                            for (int j = 0; j < len; j++) {
//                                tags2.add(postTag.get(j).toString());
//                                tagString += tags2.get(j) + " ";
//                            }



                        Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle + " / 글쓴이: " + postAuthor);
                        Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + "tagString" + " / 조회 수: " + Hit + " / 좋아요 수: " + Like + " / 댓글 수: " + Comment);


                        boardData = new BoardData(boardId, postCategory, postTitle, postAuthor, postDate, postBody, tagString, Hit, Like, Comment);
                        boardDataList.add(boardData);

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return boardDataList;
    }


    public BoardData getData(JSONObject response) {
        BoardData boardData = null;
        JSONObject board;
        String tagString = "";

        System.out.println("JsonParser의 getData 부분");

        if (response != null || response.length() > 0) {
            try {
                board = response.getJSONObject("board");
                String boardId = board.getString("_id");
                System.out.println("boardId 받아오기 :" + boardId);
                String postCategory = board.getString("category");
                // String postCategory = board.getString("자유");
                System.out.println("category 받아오기 :" + postCategory);
                String postTitle = board.getString("title");
                String postAuthor = board.getString("author");
                String postDate = board.getString("date");
                String postBody = board.getString("body");
                JSONArray postTag = board.getJSONArray("tag");
                JSONObject Meta = board.getJSONObject("meta");

                commentArray = board.getJSONArray("comment"); // 댓글 수 받아오기 위해서 댓글 Array 불러옴
                int Comment = commentArray.length(); // 댓글 수

                int Hit = Meta.getInt("hit");
                int Like = Meta.getInt("like");
                int Hate = Meta.getInt("hate");

                ArrayList tags = new ArrayList();

                if (tags != null) {
                    int len = postTag.length();
                    for (int i = 0; i < len; i++) {
                        tags.add(postTag.get(i).toString());
                        tagString += tags.get(i) + " ";
                    }
                }


                Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle + " / 글쓴이: " + postAuthor);
                Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + tagString + " / 조회 수: " + Hit + " / 좋아요 수: " + Like + " / 댓글 수: " + Comment);

                boardData = new BoardData(boardId, postCategory, postTitle, postAuthor, postDate, postBody, tagString, Hit, Like, Comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return boardData;
    }

    public CommentData getComment(JSONObject response) {
        JSONObject comment;

        System.out.println("JsonParser의 getComment 부분");

        if (response != null || response.length() > 0) {
            try {
                comment = response.getJSONObject("board");
                String boardId = comment.getString("_id");
                commentArray = comment.getJSONArray("comment");
                for (int i = 0; i < commentArray.length(); i++) {
                    JSONObject jsonObject = commentArray.getJSONObject(i);
                    authorList.add(jsonObject.getString("author"));
                    dateList.add(jsonObject.getString("date"));
                    bodyList.add(jsonObject.getString("body"));
                    commentData = new CommentData(boardId, authorList.get(i), dateList.get(i), bodyList.get(i));
                }
                System.out.println("" + authorList + "\n" + "" + dateList + "\n" + bodyList);
                // [] [] []로 뜸

                Log.d("JsonParser: ", "boardId: " + boardId + " /  글쓴이: " + authorList.get(0) + " / 날짜: " + dateList.get(0) + " / 글 내용: " + bodyList.get(0));

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        System.out.println(commentData.getAuthor());
        return commentData;
    }

//    private ArrayList<BoardData> getListData(JSONObject reponse) {
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
//
//    }
}

