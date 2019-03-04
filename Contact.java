import java.util.InputMismatchException;
public class Contact implements Comparable<Contact>
{
    private String name;
    private String number;
    
    public Contact(String name,String number)
    {
        if(this.isInvalid(name,number))
            throw new InputMismatchException("Wrong input, man");
        this.name=name;
        this.number=number;
    }
    private boolean isInvalid(String name,String number)
    {
        return (name==null || number==null || name.isEmpty() || number.isEmpty() || number.length()<3 || name.matches(".*[\\d\\W_]+.*") || number.matches(".*[\\D]+.*"));
    } 

    public String getName()
    {
        return this.name;
    }

    public String getNumber()
    {
        return this.number;
    }

    public boolean setName(String name)
    {
        if(name.matches(".*[\\d\\W_]+.*"))
            throw new InputMismatchException();
        else
        {
            this.name=name;
            return true;
        }
    }

    public boolean setNumber(String number)
    {
        if(number.matches(".*[\\D]+.*"))
            throw new InputMismatchException();
        else
        {
            this.number=number;
            return true;
        }
    }

    @Override
    public int compareTo(Contact anotherContact)
    {
        return this.name.compareTo(anotherContact.getName());
    }

    @Override
    public String toString()
    {
        return this.name+", "+this.number;
    }

    @Override
    public int hashCode()
    {
        return this.number.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this==o)
            return true;
        if(!(o instanceof Contact))
            return false;
        return ((Contact)o).getName().equalsIgnoreCase(this.name) && ((Contact)o).getNumber().equalsIgnoreCase(this.number);
    }

}