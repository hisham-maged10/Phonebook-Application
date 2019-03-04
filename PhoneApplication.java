public class PhoneApplication implements ClientInterface {

    private String filename;
    private Phonebook phonebook;
    public PhoneApplication(String filename)
    {
        this.filename=filename==null?"":filename;
        phonebook=new Phonebook(this.loadContacts(filename));
    }
    public PhoneApplication()
    {
        this(null);
    }

    @Override
    public boolean addContact(String name, String number) {
        return phonebook.add(name,number);
    }

    @Override
    public boolean removeContact(String name, String number) {
        return phonebook.remove(name,number);
    }
    @Override
    public Contact removeContact(int index) {
        return phonebook.remove(index);
    }

    @Override
    public boolean removeContact(ContactInfoSelector selector,String target) {
        return phonebook.remove(selector,target);
    }

    @Override
    public Contact overwriteContact(int index, String name, String number) {
        return phonebook.set(index,name,number);
    }

    @Override
    public boolean overwriteContact(ContactInfoSelector selector,String target,String newName,String newNumber) {
        return phonebook.set(selector,target,newName,newNumber);
    }

    @Override
    public String query(ContactInfoSelector selector,String target)
    {
        switch(selector)
        {
            case NAME:return phonebook.queryNumber(target); 
            case NUMBER:return phonebook.queryName(target);
            default:return "Invalid Selector";
        }
    }

    @Override
    public void displayAscending()
    {
        this.phonebook.print();
        System.out.println("\n Number of contacts: "+this.size());
    }

    @Override
    public void displayDescending()
    {
        this.phonebook.descendingPrint();
        System.out.println("\n Number of contacts: "+this.size());
    }

    @Override
    public int size() {
        return this.phonebook==null?0:this.phonebook.size();
    }

    @Override
    public Contact[] loadContacts(String filename) {
        return FileHandler.loadFile(filename);
    }

    @Override
    public boolean saveContacts(boolean append) {
        this.validateFile();
        return FileHandler.<Contact>writeFile(phonebook.backingArray(),this.filename,append);
    }
    @Override
    public boolean saveContacts(String filename,boolean append) {
        filename=filename.endsWith(".txt")?filename:filename+".txt";
        return FileHandler.<Contact>writeFile(phonebook.backingArray(),filename,append);
    }
    @Override
    public boolean modifyContact(ContactInfoSelector selector,String target,String newValue) {
        return phonebook.modify(target,selector,newValue);
    }
    public boolean modifyContact(ContactInfoSelector selector,int index,String newValue) {
        return phonebook.modify(index,selector,newValue);
    }

    private void validateFile()
    {
        if(this.filename.isEmpty())
            this.filename="./data/PhoneDirectory.txt";
    }
}