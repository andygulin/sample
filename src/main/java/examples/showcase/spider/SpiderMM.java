package examples.showcase.spider;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpiderMM {
    public static void main(String[] args) throws Exception {
        String url = "http://www.mmjpg.com/mm/%d/%d";
        final int maxAlbumId = 1000;
        final int maxPage = 30;
        for (int i = 1; i <= maxAlbumId; i++) {
            String parentPath = "D:/mm_jpg/" + String.valueOf(i);
            FileUtils.forceMkdir(new File(parentPath));
            for (int j = 1; j <= maxPage; j++) {
                String requestURL = String.format(url, i, j);
                Map<String, String> header = new HashMap<>();
                header.put("Referer", "http://www.mmjpg.com");
                header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3493.3 Safari/537.36");
                Document document = Jsoup.connect(requestURL).headers(header).get();
                Elements elements = document.select("div.content img");
                if (elements.size() > 0) {
                    String src = elements.get(0).attr("src");
                    String fileName = parentPath + "/" + FilenameUtils.getName(src);
                    Connection.Response response = Jsoup.connect(src).headers(header).ignoreContentType(true).execute();
                    FileCopyUtils.copy(response.bodyAsBytes(), new File(fileName));
                    System.out.println(src + " -> " + fileName);
                }
            }
        }
    }
}
