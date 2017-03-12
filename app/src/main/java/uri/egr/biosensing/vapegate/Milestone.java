package uri.egr.biosensing.vapegate;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by nickp on 3/11/2017.
 */

public class Milestone extends First
{
    ListView list;
    String title[] = {"20 Mins", "2 Hours", "12 Hours", "24 Hours", "2 Days", "3 Days", "2 Weeks",
                        "6 Months", "1 Year", "5 Years", "10 Years", "15 Years"};
    String desc[] = {"Your Heart Rate has started to drop back to normal levels!",
                        "Your heart rate and blood pressure should be back to normal levels, get ready for the cravings.",
                        "Carbon Monoxide levels in your body have started to drop, try going for a jog!",
                        "Your risk for coronary heart disease is starting to decrease, not out of the woods yet!",
                        "Your nerve senses are starting to regenerate, hows it feel to taste and smell again?!",
                        "Nicotine should be completely out of your body! Get ready for some severe cravings.",
                        "Your blood circulation and heart function should be much better!",
                        "The cilla in your lungs is starting to repair, your cravings should have stopped, that must be nice!",
                        "Your risk for heart disease has gone down by half!",
                        "Your risk of stroke is now the same as a non-smoker, congrats!!",
                        "Your risk for getting smoking-related cancers has gone down by more than half!",
                        "Your risk of heart disease and other smoking-related disease is the same as a non-smoker. YOU DID IT!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.milestone);

        list = (ListView)findViewById(R.id.mileList);

        final ArrayList<MilestoneEntry> stones = new ArrayList<MilestoneEntry>();
        for(int x = 0; x < title.length; x++)
        {
            MilestoneEntry test = new MilestoneEntry(title[x], desc[x]);
            stones.add(test);
        }
        list.setAdapter(new MilestoneAdapt(getApplicationContext(), R.layout.mile_list, stones));
    }
}
