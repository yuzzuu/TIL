package com.example.quizapp;

import org.json.JSONObject;
import java.io.*;
import java.net.*;

public class RestAPI {
    static void restFunction(String nexturl, String answer) {
    	HttpURLConnection conn = null;
		JSONObject responseJson = null;
		 try {
		        //URL 설정
		        URL url = new URL("http://13.125.222.176" + "/quiz/" + nexturl);
		        conn = (HttpURLConnection) url.openConnection();
		        //Request 형식 설정
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Content-Type", "application/json");
		        //request에 JSON data 준비
		        conn.setDoOutput(true);
		        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		        //commands라는 JSONArray를 담을 JSONObject 생성
		        JSONObject commands = new JSONObject();
		        commands.put("nickname", "문유주");
		        commands.put("yourAnswer", answer);
		        //request에 쓰기
		        bw.write(commands.toString());
		        bw.flush();
		        bw.close();

		        //보내고 결과값 받기
		        int responseCode = conn.getResponseCode();
		        if (responseCode == 400) {
		            System.out.println("400:: 해당 명령을 실행할 수 없음");
		        } else if (responseCode == 401) {
		            System.out.println("401:: X-Auth-Token Header가 잘못됨");
		        } else if (responseCode == 500) {
		            System.out.println("500:: 서버 에러, 문의 필요");
		        } else { // 성공 후 응답 JSON 데이터받기
		            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		            StringBuilder sb = new StringBuilder();
		            String line = "";
		            while ((line = br.readLine()) != null) {
		                sb.append(line);
		            }
		            responseJson = new JSONObject(sb.toString());
		        }
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		   System.out.println(responseJson);
    }
}