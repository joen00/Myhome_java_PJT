package com.ssafy.rent.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.ssafy.rent.model.dto.HomeInfo;
import com.ssafy.rent.model.dto.TourismInfo;

public class TravelSaxParser {
    // public List<TourismInfo> tourList= new ArrayList<TourismInfo>();
    //public Map<String, TourismInfo> tourList;
    //private TourismInfo tourisminfo;
    private int size;

    public List<TourismInfo> read(String csvFile) {
        List<TourismInfo> tourList = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);

            BufferedReader br = new BufferedReader(fr);
            String temp = br.readLine();
            
            while ((temp = br.readLine()) != null) {
//              }
                TourismInfo info = new TourismInfo();
                StringTokenizer st = new StringTokenizer(temp, ",");
                for (int i = 0; i < 12; i++) {
                    String tempToken = st.nextToken();
                    if (i == 1) {
                        info.setClassification(tempToken);
                        //System.out.print(tempToken+"/");
                    } else if (i == 2) {
                        info.setTourname(tempToken);
                        //System.out.print(tempToken+"/");
                    } else if (i == 6) {
                        info.setDong(tempToken);
                        //System.out.println(tempToken);
                        tourList.add(info);
                    }
                    
                }
            }
            // temp= br.readLine();}
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tourList;
    }

    public static void main(String[] args) {
        String csvFile = "./res/travelData.csv";
        new TravelSaxParser().read(csvFile);

    }

}