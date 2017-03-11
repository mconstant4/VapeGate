package uri.egr.biosensing.vapegate;

/**
 * Created by nickp on 3/11/2017.
 */

public class MilestoneEntry
{
    public String desc, title;

    public MilestoneEntry(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle(){return title;}
    public String getDesc(){return desc;}
}
