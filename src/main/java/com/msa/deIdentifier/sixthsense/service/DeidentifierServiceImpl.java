package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mongodb.Info;
import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Service
public class DeidentifierServiceImpl implements  DeidentifierService{

    @Autowired
    ResultService resultService;

    // 비식별화
    @Override
    public ResultLog deidentification(SummaryData summaryData) throws IOException, SQLException {
        // dataSource 가져오기
        Charset charset = Charset.forName("EUC-KR");
        char separator = ',';
        boolean containsHeader = true;

        // 최초의 원래 위치 지정
        // 파일의 origin 위치 가져옴
        String originLocation = summaryData.getOriginLocation();
        DataSource source = DataSource.createCSVSource(originLocation, charset, separator, containsHeader);

        // column 지정
        List<Info> infoList = summaryData.getInfo();
        for(int i = 0; i<infoList.size(); i++){
            source.addColumn(i, DataType.STRING);
        }
        // Data 생성
        Data data = createData(source);

        // anonymizer
        ARXAnonymizer anonymizer = new ARXAnonymizer();

        for(Info info : infoList){
            String attributeName = info.getColName();
            // no라고 써져있는 것 빼고 나머지 마스킹 처리
            if(info.getSummary().getDeIdentified().equals("no")) {
                data.getDefinition().setAttributeType(attributeName, AttributeType.INSENSITIVE_ATTRIBUTE);
            }else{
                data.getDefinition().setAttributeType(attributeName, AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
                data.getDefinition().setMinimumGeneralization(attributeName,1);
            }
        }

        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0.1d);
        config.setQualityModel(Metric.createEntropyMetric());

        ARXResult result = anonymizer.anonymize(data,config);

        // 최종 저장위치 수정해야함
        StringBuilder resultLocation = new StringBuilder(originLocation.replace(".csv", ""));
        resultLocation.append("_");

        // 타임스탬프 붙임
        LocalDateTime ldt = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(ldt);
        int nowTimeStamp = ts.getNanos();
        String timestampString = String.valueOf(nowTimeStamp);
        resultLocation.append(timestampString);

        // .csv 붙임
        resultLocation.append(".csv");

        // 파일 저장
        String resultFileLocation = resultLocation.toString();
        result.getOutput(false).save(resultFileLocation, separator);
        String[] checkSaveName = saveCsv(resultFileLocation);

        // update resultLog
        ResultLog resultLog = new ResultLog();
        resultLog.setFileName(summaryData.getFileName());
        resultLog.setOriginLocation(originLocation);
        resultLog.setUserName(summaryData.getUserName());

        if(!checkSaveName.equals("")){
            resultLog.setIsSucceed(true);
            resultLog.setResultFileName(checkSaveName[0]);
            resultLog.setResultLocation(checkSaveName[1]);
        }else{
            resultLog.setIsSucceed(false);
        }
        // 결과값 저장하는것 필요
        return resultService.createResultLog(resultLog);
    }

    // 결과값 pdf 저장
    @Override
    public ResultLog saveResult(ARXResult arxResult) {

        return null;
    }

