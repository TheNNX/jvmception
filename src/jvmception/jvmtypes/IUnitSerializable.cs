

public interface IUnitSerializable
{
    Unit[] serialize();
    void deserialize(Unit[] data);
    int getSerializedSize();
}
