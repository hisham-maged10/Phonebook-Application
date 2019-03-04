public enum GUISelector{
    PRINTASC(1),PRINTDESC(2),ADD(3),REMOVE(5),MODIFY(7),OVERWRITE(9),QUERY(11),SAVE(13),EXIT(15);
    private int value;
    private GUISelector(int value)
    {
        this.value=value;
    }
    public int getIntValue()
    {
        return this.value;
    }
    public String getName()
    {
        String name=this.toString().toLowerCase();
        return Character.toUpperCase(name.charAt(0))+name.substring(1);
    }
}