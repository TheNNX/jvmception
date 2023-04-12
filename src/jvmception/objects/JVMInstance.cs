using System.Collections.Generic;

public class JVMInstance
{
    private static Dictionary<int, JVMInstance> theHeap = new Dictionary<int, JVMInstance>();
    private static int nextGcSweepNumber = 1;
    private static int currentObjectId = 1;

    private JVMClass baseClass;
    private int objectId;
    private int lastGcSweepNumber;
    private bool collectable;
    private CallFrame ownerFrame;

    private Dictionary<JVMField, Unit[]> fieldStorage;

    private void init(JVMClass baseClass, CallFrame ownerFrame)
    {
        this.baseClass = baseClass;
        this.fieldStorage = new Dictionary<JVMField, Unit[]>();

        for (int i = 0; i < baseClass.getFields().Length; i++)
        {
            /* For each non-static field, allocate the storage in the object instance.
             */

            if (baseClass.getFields() [i].isStatic() == false)
            {
                fieldStorage.Add(baseClass.getFields()[i], baseClass.getFields() [i].getContainedType().serialize());
            }
        }
        lastGcSweepNumber = 0;
        collectable = false;

        registerInstance();

        this.ownerFrame = ownerFrame;
    }

    public JVMInstance(JVMClass baseClass, CallFrame ownerFrame)
    {
        init(baseClass, ownerFrame);
    }

    public static JVMInstance getFromReference(JVMReference reference)
    {
        if (reference.getObjectId() == 0)
            return null;
        return theHeap[reference.getObjectId()];
    }

    private void registerInstance()
    {
        while (theHeap.ContainsKey(currentObjectId))
        {
            currentObjectId++;
        }
        theHeap.Add(currentObjectId, this);
        objectId = currentObjectId;
    }

    public void markAsCollectable()
    {
        this.collectable = true;
        this.ownerFrame = null;
    }

    public Unit[] getFieldStorage(JVMField field)
    {
        return this.fieldStorage[field];
    }

    public JVMReference createReference()
    {
        return new JVMReference(this.objectId);
    }

    private static void markAsUsed(JVMInstance instance)
    {
        if (instance == null)
            return;

        instance.lastGcSweepNumber = nextGcSweepNumber;

        JVMField[] fields = instance.baseClass.getFields();
        foreach (JVMField field in fields)
        {
            if (field.isStatic())
                continue;

            if (!(field.getContainedType() is JVMReference))
                continue;

            if (((JVMReference)field.get(instance)).getInstance() == null)
                continue;

            if (((JVMReference)field.get(instance)).getInstance().lastGcSweepNumber >= nextGcSweepNumber)
                continue;

            markAsUsed(((JVMReference)field.get(instance)).getInstance());
        }
    }

    public static void doGarbageCollection()
    {
        foreach (int k in theHeap.Keys)
        {
            JVMInstance instance = theHeap[k];
            if (instance.collectable == false)
            {
                markAsUsed(instance);
            }
        }

        JVMField[] staticFields = JVMField.getStaticFields();
        foreach (JVMField field in staticFields)
        {
            if (field.getContainedType() is JVMReference)
            {
                markAsUsed(((JVMReference)field.get(null)).getInstance());
            }
        }

        Queue<int> removeQueue = new Queue<int>();
        foreach (int k in theHeap.Keys)
        {
            JVMInstance instance = theHeap[k];
            if (instance.lastGcSweepNumber < nextGcSweepNumber)
            {
                removeQueue.Enqueue(k);
            }
        }

        foreach (int i in removeQueue)
        {
            theHeap.Remove(i);
        }

        nextGcSweepNumber++;
    }

    public CallFrame getOwnerFrame()
    {
        return ownerFrame;
    }

    public void setOwnerFrame(CallFrame frame)
    {
        this.ownerFrame = frame;
    }

    public int getObjectId()
    {
        return this.objectId;
    }
}
