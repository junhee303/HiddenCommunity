package data;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class MajorData{
    private String Major;
    private String SubMajor;

    public MajorData(String Major, String SubMajor) {
        this.Major = Major;
        this.SubMajor = SubMajor;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String Major) {
        this.Major = Major;
    }

    public String getSubMajor() {
        return SubMajor;
    }

    public void setSubMajor(String SubMajor) {
        this.SubMajor = SubMajor;
    }
}

