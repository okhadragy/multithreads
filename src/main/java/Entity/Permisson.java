package Entity;

import java.util.ArrayList;

public class Permisson implements Entity{
    private Role role;
    private ArrayList<String> actions;

    public Permisson(Role role, ArrayList<String> actions){
        this.role = role;
        this.actions = actions;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Role Code: " + role.getCode()+"\nActions: "+actions;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Permisson){
            return this.role == ((Permisson)obj).role;
        }
        return false;
    }

    @Override
    public Object getKey(){
        return this.role;
    }
}
