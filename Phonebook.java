import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.ListIterator;
public class Phonebook implements IPhonebook {
    private List<Contact> contacts;
    private Set<String> numbers;
    {
        this.numbers = new HashSet<>();
        this.contacts = new ArrayList<>();
    }

    public Phonebook(Contact[] contacts) {
        initialize(contacts);
    }

    public Phonebook() {
        this(null);
    }

    private boolean initialize(Contact[] contacts) {
        if (contacts == null)
            return false;

        for (Contact e : contacts)
            if (e!=null && numbers.add(e.getNumber()))
                this.contacts.add(e);
            else
                continue;

        return true;
    }

    public boolean add(Contact contact){
        return contact!=null && numbers.add(contact.getNumber()) && this.contacts.add(contact);
    }

    @Override
    public boolean add(String name, String number) {
        if(name==null || number== null)
            return false;
        return this.add(new Contact(name,number));
    }

    @Override
    public boolean modify(String target, ContactInfoSelector selector, String newValue) {
        if(target==null || newValue==null)
            return false;
        int index=this.indexOf(target,selector);
        if(index==-1)
            return false;
        else
            switch(selector)
            {
                case NAME: this.get(index).setName(newValue); return true;
                case NUMBER: this.get(index).setNumber(newValue);return true; 
                default : return false;
            }
        
    }
    @Override
    public boolean modify(int index, ContactInfoSelector selector, String newValue) {
        if(index<0 || index>=this.contacts.size() || newValue==null)
            return false;
            switch(selector)
            {
                case NAME: this.get(index).setName(newValue); return true;
                case NUMBER: this.get(index).setNumber(newValue);return true; 
                default : return false;
            }
        
    }
    public int indexOf(String target,ContactInfoSelector selector){
        if(target==null)
            return -1;
        validateTarget(target,selector);
        for(ListIterator<Contact> it=this.contacts.listIterator();it.hasNext();)
        {
            switch(selector)
            {
                case NAME: if(it.next().getName().equalsIgnoreCase(target))
                                return it.previousIndex();
                            break;
                case NUMBER: if(it.next().getNumber().equalsIgnoreCase(target))
                                return it.previousIndex();
                            break;
                default:return -1;
            }
        }
        return -1;
    }
    @Override
    public boolean contains(Contact contact) {
        return this.contacts.contains(contact);
    }

    @Override
    public boolean contains(ContactInfoSelector selector, String target) {
        
        if(target==null) 
            return false;
        return this.indexOf(target,selector)==-1?false:true;
        
    }
    private void validateTarget(String target,ContactInfoSelector selector)
    {
        if(selector==ContactInfoSelector.NAME)
            if(target.matches(".*[\\d\\W_]+.*"))
                throw new InputMismatchException("Name can't have digits or symbols");
        else if(selector==ContactInfoSelector.NUMBER)
            if(target.matches(".*[\\D]+.*"))
                throw new InputMismatchException("Number can't have non digits");

    }
    @Override
    public boolean containsAll(Contact[] contacts) {
        return this.contacts.containsAll(Arrays.asList(contacts));
    }

    @Override
    public boolean remove(Contact contact) {
        return this.contacts.remove(contact);
    }

    @Override
    public boolean remove(String name, String number) {
        try{
            return this.remove(new Contact(name,number));
        }catch(InputMismatchException ex)
        {
            return false;
        }
    }

    @Override
    public Contact set(int index, Contact contact) {
        if(contact==null)
            throw new NullPointerException("You can't add a null to a phonebook");
        return this.contacts.set(index,contact);
    }

    @Override
    public Contact set(int index, String name, String number) {
            return this.set(index,new Contact(name,number));
    }
    @Override 
    public boolean set(ContactInfoSelector selector,String target,String newName,String newNumber)
    {
        validateTarget(target,selector);
        Contact c=new Contact(newName,newNumber);
            for(ListIterator<Contact> it=this.contacts.listIterator();it.hasNext();)
            {
                switch(selector)
                {
                    case NAME:if(it.next().getName().equalsIgnoreCase(target)){it.set(c); return true;}break;
                    case NUMBER:if(it.next().getNumber().equalsIgnoreCase(target)){it.set(c); return true;}break;
                }
            }
        return false;
    }

    @Override
    public Contact remove(int index) {
        return this.contacts.remove(index);
    }
    @Override
    public boolean remove(ContactInfoSelector selector, String target)
    {
        validateTarget(target,selector);
            for(ListIterator<Contact> it=this.contacts.listIterator();it.hasNext();)
            {
                switch(selector)
                {
                    case NAME:if(it.next().getName().equalsIgnoreCase(target)){it.remove(); return true;}break;
                    case NUMBER:if(it.next().getNumber().equalsIgnoreCase(target)){it.remove(); return true;}break;
                }
            }
        return false;
    }

    @Override
    public Contact get(int index) {
        return this.contacts.get(index);
    }

    @Override
    public String queryName(String numberTarget) {
        
        int idx=this.indexOf(numberTarget,ContactInfoSelector.NUMBER);
        return idx==-1?"Not Found":this.get(idx).getName();
    }

    @Override
    public String queryNumber(String nameTarget)
    {
        int idx=this.indexOf(nameTarget,ContactInfoSelector.NAME);
        return idx==-1?"Not Found":this.get(idx).getNumber();
    }

    @Override
    public Contact queryContact(ContactInfoSelector selector, String target) {
        int idx=this.indexOf(target,selector);
        return idx==-1?null:this.get(idx);
    }

    @Override
    public void sort(Comparator<Contact> comparator) {
        this.contacts.sort(comparator);
    }

    @Override
    public void print()
    {
        this.contacts.sort(null);
        this.listItems();
    }

    private void listItems()
    {
        int i=1;
        for(Contact e: this.contacts)
            System.out.println(i+++". "+e);
    }
    @Override
    public void sortPrint(Comparator<Contact> comparator)
    {
        this.contacts.sort(comparator);
        this.listItems();
    }

    @Override
    public void descendingPrint()
    {
        this.contacts.sort(Collections.reverseOrder());
        this.listItems();
    }
    
    @Override
    public int size()
    {
        return this.contacts==null?0:this.contacts.size();
    }

    @Override
    public Contact[] subContacts(int from,int to)
    {
        if(from<0 || to<0 || from>to || from>=this.contacts.size() || to>this.contacts.size())
            throw new IndexOutOfBoundsException("Incorrect Index");
        return this.contacts.subList(from,to).toArray(new Contact[0]);
    } 

    @Override
    public Contact[] backingArray()
    {
        return this.contacts.toArray(new Contact[0]);
    }
}