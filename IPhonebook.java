import java.util.Comparator;
public interface IPhonebook{
    public boolean modify(String target,ContactInfoSelector selector,String newValue);
    public boolean modify(int index,ContactInfoSelector selector,String newValue);
    public boolean contains(Contact contact);
    public boolean contains(ContactInfoSelector selector,String target);
    public boolean containsAll(Contact[] contacts);
    public boolean add(Contact contact);
    public boolean add(String name,String number);
    public boolean remove(Contact contact);
    public boolean remove(String name,String number);
    public Contact set(int index,Contact contact);
    public Contact set(int index,String name,String number);
    public Contact remove(int index);
    public Contact get(int index);
    public String queryName(String numberTarget);
    public String queryNumber(String nameTarget);
    public Contact queryContact(ContactInfoSelector selector,String target);
    public void sort(Comparator<Contact> comparator);
    public int indexOf(String target,ContactInfoSelector selector);
    public void print();
    public void sortPrint(Comparator<Contact> comparator);
    public void descendingPrint();
    public int size();
    public Contact[] subContacts(int from,int to);
    public Contact[] backingArray();
}