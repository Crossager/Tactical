package net.crossager.tactical.config.type;

public class XmlConfigType {

    public String listObjectSeparator(boolean compact) {
        return "";
    }


    public String createList(String name, boolean compact, String tabs) {
        checkName(name);
        return (tabs + "<" + name + " list=\"\">\n%s\n" + tabs + "</" + name + ">");
    }


    public String createListInList(String name, boolean compact, String tabs) {
        return createList(name, compact, tabs);
    }


    public String createField(String name, boolean compact, String tabs) {
        checkName(name);
        return (compact ? "" : tabs) + "<" + name + ">%s</" + name + ">";
    }


    public String createSection(String name, boolean compact, String tabs) {
        checkName(name);
        return compact ?
                ("<" + name + ">%s</" + name + ">") :
                (tabs + "<" + name + ">\n%s\n" + tabs + "</" + name + ">");
    }


    public String createSectionInList(String name, boolean compact, String tabs) {
        return createSection(name, compact, tabs);
    }


    public String createFile(String name, boolean compact) {
        checkName(name);
        return createSection(name, compact, "");
    }


    public boolean isNewLineRequired() {
        return false;
    }


    public String createValue(String value) {
        return value;
    }


    public String createListObject(boolean compact, String tabs) {
        return createField("element", compact, tabs);
    }

    private void checkName(String name) {
        for (int i = 0; i < 10; i++) {
            if (name.contains(String.valueOf(i))) throw new IllegalStateException("Xml config type does not support numerical characters in field/section names");
        }
    }
}
