import java.util.List;
import java.util.ArrayList;

public class Day07 {

    public static void Run(List<String>input){
        var fileSystem = new FileSystem();
        ProcessInput(input,fileSystem);
        
        p1(fileSystem);
        p2(fileSystem);
    }

    public static void p1(FileSystem filesystem)
    {
        var foundFolders = SubFoldersUpToSize( filesystem.getRoot(), 100000);
        int totalSize =0;
        for(var folder: foundFolders)
            totalSize += folder.getTotalSize(true);
        System.out.println("07.1: " + totalSize);
    }
    public static void p2(FileSystem filesystem)
    {
        int requiredForUpdate = 30000000;
        int totalAvailable = 70000000;
        int currentFreeSpace = totalAvailable - filesystem.getRoot().getTotalSize(true);
        int neededToFree = requiredForUpdate - currentFreeSpace ;
        var deleteMe = SmallestSubFolderInSizeRange(filesystem.getRoot(),
            neededToFree, Integer.MAX_VALUE);
        if (deleteMe ==null)
            System.out.println("07.2: no folder is big enough");
        else
            System.out.println("07.2: " + deleteMe.getTotalSize(true));
    }
    public static void ProcessInput(List<String>input, FileSystem fileSystem)
    {
        for(var line: input)
        {
            var tokens = line.split(" ");
            if (tokens[0].compareTo( "$")==0)
            {
              if (tokens[1].compareTo("cd")==0 )
                fileSystem.SetCurrent(tokens[2]);
            }
            else if (tokens[0].compareTo("dir")==0)
            {
                fileSystem.getCurrent().mkDir(tokens[1]);
            }
            else
            {
                fileSystem.getCurrent().createFile(tokens[1], Integer.parseInt(tokens[0]));
            }
        }
    }
    
    public static List<Folder> SubFoldersUpToSize(Folder start, int maxSize)
    {
        var foundFolders = new ArrayList<Folder>();
        for(var sub: start.getSubFolders())
        {
            for(var subFound : SubFoldersUpToSize(sub,maxSize))
                foundFolders.add(subFound);
            int subSize = sub.getTotalSize(true);
            System.out.println("folder "+sub.getName()+" totalSize: "+subSize);
            if (sub.getTotalSize(true) <= maxSize)
                {
                    System.out.println("folder "+sub.getName()+ " is at most " +maxSize);
                    foundFolders.add(sub);
                }
        }
        return foundFolders;
    }
    
    public static Folder SmallestSubFolderInSizeRange(Folder start, int minSize, int maxSize)
    {
        int smallestSize=Integer.MAX_VALUE;
        Folder smallestFolder=null;
        for(var sub: start.getSubFolders())
        {
            var smallestInSub = SmallestSubFolderInSizeRange(sub, minSize, maxSize);
            boolean updatedSmallest=false;
            if (smallestInSub != null)
            {
                int size = smallestInSub.getTotalSize(true);
                if (size < smallestSize)
                {
                    smallestSize=size;
                    smallestFolder=smallestInSub;
                    maxSize = smallestSize;
                    updatedSmallest=true;
                }
            }
            // check sub if one of its subs didn't already win 
            // (sub can't be smaller than one of its subs so don't check if one of them already won)
            if (!updatedSmallest)
            {
                int size = sub.getTotalSize(true);
                if ((size >= minSize) && (size <= maxSize) && (size < smallestSize))
                {
                    smallestSize=size;
                    maxSize=smallestSize;
                    smallestFolder=sub;
                }
            }
        }
        return smallestFolder;
    }
static class FileSystem{
    Folder _root;
    Folder _current;
    
    public FileSystem(){
        _root = new Folder(null, "/");
        _current = _root;
    }
    public Folder getRoot() {return _root;}
    public Folder getCurrent(){return _current;}

    public Folder SetCurrent(String folderName)
    {
        if (folderName.compareTo("/") == 0)
            _current=_root;
        else
        {
            var newFolder = _current.getFolder(folderName);
            if (newFolder != null)
                _current=newFolder;
        }
        return _current;
    }
}
static class Folder{
    String _name;
    Folder _parent;
    ArrayList<Folder> _subfolders;
    ArrayList<File> _files;

    public Folder(Folder parent, String name)
    {
        _name=name;
        _parent=parent;
        _subfolders=new ArrayList<Folder>();
        _files = new ArrayList<File>();
    }
    public Folder getParent(){return _parent;}
    public String getName(){return _name;}
    public List<Folder> getSubFolders(){return _subfolders;    }
    public List<File> getFiles(){return _files;}

    public void mkDir(String name)
    {
        _subfolders.add(new Folder(this,name));
    }
    public void createFile(String name, int size)
    {
        _files.add(new File(name, size));
    }
    public Folder getFolder(String name)
    {
        if(name.compareTo("..")==0)
            return _parent;
        for(var sub: _subfolders)
        {
            if (sub.getName().compareTo(name)==0)
            return sub;
        }
        return null;
    }
    public int getTotalSize(boolean recursive)
    {
        int size = 0;
        for(var file: getFiles())
            size += file.getSize();
        if (recursive)
        {
            for(var sub: getSubFolders())
                size += sub.getTotalSize(true);
        }
        return size;
    }
}
static class File{
    String _name;
    int _size;
    
    public File(String name, int size)
    {
        _name=name;
        _size= size;
    }
    public String getName(){return _name;}
    public int getSize(){return _size;}
}
}

