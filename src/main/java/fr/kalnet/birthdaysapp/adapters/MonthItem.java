package fr.kalnet.birthdaysapp.adapters;

public class MonthItem extends ListItem{

    public String month;

    public MonthItem(String month) {
        this.month = month;
    }

    @Override
    public int getType() {
        return TYPE_MONTH;
    }
}