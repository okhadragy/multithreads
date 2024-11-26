import java.lang.reflect.Array;
import java.util.Arrays;

public class Table{
    private Entity elements[];
    private int numberOfElements;
    private int size;
    private Class cl;

    Table(Class cl, int size){
        elements = (Entity[]) Array.newInstance(cl, size);
        this.cl = cl;
        this.size = size;
    }

    public void add(Entity e){
        if (e.isSaved()) {
            elements[numberOfElements] = e;
            numberOfElements++;
        }
    }

    public void delete(Entity e) throws IndexOutOfBoundsException{
        int index = get(e);
        if (index!=-1) {
            numberOfElements--;
            elements[index] = elements[numberOfElements];
        }else{
            throw new IndexOutOfBoundsException("This Element does not exist");
        }
    }

    public void delete(int index) throws IndexOutOfBoundsException{
        elements[index] = elements[numberOfElements-1];
        numberOfElements--;        
    }

    public int get(Entity e){
        for (int i = 0; i < numberOfElements; i++) {
            if (elements[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public Entity get(int index) throws IndexOutOfBoundsException{
        return elements[index];        
    }

    public void update(int firstElementIndex, Entity e) throws IndexOutOfBoundsException{
        elements[firstElementIndex] = e;
    }

    public void update(Entity firstElement, Entity e) throws IndexOutOfBoundsException{
        int index = get(firstElement);
        if (index!=-1) {
            elements[index] = e;
        }else{
            throw new IndexOutOfBoundsException("This Element does not exist");    
        }   
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void erase(){
        elements = (Entity[]) Array.newInstance(cl, 100);
    }

    public String toString(){
        String s ="";
        for (int i = 0; i < numberOfElements; i++) {
            s += elements[i].toString() + "\n";
        }
        return s;
    }

    public int getSize() {
        return size;
    }

    // sort is not working
    public void sort(){
        Arrays.sort(elements);
    }

}
