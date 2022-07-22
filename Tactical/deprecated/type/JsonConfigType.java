package net.crossager.tactical.config.type;

public class JsonConfigType {

    public String listObjectSeparator(boolean compact) {
        return "," + (compact ? "" : " ");
    }


    public String createList(String name, boolean compact, String tabs) {
        return (compact ? "" : tabs) + '"' + name + "\":" + (compact ? "" : " ") +
                (compact ? "[%s]" : ("\n" + tabs + "[\n%s\n" + tabs + "]"));
    }


    public String createListInList(String name, boolean compact, String tabs) {
        return (compact ? "[%s]" : ("\n" + tabs + "[\n%s\n" + tabs + "]"));
    }


    public String createField(String name, boolean compact, String tabs) {
        return (compact ? "" : tabs) + '"' + name + "\":" + (compact ? "" : " ") + "%s";
    }


    public String createSection(String name, boolean compact, String tabs) {
        return (compact ? "" : tabs) + '"' + name + "\":" + (compact ? "" : " ") +
                (compact ? "{%s}" : ("\n" + tabs + "{\n%s\n" + tabs + "}"));
    }


    public String createSectionInList(String name, boolean compact, String tabs) {
        return (compact ? "{%s}" : (tabs + "{\n%s\n" + tabs + "}"));
    }


    public String createFile(String name, boolean compact) {
        return compact ? "{%s}" : "{\n%s\n}";
    }


    public String createValue(String value) {
        return '"' + value + '"';
    }


    public String createListObject(boolean compact, String tabs) {
        return (compact ? "" : tabs) + "%s";
    }


    public boolean isNewLineRequired() {
        return false;
    }
}
