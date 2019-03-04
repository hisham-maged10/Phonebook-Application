import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
public abstract class GUI
{
    private final String INDEX="Via Index (will print all the phonenumbers)";
    private final String INFO="Via Info";
    private static final Scanner input=new Scanner(System.in);
    public void doGUI(String header,String subheader,boolean choice,String... items)
    {
        if(header!=null)
            System.out.println("\t\t\t "+header+" Menu\t \t \t");
        if(subheader!=null)
            System.out.println("\n  "+subheader);
        for(int i=0;i<items.length;i++)
            System.out.println("\t "+(i+1)+"."+items[i]);
        if(choice)
            System.out.print("  Enter your choice: ");
    }
    public void doInteraction(GUISelector selector,PhoneApplication app)
    {
        switch(selector)
        {
            case PRINTASC:
            case PRINTDESC:this.Interaction(selector,"Print Menu","Contacts",app); break;
            case ADD:this.Interaction(selector,"Add","Options",app,"Add a Contact","return to main menu");break;
            case REMOVE:
            case MODIFY:
            case OVERWRITE: this.Interaction(selector,selector.getName()+" Contact Menu","Options",app,INDEX,INFO);break;
            case QUERY:this.Interaction(selector,"Query Menu","Options",app,"Query by Name","Query by Number","Return to main menu");break;
            case SAVE:this.Interaction(selector,"Save Menu","Options",app,"Save in same file","Save in another file","Return to main menu");break;
            case EXIT:this.Interaction(selector,"Exit Menu","Options",app,"Exit without saving","Save and Exit","Return to main menu");break;
        }
    }
    private void Interaction(GUISelector selector,String header,String subheader,PhoneApplication app,String... options)
    {
        this.doGUI(header,subheader,(selector==GUISelector.PRINTASC || selector==GUISelector.PRINTDESC)?false:true,options);
        try
        {
			switch(selector)
            {
                case PRINTASC:app.displayAscending();System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case PRINTDESC:app.displayDescending();System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case ADD:this.addInteraction(app);System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case REMOVE:
                case MODIFY:
                case OVERWRITE:this.getIndexInfoInput(selector,app); System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case QUERY:this.queryInteraction(app);System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case SAVE:this.saveInteraction(app);System.out.println("Press enter to return to main menu"); input.nextLine(); sucessReturn(app);return;
                case EXIT:this.exitInteraction(app); 
            }
        }catch(NoSuchElementException ex)
        {
            GUIClient.getInstance().mainMenuGUI();
        }
    }
    private void saveInteraction(PhoneApplication app)
    {
        try
        {
            switch(input.nextInt())
            {
                case 1:app.saveContacts(true);input.nextLine();return;
                case 2: System.out.print("Enter the new filepath");input.nextLine();
                        System.out.println((app.saveContacts(input.nextLine(),false))?"Operation done successfully":"Operation failed");input.nextLine();return;
                case 3: GUIClient.getInstance().mainMenuGUI();return;
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(NumberFormatException | NoSuchElementException ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    private void exitInteraction(PhoneApplication app)
    {
        try
        {
            switch(input.nextInt())
            {
                case 1:System.exit(0);return;
                case 2:System.out.println((app.saveContacts(true)?"Operation done successfully, Exiting":"Operation failed, Exiting"));System.exit(0);return;
                case 3:GUIClient.getInstance().mainMenuGUI();return;
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(NumberFormatException | NoSuchElementException ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    private void addInteraction(PhoneApplication app)
    {
        try
        {
            switch(input.nextInt())
            {
                case 1:System.out.println(app.addContact(this.getName(),this.getNumber())?"Contact added successfully":"Number already exists"); return;
                case 2:GUIClient.getInstance().mainMenuGUI();return;
                default:doInteraction(GUISelector.ADD,app);return;
            }
        }catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            System.out.println("Press enter to continue");
            input.nextLine();
            GUIClient.getInstance().addGUI();
            return;
        }
        catch(NumberFormatException | NoSuchElementException ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    private void queryInteraction(PhoneApplication app)
    {
        if(app.size()==0)
        {
            System.out.println("\nyou can't use this option yet as no element exists");
            return;
        }
        try
        {
            switch(input.nextInt())
            {
                case 1:System.out.println("Number: "+(app.query(ContactInfoSelector.NAME,this.getName())));return;
                case 2:input.nextLine();System.out.println("Name: "+(app.query(ContactInfoSelector.NUMBER,this.getNumber())));return;
                case 3:return;
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(NumberFormatException | NoSuchElementException ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    private void sucessReturn(PhoneApplication app)
    {
        System.out.println("Operation done successfully, returning to main menu");
        GUIClient.getInstance().mainMenuGUI();
    }
    private void getIndexInfoInput(GUISelector selector,PhoneApplication app)
    {
        if(app.size()==0)
        {
            System.out.println("you can't use this option yet as no element exists");
            return;
        }
        try
        {
            switch(input.nextInt()*selector.getIntValue())
            {
                //(index,info)
                //add(3),remove(5,10),modify(7,14),overwrite(9,18),query(11)
                case 5:  
                case 7:
                case 9:doIndexInfoOperation(selector,INDEX,app);return;
                case 10:
                case 14:
                case 18:doIndexInfoOperation(selector,INFO,app);return;
                default:System.err.println("Wrong input");
                        GUIClient.getInstance().mainMenuGUI();
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(NumberFormatException | NoSuchElementException ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    private void doIndexInfoOperation(GUISelector selector,String operation,PhoneApplication app)
    {
        this.doGUI(selector.getName()+" via "+(operation.equals(INDEX)?"Index":"Information"),null,false);
            switch(operation)
            {
                case INDEX:this.doIndexOperation(selector,app);return;
                case INFO:this.doInfoOperation(selector,app);return;
            }    
    }
    private void doIndexOperation(GUISelector selector,PhoneApplication app)
    {
        try
        {
            int index;
            switch(selector)
            {
                case REMOVE:app.displayAscending();System.out.print("Enter index: ");System.out.println(app.removeContact((input.nextInt())-1)+" was removed");input.nextLine();return;
                
                case MODIFY:
                            app.displayAscending();
                            System.out.print("Enter index: ");
                            index=input.nextInt();
                            this.doGUI(null,"Options",true,"Modify Name","Modify Number","return");
                            int choice=input.nextInt();
                            input.nextLine();
                            if(choice==1)
                            {
                                System.out.print("Enter new Name: ");
                                System.out.println(app.modifyContact(ContactInfoSelector.NAME,index-1,input.nextLine())?"Name was modefied":"Name failed to modify");
                            }
                            else if(choice==2)
                            {
                                System.out.print("Enter new Number: ");
                                System.out.println(app.modifyContact(ContactInfoSelector.NUMBER,index-1,input.nextLine())?"Number was modefied":"Number failed to modify");    
                            }
                            else if(choice==3)
                            {
                                return;
                            }
                            else{
                                System.err.println("Option number unlisted, try again");
                                this.doIndexOperation(selector,app);return;
                            }
                            return;
                case OVERWRITE:
                             app.displayAscending();
                            System.out.print("Enter index: ");
                            index=input.nextInt();
                               System.out.println(app.overwriteContact(index-1,this.getName(),this.getNumber())+" was Overwritten");
                               return;
                default:return;
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            return;
        }
        catch(NumberFormatException | NoSuchElementException  ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.err.println("Bad indexing!, press any key to return to main menu");
            input.nextLine();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(Throwable th)
        {
            th.printStackTrace();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }

    private void doInfoOperation(GUISelector selector,PhoneApplication app)
    {
        try
        {
            switch(selector)
            {
                case REMOVE:System.out.println(app.removeContact(this.getName(),this.getNumber())?"Operation done successfully":"Operation failed");return;
                case MODIFY:
                            this.doGUI(null,"Search and Modify with:",true,"Modify Name","Modify Number","return");
                            int choice=input.nextInt();
                            input.nextLine();
                            if(choice==1)
                            {
                                
                                System.out.print("Enter search target: ");
                                String target=input.nextLine();
                                System.out.print("Enter new Name: ");
                                System.out.println(app.modifyContact(ContactInfoSelector.NAME,target,input.nextLine())?"Name modefied successfully":"Name failed to modefiy");
                            }
                            else if(choice==2)
                            {
                                String target=this.getNumber();
                                System.out.print("Enter new Number: ");
                                System.out.println(app.modifyContact(ContactInfoSelector.NUMBER,target,input.nextLine())?"Number modefied successfully":"Number failed to modefiy");  
                            }
                            else if(choice==3)
                            {
                                return;
                            }
                            else{
                                System.err.println("Option number unlisted, try again");
                                this.doIndexOperation(selector,app);return;
                            }
                            return;
                case OVERWRITE:System.err.println("Operation doesn't exist yet, wait for updates");return;
                default:return;
            }
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Please only enter an integer for the choice,\n For name only enter Letters,\n for Number only enter digits");
            return;
        }
        catch(NumberFormatException | NoSuchElementException  ex)
        {
            System.err.println("Wrong format has been entered or program is being terminated by an output source");
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.err.println("Bad indexing!, press any key to return to main menu");
            input.nextLine();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
        catch(Throwable th)
        {
            th.printStackTrace();
            GUIClient.getInstance().mainMenuGUI();
            return;
        }
    }
    @SuppressWarnings("resource")
    private String getName() throws InputMismatchException,NoSuchElementException
    {
        System.out.print("Enter the Name: ");
        input.nextLine();
        return input.nextLine();
    }
    @SuppressWarnings("resource")
    private String getNumber() throws InputMismatchException,NoSuchElementException
    {
        System.out.print("Enter the Number: ");
       // input.nextLine();
        return input.nextLine();
    }
}