package com.msa.deIdentifier.sixthsense.temp;

import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



public class DeIdentifierTest {
    public void testDeIdentifier() throws IOException, SQLException {

        DataSource source = DataSource.createCSVSource("/Users/griffindouble/downloads/police.csv", Charset.forName("EUC-KR"), ',', true);
        // 데이터 소스에서 사용하려면 원하는 컬럼 가져와야함 => mongoDB의 Summary에서 데이터 가져옴

        //Mock_data
        source.addColumn(0, DataType.INTEGER);
        source.addColumn(1, DataType.STRING);
        source.addColumn(2, DataType.STRING);
        source.addColumn(3, DataType.STRING);
        source.addColumn(4, DataType.STRING);
        source.addColumn(6, DataType.STRING);


        /* DataSource -> Data & Hierarchy */
        Data data = createData(source);
        DataHandle dh = data.getHandle();
        print(dh);

        ARXAnonymizer anonymizer = new ARXAnonymizer();

        data.getDefinition().setAttributeType("연번", AttributeType.INSENSITIVE_ATTRIBUTE);
        data.getDefinition().setAttributeType("관할경찰서", AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setMaximumGeneralization("관할경찰서", 3);
        data.getDefinition().setMinimumGeneralization("관할경찰서", 1);
        ARXConfiguration config = ARXConfiguration.create();
        // 2를 할때만 값이 옳게 나옴
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0.1d);
        config.setQualityModel(Metric.createEntropyMetric());

        //
        // Anonymize
        ARXResult result = anonymizer.anonymize(data, config);

        printResult(result, data);
        System.out.println("-Data:");
        // Process results
        if (result.isResultAvailable()) {
            System.out.println(" - Transformed data:");
            Iterator<String[]> transformed = result.getOutput(false).iterator();
            while (transformed.hasNext()) {
                System.out.print("   ");
                System.out.println(Arrays.toString(transformed.next()));
            }
        }
        result.getOutput(false).save("/Users/griffindouble/downloads/boot_test_anonymized.csv", ';');
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
//            StringBuilder sb = new StringBuilder(value.substring(0, value.length()-i));
//            for(int j = 0; j<i;j++){
//                sb.append(DataType.ANY_VALUE);
//            }
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
        //System.out.println("values : " + Arrays.toString(values));
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

    /**
     * Prints a given iterator.
     *
     * @param iterator
     */
    protected static void print(Iterator<String[]> iterator) {
        while (iterator.hasNext()) {
            System.out.print("   ");
            System.out.println(Arrays.toString(iterator.next()));
        }
    }
}
