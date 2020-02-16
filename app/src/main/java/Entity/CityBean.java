package Entity;

public class CityBean {

    @Override
    public String toString() {
        return "CityBean{" +
                "label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

    /**
     * label : 北京Beijing010
     * name : 北京
     * pinyin : Beijing
     * zip : 010
     */

    private String label;
    private String name;
    private String pinyin;
    private String zip;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
