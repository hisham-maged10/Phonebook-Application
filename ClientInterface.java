public interface ClientInterface{
    public Contact[] loadContacts(String filename);
    public boolean saveContacts(boolean append);
    public boolean saveContacts(String filename,boolean append);
    public boolean modifyContact(ContactInfoSelector selector,String target,String newValue);
    public boolean modifyContact(ContactInfoSelector selector,int index,String newValue);
    public boolean addContact(String name,String number);
    public boolean removeContact(String name,String number);
    public Contact removeContact(int index);
    public boolean removeContact(ContactInfoSelector selector,String target);
    public Contact overwriteContact(int index,String name,String number);
    public boolean overwriteContact(ContactInfoSelector selector,String target,String newName,String newNumber);
    public String query(ContactInfoSelector selector,String target);
    public void displayAscending();
    public void displayDescending();
    public int size();
}