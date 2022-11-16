import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCodeMMD {

    @Test
    public void asd(){
        String city = "上海\n" +
                "合肥\n" +
                "南京\n" +
                "杭州\n" +
                "苏州\n" +
                "无锡\n" +
                "宁波\n" +
                "南通\n" +
                "常州\n" +
                "绍兴\n" +
                "盐城\n" +
                "扬州\n" +
                "泰州\n" +
                "台州\n" +
                "嘉兴\n" +
                "镇江\n" +
                "金华\n" +
                "芜湖\n" +
                "湖州\n" +
                "马鞍山\n" +
                "安庆\n" +
                "滁州\n" +
                "舟山\n" +
                "宣城\n" +
                "铜陵\n" +
                "池州\n" +
                "温州\n" +
                "六安\n" +
                "蚌埠\n" +
                "淮南\n" +
                "淮北\n" +
                "黄山\n" +
                "阜阳\n" +
                "宿州\n" +
                "巢湖\n" +
                "亳州\n";
        String[] split = city.split("\n");
        List<String> cityList = new ArrayList<>();
        for (String s : split) {
            cityList.add('"'+s+"市"+'"');
        }
        System.out.println(cityList);

        String index = "地区生产总值_累计值\n" +
                "地区生产总值_累计增长\n" +
                "社会消费品零售总额_累计值\n" +
                "社会消费品零售总额_累计增长\n" +
                "规模以上工业增加值_累计增长\n" +
                "固定资产投资_累计值\n" +
                "固定资产投资_累计增长\n" +
                "居民消费价格指数(上年同期=100)\n";
        List<String> indexList = new ArrayList<>();
        for (String s : index.split("\n")) {
            indexList.add('"'+s+'"');
        }
        System.out.println(indexList);
    }
}
