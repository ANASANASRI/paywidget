package ma.m2t.paywidget.enums;


public enum AneeActivite {
    MOINS_1_AN("Moins < 1 an"),
    UN_A_5_ANS("1 an à 5 ans"),
    PLUS_5_ANS("Plus > 5 ans");

    private final String label;

    private AneeActivite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}