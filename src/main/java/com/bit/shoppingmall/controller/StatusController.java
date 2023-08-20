package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Status;
import com.bit.shoppingmall.service.StatusService;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatusController extends HttpServlet {
    private final StatusService statusService;
    private JSONObject jsonObject = null;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(jsonObject==null){
            List<Status> statuses = statusService.selectAll();
            Map<String, List<String>> largeStatus = new LinkedHashMap<>();
            Map<String, List<String>> detailStatus = new LinkedHashMap<>();
            initStatus(statuses, largeStatus, detailStatus);

            jsonObject = new JSONObject();
            try {
                jsonObject.put("mainStatus", largeStatus);
                jsonObject.put("detailStatus", detailStatus);
            }catch(Exception e){ // TODO 무슨 예외를 던질가
                throw new RuntimeException();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

    private void initStatus(List<Status> statuses, Map<String, List<String>> largeStatus,
                              Map<String, List<String>> detailStatus){
        for(Status status : statuses){
            if(status.getMasterStatusId()==null){
                largeStatus.put(status.getStatusId()+";"+status.getStatusName(),new ArrayList<>());
            }
        }

        setStatus(statuses, largeStatus, detailStatus);

    }

    private void setStatus(List<Status> statuses, Map<String, List<String>> statusMap,
                             Map<String, List<String>> nextStatusMap) {
        for (String statusKey : statusMap.keySet()) {
            String[] key = statusKey.split(";");
            for (Status status : statuses) {
                if(status.getMasterStatusId()==null) continue;
                if (Long.parseLong(key[0]) == status.getMasterStatusId()) { // 타입 변환 후 비교
                    List<String> list = statusMap.get(statusKey);
                    list.add(status.getStatusId() + ";" + status.getStatusName());
                    nextStatusMap.put(status.getStatusId() + ";" + status.getStatusName(), new ArrayList<>());
                }
            }
        }
    }
}