public class WoShiNiMa {
    public static void main(String[] args) {
        String city = "北京\n" +
                "上海\n" +
                "天津\n" +
                "重庆\n" +
                "哈尔滨\n" +
                "长春\n" +
                "沈阳\n" +
                "呼和浩特\n" +
                "石家庄\n" +
                "乌鲁木齐\n" +
                "兰州\n" +
                "西宁\n" +
                "西安\n" +
                "银川\n" +
                "郑州\n" +
                "济南\n" +
                "太原\n" +
                "合肥\n" +
                "武汉\n" +
                "长沙\n" +
                "南京\n" +
                "成都\n" +
                "贵阳\n" +
                "昆明\n" +
                "南宁\n" +
                "拉萨\n" +
                "杭州\n" +
                "南昌\n" +
                "广州\n" +
                "福州\n" +
                "海口\n" +
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
                "亳州";
        String[] split = city.split("\n");
        city = "";
        for (String s : split) {
            city = city+'"'+s+"市"+'"'+",";
        }
        System.out.println(city);
    }
}
