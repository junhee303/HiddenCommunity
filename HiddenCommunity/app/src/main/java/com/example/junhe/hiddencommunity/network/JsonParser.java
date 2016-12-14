package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import com.example.junhe.hiddencommunity.Message;
import com.example.junhe.hiddencommunity.Notice;

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

    public ArrayList<String> getMessageList (JSONObject response) {
        ArrayList<String> message_otherNicknameList = new ArrayList<>();

        System.out.println("JsonParser의 getMessageList 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONArray members = response.getJSONArray("members");

                //ArrayList<JSONObject> messageOtherList = new ArrayList<>();

                System.out.println("members 길이" + members.length());

                if (message_otherNicknameList != null) {
                    int len = members.length();
                    for (int i = 0; i < len; i++) {
                        message_otherNicknameList.add(members.get(i).toString());
                        System.out.println("message_otherNicknameList에 저장된 닉네임 : " + message_otherNicknameList.get(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return message_otherNicknameList;
    }

    public ArrayList<Message> getMessage (JSONObject response, String myNickname) {
        ArrayList<Message> messageDataList = new ArrayList<>();
        Message message;

        System.out.println("JsonParser의 getMessage 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONArray messages = response.getJSONArray("msgs");

                ArrayList<JSONObject> messagesList = new ArrayList<>();

                System.out.println("해당 채팅방 메세지 갯수" + messages.length());

                if (messagesList != null) {
                    int len = messages.length();
                    for (int i = 0; i < len; i++) {
                        messagesList.add(messages.getJSONObject(i));

                        String recipient = messagesList.get(i).getString("recipient"); // 수신자
                        String body = messagesList.get(i).getString("body"); // 채팅 메세지
                        String sender = messagesList.get(i).getString("sender"); // 발신자

                        String totalDate = messagesList.get(i).getString("date"); // 날짜
                        String date = totalDate.substring(0,totalDate.indexOf("T"));
                        String time = totalDate.substring(totalDate.indexOf("T")+1, totalDate.indexOf("."));
                        String messageDate = date + "      " + time;

                        if(recipient.equals(myNickname)){ // 상대방이 보낸 메세지 - 왼쪽 정렬
                            message = new Message(true, recipient, body, sender, messageDate);
                            messageDataList.add(message);
                            Log.d("JsonParser", "left: true / " + "recipient: " + recipient + " / body : " + body + " / sender: " + sender + " / messageDate: " + messageDate);
                        } else if(sender.equals(myNickname)) { // 내가 보낸 메세지 - 오른쪽 정렬
                            message = new Message(false, recipient, body, sender, messageDate);
                            messageDataList.add(message);
                            Log.d("JsonParser", "left: false / " + "recipient: " + recipient + " / body : " + body + " / sender: " + sender + " / messageDate: " + messageDate);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messageDataList;
    }

    public ArrayList<Notice> getNoticeList (JSONObject response) {
        ArrayList<Notice> noticeDataList = new ArrayList<>();
        Notice notice;

        System.out.println("JsonParser의 getNoticeList 부분");

        if (response != null || response.length() > 0) {
            try {
                JSONArray notices = response.getJSONArray("notices");

                ArrayList<JSONObject> noticeList = new ArrayList<>();

                System.out.println("알림 갯수" + notices.length());

                if (noticeList != null) {
                    int len = notices.length();
                    for (int i = 0; i < len; i++) {
                        noticeList.add(notices.getJSONObject(i));

                        String noticeId = noticeList.get(i).getString("_id"); // 해당 알림 ID
                        String boardId = noticeList.get(i).getString("boardId"); // 해당 게시글 ID
                        String actionAuthor = noticeList.get(i).getString("actionAuthor"); // 알림 상대방
                        String type = noticeList.get(i).getString("type"); // 알림 종류
                        boolean check = noticeList.get(i).getBoolean("check"); // 알림 확인 유무

                        String totalDate = noticeList.get(i).getString("date"); // 날짜
                        String noticeDate = totalDate.substring(0,totalDate.indexOf("T"));

                            notice = new Notice(noticeId, boardId, actionAuthor, type, check, noticeDate);
                            noticeDataList.add(notice);
                            Log.d("JsonParser", "noticeId : " + noticeId + " / boardId : " + boardId + " / actionAuthor : " + actionAuthor + " / type : " + type + " / check : " + check + " / noticeDate : " + noticeDate);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return noticeDataList;
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

