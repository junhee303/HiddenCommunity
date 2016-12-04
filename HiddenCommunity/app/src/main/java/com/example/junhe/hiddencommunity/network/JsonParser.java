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
    JSONArray postCommentList;

    //JSONObject
    public ArrayList<BoardData> getBoardData(JSONObject response) {
        ArrayList<BoardData> boardDataList = new ArrayList<>();
        BoardData boardData;

        System.out.println("JsonParser의 getBoardData 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONArray postBoardsList = response.getJSONArray("boards");

                ArrayList<JSONObject> boardsList = new ArrayList<>();

                System.out.println("postBoardList의 길이" + postBoardsList.length());

                if (boardsList != null) {
                    int len = postBoardsList.length();
                    for (int i = 0; i < len; i++) {
                        boardsList.add(postBoardsList.getJSONObject(i));
                        String tagString = "";

                        String boardId = boardsList.get(i).getString("_id"); // 게시글 ID
                        String postCategory = boardsList.get(i).getString("category"); // 게시판 카테고리
                        String postTitle = boardsList.get(i).getString("title"); // 제목
                        String postAuthor = boardsList.get(i).getString("author"); // 작성자
                        String postBody = boardsList.get(i).getString("body"); // 내용
                        JSONArray postTag = boardsList.get(i).getJSONArray("tag"); // 태그 배열
                        JSONObject Meta = boardsList.get(i).getJSONObject("meta"); // 조회 수, 좋아요 수
                        postCommentList = boardsList.get(i).getJSONArray("comment"); // 댓글 수 받아오기 위해서 댓글 Array 불러옴
                        int Hit = Meta.getInt("hit"); // 조회 수
                        int Like = Meta.getInt("like"); // 좋아요 수
                        int Comment = postCommentList.length(); // 댓글 수
                        //int Hate = Meta.getInt("hate"); // 신고하기 수

                        ArrayList tags2 = new ArrayList(); // 태그 배열 스트링으로 바꾸기
                        if (tags2 != null) {
                            int len2 = postTag.length();
                            for (int j = 0; j < len2; j++) {
                                tags2.add(postTag.get(j).toString());
                                tagString += tags2.get(j) + " ";
                            }
                        }
                        // postDate를 T를 기준으로 날짜와 시간으로 나누기
                        String totalDate = boardsList.get(i).getString("date"); // 날짜
                        String date = totalDate.substring(0,totalDate.indexOf("T"));
                        String time = totalDate.substring(totalDate.indexOf("T")+1, totalDate.indexOf("."));
                        String postDate = date + "      " + time;


                        Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle + " / 글쓴이: " + postAuthor);
                        Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + tagString + " / 조회 수: " + Hit + " / 좋아요 수: " + Like + " / 댓글 수: " + Comment);


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
                String boardId = board.getString("_id"); // 게시글 ID
                String postCategory = board.getString("category"); // 게시판 카테고리
                String postTitle = board.getString("title"); // 제목
                String postAuthor = board.getString("author"); // 작성자
                String postBody = board.getString("body"); // 내용
                JSONArray postTag = board.getJSONArray("tag"); // 태그 배열
                JSONObject Meta = board.getJSONObject("meta"); // 조회 수, 좋아요 수
                postCommentList = board.getJSONArray("comment"); // 댓글 수 받아오기 위해서 댓글 Array 불러옴
                int Hit = Meta.getInt("hit"); // 조회 수
                int Like = Meta.getInt("like"); // 좋아요 수
                int Comment = postCommentList.length(); // 댓글 수
                //int Hate = Meta.getInt("hate"); // 신고하기 수

                ArrayList tags = new ArrayList(); // 태그 배열 스트링으로 바꾸기
                if (tags != null) {
                    int len = postTag.length();
                    for (int i = 0; i < len; i++) {
                        tags.add(postTag.get(i).toString());
                        tagString += tags.get(i) + " ";
                    }
                }

                //getComment(response);

                String totalDate = board.getString("date"); // 날짜
                String date = totalDate.substring(0,totalDate.indexOf("T"));
                String time = totalDate.substring(totalDate.indexOf("T")+1, totalDate.indexOf("."));
                String postDate = date + "      " + time;

                Log.d("JsonParser: ", "boardId: " + boardId + " / 작성 게시판: " + postCategory + " / 글 제목: " + postTitle + " / 글쓴이: " + postAuthor);
                Log.d("JsonParser: ", " / 날짜: " + postDate + " / 글내용: " + postBody + " / 태그: " + tagString + " / 조회 수: " + Hit + " / 좋아요 수: " + Like + " / 댓글 수: " + Comment);

                boardData = new BoardData(boardId, postCategory, postTitle, postAuthor, postDate, postBody, tagString, Hit, Like, Comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return boardData;
    }

    public ArrayList<CommentData> getComment(JSONObject response) {
        ArrayList<CommentData> commentDataList = new ArrayList<>();
        CommentData commentData;

        System.out.println("JsonParser의 getComment 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONObject board = response.getJSONObject("board");
                String boardId = board.getString("_id");
                postCommentList = board.getJSONArray("comment");

                ArrayList<JSONObject> commentList = new ArrayList<>();
                System.out.println("postCommentList의 길이" + postCommentList.length());
                if (commentList != null) {
                    int len = postCommentList.length();
                    for (int i = 0; i < len; i++) {
                        commentList.add(postCommentList.getJSONObject(i));

                        String commentId = commentList.get(i).getString("_id");
                        String commentAuthor = commentList.get(i).getString("author");
                        String commentBody = commentList.get(i).getString("body");

                        String totalDate = commentList.get(i).getString("date"); // 날짜
                        String date = totalDate.substring(0,totalDate.indexOf("T"));
                        String time = totalDate.substring(totalDate.indexOf("T")+1, totalDate.indexOf("."));
                        String commentDate = date + "      " + time;

                        Log.d("JsonParser: ", "boardId: " + boardId + " / commentId: " + commentId + " /  댓글쓴이: " + commentAuthor + " / 댓글 쓴 날짜: " + commentDate + " / 댓글 내용: " + commentBody);

                        commentData = new CommentData(boardId, commentAuthor, commentDate, commentBody);
                        commentDataList.add(commentData);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return commentDataList;
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

