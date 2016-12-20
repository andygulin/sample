package examples.showcase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class JfreechartTest {

    private static final Logger logger = LogManager.getLogger(JfreechartTest.class);

    @Test
    public void pie() throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("管理人员", 25d);
        dataset.setValue("市场人员", 25d);
        dataset.setValue("开发人员", 45d);
        dataset.setValue("其他人员", 10d);
        JFreeChart chart = ChartFactory.createPieChart("某公司人员组织数据图", dataset, true, true, false);
        File chartFile = new File(FileUtils.getTempDirectoryPath(), "pie.png");
        ChartUtilities.saveChartAsPNG(chartFile, chart, 800, 800);
        logger.info(chartFile);
    }
}