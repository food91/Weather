package data;

import java.io.Serializable;
import java.util.List;

public class WeatherData implements Serializable {

    public static final String DATANAME="WeatherData";

    @Override
    public String toString() {
        return "WeaterData{" +
                "cityid='" + cityid + '\'' +
                ", update_time='" + update_time + '\'' +
                ", city='" + city + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", country='" + country + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * cityid : 101280601
     * update_time : 2020-01-29 18:00:00
     * city : 深圳
     * cityEn : shenzhen
     * country : 中国
     * countryEn : China
     * data : [{"day":"29日（今天）","date":"2020-01-29","week":"星期三","wea":"多云","wea_img":"yun","air":35,"humidity":58,"air_level":"优","air_tips":"空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！","alarm":{"alarm_type":"","alarm_level":"","alarm_content":""},"tem1":"17℃","tem2":"9℃","tem":"13℃","win":["东北风"],"win_speed":"3-4级","hours":[{"day":"29日20时","wea":"晴","tem":"13℃","win":"东北风","win_speed":"3-4级"},{"day":"29日23时","wea":"多云","tem":"12℃","win":"东北风","win_speed":"3-4级"},{"day":"30日02时","wea":"多云","tem":"10℃","win":"东北风","win_speed":"3-4级"},{"day":"30日05时","wea":"多云","tem":"9℃","win":"东北风","win_speed":"3-4级"}],"index":[{"title":"紫外线指数","level":"弱","desc":"辐射较弱，涂擦SPF12-15、PA+护肤品。"},{"title":"<\/em><em><\/em><em><\/em><em>","level":null,"desc":"春天不减肥，夏天徒伤悲。风虽有点大，室内可健身。"},{"title":"健臻·血糖指数","level":"不易波动","desc":"天气条件好，血糖不易波动，可适时进行户外锻炼。"},{"title":"穿衣指数","level":"较冷","desc":"建议着厚外套加毛衣等服装。"},{"title":"洗车指数","level":"适宜","desc":"天气较好，适合擦洗汽车。"},{"title":"空气污染扩散指数","level":"良","desc":"气象条件有利于空气污染物扩散。"}]},{"day":"30日（明天）","date":"2020-01-30","week":"星期四","wea":"多云","wea_img":"yun","tem1":"17℃","tem2":"9℃","tem":"9℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"30日08时","wea":"多云","tem":"9℃","win":"东北风","win_speed":"3-4级"},{"day":"30日11时","wea":"多云","tem":"13℃","win":"无持续风向","win_speed":"<3级"},{"day":"30日14时","wea":"多云","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"30日17时","wea":"多云","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"30日20时","wea":"多云","tem":"14℃","win":"无持续风向","win_speed":"<3级"},{"day":"30日23时","wea":"晴","tem":"13℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日02时","wea":"晴","tem":"11℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日05时","wea":"晴","tem":"10℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"弱","desc":"辐射较弱，涂擦SPF12-15、PA+护肤品。"},{"title":"减肥指数","level":null,"desc":"春天不减肥，夏天徒伤悲。天气较舒适，快去运动吧。"},{"title":"健臻·血糖指数","level":"不易波动","desc":"天气条件好，血糖不易波动，可适时进行户外锻炼。"},{"title":"穿衣指数","level":"较冷","desc":"建议着厚外套加毛衣等服装。"},{"title":"洗车指数","level":"适宜","desc":"天气较好，适合擦洗汽车。"},{"title":"空气污染扩散指数","level":"中","desc":"易感人群应适当减少室外活动。"}]},{"day":"31日（后天）","date":"2020-01-31","week":"星期五","wea":"多云","wea_img":"yun","tem1":"18℃","tem2":"10℃","tem":"13℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"31日08时","wea":"多云","tem":"10℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日11时","wea":"多云","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日14时","wea":"晴","tem":"18℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日17时","wea":"晴","tem":"17℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日20时","wea":"晴","tem":"15℃","win":"无持续风向","win_speed":"<3级"},{"day":"31日23时","wea":"晴","tem":"14℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日02时","wea":"晴","tem":"14℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日05时","wea":"晴","tem":"13℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"弱","desc":"辐射较弱，涂擦SPF12-15、PA+护肤品。"},{"title":"减肥指数","level":null,"desc":"春天不减肥，夏天徒伤悲。天气较舒适，快去运动吧。"},{"title":"健臻·血糖指数","level":"不易波动","desc":"天气条件好，血糖不易波动，可适时进行户外锻炼。"},{"title":"穿衣指数","level":"较舒适","desc":"建议穿薄外套或牛仔裤等服装。"},{"title":"洗车指数","level":"适宜","desc":"天气较好，适合擦洗汽车。"},{"title":"空气污染扩散指数","level":"中","desc":"易感人群应适当减少室外活动。"}]},{"day":"1日（周六）","date":"2020-02-01","week":"星期六","wea":"多云","wea_img":"yun","tem1":"19℃","tem2":"12℃","tem":"15℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"01日08时","wea":"多云","tem":"14℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日11时","wea":"多云","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日14时","wea":"多云","tem":"17℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日17时","wea":"多云","tem":"19℃","win":"无持续风向","win_speed":"<3级"},{"day":"01日20时","wea":"多云","tem":"18℃","win":"无持续风向","win_speed":"<3级"},{"day":"02日02时","wea":"多云","tem":"15℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"},{"title":"减肥指数","level":null,"desc":"春天不减肥，夏天徒伤悲。天气较舒适，快去运动吧。"},{"title":"健臻·血糖指数","level":"不易波动","desc":"天气条件好，血糖不易波动，可适时进行户外锻炼。"},{"title":"穿衣指数","level":"较舒适","desc":"建议穿薄外套或牛仔裤等服装。"},{"title":"洗车指数","level":"适宜","desc":"天气较好，适合擦洗汽车。"},{"title":"空气污染扩散指数","level":"中","desc":"易感人群应适当减少室外活动。"}]},{"day":"2日（周日）","date":"2020-02-02","week":"星期日","wea":"多云转阴","wea_img":"yun","tem1":"20℃","tem2":"15℃","tem":"16℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"02日08时","wea":"多云","tem":"15℃","win":"无持续风向","win_speed":"<3级"},{"day":"02日14时","wea":"多云","tem":"20℃","win":"无持续风向","win_speed":"<3级"},{"day":"02日20时","wea":"多云","tem":"17℃","win":"无持续风向","win_speed":"<3级"},{"day":"03日02时","wea":"晴","tem":"16℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"弱","desc":"辐射较弱，涂擦SPF12-15、PA+护肤品。"},{"title":"减肥指数","level":null,"desc":"春天不减肥，夏天徒伤悲。天气较舒适，快去运动吧。"},{"title":"健臻·血糖指数","level":"较易波动","desc":"血糖较易波动，注意监测。"},{"title":"穿衣指数","level":"较舒适","desc":"建议穿薄外套或牛仔裤等服装。"},{"title":"洗车指数","level":"较适宜","desc":"无雨且风力较小，易保持清洁度。"},{"title":"空气污染扩散指数","level":"中","desc":"易感人群应适当减少室外活动。"}]},{"day":"3日（周一）","date":"2020-02-03","week":"星期一","wea":"阴转小雨","wea_img":"yu","tem1":"22℃","tem2":"13℃","tem":"14℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"03日08时","wea":"阴","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"03日14时","wea":"多云","tem":"21℃","win":"无持续风向","win_speed":"<3级"},{"day":"03日20时","wea":"阴","tem":"18℃","win":"无持续风向","win_speed":"<3级"},{"day":"04日02时","wea":"小雨","tem":"14℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"最弱","desc":"辐射弱，涂擦SPF8-12防晒护肤品。"},{"title":"减肥指数","level":null,"desc":"春天不减肥，夏天徒伤悲。天气较舒适，快去运动吧。"},{"title":"健臻·血糖指数","level":"较易波动","desc":"血糖较易波动，注意监测。"},{"title":"穿衣指数","level":"较舒适","desc":"建议穿薄外套或牛仔裤等服装。"},{"title":"洗车指数","level":"不宜","desc":"有雨，雨水和泥水会弄脏爱车。"},{"title":"空气污染扩散指数","level":"较差","desc":"气象条件较不利于空气污染物扩散。。"}]},{"day":"4日（周二）","date":"2020-02-04","week":"星期二","wea":"小雨转阴","wea_img":"yu","tem1":"19℃","tem2":"13℃","tem":"16℃","win":["无持续风向","无持续风向"],"win_speed":"<3级","hours":[{"day":"04日08时","wea":"多云","tem":"13℃","win":"无持续风向","win_speed":"<3级"},{"day":"04日14时","wea":"小雨","tem":"18℃","win":"无持续风向","win_speed":"<3级"},{"day":"04日20时","wea":"小雨","tem":"16℃","win":"无持续风向","win_speed":"<3级"},{"day":"05日02时","wea":"阴","tem":"16℃","win":"无持续风向","win_speed":"<3级"}],"index":[{"title":"紫外线指数","level":"最弱","desc":"辐射弱，涂擦SPF8-12防晒护肤品。"},{"title":"<\/em><em><\/em><em><\/em><em>","level":null,"desc":"春天快来了，雨天坚持室内运动吧。"},{"title":"健臻·血糖指数","level":"较易波动","desc":"血糖较易波动，注意监测。"},{"title":"穿衣指数","level":"较舒适","desc":"建议穿薄外套或牛仔裤等服装。"},{"title":"洗车指数","level":"不宜","desc":"有雨，雨水和泥水会弄脏爱车。"},{"title":"空气污染扩散指数","level":"良","desc":"气象条件有利于空气污染物扩散。"}]}]
     */

    private String cityid;
    private String update_time;
    private String city;
    private String cityEn;
    private String country;
    private String countryEn;
    private List<DataBean> data;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * day : 29日（今天）
         * date : 2020-01-29
         * week : 星期三
         * wea : 多云
         * wea_img : yun
         * air : 35
         * humidity : 58
         * air_level : 优
         * air_tips : 空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！
         * alarm : {"alarm_type":"","alarm_level":"","alarm_content":""}
         * tem1 : 17℃
         * tem2 : 9℃
         * tem : 13℃
         * win : ["东北风"]
         * win_speed : 3-4级
         * hours : [{"day":"29日20时","wea":"晴","tem":"13℃","win":"东北风","win_speed":"3-4级"},{"day":"29日23时","wea":"多云","tem":"12℃","win":"东北风","win_speed":"3-4级"},{"day":"30日02时","wea":"多云","tem":"10℃","win":"东北风","win_speed":"3-4级"},{"day":"30日05时","wea":"多云","tem":"9℃","win":"东北风","win_speed":"3-4级"}]
         * index : [{"title":"紫外线指数","level":"弱","desc":"辐射较弱，涂擦SPF12-15、PA+护肤品。"},{"title":"<\/em><em><\/em><em><\/em><em>","level":null,"desc":"春天不减肥，夏天徒伤悲。风虽有点大，室内可健身。"},{"title":"健臻·血糖指数","level":"不易波动","desc":"天气条件好，血糖不易波动，可适时进行户外锻炼。"},{"title":"穿衣指数","level":"较冷","desc":"建议着厚外套加毛衣等服装。"},{"title":"洗车指数","level":"适宜","desc":"天气较好，适合擦洗汽车。"},{"title":"空气污染扩散指数","level":"良","desc":"气象条件有利于空气污染物扩散。"}]
         */

        private String day;
        private String date;
        private String week;
        private String wea;
        private String wea_img;
        private int air;
        private int humidity;
        private String air_level;
        private String air_tips;
        private AlarmBean alarm;
        private String tem1;
        private String tem2;
        private String tem;
        private String win_speed;
        private List<String> win;
        private List<HoursBean> hours;
        private List<IndexBean> index;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWea() {
            return wea;
        }

        public void setWea(String wea) {
            this.wea = wea;
        }

        public String getWea_img() {
            return wea_img;
        }

        public void setWea_img(String wea_img) {
            this.wea_img = wea_img;
        }

        public int getAir() {
            return air;
        }

        public void setAir(int air) {
            this.air = air;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public String getAir_level() {
            return air_level;
        }

        public void setAir_level(String air_level) {
            this.air_level = air_level;
        }

        public String getAir_tips() {
            return air_tips;
        }

        public void setAir_tips(String air_tips) {
            this.air_tips = air_tips;
        }

        public AlarmBean getAlarm() {
            return alarm;
        }

        public void setAlarm(AlarmBean alarm) {
            this.alarm = alarm;
        }

        public String getTem1() {
            return tem1;
        }

        public void setTem1(String tem1) {
            this.tem1 = tem1;
        }

        public String getTem2() {
            return tem2;
        }

        public void setTem2(String tem2) {
            this.tem2 = tem2;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getWin_speed() {
            return win_speed;
        }

        public void setWin_speed(String win_speed) {
            this.win_speed = win_speed;
        }

        public List<String> getWin() {
            return win;
        }

        public void setWin(List<String> win) {
            this.win = win;
        }

        public List<HoursBean> getHours() {
            return hours;
        }

        public void setHours(List<HoursBean> hours) {
            this.hours = hours;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public static class AlarmBean implements  Serializable{
            /**
             * alarm_type :
             * alarm_level :
             * alarm_content :
             */

            private String alarm_type;
            private String alarm_level;
            private String alarm_content;

            public String getAlarm_type() {
                return alarm_type;
            }

            public void setAlarm_type(String alarm_type) {
                this.alarm_type = alarm_type;
            }

            public String getAlarm_level() {
                return alarm_level;
            }

            public void setAlarm_level(String alarm_level) {
                this.alarm_level = alarm_level;
            }

            public String getAlarm_content() {
                return alarm_content;
            }

            public void setAlarm_content(String alarm_content) {
                this.alarm_content = alarm_content;
            }
        }

        public static class HoursBean implements  Serializable{
            /**
             * day : 29日20时
             * wea : 晴
             * tem : 13℃
             * win : 东北风
             * win_speed : 3-4级
             */

            private String day;
            private String wea;
            private String tem;
            private String win;
            private String win_speed;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getWea() {
                return wea;
            }

            public void setWea(String wea) {
                this.wea = wea;
            }

            public String getTem() {
                return tem;
            }

            public void setTem(String tem) {
                this.tem = tem;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public String getWin_speed() {
                return win_speed;
            }

            public void setWin_speed(String win_speed) {
                this.win_speed = win_speed;
            }
        }

        public static class IndexBean implements Serializable{
            /**
             * title : 紫外线指数
             * level : 弱
             * desc : 辐射较弱，涂擦SPF12-15、PA+护肤品。
             */

            private String title;
            private String level;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