    public static String[] saveCsv(String inputFile) throws IOException {
        // 리턴값 지정
        // 0은 파일 명, 1은 파일 위치
        String[] returnString = new String[2];
        // 파일 읽기
        File inputPath = new File(inputFile);

        // 파일 출력 지정
        StringBuilder resultLocation = new StringBuilder(inputFile.replace(".csv", ""));
        resultLocation.append("_Result.csv");
        returnString[1] = resultLocation.toString();
        String[] splitLocation = returnString[1].split("/");
        returnString[0] = splitLocation[splitLocation.length-1];

        System.out.println("input location : " + inputFile);
        System.out.println("result location : " + resultLocation);
        File outputPath = new File(returnString[1]);

        // 파일 입력
        FileInputStream fileInputStream = new FileInputStream(inputPath);
        Charset inputCharSet = Charset.forName("UTF-8");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, inputCharSet);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 파일 출력
        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        // bom 추가
        fileOutputStream.write(0xEF);
        fileOutputStream.write(0xBB);
        fileOutputStream.write(0xBF);
        Charset outputCharSet = Charset.forName("UTF-8");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, outputCharSet);

        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        while(true){
            String str = bufferedReader.readLine();
            // System.out.println("변환 : " + str);
            if(str == null) break;
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedReader.close();
        bufferedWriter.close();
        if(inputPath.delete()){
            System.out.println("UTF 변환 전 파일 삭제");
        }else{
            System.out.println("UTF 변환 전 파일 삭제 실패");
        };
        System.out.println("save success");
        if(outputPath.exists()){
            return returnString;
        }else{
            returnString[0] = "";
            returnString[1] = "";
            return returnString;
        }
    }

    public static Data createData(final DataSource source) throws IOException, SQLException {
        // Load data
        Data data = Data.create(source);
        for (int i = 0; i < data.getHandle().getNumColumns(); i++) {
            String attribute = data.getHandle().getAttributeName(i);
            data.getDefinition().setAttributeType(attribute, getHierarchy(data, attribute));
        }
        return data;
    }

    private static int shortestStringLength(String[] list) {
        int result = list[0].length();
        for (String data : list) {
            if (data.length() < result) {
                result = data.length();
            }
        }
        return result;
    }

    private static String[] makeStringArray(String value, int number) {
        String[] result = new String[number + 1];
        for (int i = 0; i <= number; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(DataType.ANY_VALUE);
            }
            sb.append(value.substring(i, value.length()));
            result[i] = sb.toString();
        }
        return result;
    }

    private static AttributeType.Hierarchy.DefaultHierarchy getHierarchy(Data data, String attribute) throws IOException {
        AttributeType.Hierarchy.DefaultHierarchy hierarchy = AttributeType.Hierarchy.create();
        int col = data.getHandle().getColumnIndexOf(attribute);
        String[] values = data.getHandle().getDistinctValues(col);
        // values중에 가장 작은 단어길이를 찾아서 생성
        int shortLength = shortestStringLength(values);

        for (int i = 0; i < values.length; i++) {
            String[] valueArray = makeStringArray(values[i], shortLength);
            hierarchy.add(valueArray);
        }
        return hierarchy;
    }

    protected static void printResult(final ARXResult result, final Data data) {

        // Print time
        final DecimalFormat df1 = new DecimalFormat("#####0.00");
        final String sTotal = df1.format(result.getTime() / 1000d) + "s";
        System.out.println(" - Time needed: " + sTotal);

        // Extract
        final ARXLattice.ARXNode optimum = result.getGlobalOptimum();
        final List<String> qis = new ArrayList<String>(data.getDefinition().getQuasiIdentifyingAttributes());

        if (optimum == null) {
            System.out.println(" - No solution found!");
            return;
        }

        // Initialize
        final StringBuffer[] identifiers = new StringBuffer[qis.size()];
        final StringBuffer[] generalizations = new StringBuffer[qis.size()];
        int lengthI = 0;
        int lengthG = 0;
        for (int i = 0; i < qis.size(); i++) {
            identifiers[i] = new StringBuffer();
            generalizations[i] = new StringBuffer();
            identifiers[i].append(qis.get(i));
            generalizations[i].append(optimum.getGeneralization(qis.get(i)));
            if (data.getDefinition().isHierarchyAvailable(qis.get(i)))
                generalizations[i].append("/").append(data.getDefinition().getHierarchy(qis.get(i))[0].length - 1);
            lengthI = Math.max(lengthI, identifiers[i].length());
            lengthG = Math.max(lengthG, generalizations[i].length());
        }

        // Padding
        for (int i = 0; i < qis.size(); i++) {
            while (identifiers[i].length() < lengthI) {
                identifiers[i].append(" ");
            }
            while (generalizations[i].length() < lengthG) {
                generalizations[i].insert(0, " ");
            }
        }

        // Print
        System.out.println(" - Information loss: " + result.getGlobalOptimum().getLowestScore() + " / " + result.getGlobalOptimum().getHighestScore());
        System.out.println(" - Optimal generalization");
        for (int i = 0; i < qis.size(); i++) {
            System.out.println("   * " + identifiers[i] + ": " + generalizations[i]);
        }
        System.out.println(" - Statistics");
        System.out.println(result.getOutput(result.getGlobalOptimum(), false).getStatistics().getEquivalenceClassStatistics());
    }

    protected static void print(DataHandle handle) {
        final Iterator<String[]> itHandle = handle.iterator();
        print(itHandle);
    }
    protected static void print(Iterator<String[]> iterator) {
        while (iterator.hasNext()) {
            System.out.print("   ");
            System.out.println(Arrays.toString(iterator.next()));
        }
    }
}
