import java.util.Scanner;
import java.util.NoSuchElementException;
public class GUIClient extends GUI implements GuiInterface
{
    private static GUIClient client=null;
    private PhoneApplication app;
    private GUIClient(PhoneApplication app){
        this.app=app;
    }
    public static GUIClient getInstance()
    {
        if(client==null)
            return client=new GUIClient(new PhoneApplication("./data/PhoneDirectory.txt"));
        else
            return client;
    }
    public void mainMenuGUI()
    {
        this.doGUI("Phone Application","Options",true,"Print Ascendingly","Print Descendingly",
                "Add Contact","Remove Contact","Modify Contact",
                "Overwrite Contact","Query","Save","Exit");
        try(Scanner input=new Scanner(System.in))
        {
            switch(input.nextInt())
            {
                case 1:this.printAscGUI();break;
                case 2:this.printDescGUI();break;
                case 3:this.addGUI();break;
                case 4:this.removeGUI();break;
                case 5:this.modifyGUI();break;
                case 6:this.overwriteGUI();break;
                case 7:this.queryGUI();break;
                case 8:this.saveGUI();break;
                case 9:this.exitGUI();break;
                default: System.err.println("Wrong Input");
                        this.mainMenuGUI();
            }
        }catch(NoSuchElementException ex)
        {
            System.err.println("Program terminating by an outside source!");
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }catch(Throwable th)
        {
            th.printStackTrace();
        }
    }

    @Override
    public void printAscGUI() {
        this.doInteraction(GUISelector.PRINTASC,this.app);

    }

    @Override
    public void printDescGUI() {
        this.doInteraction(GUISelector.PRINTDESC,this.app);

    }

    @Override
    public void addGUI() {
        this.doInteraction(GUISelector.ADD,this.app);
    }

    @Override
    public void removeGUI() {
        this.doInteraction(GUISelector.REMOVE,this.app);
    }

    @Override
    public void modifyGUI() {
        this.doInteraction(GUISelector.MODIFY,this.app);
    }

    @Override
    public void overwriteGUI() {
        this.doInteraction(GUISelector.OVERWRITE,this.app);
    }

    @Override
    public void saveGUI() {
        this.doInteraction(GUISelector.SAVE,this.app);
    }

    @Override
    public void queryGUI() {
        this.doInteraction(GUISelector.QUERY,this.app);
    }

    @Override
    public void exitGUI() {
        this.doInteraction(GUISelector.EXIT,this.app);
    }

    
}