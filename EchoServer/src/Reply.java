import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum Reply {
    DATE("date") {
        @Override
        public String direct(String[] strings) {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("ISO8601"));
        }
    },
    TIME("time") {
        @Override
        public String direct(String[] strings) {
            LocalTime localTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            return localTime.toString();
        }
    },
    REVERSE("reverse") {
        @Override
        public String direct(String[] strings) {
            if (strings.length > 1) {
                for (String string : strings) {
                    BUILDER.append(string).append(" ");
                }
                BUILDER.deleteCharAt(BUILDER.length() - 1);
                BUILDER.reverse();
                return BUILDER.toString();
            } else {
                return "THERE'S NOTHING TO REVERSE";
            }
        }
    },
    UPPER("upper") {
        @Override
        public String direct(String[] strings) {
            if (strings.length > 1) {
                for (String string : strings) {
                    BUILDER.append(string).append(" ");
                }
                BUILDER.deleteCharAt(BUILDER.length() - 1);
                return BUILDER.toString().toUpperCase();
            } else {
                return "THERE'S NOTHING TO UPPER";
            }
        }
    },
    BYE("bye") {
        @Override
        public String direct(String[] strings) {
            return null;
        }
    };
    private static final StringBuilder BUILDER = new StringBuilder();

    Reply(String value) {
        this.value = value;
    }


    public abstract String direct(String[] strings);

    final String value;

    public static String Act(String msgg) {
        String[] strings = msgg.split(" ");
        String str = strings[0].toLowerCase();
        for (int i = 0; i < Reply.values().length; i++) {
            if (Reply.values()[i].value.compareTo(str) == 0) {
                str = Reply.values()[i].direct(strings);
                break;
            }
        }
        return str;
    }
}
